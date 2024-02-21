package vincenzo.caio.dws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import vincenzo.caio.dws.model.Band;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BandService {
    @Autowired
    Environment env;

    @Cacheable("bands")
    public List<Band> getAllBands() {
        System.out.println("Fetching bands....");
        String uri = env.getProperty("data.url");
        RestTemplate restTemplate = new RestTemplate();
        Band[] result = restTemplate.getForObject(uri, Band[].class);
        System.out.println("Finished fetching");
        return Arrays.asList(result);
    }

    public Optional<Band> getBandById(List<Band> bands, String id) {
        return bands.stream().filter(b -> b.id().equals(id)).reduce((a, b) -> {
            throw new IllegalStateException("Found duplicated elements");
        });
    }

    public List<Band> findByName(List<Band> bands, String name) {
        return bands.stream().filter(b -> b.name().contains(name)).toList();
    }

    public List<Band> sortBandsByName(List<Band> bands) {
        return bands.stream().sorted((Comparator.comparing(Band::name))).toList();
    }

    public List<Band> sortBandsByPlays(List<Band> bands) {
        return bands.stream().sorted((Comparator.comparing(Band::numPlays)).reversed()).toList();
    }
}
