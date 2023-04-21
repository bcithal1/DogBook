package dogbook.service;

import dogbook.mappers.EventUserMapper;
import dogbook.model.Event;
import dogbook.model.EventUserRelations;
import dogbook.model.User;
import dogbook.model.UserEventDTO;
import dogbook.repository.EventRepo;
import dogbook.repository.EventUserRelationRepo;
import dogbook.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventUserDTOService {

    @Autowired
    EventUserMapper eventUserMapper;

    @Autowired
    UserRepo userRepo;

    @Autowired
    EventRepo eventRepo;

    @Autowired
    EventUserRelationRepo eventUserRelationRepo;

    public UserEventDTO getEventUserDTOResponse(Integer eventId, Integer userId) {
        Optional<User> userFound = userRepo.findById(userId);
        Optional<Event> eventFound = eventRepo.findById(eventId);
        List<EventUserRelations> eventUserRelationsFound = eventUserRelationRepo.findByEventId(eventId)
                .stream()
                .filter(eventUserRelations -> eventUserRelations.getUser().getId()==userId)
                .collect(Collectors.toList());
        if(userFound.isPresent() && eventFound.isPresent() && !eventUserRelationsFound.isEmpty()){
            return eventUserMapper.mapFromUserEventAndEventUserRelations(userFound.get(),eventFound.get(),eventUserRelationsFound.get(0));
        }

        return null;

    }
}
