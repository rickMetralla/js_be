package com.store.utils;

import com.store.LoggerWrapper;
import com.store.dto.CustomerPurchase;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DrawUtil {
    static Logger LOGGER = LoggerWrapper.getInstance().logger;

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
        LOGGER.log(Level.INFO, "**Amphora of dni**");
        for (int dni : dnisAmphora) {
            System.out.println(dni);
            LOGGER.log(Level.INFO, (String.valueOf(dni)));
        }
        System.out.println(String.format("position winner: %s", randomInteger));
        LOGGER.log(Level.INFO, "position winner: {0}", randomInteger);
        System.out.println(String.format("**** winner: %s ****", dnisAmphora.get(randomInteger)));
        LOGGER.log(Level.INFO, "**** winner: {0} ****", dnisAmphora.get(randomInteger));
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
