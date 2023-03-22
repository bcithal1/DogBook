package dogbook.controller;

import dogbook.model.Event;
import dogbook.service.EventService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
public class EventController {

    @Autowired
    EventService eventService;


    @PostMapping("/api/v1/event/{userId}")
    public ResponseEntity<Event> createEvent(@PathVariable Integer userId, @RequestBody Event event){
        Event response = eventService.createEvent(userId, event);
        return response==null? new ResponseEntity<>(HttpStatus.BAD_REQUEST): ResponseEntity.ok(response);
    }
}
