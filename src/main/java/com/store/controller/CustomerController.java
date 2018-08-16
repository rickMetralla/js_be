package com.store.controller;

import com.store.LoggerWrapper;
import com.store.domain.Customer;
import com.store.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
public class CustomerController {

    @Autowired
    CustomerService service;
    Logger LOGGER = LoggerWrapper.getInstance().logger;

    @RequestMapping(value = "/customers/{dni}", method = RequestMethod.GET)
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer dni) throws EntityNotFoundException {
        Customer one;
        try{
            one = service.findByDni(dni);
        }catch (EntityNotFoundException error){
            throw error;
        }
        return new ResponseEntity<Customer>(one, HttpStatus.OK);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Customer>> getCustomers(){
        return new ResponseEntity<Iterable<Customer>>(service.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
//        validateCustomerFields(customer);
        if(customer.validate()){
            return new ResponseEntity<Customer>(service.create(customer), HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<Customer>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @RequestMapping(value = "/customers", method = RequestMethod.PUT)
    public ResponseEntity<String> updateCustomer(@RequestBody Customer updatedCustomer){
//        validateCustomerFields(customer);
        if(service.findByDni(updatedCustomer.getDni()) == null){
            return new ResponseEntity<String>("Customer not found", HttpStatus.NOT_FOUND);
        }
        else{
            service.update(updatedCustomer);
            return new ResponseEntity<String>("Customer updated successfully", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCustomer(@PathVariable Integer id){
        if(service.findByDni(id) == null){
            return new ResponseEntity<String>("Customer not found", HttpStatus.NOT_FOUND);
        }
        else{
            service.delete(id);
            return new ResponseEntity<String>("Customer deleted successfully", HttpStatus.OK);
        }
    }
}
