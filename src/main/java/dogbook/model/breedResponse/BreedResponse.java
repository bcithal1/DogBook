package dogbook.model.breedResponse;

import java.util.ArrayList;
import java.util.List;

public class BreedResponse {

    private List<BreedInfo> results = new ArrayList<>();

    public List<BreedInfo> getResults() {
        return results;
    }

    public void setResults(List<BreedInfo> results) {
        this.results = results;
    }
}
