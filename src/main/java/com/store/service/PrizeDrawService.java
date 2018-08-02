package com.store.service;

import com.store.domain.PrizeDraw;
import com.store.repository.PrizeDrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrizeDrawService {

    @Autowired
    PrizeDrawRepository prizeDrawRepository;

    public List<PrizeDraw> getAllPrizes(){
        return prizeDrawRepository.findAll();
    }

//    public List<Integer> findDniByPromoId(int promoId){
//        return prizeDrawRepository.findDniByPromoId(promoId);
//    }

    public PrizeDraw getById(int id){
        return prizeDrawRepository.getOne(id);
    }

    public void create(PrizeDraw prizeDraw){
        prizeDrawRepository.save(prizeDraw);
    }

    public void update(PrizeDraw prizeDraw){
        prizeDrawRepository.save(prizeDraw);
    }
}
