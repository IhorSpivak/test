package llc.net.mydutyfree.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gorf on 1/19/16.
 */
public class Order {

    public static class ORDER_STATUS {
        public static String NEW        = "new";
        public static String CONFIRMED  = "confirmed";
        public static String UPDATED    = "updated";
        public static String COLLECTED  = "collected";
        public static String COMPLETED  = "completed";
        public static String CANCELLED  = "cancelled";
        public static String SLAVE      = "slave";
    }

    //Products List Keys
    public static final String ORDER_ID                 =  "id";
    public static final String STORE_ID                 =  "store_id";
    public static final String QUANTITY_POSITIONS       =  "quantity_positions";
    public static final String QUANTITY_ITEMS           =  "quantity_items";
    public static final String FLIGHT_NUMBER            =  "flight_number";
    public static final String DEPARTURE_DATE           =  "departure_date";
    public static final String STATUS                   =  "status";
    public static final String AMOUNT                   =  "amount";
    public static final String AMOUNT_IN_CURRENCY       =  "amount_in_currency";
    public static final String DISCOUNT                 =  "discount";
    public static final String CURRENCY                 =  "currency";
    public static final String PRODUCTS                 =  "products";

    @SerializedName(ORDER_ID)
    @Expose
    private String ID;

    @SerializedName(STORE_ID)
    @Expose
    private String storeID;

    @SerializedName(QUANTITY_POSITIONS)
    @Expose
    private Number quantityPositions;

    @SerializedName(QUANTITY_ITEMS)
    @Expose
    private Number quantityItems;

    @SerializedName(FLIGHT_NUMBER)
    @Expose
    private String flightNumber;

    @SerializedName(DEPARTURE_DATE)
    @Expose
    private String departureDate;

    @SerializedName(STATUS)
    @Expose
    private String orderStatus;

    @SerializedName(AMOUNT)
    @Expose
    private Number amount;

    @SerializedName(AMOUNT_IN_CURRENCY)
    @Expose
    private Number amountInCurrency;

    @SerializedName(DISCOUNT)
    @Expose
    private Number dicount;

    @SerializedName(CURRENCY)
    @Expose
    private String currency;

    @SerializedName(PRODUCTS)
    @Expose
    private List<Product> products;

    public String getID() {
        return ID;
    }

    public String getStoreID() {
        return storeID;
    }

    public Number getQuantityPositions() {
        return quantityPositions;
    }

    public Number getQuantityItems() {
        return quantityItems;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public Number getAmount() {
        return amount;
    }

    public Number getAmountInCurrency() {
        return amountInCurrency;
    }

    public Number getDicount() {
        return dicount;
    }

    public String getCurrency() {
        return currency;
    }

    public List<Product> getProducts() {
        return products;
    }
}
