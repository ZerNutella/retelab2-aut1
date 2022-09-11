package hu.bme.aut.retelab2.controller;

import hu.bme.aut.retelab2.SecretGenerator;
import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.domain.Note;
import hu.bme.aut.retelab2.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ads")
public class AdController {
    @Autowired
    private AdRepository adRepository;

    @GetMapping
    public List<Ad> getAll(@RequestParam(required = false, defaultValue = "0") int minprice, @RequestParam(required = false, defaultValue = "10000000") int maxprice) {
        List<Ad> ads = adRepository.findByPrice(maxprice, minprice);
        for(Ad ad : ads){
            ad.setCode(null);
        }
        return ads;
    }

    @PostMapping
    public Ad create(@RequestBody Ad ad) {
        ad.setId(null);
        ad.setCreatedAt(null);
        ad.setCode(SecretGenerator.generate());
        return adRepository.save(ad);
    }

    @PutMapping
    public ResponseEntity<Ad> update(@RequestBody Ad ad){
        Ad advert = adRepository.modify(ad);
        if(advert == null)
            return ResponseEntity.status(403).build();
        return ResponseEntity.ok().build();
    }

    @GetMapping("{tag}")
    public ResponseEntity<List<Ad>> getByTag(@PathVariable String tag) {
        List<Ad> ads = adRepository.findByTag(tag);
        if (ads == null)
            return ResponseEntity.notFound().build();
        for(Ad ad : ads){
            ad.setCode(null);
        }
        return ResponseEntity.ok(ads);
    }
}
