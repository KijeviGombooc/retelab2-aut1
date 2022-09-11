package hu.bme.aut.retelab2.repository;

import hu.bme.aut.retelab2.domain.Ad;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AdRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Ad save(Ad feedback) {
        return em.merge(feedback);
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
}
