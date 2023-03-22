package dogbook.service.implementation;

import dogbook.model.Event;
import dogbook.repository.EventRepo;
import dogbook.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class EventServiceImpl implements EventService {


    @Autowired
    EventRepo eventRepo;
    @Override
    public Event createEvent(Integer userId, Event event) {
        return eventRepo.save(event);
    }
}
