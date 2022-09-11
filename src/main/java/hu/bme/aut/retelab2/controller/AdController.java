package hu.bme.aut.retelab2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.repository.AdRepository;

@RestController
@RequestMapping("/api/ads")
public class AdController {

    @Autowired
    private AdRepository adRepository;

    @GetMapping
    public List<Ad> getAll(@RequestParam(required = false, defaultValue = "0") Integer min, @RequestParam(required = false, defaultValue = "10000000") Integer max) {
        List<Ad> ads = adRepository.findAllBetweenPrice(min, max);
        ads.forEach(ad -> ad.setSecret(null));
        return ads;
    }

    // @GetMapping("{id}")
    // public ResponseEntity<Ad> getById(@PathVariable long id) {
    //     Ad ad = adRepository.findById(id);
    //     if (ad == null)
    //         return ResponseEntity.notFound().build();
    //     else
    //         return ResponseEntity.ok(ad);
    // }

    @PostMapping
    public Ad create(@RequestBody Ad ad) {
        ad.setId(null);
        return adRepository.save(ad);
    }

    @PutMapping
    public ResponseEntity<Ad> update(@RequestBody Ad ad) {
        Ad updatedAd = null;
        try {
            updatedAd = adRepository.update(ad);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(updatedAd);
    }

    // @DeleteMapping("{id}")
    // public ResponseEntity<?> delete(@PathVariable long id) {
    //     Ad ad = adRepository.findById(id);
    //     if (ad == null)
    //         return ResponseEntity.notFound().build();
    //     else {
    //         adRepository.deleteById(id);
    //         return ResponseEntity.ok().build();
    //     }
    // }
}