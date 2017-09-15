package llc.net.mydutyfree.utils;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import llc.net.mydutyfree.base.CartSingleton;
import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.fragments.FragmentCategory;
import llc.net.mydutyfree.newmdf.FilterAdapter;
import llc.net.mydutyfree.newmdf.R;

/**
 * Created by gorf on 7/15/16.
 */
public class JsonCreator {

    public static class Orders {
        public static JsonObject GetAll() {
            String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
            String airport = MDFApplication.getMDFApplication().getAirport();
            String userAuthKey = MDFApplication.getMDFApplication().getUserAuthKey();
            if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
                airport = "1";
            JsonObject jsonObject = new JsonObject();
            JsonObject body = new JsonObject();
            jsonObject.addProperty("lang_code", curLanguage);
            jsonObject.addProperty("store_id", airport);
            jsonObject.addProperty("action", Const.Actions.Orders.GET_ALL);
            jsonObject.addProperty("auth_key", Const.API_KEY);
            if (userAuthKey.length() > 0)
                jsonObject.addProperty("user_auth_key", userAuthKey);
            jsonObject.add("body", body);
            return jsonObject;
        }

        public static JsonObject ShowPartnerFieldFor(String partner) {
            String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
            String airport = MDFApplication.getMDFApplication().getAirport();
            String userAuthKey = MDFApplication.getMDFApplication().getUserAuthKey();
            if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
                airport = "1";
            JsonObject jsonObject = new JsonObject();
            JsonObject body = new JsonObject();
            body.addProperty("partner",partner);
            jsonObject.addProperty("lang_code", curLanguage);
            jsonObject.addProperty("store_id", airport);
            jsonObject.addProperty("action", Const.Actions.Orders.NEED_SHOW_PARTNER_FIELD);
            jsonObject.addProperty("auth_key", Const.API_KEY);
            if (userAuthKey.length() > 0)
                jsonObject.addProperty("user_auth_key", userAuthKey);
            jsonObject.add("body", body);
            return jsonObject;
        }

        public static JsonObject GetOne(String orderID) {
            String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
            String airport = MDFApplication.getMDFApplication().getAirport();
            String userAuthKey = MDFApplication.getMDFApplication().getUserAuthKey();
            if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
                airport = "1";
            JsonObject jsonObject = new JsonObject();
            JsonObject body = new JsonObject();
            jsonObject.addProperty("lang_code", curLanguage);
            jsonObject.addProperty("store_id", airport);
            jsonObject.addProperty("action", Const.Actions.Orders.GET_ONE);
            jsonObject.addProperty("auth_key", Const.API_KEY);
            if (userAuthKey.length() > 0)
                jsonObject.addProperty("user_auth_key", userAuthKey);
            body.addProperty("id", orderID);
            jsonObject.add("body", body);
            return jsonObject;
        }
    }

    public static class WishList {

        public static JsonObject GetAll() {
            String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
            String airport = MDFApplication.getMDFApplication().getAirport();
            String userAuthKey = MDFApplication.getMDFApplication().getUserAuthKey();
            if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
                airport = "1";
            JsonObject jsonObject = new JsonObject();
            JsonObject body = new JsonObject();
            jsonObject.addProperty("lang_code", curLanguage);
            jsonObject.addProperty("store_id", airport);
            jsonObject.addProperty("action", Const.Actions.WishList.GET_ALL);
            jsonObject.addProperty("auth_key", Const.API_KEY);
            if (userAuthKey.length() > 0)
                jsonObject.addProperty("user_auth_key", userAuthKey);
            jsonObject.add("body", body);
            return jsonObject;
        }

        public static JsonObject Clear() {
            String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
            String airport = MDFApplication.getMDFApplication().getAirport();
            String userAuthKey = MDFApplication.getMDFApplication().getUserAuthKey();
            if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
                airport = "1";
            JsonObject jsonObject = new JsonObject();
            JsonObject body = new JsonObject();
            jsonObject.addProperty("lang_code", curLanguage);
            jsonObject.addProperty("store_id", airport);
            jsonObject.addProperty("action", Const.Actions.WishList.CLEAR);
            jsonObject.addProperty("auth_key", Const.API_KEY);
            if (userAuthKey.length() > 0)
                jsonObject.addProperty("user_auth_key", userAuthKey);
            jsonObject.add("body", body);
            return jsonObject;
        }

        public static JsonObject AddProductWithID(String productID) {
            String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
            String airport = MDFApplication.getMDFApplication().getAirport();
            String userAuthKey = MDFApplication.getMDFApplication().getUserAuthKey();
            if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
                airport = "1";
            JsonObject jsonObject = new JsonObject();
            JsonObject body = new JsonObject();
            jsonObject.addProperty("lang_code", curLanguage);
            jsonObject.addProperty("store_id", airport);
            jsonObject.addProperty("action", Const.Actions.WishList.ADD_PRODUCT);
            jsonObject.addProperty("auth_key", Const.API_KEY);
            if (userAuthKey.length() > 0)
                jsonObject.addProperty("user_auth_key", userAuthKey);
            body.addProperty("id", productID);
            jsonObject.add("body", body);
            return jsonObject;
        }

        public static JsonObject RemoveProductWithID(String productID) {
            String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
            String airport = MDFApplication.getMDFApplication().getAirport();
            String userAuthKey = MDFApplication.getMDFApplication().getUserAuthKey();
            if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
                airport = "1";
            JsonObject jsonObject = new JsonObject();
            JsonObject body = new JsonObject();
            jsonObject.addProperty("lang_code", curLanguage);
            jsonObject.addProperty("store_id", airport);
            jsonObject.addProperty("action", Const.Actions.WishList.REMOVE_PRODUCT);
            jsonObject.addProperty("auth_key", Const.API_KEY);
            if (userAuthKey.length() > 0)
                jsonObject.addProperty("user_auth_key", userAuthKey);
            body.addProperty("id", productID);
            jsonObject.add("body", body);
            return jsonObject;
        }
    }

    public static JsonObject GetSomeSpecialOffers(int limit, boolean random) {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        JsonObject filtr = new JsonObject();
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Products.GET_FILTERED);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        if (limit > 0)
            filtr.addProperty("limit", limit);
        filtr.addProperty("extra_flag", "sale");
        if (random)
            filtr.addProperty("random", "1");
        body.add("filter", filtr);
        String userAuthKey = MDFApplication.getMDFApplication().getUserAuthKey();
        if (userAuthKey.length() > 10)
            jsonObject.addProperty("user_auth_key", userAuthKey);
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject GetProfileAttributes() {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        String userAuthKey = MDFApplication.getMDFApplication().getUserAuthKey();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Profile.GET_ATTRIBUTES);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        if (userAuthKey.length() > 0)
            jsonObject.addProperty("user_auth_key", userAuthKey);
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject SetProfileAttributes(String fName, String lName, String phone) {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        String userAuthKey = MDFApplication.getMDFApplication().getUserAuthKey();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        JsonObject attributes = new JsonObject();
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Profile.SET_ATTRIBUTES);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        if (userAuthKey.length() > 0)
            jsonObject.addProperty("user_auth_key", userAuthKey);
        attributes.addProperty("first_name", fName);
        attributes.addProperty("last_name", lName);
        attributes.addProperty("phone_number", phone);
        body.add("attributes", attributes);
        jsonObject.add("body", body);
        return jsonObject;
    }

    @Deprecated
    public static JsonObject GetSomeNewArrivals(int limit, boolean random) {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        JsonObject filtr = new JsonObject();
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Products.GET_FILTERED);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        if (limit > 0)
            filtr.addProperty("limit", limit);
        filtr.addProperty("extra_flag", "new");
        if (random)
            filtr.addProperty("random", "1");
        body.add("filter", filtr);
        String userAuthKey = MDFApplication.getMDFApplication().getUserAuthKey();
        if (userAuthKey.length() > 10)
            jsonObject.addProperty("user_auth_key", userAuthKey);
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject GetSomeTopSellers(int limit, boolean random) {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        JsonObject filtr = new JsonObject();
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Products.GET_FILTERED);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        if (limit > 0)
        filtr.addProperty("limit", limit);
        filtr.addProperty("extra_flag", "top");
        if (random)
            filtr.addProperty("random", "1");
        body.add("filter", filtr);
        String userAuthKey = MDFApplication.getMDFApplication().getUserAuthKey();
        if (userAuthKey.length() > 10)
            jsonObject.addProperty("user_auth_key", userAuthKey);
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject GetStores() {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Content.GET_STORES);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject GetStoresAir() {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Content.GET_STORES);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        body.addProperty("type", "airport");
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject GetStoresBorder() {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Content.GET_STORES);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        body.addProperty("type", "border-shop");
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject GetStoreDiscount() {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Content.GET_STORE_DISCOUNT);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject GetLanguages() {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Content.GET_LANGUAGES);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject GetCategories() {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Categories.GET_ALL);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject GetCurrency() {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Content.GET_CURRENCY_LIST);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject GetBanners() {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Content.GET_BANNERS);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject GetBannersAll() {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Content.GET_BANNERS_ALL);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject GetFlights(String date, String time) {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        body.addProperty("time", time);
        body.addProperty("date", date);
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Content.GET_FLIGHTS);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject SaveDeviceToken(String token) {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        body.addProperty("token", token);
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Users.SAVE_DEVICE_TOKEN);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject CheckDisountCode(String code) {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        body.addProperty("code", code);
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Order.CHECK_DISCOUNT_CODE);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject CheckDisountCodeS7(String code, Number amount) {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        body.addProperty("code", code);
        body.addProperty("amount", amount);
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Order.CHECK_DISCOUNT_CODE);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject GetBrandsForCategory(String categoryID) {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        body.addProperty("category_id", categoryID);
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Brands.GET_FOR_CATEGORY);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject SearchWithKeyword(String keyword) {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        body.addProperty("keywords", keyword);
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Products.SEARCH);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        String userAuthKey = MDFApplication.getMDFApplication().getUserAuthKey();
        if (userAuthKey.length() > 10)
            jsonObject.addProperty("user_auth_key", userAuthKey);
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject GetOneProduct(String productID) {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        body.addProperty("id", productID);
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Products.GET_ONE);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        String userAuthKey = MDFApplication.getMDFApplication().getUserAuthKey();
        if (userAuthKey.length() > 10)
            jsonObject.addProperty("user_auth_key", userAuthKey);
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject AccountSignIn(String email, String password) {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        body.addProperty("email", email);
        body.addProperty("password", password);
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Account.SIGN_IN);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        jsonObject.add("body", body);
        Log.e("SignIn Request:",jsonObject.toString());
        return jsonObject;
    }

    public static JsonObject AccountSignUp(String email, String password, Boolean agreeSendEmail) {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        body.addProperty("email", email);
        body.addProperty("password", password);
        if (agreeSendEmail == true)
            body.addProperty("agree", "1");
        else
            body.addProperty("agree", "0");
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Account.SIGN_UP);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject GetFilteredProducts(
            String categoryID,
            String brand,
            String priceFrom,
            String priceTo,
            FilterAdapter.SortBy sortType,
            FragmentCategory.ExtraCategoryType extraCategoryType,
            String limit
    ) {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";
        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        JsonObject filtr = new JsonObject();
        //
        if ((priceFrom != null) && (priceTo != null)) {
            String currentCurrency = MDFApplication.getMDFApplication().getCurrentCurrency();
            JsonObject price = new JsonObject();
            price.addProperty("currency", currentCurrency);
            price.addProperty("from", priceFrom);
            price.addProperty("to", priceTo);
            filtr.add("price", price);
        }
        if (brand != null) {
            JsonArray brandArray = new JsonArray();
            brandArray.add(brand);
            filtr.add("brand_id_list", brandArray);
        }
        if (sortType != FilterAdapter.SortBy.DATE) {
            String order = null;
            if (sortType == FilterAdapter.SortBy.PRICE_UP)
                order = "price.asc";
            else if (sortType == FilterAdapter.SortBy.PRICE_DOWN)
                order = "price.desc";
            filtr.addProperty("order_by", order);
        }

        String nameForAnalytics = null;
        if (extraCategoryType != null) {
            String extraFlag = null;
//            if (extraCategoryType == FragmentCategory.ExtraCategoryType.NEW_ARRIVALS) {
//                extraFlag = "new";
//            }
//            else
            if (extraCategoryType == FragmentCategory.ExtraCategoryType.TOP_SELLERS) {
                extraFlag = "top";
            }
            else if (extraCategoryType == FragmentCategory.ExtraCategoryType.SPECIAL_OFFERS) {
                extraFlag = "sale";
            }
            filtr.addProperty("extra_flag", extraFlag);
        }
        if (categoryID != null) {
            filtr.addProperty("category_id", categoryID);
        }

        if (limit != null)
            filtr.addProperty("limit", limit);
        body.add("filter", filtr);
        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Products.GET_FILTERED);
        jsonObject.addProperty("auth_key", Const.API_KEY);
        String userAuthKey = MDFApplication.getMDFApplication().getUserAuthKey();
        if (userAuthKey.length() > 10)
            jsonObject.addProperty("user_auth_key", userAuthKey);
        jsonObject.add("body", body);
        return jsonObject;
    }

    public static JsonObject OrderCheckout(
            String firstName,
            String lastName,
            String email,
            String phone,
            String discountCode,
            String s7Code,
            String dateTime,
            String flightNumber
            ) {
        String curLanguage = MDFApplication.getMDFApplication().getCurrentLanguage();
        String airport = MDFApplication.getMDFApplication().getAirport();
        String currentCurrency = MDFApplication.getMDFApplication().getCurrentCurrency();

        if ((airport == null) || (airport.equalsIgnoreCase("")) || (airport.equalsIgnoreCase("0")))
            airport = "1";

        JsonObject jsonObject = new JsonObject();
        JsonObject body = new JsonObject();
        JsonArray productsList = new JsonArray();
        Map<String, String> prodQuantities = new HashMap<String, String>(CartSingleton.getInstance().getProductsCounts());
        Iterator iterator = prodQuantities.entrySet().iterator();
        while (iterator.hasNext()) {
            JsonObject productQuantityJsonObject = new JsonObject();
            Map.Entry pair = (Map.Entry)iterator.next();
            productQuantityJsonObject.addProperty("product_id", (String) pair.getKey());
            productQuantityJsonObject.addProperty("product_qty", (String) pair.getValue());
            iterator.remove(); // avoids a ConcurrentModificationException
            productsList.add(productQuantityJsonObject);
        }
        body.add("products_list", productsList);

        JsonObject orderData = new JsonObject();
        if ((discountCode != null) && (!discountCode.equalsIgnoreCase("")))
            orderData.addProperty("discount_code", discountCode);
        if ((s7Code != null) && (!s7Code.equalsIgnoreCase("")))
            orderData.addProperty("card_code", s7Code);

        orderData.addProperty("currency", currentCurrency);
        orderData.addProperty("departure_date", dateTime);

        if ( (flightNumber != null) && (!flightNumber.equalsIgnoreCase("")))
            orderData.addProperty("flight_number", flightNumber);
        else
            orderData.addProperty("flight_number", "--");

        orderData.addProperty("first_name", firstName);
        orderData.addProperty("last_name", lastName);
        orderData.addProperty("email", email);
        orderData.addProperty("phone_number", phone);
        body.add("order_data", orderData);

        jsonObject.addProperty("lang_code", curLanguage);
        jsonObject.addProperty("store_id", airport);
        jsonObject.addProperty("action", Const.Actions.Order.CHECKOUT);
        jsonObject.addProperty("auth_key", Const.API_KEY);

        String userAuthKey = MDFApplication.getMDFApplication().getUserAuthKey();
        if (userAuthKey.length() > 10)
            jsonObject.addProperty("user_auth_key", userAuthKey);
        jsonObject.add("body", body);
        return jsonObject;
    }
}
