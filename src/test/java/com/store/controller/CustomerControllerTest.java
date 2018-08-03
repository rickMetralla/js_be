package com.store.controller;

import com.store.domain.Customer;
import com.store.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc controllerMvc;

    @MockBean
    private CustomerService service;

    @Test
    public void getCustomerById() throws Exception {
        int dni = 123;
        Customer customer = new Customer("rick", 123, "calle", 54466, "rick@rick.com");
        given(service.findByDni(dni)).willReturn(customer);
        controllerMvc.perform(get("/customers/123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(jsonPath("$", hasSize(1)));
//                .andExpect(jsonPath("$.name", is(customer.getName())));
    }

    @Test
    public void getCustomers() throws Exception {
    }

    @Test
    public void createCustomer() throws Exception {
    }

    @Test
    public void updateCustomer() throws Exception {
    }

    @Test
    public void deleteCustomer() throws Exception {
    }

}