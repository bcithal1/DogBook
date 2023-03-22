package dogbook.model;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Event {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer event_id;
    @Column
    private Integer host_id;
    @Column
    private String event_title;
    @Column
    private String event_location;
    @Column
    private String event_description;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column
    private LocalDate date;

    @ManyToMany(fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "User_Event_table",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")

    )
    @JsonIgnoreProperties("eventSet")
    private Set<User> userSetEvent = new HashSet<>();

    public Integer getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Integer event_id) {
        this.event_id = event_id;
    }

    public Integer getHost_id() {
        return host_id;
    }

    public void setHost_id(Integer host_id) {
        this.host_id = host_id;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<User> getUserSet_Event() {
        return userSetEvent;
    }

    public void setUserSet_Event(Set<User> userSet_Event) {
        this.userSetEvent = userSet_Event;
    }
}
