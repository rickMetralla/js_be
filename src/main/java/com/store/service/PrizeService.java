package com.store.service;

import com.store.domain.Prize;
import com.store.repository.PrizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrizeService {

    @Autowired
    PrizeRepository prizeRepository;

    public List<Prize> getAllPrizes(){
        return prizeRepository.findAll();
    }

    public Prize getOnePrizeById(int id){
        return prizeRepository.getOne(id);
    }

    public Prize create(Prize prize){
        return prizeRepository.save(prize);
    }

    public Prize update(Prize prize){
        return prizeRepository.save(prize);
    }

    public void deleteById(int id){
        prizeRepository.deleteById(id);
    }
}
