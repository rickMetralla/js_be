package com.store.controller;

import com.store.LoggerWrapper;
import com.store.domain.Order;
import com.store.domain.Product;
import com.store.domain.Transaction;
import com.store.dto.Invoice;
import com.store.dto.CustomerPurchase;
import com.store.dto.ProductOrder;
import com.store.service.ProductService;
import com.store.service.TransactionService;
import com.store.service.CustomerService;
import com.store.utils.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
public class TransactionController {

    @Autowired
    TransactionService service;

    @Autowired
    ProductService prodService;

    @Autowired
    CustomerService customerService;
    Logger LOGGER = LoggerWrapper.getInstance().logger;

    @RequestMapping(value = "/purchases", method = RequestMethod.POST)
    public ResponseEntity<String> createPurchase(@RequestBody CustomerPurchase customerPurchase){
        LOGGER.log(Level.INFO, "Applying purchase request for: {0}", customerPurchase.toString());
        try {
            validatePurchase(customerPurchase);
            saveTransactionForCustomer(customerPurchase);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Not possible to perform purchase: {0}", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        LOGGER.log(Level.INFO, "Purchase transaction for: {0} Completed", customerPurchase.toString());
        return new ResponseEntity<String>("Successfully created", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/purchases", method = RequestMethod.GET)
    public ResponseEntity<Iterable<CustomerPurchase>> getPurchasers(){
        Iterable<Transaction> transaction = service.getAll();
        Iterable<CustomerPurchase> normalizedTransaction = TransactionUtil.normalizeTransaction(transaction);
        return new ResponseEntity<Iterable<CustomerPurchase>>(normalizedTransaction , HttpStatus.OK);
    }

    @RequestMapping(value = "/purchases/{dni}", method = RequestMethod.GET)
    public ResponseEntity<Transaction> getPurchase(){
        return null;
    }

    private void validatePurchase(CustomerPurchase customerPurchase) throws Exception {
        int dni = customerPurchase.getCustDni(); //maybe validate if dni exist
        List<Invoice> invoices = customerPurchase.getInvoices();
        for (Invoice custOrder : invoices) {
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
                String msg = String.format("Product %s amount request out of stock: Amount requested: %d; Stock: %d",
                        product.getName(), amount, product.getStock());
                LOGGER.log(Level.SEVERE, msg);
                throw new Exception("Amount is bigger than stock");
            }
            LOGGER.log(Level.INFO, "Stock validation for product {0} OK", product.getName());
        }
    }

    private void saveTransactionForCustomer(CustomerPurchase cp) {
        for (Invoice po: cp.getInvoices()){
            for (ProductOrder pOrder: po.getProductOrders()) {
                Order order = new Order(pOrder.getProdId(), pOrder.getAmount());
                Transaction transaction = new Transaction(cp.getCustDni(), order, po.getPurchasedAt());
                service.createBuy(transaction);
                LOGGER.log(Level.INFO, "Transaction: {0} saved successfully", transaction.toString());
            }
        }
    }

//    private Iterable<CustomerPurchase> normalizeTransaction(Iterable<Transaction> transactions){
//        List<CustomerPurchase> result = new ArrayList<>();
//        for (Transaction tr : transactions){
//           loadTransaction(tr, result);
//        }
//        return result;
//    }
//
//    private void loadTransaction(Transaction tr, List<CustomerPurchase> result) {
//        int indexCustomer = getIndexCustomer(tr.getCustDni(), result);
//        if (indexCustomer > -1) {
//            ProductOrder prod = new ProductOrder(tr.getOrder());
//            List<Invoice> res = result.get(indexCustomer).getInvoices();
//            for (Invoice cPurch : res) {
//                if(cPurch.getPurchasedAt().compareTo(tr.getPurchasedAt()) == 0){
//                    int i = result.get(indexCustomer).getInvoices().indexOf(cPurch);
//                    result.get(indexCustomer).getInvoices().get(i).getProductOrders().add(prod);
//                    return;
//                }
//            }
//            List<ProductOrder> lpo = new ArrayList<ProductOrder>() {{
//                add(new ProductOrder(tr.getOrder()));
//            }};
//
//            Invoice custOrder = new Invoice(lpo, tr.getPurchasedAt());
//            result.get(indexCustomer).getInvoices().add(custOrder);
//        } else {
//            List<ProductOrder> lpo = new ArrayList<ProductOrder>() {{
//                add(new ProductOrder(tr.getOrder()));
//            }};
//
//            List<Invoice> listCustomerOrders = new ArrayList<Invoice>() {{
//                add(new Invoice(lpo, tr.getPurchasedAt()));
//            }};
//            CustomerPurchase cp = new CustomerPurchase(tr.getCustDni(), listCustomerOrders);
//            result.add(cp);
//        }
//    }
//
//    private int getIndexCustomer(int custDni, List<CustomerPurchase> result) {
//        if(!result.isEmpty()){
//            for (CustomerPurchase cp : result) {
//                if (cp.getCustDni() == custDni){
//                    return result.indexOf(cp);
//                }
//            }
//        }
//        return -1;
//    }

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
