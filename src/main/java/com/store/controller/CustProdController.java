package com.store.controller;

import com.store.domain.CustProd;
import com.store.domain.Customer;
import com.store.service.CustProdService;
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
public class CustProdController {
    @Autowired
    CustProdService service;

    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/abuyers", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Customer>> getAvailableBuyers(){
        Iterable<CustProd> customers = service.getBuyers();
        Iterable<Integer> availables = DrawUtil.getDniCustomers(customers);

        Iterable<Customer> ucst = getAvalaible(availables);

        return new ResponseEntity<Iterable<Customer>>(ucst, HttpStatus.OK);
    }

    @RequestMapping(value = "/buyers", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Customer>> getAllBuyers(){
        Iterable<CustProd> customers = service.getBuyers();
        Iterable<Integer> dni = DrawUtil.getDniCustomers(customers);

        List<Customer> res = new ArrayList<>();
        for (int i:dni) {
            res.add(customerService.findByDni(i));
        }
        return new ResponseEntity<Iterable<Customer>>(res, HttpStatus.OK);
    }

    @RequestMapping(value = "/prize", method = RequestMethod.GET)
    public ResponseEntity<Customer> makeDraw(){

        Iterable<CustProd> customers = service.getBuyers();
        Iterable<Integer> availables = DrawUtil.getDniCustomers(customers);
        Iterable<Customer> ucst = getAvalaible(availables);

        int awardedDni = DrawUtil.getAwards(ucst);
        Customer luckyCustomer = customerService.findByDni(awardedDni);
        updateLuckyCustomer(luckyCustomer);
        return new ResponseEntity<Customer>(luckyCustomer, HttpStatus.OK);
    }

    private void updateLuckyCustomer(Customer luckyCustomer) {
        luckyCustomer.setWinner(true);
        customerService.update(luckyCustomer);
    }

    private Iterable<Customer> getAvalaible(Iterable<Integer> dni){
        List<Customer> res = new ArrayList<>();
        for (int i:dni) {
            Customer cu = customerService.findByDni(i);
            if (!cu.isWinner()){
                res.add(cu);
            }
        }
        return res;
    }
}
