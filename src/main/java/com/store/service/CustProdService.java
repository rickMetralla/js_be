package com.store.service;

import com.store.domain.CustProd;
import com.store.repository.CustProdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustProdService {

    @Autowired
    CustProdRepository repo;

    public Iterable<CustProd> getBuyers() {
        return repo.find();
    }

    public Iterable<CustProd> getAll(){
        return repo.findAll();
    }
}
