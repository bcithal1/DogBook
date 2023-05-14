package dogbook.service;

import dogbook.enums.EventAccessLevel;
import dogbook.enums.EventInvitedStatus;
import dogbook.enums.GoingStatus;
import dogbook.model.*;
import dogbook.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    AuthenticatedUserService authenticatedUserService;

    @Autowired
    EventRepo eventRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    EventUserRelationRepo eventUserRelationRepo;

    @Autowired
    DogOwnerRepo dogOwnerRepo;

    @Autowired
    DogRepo dogRepo;

    public Event createEvent(Event event) {

        Integer userId = authenticatedUserService.getId();
        Optional<User> userFound = userRepo.findById(userId);

        if (userFound.isEmpty())
            return null;

        event.setEventId(null);
        event.setHostId(userId);
        EventUserRelations eventUserRelations = new EventUserRelations(
                userFound.get(),
                event,
                EventAccessLevel.EVENT_HOST,
                GoingStatus.GOING,
                EventInvitedStatus.INVITED);

        eventUserRelationRepo.save(eventUserRelations);
        return event;
    }

    public User inviteUserToEvent(Integer eventId, Integer userId) {
        Integer hostId = authenticatedUserService.getId();
        Optional<User> userFound = userRepo.findById(userId);
        Optional<Event> eventFound = eventRepo.findById(eventId);
        // now make sure the event we are sending invite out for is actually hosted by the current user,
        // we can check relations and see if the accessLevel is Host with hostID and eventId filtered.

        List<EventUserRelations> guestListFound = eventUserRelationRepo.findAll().stream()
                .filter(relation -> Objects.equals(relation.getEvent().getEventId(), eventId) && Objects.equals(relation.getUser().getId(), hostId))
                .collect(Collectors.toList());

        // we need to know if the userId has already been invited to the same event or not, if yes don't create the relation object
        List<EventUserRelations> alreadyInvitedUserList = eventUserRelationRepo.findAll().stream()
                .filter(relation -> Objects.equals(relation.getEvent().getEventId(), eventId) && Objects.equals(relation.getUser().getId(), userId))
                .collect(Collectors.toList());

        if (userFound.isPresent() && eventFound.isPresent() && !guestListFound.isEmpty() && alreadyInvitedUserList.isEmpty()) {

            if (guestListFound.get(0).getEventaccessLevel() == EventAccessLevel.EVENT_HOST) {
                // now create a new event-user relationship that is linking to the invited user

                EventUserRelations eventUserRelations = new EventUserRelations(
                        userFound.get(),
                        eventFound.get(),
                        EventAccessLevel.EVENT_GUEST,
                        GoingStatus.NOTGOING,
                        EventInvitedStatus.INVITED);
                return eventUserRelationRepo.save(eventUserRelations).getUser();


            }
        } else {
            // throw new HttpClientErrorException( HttpStatus.NOT_FOUND, "Event or User not Found");
        }
        return null;

    }

    public List<Event> getEventList() {
        return eventRepo.findAll();
    }

    public Event getEventListById(Integer id) {
        Event event = null;

        if (eventRepo.findById(id).isPresent())
            event = eventRepo.findById(id).get();

        return event;
    }

    public List<Event> getEventListOfCurrentUser() {

        Integer hostId = authenticatedUserService.getId();
        return eventRepo.findByHostId(hostId);
    }

    public List<Event> getInvitedEventList() {

        Integer hostId = authenticatedUserService.getId();

        List<EventUserRelations> eventUserRelationsList = eventUserRelationRepo.findByUserId(hostId);
        if (!eventUserRelationsList.isEmpty()) {
            return eventUserRelationRepo.findByUserId(hostId).stream().map(relation -> relation.getEvent()).collect(Collectors.toList());
        }
        return null;
    }

    public Event acceptEventInvite(Integer eventId) {
        Integer hostId = authenticatedUserService.getId();
        List<EventUserRelations> eventUserRelationsFound = eventUserRelationRepo.findByUserId(hostId).stream()
                .filter(relation -> Objects.equals(relation.getEvent().getEventId(), eventId)).collect(Collectors.toList());

        if (!eventUserRelationsFound.isEmpty()) {

            if (eventUserRelationsFound.get(0).getEventInvitedStatus() == EventInvitedStatus.INVITED) {

                eventUserRelationsFound.get(0).setGoingStatus(GoingStatus.GOING);
                return eventUserRelationRepo.save(eventUserRelationsFound.get(0)).getEvent();

            }
            return null;
        }
        return null;
    }

    public String userApplyToEvent(Integer eventId) {
        Integer hostId = authenticatedUserService.getId();
        Optional<Event> eventFound = eventRepo.findById(eventId);
        Optional<User> userFound = userRepo.findById(hostId);


        if (eventFound.isPresent() && userFound.isPresent()) {
            List<EventUserRelations> eventUserRelationsFound = eventUserRelationRepo.findByUserId(hostId).stream()
                    .filter(relation -> Objects.equals(relation.getEvent().getEventId(), eventId)).collect(Collectors.toList());
            if (!eventUserRelationsFound.isEmpty() && eventUserRelationsFound.get(0).getEventInvitedStatus() == EventInvitedStatus.INVITED) {
                return "User already invited to the event";
            }

            EventUserRelations eventUserRelations = new EventUserRelations(userFound.get(), eventFound.get(), EventAccessLevel.NO_ACCESS, GoingStatus.NOTGOING, EventInvitedStatus.APPLIED);

            eventUserRelationRepo.save(eventUserRelations);

            return "User has successfully applied to the event, please wait for event host to accept";
        }
        return null;
    }

    public String hostProcessApplication(Integer eventId, Integer userId) {
        Integer hostId = authenticatedUserService.getId();
        Optional<Event> eventFound = eventRepo.findById(eventId);
        Optional<User> userFound = userRepo.findById(hostId);


        if (eventFound.isPresent() && userFound.isPresent() && Objects.equals(eventFound.get().getHostId(), hostId)) {
            List<EventUserRelations> eventUserRelationsFound = eventUserRelationRepo.findByUserId(userId).stream()
                    .filter(relation -> Objects.equals(relation.getEvent().getEventId(), eventId)).collect(Collectors.toList());
            if (!eventUserRelationsFound.isEmpty() && eventUserRelationsFound.get(0).getEventInvitedStatus() == EventInvitedStatus.APPLIED) {

                eventUserRelationsFound.get(0).setEventInvitedStatus(EventInvitedStatus.INVITED);
                eventUserRelationsFound.get(0).setGoingStatus(GoingStatus.GOING);
                eventUserRelationsFound.get(0).setEventaccessLevel(EventAccessLevel.EVENT_GUEST);
                eventUserRelationRepo.save(eventUserRelationsFound.get(0));
                return String.format("Host has accepted the %s into event %s", userFound.get().getFullName(), eventFound.get().getEventTitle());
            }
            return null;
        }
        return null;
    }

    //we want this api to be available to all users so all user can see who is going to what event
    public List<EventUserRelations> getAllEventUserRelationsForEvent(Integer eventId) {

        Integer hostId = authenticatedUserService.getId();
        Optional<Event> eventFound = eventRepo.findById(eventId);
        Optional<User> userFound = userRepo.findById(hostId);
        if (eventFound.isPresent() && userFound.isPresent()) {
            return eventUserRelationRepo.findByEventId(eventId);
        }
        return null;
    }

    public Event updateEvent(Event event, Integer eventId) {

        Integer hostId = authenticatedUserService.getId();
        Optional<Event> eventFound = eventRepo.findById(eventId);
        if(eventFound.isPresent() && Objects.equals(event.getHostId(), hostId)){
            eventFound.get().setEventDescription(event.getEventDescription());
            eventFound.get().setEventLocation(event.getEventLocation());
            eventFound.get().setEventTitle(event.getEventTitle());
            eventFound.get().setDate(event.getDate());
            eventFound.get().setLat(event.getLat());
            eventFound.get().setLng(event.getLng());
            eventFound.get().setTime(event.getTime());
            return eventRepo.save(eventFound.get());

        }

        return null;

    }

    public Event deleteEventById(Integer eventId) {

        Integer hostId = authenticatedUserService.getId();
        Optional<Event> eventFound = eventRepo.findById(eventId);
        if(eventFound.isPresent() && Objects.equals(eventFound.get().getHostId(), hostId)){

            List<Integer> relationsIdList =  eventUserRelationRepo.findByEventId(eventId).stream().map(EventUserRelations::getId).collect(Collectors.toList());
            eventUserRelationRepo.deleteAllById(relationsIdList);

            return eventFound.get();
        }

        return null;
    }

    public List<Dog> getAllDogsInEvent(Integer eventId) {
        Integer hostId = authenticatedUserService.getId();
        Optional<Event> eventFound = eventRepo.findById(eventId);
        Optional<User> userFound = userRepo.findById(hostId);
//        get the list of users that are in the event
        List<EventUserRelations> eventUserRelationsList = eventUserRelationRepo.findAll().stream().filter((eventUserRelations ->Objects.equals(eventUserRelations.getEvent().getEventId(), eventId) )).collect(Collectors.toList());
//        get all the dogs id these user own
        List<DogOwner> dogOwnersList =  eventUserRelationsList.stream().flatMap(eventUserRelations -> dogOwnerRepo.findAllByUserId(eventUserRelations.getUser().getId()).stream()).collect(Collectors.toList());
//        get all the dogs in the event
        List<Dog> dogListInEvent = dogOwnersList.stream().map(dogOwner -> dogRepo.findById(dogOwner.getDogId()).get()).collect(Collectors.toList());


        if(eventFound.isPresent() && userFound.isPresent()){
            return dogListInEvent;
        }
        return null;

    }
}
