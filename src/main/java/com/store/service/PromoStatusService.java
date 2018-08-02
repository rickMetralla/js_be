package com.store.service;

import com.store.domain.PromoStatus;
import com.store.repository.PromoStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoStatusService {
    @Autowired
    PromoStatusRepository promoStatusRepo;

    public List<PromoStatus> getAllStatus(){
        return promoStatusRepo.findAll();
    }

    public PromoStatus getOneById(int id){
        return promoStatusRepo.getOne(id);
    }

    public void createPromoStatus(PromoStatus promoStatus){
        promoStatusRepo.save(promoStatus);
    }

    public void deletePromoStatus(int id){
        promoStatusRepo.deleteById(id);
    }
}
