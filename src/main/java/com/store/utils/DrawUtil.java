package com.store.utils;

import com.store.domain.Transaction;
import com.store.domain.Customer;
import com.store.domain.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DrawUtil {

    public static Iterable<Integer> getDniCustomers(Iterable<Transaction> buyers) {
        Set<Integer> customer_dni = new HashSet<Integer>();
        for(Transaction buyer:buyers)
            customer_dni.add(buyer.getCustDni());
        return customer_dni;
    }

//    public static int getAwards(Iterable<Customer> customers) {
//        int luckyDni;
//        List<Integer> dnisAmphora = new ArrayList<>();
//        for (Customer customer:customers){
//            setChancesForPrize(customer, dnisAmphora);
//        }
//        return makeDrawPrize(dnisAmphora);
//    }

//    private static void setChancesForPrize(Customer customer, List<Integer> dnisAmphora) {
//        List<Product> products = customer.getProducts();
//        if (!products.isEmpty()){
//            for (Product product:products){
//                int chances = getChanceByModel(product.getModel());
//                setChancesToWin(customer, dnisAmphora, chances);
//            }
//        }
//    }

    private static void setChancesToWin(Customer customer, List<Integer> dnisAmphora, int chances) {
        for (;0 < chances;chances--) {
            dnisAmphora.add(customer.getDni());
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
