package hu.bme.aut.retelab2.controller;

import hu.bme.aut.retelab2.domain.Ad;
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
    public List<Ad> getAll(@RequestParam(required = false, defaultValue = "0") Integer min, @RequestParam(required = false, defaultValue = "10000000") Integer max) {
        return adRepository.findAllBetweenPrice(min, max);
    }

    @GetMapping("{id}")
    public ResponseEntity<Ad> getById(@PathVariable long id) {
        Ad ad = adRepository.findById(id);
        if (ad == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(ad);
    }

    @PostMapping
    public Ad create(@RequestBody Ad ad) {
        ad.setId(null);
        return adRepository.save(ad);
    }

    @PutMapping
    public ResponseEntity<Ad> update(@RequestBody Ad ad) {
        Ad n = adRepository.findById(ad.getId());
        if (n == null)
            return ResponseEntity.notFound().build();
        n = adRepository.save(ad);
        return ResponseEntity.ok(n);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Ad ad = adRepository.findById(id);
        if (ad == null)
            return ResponseEntity.notFound().build();
        else {
            adRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
    }
}