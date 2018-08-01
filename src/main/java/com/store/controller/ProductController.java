package com.store.controller;

import com.store.LoggerWrapper;
import com.store.domain.Product;
import com.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
public class ProductController {

    Logger LOGGER = LoggerWrapper.getInstance().logger;

    @Autowired
    ProductService service;

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> getProductById(@PathVariable Integer id){
        LOGGER.log(Level.INFO, "Getting product id: {0} entry", id);
        Product oneProd = service.findById(id);
        if (oneProd == null){
            LOGGER.log(Level.INFO, "Id {0} product not found", id);
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        else {
            LOGGER.log(Level.INFO, "Returns product name: {0} ", oneProd.getName());
            return new ResponseEntity<Product>(oneProd, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Product>> getProducts(){
        return new ResponseEntity<Iterable<Product>>(service.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<String> createProduct(@RequestBody Product product){
        service.create(product);
        return new ResponseEntity<String>("Product created successfully", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/products", method = RequestMethod.PUT)
    public ResponseEntity<String> updateProduct(@RequestBody Product updatedProduct){
        if(service.findById(updatedProduct.getId()) == null){
            return new ResponseEntity<String>("Product not found", HttpStatus.NOT_FOUND);
        }
        else{
            service.update(updatedProduct);
            return new ResponseEntity<String>("Product updated successfully", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id){
        if(service.findById(id) == null){
            return new ResponseEntity<String>("Product not found", HttpStatus.NOT_FOUND);
        }
        else{
            service.delete(id);
            return new ResponseEntity<String>("Product deleted successfully", HttpStatus.OK);
        }
    }
}
