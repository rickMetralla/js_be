package com.store.controller;

import com.store.LoggerWrapper;
import com.store.domain.Customer;
import com.store.domain.Promo;
import com.store.domain.Product;
import com.store.dto.CustomerPurchase;
import com.store.service.CustomerService;
import com.store.service.PromoService;
import com.store.service.ProductService;
import com.store.service.TransactionService;
import com.store.utils.DrawUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
public class PromoController {

    @Autowired
    PromoService promoService;

    @Autowired
    CustomerService customerService;

    @Autowired
    TransactionService transService;

    @Autowired
    ProductService prodService;
    Logger LOGGER = LoggerWrapper.getInstance().logger;

    @RequestMapping(value = "/promos/{promoId}", method = RequestMethod.GET)
    public ResponseEntity<Promo> getLottery(@PathVariable Integer promoId){
        Promo lot = promoService.findById(promoId);
        if(lot == null)
            return new ResponseEntity<Promo>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<Promo>(lot, HttpStatus.OK);
    }

    @RequestMapping(value = "/promos", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Promo>> getLotteries(){
        return new ResponseEntity<Iterable<Promo>>(promoService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/promos", method = RequestMethod.POST)
    public ResponseEntity<String> createLottery(@RequestBody Promo lot){
        if(verifyDate(lot)){
            promoService.create(lot);
            return new ResponseEntity<String>("Promo successfully created", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Not possible to create, date collision", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/promos", method = RequestMethod.PUT)
    public ResponseEntity<String> updateLottery(@RequestBody Promo lot){
        promoService.update(lot);
        return new ResponseEntity<String>("Promo successfully updated", HttpStatus.OK);
    }

    @RequestMapping(value = "/promos/{promoId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> createLottery(@RequestBody int promoId){
        promoService.delete(promoId);
        return new ResponseEntity<String>("Promo successfully deleted", HttpStatus.OK);
    }

    @RequestMapping(value = "/promos/active", method = RequestMethod.GET)
    public ResponseEntity<Promo> getActiveLottery(){
        Promo activeLot = findActiveLotery();
        if(activeLot == null){
            return new ResponseEntity<Promo>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Promo>(activeLot, HttpStatus.OK);
    }

    @RequestMapping(value = "/drawPrize", method = RequestMethod.POST)
    public ResponseEntity<Customer> makeDrawPrize(@RequestBody Iterable<CustomerPurchase> customers){
        Iterable<Product> allProducts = prodService.getAll();
        int luckyDni = DrawUtil.getAwards(customers, allProducts);
        Customer customer = customerService.findByDni(luckyDni);

        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    private Promo findActiveLotery() {
        Iterable<Promo> lots = promoService.findAll();
        for (Promo lot : lots) {
            if(lot.isActive()){
                return lot;
            }
        }
        return null;
    }

    private boolean verifyDate(Promo lot) {
        Iterable<Promo> lots = promoService.findAll();
        Date start = lot.getStartAt();
        Date end = lot.getEndAt();
        for (Promo l : lots) {
            if (start.after(l.getStartAt()) && start.before(l.getEndAt()) ||
                    end.after(l.getStartAt()) && end.before(l.getEndAt())){
                return false;
            }
        }
        return true;
    }
}
