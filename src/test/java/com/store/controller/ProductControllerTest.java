package com.store.controller;

import com.store.domain.Product;
import com.store.service.ProductService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
@WebAppConfiguration
public class ProductControllerTest {

    @Autowired
    private MockMvc controllerMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void getProductById() throws Exception {
        int productId = 1;
        given(productService.findById(productId)).willReturn(new Product(1,"product", "L", 15 ));

        controllerMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("product")))
                .andExpect(jsonPath("$.model", is("L")))
                .andExpect(jsonPath("$.stock", is(15)));

        verify(productService, times(1)).findById(productId);
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void getProducts() throws Exception {
        given(productService.getAll()).willReturn(new ArrayList<Product>(){{
           add(new Product(1,"product", "L", 15 ));
           add(new Product(2,"other product", "XL", 6 ));
           add(new Product(3,"another product", "S", 10 ));
           add(new Product(4,"another product else", "M", 2 ));
        }});

        controllerMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("product")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("other product")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("another product")))
                .andExpect(jsonPath("$[3].id", is(4)))
                .andExpect(jsonPath("$[3].name", is("another product else")));

        verify(productService, times(1)).getAll();
        verifyNoMoreInteractions(productService);
    }

    @Test
    @Ignore
    public void createProduct() throws Exception {
    }

    @Test
    @Ignore
    public void updateProduct() throws Exception {
    }

    @Test
    @Ignore
    public void deleteProduct() throws Exception {
    }

}