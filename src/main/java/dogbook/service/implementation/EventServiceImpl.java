package dogbook.service.implementation;


import dogbook.enums.EventAccessLevel;
import dogbook.enums.EventInvitedStatus;
import dogbook.enums.GoingStatus;
import dogbook.model.Event;
import dogbook.model.EventUserRelations;
import dogbook.model.User;
import dogbook.repository.EventRepo;
import dogbook.repository.EventUserRelationRepo;
import dogbook.repository.UserRepo;
import dogbook.service.AuthenticatedUserService;
import dogbook.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class EventServiceImpl implements EventService {

    @Autowired
    AuthenticatedUserService authenticatedUserService;
    @Autowired
    EventRepo eventRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    EventUserRelationRepo eventUserRelationRepo;

    @Override
    public Event createEvent(Event event) {

        Integer userId = authenticatedUserService.getId();
        Optional<User> userFound = userRepo.findById(userId);

        if(userId==null || userFound.isEmpty()){
            return null;
        }
        event.setEventId(null);
        event.setHostId(userId);
        EventUserRelations eventUserRelations = new EventUserRelations(userFound.get(), event, EventAccessLevel.EVENT_HOST, GoingStatus.GOING, EventInvitedStatus.INVITED);

        eventUserRelationRepo.save(eventUserRelations);

        return event;
    }

    @Override
    public User inviteUserToEvent(Integer eventId, Integer userId) {
        Integer hostId = authenticatedUserService.getId();
        Optional<User> userFound = userRepo.findById(userId);
        Optional<Event> eventFound = eventRepo.findById(eventId);
//        now make sure the event we are sending invite out for is actually hosted by the current user,
//        we can check relations and see if the accessLevel is Host with hostID and eventId filtered.
        List<EventUserRelations> guestListFound = eventUserRelationRepo.findAll().stream()
                .filter(relation -> relation.getEvent().getEventId()==eventId && relation.getUser().getId()==hostId)
                .collect(Collectors.toList());

        if(userFound.isPresent() && eventFound.isPresent() && !guestListFound.isEmpty()){

            if(guestListFound.get(0).getEventaccessLevel() == EventAccessLevel.EVENT_HOST){
//                now create a new event-user relationship that is linking to the invited user


                EventUserRelations eventUserRelations = new EventUserRelations(userFound.get(), eventFound.get(), EventAccessLevel.EVENT_GUEST, GoingStatus.NOTGOING, EventInvitedStatus.INVITED);

                return eventUserRelationRepo.save(eventUserRelations).getUser();


            }
            return null;
        }else{
//            throw new HttpClientErrorException( HttpStatus.NOT_FOUND, "Event or User not Found");
            return null;
        }

    }

    @Override
    public List<Event> getEventList() {
        return eventRepo.findAll();
    }

    @Override
    public Event getEventListById(Integer id) {
        return eventRepo.findById(id).get();
    }

    @Override
    public List<Event> getEventListOfCurrentUser() {

        Integer hostId = authenticatedUserService.getId();
        return eventRepo.findByHostId(hostId);
    }

    @Override
    public List<Event> getInvitedEventList() {

        Integer hostId = authenticatedUserService.getId();

        List<EventUserRelations> eventUserRelationsList = eventUserRelationRepo.findByUserId(hostId);
        if (!eventUserRelationsList.isEmpty()) {
            return eventUserRelationRepo.findByUserId(hostId).stream().map(relation -> relation.getEvent()).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Event acceptEventInvite(Integer eventId) {
        Integer hostId = authenticatedUserService.getId();
        List<EventUserRelations> eventUserRelationsFound = eventUserRelationRepo.findByUserId(hostId).stream()
                .filter(relation -> relation.getEvent().getEventId()==eventId).collect(Collectors.toList());;
        if(!eventUserRelationsFound.isEmpty() ){

            if(eventUserRelationsFound.get(0).getEventInvitedStatus() == EventInvitedStatus.INVITED){

                eventUserRelationsFound.get(0).setGoingStatus(GoingStatus.GOING);
                return eventUserRelationRepo.save(eventUserRelationsFound.get(0)).getEvent();

            }
            return null;
        }
        return null;
    }

    @Override
    public String userApplyToEvent(Integer eventId) {
        Integer hostId = authenticatedUserService.getId();
        Optional<Event> eventFound = eventRepo.findById(eventId);
        Optional<User> userFound =userRepo.findById(hostId);


        if(eventFound.isPresent() && userFound.isPresent()){

            List<EventUserRelations> eventUserRelationsFound = eventUserRelationRepo.findByUserId(hostId).stream()
                    .filter(relation->relation.getEvent().getEventId()==eventId).collect(Collectors.toList());
            if(!eventUserRelationsFound.isEmpty() && eventUserRelationsFound.get(0).getEventInvitedStatus()==EventInvitedStatus.INVITED){
                return "User already invited to the event";
            }

            EventUserRelations eventUserRelations = new EventUserRelations(userFound.get(), eventFound.get(), EventAccessLevel.NO_ACCESS, GoingStatus.NOTGOING, EventInvitedStatus.APPLIED);

            eventUserRelationRepo.save(eventUserRelations);

            return "User has successfully applied to the event, please wait for event host to accept";
        }
        return null;
    }

    @Override
    public String hostProcessApplication(Integer eventId,Integer userId) {
        Integer hostId = authenticatedUserService.getId();
        Optional<Event> eventFound = eventRepo.findById(eventId);
        Optional<User> userFound =userRepo.findById(hostId);


        if(eventFound.isPresent() && userFound.isPresent() && eventFound.get().getHostId()==hostId){

            List<EventUserRelations> eventUserRelationsFound = eventUserRelationRepo.findByUserId(userId).stream()
                    .filter(relation->relation.getEvent().getEventId()==eventId).collect(Collectors.toList());
            if(!eventUserRelationsFound.isEmpty() && eventUserRelationsFound.get(0).getEventInvitedStatus()==EventInvitedStatus.APPLIED){

                eventUserRelationsFound.get(0).setEventInvitedStatus(EventInvitedStatus.INVITED);
                eventUserRelationsFound.get(0).setGoingStatus(GoingStatus.GOING);
                eventUserRelationsFound.get(0).setEventaccessLevel(EventAccessLevel.EVENT_GUEST);
                eventUserRelationRepo.save(eventUserRelationsFound.get(0));
                return String.format("Host has accepted the %s into event %s", userFound.get().getFullName(), eventFound.get().getEventTitle())   ;
            }

            return null;
        }
        return null;
    }

//    we want this api to be available to all users so all user can see who is going to what event
    @Override
    public List<EventUserRelations> getAllEventUserRelationsForEvent(Integer eventId) {

        Integer hostId = authenticatedUserService.getId();
        Optional<Event> eventFound = eventRepo.findById(eventId);
        Optional<User> userFound =userRepo.findById(hostId);
        if(eventFound.isPresent() && userFound.isPresent()){

            return eventUserRelationRepo.findByEventId(eventId);

        }
        return null;

    }
}
