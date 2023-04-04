package dogbook.service;

import dogbook.model.Event;
import dogbook.model.EventUserRelations;
import dogbook.model.User;

import java.util.List;

public interface EventService {
    Event createEvent(Event event);

    User inviteUserToEvent(Integer eventId, Integer userId);

    List<Event> getEventList();

    Event getEventListById(Integer id);

    List<Event> getEventListOfCurrentUser();

    List<Event> getInvitedEventList();

    Event acceptEventInvite(Integer eventId);

    String userApplyToEvent(Integer eventId);

    String hostProcessApplication(Integer eventId,Integer userId);

    List<EventUserRelations> getAllEventUserRelationsForEvent(Integer eventId);
}
