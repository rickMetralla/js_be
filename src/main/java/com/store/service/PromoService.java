package com.store.service;

import com.store.domain.Promo;
import com.store.repository.PromoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.util.List;

@Service
public class PromoService {

    @Autowired
    PromoRepository promoRepository;

    public Promo findById(int id) throws EntityNotFoundException {
        return promoRepository.getOne(id);
    }

    public List<Promo> getAllPromoByStatus(int status){
        return promoRepository.findPromoByStatus(status);
    }

    public List<Promo> getPromoBySeason(String season){
        return promoRepository.findPromoBySeasonIsContaining(season);
    }

    public Iterable<Promo> findAll(){
        return promoRepository.findAll();
    }

    public Promo create(Promo promo){
        return promoRepository.save(promo);
    }

    public Promo update(Promo updatedLot) throws PersistenceException{
        Promo promo = promoRepository.getOne(updatedLot.getId());
        if (promo.getStatus() != updatedLot.getStatus()){
            throw new PersistenceException("Not possible to change status");
        }
        return promoRepository.save(updatedLot);
    }

    public void delete(int id){
        promoRepository.deleteById(id);
    }
}
