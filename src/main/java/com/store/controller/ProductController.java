package com.store.controller;

import com.store.domain.Product;
import com.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
public class ProductController {

    @Autowired
    ProductService service;

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> getProductById(@PathVariable Integer id){
        Product oneProd = service.findById(id);
        if (oneProd == null){
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        else {
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
