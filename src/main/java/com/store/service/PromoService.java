package com.store.service;

import com.store.domain.Promo;
import com.store.repository.PromoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

@Service
public class PromoService {

    @Autowired
    PromoRepository lotRepo;

    public Promo findById(int id) throws EntityNotFoundException {
        return lotRepo.getOne(id);
    }

    public Iterable<Promo> findAll(){
        return lotRepo.findAll();
    }

    public void create(Promo promo){
        lotRepo.save(promo);
    }

    public void update(Promo updatedLot) throws PersistenceException{
        Promo promo = lotRepo.getOne(updatedLot.getId());
        if (promo.getStatus() != updatedLot.getStatus()){
            throw new PersistenceException("Not possible to change status");
        }
        lotRepo.save(updatedLot);
    }

    public void delete(int id){
        lotRepo.deleteById(id);
    }
}
