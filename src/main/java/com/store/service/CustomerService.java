package com.store.service;

import com.store.domain.Customer;
import com.store.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository repo;

    public Customer findByName(String name){
        return repo.findByNameContaining(name);
    }

    public Customer findByPhone(int phone){
        return repo.findByPhone(phone);
    }

    public Customer findByDni (int dni) throws EntityNotFoundException {
        Customer customer = repo.findByDni(dni);
        if(customer != null){
            return customer;
        }else{
            throw new EntityNotFoundException(String.format("Customer with dni %s not found.", dni));
        }
    }

    public List<Customer> getAll(){
        return repo.findAll();
    }

    public Customer create(Customer customer){
        return repo.save(customer);
    }

    public Customer update(Customer updatedCustomer){
//        Customer custom = repo.findByDni(updatedCustomer.getDni());
//        repo.deleteById(updatedCustomer.getDni());
        return repo.save(updatedCustomer);
    }

    public void delete(int dni){
        repo.deleteById(dni);
    }
}
