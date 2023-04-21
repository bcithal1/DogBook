package dogbook.model;

import dogbook.enums.EventAccessLevel;
import dogbook.enums.EventInvitedStatus;
import dogbook.enums.GoingStatus;

import java.util.List;
import java.util.UUID;

public class UserEventDTO {

    private UUID id;
    private Integer eventId;
    private Integer userId;

    private GoingStatus goingStatus;

    private EventAccessLevel eventAccessLevel;

    private EventInvitedStatus eventInvitedStatus;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public GoingStatus getGoingStatus() {
        return goingStatus;
    }

    public void setGoingStatus(GoingStatus goingStatus) {
        this.goingStatus = goingStatus;
    }

    public EventAccessLevel getEventAccessLevel() {
        return eventAccessLevel;
    }

    public void setEventAccessLevel(EventAccessLevel eventAccessLevel) {
        this.eventAccessLevel = eventAccessLevel;
    }

    public EventInvitedStatus getEventInvitedStatus() {
        return eventInvitedStatus;
    }

    public void setEventInvitedStatus(EventInvitedStatus eventInvitedStatus) {
        this.eventInvitedStatus = eventInvitedStatus;
    }
}
