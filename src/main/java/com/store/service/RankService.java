package com.store.service;

import com.store.domain.Rank;
import com.store.repository.RankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankService {

    @Autowired
    RankRepository rankRepository;

    public List<Rank> getAllRank(){
        return rankRepository.findAll();
    }

    public Rank getOnePrizeById(int id){
        return rankRepository.getOne(id);
    }

    public Rank create(Rank rank){
        return rankRepository.save(rank);
    }

    public Rank update(Rank rank){
        return rankRepository.save(rank);
    }

    public void deleteById(int id){
        rankRepository.deleteById(id);
    }
}
