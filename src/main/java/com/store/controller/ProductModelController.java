package com.store.controller;

import com.store.LoggerWrapper;
import com.store.domain.ProductModel;
import com.store.service.ProductModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
public class ProductModelController {

    @Autowired
    ProductModelService service;
    Logger LOGGER = LoggerWrapper.getInstance().logger;

    @RequestMapping(value = "/pmodels", method = RequestMethod.GET)
    public ResponseEntity<Iterable<ProductModel>> getAllProductModel(){
        return new ResponseEntity<Iterable<ProductModel>>(service.getAll(), HttpStatus.OK);
    }
}
