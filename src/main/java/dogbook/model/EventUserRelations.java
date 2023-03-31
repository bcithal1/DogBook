package dogbook.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import dogbook.enums.EventAccessLevel;
import dogbook.enums.EventInvitedStatus;
import dogbook.enums.GoingStatus;

import javax.persistence.*;

@Entity
public class EventUserRelations {

    @Id
    @Column(name = "guestList")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "eventId")
    @JsonIgnore
    private Event event;

    @Enumerated(EnumType.STRING)
    private EventAccessLevel eventaccessLevel;
    @Enumerated(EnumType.STRING)
    private GoingStatus goingStatus;
    @Enumerated(EnumType.STRING)
    private EventInvitedStatus eventInvitedStatus;


    public EventUserRelations() {
    }

    public EventUserRelations(User user, Event event, EventAccessLevel eventaccessLevel, GoingStatus goingStatus, EventInvitedStatus eventInvitedStatus) {

        this.user = user;
        this.event = event;
        this.eventaccessLevel = eventaccessLevel;
        this.goingStatus = goingStatus;
        this.eventInvitedStatus = eventInvitedStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public EventAccessLevel getEventaccessLevel() {
        return eventaccessLevel;
    }

    public void setEventaccessLevel(EventAccessLevel eventaccessLevel) {
        this.eventaccessLevel = eventaccessLevel;
    }

    public GoingStatus getGoingStatus() {
        return goingStatus;
    }

    public void setGoingStatus(GoingStatus goingStatus) {
        this.goingStatus = goingStatus;
    }

    public EventInvitedStatus getEventInvitedStatus() {
        return eventInvitedStatus;
    }

    public void setEventInvitedStatus(EventInvitedStatus eventInvitedStatus) {
        this.eventInvitedStatus = eventInvitedStatus;
    }
}
