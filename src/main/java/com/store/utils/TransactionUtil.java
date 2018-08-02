package com.store.utils;

import com.store.domain.Transaction;
import com.store.dto.Invoice;
import com.store.dto.CustomerPurchase;
import com.store.dto.ProductOrder;

import java.util.ArrayList;
import java.util.List;

public class TransactionUtil {

    public static Iterable<CustomerPurchase> normalizeTransaction(Iterable<Transaction> transactions){
        List<CustomerPurchase> result = new ArrayList<>();
        for (Transaction tr : transactions){
            loadTransaction(tr, result);
        }
        return result;
    }

    private static void loadTransaction(Transaction tr, List<CustomerPurchase> result) {
        int indexCustomer = getIndexCustomer(tr.getCustDni(), result);
        if (indexCustomer > -1) {
            ProductOrder prod = new ProductOrder(tr.getOrder());
            List<Invoice> res = result.get(indexCustomer).getInvoices();
            for (Invoice cPurch : res) {
                if(cPurch.getPurchasedAt().compareTo(tr.getPurchasedAt()) == 0){
                    int i = result.get(indexCustomer).getInvoices().indexOf(cPurch);
                    result.get(indexCustomer).getInvoices().get(i).getProductOrders().add(prod);
                    return;
                }
            }
            List<ProductOrder> lpo = new ArrayList<ProductOrder>() {{
                add(new ProductOrder(tr.getOrder()));
            }};

            Invoice custOrder = new Invoice(lpo, tr.getPurchasedAt());
            result.get(indexCustomer).getInvoices().add(custOrder);
        } else {
            List<ProductOrder> lpo = new ArrayList<ProductOrder>() {{
                add(new ProductOrder(tr.getOrder()));
            }};

            List<Invoice> listInvoices = new ArrayList<Invoice>() {{
                add(new Invoice(lpo, tr.getPurchasedAt()));
            }};
            CustomerPurchase cp = new CustomerPurchase(tr.getCustDni(), listInvoices);
            result.add(cp);
        }
    }

    private static int getIndexCustomer(int custDni, List<CustomerPurchase> result) {
        if(!result.isEmpty()){
            for (CustomerPurchase cp : result) {
                if (cp.getCustDni() == custDni){
                    return result.indexOf(cp);
                }
            }
        }
        return -1;
    }
}
