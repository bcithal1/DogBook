package dogbook.controller;

import dogbook.model.UserEventDTO;
import dogbook.service.EventUserDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class UserEventDTOController {


    @Autowired
    EventUserDTOService eventUserDTOService;

    @GetMapping("/api/v1/eventUserMapper/{eventId}/{userId}")
    public ResponseEntity<UserEventDTO> getEventUserDTO(@PathVariable(name = "eventId") Integer eventId, @PathVariable(name = "userId") Integer userId){
        UserEventDTO response  = eventUserDTOService.getEventUserDTOResponse(eventId,userId);
        return response==null? new ResponseEntity<>(HttpStatus.BAD_REQUEST): ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/eventUserMapper/{eventId}")
    public ResponseEntity<List<UserEventDTO>> getAllEventUserDTO(@PathVariable(name = "eventId") Integer eventId){
        List<UserEventDTO> response  = eventUserDTOService.getAllEventUserDTO(eventId);
        return response==null? new ResponseEntity<>(HttpStatus.BAD_REQUEST): ResponseEntity.ok(response);
    }

}
