package com.store.utils;

import com.store.domain.Transaction;
import com.store.domain.Customer;
import com.store.domain.Product;
import com.store.dto.CustomerOrder;
import com.store.dto.CustomerPurchase;
import com.store.dto.ProductOrder;
import com.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DrawUtil {

//    @Autowired
//    ProductService productService;

//    public static Iterable<Integer> getDniCustomers(Iterable<Transaction> buyers) {
//        Set<Integer> customer_dni = new HashSet<Integer>();
//        for(Transaction buyer:buyers)
//            customer_dni.add(buyer.getCustDni());
//        return customer_dni;
//    }

    public static int getAwards(Iterable<CustomerPurchase> customers, Iterable<Product> products) {
        int luckyDni;
        List<Integer> dnisAmphora = new ArrayList<>();
        for (CustomerPurchase customer:customers){
            setChancesForPrize(customer, dnisAmphora, products);
        }
        return makeDrawPrize(dnisAmphora);
    }

    private static void setChancesForPrize(CustomerPurchase customer, List<Integer> dnisAmphora, Iterable<Product> listProducts) {
        List<Product> products = getProducts(customer, listProducts);
        if (!products.isEmpty()){
            for (Product product:products){
                int chances = getChanceByModel(product.getModel());
                setChancesToWin(customer, dnisAmphora, chances);
            }
        }
    }

    private static List<Product> getProducts(CustomerPurchase customer, Iterable<Product> listProducts) {
        List<CustomerOrder> custOrders = customer.getCustomerOrders();
        List<Product> productsResults = new ArrayList<>();
        for (CustomerOrder cOrder : custOrders) {
            List<ProductOrder> productOrders = cOrder.getProductOrders();
            loadProducts(productsResults, productOrders, listProducts);
        }
        return productsResults;
    }

    private static void loadProducts(List<Product> products, List<ProductOrder> productOrders, Iterable<Product> listProducts) {
        ProductService ps = new ProductService();
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

    private static void setChancesToWin(CustomerPurchase customer, List<Integer> dnisAmphora, int chances) {
        for (;0 < chances;chances--) {
            dnisAmphora.add(customer.getCustDni());
        }
    }

    private static int makeDrawPrize(List<Integer> dnisAmphora) {
        int randomInteger = (int) (Math.random() * dnisAmphora.size() -1 );
        showInConsole(dnisAmphora, randomInteger);
        return dnisAmphora.get(randomInteger);
    }

    private static void showInConsole(List<Integer> dnisAmphora, int randomInteger) {
        System.out.println("**Amphora of dni**");
        for (int dni : dnisAmphora) {
            System.out.println(dni);
        }
        System.out.println(String.format("position winner: %s", randomInteger));
        System.out.println(String.format("**** winner: %s ****", dnisAmphora.get(randomInteger)));
    }

    private static int getChanceByModel(String modelArg){
        switch (modelArg.toUpperCase()){
            case "M":
                return 1;
            case "L":
                return 2;
            case "XL":
                return 3;
            case "XXL":
                return 4;
            default:
                return 0;
        }
    }
}
