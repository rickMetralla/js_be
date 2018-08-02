package com.store.service;

import com.store.domain.PromoStatus;
import com.store.repository.PromoStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PromoStatusService {
    @Autowired
    PromoStatusRepository promoStatusRepo;

    public List<PromoStatus> getAllStatus(){
        return promoStatusRepo.findAll();
    }

    public void createPromoStatus(PromoStatus promoStatus){
        promoStatusRepo.save(promoStatus);
    }
}
