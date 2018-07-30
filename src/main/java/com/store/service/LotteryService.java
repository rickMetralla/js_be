package com.store.service;

import com.store.domain.Lottery;
import com.store.repository.LotteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotteryService {

    @Autowired
    LotteryRepository lotRepo;

    public Lottery findById(int id){
        return lotRepo.getOne(id);
    }

    public Iterable<Lottery> findAll(){
        return lotRepo.findAll();
    }

    public void create(Lottery lottery){
        lotRepo.save(lottery);
    }

    public void update(Lottery updatedLot){
//        Lottery lot = lotRepo.getOne(updatedLot.getId());
//        lotRepo.deleteById(updatedLot.getId());
        lotRepo.save(updatedLot);
    }

    public void delete(int id){
        lotRepo.deleteById(id);
    }
}
