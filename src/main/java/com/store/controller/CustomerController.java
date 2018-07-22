package com.store.controller;

import com.store.domain.Customer;
import com.store.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
public class CustomerController {
    @Autowired
    CustomerService service;

    //@CrossOrigin(origins = "http://localhost:9000")
    @RequestMapping(value = "/customer/{dni}", method = RequestMethod.GET)
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer dni){
        Customer one = service.findByDni(dni);
        if (one == null){
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<Customer>(service.findByDni(dni), HttpStatus.OK);
        }
    }

    //@CrossOrigin(origins = "http://localhost:9000")
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Customer>> getCustomers(){
        return new ResponseEntity<Iterable<Customer>>(service.getAll(), HttpStatus.OK);
    }

    //@CrossOrigin(origins = "http://localhost:9000")
    @RequestMapping(value = "/customer/", method = RequestMethod.POST)
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer){
        service.create(customer);
        return new ResponseEntity<String>("Customer created successfully", HttpStatus.CREATED);
    }

    //@CrossOrigin(origins = "http://localhost:9000")
    @RequestMapping(value = "/customer/", method = RequestMethod.PUT)
    public ResponseEntity<String> updateCustomer(@RequestBody Customer updatedCustomer){
        if(service.findByDni(updatedCustomer.getDni()) == null){
            return new ResponseEntity<String>("Customer not found", HttpStatus.NOT_FOUND);
        }
        else{
            service.update(updatedCustomer);
            return new ResponseEntity<String>("Customer updated successfully", HttpStatus.OK);
        }
    }

    //@CrossOrigin(origins = "http://localhost:9000")
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
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
