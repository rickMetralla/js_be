package com.store.service;

import com.store.domain.Promo;
import com.store.repository.PromoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromoService {

    @Autowired
    PromoRepository lotRepo;

    public Promo findById(int id){
        return lotRepo.getOne(id);
    }

    public Iterable<Promo> findAll(){
        return lotRepo.findAll();
    }

    public void create(Promo promo){
        lotRepo.save(promo);
    }

    public void update(Promo updatedLot){
//        Promo lot = lotRepo.getOne(updatedLot.getId());
//        lotRepo.deleteById(updatedLot.getId());
        lotRepo.save(updatedLot);
    }

    public void delete(int id){
        lotRepo.deleteById(id);
    }
}
