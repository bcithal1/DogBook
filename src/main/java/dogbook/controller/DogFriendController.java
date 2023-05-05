package dogbook.controller;

import dogbook.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DogFriendController {

    @Autowired
    DogService dogService;

}
