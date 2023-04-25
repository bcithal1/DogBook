package dogbook.controller;

import dogbook.model.Event;
import dogbook.model.EventUserRelations;
import dogbook.model.User;
import dogbook.service.EventService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

@RestController
public class EventController {

    @Autowired
    EventService eventService;


    @PostMapping("/api/v1/event")
    public ResponseEntity<Event> createEvent(@RequestBody Event event){
        Event response = eventService.createEvent(event);
        return response==null? new ResponseEntity<>(HttpStatus.BAD_REQUEST): ResponseEntity.ok(response);
    }

    @PostMapping("/api/v1/event/update/{eventId}")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event, @PathVariable Integer eventId){
        Event response = eventService.updateEvent(event, eventId);
        return response==null? new ResponseEntity<>(HttpStatus.BAD_REQUEST): ResponseEntity.ok(response);
    }

    @PutMapping("/api/v1/event/invite/{eventId}/{userId}")
    public ResponseEntity<User> inviteUserToEvent(@PathVariable Integer eventId, @PathVariable Integer userId){
        User response = eventService.inviteUserToEvent(eventId,userId);
        return response==null? new ResponseEntity<>(HttpStatus.BAD_REQUEST): ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/event")
    public ResponseEntity<List<Event>> getEventList(){
        List<Event> response  = eventService.getEventList();
        return response==null? new ResponseEntity<>(HttpStatus.BAD_REQUEST): ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/event/{id}")
    public ResponseEntity<Event> getEventListById(@PathVariable Integer id){
        Event response  = eventService.getEventListById(id);
        return response==null? new ResponseEntity<>(HttpStatus.BAD_REQUEST): ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/event/currentUser")
    public ResponseEntity<List<Event>> getEventListOfCurrentUser(){
        List<Event> response  = eventService.getEventListOfCurrentUser();
        return response==null? new ResponseEntity<>(HttpStatus.BAD_REQUEST): ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/event/invitedEvent")
    public ResponseEntity<List<Event>> getInvitedEventList(){
        List<Event> response  = eventService.getInvitedEventList();
        return response==null? new ResponseEntity<>(HttpStatus.BAD_REQUEST): ResponseEntity.ok(response);
    }

    @PutMapping("/api/v1/event/invitedEvent/{eventId}")
    public ResponseEntity<Event> acceptEventInvite(@PathVariable Integer eventId){
        Event response  = eventService.acceptEventInvite(eventId);
        return response==null? new ResponseEntity<>(HttpStatus.BAD_REQUEST): ResponseEntity.ok(response);
    }

    @PutMapping("/api/v1/event/applyToEvent/{eventId}")
    public ResponseEntity<Void> userApplyToEvent(@PathVariable Integer eventId){
        String response  = eventService.userApplyToEvent(eventId);
        return response==null? new ResponseEntity<>(HttpStatus.BAD_REQUEST): ResponseEntity.ok().build();
    }

    @PutMapping("/api/v1/event/processApplication/{eventId}/{userId}")
    public ResponseEntity<Void> hostProcessApplication(@PathVariable Integer eventId, @PathVariable Integer userId){
        String response  = eventService.hostProcessApplication(eventId, userId);
        return response==null? new ResponseEntity<>(HttpStatus.BAD_REQUEST): ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/event/eventUserRelation/{eventId}")
    public ResponseEntity<List<EventUserRelations>> getAllEventUserRelationsForEvent(@PathVariable Integer eventId){
        List<EventUserRelations> response = eventService.getAllEventUserRelationsForEvent(eventId);
        return response==null? new ResponseEntity<>(HttpStatus.BAD_REQUEST): ResponseEntity.ok(response);
    }



}
