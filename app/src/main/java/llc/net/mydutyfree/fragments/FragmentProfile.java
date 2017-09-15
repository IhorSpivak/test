package llc.net.mydutyfree.fragments;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcelable;
import android.support.design.widget.TextInputLayout;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import llc.net.mydutyfree.base.MDFProgressDialog;
import llc.net.mydutyfree.base.CartSingleton;
import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.base.RetrofitService;
import llc.net.mydutyfree.controls.RoundedImageView;
import llc.net.mydutyfree.interfaces.PostInterfaceApi;
import llc.net.mydutyfree.newmdf.FullScreenImageActivity;
import llc.net.mydutyfree.newmdf.MainActivity;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.response.AccountSignIn;
import llc.net.mydutyfree.response.GetLanguagesResponse;
import llc.net.mydutyfree.response.GetStores;
import llc.net.mydutyfree.response.Profile;
import llc.net.mydutyfree.response.ProfileGetAttributes;
import llc.net.mydutyfree.response.ProfileSetAttributes;
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
 * Created by gorf on 12/14/16.
 */
public class FragmentProfile extends Fragment {
    private Button btnLogOut;
    private EditText edtFirstName, edtLastName, edtPhone, edtEmail;
    private TextInputLayout tilFirstName, tilLastName, tilEmail, tilPhone;
    private ImageButton imgBtnWishList, imgBtnOrdersHistory;
    private TextView txtYourData;
    private RoundedImageView imgMain;
    private String mFirstName, mLastName, mPhone;
    private Tracker mTracker;

    public FragmentProfile() {
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

//    private void updateAirportSpinner() {
//        RetrofitService retrofitService = new RetrofitService();
//        PostInterfaceApi service = retrofitService.create();
//
//        Call<GetStores> callStores = service.getStores(JsonCreator.GetStores());
//        ((MainActivity)getActivity()).showProgress();
//        callStores.enqueue(new Callback<GetStores>() {
//            @Override
//            public void onResponse(Call<GetStores> call, Response<GetStores> response) {
//                Log.e("!!!", "!!!");
//                ((MainActivity)getActivity()).hideProgress();
//                Map<String, Map<String, String>> mapStores = response.body().getData();
//
//                // Convert Map to L
// ist
//                List<Map.Entry<String, Map<String, String>>> list = new LinkedList<>(mapStores.entrySet());
//
//                // Sort list with comparator, to compare the Map values
//                Collections.sort(list, new Comparator<Map.Entry<String, Map<String, String>>>() {
//                    public int compare(Map.Entry<String, Map<String, String>> o1,
//                                       Map.Entry<String, Map<String, String>> o2) {
//                        return (o1.getKey()).compareTo(o2.getKey());
//                    }
//                });
//                mStoreNames = new ArrayList<String>();
//                for (Iterator<Map.Entry<String, Map<String, String>>> it = list.iterator(); it.hasNext();) {
//                    Map.Entry<String, Map<String, String>> entry = it.next();
//                    Map<String, String> namesStore = entry.getValue();
//                    if (namesStore.get(curLanguage) != null) {
//                        mStoreNames.add(namesStore.get(curLanguage));
//                        Log.e("!!!", "!!!");
//                    }
//                    else {
//                        Log.e("!!!", "!!!");
//                    }
//                }
//                final String currentAirport = ((MDFApplication)getActivity().getApplication()).getAirport();
////        final String[] strAirports = getResources().getStringArray(R.array.airports_array);
//                final String[] strAirports = mStoreNames.toArray(new String[0]);
//                final ArrayList<String> mAirports = new ArrayList<String>(Arrays.asList(strAirports));
//                mAirports.add(getContext().getResources().getString(R.string.select_airport));
//
//                adapterAirports = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mAirports)  {
//                    @Override
//                    public View getView(int position, View convertView,ViewGroup parent) {
//                        View v = super.getView(position, convertView, parent);
//                        ((TextView) v).setTextSize(16);
//                        FontUtils.setNormalFont(getContext(), ((TextView) v));
//                        ((TextView) v).setGravity(Gravity.CENTER);
//                        if (position == getCount()) {
//                            ((TextView)v.findViewById(android.R.id.text1)).setText("");
//                            ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
//                        }
//                        return v;
//                    }
//                    @Override
//                    public View getDropDownView(int position, View convertView,ViewGroup parent) {
//                        View v = super.getDropDownView(position, convertView, parent);
//                        ((TextView) v).setGravity(Gravity.CENTER);
//                        FontUtils.setNormalFont(getContext(), ((TextView) v));
//                        return v;
//                    }
//
//                    @Override
//                    public int getCount() {
//                        return mAirports.size() - 1; // you dont display last item. It is used as hint.
//                    }
//                };
//                spinnerAirport.setAdapter(adapterAirports);
//                adapterAirports.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinnerAirport.setOnItemSelectedListener(null);
//                spinnerAirport.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (currentAirport.equalsIgnoreCase("0"))
//                            spinnerAirport.setSelection(mAirports.size() - 1);
//                        else
//                            spinnerAirport.setSelection((Integer.parseInt(currentAirport) - 1));
//                        spinnerAirport.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                spinnerAirport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                    @Override
//                                    public void onItemSelected(AdapterView<?> parent, View view,
//                                                               int position, long id) {
//                                        if ((!currentAirport.equalsIgnoreCase(String.format("%d", position + 1))) && (position != mAirports.size() - 1)) {
//                                            isAirportChange = true;
//                                            ((MDFApplication) getActivity().getApplication()).setAirport(String.format("%d", position + 1));
//                                        }
//
//                                        if (isAirportChange && view != null) {
//                                            isAirportChange = false;
//                                            if (String.format("%d", position + 1).equalsIgnoreCase("3") ) {
//                                                if (curLanguage.equalsIgnoreCase("uk")) {
//                                                    curLanguage = "ru";
//                                                    ((MDFApplication) getActivity().getApplication()).setCurrentLanguageRus();
//                                                    Locale locale2 = new Locale(curLanguage);
//                                                    Locale.setDefault(locale2);
//                                                    Configuration config2 = new Configuration();
//                                                    config2.locale = locale2;
//                                                    getActivity().getResources().updateConfiguration(config2, null);
//                                                }
//                                            }
//                                            ///!!!!!!!!!
//                                            RetrofitService retrofitService = new RetrofitService();
//                                            PostInterfaceApi service = retrofitService.create();
//                                            Call<GetLanguagesResponse> call = service.getLanguages(JsonCreator.GetLanguages());
//                                            ((MainActivity)getActivity()).showProgress();
//                                            call.enqueue(new Callback<GetLanguagesResponse>() {
//                                                @Override
//                                                public void onResponse(Call<GetLanguagesResponse> call, Response<GetLanguagesResponse> response) {
//                                                    Log.e("!!!", "!!!");
//
//                                                    mLanguagesMap = response.body().getData();
//                                                    // Convert Map to List
//                                                    List<Map.Entry<String, String>> list = new LinkedList<Map.Entry<String, String>>(mLanguagesMap.entrySet());
//
//                                                    // Sort list with comparator, to compare the Map values
//                                                    Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
//                                                        public int compare(Map.Entry<String, String> o1,
//                                                                           Map.Entry<String, String> o2) {
//                                                            return (o1.getValue()).compareTo(o2.getValue());
//                                                        }
//                                                    });
//
//                                                    // Convert sorted map back to a Map
//                                                    Map<String, String> sortedMap = new LinkedHashMap<String, String>();
//                                                    mLanguagesCodesList = new ArrayList<>();
//                                                    mLanguagesNamesList = new ArrayList<>();
//                                                    for (Iterator<Map.Entry<String, String>> it = list.iterator(); it.hasNext();) {
//                                                        Map.Entry<String, String> entry = it.next();
//                                                        mLanguagesCodesList.add(entry.getKey());
//                                                        mLanguagesNamesList.add(entry.getValue());
//                                                    }
//
//                                                    if (!mLanguagesCodesList.contains(curLanguage)) {
//                                                        curLanguage = "ru";
//                                                        ((MDFApplication) getActivity().getApplication()).setCurrentLanguage(curLanguage);
//                                                        Locale locale2 = new Locale(curLanguage);
//                                                        Locale.setDefault(locale2);
//                                                        Configuration config2 = new Configuration();
//                                                        config2.locale = locale2;
//                                                        getActivity().getResources().updateConfiguration(config2, null);
//                                                    }
//
//                                                    ((MainActivity)getActivity()).hideProgress();
//                                                    CartSingleton.getInstance().ClearCart();
//                                                    getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                                                    Fragment fragment = new FragmentHome();
//                                                    if (fragment != null) {
//                                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                                                        FragmentTransaction transaction = fragmentManager.beginTransaction();
//                                                        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
//                                                        transaction.replace(R.id.content_frame, fragment);
//                                                        transaction.addToBackStack(null);
//                                                        transaction.commit();
//                                                    }
//                                                }
//                                                @Override
//                                                public void onFailure(Call<GetLanguagesResponse> call, Throwable t) {
//                                                    Log.e("!!!", "error!!!");
//                                                    ((MainActivity)getActivity()).hideProgress();
//                                                }
//                                            });
//                                            ///!!!!!!!!!
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onNothingSelected(AdapterView<?> arg0) {
//
//                                    }
//                                });
//
//                            }
//                        });
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<GetStores> call, Throwable t) {
//                Log.e("!!!", "error!!!");
//                ((MainActivity)getActivity()).hideProgress();
//            }
//        });
//        //
//    }

    private void saveAttrs() {
        RetrofitService retrofitService = new RetrofitService(true);
        PostInterfaceApi service = retrofitService.create();
        Call<ProfileSetAttributes> call = service.profileSetAttributes(JsonCreator.SetProfileAttributes(
                edtFirstName.getText().toString(),
                edtLastName.getText().toString(),
                edtPhone.getText().toString()
                ));
        ((MainActivity)getActivity()).showProgress();
        call.enqueue(new Callback<ProfileSetAttributes>() {
            @Override
            public void onResponse(Call<ProfileSetAttributes> call, Response<ProfileSetAttributes> response) {
                Log.e("!!!", "!!!");
                ((MainActivity)getActivity()).hideProgress();
                if (response.body().getStatus().equalsIgnoreCase(Const.Actions.Status.SUCCESS)) {
                    Log.e("!!!", "!!!");
                }
                else {
                    Log.e("!!!", "!!!");
                }
            }
            @Override
            public void onFailure(Call<ProfileSetAttributes> call, Throwable t) {
                Log.e("!!!", "error!!!");
                ((MainActivity)getActivity()).hideProgress();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.screen_profile, container, false);

        final Fragment f = this;

        mTracker = ((MDFApplication)getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName("Profile");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        btnLogOut = (Button) rootView.findViewById(R.id.btnLogOutScreenProfile);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MDFApplication)(getActivity().getApplication())).setUserAuthKey("");
                ((MDFApplication)(getActivity().getApplication())).setEmail("");
                ((MDFApplication)(getActivity().getApplication())).setFirstName("");
                ((MDFApplication)(getActivity().getApplication())).setLastName("");
                ((MDFApplication)(getActivity().getApplication())).setPhoneNumger("");

                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove(f);
                trans.commit();
                manager.popBackStack();
            }
        });
        edtEmail = (EditText) rootView.findViewById(R.id.edtEmailScreenProfile);
        edtFirstName = (EditText) rootView.findViewById(R.id.edtFirstNameScreenProfile);
        edtLastName = (EditText) rootView.findViewById(R.id.edtLastNameScreenProfile);
        edtPhone = (EditText) rootView.findViewById(R.id.edtPhoneScreenProfile);
        imgMain = (RoundedImageView) rootView.findViewById(R.id.imgMainProfileScreenProfile);
        imgBtnWishList = (ImageButton) rootView.findViewById(R.id.imgBtnWishListScreenProfile);
        imgBtnOrdersHistory = (ImageButton) rootView.findViewById(R.id.imgBtnOrdersListScreenProfile);

        imgBtnWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentWishList();
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

        imgBtnOrdersHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentOrdersHistory();
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

        edtFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!mFirstName.equalsIgnoreCase(edtFirstName.getText().toString())) {
                        saveAttrs();
                        mFirstName = edtFirstName.getText().toString();
                    }
                }
            }
        });
        edtLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!mLastName.equalsIgnoreCase(edtLastName.getText().toString())) {
                        saveAttrs();
                        mLastName = edtLastName.getText().toString();
                    }
                }
            }
        });
        edtPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!mPhone.equalsIgnoreCase(edtPhone.getText().toString())) {
                        saveAttrs();
                        mPhone = edtPhone.getText().toString();
                    }
                }
            }
        });

        String userAuthKey = ((MDFApplication)(getActivity().getApplication())).getUserAuthKey();

        if (userAuthKey.length() > 10) {
            RetrofitService retrofitService = new RetrofitService();
            PostInterfaceApi service = retrofitService.create();
            Call<ProfileGetAttributes> call = service.profileGetAttributes(JsonCreator.GetProfileAttributes());
            ((MainActivity)getActivity()).showProgress();
            call.enqueue(new Callback<ProfileGetAttributes>() {
                 @Override
                public void onResponse(Call<ProfileGetAttributes> call, Response<ProfileGetAttributes> response) {
                    Log.e("!!!", "!!!");
                    ((MainActivity)getActivity()).hideProgress();
                    if (response.body().getStatus().equalsIgnoreCase(Const.Actions.Status.SUCCESS)) {
                        Log.e("!!!", "!!!");
                        final Profile profile = response.body().getData();
                        if (profile != null) {
                            String email = ((MDFApplication) (getActivity().getApplication())).getEmail();
                            edtEmail.setText(email);
                            edtEmail.setEnabled(false);

                            mFirstName = profile.getFirstName();
                            mLastName = profile.getLastName();
                            mPhone = profile.getPhone();

                            ((MDFApplication) (getActivity().getApplication())).setFirstName(profile.getFirstName());
                            ((MDFApplication) (getActivity().getApplication())).setLastName(profile.getLastName());
                            ((MDFApplication) (getActivity().getApplication())).setPhoneNumger(profile.getPhone());

                            edtFirstName.setText(mFirstName);
                            edtLastName.setText(mLastName);
                            edtPhone.setText(mPhone);

                            Picasso.Builder picassoLoader = new Picasso.Builder(getActivity().getApplicationContext());
                            Picasso picasso = picassoLoader.build();
                            picasso
                                    .load(Uri.encode(profile.getAvatar(), "@#&=*+-_.,:!?()/~'%"))
                                    .error(R.drawable.missing_product)
                                    .into(imgMain, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {
                                            imgMain.getDrawable();
                                            imgMain.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent myIntent = new Intent(getContext(), FullScreenImageActivity.class);
                                                    myIntent.putExtra("image", profile.getAvatar()); //Optional parameters
                                                    Bundle bndlanimation =
                                                            ActivityOptions.makeCustomAnimation(getContext(), R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
                                                    getActivity().startActivity(myIntent, bndlanimation);
                                                }
                                            });
                                        }

                                        @Override
                                        public void onError() {
                                        }
                                    });
                        }
                        else {
                            ((MDFApplication)(getActivity().getApplication())).setUserAuthKey("");
                            ((MDFApplication)(getActivity().getApplication())).setEmail("");
                            ((MDFApplication)(getActivity().getApplication())).setFirstName("");
                            ((MDFApplication)(getActivity().getApplication())).setLastName("");
                            ((MDFApplication)(getActivity().getApplication())).setPhoneNumger("");
                            ((MainActivity)getActivity()).onBackPressed();
                        }
                    }
                    else {
                        Log.e("!!!", "!!!");
                    }
                }
                @Override
                public void onFailure(Call<ProfileGetAttributes> call, Throwable t) {
                    Log.e("!!!", "error!!!");
                    ((MainActivity)getActivity()).hideProgress();
                }
            });

        }
        else {
            ((MDFApplication)(getActivity().getApplication())).setUserAuthKey("");
            ((MDFApplication)(getActivity().getApplication())).setEmail("");
            ((MDFApplication)(getActivity().getApplication())).setFirstName("");
            ((MDFApplication)(getActivity().getApplication())).setLastName("");
            ((MDFApplication)(getActivity().getApplication())).setPhoneNumger("");
        }


//        btnEng = (Button)rootView.findViewById(R.id.btnEngSettings);
//        btnRus = (Button)rootView.findViewById(R.id.btnRusSettings);
//        btnUkr = (Button)rootView.findViewById(R.id.btnUkrSettings);
//
//        btnUSD = (Button)rootView.findViewById(R.id.btnUSDSettings);
//        btnEUR = (Button)rootView.findViewById(R.id.btnEURSettings);
//        btnUAH = (Button)rootView.findViewById(R.id.btnUAHSettings);
//
//        btnTermsAndConditions = (Button)rootView.findViewById(R.id.btnTermsAndConditionsSettingsScreen);
//        btnImprint = (Button)rootView.findViewById(R.id.btnImprintSettingsScreen);
//        btnCustomerSupport = (Button)rootView.findViewById(R.id.btnCustomerSupportSettingsScreen);
//
//        txtCurrency = (TextView)rootView.findViewById(R.id.txtCurrencySettings);
//        txtLanguage = (TextView)rootView.findViewById(R.id.txtLanguageSettings);
//        txtAirport = (TextView)rootView.findViewById(R.id.txtAirportSettings);
//
//        spinnerCurrencies = (Spinner) rootView.findViewById(R.id.spinnerCurrencySettingsScreen);
//        spinnerAirport = (Spinner) rootView.findViewById(R.id.spinnerAirportSettingsScreen);
//        spinnerLanguage = (Spinner) rootView.findViewById(R.id.spinnerLanguagesSettingsScreen);
//
//ё        setHasOptionsMenu(true);
//
//        mTracker = ((MDFApplication)getActivity().getApplication()).getDefaultTracker();
//        mTracker.setScreenName("Settings");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
//
//        final ArrayList<String> mCurrencies = new ArrayList<>();
//
//        curLanguage = ((MDFApplication) getActivity().getApplication()).getCurrentLanguage();
//        updateLangButtons();
//
//        curCurrency = ((MDFApplication)getActivity().getApplication()).getCurrentCurrency();
////        updateCurrencyButtons();
//
//        final ArrayList<String> mLanguages = new ArrayList<>();
//        mLanguages.add("RU");
//        mLanguages.add("EN");
//        mLanguages.add("UA");
//
//        currencySymbolsList = ((MDFApplication)getActivity().getApplication()).getCurrencySymbols();
//
//        final String currentAirport = ((MDFApplication)getActivity().getApplication()).getAirport();
//        Iterator it = currencySymbolsList.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry pair = (Map.Entry)it.next();
////            System.out.println(pair.getKey() + " = " + pair.getValue());
//            mCurrencies.add((String)pair.getKey());
//            it.remove(); // avoids a ConcurrentModificationException
//        }
//
//        RetrofitService retrofitService = new RetrofitService();
//        PostInterfaceApi service = retrofitService.create();
//        Call<GetLanguagesResponse> call = service.getLanguages(JsonCreator.GetLanguages());
//        ((MainActivity)getActivity()).showProgress();
//        call.enqueue(new Callback<GetLanguagesResponse>() {
//            @Override
//            public void onResponse(Call<GetLanguagesResponse> call, Response<GetLanguagesResponse> response) {
//                Log.e("!!!", "!!!");
//
//                mLanguagesMap = response.body().getData();
//                // Convert Map to List
//                List<Map.Entry<String, String>> list = new LinkedList<Map.Entry<String, String>>(mLanguagesMap.entrySet());
//
//                // Sort list with comparator, to compare the Map values
//                Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
//                    public int compare(Map.Entry<String, String> o1,
//                                       Map.Entry<String, String> o2) {
//                        return (o1.getValue()).compareTo(o2.getValue());
//                    }
//                });
//
//                // Convert sorted map back to a Map
//                Map<String, String> sortedMap = new LinkedHashMap<String, String>();
//                mLanguagesCodesList = new ArrayList<>();
//                mLanguagesNamesList = new ArrayList<>();
//                for (Iterator<Map.Entry<String, String>> it = list.iterator(); it.hasNext();) {
//                    Map.Entry<String, String> entry = it.next();
//                    mLanguagesCodesList.add(entry.getKey());
//                    mLanguagesNamesList.add(entry.getValue());
//                }
//
//                adapterLanguages = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mLanguagesNamesList)  {
//                    @Override
//                    public View getView(int position, View convertView,ViewGroup parent) {
//                        View v = super.getView(position, convertView, parent);
//                        ((TextView) v).setTextSize(16);
//                        FontUtils.setNormalFont(getContext(), ((TextView) v));
//                        ((TextView) v).setGravity(Gravity.CENTER);
//                        return v;
//                    }
//                    @Override
//                    public View getDropDownView(int position, View convertView,ViewGroup parent) {
//                        View v = super.getDropDownView(position, convertView,parent);
//                        ((TextView) v).setGravity(Gravity.CENTER);
//                        FontUtils.setNormalFont(getContext(), ((TextView) v));
//                        return v;
//                    }
//                };
//                adapterLanguages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinnerLanguage.setAdapter(adapterLanguages);
//                spinnerLanguage.setSelection(mLanguagesCodesList.indexOf(curLanguage));
//                spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int index, long id) {
//                        Log.e("!!!", "!!!");
//                        ((MDFApplication)getActivity().getApplication()).setCurrentLanguage(mLanguagesCodesList.get(index));
//                        if (!curLanguage.equalsIgnoreCase(mLanguagesCodesList.get(index))) {
//                            isLanguageChange = true;
//                            curLanguage = mLanguagesCodesList.get(index);
//                            updateLangButtons();
//                        }
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });
////                ((MDFApplication)getApplication()).setLanguages(response.body().getData());
//                ((MainActivity)getActivity()).hideProgress();
//            }
//
//            @Override
//            public void onFailure(Call<GetLanguagesResponse> call, Throwable t) {
//                Log.e("!!!", "error!!!");
//                ((MainActivity)getActivity()).hideProgress();
//            }
//        });
//
//        adapterCurrencies = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mCurrencies)  {
//            @Override
//            public View getView(int position, View convertView,ViewGroup parent) {
//                View v = super.getView(position, convertView, parent);
//                ((TextView) v).setTextSize(16);
//                FontUtils.setNormalFont(getContext(), ((TextView) v));
//                ((TextView) v).setGravity(Gravity.CENTER);
//                return v;
//            }
//            @Override
//            public View getDropDownView(int position, View convertView,ViewGroup parent) {
//                View v = super.getDropDownView(position, convertView,parent);
//                ((TextView) v).setGravity(Gravity.CENTER);
//                FontUtils.setNormalFont(getContext(), ((TextView) v));
//                return v;
//            }
//        };
//
//        adapterCurrencies.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//
//        spinnerCurrencies.setAdapter(adapterCurrencies);
//
//        updateAirportSpinner();
//
//        isFirstTouch = true;
//
//        spinnerCurrencies.setSelection(mCurrencies.indexOf(curCurrency));
//
//        spinnerCurrencies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//                if (!(curCurrency.equalsIgnoreCase(mCurrencies.get(position)))) {
//                    ((MDFApplication) getActivity().getApplication()).setCurrentCurrency(mCurrencies.get(position));
//                    AlertDialog.Builder alertCurrencies = new AlertDialog.Builder(getContext());
//                    alertCurrencies.setCancelable(false);
//                    alertCurrencies.setNegativeButton(getResources().getString(R.string.ok_caps), null);
//                    TextView message = new TextView(getContext());
//                    message.setText(getResources().getString(R.string.currency_alert));
//                    message.setGravity(Gravity.CENTER_HORIZONTAL);
//                    message.setPadding(
//                            (int) Utils.dpToPx(10),
//                            (int) Utils.dpToPx(10),
//                            (int) Utils.dpToPx(10),
//                            (int) Utils.dpToPx(10)
//                    );
//                    FontUtils.setBoldFont(getContext(), message);
//                    alertCurrencies.setView(message);
//                    alertCurrencies.show();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });
//
//        btnTermsAndConditions.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment fragment = new FragmentWebView();
//                ((FragmentWebView) fragment).setContentType("terms-and-conditions");
//                if (fragment != null) {
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction transaction = fragmentManager.beginTransaction();
//                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
//                    transaction.replace(R.id.content_frame, fragment);
//                    transaction.addToBackStack(null);
//                    transaction.commit();
//                }
//            }
//        });
//
//        btnImprint.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment fragment = new FragmentWebView();
//                ((FragmentWebView) fragment).setContentType("imprint");
//                if (fragment != null) {
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction transaction = fragmentManager.beginTransaction();
//                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
//                    transaction.replace(R.id.content_frame, fragment);
//                    transaction.addToBackStack(null);
//                    transaction.commit();
//                }
//            }
//        });
//
//        btnCustomerSupport.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment fragment = new FragmentCustomerSupport();
//                if (fragment != null) {
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction transaction = fragmentManager.beginTransaction();
//                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
//                    transaction.replace(R.id.content_frame, fragment);
//                    transaction.addToBackStack(null);
//                    transaction.commit();
//                }
//            }
//        });
//
//
        return rootView;
    }

}
