package com.store.service;

import com.store.domain.Transaction;
import com.store.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository repo;

    public Transaction createBuy(Transaction transaction) {
        return repo.save(transaction);
    }

    public List<Transaction> getAllTransaction(){
        return repo.findAll();
    }

    public List<Transaction> getAllTransactionByCustDni(int dni){
        return repo.findTransactionByCustDni(dni);
    }
}
