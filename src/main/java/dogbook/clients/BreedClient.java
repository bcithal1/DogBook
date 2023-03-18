package dogbook.clients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import dogbook.model.breedResponse.BreedResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

@Component
public class BreedClient {

    private final RestTemplate restTemplate;

    @Value("${api.breeds.url}")
    private String url;

    @Value("${api.breeds.key}")
    private String key;

    public BreedClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BreedResponse getBreedList(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("x-api-key", key);
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
//                .queryParam("limit", 10);


        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, BreedResponse.class).getBody();
    }
}
