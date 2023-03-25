package dogbook.service.implementation;


import dogbook.enums.EventAccessLevel;
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
import org.springframework.stereotype.Service;

import java.util.Optional;


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
        EventUserRelations eventUserRelations = new EventUserRelations(userFound.get(), event, EventAccessLevel.EVENT_HOST, GoingStatus.GOING);

        eventUserRelationRepo.save(eventUserRelations);

        return event;
    }
}
