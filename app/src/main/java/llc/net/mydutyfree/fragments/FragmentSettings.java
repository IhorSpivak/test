package llc.net.mydutyfree.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import llc.net.mydutyfree.adapters.StoreListSettingsAdapter;
import llc.net.mydutyfree.base.MDFProgressDialog;
import llc.net.mydutyfree.base.CartSingleton;
import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.base.RetrofitService;
import llc.net.mydutyfree.interfaces.PostInterfaceApi;
import llc.net.mydutyfree.newmdf.MainActivity;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.response.GetLanguagesResponse;
import llc.net.mydutyfree.response.GetStores;
import llc.net.mydutyfree.utils.Const;
import llc.net.mydutyfree.utils.FontUtils;
import llc.net.mydutyfree.utils.JsonCreator;
import llc.net.mydutyfree.utils.Utils;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gorf on 11/27/15.
 */
public class FragmentSettings extends Fragment {
    protected MDFProgressDialog mProgressDialog;
    Button btnRus, btnUkr, btnEng, btnTermsAndConditions, btnImprint, btnCustomerSupport;
    Button btnUSD, btnEUR, btnUAH;
    Spinner spinnerCurrencies, //spinnerAirport,
            spinnerLanguage;
    Button btnAirport;
    Map<String, String> currencySymbolsList;
    Boolean isAirportChange, isCurrencyChange, isLanguageChange, isFirstTouch;
    ArrayAdapter<String> adapterCurrencies, adapterLanguages;

    ArrayList<String> mLanguagesCodesList;
    ArrayList<String> mLanguagesNamesList;
    Map<String, String> mLanguagesMap;
    ArrayList<String> mStoreNamesAir;
    ArrayList<String> mStoreNamesBorder;
    ArrayList<String> mStoreCodesAir;
    ArrayList<String> mStoreCodesBorder;
    GetStores mResponseAirports, mResponseBorderShops;

//    WrapContentHeightViewPager pager;
//    CirclePageIndicator indicator;

    TextView txtCurrency, txtLanguage, txtAirport;

    int languageSelected;
    int currencySelected;
    String curLanguage;
    String curCurrency;
    String curStore;
    private Tracker mTracker;

    public FragmentSettings() {
        isAirportChange = false;
        isCurrencyChange = false;
        isLanguageChange = false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    private void updateAirportSpinner() {
//////
        curLanguage = ((MDFApplication) getActivity().getApplication()).getCurrentLanguage();
        curStore = ((MDFApplication) getActivity().getApplication()).getAirport();
//        String title = getResources().getString(R.string.select_airport);
//        title = title.substring(0,1).toUpperCase() + title.substring(1).toLowerCase();

        RetrofitService retrofitService = new RetrofitService();
        PostInterfaceApi service = retrofitService.create();
        Call<GetStores> callStoresAir = service.getStores(JsonCreator.GetStoresAir());
        ((MainActivity)getActivity()).showProgress();
        callStoresAir.enqueue(new Callback<GetStores>() {
            @Override
            public void onResponse(Call<GetStores> call, Response<GetStores> response) {
                Log.e("!!!", "!!!");
                ((MainActivity)getActivity()).hideProgress();
                Map<String, Map<String, String>> mapStores = response.body().getData();
                mResponseAirports = response.body();

                // Convert Map to List
                List<Map.Entry<String, Map<String, String>>> list = new LinkedList<>(mapStores.entrySet());

                // Sort list with comparator, to compare the Map values
                Collections.sort(list, new Comparator<Map.Entry<String, Map<String, String>>>() {
                    public int compare(Map.Entry<String, Map<String, String>> o1,
                                       Map.Entry<String, Map<String, String>> o2) {
                        return (o1.getKey()).compareTo(o2.getKey());
                    }
                });
                mStoreNamesAir = new ArrayList<String>();
                mStoreCodesAir = new ArrayList<String>();
                for (Iterator<Map.Entry<String, Map<String, String>>> it = list.iterator(); it.hasNext(); ) {
                    Map.Entry<String, Map<String, String>> entry = it.next();
                    Map<String, String> namesStore = entry.getValue();
                    if (namesStore.get(curLanguage) != null) {
                        mStoreNamesAir.add(namesStore.get(curLanguage));
                        mStoreCodesAir.add(entry.getKey());
                        Log.e("!!!", "!!!");
                    } else {
                        Log.e("!!!", "!!!");
                    }
                }

                RetrofitService retrofitService = new RetrofitService();
                PostInterfaceApi service = retrofitService.create();
                Call<GetStores> callStoresAir = service.getStores(JsonCreator.GetStoresBorder());
                ((MainActivity)getActivity()).showProgress();
                callStoresAir.enqueue(new Callback<GetStores>() {
                    @Override
                    public void onResponse(Call<GetStores> call, Response<GetStores> response) {
                        Log.e("!!!", "!!!");
                        ((MainActivity)getActivity()).hideProgress();
                        Map<String, Map<String, String>> mapStores = response.body().getData();
                        mResponseBorderShops = response.body();

                        // Convert Map to List
                        List<Map.Entry<String, Map<String, String>>> list = new LinkedList<>(mapStores.entrySet());

                        // Sort list with comparator, to compare the Map values
                        Collections.sort(list, new Comparator<Map.Entry<String, Map<String, String>>>() {
                            public int compare(Map.Entry<String, Map<String, String>> o1,
                                               Map.Entry<String, Map<String, String>> o2) {
                                return (o1.getKey()).compareTo(o2.getKey());
                            }
                        });
                        mStoreNamesBorder = new ArrayList<String>();
                        mStoreCodesBorder = new ArrayList<String>();
                        for (Iterator<Map.Entry<String, Map<String, String>>> it = list.iterator(); it.hasNext(); ) {
                            Map.Entry<String, Map<String, String>> entry = it.next();
                            Map<String, String> namesStore = entry.getValue();
                            if (namesStore.get(curLanguage) != null) {
                                mStoreNamesBorder.add(namesStore.get(curLanguage));
                                mStoreCodesBorder.add(entry.getKey());
                                Log.e("!!!", "!!!");
                            } else {
                                Log.e("!!!", "!!!");
                            }
                        }
                        if (mStoreCodesAir.contains(curStore)) {
                            btnAirport.setTextColor(ContextCompat.getColor(MDFApplication.getAppContext(), R.color.colorBlack));
                            btnAirport.setText(mStoreNamesAir.get(mStoreCodesAir.indexOf(curStore)));
                        }
                        else if (mStoreCodesBorder.contains(curStore)) {
                            btnAirport.setTextColor(ContextCompat.getColor(MDFApplication.getAppContext(), R.color.colorBlack));
                            btnAirport.setText(mStoreNamesBorder.get(mStoreCodesBorder.indexOf(curStore)));
                        }
                        else {
                            btnAirport.setTextColor(ContextCompat.getColor(MDFApplication.getAppContext(), R.color.colorRed));
                            btnAirport.setText(getString(R.string.select_store));
                        }
                    }

                    @Override
                    public void onFailure(Call<GetStores> call, Throwable t) {
                        Log.e("!!!", "error!!!");
                        ((MainActivity)getActivity()).hideProgress();
                    }
                });
            }

            @Override
            public void onFailure(Call<GetStores> call, Throwable t) {
                Log.e("!!!", "error!!!");
                ((MainActivity)getActivity()).hideProgress();
            }
        });

    }

    private void updateLangButtons() {
        Locale locale2 = new Locale(curLanguage);
        Locale.setDefault(locale2);
        Configuration config2 = new Configuration();
        config2.locale = locale2;

        getContext().getResources().updateConfiguration(config2, null);

        btnTermsAndConditions.setText(R.string.terms_and_conditions);
        btnImprint.setText(R.string.imprint);
        btnCustomerSupport.setText(R.string.customer_support);

        txtCurrency.setText(getResources().getString(R.string.currency));
        txtLanguage.setText(getResources().getString(R.string.language));
        txtAirport.setText(getResources().getString(R.string.airport));
        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.settings_caps));

        if (curLanguage.equalsIgnoreCase("en"))
        {
            ((MDFApplication)getActivity().getApplication()).setCurrentLanguageEng();
            btnEng.setPressed(true);
            btnRus.setPressed(false);
            btnUkr.setPressed(false);
            btnEng.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
            btnRus.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
            btnUkr.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
        }
        else if (curLanguage.equalsIgnoreCase("ru"))
        {
            ((MDFApplication)getActivity().getApplication()).setCurrentLanguageRus();
            btnEng.setPressed(false);
            btnRus.setPressed(true);
            btnUkr.setPressed(false);
            btnEng.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
            btnRus.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
            btnUkr.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
        }
        else if (curLanguage.equalsIgnoreCase("uk"))
        {
            ((MDFApplication)getActivity().getApplication()).setCurrentLanguageUkr();
            btnEng.setPressed(false);
            btnRus.setPressed(false);
            btnUkr.setPressed(true);
            btnEng.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
            btnRus.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
            btnUkr.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
        }

        if (isLanguageChange) {
            isLanguageChange = false;
//            adapterAirports.
            updateAirportSpinner();
            ((MainActivity) getActivity()).updateCategoriesMenu();
        }
    }

    private void updateCurrencyButtons() {

        if (curCurrency.equalsIgnoreCase("UAH"))
        {
            ((MDFApplication)getActivity().getApplication()).setCurrentCurrency("UAH");
            btnUAH.setPressed(true);
            btnEUR.setPressed(false);
            btnUSD.setPressed(false);
        }
        else if (curCurrency.equalsIgnoreCase("EUR"))
        {
            ((MDFApplication)getActivity().getApplication()).setCurrentCurrency("EUR");
            btnEUR.setPressed(true);
            btnUSD.setPressed(false);
            btnUAH.setPressed(false);
        }
        else if (curCurrency.equalsIgnoreCase("USD"))
        {
            ((MDFApplication)getActivity().getApplication()).setCurrentCurrency("USD");
            btnUSD.setPressed(true);
            btnEUR.setPressed(false);
            btnUAH.setPressed(false);

        }

    }

//    @Override
//    public void onDestroyView() {
//        spinnerAirport.setOnItemSelectedListener(null);
//        spinnerAirport.setAdapter(null);
////        spinnerAirport.setSelection(2);
//        super.onDestroyView();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.screen_settings, container, false);


//        pager = (WrapContentHeightViewPager) rootView.findViewById(R.id.viewPager);
//        indicator = (CirclePageIndicator) rootView.findViewById(R.id.pageIndicator);
//        indicator.setPageColor(Color.RED);
//        indicator.setFillColor(Color.CYAN);
//        indicator.setRadius(20);
//        indicator.setStrokeColor(Color.BLACK);
//        indicator.setStrokeWidth(1.0f);

        btnEng = (Button)rootView.findViewById(R.id.btnEngSettings);
        btnRus = (Button)rootView.findViewById(R.id.btnRusSettings);
        btnUkr = (Button)rootView.findViewById(R.id.btnUkrSettings);

        btnUSD = (Button)rootView.findViewById(R.id.btnUSDSettings);
        btnEUR = (Button)rootView.findViewById(R.id.btnEURSettings);
        btnUAH = (Button)rootView.findViewById(R.id.btnUAHSettings);

        btnTermsAndConditions = (Button)rootView.findViewById(R.id.btnTermsAndConditionsSettingsScreen);
        btnImprint = (Button)rootView.findViewById(R.id.btnImprintSettingsScreen);
        btnCustomerSupport = (Button)rootView.findViewById(R.id.btnCustomerSupportSettingsScreen);

        txtCurrency = (TextView)rootView.findViewById(R.id.txtCurrencySettings);
        txtLanguage = (TextView)rootView.findViewById(R.id.txtLanguageSettings);
        txtAirport = (TextView)rootView.findViewById(R.id.txtAirportSettings);

        spinnerCurrencies = (Spinner) rootView.findViewById(R.id.spinnerCurrencySettingsScreen);
//        spinnerAirport = (Spinner) rootView.findViewById(R.id.spinnerAirportSettingsScreen);
        spinnerLanguage = (Spinner) rootView.findViewById(R.id.spinnerLanguagesSettingsScreen);
        btnAirport = (Button) rootView.findViewById(R.id.spinnerAirportSettingsScreen);
        btnAirport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentSelectAirport();
                ((FragmentSelectAirport) fragment).setLists(mStoreNamesAir, mStoreCodesAir, mStoreNamesBorder, mStoreCodesBorder);
                ((FragmentSelectAirport) fragment).setResponses(mResponseAirports, mResponseBorderShops);
                if (fragment != null) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                    transaction.replace(R.id.content_frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        setHasOptionsMenu(true);

        mTracker = ((MDFApplication)getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName("Settings");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        final ArrayList<String> mCurrencies = new ArrayList<>();

        curLanguage = ((MDFApplication) getActivity().getApplication()).getCurrentLanguage();
        updateLangButtons();

        curCurrency = ((MDFApplication)getActivity().getApplication()).getCurrentCurrency();
//        updateCurrencyButtons();

        final ArrayList<String> mLanguages = new ArrayList<>();
        mLanguages.add("RU");
        mLanguages.add("EN");
        mLanguages.add("UA");

        currencySymbolsList = ((MDFApplication)getActivity().getApplication()).getCurrencySymbols();

        final String currentAirport = ((MDFApplication)getActivity().getApplication()).getAirport();
        Iterator it = currencySymbolsList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
//            System.out.println(pair.getKey() + " = " + pair.getValue());
            mCurrencies.add((String)pair.getKey());
            it.remove(); // avoids a ConcurrentModificationException
        }

        RetrofitService retrofitService = new RetrofitService();
        PostInterfaceApi service = retrofitService.create();
        Call<GetLanguagesResponse> call = service.getLanguages(JsonCreator.GetLanguages());
        ((MainActivity)getActivity()).showProgress();
        call.enqueue(new Callback<GetLanguagesResponse>() {
            @Override
            public void onResponse(Call<GetLanguagesResponse> call, Response<GetLanguagesResponse> response) {
                Log.e("!!!", "!!!");

                mLanguagesMap = response.body().getData();
                // Convert Map to List
                List<Map.Entry<String, String>> list = new LinkedList<Map.Entry<String, String>>(mLanguagesMap.entrySet());

                // Sort list with comparator, to compare the Map values
                Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
                    public int compare(Map.Entry<String, String> o1,
                                       Map.Entry<String, String> o2) {
                        return (o1.getValue()).compareTo(o2.getValue());
                    }
                });

                // Convert sorted map back to a Map
                Map<String, String> sortedMap = new LinkedHashMap<String, String>();
                mLanguagesCodesList = new ArrayList<>();
                mLanguagesNamesList = new ArrayList<>();
                for (Iterator<Map.Entry<String, String>> it = list.iterator(); it.hasNext();) {
                    Map.Entry<String, String> entry = it.next();
                    mLanguagesCodesList.add(entry.getKey());
                    mLanguagesNamesList.add(entry.getValue());
                }

                adapterLanguages = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mLanguagesNamesList)  {
                    @Override
                    public View getView(int position, View convertView,ViewGroup parent) {
                        View v = super.getView(position, convertView, parent);
                        ((TextView) v).setTextSize(16);
                        FontUtils.setNormalFont(getContext(), ((TextView) v));
                        ((TextView) v).setGravity(Gravity.CENTER);
                        return v;
                    }
                    @Override
                    public View getDropDownView(int position, View convertView,ViewGroup parent) {
                        View v = super.getDropDownView(position, convertView,parent);
                        ((TextView) v).setGravity(Gravity.CENTER);
                        FontUtils.setNormalFont(getContext(), ((TextView) v));
                        return v;
                    }
                };
                adapterLanguages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerLanguage.setAdapter(adapterLanguages);
                spinnerLanguage.setSelection(mLanguagesCodesList.indexOf(curLanguage));
                spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int index, long id) {
                        Log.e("!!!", "!!!");
                        ((MDFApplication)getActivity().getApplication()).setCurrentLanguage(mLanguagesCodesList.get(index));
                        if (!curLanguage.equalsIgnoreCase(mLanguagesCodesList.get(index))) {
                            isLanguageChange = true;
                            curLanguage = mLanguagesCodesList.get(index);
                            updateLangButtons();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
//                ((MDFApplication)getApplication()).setLanguages(response.body().getData());
                ((MainActivity)getActivity()).hideProgress();
            }

            @Override
            public void onFailure(Call<GetLanguagesResponse> call, Throwable t) {
                Log.e("!!!", "error!!!");
                ((MainActivity)getActivity()).hideProgress();
            }
        });

        adapterCurrencies = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mCurrencies)  {
            @Override
            public View getView(int position, View convertView,ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextSize(16);
                FontUtils.setNormalFont(getContext(), ((TextView) v));
                ((TextView) v).setGravity(Gravity.CENTER);
                return v;
            }
            @Override
            public View getDropDownView(int position, View convertView,ViewGroup parent) {
                View v = super.getDropDownView(position, convertView,parent);
                ((TextView) v).setGravity(Gravity.CENTER);
                FontUtils.setNormalFont(getContext(), ((TextView) v));
                return v;
            }
        };

        adapterCurrencies.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerCurrencies.setAdapter(adapterCurrencies);

        updateAirportSpinner();

        isFirstTouch = true;

        spinnerCurrencies.setSelection(mCurrencies.indexOf(curCurrency));

        spinnerCurrencies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (!(curCurrency.equalsIgnoreCase(mCurrencies.get(position)))) {
                    ((MDFApplication) getActivity().getApplication()).setCurrentCurrency(mCurrencies.get(position));
                    AlertDialog.Builder alertCurrencies = new AlertDialog.Builder(getContext());
                    alertCurrencies.setCancelable(false);
                    alertCurrencies.setNegativeButton(getResources().getString(R.string.ok_caps), null);
                    TextView message = new TextView(getContext());
                    message.setText(getResources().getString(R.string.currency_alert));
                    message.setGravity(Gravity.CENTER_HORIZONTAL);
                    message.setPadding(
                            (int) Utils.dpToPx(10),
                            (int) Utils.dpToPx(10),
                            (int) Utils.dpToPx(10),
                            (int) Utils.dpToPx(10)
                    );
                    FontUtils.setBoldFont(getContext(), message);
                    alertCurrencies.setView(message);
                    alertCurrencies.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        btnTermsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentWebView();
                ((FragmentWebView) fragment).setContentType("terms-and-conditions");
                if (fragment != null) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                    transaction.replace(R.id.content_frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        btnImprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentWebView();
                ((FragmentWebView) fragment).setContentType("imprint");
                if (fragment != null) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                    transaction.replace(R.id.content_frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        btnCustomerSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentCustomerSupport();
                if (fragment != null) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                    transaction.replace(R.id.content_frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

//        btnEng.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (!curLanguage.equalsIgnoreCase("en")) {
//                    isLanguageChange = true;
//                    curLanguage = "en";
//                    updateLangButtons();
//                }
//                return true;
//            }
//        });
//
//        btnRus.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (!curLanguage.equalsIgnoreCase("ru")) {
//                    isLanguageChange = true;
//                    curLanguage = "ru";
//                    updateLangButtons();
//                }
//                return true;
//            }
//        });
//
//        btnUkr.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (!curLanguage.equalsIgnoreCase("uk")) {
//                    isLanguageChange = true;
//                    curLanguage = "uk";
//                    updateLangButtons();
//                }
//                return true;
//            }
//        });
//
//        btnEUR.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                curCurrency = "EUR";
//                updateCurrencyButtons();
//                return true;
//            }
//        });
//
//        btnUSD.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                curCurrency = "USD";
//                updateCurrencyButtons();
//                return true;
//            }
//        });
//
//        btnUAH.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                curCurrency = "UAH";
//                updateCurrencyButtons();
//                return true;
//            }
//        });
        //!!!!!!!


        return rootView;
    }

}
