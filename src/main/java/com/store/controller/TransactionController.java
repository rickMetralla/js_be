package com.store.controller;

import com.store.domain.Transaction;
import com.store.domain.Customer;
import com.store.service.TransactionService;
import com.store.service.CustomerService;
import com.store.utils.DrawUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
public class TransactionController {
    @Autowired
    TransactionService service;

    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public ResponseEntity<String> createPurchase(@RequestBody Transaction transaction){
        saveTransactionForCustomer(transaction);
        return new ResponseEntity<String>("successfully created", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/buyers", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Transaction>> getBuyers(){
        Iterable<Transaction> transaction = service.getAll();
        return new ResponseEntity<Iterable<Transaction>>(transaction, HttpStatus.OK);
    }

    @RequestMapping(value = "/abuyers", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Transaction>> getAvailableBuyers(){
        Iterable<Transaction> transaction = service.getAll();

        Iterable<Integer> availables = DrawUtil.getDniCustomers(transaction );

        Iterable<Transaction> ucst = getAvalaible(availables);

        return new ResponseEntity<Iterable<Transaction>>(ucst, HttpStatus.OK);
    }

    private void saveTransactionForCustomer(Transaction transaction) {
        service.createBuy(transaction);
    }


//    @RequestMapping(value = "/buyers", method = RequestMethod.GET)
//    public ResponseEntity<Iterable<Customer>> getAllBuyers(){
//        Iterable<Transaction> transaction = service.getAll();
//        Iterable<Integer> dni = DrawUtil.getDniCustomers(transaction);
//
//        List<Customer> res = new ArrayList<>();
//        for (int i:dni) {
//            res.add(customerService.findByDni(i));
//        }
//        return new ResponseEntity<Iterable<Customer>>(res, HttpStatus.OK);
//    }

//    @RequestMapping(value = "/prize", method = RequestMethod.GET)
//    public ResponseEntity<Customer> makeDraw(){
//
//        Iterable<Transaction> customers = service.getAll();
//        Iterable<Integer> availables = DrawUtil.getDniCustomers(customers);
//        Iterable<Customer> ucst = getAvalaible(availables);
//
//        int awardedDni = DrawUtil.getAwards(ucst);
//        Customer luckyCustomer = customerService.findByDni(awardedDni);
//        updateLuckyCustomer(luckyCustomer);
//        return new ResponseEntity<Customer>(luckyCustomer, HttpStatus.OK);
//    }

    private void updateLuckyCustomer(Customer luckyCustomer) {
        //luckyCustomer.setWinner(true);
        customerService.update(luckyCustomer);
    }

    private Iterable<Transaction> getAvalaible(Iterable<Integer> dni){
        List<Transaction> res = new ArrayList<>();
//        for (int i:dni) {
//            Customer cu = customerService.findByDni(i);
////            if (!cu.isWinner()){
////                res.add(cu);
////            }
//        }
        return res;
    }
}
