package com.store.service;

import com.store.domain.Winners;
import com.store.repository.WinnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WinnerService {

    @Autowired
    WinnerRepository winRepo;

    public Winners findById (int id) {
        return winRepo.getOne(id);
    }

    public Iterable<Winners> findAll(){
        return winRepo.findAll();
    }

    public void create(Winners win){
        winRepo.save(win);
    }
}
