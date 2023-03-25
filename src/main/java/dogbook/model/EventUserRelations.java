package dogbook.model;


import dogbook.enums.EventAccessLevel;
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
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "eventId")
    private Event event;

    private EventAccessLevel eventaccessLevel;

    private GoingStatus goingStatus;

    public EventUserRelations() {
    }

    public EventUserRelations(User user, Event event, EventAccessLevel eventaccessLevel, GoingStatus goingStatus) {

        this.user = user;
        this.event = event;
        this.eventaccessLevel = eventaccessLevel;
        this.goingStatus = goingStatus;
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
}
