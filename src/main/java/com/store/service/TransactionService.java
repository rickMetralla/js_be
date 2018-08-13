package com.store.service;

import com.store.domain.Order;
import com.store.domain.Product;
import com.store.domain.Transaction;
import com.store.repository.ProductRepository;
import com.store.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository repo;

    @Autowired
    ProductRepository prepo;

    public Transaction createBuy(Transaction transaction) {
        Order order = transaction.getOrder();
        Product product = prepo.getOne(order.getProdId());
        int stock = product.getStock() - order.getAmount();
        product.setStock(stock);
        prepo.save(product);
        return repo.save(transaction);
    }

    public List<Transaction> getAllTransaction(){
        return repo.findAll();
    }

    public List<Transaction> getAllTransactionByCustDni(int dni){
        return repo.findTransactionByCustDni(dni);
    }
}
