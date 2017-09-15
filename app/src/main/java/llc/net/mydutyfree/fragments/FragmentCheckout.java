package llc.net.mydutyfree.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v7.app.AlertDialog;
//import android.app.DatePickerDialog;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import llc.net.mydutyfree.base.CartSingleton;
import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.base.RetrofitService;
import llc.net.mydutyfree.interfaces.PostInterfaceApi;
import llc.net.mydutyfree.newmdf.MainActivity;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.newmdf.ThankPageType;
import llc.net.mydutyfree.response.CheckDiscountResponse;
import llc.net.mydutyfree.response.Flight;
import llc.net.mydutyfree.response.GetFlightsResponse;
import llc.net.mydutyfree.response.GetStores;
import llc.net.mydutyfree.response.ShowPartnerField;
import llc.net.mydutyfree.utils.Const;
import llc.net.mydutyfree.utils.CustomTypefaceSpan;
import llc.net.mydutyfree.utils.FontUtils;
import llc.net.mydutyfree.utils.JsonCreator;
import llc.net.mydutyfree.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gorf on 2/3/16.
 */
public class FragmentCheckout extends Fragment {

//    Button btnDiscount;
    ProgressBar progressBar, progressBarS7;
    Button btnAirport;
    Button btnContinue;
    Button btnFlight;
    Button btnDate;
    Button btnTime;
    ImageButton btnEnterFlightManually;
    EditText edtFlight;
    String value = null;
    String oldValue = null;
    TextView txtEmail, txtFirstName, txtLastName, txtPanoramaCard, txtDiscountInfo, txtDiscountHint, txtPhone, txtTitle, txtDepartureDateTime, txtFlight, txtAirport, txtMasterCardHint, txtS7Info, txtS7Hint, txtS7CardHint;
    EditText edtEmail, edtFirstName, edtLastName, edtPanoramaCard, edtDiscount, edtPhone, edtS7;
    String curAirport, mDateSelectedString, mTimeSelectedString;
    DateTimeFormatter dateFormatter, timeFormatter;
    List<Flight> listFlights;
    Flight mSelectedFlight;
    List<String> mFlightsNamesArray;
    RelativeLayout mLayoutS7;
    LocalDate mLocalDate;
    LocalTime mLocalTime;
    DateTime mDateTime;
    int mMiles = 0;
    int mDiscountPercent = 0;
    Date mDate;
    boolean mIsS7Code = false;
    private Tracker mTracker;
    static Interpolator easeInOutQuart = PathInterpolatorCompat.create(0.77f, 0f, 0.175f, 1f);

    public FragmentCheckout() {
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
    public void onResume() {
        super.onResume();

        if (CartSingleton.getInstance().getTotalAmount().get(((MDFApplication) getActivity().getApplication()).getCurrentCurrency()).floatValue() > 0) {

            String authKey = ((MDFApplication) (getActivity().getApplication())).getUserAuthKey();
            String email = ((MDFApplication) (getActivity().getApplication())).getEmail();
            String firstName = ((MDFApplication) (getActivity().getApplication())).getFirstName();
            String lastName = ((MDFApplication) (getActivity().getApplication())).getLastName();
            String phone = ((MDFApplication) (getActivity().getApplication())).getPhoneNumber();
            if (email.length() > 0)
                edtEmail.setText(email);
            if (firstName.length() > 0)
                edtFirstName.setText(firstName);
            if (lastName.length() > 0)
                edtLastName.setText(lastName);
            if (phone.length() > 0)
                edtPhone.setText(phone);

            if (authKey.length() > 0)
                edtEmail.setEnabled(false);

            edtFlight.setText("");
            mSelectedFlight = null;
            mDateSelectedString = dateFormatter.print(mLocalDate);
            btnDate.setText(mDateSelectedString);
            mTimeSelectedString = timeFormatter.print(mLocalTime);
            btnTime.setText(mTimeSelectedString);

            updateFlights();
        }
        else  {
            ((MainActivity)getActivity()).onBackPressed();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.screen_checkout, container, false);

        setHasOptionsMenu(true);
        mTracker = ((MDFApplication)getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName("Checkout");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        ((MainActivity)getActivity()).setTitle(getResources().getString(R.string.checkout_caps));
        curAirport = ((MDFApplication) getActivity().getApplication()).getAirport();
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBarScreenCheckout);
        progressBarS7 = (ProgressBar)rootView.findViewById(R.id.progressBarS7ScreenCheckout);

        mLocalDate = new LocalDate();
        mLocalTime = new LocalTime();
        mLocalTime = mLocalTime.plusSeconds(60 * 60 * 2);

        float buttonTextSize = 0;
        float density = getContext().getResources().getDisplayMetrics().density;
        Log.e("!DENSITY!", "DENSITY = " + density);

        if (density >= 4.0) {
            //xxxhdpi
            Log.e("!DENSITY!", "xxxhdpi");
        }
        else if ((density >= 3.0) && (density < 4.0)) {
            //xxhdpi
            Log.e("!DENSITY!", "xxhdpi");
        }
        else if ((density >= 2.0) && (density < 3.0)) {
            //xhdpi
            Log.e("!DENSITY!", "xhdpi");
        }
        else if ((density >= 1.5) && (density < 2.0)) {
            //hdpi
            Log.e("!DENSITY!", "hdpi");
        }
        else if ((density >= 1.0) && (density < 1.5)) {
            //mdpi
            Log.e("!DENSITY!", "mdpi");
        }
        else if (density < 1.0) {
            //ldpi
            Log.e("!DENSITY!", "ldpi");
        }

        if (density <= 1.5f)
            buttonTextSize = 13;
        else if ((density > 1.5f) && (density < 3.0f))
            buttonTextSize = 13;
        else if (density >= 3.0f)
            buttonTextSize = 12;

        btnAirport = (Button)rootView.findViewById(R.id.btnSelectAirportScreenCheckout);
        FontUtils.setNormalFont(getContext(), btnAirport);

        btnEnterFlightManually = (ImageButton) rootView.findViewById(R.id.btnDidNotFindFlightScreenCheckout);
        btnFlight = (Button)rootView.findViewById(R.id.btnSelectFlightScreenCheckout);
        FontUtils.setNormalFont(getContext(), btnFlight);

        btnDate = (Button)rootView.findViewById(R.id.btnSelectDateScreenCheckout);
        FontUtils.setNormalFont(getContext(), btnDate);

        btnTime = (Button)rootView.findViewById(R.id.btnSelectTimeScreenCheckout);
        FontUtils.setNormalFont(getContext(), btnTime);

        btnContinue = (Button)rootView.findViewById(R.id.btnContinueScreenCheckout);
        FontUtils.setBoldFont(getContext(), btnContinue);

        edtFlight = (EditText)rootView.findViewById(R.id.edtFlightScreenCheckout);
        FontUtils.setNormalFont(getContext(), edtFlight);

        txtMasterCardHint = (TextView)rootView.findViewById(R.id.txtMasterCardHintScreenCheckout);
        FontUtils.setNormalFont(getContext(), txtMasterCardHint);
        txtMasterCardHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize - 2);

        txtS7CardHint = (TextView)rootView.findViewById(R.id.txtS7CardHintScreenCheckout);
        FontUtils.setNormalFont(getContext(), txtS7CardHint);
        txtS7CardHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize - 2);

        txtDiscountHint = (TextView)rootView.findViewById(R.id.txtDiscountHintScreenCheckout);
        txtDiscountInfo = (TextView)rootView.findViewById(R.id.txtDiscountInfoScreenCheckout);
        edtDiscount = (EditText)rootView.findViewById(R.id.edtDiscountScreenCheckout);
        FontUtils.setNormalFont(getContext(), txtDiscountHint);
        FontUtils.setNormalFont(getContext(), txtDiscountInfo);
        FontUtils.setNormalFont(getContext(), edtDiscount);

        txtS7Hint = (TextView)rootView.findViewById(R.id.txtS7HintScreenCheckout);
        txtS7Info = (TextView)rootView.findViewById(R.id.txtS7InfoScreenCheckout);
        edtS7 = (EditText)rootView.findViewById(R.id.edtS7ScreenCheckout);
        FontUtils.setNormalFont(getContext(), txtS7Hint);
        FontUtils.setNormalFont(getContext(), txtS7Info);
        FontUtils.setNormalFont(getContext(), edtS7);

        txtEmail = (TextView)rootView.findViewById(R.id.txtEmailScreenCheckout);
        edtEmail = (EditText)rootView.findViewById(R.id.edtEmailScreenCheckout);
        FontUtils.setNormalFont(getContext(), txtEmail);
        FontUtils.setNormalFont(getContext(), edtEmail);

        txtTitle = (TextView)rootView.findViewById(R.id.txtTitleScreenCheckout);
        FontUtils.setNormalFont(getContext(), txtTitle);

        txtFirstName = (TextView)rootView.findViewById(R.id.txtFirstNameScreenCheckout);
        edtFirstName = (EditText)rootView.findViewById(R.id.edtFirstNameScreenCheckout);
        FontUtils.setNormalFont(getContext(), txtFirstName);
        FontUtils.setNormalFont(getContext(), edtFirstName);

        txtLastName = (TextView)rootView.findViewById(R.id.txtLastNameScreenCheckout);
        edtLastName = (EditText)rootView.findViewById(R.id.edtLastNameScreenCheckout);
        FontUtils.setNormalFont(getContext(), txtLastName);
        FontUtils.setNormalFont(getContext(), edtLastName);

        txtPhone = (TextView)rootView.findViewById(R.id.txtPhoneScreenCheckout);
        edtPhone = (EditText)rootView.findViewById(R.id.edtPhoneScreenCheckout);
        FontUtils.setNormalFont(getContext(), txtPhone);
        FontUtils.setNormalFont(getContext(), edtPhone);

        txtPanoramaCard = (TextView)rootView.findViewById(R.id.txtPanoramaScreenCheckout);
        edtPanoramaCard = (EditText)rootView.findViewById(R.id.edtPanoramaScreenCheckout);
        FontUtils.setNormalFont(getContext(), txtPanoramaCard);
        FontUtils.setNormalFont(getContext(), edtPanoramaCard);

        txtDepartureDateTime = (TextView)rootView.findViewById(R.id.txtDateTimeScreenCheckout);
        FontUtils.setNormalFont(getContext(), txtDepartureDateTime);

        txtFlight = (TextView)rootView.findViewById(R.id.txtFlightNumberScreenCheckout);
        FontUtils.setNormalFont(getContext(), txtFlight);

        txtAirport = (TextView)rootView.findViewById(R.id.txtAirportScreenCheckout);
        FontUtils.setNormalFont(getContext(), txtAirport);

        mLayoutS7 = (RelativeLayout) rootView.findViewById(R.id.relativeLayoutS7ScreenCheckout);

        btnAirport.setEnabled(false);

        btnContinue.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);

        dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        timeFormatter = DateTimeFormat.forPattern("HH:mm");

        GetStores stores = ((MDFApplication)getActivity().getApplication()).getStores();
        String curLanguage = ((MDFApplication) getActivity().getApplication()).getCurrentLanguage();
        Map<String, Map<String, String>> mapStores = stores.getData();
        List<Map.Entry<String, Map<String, String>>> list = new LinkedList<>(mapStores.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Map<String, String>>>() {
            public int compare(Map.Entry<String, Map<String, String>> o1,
                               Map.Entry<String, Map<String, String>> o2) {
                return (o1.getKey()).compareTo(o2.getKey());
            }
        });
        ArrayList<String> mStoreNames = new ArrayList<String>();
        for (Iterator<Map.Entry<String, Map<String, String>>> it = list.iterator(); it.hasNext();) {
            Map.Entry<String, Map<String, String>> entry = it.next();
            Map<String, String> namesStore = entry.getValue();
            if (namesStore.get(curLanguage) != null) {
                mStoreNames.add(namesStore.get(curLanguage));
                Log.e("!!!", "!!!");
            }
            else {
                Log.e("!!!", "!!!");
            }
        }
        String airportString = mapStores.get(curAirport).get(curLanguage);
        btnAirport.setText(airportString);

        RetrofitService retrofitService = new RetrofitService();
        PostInterfaceApi service = retrofitService.create();
        Call<ShowPartnerField> callStores = service.ordersShowPartnerField(JsonCreator.Orders.ShowPartnerFieldFor("s7"));
        ((MainActivity)getActivity()).showProgress();
        callStores.enqueue(new Callback<ShowPartnerField>() {
            @Override
            public void onResponse(Call<ShowPartnerField> call, Response<ShowPartnerField> response) {

//                String curLanguage = ((MDFApplication) getActivity().getApplication()).getCurrentLanguage();
                ((MainActivity)getActivity()).hideProgress();
                Boolean mIsShow = response.body().getData();
                if (mIsShow)
                    mLayoutS7.setVisibility(View.VISIBLE);
                else
                    mLayoutS7.setVisibility(View.GONE
                    );
//                Map<String, Map<String, String>> mapStores = response.body().getData();
//
//                // Convert Map to List
//                List<Map.Entry<String, Map<String, String>>> list = new LinkedList<>(mapStores.entrySet());
//
//                // Sort list with comparator, to compare the Map values
//                Collections.sort(list, new Comparator<Map.Entry<String, Map<String, String>>>() {
//                    public int compare(Map.Entry<String, Map<String, String>> o1,
//                                       Map.Entry<String, Map<String, String>> o2) {
//                        return (o1.getKey()).compareTo(o2.getKey());
//                    }
//                });
//                ArrayList<String> mStoreNames = new ArrayList<String>();
//                for (Iterator<Map.Entry<String, Map<String, String>>> it = list.iterator(); it.hasNext();) {
//                    Map.Entry<String, Map<String, String>> entry = it.next();
//                    Map<String, String> namesStore = entry.getValue();
//                    if (namesStore.get(curLanguage) != null) {
//                        mStoreNames.add(namesStore.get(curLanguage));
//                        Log.e("!!!", "!!!");
//                    }
//                    else {
                Log.e("!!!", "!!!");
//                    }
//                }
//                String airportString = mStoreNames.get(Integer.parseInt(curAirport)-1);
//                btnAirport.setText(airportString);
            }

            @Override
            public void onFailure(Call<ShowPartnerField> call, Throwable t) {
                ((MainActivity)getActivity()).hideProgress();
            }
        });

        btnEnterFlightManually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(getContext());
                dialog.setMessage(R.string.flight_not_found_message);
                dialog.setTitle(R.string.flight_not_found_title);
                dialog.setPositiveButton(R.string.yes_caps, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showKeyboard(getContext(), edtFlight);
                        btnFlight.setText("");
                        edtFlight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if (hasFocus) {
                                    btnFlight.setText("");
                                }
                                else {
                                    if (edtFlight.getText().toString().length() > 0) {
                                        mSelectedFlight = new Flight("", edtFlight.getText().toString(), "");
                                    }
                                    else {
                                        mSelectedFlight = null;
                                        updateFlights();
                                    }
                                }
                            }
                        });

                    }
                });
                dialog.setNegativeButton(R.string.no_caps, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog.show();
            }
        });

        btnFlight.setOnClickListener(new View.OnClickListener() {
            int backupNumber = -1;
            int oldNumber = -1;
            @Override
            public void onClick(View v) {
                edtFlight.setText("");
                edtFlight.clearFocus();
                mSelectedFlight = null;
                if (listFlights.size() > 0) {
                    btnFlight.setText(getContext().getResources().getString(R.string.select_flight_number));
                    backupNumber = -1;
                    oldNumber = -1;
                    CustomNumberPicker picker = new CustomNumberPicker(getContext());
                    picker.setMinValue(0);
                    picker.setMaxValue(mFlightsNamesArray.size() - 1);
                    picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

                    String[] myArray = new String[mFlightsNamesArray.size()];
                    mFlightsNamesArray.toArray(myArray);
                    picker.setDisplayedValues(myArray);

                    NumberPicker.OnValueChangeListener myValChangedListener = new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                            backupNumber = newVal;
                            oldNumber = oldVal;
                        }
                    };

                    picker.setOnValueChangedListener(myValChangedListener);
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext()).setView(picker);
                    dialogBuilder.setCancelable(false);
                    dialogBuilder.setTitle(getContext().getResources().getString(R.string.select_flight_number));
                    dialogBuilder.setPositiveButton(getContext().getResources().getString(R.string.ok_caps), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (backupNumber == -1) {
                                backupNumber = 0;
                            }
                            mSelectedFlight = listFlights.get(backupNumber);
                            btnFlight.setText(mFlightsNamesArray.get(backupNumber));
//                        Toast.makeText(getContext(), value, Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialogBuilder.setNegativeButton(getContext().getResources().getString(R.string.cancel_caps), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if ((oldNumber != -1) && mSelectedFlight == null) {
                                mSelectedFlight = listFlights.get(oldNumber);
                                btnFlight.setText(mFlightsNamesArray.get(oldNumber));
                            }

                        }
                    });
                    AlertDialog dialog = dialogBuilder.create();
                    dialog.show();
                } else {
                    btnFlight.setText(getContext().getResources().getString(R.string.no_flights));
                }
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener myDatepickerListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mLocalDate = new LocalDate(year, monthOfYear + 1, dayOfMonth);
                        if (mLocalDate.isEqual(new LocalDate()))
                        {
                            int minHour = new LocalTime().getHourOfDay() + 2;
                            int minMinute = new LocalTime().getMinuteOfHour();

                            if ( (mLocalTime.getHourOfDay() < minHour) || (mLocalTime.getHourOfDay() == minHour && mLocalTime.getMinuteOfHour() < minMinute) ) {
                                mLocalTime = new LocalTime(minHour, minMinute);
                                mTimeSelectedString = timeFormatter.print(mLocalTime);
                                btnTime.setText(mTimeSelectedString);
                            }
                        }

                        mDateSelectedString = dateFormatter.print(mLocalDate);
                        btnDate.setText(mDateSelectedString);
                        updateFlights();
                    }
                };
                int resStyleID = 0;
                if (android.os.Build.VERSION.SDK_INT > 20)
                    resStyleID = R.style.MDFPickerDialog;
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        resStyleID,
                        myDatepickerListener,
                        mLocalDate.getYear(),
                        mLocalDate.getMonthOfYear() - 1,
                        mLocalDate.getDayOfMonth()
//                        Calendar.getInstance().get(Calendar.YEAR),
//                        Calendar.getInstance().get(Calendar.MONTH),
//                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                        );
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.getDatePicker().setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                datePickerDialog.setCancelable(false);
                datePickerDialog.show();

            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener myTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (mLocalDate.isEqual(new LocalDate()))
                        {
                            int minHour = new LocalTime().getHourOfDay() + 2;
                            int minMinute = new LocalTime().getMinuteOfHour();
                            if ( (hourOfDay < minHour) || (hourOfDay == minHour && minute < minMinute) ) {
                                hourOfDay = minHour;
                                minute = minMinute;
                                Toast.makeText(getContext(), R.string.invalid_time_toast, Toast.LENGTH_LONG).show();
                            }
                        }

                        mLocalTime = new LocalTime(hourOfDay, minute);
                        mTimeSelectedString = timeFormatter.print(mLocalTime);
                        btnTime.setText(mTimeSelectedString);
                        updateFlights();
                    }
                };

                int resStyleID = 0;
                if (android.os.Build.VERSION.SDK_INT > 20)
                    resStyleID = R.style.MDFPickerDialog;

                final TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getContext(),
                        resStyleID,
                        myTimePickerListener,
                        mLocalTime.getHourOfDay(),
                        mLocalTime.getMinuteOfHour(),
//                        Calendar.getInstance().get(Calendar.HOUR),
//                        Calendar.getInstance().get(Calendar.MINUTE),
                        true
                ){
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        view.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                        if (mLocalDate.isEqual(new LocalDate()))
                        {
                            int minHour = new LocalTime().getHourOfDay() + 2;
                            int minMinute = new LocalTime().getMinuteOfHour();
                            if (hourOfDay < minHour) {
                                hourOfDay = minHour;
                                if (android.os.Build.VERSION.SDK_INT < 23){
                                    view.setCurrentHour(hourOfDay);
                                } else{
                                    view.setHour(hourOfDay);
                                }
                            }
                            if (hourOfDay == minHour && minute < minMinute)
                            {
                                minute = minMinute;
                                if (android.os.Build.VERSION.SDK_INT < 23){
                                    view.setCurrentMinute(minute);
                                } else{
                                    view.setMinute(minute);
                                }
                            }
                        }
                        super.onTimeChanged(view, hourOfDay, minute);
                    }
                };
                timePickerDialog.setCancelable(false);
                timePickerDialog.show();
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    Fragment fragment = new FragmentConfirm();
                    ((FragmentConfirm) fragment).setEmail(edtEmail.getText().toString());
//                    ((FragmentConfirm) fragment).setThankType(ThankPageType.THANK_TYPE_OTHER_AIRPORTS);
                    ((FragmentConfirm) fragment).setAirport(btnAirport.getText().toString());
                    if (mDiscountPercent > 0) {
                        if (mIsS7Code)
                            ((FragmentConfirm) fragment).setS7PercentAndCode(mDiscountPercent, edtS7.getText().toString(), mMiles);
                        else
                            ((FragmentConfirm) fragment).setDiscountPercentAndCode(mDiscountPercent, edtDiscount.getText().toString());
                    }
                    ((FragmentConfirm) fragment).setDateTime(mDateSelectedString, mTimeSelectedString);
                    ((FragmentConfirm) fragment).setFirstLastName(edtFirstName.getText().toString(), edtLastName.getText().toString());
                    ((FragmentConfirm) fragment).setFlight(mSelectedFlight.getCode());
                    ((FragmentConfirm) fragment).setPhone(edtPhone.getText().toString());
                    if (fragment != null) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                        transaction.replace(R.id.content_frame, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    String projectToken = Const.MIXPANEL_PROJECT_TOKEN;
                    MixpanelAPI mixpanel = MixpanelAPI.getInstance(getContext(), projectToken);
                    try {
                        JSONObject properties = new JSONObject();
                        properties.put("label", String.format("%d", CartSingleton.getInstance().  getProductsCount()));
                        properties.put("value", String.format("%.2f", CartSingleton.getInstance().getTotalAmount().get("EUR")));
                        mixpanel.track("Checkout Submit order confirm", properties);
                    } catch (JSONException e) {

                    }

                }
            }
        });

//        btnDiscount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ObjectAnimator animator = new ObjectAnimator().ofFloat(btnDiscount, "alpha", 1.0f, 0.0f);
//                animator.addListener(new AnimatorListenerAdapter() {
//                                              @Override
//                                              public void onAnimationEnd(Animator animation) {
//                                                  super.onAnimationEnd(animation);
//                                                  btnDiscount.setVisibility(View.GONE);
//                                              }
//                                          });
//                animator.setDuration(300).start();
////                ObjectAnimator.ofFloat(btnDiscount, "alpha", 1.0f, 0.0f).setDuration(300).start();
//                ObjectAnimator.ofFloat(txtDiscountHint, "alpha", 0.0f, 1.0f).setDuration(300).start();
//                ObjectAnimator.ofFloat(edtDiscount, "alpha", 0.0f, 1.0f).setDuration(300).start();
//            }
//        });

        edtS7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    edtDiscount.setEnabled(false);
                }
                else {
                    edtDiscount.setEnabled(true);
                }
            }
        });

        edtDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    edtS7.setEnabled(false);
                }
                else {
                    edtS7.setEnabled(true);
                }
            }
        });

        edtS7.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String text = ((EditText) v).getText().toString();
                    if (text.length() > 0) {
                        updateS7(text);
                    }
                    else {
                        txtS7Info.setAlpha(0);
                        txtS7CardHint.setVisibility(View.GONE);
                    }
                }
                else {
                    txtS7Info.setAlpha(0);
                    txtS7CardHint.setVisibility(View.GONE);
                }
            }
        });

        edtDiscount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String text = ((EditText) v).getText().toString();
                    if (text.length() > 0) {
                        updateDiscount(text);
                    }
                    else {
                        txtDiscountInfo.setAlpha(0);
                        txtMasterCardHint.setVisibility(View.GONE);
                    }
                }
                else {
                    txtDiscountInfo.setAlpha(0);
                    txtMasterCardHint.setVisibility(View.GONE);
                }
            }
        });

        return rootView;
    }


    public static Animation expand(final View view) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) view.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = view.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0 so use 1 instead.
        view.getLayoutParams().height = 1;
        view.setVisibility(View.VISIBLE);

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                view.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);

                view.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        animation.setInterpolator(easeInOutQuart);
        animation.setDuration(computeDurationFromHeight(view));
        view.startAnimation(animation);

        return animation;
    }

    public static void showKeyboard(Context context, EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        if(imm != null){
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }

    public static void hideKeyboard(Activity activity) {
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    public static Animation collapse(final View view) {
        final int initialHeight = view.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setInterpolator(easeInOutQuart);

        int durationMillis = computeDurationFromHeight(view);
        a.setDuration(durationMillis);

        view.startAnimation(a);

        return a;
    }

    private static int computeDurationFromHeight(View view) {
        // 1dp/ms * multiplier
        return (int) (view.getMeasuredHeight() / view.getContext().getResources().getDisplayMetrics().density);
    }
//    public static void expand(final View v) {
//        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        final int targetHeight = v.getMeasuredHeight();
//
//        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
//        v.getLayoutParams().height = 1;
//        v.setVisibility(View.VISIBLE);
//        Animation a = new Animation()
//        {
//            @Override
//            protected void applyTransformation(float interpolatedTime, Transformation t) {
//                v.getLayoutParams().height = interpolatedTime == 1
//                        ? ViewGroup.LayoutParams.WRAP_CONTENT
//                        : (int)(targetHeight * interpolatedTime);
//                v.requestLayout();
//            }
//
//            @Override
//            public boolean willChangeBounds() {
//                return true;
//            }
//        };
//
//        // 1dp/ms
//        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
//        v.startAnimation(a);
//    }
//
//    public static void collapse(final View v) {
//        final int initialHeight = v.getMeasuredHeight();
//
//        Animation a = new Animation()
//        {
//            @Override
//            protected void applyTransformation(float interpolatedTime, Transformation t) {
//                if(interpolatedTime == 1){
//                    v.setVisibility(View.GONE);
//                }else{
//                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
//                    v.requestLayout();
//                }
//            }
//
//            @Override
//            public boolean willChangeBounds() {
//                return true;
//            }
//        };
//
//        // 1dp/ms
//        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
//        v.startAnimation(a);
//    }


    private void updateDiscount(String code) {
        RetrofitService retrofitService = new RetrofitService();
        PostInterfaceApi service = retrofitService.create();
        Call<CheckDiscountResponse> callCheckDiscountCode = service.checkDiscount(JsonCreator.CheckDisountCode(code));
        progressBar.setVisibility(View.VISIBLE);
        callCheckDiscountCode.enqueue(new Callback<CheckDiscountResponse>() {
            @Override
            public void onResponse(Call<CheckDiscountResponse> call, Response<CheckDiscountResponse> response) {
                mIsS7Code = false;
                progressBar.setVisibility(View.GONE);
                Boolean isAccepted = response.body().getData().isValid();
                Integer discountPercent = response.body().getData().getDiscount();
                txtDiscountInfo.setAlpha(1);
                if ( (!isAccepted) && (discountPercent == 0))
                {
                    txtDiscountInfo.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    txtDiscountInfo.setText(getResources().getString(R.string.code_invalid));
                    mDiscountPercent = 0;
                }
                else if ( (!isAccepted) && (discountPercent != 0))
                {
                    txtDiscountInfo.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    txtDiscountInfo.setText(getResources().getString(R.string.code_used));
                    mDiscountPercent = 0;
                }
                else if ( (isAccepted) && (discountPercent != 0))
                {
                    txtDiscountInfo.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
                    txtDiscountInfo.setText(getResources().getString(R.string.code_accepted));
                    mDiscountPercent = discountPercent;
                }
                if (edtDiscount.getText().toString().equalsIgnoreCase("mcpremium")) {
                    txtMasterCardHint.setVisibility(View.VISIBLE);
                    String stringBlack = getResources().getString(R.string.master_card_tip_black);
                    stringBlack = String.format(stringBlack, mDiscountPercent);
                    String stringRed = getResources().getString(R.string.master_card_tip_red);
                    stringRed = String.format(stringRed, mDiscountPercent);
                    Spannable tipBlack = new SpannableString(stringBlack);

                    tipBlack.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.colorBlack)), 0, tipBlack.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    tipBlack.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.colorRed)), stringBlack.indexOf(stringRed), stringBlack.indexOf(stringRed) + stringRed.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    tipBlack.setSpan(new CustomTypefaceSpan("", MDFApplication.getBoldTypeface()), stringBlack.indexOf(stringRed), stringBlack.indexOf(stringRed) + stringRed.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    txtMasterCardHint.setText(tipBlack);
                }
                else {
                    txtMasterCardHint.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CheckDiscountResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void updateS7(String code) {
        RetrofitService retrofitService = new RetrofitService();
        PostInterfaceApi service = retrofitService.create();
        Call<CheckDiscountResponse> callCheckDiscountCode = service.checkDiscount(JsonCreator.CheckDisountCodeS7(code, CartSingleton.getInstance().getTotalWithoutDiscount().get("EUR")));
        progressBarS7.setVisibility(View.VISIBLE);
        callCheckDiscountCode.enqueue(new Callback<CheckDiscountResponse>() {
            @SuppressLint("StringFormatInvalid")
            @Override
            public void onResponse(Call<CheckDiscountResponse> call, Response<CheckDiscountResponse> response) {
                mIsS7Code = true;
                progressBarS7.setVisibility(View.GONE);
                Boolean isAccepted = response.body().getData().isValid();
                Integer discountPercent = response.body().getData().getDiscount();
                Integer miles = response.body().getData().getMiles();
                mMiles = miles;
                txtS7Info.setAlpha(1);
                if ( (!isAccepted) && (discountPercent == 0))
                {
                    txtS7Info.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    txtS7Info.setText(getResources().getString(R.string.code_invalid));
                    mDiscountPercent = 0;
                }
                else if ( (!isAccepted) && (discountPercent != 0))
                {
                    txtS7Info.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    txtS7Info.setText(getResources().getString(R.string.code_used));
                    mDiscountPercent = 0;
                }
                else if ( (isAccepted) && (discountPercent != 0))
                {
                    txtS7Info.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
                    txtS7Info.setText(getResources().getString(R.string.code_accepted));
                    mDiscountPercent = discountPercent;
                }
//                if (edtDiscount.getText().toString().equalsIgnoreCase("mcpremium")) {
                    txtS7CardHint.setVisibility(View.VISIBLE);
                    String stringBlack = getResources().getString(R.string.s7_text_black);
                    stringBlack = String.format(stringBlack, miles, discountPercent);
                    String stringRedOne = getResources().getString(R.string.s7_text_red_one);
                    stringRedOne = String.format(stringRedOne, miles);
                    String stringRedTwo = getResources().getString(R.string.s7_text_red_two);
                    stringRedTwo = String.format(stringRedTwo, discountPercent);
                    Spannable tipBlack = new SpannableString(stringBlack);

                    tipBlack.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.colorBlack)), 0, tipBlack.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    tipBlack.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.colorRed)), stringBlack.indexOf(stringRedOne), stringBlack.indexOf(stringRedOne) + stringRedOne.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    tipBlack.setSpan(new CustomTypefaceSpan("", MDFApplication.getBoldTypeface()), stringBlack.indexOf(stringRedOne), stringBlack.indexOf(stringRedOne) + stringRedOne.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    tipBlack.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.colorRed)), stringBlack.indexOf(stringRedTwo), stringBlack.indexOf(stringRedTwo) + stringRedTwo.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    tipBlack.setSpan(new CustomTypefaceSpan("", MDFApplication.getBoldTypeface()), stringBlack.indexOf(stringRedTwo), stringBlack.indexOf(stringRedTwo) + stringRedTwo.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//                    txtS7CardHint.setText(String.format("Discount = %d; Miles = %d",discountPercent , miles));
                    txtS7CardHint.setText(tipBlack);
//                }
//                else {
//                    txtMasterCardHint.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onFailure(Call<CheckDiscountResponse> call, Throwable t) {
                progressBarS7.setVisibility(View.GONE);
            }
        });
    }

    private void updateFlights() {
        edtFlight.setText("");
        mSelectedFlight = null;

        RetrofitService retrofitService = new RetrofitService();
        PostInterfaceApi service = retrofitService.create();

        Call<GetFlightsResponse> callGetFlights = service.getFlights(JsonCreator.GetFlights(mDateSelectedString, mTimeSelectedString));
        ((MainActivity)getActivity()).showProgress();
        callGetFlights.enqueue(new Callback<GetFlightsResponse>() {
            @Override
            public void onResponse(Call<GetFlightsResponse> call, Response<GetFlightsResponse> response) {
                Log.e("","");
                ((MainActivity)getActivity()).hideProgress();
                listFlights = response.body().getData();
                mFlightsNamesArray = new ArrayList<String>();
                if (listFlights.size() > 0) {
                    for (int i = 0; i < listFlights.size(); i++) {
                        Flight item = listFlights.get(i);
                        String flightCodePlusDestination = String.format(item.getCode() + " / " + item.getDestination());
                        mFlightsNamesArray.add(flightCodePlusDestination);
                    }
                    btnFlight.setText(getContext().getResources().getString(R.string.select_flight_number));
                    btnFlight.setEnabled(true);
                } else {
                    btnFlight.setText(getContext().getResources().getString(R.string.no_flights));
//                    btnFlight.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<GetFlightsResponse> call, Throwable t) {
                Log.e("","");
                ((MainActivity)getActivity()).hideProgress();
            }
        });
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public final static boolean isValidPhone(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches();
    }

    private Boolean isValid() {
        Boolean isValid = true;
        String errorString = "";

        if (edtFirstName.getText().toString().equalsIgnoreCase("")) {
            errorString = errorString.concat("\n" + getResources().getString(R.string.please_enter_valid_name));
            isValid = false;
        }
        if (edtLastName.getText().toString().equalsIgnoreCase("")) {
            errorString = errorString.concat("\n" + getResources().getString(R.string.please_enter_valid_lastname));
            isValid = false;
        }
        if (!edtPanoramaCard.getText().toString().equalsIgnoreCase("")) {
            if (edtPanoramaCard.getText().toString().length() < 7 || edtPanoramaCard.getText().toString().length() < 10) {
                errorString = errorString.concat("\n" + getResources().getString(R.string.please_enter_valid_panorama_card_code));
                isValid = false;
            }
        }
        if (mSelectedFlight == null) {
            errorString = errorString.concat("\n" + getResources().getString(R.string.select_flight_number));
            isValid = false;
        }
        if (!isValidPhone(edtPhone.getText())) {
            errorString = errorString.concat("\n" + getResources().getString(R.string.please_enter_valid_phone));
            isValid = false;
        }
        if (!isValidEmail(edtEmail.getText())) {
            errorString = errorString.concat("\n" + getResources().getString(R.string.please_enter_valid_email));
            isValid = false;
        }


        if (!errorString.equalsIgnoreCase(""))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(false);
            builder.setPositiveButton(getResources().getString(R.string.ok_caps), null);
            TextView message = new TextView(getContext());
            message.setText(errorString);
            message.setGravity(Gravity.CENTER_HORIZONTAL);
            message.setPadding(
                    (int) Utils.dpToPx(10),
                    (int) Utils.dpToPx(10),
                    (int) Utils.dpToPx(10),
                    (int) Utils.dpToPx(10)
            );
            FontUtils.setBoldFont(getContext(), message);
            builder.setView(message);
            builder.show();
        }

        return isValid;
    }

    public static class CustomNumberPicker extends NumberPicker {
        private Context mContext;
        public CustomNumberPicker(Context context, AttributeSet attrs) {
            super(context, attrs);
            mContext = context;
        }

        public CustomNumberPicker(Context context) {
            super(context);
            mContext = context;
        }

        @Override
        public void addView(View child) {
            super.addView(child);
            updateView(child);
        }

        @Override
        public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
            super.addView(child, index, params);
            updateView(child);
        }

        @Override
        public void addView(View child, android.view.ViewGroup.LayoutParams params) {
            super.addView(child, params);
            updateView(child);
        }

        private void updateView(View view) {
            if(view instanceof EditText){
                ((EditText) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                ((EditText) view).setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));

            }
        }
    }
}
