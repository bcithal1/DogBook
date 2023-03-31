package dogbook.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dogbook.model.Photo;
import dogbook.service.PhotoService;

@RestController
public class PhotoController {
    @Autowired
    PhotoService photoService;

    @GetMapping("/api/v1/photos/{id}")
    public ResponseEntity<byte[]> getPhotoById(@PathVariable Integer id){
        Optional<Photo> photo = photoService.getPhotoById(id);
        if(photo.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(photo.get().getType()))
            .body(photo.get().getData());
    }
}
