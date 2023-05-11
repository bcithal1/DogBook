package dogbook.development;

import dogbook.model.Photo;
import dogbook.repository.PhotoRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class PhotoInitializer {

    @Bean
    public CommandLineRunner importSamplePhotos(PhotoRepo photoRepo) {
        return args -> {
            try {
                importPhoto("samplephotos/1golden-retriever.jpg", photoRepo);
                importPhoto("samplephotos/2lab-retriever.jpg", photoRepo);
                importPhoto("samplephotos/3german-shepard.jpg", photoRepo);
                importPhoto("samplephotos/4poodle.jpg", photoRepo);
                importPhoto("samplephotos/5beagle.jpg", photoRepo);
                importPhoto("samplephotos/6bulldog.jpg", photoRepo);
                importPhoto("samplephotos/7ladybird.jpg", photoRepo);
                importPhoto("samplephotos/8HankHill.PNG", photoRepo);
                importPhoto("samplephotos/9Bill.PNG", photoRepo);
                importPhoto("samplephotos/10Rusty.PNG", photoRepo);
                importPhoto("samplephotos/11PeggyHIll.PNG", photoRepo);
                importPhoto("samplephotos/12Nancy.png", photoRepo);
                importPhoto("samplephotos/13Rika.jpeg", photoRepo);
                importPhoto("samplephotos/14Brian.jpg", photoRepo);
                importPhoto("samplephotos/15default.webp", photoRepo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    private void importPhoto(String filePath, PhotoRepo photoRepo) throws IOException {
        Path path = Paths.get(new ClassPathResource(filePath).getURI());
        String fileName = path.getFileName().toString();
        String contentType = Files.probeContentType(path);
        byte[] bytes = Files.readAllBytes(path);
        MultipartFile file = new MockMultipartFile(fileName, fileName, contentType, bytes);

        Photo photo = new Photo(file);
        photoRepo.save(photo);
    }
}