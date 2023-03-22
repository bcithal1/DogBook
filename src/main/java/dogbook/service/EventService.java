package dogbook.service;

import dogbook.model.Event;

import java.util.Optional;

public interface EventService {
    Event createEvent(Integer userId, Event event);
}
