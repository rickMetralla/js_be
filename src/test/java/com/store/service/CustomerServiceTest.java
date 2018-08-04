package com.store.service;

import com.store.domain.Customer;
import com.store.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Test
    public void findByDniTest() throws Exception {
        int expectedDni = 123;
        when(customerRepository.findByDni(expectedDni))
                .thenReturn(new Customer("Ferchis", 123, "callo", 76907520, "Ferchis@bazzoka.com"));
        Customer actual = customerService.findByDni(123);
        assertEquals(expectedDni, actual.getDni());
    }

    @Test
    public void getAllTest() throws Exception {
        Customer expectedCustomer = new Customer("rick", 6432037, "calle", 76907520, "rick@bazzoka.com");

        when(customerRepository.findAll())
                .thenReturn(new ArrayList<Customer>(){{
                    add(new Customer("Rick", 6432037, "calle", 76907520, "rick@bazzoka.com"));
                }});
        Iterable<Customer> result = customerService.getAll();
        for (Customer customer :
                result) {
            assertEquals(expectedCustomer.getDni(), customer.getDni());
        }
    }

}