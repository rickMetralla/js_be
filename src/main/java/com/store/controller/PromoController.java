package com.store.controller;

import com.store.LoggerWrapper;
import com.store.domain.*;
import com.store.dto.Invoice;
import com.store.dto.CustomerPurchase;
import com.store.dto.ProductOrder;
import com.store.handlers.CustomRestExceptionHandler;
import com.store.service.*;
import com.store.utils.DrawUtil;
import com.store.utils.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    PrizeService prizeService;

    @Autowired
    TransactionService transactionService;
    
    @Autowired
    PromoStatusService promoStatusService;

    @Autowired
    PromoService promoService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductModelService productModelService;

    Logger LOGGER = LoggerWrapper.getInstance().logger;

    @RequestMapping(value = "/promos/{promoId}/prizedraws", method = RequestMethod.GET)
    public ResponseEntity<List<PrizeDraw>> getPrizeDrawByPromoId(@PathVariable int promoId){
        return new ResponseEntity<List<PrizeDraw>>(prizeDrawService.getAllByPromoId(promoId),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/promos/{promoId}/prizedraws/{winners}", method = RequestMethod.GET)
    public ResponseEntity<List<PrizeDraw>> getPrizeDrawByPromoId(@PathVariable("promoId") int promoId,
                                                                 @PathVariable("winners") boolean winners){
        return new ResponseEntity<List<PrizeDraw>>(prizeDrawService.getAllWinners(winners, promoId),
                HttpStatus.OK);
    }

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
    public ResponseEntity<PromoStatus> createPromoStatus(@RequestBody PromoStatus promoStatus){
//        promoStatusService.createPromoStatus(promoStatus);
        return new ResponseEntity<PromoStatus>(promoStatusService.createPromoStatus(promoStatus), HttpStatus.CREATED);
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
    public ResponseEntity<Promo> createPromo(@RequestBody Promo promo) throws HttpRequestMethodNotSupportedException {
        try{
            if(verifyDate(promo)){
                promo.setStatus(2);
                return new ResponseEntity<Promo>(promoService.create(promo), HttpStatus.CREATED);
            }else {
                throw new HttpRequestMethodNotSupportedException("Not possible to create, date collision");
            }
        } catch (HttpRequestMethodNotSupportedException error){
            throw error;
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
    public ResponseEntity deletePromoById(@PathVariable Integer promoId) throws HttpRequestMethodNotSupportedException {
        Promo promo = promoService.findById(promoId);
        if(promo.getStatus() != 2){
            throw new HttpRequestMethodNotSupportedException("Not possible to delete, promotion needs inactive status.");
        }
        promoService.delete(promoId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/promos/{idPromo}/activate", method = RequestMethod.PUT)
    public ResponseEntity<Promo> activatePromo(@PathVariable Integer idPromo) throws HttpRequestMethodNotSupportedException{
        Promo promo = promoService.findById(idPromo);
        try {
            if(promo.getStatus() == 2){
                promo.setStatus(1);
                promoService.update(promo);
                List<CustomerPurchase> availableCustomers = findAvailableCustomerByPromo(promo);
                loadPrizeDraw(availableCustomers, promo);
                return new ResponseEntity<Promo>(promo, HttpStatus.OK);
            }
            else{
                throw new HttpRequestMethodNotSupportedException("Activation of promo not allowed, inactive status required");
            }
        }catch (PersistenceException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<List<CustomerPurchase>> getAvailableCustomerByPromoBeforeActive(@PathVariable Integer idPromo){
        Promo promo = promoService.findById(idPromo);
        try{
            if(promo.getStatus() != 2){
                return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
            }
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<CustomerPurchase> availableCustomers = findAvailableCustomerByPromo(promo);
        return new ResponseEntity<List<CustomerPurchase>>(availableCustomers, HttpStatus.OK);
    }

    @RequestMapping(value = "/promos/{idPromo}/active/customers", method = RequestMethod.GET)
    public ResponseEntity<List<CustomerPurchase>> getAvailableCustomerByPromoAfterActive(@PathVariable("idPromo") final Integer idPromo){
        Promo promo = promoService.findById(idPromo);
        try{
            if(promo.getStatus() != 1){
                return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
            }
//            if()
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<PrizeDraw> prizeDraws = prizeDrawService.getAllByPromoId(idPromo);
        List<CustomerPurchase> availableCustomers = loadAvailableCustomersActivePromo(prizeDraws, promo);
        return new ResponseEntity<List<CustomerPurchase>>(availableCustomers, HttpStatus.OK);
    }

//    @RequestMapping(value = "/{idPromo}/drawprize", method = RequestMethod.POST)
//    public ResponseEntity<Customer> makeDrawPrize(@PathVariable Integer idPromo){
//        Promo promo = promoService.findById(idPromo);
//        List<Prize> prizes = prizeService.getAllPrizesByPromoId(idPromo);
//
//        try {
////            promo = promoService.findById(idPromo);
//            if(promo.getStatus() != 1){
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//            LOGGER.log(Level.INFO, "Make draw prize request for promo with ID: {0}", idPromo);
////            List<Prize> prizes = prizeService.getAllPrizesByPromoId(idPromo);
//            if(prizes.size() == 0) {
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//        } catch (PersistenceException e){
//            LOGGER.log(Level.SEVERE, "Make draw prize failed for promo with ID: {0}", idPromo);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        // get available customer for prize's promotion
//        List<PrizeDraw> prizeDraws = prizeDrawService.getAllByPromoId(idPromo);
//        List<CustomerPurchase> availableCustomers = loadAvailableCustomersActivePromo(prizeDraws, promo);
//        LOGGER.log(Level.INFO, String.format("Available users: %d for promo: %d", idPromo, availableCustomers.size()));
//
//        //draw prize with all available customers
//        int luckyDni = getAwards(availableCustomers, promo);
//        updateWinner(prizeDraws, luckyDni, idPromo, prizes.get(0).getId());
//
//        Customer customer = customerService.findByDni(luckyDni);
//        LOGGER.log(Level.SEVERE, "Winner after make draw prize: {0}", customer.toString());
//        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
//    }

    @RequestMapping(value = "promos/{idPromo}/drawprizes", method = RequestMethod.POST)
    public ResponseEntity<List<Customer>> makeDrawPrizes(@PathVariable Integer idPromo){
        Promo promo = promoService.findById(idPromo);
        List<Prize> prizes = prizeService.getAllPrizesByPromoId(idPromo);

        try {
            if(promo.getStatus() != 1){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            LOGGER.log(Level.INFO, "Make draw prize request for promo with ID: {0}", idPromo);
            if(prizes.size() == 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (PersistenceException e){
            LOGGER.log(Level.SEVERE, "Make draw prize failed for promo with ID: {0}", idPromo);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // get available customer for prize's promotion
        List<PrizeDraw> prizeDraws = prizeDrawService.getAllByPromoId(idPromo);
        List<CustomerPurchase> availableCustomers = loadAvailableCustomersActivePromo(prizeDraws, promo);
        LOGGER.log(Level.INFO, String.format("Available users: %d for promo: %d", idPromo, availableCustomers.size()));

        //draw prize with all available customers and all prizes for current promotion
        List<Customer> customerWinners = new ArrayList<>();
        for (Prize prize : prizes) {
            int luckyDni = getAwards(availableCustomers, promo);
            updateWinner(prizeDraws, luckyDni, idPromo, prize.getId());
            removeAvailableCustomerAfterWinPrize(availableCustomers, luckyDni);
            Customer customer = customerService.findByDni(luckyDni);
            customerWinners.add(customer);
            LOGGER.log(Level.SEVERE, "Winner after make draw prize: {0}", customer.toString());
        }
        return new ResponseEntity<>(customerWinners, HttpStatus.OK);
    }

    private void removeAvailableCustomerAfterWinPrize(List<CustomerPurchase> availableCustomers, int dni) {
        for(int i = 0; i < availableCustomers.size(); i++){
            if(availableCustomers.get(i).getCustDni() == dni){
                availableCustomers.remove(i);
                return;
            }
        }
    }

    private List<CustomerPurchase> loadAvailableCustomersActivePromo(List<PrizeDraw> prizeDrawList, Promo promo){
        List<CustomerPurchase> customerPurchaseList = new ArrayList<>();
        for (PrizeDraw prizeDraw : prizeDrawList) {
            if(!prizeDraw.isWinner()){
                int dni = prizeDraw.getCustDni();
                loadPurchaseByDni(customerPurchaseList, dni, promo);
            }
        }
        return customerPurchaseList;
    }

    private void loadPurchaseByDni(List<CustomerPurchase> customerPurchaseList, int dni, Promo promo) {
        List<Transaction> transactionList = transactionService.getAllTransactionByCustDni(dni);
        List<CustomerPurchase> customerPurchase = TransactionUtil.normalizeTransactionByDateRange(transactionList, promo.getStartAt(), promo.getEndAt());
        if(customerPurchase.size() == 1){
            customerPurchaseList.add(customerPurchase.get(0));
        }
    }

    private void loadPrizeDraw(List<CustomerPurchase> availableCustomers, Promo promo) {
        for (CustomerPurchase customer : availableCustomers) {
            int totalCustomerChances = setChancesForPrize(customer, new ArrayList<>(), promo);
            PrizeDraw pr = new PrizeDraw(customer.getCustDni(), promo.getId(), totalCustomerChances, false, null);
            prizeDrawService.create(pr);
        }
    }

    private void updateWinner(List<PrizeDraw> winnerToUpdate, int luckyDni, int promoId, int prize) {
        for (PrizeDraw prizeDraw : winnerToUpdate) {
            if(prizeDraw.getPromoId() == promoId && prizeDraw.getCustDni() == luckyDni){
                prizeDraw.setWinner(true);
                prizeDraw.setWonArticle(prize);
                prizeDrawService.update(prizeDraw);
            }
        }
    }

    private List<CustomerPurchase> findAvailableCustomerByPromo(Promo promo) {
        List<CustomerPurchase> availableCustomers = new ArrayList<>();
        loadAvailableCustomerList(availableCustomers, promo);
        return availableCustomers;
    }

    private int getAwards(Iterable<CustomerPurchase> customers, Promo promo) {
        List<Integer> dnisAmphora = new ArrayList<>();
        for (CustomerPurchase customer : customers){
            setChancesForPrize(customer, dnisAmphora, promo);
        }
        return DrawUtil.makeDrawPrize(dnisAmphora);
    }

    private int setChancesForPrize(CustomerPurchase customer, List<Integer> dnisAmphora, Promo promo) {
        Iterable<Product> listProducts = productService.getAll();
        List<Product> products = getProducts(customer, listProducts, promo);
        int totalCustomerChances = 0;
        if (!products.isEmpty()){
            for (Product product : products){
//                int chances = DrawUtil.getChanceByModel(product.getModel()); //should retrieve from db
                int chances = productModelService.getChanceByModel(product.getModel()); //should retrieve from db
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
