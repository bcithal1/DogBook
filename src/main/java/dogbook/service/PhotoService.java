package dogbook.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dogbook.model.Photo;
import dogbook.repository.PhotoRepo;

@Service
public class PhotoService {
    @Autowired
    private PhotoRepo photoRepo;

    public Optional<Photo> getPhotoById(Integer id) {
        return photoRepo.findById(id);
    }

    public Photo save(Photo photo){
        return photoRepo.save(photo);
    }
}
