package hu.bme.aut.retelab2.scheduling;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.repository.AdRepository;

@Component
public class ExpirationScheduler {
    
    @Autowired
    private AdRepository adRepository;

    @Scheduled(fixedDelay= 6000)
    public void cleanExpired() {
        List<Ad> ads = adRepository.findAll();
        ads.forEach(ad -> {
            if(ad.getExpirationDate() != null && ad.getExpirationDate().isBefore(LocalDateTime.now())) {
                adRepository.deleteById(ad.getId());
            }
        });
    }
}
