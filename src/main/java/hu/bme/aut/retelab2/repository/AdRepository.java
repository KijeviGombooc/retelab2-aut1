package hu.bme.aut.retelab2.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.secret.SecretGenerator;

@Repository
public class AdRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Ad save(Ad ad) {
        if(ad.getTags() != null) ad.setTags(ad.getTags().stream().distinct().toList());
        ad.setSecret(SecretGenerator.generate());
        return em.merge(ad);
    }

    @Transactional
    public Ad update(Ad ad) throws Exception {
        Ad storedAd = findById(ad.getId());
        if(!ad.getSecret().equals(storedAd.getSecret())) {
            throw new Exception("Secrets do not match");
        }
        if(ad.getTags() != null) ad.setTags(ad.getTags().stream().distinct().toList());
        if(ad.getPrice() != null) storedAd.setPrice(ad.getPrice());
        if(ad.getTitle() != null) storedAd.setTitle(ad.getTitle());
        return em.merge(storedAd);
    }

    public List<Ad> findAll() {
        return em.createQuery("SELECT a FROM Ad a", Ad.class).getResultList();
    }

    public List<Ad> findAllBetweenPrice(int min, int max) {
        return em.createQuery("SELECT a FROM Ad a WHERE a.price BETWEEN ?1 AND ?2", Ad.class)
            .setParameter(1, min)
            .setParameter(2, max)
        .getResultList();
    }

    public Ad findById(long id) {
        return em.find(Ad.class, id);
    }

    public List<Ad> findByKeyword(String keyword) {
        return em.createQuery("SELECT a FROM Ad a WHERE a.text LIKE ?1", Ad.class).setParameter(1, '%' + keyword + '%').getResultList();
    }

    @Transactional
    public void deleteById(long id) {
        Ad todo = findById(id);
        em.remove(todo);
    }

    public List<Ad> findAllWithTag(String tag) {
        return em.createQuery("SELECT a FROM Ad a JOIN a.tags t WHERE LOWER(t) = LOWER(?1)", Ad.class)
            .setParameter(1, tag)
        .getResultList();
    }
}
