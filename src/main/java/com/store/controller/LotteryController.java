package com.store.controller;

import com.store.domain.Customer;
import com.store.domain.Lottery;
import com.store.domain.Product;
import com.store.dto.CustomerPurchase;
import com.store.service.CustomerService;
import com.store.service.LotteryService;
import com.store.service.ProductService;
import com.store.service.TransactionService;
import com.store.utils.DrawUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
public class LotteryController {

    @Autowired
    LotteryService lotService;

    @Autowired
    CustomerService customerService;

    @Autowired
    TransactionService transService;

    @Autowired
    ProductService prodService;

    @RequestMapping(value = "/lot/{idLot}", method = RequestMethod.GET)
    public ResponseEntity<Lottery> getLottery(@PathVariable Integer idLot){
        Lottery lot = lotService.findById(idLot);
        if(lot == null)
            return new ResponseEntity<Lottery>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<Lottery>(lot, HttpStatus.OK);
    }

    @RequestMapping(value = "/lots", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Lottery>> getLotteries(){
        return new ResponseEntity<Iterable<Lottery>>(lotService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/lot", method = RequestMethod.POST)
    public ResponseEntity<String> createLottery(@RequestBody Lottery lot){
        if(verifyDate(lot)){
            lotService.create(lot);
            return new ResponseEntity<String>("Lottery successfully created", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Not possible to create, date collision", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/lot", method = RequestMethod.PUT)
    public ResponseEntity<String> updateLottery(@RequestBody Lottery lot){
        lotService.update(lot);
        return new ResponseEntity<String>("Lottery successfully updated", HttpStatus.OK);
    }

    @RequestMapping(value = "/lot/{lotId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> createLottery(@RequestBody int lotId){
        lotService.delete(lotId);
        return new ResponseEntity<String>("Lottery successfully deleted", HttpStatus.OK);
    }

    @RequestMapping(value = "/activeLot", method = RequestMethod.GET)
    public ResponseEntity<Lottery> getActiveLottery(){
        Lottery activeLot = findActiveLotery();
        if(activeLot == null)
            return new ResponseEntity<Lottery>(activeLot, HttpStatus.NOT_FOUND);
        return new ResponseEntity<Lottery>(activeLot, HttpStatus.OK);
    }

    @RequestMapping(value = "/drawPrize", method = RequestMethod.POST)
    public ResponseEntity<Customer> makeDrawPrize(@RequestBody Iterable<CustomerPurchase> customers){
        Iterable<Product> allProducts = prodService.getAll();
        int luckyDni = DrawUtil.getAwards(customers, allProducts);
        Customer customer = customerService.findByDni(luckyDni);

        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    private Lottery findActiveLotery() {
        Iterable<Lottery> lots = lotService.findAll();
//        Date current = new Date();
        for (Lottery lot : lots) {
            if(lot.isActive()){
                return lot;
            }
        }
        return null;
    }

    private boolean verifyDate(Lottery lot) {
        Iterable<Lottery> lots = lotService.findAll();
        Date start = lot.getStartAt();
        Date end = lot.getEndAt();
        for (Lottery l : lots) {
            if (start.after(l.getStartAt()) && start.before(l.getEndAt()) ||
                    end.after(l.getStartAt()) && end.before(l.getEndAt())){
                return false;
            }
        }
        return true;
    }
}
