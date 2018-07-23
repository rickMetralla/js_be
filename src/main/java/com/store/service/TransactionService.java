package com.store.service;

import com.store.domain.Transaction;
import com.store.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository repo;

    public void createBuy(Transaction transaction) {
        repo.save(transaction);
    }

    public Iterable<Transaction> getAll(){
        return repo.findAll();
    }
}
