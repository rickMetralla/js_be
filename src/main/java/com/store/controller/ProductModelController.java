package com.store.controller;

import com.store.LoggerWrapper;
import com.store.domain.ProductModel;
import com.store.service.ProductModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
public class ProductModelController {

    @Autowired
    ProductModelService productModelService;
    Logger LOGGER = LoggerWrapper.getInstance().logger;

    @RequestMapping(value = "/pmodels", method = RequestMethod.GET)
    public ResponseEntity<Iterable<ProductModel>> getAllProductModel(){
        return new ResponseEntity<Iterable<ProductModel>>(productModelService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/pmodels", method = RequestMethod.POST)
    public ResponseEntity<ProductModel> createNewProductModel(@RequestBody ProductModel productModel)
            throws HttpRequestMethodNotSupportedException {
        try{
            ProductModel newProductModel = productModelService.createNewModel(productModel);
            return new ResponseEntity<ProductModel>(newProductModel, HttpStatus.CREATED);
        }catch (HttpRequestMethodNotSupportedException error){
            throw error;
        }
    }

    @RequestMapping(value = "/pmodels", method = RequestMethod.PUT)
    public ResponseEntity<ProductModel> updateProductModel(@RequestBody ProductModel productModel)
            throws HttpRequestMethodNotSupportedException {
        try{
            ProductModel newProductModel = productModelService.updateModel(productModel);
            return new ResponseEntity<ProductModel>(newProductModel, HttpStatus.OK);
        }catch (HttpRequestMethodNotSupportedException error){
            throw error;
        }
    }

    @RequestMapping(value = "/pmodels/{modelName}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProductModel(@PathVariable String modelName) {
        productModelService.deleteModel(modelName);
        return new ResponseEntity(HttpStatus.OK);
    }

}
