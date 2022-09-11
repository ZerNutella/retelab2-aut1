package hu.bme.aut.retelab2.repository;

import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.domain.Note;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AdRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Ad save(Ad advert) {return em.merge(advert); }

    @Transactional
    public Ad modify(Ad advert){
        Ad ad = findById(advert.getId());
        if(ad == null)
            return null;
        if(!ad.getCode().equals(advert.getCode()))
            return null;
        deleteById(ad.getId());
        return save(advert);
    }

    public Ad findById(long id) {
        return em.find(Ad.class, id);
    }

    public List<Ad> findByTag(String tag){
        return em.createQuery("SELECT a FROM Ad a join a.tags t WHERE t = ?1 ", Ad.class).setParameter(1,tag).getResultList();
    }

    @Transactional
    public void deleteById(long id) {
        Ad advert = findById(id);
        em.remove(advert);
    }

    public List<Ad> findByPrice(int max, int min) {
        return em.createQuery("SELECT n FROM Ad n WHERE n.price BETWEEN ?1 And ?2", Ad.class).setParameter(1, min).setParameter(2,max).getResultList();
    }

    @Transactional
    @Scheduled(fixedDelay = 6000)
    public void expireAds(){
        List<Ad> expiredAds = em.createQuery("SELECT a From Ad a where a.expireAt < ?1", Ad.class).setParameter(1, LocalDateTime.now()).getResultList();
        for (Ad expiredAd : expiredAds) {
            em.remove(expiredAd);
        }
    }
}
