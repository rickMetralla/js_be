package com.store.service;

import com.store.domain.Product;
import com.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;

    public Product findById(int id) {
        return repository.getOne(id);
    }

    public Iterable<Product> getAll() {
        return repository.findAll();
    }

    public void create (Product product) {
        repository.save(product);
    }

    public void update(Product updateProd){
        Product prod = repository.getOne(updateProd.getId());
        repository.deleteById(updateProd.getId());
        repository.save(updateProd);
    }
    public void delete(int id) {
        repository.deleteById(id);
    }

}

