package llc.net.mydutyfree.base;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import llc.net.mydutyfree.response.Product;


/**
 * Created by gorf on 1/28/16.
 */
public class CartSingleton {

private static CartSingleton instance;

public static String customVar="Hello";

    List<Product> mProductList;
    Map<String,String> mProductsCounts;

    public static void initInstance()
    {
        if (instance == null)
        {
            // Create the instance
            instance = new CartSingleton();
        }
    }

    public static CartSingleton getInstance()
    {
        // Return the instance
        return instance;
    }

    private CartSingleton()
    {
        mProductList = new ArrayList<Product>();
        mProductsCounts = new HashMap<String, String>();
        // Constructor hidden because this is a singleton
    }

    public void customSingletonMethod()
    {
        // Custom method
    }

    public void AddProduct (Product product) {
        AddProduct(product, 1);
    }

    public void AddProduct (Product product, int count) {
        if (mProductsCounts.containsKey(product.getID())) {
            String strQuantity = mProductsCounts.get(product.getID());
            int quantity = Integer.parseInt(strQuantity);
            quantity = quantity + count;
            if (quantity < 1) {
                mProductsCounts.remove(product.getID());
                if (mProductList.contains(product))
                    mProductList.remove(product);
            } else {
                mProductsCounts.put(product.getID(), Integer.toString(quantity));
            }
        } else {
            if (count < 1) {
                mProductsCounts.remove(product.getID());
                if (mProductList.contains(product))
                    mProductList.remove(product);
            } else {
                mProductsCounts.put(product.getID(), Integer.toString(count));
                if (!(mProductList.contains(product)))
                    mProductList.add(product);
            }
        }
    }

    public void ChangeProductCount (Product product, int newCount) {
        if (mProductsCounts.containsKey(product.getID())) {
            if (newCount < 1) {
                mProductsCounts.remove(product.getID());
                if (mProductList.contains(product))
                    mProductList.remove(product);
            } else {
                mProductsCounts.put(product.getID(), Integer.toString(newCount));
            }
        } else {
            if (newCount < 1) {
                mProductsCounts.remove(product.getID());
                if (mProductList.contains(product))
                    mProductList.remove(product);
            } else {
                mProductsCounts.put(product.getID(), Integer.toString(newCount));
                if (!(mProductList.contains(product)))
                    mProductList.add(product);
            }
        }
    }

    public void RemoveProductWithID (String productID) {
//        for (Product item : mProductList) {
//            if (item.getID().equalsIgnoreCase(productID)) {
//                mProductList.remove(item);
//
//            }
//        }
        Iterator it = mProductList.iterator();
        while (it.hasNext())
        {
            Product item = (Product)it.next();
            if (item.getID().equalsIgnoreCase(productID)) {
                it.remove();
                if (mProductsCounts.containsKey(item.getID()))
                    mProductsCounts.remove(item.getID());
            }
        }
    }

    public void ClearCart () {
        mProductList.clear();
//        mProductList = new ArrayList<Product>();
        mProductsCounts.clear();
//        mProductsCounts = new HashMap<String, String>();
    }

    public List<Product> getProductList() {
        return mProductList;
    }

    public Product getProductWithID(String id) {
        Product retItem = null;
        for (int i = 0; i < mProductList.size(); i++) {
            Product retItemT = mProductList.get(i);
            if (retItemT.getID().equalsIgnoreCase(id)) {
                retItem = retItemT;
                break;
            }
        }
        return retItem;
    }

    public List<Product> getReversedProductList() {
        List<Product> reversedProductList = mProductList;
        Collections.reverse(reversedProductList);
        return reversedProductList;
    }

    public Map<String, String> getProductsCounts() {
        return mProductsCounts;
    }

    public int getProductsCount () {
        return mProductList.size();
    }

    public int getProductCount(String productID)
    {
        int result = 0;
        try {
            result = Integer.parseInt(mProductsCounts.get(productID));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getProductCountAll()
    {
        int result = 0;
        for (int i = 0; i < mProductList.size(); i++ ) {
            try {
                result += Integer.parseInt(mProductsCounts.get(mProductList.get(i).getID()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Map<String, Double> getTotalAmount() {
        Map<String, Double> result = new HashMap<>();
        Context cntx = MDFApplication.getAppContext();
        Map<String, String> mapCurrencies = ((MDFApplication)cntx).getCurrencySymbols();
        Iterator it = mapCurrencies.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            String key = (String)pair.getKey();
            double tmpReturnedValue = 0.0f;
            for (Product item : mProductList)
            {
                try {
                    tmpReturnedValue +=item.getPrice().get(key) * Integer.parseInt(mProductsCounts.get(item.getID()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            result.put(key, tmpReturnedValue);
            it.remove(); // avoids a ConcurrentModificationException
        }
       return result;
    }

    public Map<String, Double> getTotalWithoutDiscount() {
        Map<String, Double> result = new HashMap<>();
        Context cntx = MDFApplication.getAppContext();
        Map<String, String> mapCurrencies = ((MDFApplication)cntx).getCurrencySymbols();
        Iterator it = mapCurrencies.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            String key = (String)pair.getKey();
            double tmpReturnedValue = 0.0f;
            for (Product item : mProductList)
            {
                try {
                    tmpReturnedValue +=item.getPriceOld().get(key) * Integer.parseInt(mProductsCounts.get(item.getID()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            result.put(key, tmpReturnedValue);
            it.remove(); // avoids a ConcurrentModificationException
        }
        return result;
    }

    /*
        double returnValueUSD = 0.0f;
        double returnValueEUR = 0.0f;
        double returnValueUAH = 0.0f;
        double returnValueGBP = 0.0f;

        Context cntx = MDFApplication.getAppContext();
        ((MDFApplication)cntx).geta

        for (Product item : mProductList)
        {
            try {
                returnValueUSD += item.getPrice().get("USD") * Integer.parseInt(mProductsCounts.get(item.getID()));
                returnValueEUR += item.getPrice().get("EUR") * Integer.parseInt(mProductsCounts.get(item.getID()));
                returnValueUAH += item.getPrice().get("UAH") * Integer.parseInt(mProductsCounts.get(item.getID()));
                if (item.getPrice().get("GBP") != null)
                    returnValueGBP += item.getPrice().get("GBP") * Integer.parseInt(mProductsCounts.get(item.getID()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        Map<String, Double> result = new HashMap<>();
        result.put("USD", returnValueUSD);
        result.put("EUR", returnValueEUR);
        result.put("UAH", returnValueUAH);
        result.put("GBP", returnValueGBP);
        return result;
     */
}