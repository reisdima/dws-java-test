package vincenzo.caio.dws.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import vincenzo.caio.dws.model.Band;
import vincenzo.caio.dws.service.BandService;

import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bands")
public class BandController {

    @Autowired
    BandService bandService;

    @GetMapping(value = "getAll")
    public List<Band> getBands() {
        return bandService.getAllBands();
    }

    @GetMapping(value ="/{id}")
    public ResponseEntity<Object> getBandById(@PathVariable(value = "id") String id) {
        List<Band> bands = getBands();
        Optional<Band> band = bandService.getBandById(bands, id);
        if(band.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bands with id " + id + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(band);
    }

    @GetMapping(value ="/findByName/{name}")
    public ResponseEntity<Object> findByName(@PathVariable(value = "name") String name) {
        List<Band> bands = getBands();
        List<Band> bandsWithName = bandService.findByName(bands,name);
        if(bandsWithName.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No band contains " + name + " in its name");
        }
        return ResponseEntity.status(HttpStatus.OK).body(bandsWithName);
    }

    @GetMapping(value ="/sortByName")
    public ResponseEntity<Object> getBandsSortedByName() {
        List<Band> bands = getBands();
        List<Band> bandsSortedByName = bandService.sortBandsByName(bands);
        return ResponseEntity.status(HttpStatus.OK).body(bandsSortedByName);

    }
    @GetMapping(value ="/sortByPopularity")
    public ResponseEntity<Object> getBandsSortedByPopularity() {
        List<Band> bands = getBands();
        List<Band> bandsSortedByPlays = bandService.sortBandsByPlays(bands);
        return ResponseEntity.status(HttpStatus.OK).body(bandsSortedByPlays);
    }


}
