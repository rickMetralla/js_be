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

    public PromoStatus getPromoStatusByStatus(String status){
        return promoStatusRepo.findPromoStatusByStatus(status);
    }

    public PromoStatus getOneById(int id){
        return promoStatusRepo.getOne(id);
    }

    public PromoStatus createPromoStatus(PromoStatus promoStatus){
        return promoStatusRepo.save(promoStatus);
    }

    public PromoStatus updatePromoStatus(PromoStatus updatedPromoStatus){
        return promoStatusRepo.save(updatedPromoStatus);
    }

    public void deletePromoStatus(int id){
        promoStatusRepo.deleteById(id);
    }
}
