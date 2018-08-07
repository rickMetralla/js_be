package com.store.controller;

import com.store.LoggerWrapper;
import com.store.domain.*;
import com.store.dto.Invoice;
import com.store.dto.CustomerPurchase;
import com.store.dto.ProductOrder;
import com.store.service.*;
import com.store.utils.DrawUtil;
import com.store.utils.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
public class PromoController {

    @Autowired
    PrizeDrawService prizeDrawService;

    @Autowired
    TransactionService transactionService;
    
    @Autowired
    PromoStatusService promoStatusService;

    @Autowired
    PromoService promoService;

    @Autowired
    CustomerService customerService;

    @Autowired
    TransactionService transService;

    @Autowired
    ProductService prodService;
    Logger LOGGER = LoggerWrapper.getInstance().logger;

    @RequestMapping(value = "/promoStatus", method = RequestMethod.GET)
    public ResponseEntity<List<PromoStatus>> getAllPromoStatus(){
        return new ResponseEntity<List<PromoStatus>>(promoStatusService.getAllStatus(),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/promoStatus/{id}", method = RequestMethod.GET)
    public ResponseEntity<PromoStatus> getAllPromoStatus(@RequestParam int id){
        return new ResponseEntity<PromoStatus>(promoStatusService.getOneById(id),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/promoStatus", method = RequestMethod.POST)
    public ResponseEntity<String> createPromoStatus(@RequestBody PromoStatus promoStatus){
        promoStatusService.createPromoStatus(promoStatus);
        return new ResponseEntity<String>("successfully created", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/promos/{promoId}", method = RequestMethod.GET)
    public ResponseEntity<Promo> getPromo(@PathVariable Integer promoId){
        LOGGER.log(Level.INFO, "GET request for promotion with id {0}", promoId);
        Promo promo = promoService.findById(promoId);
        try{
            if(promo.getSeason() == null)
                return new ResponseEntity<Promo>(HttpStatus.NOT_FOUND);
        } catch (EntityNotFoundException e){
            LOGGER.log(Level.INFO, "Promotion with id {0} not found", promoId);
            return new ResponseEntity<Promo>(HttpStatus.NOT_FOUND);
        }
        LOGGER.log(Level.INFO, "Promotion found and retrieved with id {0}", promoId);
        return new ResponseEntity<Promo>(promo, HttpStatus.OK);
    }

    @RequestMapping(value = "/promos", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Promo>> getPromotions(){
        return new ResponseEntity<Iterable<Promo>>(promoService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/promos", method = RequestMethod.POST)
    public ResponseEntity<String> createPromo(@RequestBody Promo promo){
        if(verifyDate(promo)){
            promo.setStatus(2);
            promoService.create(promo);
            return new ResponseEntity<String>("Promo successfully created", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Not possible to create, date collision", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/promos", method = RequestMethod.PUT)
    public ResponseEntity<String> updatePromo(@RequestBody Promo promo){
        try{
            promoService.update(promo);
        }catch (PersistenceException e){
            return new ResponseEntity<String>(HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<String>("Promo successfully updated", HttpStatus.OK);
    }

    @RequestMapping(value = "/promos/{promoId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deletePromoById(@RequestBody Integer promoId){
        promoService.delete(promoId);
        return new ResponseEntity<String>("Promo successfully deleted", HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/promos/{idPromo}/activate", method = RequestMethod.PUT)
    public ResponseEntity<String> activatePromo(@PathVariable Integer idPromo){
        Promo promo = promoService.findById(idPromo);
        try {
            if(promo.getSeason() == null){
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
            }
        }catch (PersistenceException e){
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        if(promo.getStatus() == 2){
            promo.setStatus(1);
            promoService.update(promo);
            List<CustomerPurchase> availableCustomers = findAvailableCustomerByPromo(promo);
            loadPrizeDraw(availableCustomers, promo);
            return new ResponseEntity<String>("Activation completed", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Activation of promo not allowed, inactive status required",
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @RequestMapping(value = "/promos/{idPromo}/complete", method = RequestMethod.PUT)
    public ResponseEntity<String> completePromo(@PathVariable Integer idPromo){
        Promo promo = promoService.findById(idPromo);
        if(promo == null){
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        if(promo.getStatus() == 1){
            promo.setStatus(3);
            promoService.update(promo);
        }else{
            return new ResponseEntity<String>("Completion of promo not allowed, active status required",
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<String>("Completion of promo completed", HttpStatus.OK);
    }

    @RequestMapping(value = "/promos/{idPromo}/inactive", method = RequestMethod.PUT)
    public ResponseEntity<String> inactivePromo(@PathVariable Integer idPromo){
        Promo promo = promoService.findById(idPromo);
        if(promo == null){
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        if(promo.getStatus() == 1){
            promo.setStatus(2);
            promoService.update(promo);
        }else{
            return new ResponseEntity<String>("Inactivation of promo not allowed, active status required",
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<String>("Inactivation completed", HttpStatus.OK);
    }

    @RequestMapping(value = "/promos/{idPromo}/customers", method = RequestMethod.GET)
    public ResponseEntity<List<CustomerPurchase>> getAvailableCustomerByPromo(@PathVariable Integer idPromo){
        Promo promo = promoService.findById(idPromo);
        try{
            if(promo.getSeason() == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<CustomerPurchase> availableCustomers = findAvailableCustomerByPromo(promo);
        return new ResponseEntity<List<CustomerPurchase>>(availableCustomers, HttpStatus.OK);
    }

    @RequestMapping(value = "/drawprize/{idPromo}", method = RequestMethod.POST)
    public ResponseEntity<Customer> makeDrawPrize(@PathVariable Integer idPromo){
        Promo promo;
        try {
            promo = promoService.findById(idPromo);
            if(promo.getStatus() != 1){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            LOGGER.log(Level.INFO, "Make draw prize request for promo with ID: {0}", idPromo);
        } catch (PersistenceException e){
            LOGGER.log(Level.SEVERE, "Make draw prize failed for promo with ID: {0}", idPromo);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<CustomerPurchase> availableCustomers = findAvailableCustomerByPromo(promo);
        LOGGER.log(Level.INFO, String.format("Available users: %d for promo: %d", idPromo, availableCustomers.size()));

        int luckyDni = getAwards(availableCustomers/*, allProducts*/, promo);
        List<PrizeDraw> winnerToUpdate = prizeDrawService.getAllPrizes();
        updateWinner(winnerToUpdate, luckyDni, promo);

        Customer customer = customerService.findByDni(luckyDni);
        LOGGER.log(Level.SEVERE, "Winner after make draw prize: {0}", customer.toString());
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    private void loadPrizeDraw(List<CustomerPurchase> availableCustomers, Promo promo) {
        for (CustomerPurchase customer :
                availableCustomers) {
            int totalCustomerChances = setChancesForPrize(customer, new ArrayList<>(), promo);
            PrizeDraw pr = new PrizeDraw(customer.getCustDni(), promo.getId(), totalCustomerChances, false);
            prizeDrawService.create(pr);
        }
    }

    private void updateWinner(List<PrizeDraw> winnerToUpdate, int luckyDni, Promo promo) {
        for (PrizeDraw prizeDraw :
                winnerToUpdate) {
            if(prizeDraw.getPromoId() == promo.getId() && prizeDraw.getCustDni() == luckyDni){
                prizeDraw.setWinner(true);
                prizeDrawService.update(prizeDraw);
            }
        }
    }

    private List<CustomerPurchase> findAvailableCustomerByPromo(Promo promo) {
        List<CustomerPurchase> availableCustomers = new ArrayList<>();
        loadAvailableCustomerList(availableCustomers, promo);
        return availableCustomers;
    }

    private int getAwards(Iterable<CustomerPurchase> customers/*, Iterable<Product> products*/, Promo promo) {
        List<Integer> dnisAmphora = new ArrayList<>();
        for (CustomerPurchase customer : customers){
            setChancesForPrize(customer, dnisAmphora/*, products*/, promo);
        }
        return DrawUtil.makeDrawPrize(dnisAmphora);
    }

    private int setChancesForPrize(CustomerPurchase customer, List<Integer> dnisAmphora/*,
                                           Iterable<Product> listProducts*/, Promo promo) {
        Iterable<Product> listProducts = prodService.getAll();
        List<Product> products = getProducts(customer, listProducts, promo);
        int totalCustomerChances = 0;
        if (!products.isEmpty()){
            for (Product product : products){
                int chances = DrawUtil.getChanceByModel(product.getModel()); //should retrieve from db
                totalCustomerChances = totalCustomerChances + chances;
                DrawUtil.setChancesToWin(customer, dnisAmphora, chances);
            }
        }
        return totalCustomerChances;
    }

    private List<Product> getProducts(CustomerPurchase customer, Iterable<Product> listProducts, Promo promo) {
        List<Invoice> invoices = customer.getInvoices();
        List<Product> productsResults = new ArrayList<>();
        Date start = promo.getStartAt();
        Date end = promo.getEndAt();
        for (Invoice cOrder : invoices) {
            if(cOrder.getPurchasedAt().after(start) && cOrder.getPurchasedAt().before(end)){
                List<ProductOrder> productOrders = cOrder.getProductOrders();
                loadProducts(productsResults, productOrders, listProducts);
            }
        }
        return productsResults;
    }

    private void loadProducts(List<Product> products, List<ProductOrder> productOrders, Iterable<Product> listProducts) {
        for (ProductOrder pOrder : productOrders) {
            Product product = findProduct(pOrder.getProdId(), listProducts);
            for (int i = 0; i < pOrder.getAmount(); i++){
                if(product != null){
                    products.add(product);
                }
            }
        }
    }

    private static Product findProduct(int prodId, Iterable<Product> listProducts) {
        for (Product p : listProducts) {
            if (p.getId() == prodId) {
                return p;
            }
        }
        return null;
    }

    private void loadAvailableCustomerList(List<CustomerPurchase> availableCustomers, Promo promo) {
        Date start = promo.getStartAt();
        Date end = promo.getEndAt();
        List<Transaction> transactions = transactionService.getAllTransaction();
        List<PrizeDraw> prizeDraws = prizeDrawService.getAllPrizes();
        Iterable<CustomerPurchase> customers = TransactionUtil.normalizeTransaction(transactions);
        for (CustomerPurchase custPurchase : customers) {
            if(existPurchaseByDate(custPurchase.getInvoices(), start, end) &&
                    !isWinner(prizeDraws, custPurchase.getCustDni(), promo.getId())){
                availableCustomers.add(custPurchase);
            }
        }
    }

    private boolean isWinner(List<PrizeDraw> prizeDraws, int custDni, int promoId) {
        for (PrizeDraw prizeDraw: prizeDraws) {
            if(prizeDraw.getCustDni() == custDni && prizeDraw.getPromoId() == promoId){
                return prizeDraw.isWinner();
            }
        }
        return false;
    }

    private boolean existPurchaseByDate(List<Invoice> invoices, Date start, Date end) {
        for (Invoice invoice : invoices) {
            Date currentOrderDate = invoice.getPurchasedAt();
            if(currentOrderDate.after(start) && currentOrderDate.before(end)){
                return true;
            }
        }
        return false;
    }

    private Promo findActivePromo() {
        Iterable<Promo> promos = promoService.findAll();
        for (Promo promo : promos) {
            if(promo.getStatus() == 1){
                return promo;
            }
        }
        return null;
    }

    private boolean verifyDate(Promo promo) {
        Date start = promo.getStartAt();
        Date end = promo.getEndAt();
        if (start.after(end)){
            return false;
        }
        Iterable<Promo> promos = promoService.findAll();
        for (Promo l : promos) {
            if (start.after(l.getStartAt()) && start.before(l.getEndAt()) ||
                    end.after(l.getStartAt()) && end.before(l.getEndAt())){
                return false;
            }
        }
        return true;
    }
}
