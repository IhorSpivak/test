package llc.net.mydutyfree.fragments;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import llc.net.mydutyfree.base.CartSingleton;
import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.base.RetrofitService;
import llc.net.mydutyfree.interfaces.PostInterfaceApi;
import llc.net.mydutyfree.newmdf.MainActivity;
import llc.net.mydutyfree.newmdf.NewThankActivity;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.newmdf.ThankPageType;
import llc.net.mydutyfree.response.GetStoreDiscountResponse;
import llc.net.mydutyfree.response.OrderCheckoutResponse;
import llc.net.mydutyfree.response.Product;
import llc.net.mydutyfree.utils.Const;
import llc.net.mydutyfree.utils.FontUtils;
import llc.net.mydutyfree.utils.JsonCreator;
import llc.net.mydutyfree.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gorf on 2/3/16.
 */
public class FragmentConfirm extends Fragment {

    //    Button btnAirport;
//    Button btnFlight;
//    Button btnDate;
//    Button btnTime;
    String value = null;
    String oldValue = null;
    LinearLayout parentLayout;
    Button btnContinue;
    TextView txtAirport, txtDateTime, txtFlightNumber, txtFirstLastName, txtEmail, txtPhone, txtTitle, txtCartTitle;
    TextView txtProductName, txtProductQuantity, txtProductPrice, txtProductDiscount, txtTotalPriceTitle, txtTotalPrice, txtTotalPriceWithDiscountTitle, txtTotalPriceWithDiscount;
    String mAirport = null;
    String mDateTime = null;
    String mFlightNumber = null;
    String mFirstLastName = null;
    String mEmail = null;
    String mPhone = null;
    boolean mIsS7 = false;
    int mMiles;
    String mAirportStringForRequest = null;
    String mDateTimeStringForRequest = null;
    String mFlightNumberStringForRequest = null;
    String mFirstNameStringForRequest = null;
    String mLastNameStringForRequest = null;
    String mEmailStringForRequest = null;
    String mPhoneStringForRequest = null;
    String mDiscountCodeForRequest = null;
    boolean isBestOfferPresent = false;
    boolean mIsDiscountAccepted = false;
    int mDiscountPercent = 0;
    int mDiscountFromCode = 0;
    private Tracker mTracker;

    private ThankPageType mThankType = null;

    private String mFirstName = null;
    private String mLastName = null;
    private String mDate = null;

    public void setAirport(String airportName) {
        mAirport = airportName;
    }

    public void setThankType(ThankPageType type) {
        mThankType = type;
    }

    public void setDateTime(String date, String time) {
        mDate = date;
        mDateTime = String.format("%s %s", date, time);
        mDateTimeStringForRequest = mDateTime;
    }

    public void setFlight(String flight) {
        mFlightNumber = flight;
        mFlightNumberStringForRequest = flight;
    }

    public void setDiscountPercentAndCode(int discount, String code) {
        mDiscountCodeForRequest = code;
        mDiscountFromCode = discount;
        mIsDiscountAccepted = true;
    }

    public void setS7PercentAndCode(int discount, String code, int miles) {
        mDiscountCodeForRequest = code;
        mDiscountFromCode = discount;
        mIsDiscountAccepted = true;
        mMiles = miles;
        mIsS7 = true;
    }

    public void setFirstLastName(String firstName, String lastName) {
        mFirstName = firstName;
        mLastName = lastName;
        mFirstLastName = String.format("%s %s", firstName, lastName);
        mFirstNameStringForRequest = firstName;
        mLastNameStringForRequest = lastName;
    }

    public void setEmail(String email) {
        mEmail = email;
        mEmailStringForRequest = email;
    }

    public void setPhone(String phone) {
        mPhone = phone;
        mPhoneStringForRequest = phone;
    }

    public FragmentConfirm() {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.screen_confirm, container, false);
        setHasOptionsMenu(true);
        mTracker = ((MDFApplication)getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName("Order Preview");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        mAirport = String.format("%s - %s", getResources().getString(R.string.airport_caps), mAirport);
        mDateTime = String.format("%s - %s", getResources().getString(R.string.date_and_time).toUpperCase(), mDateTime);
        if (mFlightNumber != null)
            mFlightNumber = String.format("%s - %s", getResources().getString(R.string.flight_number_caps), mFlightNumber);
        mFirstLastName = String.format("%s - %s", getResources().getString(R.string.name_last_name), mFirstLastName);
        mEmail = String.format("%s - %s", getActivity().getResources().getString(R.string.email_caps), mEmail);
        mPhone = String.format("%s - %s", getResources().getString(R.string.phone_number_caps), mPhone);

        txtAirport = (TextView)rootView.findViewById(R.id.txtAirportScreenConfirm);
        txtDateTime = (TextView)rootView.findViewById(R.id.txtDateTimeScreenConfirm);
        txtFlightNumber = (TextView)rootView.findViewById(R.id.txtFlightScreenConfirm);
        txtFirstLastName = (TextView)rootView.findViewById(R.id.txtFirstLastNameScreenConfirm);
        txtEmail = (TextView)rootView.findViewById(R.id.txtEmailScreenConfirm);
        txtPhone = (TextView)rootView.findViewById(R.id.txtPhoneNumberScreenConfirm);
        FontUtils.setNormalFont(getContext(), txtAirport);
        FontUtils.setNormalFont(getContext(), txtDateTime);
        FontUtils.setNormalFont(getContext(), txtFlightNumber);
        FontUtils.setNormalFont(getContext(), txtFirstLastName);
        FontUtils.setNormalFont(getContext(), txtEmail);
        FontUtils.setNormalFont(getContext(), txtPhone);

        txtTitle = (TextView)rootView.findViewById(R.id.txtTitleScreenConfirm);
        FontUtils.setBoldFont(getContext(), txtTitle);
        txtCartTitle = (TextView)rootView.findViewById(R.id.txtCartTitleScreenConfirm);
        FontUtils.setBoldFont(getContext(), txtCartTitle);

        txtAirport.setText(mAirport);
        txtDateTime.setText(mDateTime);
        txtFlightNumber.setText(mFlightNumber);
        txtFirstLastName.setText(mFirstLastName);
        txtEmail.setText(mEmail);
        txtPhone.setText(mPhone);

        txtProductName = (TextView)rootView.findViewById(R.id.txtProductNameScreenConfirm);
        FontUtils.setNormalFont(getContext(), txtProductName);
        txtProductQuantity = (TextView)rootView.findViewById(R.id.txtProductQuantityScreenConfirm);
        FontUtils.setNormalFont(getContext(), txtProductQuantity);
        txtProductPrice = (TextView)rootView.findViewById(R.id.txtProductPriceScreenConfirm);
        FontUtils.setNormalFont(getContext(), txtProductPrice);
        txtProductDiscount = (TextView)rootView.findViewById(R.id.txtProductDiscountScreenConfirm);
        FontUtils.setNormalFont(getContext(), txtProductDiscount);
        txtTotalPriceTitle = (TextView)rootView.findViewById(R.id.txtTotalPriceTitleScreenConfirm);
        FontUtils.setNormalFont(getContext(), txtTotalPriceTitle);
        txtTotalPrice = (TextView)rootView.findViewById(R.id.txtTotalPriceScreenConfirm);
        FontUtils.setNormalFont(getContext(), txtTotalPrice);
        txtTotalPriceWithDiscountTitle = (TextView)rootView.findViewById(R.id.txtTotalPriceWithDiscountTitleScreenConfirm);
        FontUtils.setNormalFont(getContext(), txtTotalPriceWithDiscountTitle);
        txtTotalPriceWithDiscount = (TextView)rootView.findViewById(R.id.txtTotalPriceWithDiscountScreenConfirm);
        FontUtils.setNormalFont(getContext(), txtTotalPriceWithDiscount);

        if (mFlightNumber != null) {
            txtFlightNumber.setVisibility(View.VISIBLE);
            txtAirport.setVisibility(View.VISIBLE);
        }
        else {
            txtAirport.setVisibility(View.GONE);
            txtFlightNumber.setVisibility(View.GONE);
        }

        parentLayout = (LinearLayout)rootView.findViewById(R.id.linearLayoutCartListScreenConfirm);
        btnContinue = (Button)rootView.findViewById(R.id.btnContinueScreenConfirm);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOrder();
            }
        });

        ((MainActivity)getActivity()).setTitle(getResources().getString(R.string.confirm_order));

        RetrofitService retrofitService = new RetrofitService();
        PostInterfaceApi service = retrofitService.create();
        Call<GetStoreDiscountResponse> call = service.getStoreDiscount(JsonCreator.GetStoreDiscount());
        ((MainActivity)getActivity()).showProgress();
        call.enqueue(new Callback<GetStoreDiscountResponse>() {
            @Override
            public void onResponse(Call<GetStoreDiscountResponse> call, Response<GetStoreDiscountResponse> response) {
                Log.e("!!!", "!!!");
                ((MainActivity)getActivity()).hideProgress();
                mDiscountPercent = response.body().getData();
                updateProductsList();
            }
            @Override
            public void onFailure(Call<GetStoreDiscountResponse> call, Throwable t) {
                Log.e("!!!", "error!!!");
                ((MainActivity)getActivity()).hideProgress();
            }
        });

        return rootView;
    }

    private void sendOrder() {
        Map<String, String> prodQuantities = new HashMap<String, String>(CartSingleton.getInstance().getProductsCounts());

        final JSONObject bodyOrder = new JSONObject();
        JSONObject orderData = new JSONObject();

        String curCurrency = ((MDFApplication) getActivity().getApplication()).getCurrentCurrency();
        mAirportStringForRequest = ((MDFApplication) getActivity().getApplication()).getAirport();

        try {
            JSONArray productQuantitiesJsonArray = new JSONArray();
            Iterator iterator = prodQuantities.entrySet().iterator();
            while (iterator.hasNext()) {
                JSONObject productQuantitiesJsonObject = new JSONObject();
                Map.Entry pair = (Map.Entry)iterator.next();
                productQuantitiesJsonObject.accumulate("product_id", pair.getKey());
                productQuantitiesJsonObject.accumulate("product_qty", pair.getValue());
                productQuantitiesJsonObject.accumulate("store_id", CartSingleton.getInstance().getProductWithID((String)pair.getKey()).getPickUpStoreData().getId());
                iterator.remove(); // avoids a ConcurrentModificationException
                productQuantitiesJsonArray.put(productQuantitiesJsonObject);
            }
            bodyOrder.accumulate("products_list", productQuantitiesJsonArray);

            if (mIsDiscountAccepted) {
                if (mIsS7)
                    orderData.accumulate("card_code", mDiscountCodeForRequest);
                else
                    orderData.accumulate("discount_code", mDiscountCodeForRequest);
            }

            orderData.accumulate("currency", curCurrency);
            orderData.accumulate("airport", mAirportStringForRequest);
            orderData.accumulate("departure_date", mDateTimeStringForRequest);
            if (mFlightNumber != null)
                orderData.accumulate("flight_number", mFlightNumberStringForRequest);
            else
                orderData.accumulate("flight_number", "--");

            orderData.accumulate("first_name", mFirstNameStringForRequest);
            orderData.accumulate("last_name", mLastNameStringForRequest);
            orderData.accumulate("email", mEmailStringForRequest);
            orderData.accumulate("phone_number", mPhoneStringForRequest);

            bodyOrder.accumulate("order_data", orderData);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String mS7String = null;
        String mDiscountString = null;
        if (mIsDiscountAccepted) {
            if (mIsS7)
                mS7String = mDiscountCodeForRequest;
            else
                mDiscountString = mDiscountCodeForRequest;
        }

        RetrofitService retrofitService = new RetrofitService();
        PostInterfaceApi service = retrofitService.create();
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.showProgress();
        Call<OrderCheckoutResponse> callCheckout = service.orderCheckout(JsonCreator.OrderCheckout(
                mFirstNameStringForRequest,
                mLastNameStringForRequest,
                mEmailStringForRequest,
                mPhoneStringForRequest,
                mDiscountString,
                mS7String,
                mDateTimeStringForRequest,
                mFlightNumberStringForRequest
                ));
        callCheckout.enqueue(new Callback<OrderCheckoutResponse>() {
            @Override
            public void onResponse(Call<OrderCheckoutResponse> call, Response<OrderCheckoutResponse> response) {
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.hideProgress();

                if ( (response.body().getData() != null) && (response.body().getStatus().equalsIgnoreCase("success")) ) {
                    String projectToken = Const.MIXPANEL_PROJECT_TOKEN;
                    MixpanelAPI mixpanel = MixpanelAPI.getInstance(getContext(), projectToken);
                    final MixpanelAPI.People people = mixpanel.getPeople();
                    mixpanel.alias(mEmailStringForRequest, null);
                    people.setOnce("Day of first order", mDateTimeStringForRequest);
                    people.increment("LTV", CartSingleton.getInstance().getTotalAmount().get("EUR"));
                    people.set("Currency", ((MDFApplication) getActivity().getApplication()).getCurrentCurrency());
                    people.set("$first_name", mFirstName);
                    people.set("$last_name", mLastName);
                    people.set("$phone", mPhoneStringForRequest);
                    people.set("$email", mEmailStringForRequest);
                    people.increment("Number of trips", 1);

                    int orderID = response.body().getData().getId();
                    double totalPrice = CartSingleton.getInstance().getTotalAmount().get("EUR");
                    CartSingleton.getInstance().ClearCart();
                    getActivity().getSupportFragmentManager().popBackStack("main", 0);
                    Intent myIntent = new Intent(getContext(), NewThankActivity.class);
                    myIntent.putExtra("orderID", orderID); //Optional parameters
                    myIntent.putExtra("totalPrice", totalPrice); //Optional parameters

                    if (mThankType != null) {
                        myIntent.putExtra("thankType", mThankType);
                    }
                    else {
                        String airport = MDFApplication.getMDFApplication().getAirport();
//                        Baku = 8
//                        Domodedovo = 7
                            if (airport.equalsIgnoreCase("8"))
                                myIntent.putExtra("thankType", ThankPageType.THANK_TYPE_BAKU);
                            else if  (airport.equalsIgnoreCase("7"))
                                myIntent.putExtra("thankType", ThankPageType.THANK_TYPE_DOMODEDOVO);
                            else
                                myIntent.putExtra("thankType", ThankPageType.THANK_TYPE_OTHER_AIRPORTS);
                    }

                    myIntent.putExtra("amount", txtTotalPriceWithDiscount.getText().toString());
                    myIntent.putExtra("date", mDateTimeStringForRequest);

                    if (mIsS7) {
                        myIntent.putExtra("isS7", mIsS7); //Optional parameters
                        myIntent.putExtra("isS7miles", mMiles); //Optional parameters
                        myIntent.putExtra("isS7discount", mDiscountPercent); //Optional parameters
                    }
                    if (mIsDiscountAccepted)
                        myIntent.putExtra("discountCode", mDiscountCodeForRequest); //Optional parameters
                    Bundle bndlanimation =
                            ActivityOptions.makeCustomAnimation(getContext(), R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
                    getActivity().startActivity(myIntent, bndlanimation);
                }
                else
                {
                    new AlertDialog.Builder(getContext())
                            .setTitle(getResources().getString(R.string.an_error_has_occurred))
                            .setMessage(response.body().getMessage())
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
//                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
//                                @Override
//                                public void onDismiss(DialogInterface dialog) {
//                                    getActivity().finish();
//                                }
//                            })
                            .setCancelable(false)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<OrderCheckoutResponse> call, Throwable t) {
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.hideProgress();

            }
        });
//TODO: old code, trash
//        new AsyncCustomPost(getContext(), Const.URLS.URL, Const.Actions.Order.CHECKOUT, bodyOrder).execute();
    }


    public void updateProductsList() {

        if (mIsDiscountAccepted == true)
            mDiscountPercent = mDiscountFromCode;

        String currentCurrency = ((MDFApplication)(getActivity().getApplication())).getCurrentCurrency();
        String currencySymbol = ((MDFApplication)(getActivity().getApplication())).getCurrencySymbols().get(currentCurrency);
        int k = CartSingleton.getInstance().getProductsCount();
        LinearLayout.LayoutParams layoutParamsLine = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)Utils.dpToPx(30));
        List<Product> mList = CartSingleton.getInstance().getProductList();
        float totalPriceWithDiscount = 0;
        for (int i = 0; i < k; i++) {
            LinearLayout newLine = new LinearLayout(getContext());
            newLine.setWeightSum(10);
            newLine.setPadding((int) Utils.dpToPx(10), 0, (int) Utils.dpToPx(10), 0);
            newLine.setOrientation(LinearLayout.HORIZONTAL);
            newLine.setBackgroundColor(Color.WHITE);
            parentLayout.addView(newLine, layoutParamsLine);

            Product item = mList.get(i);
            int quantity = CartSingleton.getInstance().getProductCount(item.getID());

            TextView txtName = new TextView(getContext());
            txtName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            txtName.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            txtName.setSingleLine(true);
            txtName.setEllipsize(TextUtils.TruncateAt.END);
            txtName.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));


            Spannable asterisk = new SpannableString("* ");
            asterisk.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.colorRed)), 0, asterisk.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            TV.setText(asterisk);
            Spannable digit = new SpannableString(String.format("%d. ", i + 1));
            digit.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.colorBlack)), 0, digit.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            TV.append(digit);

            if (item.isBestOffer() == true) {
                isBestOfferPresent = true;
                txtName.setText(asterisk);
            }
            txtName.append(digit);

            txtName.append(item.getName());
            txtName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            FontUtils.setNormalFont(getContext(), txtName);

            TextView txtQuantity = new TextView(getContext());
            txtQuantity.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            txtQuantity.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            txtQuantity.setSingleLine(true);
            txtQuantity.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
            txtQuantity.setText(String.format("%d", quantity));
            txtQuantity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            FontUtils.setNormalFont(getContext(), txtQuantity);

            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String priceString = decimalFormat.format(item.getPriceOld().get(currentCurrency));
            priceString = new StringBuilder().append(priceString).append(" ").append(currencySymbol).toString();

            TextView txtPrice = new TextView(getContext());
            txtPrice.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            txtPrice.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            txtPrice.setSingleLine(true);
            txtPrice.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
            txtPrice.setText(priceString);
            txtPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            FontUtils.setNormalFont(getContext(), txtPrice);

            String discountString = null;
            int itemCount = CartSingleton.getInstance().getProductCount(item.getID());
            float priceEUR = item.getPrice().get("EUR").floatValue();
            float priceOldEUR = item.getPriceOld().get("EUR").floatValue();
            float currentPriceWithDiscount = 0;
            float priceCurrentCurrency = item.getPriceOld().get(currentCurrency).floatValue();

            int discount = Math.round(100 - priceEUR * 100 / priceOldEUR);
            discountString = String.format("-%d%%", discount);
            currentPriceWithDiscount = (float) (priceCurrentCurrency * ((100. - discount) / 100.) * itemCount);

            if (item.isBestOffer() == true) {
                discountString = String.format("*");
                currentPriceWithDiscount = item.getPrice().get(currentCurrency).floatValue() * itemCount;
            }
            else {
                discountString = String.format("-%d%%", mDiscountPercent);
                currentPriceWithDiscount = (float) (priceCurrentCurrency * ((100. - mDiscountPercent) / 100.)  * itemCount);
            }
            if (item.isNoDiscount()) {
                currentPriceWithDiscount = item.getPrice().get(currentCurrency).floatValue() * itemCount;
                discountString = String.format("");
            }

            TextView txtDiscount = new TextView(getContext());
            txtDiscount.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            txtDiscount.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            txtDiscount.setSingleLine(true);
            txtDiscount.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
            txtDiscount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            txtDiscount.setText(discountString);
            FontUtils.setNormalFont(getContext(), txtDiscount);

            newLine.addView(txtName, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 4.5f));
            newLine.addView(txtQuantity, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.9f));
            newLine.addView(txtPrice, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2));
            newLine.addView(txtDiscount, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.6f));
            totalPriceWithDiscount += currentPriceWithDiscount;
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String priceWODString = decimalFormat.format(CartSingleton.getInstance().getTotalWithoutDiscount().get(currentCurrency));
        priceWODString = new StringBuilder().append(priceWODString).append(" ").append(currencySymbol).toString();
        txtTotalPrice.setText(priceWODString);

        String priceString = decimalFormat.format(totalPriceWithDiscount);
        priceString = new StringBuilder().append(priceString).append(" ").append(currencySymbol).toString();
        txtTotalPriceWithDiscount.setText(priceString);

        if (isBestOfferPresent == true) {
            TextView txtBestOfferHint = new TextView(getContext());
            txtBestOfferHint.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            txtBestOfferHint.setSingleLine(false);
            txtBestOfferHint.setPadding((int)Utils.dpToPx(15), 0, 0, (int)Utils.dpToPx(15));
            txtBestOfferHint.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
            txtBestOfferHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            FontUtils.setNormalFont(getContext(), txtBestOfferHint);
            txtBestOfferHint.setText(getContext().getResources().getString(R.string.discount_special_offer));
            parentLayout.addView(txtBestOfferHint, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }
/*
    class AsyncCustomPost extends AsyncTask<String, String, String> {
        private Context mContext;
        private JSONObject mBody;
        private String mAction;
        private String mURL;
        private Boolean mIsNewArrivals;

        public AsyncCustomPost(Context context, String url, String action, JSONObject body) {
            mContext = context;
            mURL = url;
            mAction = action;
            mBody = body;
        }

        private String POST(String url, String action, JSONObject body){
            InputStream inputStream = null;
            String result = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                String json = "";
                JSONObject filter = new JSONObject();
                JSONObject jsonObject = new JSONObject();
                String curLang = ((MDFApplication) getActivity().getApplication()).getCurrentLanguage();
                String curAirport = ((MDFApplication) getActivity().getApplication()).getAirport();
                String storeID;
                if (curAirport.equalsIgnoreCase("0"))
                    storeID = "1";
                else
                    storeID = curAirport;

                jsonObject.accumulate("lang_code", curLang);
                jsonObject.accumulate("action", action);
                jsonObject.accumulate("body", body);
                jsonObject.accumulate("store_id", storeID);
                jsonObject.accumulate("auth_key", Const.API_KEY);
                json = jsonObject.toString();
                Log.i("!!!!REQUEST!!!", json);

                // 5. set json to StringEntity
                StringEntity se = new StringEntity(json, "UTF-8");

                // 6. set httpPost Entity
                httpPost.setEntity(se);

                // 7. Set some headers to inform server about the type of the content
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                // 8. Execute POST request to the given URL
                HttpResponse httpResponse = httpclient.execute(httpPost);

                // 9. receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

                // 10. convert inputstream to string
                if(inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            // 11. return result
            return result;
        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity mainActivity = (MainActivity)getActivity();
            mainActivity.showProgress();
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (mAction.equalsIgnoreCase(Const.Actions.Order.CHECKOUT)) {
                    String projectToken = Const.MIXPANEL_PROJECT_TOKEN;
                    MixpanelAPI mixpanel = MixpanelAPI.getInstance(getContext(), projectToken);
                    final MixpanelAPI.People people = mixpanel.getPeople();
                    mixpanel.alias(mEmailStringForRequest, null);
                    people.setOnce("Day of first order", mDateTimeStringForRequest);
                    people.increment("LTV", CartSingleton.getInstance().getTotalAmount().get("EUR"));
                    people.set("Currency", ((MDFApplication) getActivity().getApplication()).getCurrentCurrency());
                    people.set("$first_name", mFirstName);
                    people.set("$last_name", mLastName);
                    people.set("$phone", mPhoneStringForRequest);
                    people.set("$email", mEmailStringForRequest);
                    people.increment("Number of trips", 1);

                    JSONObject json = new JSONObject(result);
                    JSONObject data = json.optJSONObject("data");
                    int orderID = data.optInt("id");
                    double totalPrice = CartSingleton.getInstance().getTotalAmount().get("EUR");
                    CartSingleton.getInstance().ClearCart();
                    getActivity().getSupportFragmentManager().popBackStack("main", 0);
//                    getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    Fragment fragment = new FragmentHome();
//                    if (fragment != null) {
//                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                        FragmentTransaction transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
//                        transaction.replace(R.id.content_frame, fragment);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
//                    }
                    Intent myIntent = new Intent(getContext(), NewThankActivity.class);
                    myIntent.putExtra("orderID", orderID); //Optional parameters
                    myIntent.putExtra("totalPrice", totalPrice); //Optional parameters
                    myIntent.putExtra("thankType", ThankPageType.THANK_TYPE_OTHER_AIRPORTS); //Optional parameters
                    myIntent.putExtra("amount", txtTotalPriceWithDiscount.getText().toString());
                    myIntent.putExtra("date", mDateTimeStringForRequest);
                    if (mIsS7) {
                        myIntent.putExtra("isS7", mIsS7); //Optional parameters
                        myIntent.putExtra("isS7miles", mMiles); //Optional parameters
                        myIntent.putExtra("isS7discount", mDiscountPercent); //Optional parameters
                    }
                    if (mIsDiscountAccepted)
                        myIntent.putExtra("discountCode", mDiscountCodeForRequest); //Optional parameters
                    Bundle bndlanimation =
                            ActivityOptions.makeCustomAnimation(getContext(), R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
                    getActivity().startActivity(myIntent, bndlanimation);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Error json", e.toString());
            }
            Log.d("Result", result);

            MainActivity mainActivity = (MainActivity)getActivity();
            mainActivity.hideProgress();
            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected String doInBackground(String... params) {
            return POST(mURL, mAction, mBody);
        }
    }
*/
}
