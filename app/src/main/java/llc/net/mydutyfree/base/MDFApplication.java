package llc.net.mydutyfree.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import io.branch.referral.Branch;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.response.GetStores;
import llc.net.mydutyfree.response.Product;

/**
 * Created by gorf on 1/29/16.
 */
public class MDFApplication extends Application {
    private Tracker mTracker;
    private static Context context;
    private static MDFApplication thisApp;
    private static Typeface mBoldFont;
    private static Typeface mNormalFont;
    private TreeMap<String, String> mTempStoresMap;

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Branch.getAutoInstance(this);
//        setUserAuthKey(null);
//        setUserAuthKey("01234567890123456789012345678900");
        MDFApplication.context = getApplicationContext();
        mNormalFont = Typeface.DEFAULT;
        mBoldFont = Typeface.DEFAULT_BOLD;
//        mNormalFont = Typeface.createFromAsset(context.getAssets(), "fonts/SF-UI-Display-Regular.otf");
//        mBoldFont = Typeface.createFromAsset(context.getAssets(), "fonts/SF-UI-Display-Bold.otf");
        thisApp = this;
        CartSingleton.initInstance();

        String localeLanguage = Locale.getDefault().getLanguage();

        String currentLanguage = getCurrentLanguage();
        if (currentLanguage.equalsIgnoreCase("") || currentLanguage == null) {
            currentLanguage = Locale.getDefault().getLanguage();
            if (currentLanguage.equalsIgnoreCase("ru"))
                setCurrentLanguageRus();
            else if (currentLanguage.equalsIgnoreCase("uk"))
                setCurrentLanguageUkr();
            else
                setCurrentLanguageEng();
        }
        else {
            Locale locale = new Locale(currentLanguage);
            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.locale = locale;

            getResources().updateConfiguration(configuration, null);
        }

        String currentCurrency = getCurrentCurrency();
        if (currentCurrency.equalsIgnoreCase("") || currentCurrency == null)
            setCurrentCurrency("EUR");
    }
    public static Context getAppContext() {
        return context;
    }

    public static Typeface getNormalTypeface() {
        return mNormalFont;
    }

    public static Typeface getBoldTypeface() {
        return mBoldFont;
    }

    public static MDFApplication getMDFApplication() {
        return thisApp;
    }

    public String getString(String key) {
        return getSharedPreferences("llc.net.mydutyfree", Context.MODE_PRIVATE).getString(key, "");
    }

    public void setString(String key, String value) {
        getSharedPreferences("llc.net.mydutyfree", Context.MODE_PRIVATE).edit().putString(key, value).commit();
    }

    public Boolean getBoolean(String key) {
        return getSharedPreferences("llc.net.mydutyfree", Context.MODE_PRIVATE).getBoolean(key, false);
    }

    public void setBoolean(String key, Boolean value) {
        getSharedPreferences("llc.net.mydutyfree", Context.MODE_PRIVATE).edit().putBoolean(key, value).commit();
    }

    public int getInteger(String key) {
        return getSharedPreferences("llc.net.mydutyfree", Context.MODE_PRIVATE).getInt(key, 0);
    }

    public void setInteger(String key, int value) {
        getSharedPreferences("llc.net.mydutyfree", Context.MODE_PRIVATE).edit().putInt(key, value).commit();
    }

    public void setMyObject(String key , Object obj) {
        //AnyVehicleModel mvehicle  =new AnyVehicleModel();
        SharedPreferences.Editor editor = getSharedPreferences("llc.net.mydutyfree", Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        editor.putString(key,json);
        editor.apply();
    }

    public void setStores(GetStores stores) {
        //AnyVehicleModel mvehicle  =new AnyVehicleModel();
        SharedPreferences.Editor editor = getSharedPreferences("llc.net.mydutyfree", Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(stores);
        editor.putString("MDFStores",json);
        editor.apply();
    }

    public GetStores getStores() {
        Gson gson = new Gson();
        String json = getSharedPreferences("llc.net.mydutyfree", Context.MODE_PRIVATE).getString("MDFStores","");
        GetStores obj = gson.fromJson(json, GetStores.class);
        if (obj== null){return new GetStores();}
        return obj;

    }

    public HashMap<String, String> getMyObject(String key) {
        Gson gson = new Gson();
        String json = getSharedPreferences("llc.net.mydutyfree", Context.MODE_PRIVATE).getString(key,"");
        HashMap<String, String> obj = gson.fromJson(json, HashMap.class);
        if (obj== null){return new HashMap<> ();}
        return obj;

    }

//    public void setNewArrivals8ItemsJSON(String json)
//    {
//        setString("newArrivalsEightItems", json);
//    }
//
//    public List<Product> getNewArrivals8Items() {
//        ArrayList<Product> arrayList = new ArrayList<>();
//        try {
//            JSONObject json = new JSONObject(getString("newArrivalsEightItems"));
//            JSONArray data = json.getJSONArray("data");
//            for (int i = 0; i < data.length(); i++) {
//                JSONObject product = data.getJSONObject(i);
//                Product item = new Product(product);
//                arrayList.add(item);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return arrayList;
//    }
//
//    public void setTopSellers8ItemsJSON(String json)
//    {
//        setString("topSellersEightItems", json);
//    }
//
//    public List<Product> getTopSellers8ItemsJSON() {
//        ArrayList<Product> arrayList = new ArrayList<>();
//        try {
//            JSONObject json = new JSONObject(getString("topSellersEightItems"));
//            JSONArray data = json.getJSONArray("data");
//            for (int i = 0; i < data.length(); i++) {
//                JSONObject product = data.getJSONObject(i);
//                Product item = new Product(product);
//                arrayList.add(item);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return arrayList;
//    }

    //Airport
    public void setAirport(String airport) {
        setString("airport", airport);
    }

    public String getAirport() {
        return getString("airport");
    }

    public void setIsAirport(Boolean isAirport) {
        setBoolean("isAirport", isAirport);
    }

    public Boolean getIsAirport() {
        return getBoolean("isAirport");
    }

    //Date
    public void setDepartureDate(String departureDate) {
        setString("departureDate", departureDate);
    }

    public String getDepartureDate() {
        return getString("departureDate");
    }

    //Time
    public void setDepartureTime(String departureTime) {
        setString("departureTime", departureTime);
    }

    public String getDepartureTime() {
        return getString("departureTime");
    }

    //FlightNumber
    public void setFlightNumber(String flight) {
        setString("flightNumber", flight);
    }

    public String getFlightNumber() {
        return getString("flightNumber");
    }

    //PanoramaCode
    public void setPanoramaCode(String panoramaCode) {
        setString("panoramaCode", panoramaCode);
    }

    public String getPanoramaCode() {
        return getString("panoramaCode");
    }

    //FirstName
    public void setFirstName(String fName) {
        setString("firstName", fName);
    }

    public String getFirstName() {
        return getString("firstName");
    }

    //LastName
    public void setLastName(String lName) {
        setString("lastName", lName);
    }

    public String getLastName() {
        return getString("lastName");
    }

    //Email
    public void setEmail(String email) {
        setString("email", email);
    }

    public String getEmail() {
        return getString("email");
    }

    //PhoneNumber
    public void setPhoneNumger(String phoneNumber) {
        setString("phoneNumber", phoneNumber);
    }

    public String getPhoneNumber() {
        return getString("phoneNumber");
    }

    //Language
    public void setCurrentLanguage(String currentLanguage) {
        setString("currentLanguage", currentLanguage);
    }

    public void setCurrentLanguageRus() {
        setString("currentLanguage", "ru");
    }

    public void setCurrentLanguageEng() {
        setString("currentLanguage", "en");
    }

    public void setCurrentLanguageUkr() {

        setString("currentLanguage", "uk");
    }

    public String getCurrentLanguage() {
        return getString("currentLanguage");
    }

    //Currency
    public void setCurrentCurrency(String currency) {
        setString("currentCurrency", currency);
    }

    public String getCurrentCurrency() {
        return getString("currentCurrency");
    }

    //Currency symbols
    public void setCurrencySymbols(JSONObject symbols) {
        setString("currencySymbols", symbols.toString());
    }

    public Map<String, String> getCurrencySymbols() {
//        String jsonString = getString("currencySymbols");
//        JSONObject currencySymbolsJSON = null;
//        Map<String, String> result = new HashMap();
//        if (jsonString != null) {
//            try {
//                currencySymbolsJSON = new JSONObject(jsonString);
//                Iterator<String> keysItr = currencySymbolsJSON.keys();
//                while (keysItr.hasNext()) {
//                    String key = keysItr.next();
//                    String value = currencySymbolsJSON.getString(key);
//                    result.put(key, value);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        return getCurrencies();
    }

    public TreeMap<String, String> getStoresMap() {
        return mTempStoresMap;
    }

    public void setStoresMap(TreeMap<String, String> map) {
        mTempStoresMap = map;
    }

    public void clearStoresMap() {
        mTempStoresMap = null;
    }

    public Map<String, String> getCurrencies() {
        Map<String, String> retMap = new HashMap();
        SharedPreferences prefs = getSharedPreferences("currencies", 0);
        for(Map.Entry entry : prefs.getAll().entrySet() )
            retMap.put( entry.getKey().toString(), entry.getValue().toString() );
        return retMap;
    }

    public void setCurrencies(Map<String, String> currenciesMap) {
        SharedPreferences.Editor editor = getSharedPreferences("currencies", 0).edit();
        editor.clear();
        editor.commit();
        for(Map.Entry entry : currenciesMap.entrySet() )
            editor.putString( entry.getKey().toString(), entry.getValue().toString() );
        editor.commit();
    }


    //Languages
    public void setLanguages(JSONObject languages) {
        setString("languages", languages.toString());
    }

    public Map<String, String> getLanguages() {
        String jsonString = getString("languages");
        JSONObject languagesJSON = null;
        Map<String, String> result = new HashMap();
        if (jsonString != null) {
            try {
                languagesJSON = new JSONObject(jsonString);
                Iterator<String> keysItr = languagesJSON.keys();
                while (keysItr.hasNext()) {
                    String key = keysItr.next();
                    String value = languagesJSON.getString(key);
                    result.put(key, value);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Boolean isTutorialShowed() {
        return getBoolean("isTutorialShow");
    }

    public void tutorialShowed() {
        setBoolean("isTutorialShow", true);
    }

    //Email
    public void setUserAuthKey(String userAuthKey) {
        setString("userAuthKey", userAuthKey);
    }

    public String getUserAuthKey() {
        return getString("userAuthKey");
    }


}
