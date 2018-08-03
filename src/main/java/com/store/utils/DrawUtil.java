package com.store.utils;

import com.store.dto.CustomerPurchase;

import java.util.List;

public class DrawUtil {

    public static void setChancesToWin(CustomerPurchase customer, List<Integer> dnisAmphora, int chances) {
        for (;0 < chances;chances--) {
            dnisAmphora.add(customer.getCustDni());
        }
    }

    public static int makeDrawPrize(List<Integer> dnisAmphora) {
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

    public static int getChanceByModel(String modelArg){
        switch (modelArg.toUpperCase()){
            case "M":
                return 2;
            case "L":
                return 3;
            case "XL":
                return 4;
            case "XXL":
                return 5;
            default:
                return 1;
        }
    }
}
