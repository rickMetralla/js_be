package com.store.repository;

import com.store.domain.Customer;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Customer findByDni(int dni);
    Customer findByNameContaining(String name);
    Customer findByPhone(int phone);
}
