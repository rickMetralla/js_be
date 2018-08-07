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

    public List<PrizeDraw> getAllWinners(boolean winner){
        return prizeDrawRepository.findPrizeDrawByWinner(winner);
    }

    public List<PrizeDraw> getAllByPromoId(int promo){
        return prizeDrawRepository.findPrizeDrawByPromoId(promo);
    }

    public List<PrizeDraw> getAllByCustomerId(int dni){
        return prizeDrawRepository.findPrizeDrawByCustDni(dni);
    }

    public PrizeDraw getPrizeDrawByDniAndPromoId(int dni, int promoId){
        return prizeDrawRepository.findPrizeDrawByCustDniAndPromoId(dni, promoId);
    }

    public PrizeDraw getById(int id){
        return prizeDrawRepository.getOne(id);
    }

    public PrizeDraw create(PrizeDraw prizeDraw){
        return prizeDrawRepository.save(prizeDraw);
    }

    public PrizeDraw update(PrizeDraw prizeDraw){
        return prizeDrawRepository.save(prizeDraw);
    }

    public void deletePrizeDraw(int id){
        prizeDrawRepository.deleteById(id);
    }
}
