package dogbook.clients;
import dogbook.model.breedResponse.BreedEntry;
import dogbook.model.breedResponse.BreedInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Component
public class BreedClient {

    private final RestTemplate restTemplate;

    @Value("${api.breeds.url}")
    private String url;

    @Value("${api.breeds.key}")
    private String key;

    private HttpEntity<String> getRequestHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("x-api-key", key);
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        return new HttpEntity<>(httpHeaders);
    }

    public BreedClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<BreedEntry> getBreedList(){

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url + "/v1/breeds");

        BreedEntry[] breedResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, getRequestHeaders(), BreedEntry[].class).getBody();
        return Arrays.asList(breedResponse);
    }

    public BreedInfo getBreedById(Integer id){

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url + "/v1/breeds/" + id);

        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, getRequestHeaders(), BreedInfo.class).getBody();
    }
}
