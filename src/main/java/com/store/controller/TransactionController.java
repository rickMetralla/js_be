package com.store.controller;

import com.store.domain.Order;
import com.store.domain.Product;
import com.store.domain.Transaction;
import com.store.dto.CustomerOrder;
import com.store.dto.CustomerPurchase;
import com.store.dto.ProductOrder;
import com.store.service.ProductService;
import com.store.service.TransactionService;
import com.store.service.CustomerService;
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
    ProductService prodService;

    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/purchases", method = RequestMethod.POST)
    public ResponseEntity<String> createPurchase(@RequestBody CustomerPurchase customerPurchase){
        try {
            validatePurchase(customerPurchase);
            saveTransactionForCustomer(customerPurchase);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Successfully created", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/purchases", method = RequestMethod.GET)
    public ResponseEntity<Iterable<CustomerPurchase>> getPurchasers(){
        Iterable<Transaction> transaction = service.getAll();
        Iterable<CustomerPurchase> normalizedTransaction = normalizeTransaction(transaction);
        return new ResponseEntity<Iterable<CustomerPurchase>>(normalizedTransaction , HttpStatus.OK);
    }

    @RequestMapping(value = "/purchases/{dni}", method = RequestMethod.GET)
    public ResponseEntity<Transaction> getPurchase(){
        return null;
    }

    private void validatePurchase(CustomerPurchase customerPurchase) throws Exception {
        int dni = customerPurchase.getCustDni();
        List<CustomerOrder> customerOrders = customerPurchase.getCustomerOrders();
        for (CustomerOrder custOrder : customerOrders) {
            List<ProductOrder> productOrders = custOrder.getProductOrders();
            validateProductOrders(productOrders);
        }
    }

    private void validateProductOrders(List<ProductOrder> productOrders) throws Exception {
        for (ProductOrder pOrder : productOrders) {
            int id = pOrder.getProdId();
            int amount = pOrder.getAmount();
            Product product = prodService.findById(id);
            if(product.getStock() < amount){
                throw new Exception("Amount is bigger than stock");
            }
        }
    }

    private void saveTransactionForCustomer(CustomerPurchase cp) {
        for (CustomerOrder po: cp.getCustomerOrders()){
            for (ProductOrder pOrder: po.getProductOrders()) {
                Order order = new Order(pOrder.getProdId(), pOrder.getAmount());
                Transaction transaction = new Transaction(cp.getCustDni(), order, po.getPurchasedAt());
                service.createBuy(transaction);
            }
        }
    }

    private Iterable<CustomerPurchase> normalizeTransaction(Iterable<Transaction> transactions){
        List<CustomerPurchase> result = new ArrayList<>();
        for (Transaction tr : transactions){
           loadTransaction(tr, result);
        }
        return result;
    }

    private void loadTransaction(Transaction tr, List<CustomerPurchase> result) {
        int indexCustomer = getIndexCustomer(tr.getCustDni(), result);
        if (indexCustomer > -1) {
            ProductOrder prod = new ProductOrder(tr.getOrder());
            List<CustomerOrder> res = result.get(indexCustomer).getCustomerOrders();
            for (CustomerOrder cPurch : res) {
                if(cPurch.getPurchasedAt().compareTo(tr.getPurchasedAt()) == 0){
                    int i = result.get(indexCustomer).getCustomerOrders().indexOf(cPurch);
                    result.get(indexCustomer).getCustomerOrders().get(i).getProductOrders().add(prod);
                    return;
                }
            }
            List<ProductOrder> lpo = new ArrayList<ProductOrder>() {{
                add(new ProductOrder(tr.getOrder()));
            }};

            CustomerOrder custOrder = new CustomerOrder(lpo, tr.getPurchasedAt());
            result.get(indexCustomer).getCustomerOrders().add(custOrder);
        } else {
            List<ProductOrder> lpo = new ArrayList<ProductOrder>() {{
                add(new ProductOrder(tr.getOrder()));
            }};

            List<CustomerOrder> listCustomerOrders = new ArrayList<CustomerOrder>() {{
                add(new CustomerOrder(lpo, tr.getPurchasedAt()));
            }};
            CustomerPurchase cp = new CustomerPurchase(tr.getCustDni(), listCustomerOrders);
            result.add(cp);
        }
    }

    private int getIndexCustomer(int custDni, List<CustomerPurchase> result) {
        if(!result.isEmpty()){
            for (CustomerPurchase cp : result) {
                if (cp.getCustDni() == custDni){
                    return result.indexOf(cp);
                }
            }
        }
        return -1;
    }

//    private void updateLuckyCustomer(Customer luckyCustomer) {
//        //luckyCustomer.setWinner(true);
//        customerService.update(luckyCustomer);
//    }

//    private Iterable<Transaction> getAvalaible(Iterable<Integer> dni){
//        List<Transaction> res = new ArrayList<>();
////        for (int i:dni) {
////            Customer cu = customerService.findByDni(i);
//////            if (!cu.isWinner()){
//////                res.add(cu);
//////            }
////        }
//        return res;
//    }
}
