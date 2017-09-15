package llc.net.mydutyfree.response;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by gorf on 1/19/16.
 */
public class Product {
//Products List Keys
    public static final String PRODUCT_ID             =  "product_id";
    public static final String NAME                   =  "name";
    public static final String PRICE                  =  "price";
    public static final String PRICE_OLD              =  "price_old";

    public static final String IMAGES                 =  "images";
    public static final String IS_NEW                 =  "is_new";
    public static final String IS_BEST_OFFER          =  "is_best_offer";
    public static final String IS_TOP                 =  "is_top";
    public static final String IN_WISHLIST            =  "in_wishlist";
    public static final String NO_DISCOUNT            =  "no_discount";
    public static final String TITLE                  =  "title";

    public static final String MAIN_ATTRIBUTE         =  "main_attribute";
    public static final String BRAND                  =  "brand";

    public static final String DESCRIPTION            =  "description";
    public static final String CONSIST                =  "consist";

    public static final String IMAGE_URL              =  "image_url";
    public static final String QUANTITY               =  "quantity";
    public static final String SHORT_ID               =  "id";

    @SerializedName(IMAGE_URL)
    @Expose
    private String imageUrl;

    @SerializedName(SHORT_ID)
    @Expose
    private String shortID;

    @SerializedName(QUANTITY)
    @Expose
    private Number quantity;

    @SerializedName(PRODUCT_ID)
    @Expose
    private String ID;

    @SerializedName(NAME)
    @Expose
    private String name;

    @SerializedName(PRICE)
    @Expose
    private Map<String, Double> price;

    @SerializedName(PRICE_OLD)
    @Expose
    private Map<String, Double> priceOld;

    @SerializedName(IMAGES)
    @Expose
    private ArrayList<String> images;

    @SerializedName(IS_NEW)
    @Expose
    private Object isNew;

    @SerializedName(IS_BEST_OFFER)
    @Expose
    private Object isBestOffer;

    @SerializedName(IS_TOP)
    @Expose
    private Object isTop;

    @SerializedName(MAIN_ATTRIBUTE)
    @Expose
    private String mainAttribute;

    @SerializedName(TITLE)
    @Expose
    private String title;

    @SerializedName(BRAND)
    @Expose
    private Map<String, String> brand;

    @SerializedName(DESCRIPTION)
    @Expose
    private String description;

    @SerializedName(CONSIST)
    @Expose
    private String consist;

    @SerializedName(IN_WISHLIST)
    @Expose
    public Object inWishList;

    @SerializedName(NO_DISCOUNT)
    @Expose
    private Object isNoDiscount;

    @SerializedName("pickup_store")
    @Expose
    private PickUpStoreData pickUpStoreData;

    public String getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getConsist() {
        return consist;
    }

    public Map<String, Double> getPrice() {
        return price;
    }

    public Map<String, Double> getPriceOld() {
        return priceOld;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public Boolean isBestOffer() {
        Boolean returnedValue = false;
        if (this.isBestOffer instanceof Number) {
            returnedValue = ((Number)isBestOffer).intValue() != 0;
        }
        else if (this.isBestOffer instanceof Boolean) {
            returnedValue = (Boolean)isBestOffer;
        }
        return returnedValue;
    }

    public Boolean inWishlist() {
        Boolean returnedValue = false;
        if (this.inWishList instanceof Number) {
            returnedValue = ((Number)inWishList).intValue() != 0;
        }
        else if (this.inWishList instanceof Boolean) {
            returnedValue = (Boolean)inWishList;
        }
        return returnedValue;
    }

    public void setInWishList(Boolean isIn) {
        inWishList = (boolean)isIn;
    }

    public Boolean isNew() {
        Boolean returnedValue = false;
        if (this.isNew instanceof Number) {
            returnedValue = ((Number)isNew).intValue() != 0;
        }
        else if (this.isNew instanceof Boolean) {
            returnedValue = (Boolean)isNew;
        }
        return returnedValue;
    }

    public Boolean isTop() {
        Boolean returnedValue = false;
        if (this.isTop instanceof Number) {
            returnedValue = ((Number)isTop).intValue() != 0;
        }
        else if (this.isTop instanceof Boolean) {
            returnedValue = (Boolean)isTop;
        }
        return returnedValue;
    }

    public Boolean isNoDiscount() {
        Boolean returnedValue = false;
        if (this.isNoDiscount instanceof Number) {
            returnedValue = ((Number)isNoDiscount).intValue() != 0;
        }
        else if (this.isNoDiscount instanceof Boolean) {
            returnedValue = (Boolean)isNoDiscount;
        }
        return returnedValue;
    }

    public String getMainAttribute() {
        return mainAttribute;
    }

    public String getBrandID() {
//        Map<String,String> map = new HashMap<>();
        Map.Entry<String,String> entry = brand.entrySet().iterator().next();
        String key= entry.getKey();
        String value=entry.getValue();
        return key;
    }

    public String getBrandName() {
//        Map<String,String> map=new HashMap<>();
        Map.Entry<String,String> entry=brand.entrySet().iterator().next();
        String key= entry.getKey();
        String value=entry.getValue();
        return value;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getShortID() {
        return shortID;
    }

    public Number getQuantity() {
        return quantity;
    }
//    public Boolean inWishList() {
//        return inWishList;
//    }
//
//    public Boolean isNoDiscount() {
//        return isNoDiscount;
//    }

    public PickUpStoreData getPickUpStoreData() {
        return pickUpStoreData;
    }

    public class PickUpStoreData {
        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("content")
        @Expose
        private String content;

        @SerializedName("image")
        @Expose
        private String image;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getContent() {
            return content;
        }

        public String getImage() {
            return image;
        }
    }

}
