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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*; //can be used with *
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
@WebAppConfiguration
public class CustomerControllerTest {

    @Autowired
    private MockMvc controllerMvc;

    @MockBean
    private CustomerService customerService;

//    @Autowired
//    private TestRestTemplate testRestTemplate;

    @Test
    public void getCustomerByIdTest() throws Exception {
        int dni = 123;
        Customer customer = new Customer("rick", 123, "calle", 54466, "rick@rick.com");
        given(customerService.findByDni(dni)).willReturn(customer);

        controllerMvc.perform(get("/customers/123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.dni", is(dni)))
                .andExpect(jsonPath("$.address", is("calle")))
                .andExpect(jsonPath("$.phone", is(54466)))
                .andExpect(jsonPath("$.email", is("rick@rick.com")))
                .andExpect(jsonPath("$.name", is("rick")));

        verify(customerService, times(1)).findByDni(dni);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void getCustomerByIdNotFoundEntityTest() throws Exception {
        int dniNotFound = 123;
        EntityNotFoundException expectedException = new EntityNotFoundException(String.format("Customer with dni %s not found.", dniNotFound));
        given(customerService.findByDni(dniNotFound)).willThrow(expectedException);

        controllerMvc.perform(get("/customers/123"))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0]", is("Entity not found. Customer with dni 123 not found.")));
    }

   @Test
   public void getCustomersTest() throws Exception{

       Customer customer = new Customer("rick", 123, "calle", 54466, "rick@rick.com");
       Customer customer1 = new Customer("jhon", 321, "av", 73215151, "jhon@jhon.com");

       given(customerService.getAll()).willReturn(new ArrayList<Customer>(){{
           add(customer);
           add(customer1);
       }});

       controllerMvc.perform(get("/customers"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[0].dni", is(123)))
               .andExpect(jsonPath("$[1].address", is("av")))
               .andExpect(jsonPath("$[0].phone", is(54466)))
               .andExpect(jsonPath("$[1].email", is("jhon@jhon.com")))
               .andExpect(jsonPath("$[0].name", is("rick")));

       verify(customerService, times(1)).getAll();
       verifyNoMoreInteractions(customerService);
   }

}