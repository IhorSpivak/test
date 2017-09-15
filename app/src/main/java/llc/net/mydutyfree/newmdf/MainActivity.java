package llc.net.mydutyfree.newmdf;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.gson.JsonObject;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.LinkProperties;
import llc.net.mydutyfree.adapters.StoreListAdapter;
import llc.net.mydutyfree.base.CartSingleton;
import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.base.MDFProgressDialog;
import llc.net.mydutyfree.base.RetrofitService;
import llc.net.mydutyfree.fragments.FragmentCart;
import llc.net.mydutyfree.fragments.FragmentCategory;
import llc.net.mydutyfree.fragments.FragmentCheckout;
import llc.net.mydutyfree.fragments.FragmentCheckoutBorderShop;
import llc.net.mydutyfree.fragments.FragmentCustomerSupport;
import llc.net.mydutyfree.fragments.FragmentHome;
import llc.net.mydutyfree.fragments.FragmentItem;
import llc.net.mydutyfree.fragments.FragmentLogin;
import llc.net.mydutyfree.fragments.FragmentProfile;
import llc.net.mydutyfree.fragments.FragmentSettings;
import llc.net.mydutyfree.interfaces.PostInterfaceApi;
import llc.net.mydutyfree.models.BrandItem;
import llc.net.mydutyfree.response.Category;
import llc.net.mydutyfree.response.GetCategoriesResponse;
import llc.net.mydutyfree.response.GetLanguagesResponse;
import llc.net.mydutyfree.response.GetStores;
import llc.net.mydutyfree.response.SaveDeviceToken;
import llc.net.mydutyfree.utils.Const;
import llc.net.mydutyfree.utils.FontUtils;
import llc.net.mydutyfree.utils.JsonCreator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener, Button.OnClickListener {

    protected MDFProgressDialog mProgressDialog;
    int mProgressDialogLinkCount;

    private Dialog mSelectStoreDialog;
    private String[] mScreenTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList, mStoresList;
    SearchView searchView;
    MenuItem menuItem;
    private TextView txtSelectTypeDialogTitle;
    private RelativeLayout rlSelType;
    private RelativeLayout rlSelStore;

    MixpanelAPI mixpanel;

    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private CategoriesMenuAdapter categoriesMenuAdapter;
    private ArrayList<Category> categoriesList;

    private RelativeLayout dialogScreen1, dialogScreen2, dialogScreen3;
    private TextView txtDialogScreen1, txtDialogScreen2, txtDialogScreen3;
    private ImageView imgDialogScreen1, imgDialogScreen2, imgDialogScreen3;
    private HorizontalScrollView scrollViewTutorial;
    private Button btnDialogNext;
    private int currentTutorialPage;
    private SearchView mSearchView;
    private Context mContext;
    private String value = null;
    private String oldValue = null;
    private String mStoreID = null;
    private Boolean isDrawerOpened;
    private Boolean isFilter = false;
    private AdapterView.OnItemClickListener categoriesMenuItemLickListener;

    private List<String> mStoreNames;
    private List<String> mStoreCodes;

    private int hot_number = 2;
    private TextView ui_hot = null;
    private Tracker mTracker;
    private boolean mIsAirportTypeSelected = true;
    private int mDialogState = 0;

    private String mCurrentLanguage;

    public void showProgress() {
        mProgressDialogLinkCount++;
//        Log.e("MDFApplication", String.format("show progress dialog. Count - %d", mProgressDialogLinkCount));
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    public void hideProgress() {
        mProgressDialogLinkCount--;
//        Log.e("MDFApplication", String.format("hide progress dialog. Count - %d", mProgressDialogLinkCount));
        if (mProgressDialog.isShowing() && mProgressDialogLinkCount == 0)
            mProgressDialog.dismiss();
    }

    public void updateCategoriesMenu() {
        JSONObject bodyGetCategories = new JSONObject();
//        new AsyncRequest((MDFApplication) this.getApplication(), Const.URLS.URL, Const.Actions.Categories.GET_ALL, bodyGetCategories).execute();
        RetrofitService retrofitService = new RetrofitService();
        PostInterfaceApi service = retrofitService.create();

        Call<GetCategoriesResponse> callGetCategories = service.getCategories(JsonCreator.GetCategories());
        showProgress();
        callGetCategories.enqueue(new Callback<GetCategoriesResponse>() {
            @Override
            public void onResponse(Call<GetCategoriesResponse> call, Response<GetCategoriesResponse> response) {
                hideProgress();
                List<Category> catList = response.body().getData();
                categoriesList = new ArrayList<>();

                for(Iterator<Category> i = catList.iterator(); i.hasNext(); ) {
                    Category item = i.next();
                    if (item.isAvailable()) {
                        categoriesList.add(item);
                        if (item.getChildren().size() > 0) {
                            for (Iterator<llc.net.mydutyfree.response.Category> j = item.getChildren().iterator(); j.hasNext(); ) {
                                llc.net.mydutyfree.response.Category subItem = j.next();
                                if (subItem.isAvailable()) {
                                    categoriesList.add(subItem);
                                }
                            }
                        }
                    }
                }
                categoriesMenuAdapter.setList(categoriesList);
            }

            @Override
            public void onFailure(Call<GetCategoriesResponse> call, Throwable t) {
                hideProgress();
            }
        });
    }

    public void createAndShowTutorialDialog() {
        final Dialog hello = new Dialog(this, R.style.TutorialDialog);
        hello.setCancelable(false);
        hello.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                createAndShowSelectTypeDialog();
            }
        });
        WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displaymetrics);
        final int heightInPixels = displaymetrics.heightPixels;
        final int widthInPixels = displaymetrics.widthPixels;
//        View dialogView = new View(this);
        hello.setContentView(R.layout.dialog_tutorial);
//        hello.getWindow().setFlags(0x04000000, 0x04000000); // FLAG_TRANSLUCENT_STATUS
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(widthInPixels, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogScreen1 = (RelativeLayout)hello.getWindow().findViewById(R.id.relativeLayout1DialogTutorial);
        dialogScreen2 = (RelativeLayout)hello.getWindow().findViewById(R.id.relativeLayout2DialogTutorial);
        dialogScreen3 = (RelativeLayout)hello.getWindow().findViewById(R.id.relativeLayout3DialogTutorial);
        txtDialogScreen1 = (TextView)hello.getWindow().findViewById(R.id.txtTip1DialogTutorial);
        txtDialogScreen2 = (TextView)hello.getWindow().findViewById(R.id.txtTip2DialogTutorial);
        txtDialogScreen3 = (TextView)hello.getWindow().findViewById(R.id.txtTip3DialogTutorial);
        imgDialogScreen1 = (ImageView)hello.getWindow().findViewById(R.id.imgTip1DialogTutorial);
        imgDialogScreen2 = (ImageView)hello.getWindow().findViewById(R.id.imgTip2DialogTutorial);
        imgDialogScreen3 = (ImageView)hello.getWindow().findViewById(R.id.imgTip3DialogTutorial);

        hello.getWindow().setWindowAnimations(R.style.TutorialDialogAnimation);
        FontUtils.setNormalFont(this, txtDialogScreen1);
        FontUtils.setNormalFont(this, txtDialogScreen2);
        FontUtils.setNormalFont(this, txtDialogScreen3);
//        RelativeLayout.LayoutParams imglp = new RelativeLayout.LayoutParams(widthInPixels / 2, widthInPixels / 2);
//        imglp.addRule(RelativeLayout.CENTER_IN_PARENT);
//        imgDialogScreen1.setAdjustViewBounds(true);
//        imgDialogScreen1.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        imgDialogScreen1.setLayoutParams(imglp);
//        imgDialogScreen2.setAdjustViewBounds(true);
//        imgDialogScreen3.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        imgDialogScreen2.setLayoutParams(imglp);
//        imgDialogScreen3.setAdjustViewBounds(true);
//        imgDialogScreen2.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        imgDialogScreen3.setLayoutParams(imglp);

        String tip1String = getResources().getString(R.string.tip_one_black);
        String tip2String = getResources().getString(R.string.tip_two_black);
        String tip3String = getResources().getString(R.string.tip_three_black);

        String tip2Red = getResources().getString(R.string.tip_two_red);
        String mydytyfreeString = getResources().getString(R.string.MyDutyFree);

        Spannable tipOne = new SpannableString(tip1String);
        Spannable tipTwo = new SpannableString(tip2String);
        Spannable tipThree = new SpannableString(tip3String);

        tipOne.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorBlack)), 0, tip1String.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tipTwo.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorBlack)), 0, tip2String.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tipTwo.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorRed)), tip2String.indexOf(tip2Red), tip2String.indexOf(tip2Red) + tip2Red.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        tipTwo.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorRed)), tip2String.indexOf(mydytyfreeString), tip2String.indexOf(mydytyfreeString) + mydytyfreeString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
///        tipBlack.setSpan(new CustomTypefaceSpan("", MDFApplication.getBoldTypeface()), stringBlack.indexOf(stringRed), stringBlack.indexOf(stringRed) + stringRed.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        tipThree.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorBlack)), 0, tip3String.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tipThree.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorRed)), tip3String.indexOf(mydytyfreeString), tip3String.indexOf(mydytyfreeString) + mydytyfreeString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

//        Spannable tip1Black = new SpannableString(getResources().getString(R.string.tip_one_black));
//        Spannable tip2Black = new SpannableString(getResources().getString(R.string.tip_two_black));
//        Spannable tip3Black = new SpannableString(getResources().getString(R.string.tip_three_black));
//        Spannable tip1Red = new SpannableString(getResources().getString(R.string.tip_one_red));
//        Spannable tip2Red = new SpannableString(getResources().getString(R.string.tip_two_red));
//        Spannable tip3Red = new SpannableString(getResources().getString(R.string.tip_three_red));

//        tip1Black.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorBlack)), 0, tip1Black.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        tip1Red.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorRed)), 0, tip1Red.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        txtDialogScreen1.setText(tip1Black);
//        txtDialogScreen1.append(tip1Red);
        txtDialogScreen1.setText(tipOne);
//
//        tip2Black.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorBlack)), 0, tip2Black.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        tip2Red.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorRed)), 0, tip2Red.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        txtDialogScreen2.setText(tip2Black);
//        txtDialogScreen2.append(tip2Red);
        txtDialogScreen2.setText(tipTwo);
//
//        tip3Black.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorBlack)), 0, tip3Black.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        tip3Red.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorRed)), 0, tip3Red.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        txtDialogScreen3.setText(tip3Black);
//        txtDialogScreen3.append(tip3Red);
        txtDialogScreen3.setText(tipThree);

        scrollViewTutorial = (HorizontalScrollView)hello.getWindow().findViewById(R.id.scrollViewDialogTutorial);
        scrollViewTutorial = (HorizontalScrollView)hello.getWindow().findViewById(R.id.scrollViewDialogTutorial);
        scrollViewTutorial.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollX = scrollViewTutorial.getScrollX(); //for horizontalScrollView
                int scrollY = scrollViewTutorial.getScrollY(); //for verticalScrollView
                float currentPositionBottom = scrollViewTutorial.getScrollX();
                float pagesCountBottom = 3;
                float pageLengthInPxBottom = widthInPixels;
                float currentPageBottom = currentPositionBottom / pageLengthInPxBottom;
                float wholeScrollHeight = scrollViewTutorial.getChildAt(0).getHeight();
                float wholeScrollWidth = scrollViewTutorial.getChildAt(0).getWidth();

                float currentPosition = scrollViewTutorial.getScrollX();
                float pagesCount = 3;
                float pageLengthInPx = widthInPixels;
                float currentPage = currentPosition / pageLengthInPx;

                currentTutorialPage = (int) (Math.floor((currentPosition - pageLengthInPx / 2) / pageLengthInPx) + 1);
//                Log.e("page", String.format("%d", currentTutorialPage));
                if (currentTutorialPage == 0)
                    btnDialogNext.setText(getResources().getString(R.string.next_tip_caps));
                else if (currentTutorialPage == 1)
                    btnDialogNext.setText(getResources().getString(R.string.next_tip_caps));
                if (currentTutorialPage == 2)
                    btnDialogNext.setText(getResources().getString(R.string.get_started_caps));
            }
        });

        scrollViewTutorial.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        btnDialogNext = (Button)hello.getWindow().findViewById(R.id.btnNextDialogTutorial);
        btnDialogNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTutorialPage < 2) {
                    int page = currentTutorialPage;
                    int scroll = (int) (currentTutorialPage * widthInPixels);
                    ObjectAnimator objectAnimator = ObjectAnimator.ofInt(scrollViewTutorial, "scrollX", scrollViewTutorial.getScrollX(), (int) ((currentTutorialPage + 1) * widthInPixels)).setDuration(300);
                    objectAnimator.start();
                } else {
//                    ObjectAnimator objectAnimator = ObjectAnimator.ofInt(scrollViewTutorial, "scrollX", scrollViewTutorial.getScrollX(), 0).setDuration(300);
//                    objectAnimator.start();
                    ((MDFApplication)getApplication()).tutorialShowed();
                    hello.dismiss();
                }
            }
        });
        dialogScreen1.setLayoutParams(lp);
        dialogScreen2.setLayoutParams(lp);
        dialogScreen3.setLayoutParams(lp);
        hello.show();
    }

    public void createAndShowSelectTypeDialog() {
        mSelectStoreDialog = new Dialog(this, R.style.TutorialDialog);
        mSelectStoreDialog.setCancelable(false);
        WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displaymetrics);
        final int heightInPixels = displaymetrics.heightPixels;
        final int widthInPixels = displaymetrics.widthPixels;
//        View dialogView = new View(this);
        mSelectStoreDialog.setContentView(R.layout.dialog_select_travel_type);
//        hello.getWindow().setFlags(0x04000000, 0x04000000); // FLAG_TRANSLUCENT_STATUS

        Button btnAirport = (Button)mSelectStoreDialog.getWindow().findViewById(R.id.btnAirTypeDialogSelectTravelType);
        Button btnBus = (Button)mSelectStoreDialog.getWindow().findViewById(R.id.btnBusTypeDialogSelectTravelType);
        Button btnClose = (Button)mSelectStoreDialog.getWindow().findViewById(R.id.btnCloseDialogSelectTravelType);
        mStoresList = (ListView)mSelectStoreDialog.getWindow().findViewById(R.id.listDialogSelectTravelType);

        txtSelectTypeDialogTitle = (TextView) mSelectStoreDialog.getWindow().findViewById(R.id.txtTitleDialogSelectTravelType);
        FontUtils.setBoldFont(this, txtSelectTypeDialogTitle);
        rlSelType = (RelativeLayout) mSelectStoreDialog.getWindow().findViewById(R.id.rlSelectTypeDialogSelectTravelType);
        rlSelStore = (RelativeLayout) mSelectStoreDialog.getWindow().findViewById(R.id.rlSelectStoreDialogSelectTravelType);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStoreID = "0";
                ((MDFApplication) getApplication()).setAirport(mStoreID);
                ((MDFApplication) getApplication()).setIsAirport(true);
                mSelectStoreDialog.dismiss();
            }
        });

        btnAirport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplication(), "Fuck Off", Toast.LENGTH_SHORT).show();
                mDialogState = 2;
                ((MDFApplication) getApplication()).setIsAirport(true);
                createStoresDialog(true);
            }
        });

        btnBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplication(), "Another Fuck Off", Toast.LENGTH_SHORT).show();
                mDialogState = 2;
                ((MDFApplication) getApplication()).setIsAirport(false);
                createStoresDialog(false);

            }
        });

        mSelectStoreDialog.getWindow().setWindowAnimations(R.style.TutorialDialogAnimation);
        mSelectStoreDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                mDialogState = 1;
            }
        });
        mSelectStoreDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mDialogState = 0;
                Fragment fragment = new FragmentHome();
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                    transaction.replace(R.id.content_frame, fragment);
                    transaction.addToBackStack("main");
                    transaction.commit();
                } else {
                    // Error
                    Log.e(this.getClass().getName(), "Error. Fragment is not created");
                }
            }
        });
        mSelectStoreDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mDialogState = 0;
            }
        });
        mSelectStoreDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mDialogState == 2) {
                        mDialogState = 1;
                        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
                        animation.setDuration(500);
                        rlSelType.startAnimation(animation);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                rlSelType.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        return true;
                    }
                }
                return false;
            }
        });

        mSelectStoreDialog.show();
    }

    public void createStoresDialog(Boolean isAirport) {
        mStoresList.setAdapter(null);
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(400);
        rlSelType.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                rlSelType.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        final String curLang = ((MDFApplication) getApplication()).getCurrentLanguage();
        RetrofitService retrofitService = new RetrofitService();
        PostInterfaceApi service = retrofitService.create();
        JsonObject jsonObject = null;
                if (isAirport)
                    jsonObject = JsonCreator.GetStoresAir();
                else
                    jsonObject = JsonCreator.GetStoresBorder();

        Call<GetStores> callStores = service.getStores(jsonObject);
        showProgress();
        callStores.enqueue(new Callback<GetStores>() {
            @Override
            public void onResponse(Call<GetStores> call, Response<GetStores> response) {
                Log.e("!!!", "!!!");
                hideProgress();
                Map<String, Map<String, String>> mapStores = response.body().getData();
                ((MDFApplication) getApplication()).setStores(response.body());

                // Convert Map to List
                List<Map.Entry<String, Map<String, String>>> list = new LinkedList<>(mapStores.entrySet());

                // Sort list with comparator, to compare the Map values
                Collections.sort(list, new Comparator<Map.Entry<String, Map<String, String>>>() {
                    public int compare(Map.Entry<String, Map<String, String>> o1,
                                       Map.Entry<String, Map<String, String>> o2) {
                        return (o1.getKey()).compareTo(o2.getKey());
                    }
                });
                mStoreNames = new ArrayList<String>();
                mStoreCodes = new ArrayList<String>();
                for (Iterator<Map.Entry<String, Map<String, String>>> it = list.iterator(); it.hasNext(); ) {
                    Map.Entry<String, Map<String, String>> entry = it.next();
                    Map<String, String> namesStore = entry.getValue();
                    if (namesStore.get(curLang) != null) {
                        mStoreNames.add(namesStore.get(curLang));
                        mStoreCodes.add(entry.getKey());
                        Log.e("!!!", "!!!");
                    } else {
                        Log.e("!!!", "!!!");
                    }
                }
                mStoresList.setAdapter(new StoreListAdapter(mContext, mStoreNames));
                mStoresList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Toast.makeText(mContext, mStoreNames.get(position), Toast.LENGTH_SHORT).show();
                        mStoreID = mStoreCodes.get(position);
                        CartSingleton.getInstance().ClearCart();
                        String projectToken = Const.MIXPANEL_PROJECT_TOKEN;
                        MixpanelAPI mixpanel = MixpanelAPI.getInstance(mContext, projectToken);
                        try {
                            JSONObject properties = new JSONObject();
                            properties.put("airport", mStoreNames.get(mStoreCodes.indexOf(mStoreID)));
                            mixpanel.track("Store Select Airport", properties);
                        } catch (JSONException e) {}
                        ((MDFApplication) getApplication()).setAirport(mStoreID);
                        RetrofitService retrofitService = new RetrofitService();
                        PostInterfaceApi service = retrofitService.create();
                        Call<GetLanguagesResponse> call = service.getLanguages(JsonCreator.GetLanguages());
                        showProgress();
                        call.enqueue(new Callback<GetLanguagesResponse>() {
                            @Override
                            public void onResponse(Call<GetLanguagesResponse> call, Response<GetLanguagesResponse> response) {
                                Log.e("!!!", "!!!");
                                String curLanguage = ((MDFApplication) getApplication()).getCurrentLanguage();
                                ArrayList<String> mLanguagesCodesList;
                                Map<String, String> mLanguagesMap = response.body().getData();
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
                                for (Iterator<Map.Entry<String, String>> it = list.iterator(); it.hasNext();) {
                                    Map.Entry<String, String> entry = it.next();
                                    mLanguagesCodesList.add(entry.getKey());
                                }

                                if (!mLanguagesCodesList.contains(curLanguage)) {
                                    curLanguage = "ru";
                                    ((MDFApplication) getApplication()).setCurrentLanguage(curLanguage);
                                    Locale locale2 = new Locale(curLanguage);
                                    Locale.setDefault(locale2);
                                    Configuration config2 = new Configuration();
                                    config2.locale = locale2;
                                    getResources().updateConfiguration(config2, null);
                                }

                                mSelectStoreDialog.dismiss();
                                hideProgress();
                            }
                            @Override
                            public void onFailure(Call<GetLanguagesResponse> call, Throwable t) {
                                Log.e("!!!", "error!!!");
                                hideProgress();
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<GetStores> call, Throwable t) {
                Log.e("!!!", "error!!!");
                hideProgress();
            }
        });
    }

    public void createAndShowSelectStoreDialog(Boolean isAirport) {
        final String curLang = ((MDFApplication) getApplication()).getCurrentLanguage();
        RetrofitService retrofitService = new RetrofitService();
        PostInterfaceApi service = retrofitService.create();
        JsonObject jsonObject = null;
        if (isAirport)
            jsonObject = JsonCreator.GetStoresAir();
        else
            jsonObject = JsonCreator.GetStoresBorder();

        Call<GetStores> callStores = service.getStores(jsonObject);
        showProgress();
        callStores.enqueue(new Callback<GetStores>() {
            @Override
            public void onResponse(Call<GetStores> call, Response<GetStores> response) {
                Log.e("!!!", "!!!");
                hideProgress();
                Map<String, Map<String, String>> mapStores = response.body().getData();
                ((MDFApplication)getApplication()).setStores(response.body());

                // Convert Map to List
                List<Map.Entry<String, Map<String, String>>> list = new LinkedList<>(mapStores.entrySet());

                // Sort list with comparator, to compare the Map values
                Collections.sort(list, new Comparator<Map.Entry<String, Map<String, String>>>() {
                    public int compare(Map.Entry<String, Map<String, String>> o1,
                                       Map.Entry<String, Map<String, String>> o2) {
                        return (o1.getKey()).compareTo(o2.getKey());
                    }
                });
                mStoreNames = new ArrayList<String>();
                mStoreCodes = new ArrayList<String>();
                for (Iterator<Map.Entry<String, Map<String, String>>> it = list.iterator(); it.hasNext();) {
                    Map.Entry<String, Map<String, String>> entry = it.next();
                    Map<String, String> namesStore = entry.getValue();
                    if (namesStore.get(curLang) != null) {
                        mStoreNames.add(namesStore.get(curLang));
                        mStoreCodes.add(entry.getKey());
                        Log.e("!!!", "!!!");
                    }
                    else {
                        Log.e("!!!", "!!!");
                    }
                }

                final String[] strAirports = mStoreNames.toArray(new String[0]);
                FragmentCheckout.CustomNumberPicker airportsPicker = new FragmentCheckout.CustomNumberPicker(mContext);
                airportsPicker.setMinValue(0);
                airportsPicker.setMaxValue(strAirports.length - 1);
                airportsPicker.setValue(0);
                airportsPicker.setDisplayedValues(strAirports);
                airportsPicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

                oldValue = strAirports[0];
                value = strAirports[0];
                mStoreID = "1";
                NumberPicker.OnValueChangeListener myValChangedListener = new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                        Toast.makeText(getContext(), strs[newVal], Toast.LENGTH_SHORT).show();
                        value = strAirports[newVal];
                        oldValue = strAirports[oldVal];
                        mStoreID = mStoreCodes.get(newVal);
                    }
                };

                airportsPicker.setOnValueChangedListener(myValChangedListener);

                AlertDialog.Builder selectAirportDialogBuilder = new AlertDialog.Builder(mContext, android.support.v7.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert).setView(airportsPicker);
                selectAirportDialogBuilder.setCancelable(false);
                selectAirportDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
//                Toast.makeText(mContext, "cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                Spannable messageNormal = new SpannableString(getResources().getString(R.string.select_airport_message_normal) + " ");
                Spannable messageBold = new SpannableString(getResources().getString(R.string.select_airport_message_bold));

                Typeface normalFont = Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-Display-Regular.otf");
                Typeface boldFont = Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-Display-Bold.otf");

                messageNormal.setSpan (new CustomTypefaceSpan("", normalFont), 0, messageNormal.length(),Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                messageBold.setSpan (new CustomTypefaceSpan("", boldFont), 0, messageBold.length(),Spanned.SPAN_EXCLUSIVE_INCLUSIVE);


                Spannable titleSpan = new SpannableString(getResources().getString(R.string.select_airport_title));
                titleSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorRed)), 0, titleSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                titleSpan.setSpan(new CustomTypefaceSpan("", boldFont), 0, titleSpan.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                titleNormal.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorRed)), 0, tip1Red.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                txtDialogScreen1.setText(tip1Black);
//                txtDialogScreen1.append(tip1Red);


                selectAirportDialogBuilder.setTitle(titleSpan);
//                selectAirportDialogBuilder.setMessage(getResources().getString(R.string.select_airport_message));
                selectAirportDialogBuilder.setMessage(TextUtils.concat(messageNormal, messageBold));
                selectAirportDialogBuilder.setNeutralButton(getResources().getString(R.string.look_around), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(mContext, oldValue, Toast.LENGTH_SHORT).show();
                        mStoreID = "0";
                        CartSingleton.getInstance().ClearCart();
                        ((MDFApplication) getApplication()).setAirport(mStoreID);
//                        mTracker.send(new HitBuilders.EventBuilder()
//                                .setCategory("ui_action")
//                                .setAction("Select airport on start")
//                                .setLabel("Lookup click")
//                                .build());
                        String projectToken = Const.MIXPANEL_PROJECT_TOKEN;
                        MixpanelAPI mixpanel = MixpanelAPI.getInstance(mContext, projectToken);
                        try {
                            JSONObject properties = new JSONObject();
                            properties.put("airport", "Lookup");
                            mixpanel.track("Store Select Airport", properties);
                        } catch (JSONException e) {}

//                Toast.makeText(mContext, "store = " + mStoreID, Toast.LENGTH_SHORT).show();
                    }
                });
                TextView titleText = new TextView(getApplicationContext());
                titleText.setPadding(20, 30, 20, 0);
                FontUtils.setBoldFont(mContext, titleText, 14);
                titleText.setGravity(Gravity.CENTER_HORIZONTAL);
                titleText.setText(titleSpan);
                selectAirportDialogBuilder.setCustomTitle(titleText);

                selectAirportDialogBuilder.setPositiveButton(getResources().getString(R.string.proceed_caps), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        if (mStoreID.equalsIgnoreCase("3") ) {
//                            if (curLanguage.equalsIgnoreCase("uk")) {
//                                curLanguage = "ru";
//                                ((MDFApplication)getApplication()).setCurrentLanguageRus();
//                                Locale locale2 = new Locale(curLanguage);
//                                Locale.setDefault(locale2);
//                                Configuration config2 = new Configuration();
//                                config2.locale = locale2;
//                                mContext.getResources().updateConfiguration(config2, null);
//                            }
//                        }
                        CartSingleton.getInstance().ClearCart();
                        String projectToken = Const.MIXPANEL_PROJECT_TOKEN;
                        MixpanelAPI mixpanel = MixpanelAPI.getInstance(mContext, projectToken);
                        try {
                            JSONObject properties = new JSONObject();
                            properties.put("airport", mStoreNames.get(mStoreCodes.indexOf(mStoreID)));
                            mixpanel.track("Store Select Airport", properties);
                        } catch (JSONException e) {}
                        ((MDFApplication) getApplication()).setAirport(mStoreID);
                        RetrofitService retrofitService = new RetrofitService();
                        PostInterfaceApi service = retrofitService.create();
                        Call<GetLanguagesResponse> call = service.getLanguages(JsonCreator.GetLanguages());
                        showProgress();
                        call.enqueue(new Callback<GetLanguagesResponse>() {
                            @Override
                            public void onResponse(Call<GetLanguagesResponse> call, Response<GetLanguagesResponse> response) {
                                Log.e("!!!", "!!!");
                                String curLanguage = ((MDFApplication) getApplication()).getCurrentLanguage();
                                ArrayList<String> mLanguagesCodesList;
                                Map<String, String> mLanguagesMap = response.body().getData();
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
                                for (Iterator<Map.Entry<String, String>> it = list.iterator(); it.hasNext();) {
                                    Map.Entry<String, String> entry = it.next();
                                    mLanguagesCodesList.add(entry.getKey());
                                }

                                if (!mLanguagesCodesList.contains(curLanguage)) {
                                    curLanguage = "ru";
                                    ((MDFApplication) getApplication()).setCurrentLanguage(curLanguage);
                                    Locale locale2 = new Locale(curLanguage);
                                    Locale.setDefault(locale2);
                                    Configuration config2 = new Configuration();
                                    config2.locale = locale2;
                                    getResources().updateConfiguration(config2, null);
                                }

                                hideProgress();
                            }
                            @Override
                            public void onFailure(Call<GetLanguagesResponse> call, Throwable t) {
                                Log.e("!!!", "error!!!");
                                hideProgress();
                            }
                        });
                        mTracker.send(new HitBuilders.EventBuilder()
                                .setCategory("ui_action")
                                .setAction("Select airport on start")
                                .setLabel(value)
                                .build());
                    }
                });

                final AlertDialog selectAirportDialog = selectAirportDialogBuilder.create();
                selectAirportDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Fragment fragment = new FragmentHome();
                        if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                            transaction.replace(R.id.content_frame, fragment);
                            transaction.addToBackStack("main");
                            transaction.commit();
                        } else {
                            // Error
                            Log.e(this.getClass().getName(), "Error. Fragment is not created");
                        }
                    }
                });
                selectAirportDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        //TODO: remove this one
                        int titleId = getResources().getIdentifier("alertTitle", "id", "android");
//                        if (titleId > 0) {
//                            TextView titleText = (TextView) selectAirportDialog.findViewById(android.R.id.title);
//                            if (titleText != null) {
//                                FontUtils.setBoldFont(mContext, titleText, 10);
//                                titleText.setGravity(Gravity.CENTER_HORIZONTAL);
//                            }
//                        }
                        TextView messageText = (TextView) selectAirportDialog.findViewById(android.R.id.message);
//                        Button btnPositive = selectAirportDialog.getButton(Dialog.BUTTON_POSITIVE);
//                        Button btnNegative = selectAirportDialog.getButton(Dialog.BUTTON_NEGATIVE);
                        messageText.setGravity(Gravity.CENTER_HORIZONTAL);

//                        FontUtils.setBoldFont(mContext, btnPositive, 14);
//                        FontUtils.setBoldFont(mContext, btnNegative, 14);
                        FontUtils.setNormalFont(mContext, messageText, 14);
                    }
                });
                selectAirportDialog.show();
            }

            @Override
            public void onFailure(Call<GetStores> call, Throwable t) {
                Log.e("!!!", "error!!!");
                hideProgress();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Branch branch = Branch.getInstance();

//        branch.initSession(new Branch.BranchUniversalReferralInitListener() {
//            @Override
//            public void onInitFinished(BranchUniversalObject branchUniversalObject, LinkProperties linkProperties, BranchError error) {
//                if (error == null) {
//                    String projectToken = Const.MIXPANEL_PROJECT_TOKEN;
//                    MixpanelAPI mixpanel = MixpanelAPI.getInstance(mContext, projectToken);
//                    mixpanel.track("App Install buo+", branchUniversalObject.convertToJson());
//                } else {
//                    Log.i("MyApp", error.getMessage());
//                }
//            }
//        }, this.getIntent().getData(), this);
        branch.initSession(new Branch.BranchReferralInitListener(){
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    String projectToken = Const.MIXPANEL_PROJECT_TOKEN;
//                    String ttt = null;
                    MixpanelAPI mixpanel = MixpanelAPI.getInstance(mContext, projectToken);
                    try {
//                    ttt = referringParams.optString("nextKey");
                    if (referringParams.getString("+is_first_session").equalsIgnoreCase("true")) {
                        mixpanel.track("App Install", referringParams);
                    }

                    } catch (JSONException e) {}

                    // params are the deep linked params associated with the link that the user clicked -> was re-directed to this app
                    // params will be empty if no data found
                    // ... insert custom logic here ...
                } else {
                    Log.i("MyApp", error.getMessage());
                }
            }
        }, this.getIntent().getData(), this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                AdvertisingIdClient.Info idInfo = null;
                try {
                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String advertId = null;
                try{
                    advertId = idInfo.getId();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return advertId;
            }
            @Override
            protected void onPostExecute(String advertId) {
//                Toast.makeText(getApplicationContext(), advertId, Toast.LENGTH_SHORT).show();
//                String distinctId = mixpanel.getDistinctId();
                mixpanel.identify(advertId);
                final MixpanelAPI.People people = mixpanel.getPeople();
                people.identify(advertId);
            }
        };
        task.execute();
        setContentView(R.layout.activity_main);
        if (isOnline()==false) { MainActivity.this.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)); }
        mProgressDialog = new MDFProgressDialog(this, false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        mProgressDialogLinkCount = 0;

        mTracker = ((MDFApplication)getApplication()).getDefaultTracker();
//        String campaignData = "https://play.google.com/store/apps/details?id=llc.net.mydutyfree.newmdf&referrer=utm_source%3DTESTMYCAMPF%26utm_campaign%3DMYCAMPF";

//        mTracker.send(new HitBuilders.ScreenViewBuilder()
//                        .setCampaignParamsFromUrl(campaignData)
//                        .build());
        isDrawerOpened = false;
        mContext = this;

        String projectToken = Const.MIXPANEL_PROJECT_TOKEN;
        mixpanel = MixpanelAPI.getInstance(this, projectToken);

//        String projectToken = Const.MIXPANEL_PROJECT_TOKEN;
//        MixpanelAPI mixpanel = MixpanelAPI.getInstance(this, projectToken);
//        String distinctId = mixpanel.getDistinctId();
//        mixpanel.identify(distinctId);
//        final MixpanelAPI.People people = mixpanel.getPeople();
//        people.identify(distinctId);

//        mixpanel.getPeople().identify(mixpanel.getPeople().getDistinctId());
//        mixpanel.alias("Email@test.test", null);
//        people.setOnce("Day of first order", "day");
////        mixpanel.getPeople().increment("LTV", CartSingleton.getInstance().getTotalAmount().get("EUR"));
////        mixpanel.getPeople().set("Currency", ((MDFApplication) this.getApplication()).getCurrentCurrency());
//        people.set("$first_name", "FName");
//        people.set("$last_name", "LName");
//        people.set("$phone", "Phone");
//        people.set("$email", "Email@test.test");
//        mixpanel.getPeople().increment("Number of trips", 1);

//        try {
//            JSONObject props = new JSONObject();
//            props.put("Gender", "Female");
//            props.put("Logged in", false);
//            mixpanel.track("MainActivity - onCreate called", props);
//        } catch (JSONException e) {
//            Log.e("MYAPP", "Unable to add properties to JSONObject", e);
//        }

//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//// set your desired log level
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//// add your other interceptors …
//// add logging as last interceptor
//        httpClient.addInterceptor(logging);  // <-- this is the important line!
//        Retrofit client = new Retrofit.Builder()
//                .baseUrl("http://app.mydutyfree.net/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(httpClient.build())
//                .build();
//        PostInterface service = client.create(PostInterface.class);
//
//        JSONObject Jrequest = new JSONObject();
//        try {
//            Jrequest.accumulate("auth_key", "wu81dmvj34divn38dn2*2f");
//            Jrequest.accumulate("body", new JSONObject());
//            Jrequest.accumulate("lang_code", "ru");
//            Jrequest.accumulate("store_id", 1);
//            Jrequest.accumulate("action", "content\\/get-languages");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        Map<String, String> jsonMap = new HashMap<String, String>();
//        jsonMap.put("auth_key", "wu81dmvj34divn38dn2*2f");
//        jsonMap.put("body", new HashMap<String, String>().toString());
//        jsonMap.put("lang_code", "ru");
//        jsonMap.put("store_id", "1");
//        jsonMap.put("action", "content/get-languages");

//        char solidus = "\\u005C".toCharArray();
//        String sss = Character.toString((char)47);
//
//        JsonObject requestJson = new JsonObject();
//        JsonObject body = new JsonObject();
//        requestJson.addProperty("lang_code", "ru");
//        requestJson.addProperty("store_id", "1");
//        requestJson.addProperty("action", "content/get-languages");
//        requestJson.addProperty("auth_key", "wu81dmvj34divn38dn2*2f");
//        requestJson.add("body", body);
//
//        JsonObject obj = JsonCreator.GetStores();
//
//        Context cnts = MDFApplication.getAppContext();
//        MDFApplication app = MDFApplication.getMDFApplication();
//        Context cntx = getApplicationContext();
//
//        JsonObject getNewArrivalsJson = new JsonObject();
//        body = new JsonObject();
//        JsonObject filtr = new JsonObject();
//        getNewArrivalsJson.addProperty("lang_code", "ru");
//        getNewArrivalsJson.addProperty("store_id", "1");
//        getNewArrivalsJson.addProperty("action", Const.Actions.Products.GET_FILTERED);
//        getNewArrivalsJson.addProperty("auth_key", Const.API_KEY);
//        filtr.addProperty("limit", 3);
//        filtr.addProperty("extra_flag", "new");
//        body.add("filter", filtr);
//        getNewArrivalsJson.add("body", body);

//        Call<ServerResponse> call = service.getLanguages(body, Const.API_KEY, Const.Actions.Content.GET_LANGUAGES, "1", "ru");

//        Call<GetLanguagesResponse> call = service.getLanguages(requestJson);
//        showProgress();
//        Call<GetNewArrivalsResponse> callNewArrivals = service.getNewArrivals(getNewArrivalsJson);

//        call.enqueue(new Callback<GetLanguagesResponse>() {
//            @Override
//            public void onResponse(Call<GetLanguagesResponse> call, Response<GetLanguagesResponse> response) {
//                Log.e("!!!", "!!!");
////                ((MDFApplication)getApplication()).setLanguages(response.body().getData());
//                hideProgress();
//            }
//
//            @Override
//            public void onFailure(Call<GetLanguagesResponse> call, Throwable t) {
//                Log.e("!!!", "error!!!");
//                hideProgress();
//            }
//        });

//        callNewArrivals.enqueue(new Callback<GetNewArrivalsResponse>() {
//            @Override
//            public void onResponse(Call<GetNewArrivalsResponse> call, Response<GetNewArrivalsResponse> response) {
//                Log.e("!!!", "!!!");
//
//            }
//
//            @Override
//            public void onFailure(Call<GetNewArrivalsResponse> call, Throwable t) {
//                Log.e("!!!", "error!!!");
//
//            }
//        });

        new Thread(new Runnable() {
            public void run() {
                try {
                    InstanceID instanceID = InstanceID.getInstance(mContext);
                    String token = instanceID.getToken("459984876923",  GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

//                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//                    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//                    httpClient.addInterceptor(logging);
//                    Retrofit client = new Retrofit.Builder()
//                            .baseUrl("http://app.mydutyfree.net/")
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .client(httpClient.build())
//                            .build();
//                    PostInterface service = client.create(PostInterface.class);
//                    String curLang = ((MDFApplication) getApplication()).getCurrentLanguage();
//                    JsonObject requestSaveDeviceTokenJson = new JsonObject();
//                    JsonObject bodySaveDeviceToken = new JsonObject();
//                    bodySaveDeviceToken.addProperty("token", token);
//                    requestSaveDeviceTokenJson.addProperty("lang_code", curLang);
//                    requestSaveDeviceTokenJson.addProperty("store_id", "1");
//                    requestSaveDeviceTokenJson.addProperty("action", Const.Actions.Users.SAVE_DEVICE_TOKEN);
//                    requestSaveDeviceTokenJson.addProperty("auth_key", Const.API_KEY);
//                    requestSaveDeviceTokenJson.add("body", bodySaveDeviceToken);
                    RetrofitService retrofitService = new RetrofitService();
                    PostInterfaceApi service = retrofitService.create();
                    Call<SaveDeviceToken> callSaveDeviceToken = service.saveDeviceToken(JsonCreator.SaveDeviceToken(token));

                    callSaveDeviceToken.enqueue(new Callback<SaveDeviceToken>() {
                        @Override
                        public void onResponse(Call<SaveDeviceToken> call, Response<SaveDeviceToken> response) {
                            Log.e("!!TOKEN!!", "SAVED");

                        }

                        @Override
                        public void onFailure(Call<SaveDeviceToken> call, Throwable t) {
                            Log.e("!!TOKEN!!", "NOT SAVED");

                        }
                    });
                    Log.e("!!TOKEN!!", token);
//                    Toast.makeText(mContext, token, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("!!TOKEN ERROR", e.getLocalizedMessage());
                }
            }
        }).start();

//        final String[] strAirports = getResources().getStringArray(R.array.airports_array);

        JSONObject bodyGetLanguages = new JSONObject();
        new AsyncRequest((MDFApplication) this.getApplication(), Const.URLS.URL, Const.Actions.Content.GET_LANGUAGES, bodyGetLanguages).execute();

        if (((MDFApplication)getApplication()).isTutorialShowed()) {
            createAndShowSelectTypeDialog();
        }
        else {
            createAndShowTutorialDialog();
        }

        String URL = "http://api.mydutyfree.net?key=GTg7hgrRF43D5fgTGHN";

        String actionGetNewArrivals = "products/get-filtered";
        String actionGetTopSellers = "products/get-filtered";
        String actionGetBanners = "content/get-banners";
        String actionGetCategoriesAll = "categories/get-all";

        JSONObject bodyGetNewArrivals = new JSONObject();
        try {
            JSONObject filter = new JSONObject();
            filter.accumulate("category_id", "4");
            filter.accumulate("limit", "8");
            bodyGetNewArrivals.accumulate("filter", filter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject bodyGetTopSellers = new JSONObject();
        try {
            JSONObject filter = new JSONObject();
            filter.accumulate("category_id", "3");
            filter.accumulate("limit", "8");
            bodyGetTopSellers.accumulate("filter", filter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject bodyGetBanners = new JSONObject();
//        updateCategoriesMenu();
//        String URL = "http://api.mydutyfree.net?key=GTg7hgrRF43D5fgTGHN";
//        String action = "categories/get-all";
//
//        connect(URL);

//        HttpPost post = new HttpPost(URL);
//        JSONObject request = new JSONObject();
//
//        try {
//            String result = null;
//            Entity entity = new Entity(ContentValues.PARCELABLE_WRITE_RETURN_VALUE);
//            Charset chars = Charset.forName("UTF-8");
//
//            request.put("action", action);
//            request.put("body", new JSONObject());
//            request.put("store_id", "5");
//
//
//            String requestStr = request.toString();
//            //Log.i("SendRequet", requestStr);
//
//            StringBody body = new StringBody(requestStr, chars);
//            FormBodyPart part = new FormBodyPart("body", body);
//            entity.addPart(part);
//
//            post.setEntity(entity);
//
//            HttpResponse response = HttpClientFactory.getThreadSafeClient(30000).execute(post);
//            result = EntityUtils.toString(response.getEntity());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        mTitle = mDrawerTitle = getTitle();
        mScreenTitles = getResources().getStringArray(R.array.screen_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
//        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
//                R.layout.drawer_list_item, mScreenTitles));
        categoriesMenuAdapter = new CategoriesMenuAdapter(this, null);
        mDrawerList.setAdapter(categoriesMenuAdapter);


        // Set the list's click listener
//        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        categoriesMenuItemLickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isFilter) {
                    Fragment fragment = null;
                    if ((position > 2) && (position < categoriesList.size() + 3)) {
                        Category category = categoriesList.get(position - 3);
                        fragment = new FragmentCategory();
                        ((FragmentCategory) fragment).setCategory(category);
//                    setTitle(category.getName());
                    }
                    if (position == categoriesList.size() + 3) {
                        fragment = new FragmentCustomerSupport();
                        mDrawerLayout.closeDrawer(mDrawerList);
//                    setTitle("Customer Support");
                    } else if (position == 0) {
                        getSupportFragmentManager().popBackStack("main", 0);
                    } else if (position == 1) {
                        fragment = new FragmentCategory();
                        ((FragmentCategory) fragment).setExtraCategoryType(FragmentCategory.ExtraCategoryType.SPECIAL_OFFERS);
//                    setTitle(getResources().getString(R.string.special_offers));
                    } else if (position == 2) {
                        fragment = new FragmentCategory();
                        ((FragmentCategory) fragment).setExtraCategoryType(FragmentCategory.ExtraCategoryType.TOP_SELLERS);
//                    setTitle(getResources().getString(R.string.top_sellers));
                    }
//                    else if (position == 3) {
//                        fragment = new FragmentLogin();
////                        ((FragmentCategory) fragment).setExtraCategoryType(FragmentCategory.ExtraCategoryType.NEW_ARRIVALS);
////                    setTitle(getResources().getString(R.string.new_arrivals));
//                    }
                    if (fragment != null) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                        transaction.replace(R.id.content_frame, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    mDrawerLayout.closeDrawer(mDrawerList);
                }
            }
        };

        mDrawerList.setOnItemClickListener(categoriesMenuItemLickListener);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
//                R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open, /* "open drawer" description */
                R.string.drawer_close /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                if (mDrawerList.getAdapter() != categoriesMenuAdapter) {
                    mDrawerList.setAdapter(categoriesMenuAdapter);
                    mDrawerList.setOnItemClickListener(categoriesMenuItemLickListener);
                }
                isFilter = false;
                isDrawerOpened = false;
                setTitle(mTitle);

                Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/SF-UI-Display-Regular.otf");
                SpannableStringBuilder SS = new SpannableStringBuilder("MY Actionbar Tittle");
                SS.setSpan (new CustomTypefaceSpan("", font2), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                getSupportActionBar().setTitle(SS);

                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has setted in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                isDrawerOpened = true;

                setTitle(mDrawerTitle);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        // Initialize the first fragment when the application first loads.
//        if (savedInstanceState == null) {
////            selectItem(0);
//            //
//            Fragment fragment = new FragmentHome();
//            if (fragment != null) {
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
//                transaction.replace(R.id.content_frame, fragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
//            } else {
//                // Error
//                Log.e(this.getClass().getName(), "Error. Fragment is not created");
//            }
//            //
//        }

//       HttpClient httpclient = new DefaultHttpClient();
//        HttpResponse response = httpclient.execute(new HttpGet(URL));
//        StatusLine statusLine = response.getStatusLine();
//        if(statusLine.getStatusCode() == HttpStatus.SC_OK){
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            response.getEntity().writeTo(out);
//            String responseString = out.toString();
//            out.close();
//            //..more logic
//        } else{
//            //Closes the connection.
//            response.getEntity().getContent().close();
//            throw new IOException(statusLine.getReasonPhrase());
//        }

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (isDrawerOpened== true) {
                    mDrawerLayout.closeDrawer(mDrawerList);
                }
                supportInvalidateOptionsMenu();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ArrayList<Fragment> fList = (ArrayList) fragmentManager.getFragments();
                if (fList != null) {
                    Fragment lastFragment = fList.get(fList.size() - 1);
                    if (lastFragment != null) {
                        if (lastFragment.getClass() == FragmentSettings.class) {
//                            Toast.makeText(getApplicationContext(), "Settings",Toast.LENGTH_SHORT).show();
                        }
                        else if (lastFragment.getClass() == FragmentHome.class){
//                            Toast.makeText(getApplicationContext(), "Home",Toast.LENGTH_SHORT).show();
                        }
                        else if (lastFragment.getClass() == FragmentItem.class){
//                            Toast.makeText(getApplicationContext(), "Item",Toast.LENGTH_SHORT).show();
                        }
                        else if (lastFragment.getClass() == FragmentCategory.class){
//                            Toast.makeText(getApplicationContext(), "Category",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public static void connect(String url)
    {

        HttpClient httpclient = new DefaultHttpClient();

        // Prepare a request object
        HttpGet httpget = new HttpGet(url);

        // Execute the request
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            // Examine the response status
            Log.i("Praeda",response.getStatusLine().toString());

            // Get hold of the response entity
            HttpEntity entity = response.getEntity();
            // If the response does not enclose an entity, there is no need
            // to worry about connection release

            Log.e("dddddd", entity.toString());
            if (entity != null) {

                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                String result= convertStreamToString(instream);
                // now you have the string representation of the HTML request
                Log.e("dddddd", result);

                instream.close();
            }

        } catch (Exception e) {}
    }

    private static String convertStreamToString(InputStream is) {
    /*
     * To convert the InputStream to String we use the BufferedReader.readLine()
     * method. We iterate until the BufferedReader return null which means
     * there's no more data to read. Each line will appended to a StringBuilder
     * and returned as String.
     */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void setupSearchView() {

        mSearchView.setIconifiedByDefault(true);

//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        if (searchManager != null) {
//            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();
//
//            // Try to use the "applications" global search provider
//            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
//            for (SearchableInfo inf : searchables) {
//                if (inf.getSuggestAuthority() != null
//                        && inf.getSuggestAuthority().startsWith("applications")) {
//                    info = inf;
//                }
//            }
//            mSearchView.setSearchableInfo(info);
//        }
//
//        mSearchView.setOnQueryTextListener(this);
//        mSearchView.setOnCloseListener(this);
    }

    public boolean onQueryTextChange(String newText) {
//        mStatusView.setText("Query = " + newText);
        return false;
    }

    public boolean onQueryTextSubmit(String query) {
//        mStatusView.setText("Query = " + query + " : submitted");
        return true;
    }

    public boolean onClose() {
//        mStatusView.setText("Closed!");
        return false;
    }

    public void onClick(View view) {
//        if (view == mCloseButton) {
//            mSearchView.setIconified(true);
//        } else if (view == mOpenButton) {
//            mSearchView.setIconified(false);
//        }
    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
//        mSearchView = (SearchView) menu.findItem(R.id.menu_action_search).getActionView();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView = (SearchView) menu.findItem(R.id.menu_action_search).getActionView();
            menuItem = menu.findItem(R.id.menu_action_search);
            int[][] states = new int[][] {
                    new int[] { android.R.attr.state_enabled}, // enabled
                    new int[] {-android.R.attr.state_enabled}, // disabled
                    new int[] {-android.R.attr.state_checked}, // unchecked
                    new int[] { android.R.attr.state_pressed}  // pressed
            };

            int[] colors = new int[] {
                    Color.BLACK,
                    Color.RED,
                    Color.GREEN,
                    Color.YELLOW
            };

            ColorStateList myList = new ColorStateList(states, colors);
            GradientDrawable shape = new GradientDrawable();
            shape.setShape(GradientDrawable.RECTANGLE);
            shape.setCornerRadii(new float[]{8, 8, 8, 8, 8, 8, 8, 8});
            shape.setColor(Color.WHITE);
            shape.setStroke(1, Color.LTGRAY);
            searchView.setBackground(shape);

            EditText text = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            if (text != null) {
                text.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
                text.setHintTextColor(ContextCompat.getColor(this, R.color.colorGray));
                text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus == false) {
                            searchView.onActionViewCollapsed();
                            menuItem.collapseActionView();
                        }
//                            onBackPressed();
                    }
                });

            }
//            search.set
            //Applies white color on searchview text
//            int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
//            TextView textView = (TextView) search.findViewById(id);
//            textView.setTextColor(Color.GREEN);

            searchView.setIconified(true);
            searchView.setIconifiedByDefault(true);
            searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
//                    Toast.makeText(getApplicationContext(), "Submit",Toast.LENGTH_SHORT).show();
//                    MenuItemCompat.collapseActionView((MenuItem) search);
                    searchView.onActionViewCollapsed();
                    menuItem.collapseActionView();
                    if (isDrawerOpened== true) {
                        mDrawerLayout.closeDrawer(mDrawerList);
                    }
                    Fragment fragment = new FragmentCategory();
                        ((FragmentCategory)fragment).setSearchString(query);
                        setTitle("Search for \"" + query + "\"");
                    if (fragment != null) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                        transaction.replace(R.id.content_frame, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {

//                    loadHistory(query);

                    return true;

                }

            });

        }

//        setupSearchView();
        menu.findItem(R.id.menu_action_cart).setActionView(R.layout.action_bar_cart_icon);
//        MenuItemCompat cart = (MenuItemCompat)menu.findItem(R.id.menu_action_cart);
//        final ActionMenuView menu_hotlist = MenuItemCompat.getActionView(menu.findItem(R.id.menu_action_cart));;//.getActionView();
        final View menu_hotlist = menu.findItem(R.id.menu_action_cart).getActionView();
        menu_hotlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "cart", Toast.LENGTH_SHORT).show();
                Fragment fragment = new FragmentCart();
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                    transaction.replace(R.id.content_frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
        ui_hot = (TextView) menu_hotlist.findViewById(R.id.hotlist_hot);
        updateHotCount(0);
//        new MyMenuItemStuffListener(menu_hotlist, "Show hot message") {
//            @Override
//            public void onClick(View v) {
//                onHotlistSelected();
//            }
//        };
        return super.onCreateOptionsMenu(menu);
    }

    public void updateHotCount(final int new_hot_number) {
        hot_number = new_hot_number;
        if (ui_hot == null) return;
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
                if (new_hot_number == 0)
                    ui_hot.setVisibility(View.INVISIBLE);
                else {
                    ui_hot.setVisibility(View.VISIBLE);
                    ui_hot.setText(Integer.toString(new_hot_number));
                }
//            }
//        });
    }
    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        View viewFocused = getCurrentFocus();
        if (viewFocused != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(viewFocused.getWindowToken(), 0);
        }
        menu.findItem(R.id.menu_action_filter).setVisible(false);

        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        if (drawerOpen) {
            menu.findItem(R.id.menu_action_settings).setVisible(true);
            menu.findItem(R.id.menu_action_cart).setVisible(true);
            menu.findItem(R.id.menu_action_filter).setVisible(false);
            menu.findItem(R.id.menu_action_search).setVisible(true);
        }
        else {
            FragmentManager fragmentManager = getSupportFragmentManager();
//        Toast.makeText(getApplicationContext(), fragmentManager.getBackStackEntryCount(),Toast.LENGTH_SHORT).show();
            ArrayList<Fragment> fList = (ArrayList) fragmentManager.getFragments();
            if (fList != null) {
//                Fragment lastFragment = fList.get(fList.size() - 1);
                Fragment lastFragment = fragmentManager.findFragmentById(R.id.content_frame);
                if (lastFragment != null) {
                    if (lastFragment.getClass() == FragmentSettings.class) {
                        menu.findItem(R.id.menu_action_settings).setVisible(false);
                        menu.findItem(R.id.menu_action_cart).setVisible(true);
                        menu.findItem(R.id.menu_action_filter).setVisible(false);
                        menu.findItem(R.id.menu_action_search).setVisible(true);
                    } else if (lastFragment.getClass() == FragmentCart.class) {
                        menu.findItem(R.id.menu_action_settings).setVisible(true);
                        menu.findItem(R.id.menu_action_cart).setVisible(false);
                        menu.findItem(R.id.menu_action_filter).setVisible(false);
                        menu.findItem(R.id.menu_action_search).setVisible(true);
                    } else if (lastFragment.getClass() == FragmentCheckout.class) {
                        menu.findItem(R.id.menu_action_settings).setVisible(true);
                        menu.findItem(R.id.menu_action_cart).setVisible(false);
                        menu.findItem(R.id.menu_action_filter).setVisible(false);
                        menu.findItem(R.id.menu_action_search).setVisible(true);
                    } else if (lastFragment.getClass() == FragmentCheckoutBorderShop.class) {
                        menu.findItem(R.id.menu_action_settings).setVisible(true);
                        menu.findItem(R.id.menu_action_cart).setVisible(false);
                        menu.findItem(R.id.menu_action_filter).setVisible(false);
                        menu.findItem(R.id.menu_action_search).setVisible(true);
                    } else if (lastFragment.getClass() == FragmentCategory.class) {
                        menu.findItem(R.id.menu_action_settings).setVisible(true);
                        menu.findItem(R.id.menu_action_cart).setVisible(true);
                        menu.findItem(R.id.menu_action_filter).setVisible(!(((FragmentCategory)lastFragment).isSearch()));
                        menu.findItem(R.id.menu_action_search).setVisible(true);
                    } else if (lastFragment.getClass() == FragmentHome.class) {
                        setTitle(getResources().getString(R.string.app_name));
                        menu.findItem(R.id.menu_action_settings).setVisible(true);
                        menu.findItem(R.id.menu_action_cart).setVisible(true);
                        menu.findItem(R.id.menu_action_filter).setVisible(false);
                        menu.findItem(R.id.menu_action_search).setVisible(true);
                    } else {
                        menu.findItem(R.id.menu_action_settings).setVisible(true);
                        menu.findItem(R.id.menu_action_cart).setVisible(true);
                        menu.findItem(R.id.menu_action_filter).setVisible(false);
                        menu.findItem(R.id.menu_action_search).setVisible(true);
                    }

                }
            }
        }
    Log.e("","");
        updateHotCount(CartSingleton.getInstance().getProductCountAll());
//        menu.findItem(R.id.menu_action_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    public void closeSearch() {
        if (!searchView.isIconified())
        {
            searchView.onActionViewCollapsed();
            menuItem.collapseActionView();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (!searchView.isIconified())
        {
            searchView.onActionViewCollapsed();
            menuItem.collapseActionView();
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        Fragment fragment;
        switch(item.getItemId()) {
            case R.id.menu_action_filter:
                isFilter = true;
//                Toast.makeText(this, "FILTER PRESSED", Toast.LENGTH_SHORT).show();
                Fragment lastFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
                if (lastFragment.getClass() == FragmentCategory.class) {
                    final Category categoryItem = ((FragmentCategory) lastFragment).getCategory();
                    final FragmentCategory.ExtraCategoryType extraCategoryType = ((FragmentCategory) lastFragment).getExtraCategoryType();
                    List<Category> subCategories = null;
                    if (categoryItem != null)
                        subCategories = categoryItem.getChildren();
                    ArrayList<BrandItem> brandItems = ((FragmentCategory) lastFragment).getBrandsList();
                    FilterAdapter filterAdapter = new FilterAdapter(this, mDrawerList, subCategories, brandItems, new FilterAdapter.FilterAdapterListener() {
                        @Override
                        public void onBrandClick(BrandItem itemBrand) {
                            Fragment fragment = new FragmentCategory();
                            if (categoryItem == null)
                                ((FragmentCategory)fragment).setExtraCategoryType(extraCategoryType);
                            else
                                ((FragmentCategory)fragment).setCategory(categoryItem);
                            ((FragmentCategory)fragment).setBrand(itemBrand.getID());
                            if (fragment != null) {
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                                transaction.replace(R.id.content_frame, fragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                            mDrawerLayout.closeDrawer(mDrawerList);
                        }

                        @Override
                        public void onSubCategoryClick(Category subCategory) {
                            Fragment fragment = new FragmentCategory();
                            ((FragmentCategory)fragment).setCategory(subCategory);

                            if (fragment != null) {
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                                transaction.replace(R.id.content_frame, fragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                            mDrawerLayout.closeDrawer(mDrawerList);
                        }

                        @Override
                        public void onApplyClicked(String priceFrom, String priceTo, FilterAdapter.SortBy sortBy) {
                            Fragment fragment = new FragmentCategory();
                            if (categoryItem == null)
                                ((FragmentCategory)fragment).setExtraCategoryType(extraCategoryType);
                            else
                                ((FragmentCategory)fragment).setCategory(categoryItem);
                            ((FragmentCategory)fragment).setPriceFrom(priceFrom);
                            ((FragmentCategory)fragment).setPriceTo(priceTo);
                            ((FragmentCategory)fragment).setSortBy(sortBy);
                            if (fragment != null) {
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                                transaction.replace(R.id.content_frame, fragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                            mDrawerLayout.closeDrawer(mDrawerList);

                        }
                    });
                    mDrawerLayout.openDrawer(mDrawerList);
                }
                return true;
            case R.id.menu_action_search:
                // Show toast about click.
//                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                SearchView schv = new SearchView(this);
//                getSupportActionBar()
                return true;
            case R.id.menu_action_settings:
                // Show toast about click.
                fragment = new FragmentSettings();
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                    transaction.replace(R.id.content_frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                return true;
            case R.id.menu_action_profile:
                // Show toast about click.
                String userAuthKey = ((MDFApplication) getApplication()).getUserAuthKey();
                if (userAuthKey.length() < 32) {
                    ((MDFApplication) getApplication()).setUserAuthKey("");
                    fragment = new FragmentLogin();
                    if (fragment != null) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                        transaction.replace(R.id.content_frame, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
                else {
                    fragment = new FragmentProfile();
                    if (fragment != null) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                        transaction.replace(R.id.content_frame, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
                return true;
            case R.id.menu_action_cart:
                // Show toast about click.
//                Toast.makeText(this, "Cart", Toast.LENGTH_SHORT).show();
                fragment = new FragmentCart();
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                    transaction.replace(R.id.content_frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (isDrawerOpened== true) {
            mDrawerLayout.closeDrawer(mDrawerList);
        }
        else {
            FragmentManager fragmentManager = getSupportFragmentManager();
//        Toast.makeText(getApplicationContext(), fragmentManager.getBackStackEntryCount(),Toast.LENGTH_SHORT).show();
            if (fragmentManager.getBackStackEntryCount() > 1) {

//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
//                fragmentManager.popBackStack();
                fragmentManager.popBackStackImmediate();
                supportInvalidateOptionsMenu();
//                fragmentManager.beginTransaction().commit();


                //            transaction.replace(R.id.content_frame, fragment);
//            transaction.re

// Commit the transaction
//            transaction.commit();
            } else {
//                super.onBackPressed();
//                super.onBackPressed();
                finish();
            }
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        String strTitle = title.toString();
        strTitle = strTitle.substring(0,1).toUpperCase() + strTitle.substring(1).toLowerCase();
        mTitle = title = strTitle;
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/SF-UI-Display-Regular.otf");
        SpannableStringBuilder newTitleSpan = new SpannableStringBuilder(title);
        newTitleSpan.setSpan(new CustomTypefaceSpan("", typeface), 0, newTitleSpan.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        newTitleSpan.setSpan(new AbsoluteSizeSpan(14, true), 0, newTitleSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(newTitleSpan);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable() || !netInfo.isConnectedOrConnecting()){
//            Toast.makeText(this, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    class AsyncRequest extends AsyncTask<String, String, String> {
        private JSONObject mBody;
        private String mAction;
        private String mURL;
        private MDFApplication mApplication;

        public AsyncRequest(MDFApplication application, String url, String action, JSONObject body) {
            mURL = url;
            mAction = action;
            mBody = body;
            mApplication = application;
        }

        private String POST(String url, String action, JSONObject body){
            InputStream inputStream = null;
            String result = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                String json = "";
                JSONObject jsonObject = new JSONObject();
                String curLang = ((MDFApplication) getApplication()).getCurrentLanguage();

                Map<String, String> languagesMap = mApplication.getLanguages();
                Map<String, String> languagesMapSorted= new HashMap<String, String>();
                ArrayList<String> langArray = new ArrayList<String>();
                ArrayList<String> langArraySort = new ArrayList<String>();

                // Convert Map to List
                List<Map.Entry<String, String>> list = new LinkedList<Map.Entry<String, String>>(languagesMap.entrySet());

                // Sort list with comparator, to compare the Map values
                Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
                    public int compare(Map.Entry<String, String> o1,
                                       Map.Entry<String, String> o2) {
                        return (o1.getValue()).compareTo(o2.getValue());
                    }
                });

                // Convert sorted map back to a Map
                Map<String, String> sortedMap = new LinkedHashMap<String, String>();
                ArrayList<String> sortedList= new ArrayList<>();
                for (Iterator<Map.Entry<String, String>> it = list.iterator(); it.hasNext();) {
                    Map.Entry<String, String> entry = it.next();
                    languagesMapSorted.put(entry.getKey(), entry.getValue());
                    langArraySort.add(entry.getKey());
                }


                Iterator it = languagesMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
//            System.out.println(pair.getKey() + " = " + pair.getValue());
                    langArray.add((String)pair.getKey());
                    it.remove(); // avoids a ConcurrentModificationException
                }

                String curAirport = ((MDFApplication) getApplication()).getAirport();
                String storeID;
                if (curAirport == null || curAirport.equalsIgnoreCase("0"))
                    storeID = "1";
                else
                    storeID = curAirport;

                String langCode;
                if (curLang.equalsIgnoreCase("uk"))
                    langCode = "ua";
                else if (curLang.equalsIgnoreCase("ru"))
                    langCode = "ru";
                else
                    langCode = "en";
                jsonObject.accumulate("lang_code", langCode);
                jsonObject.accumulate("action", action);
                jsonObject.accumulate("body", body);
                jsonObject.accumulate("store_id", storeID);
                jsonObject.accumulate("auth_key", Const.API_KEY);

                json = jsonObject.toString();
                StringEntity se = new StringEntity(json, "UTF-8");
                httpPost.setEntity(se);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");
                HttpResponse httpResponse = httpclient.execute(httpPost);
                inputStream = httpResponse.getEntity().getContent();
                if(inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
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
            showProgress();
            Log.e("Ok","Ok");
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (mAction.equalsIgnoreCase(Const.Actions.Content.GET_LANGUAGES))
                {
                    JSONObject json = new JSONObject(result);
                    JSONObject data = json.getJSONObject("data");
                    ((MDFApplication) getApplication()).setLanguages(data);

                }
                else if (mAction.equalsIgnoreCase(Const.Actions.Content.GET_STORES))
                {
                    JSONObject json = new JSONObject(result);
                    JSONObject data = json.getJSONObject("data");

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Error json", e.toString());
            }
            Log.d("Result", result);

            hideProgress();
            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(String... values){
            super.onProgressUpdate(values);
        }


        @Override
        protected String doInBackground(String... params) {
            return POST(mURL, mAction, mBody);
        }
    }

    public class CustomTypefaceSpan extends TypefaceSpan {

        private final Typeface newType;

        public CustomTypefaceSpan(String family, Typeface type) {
            super(family);
            newType = type;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            applyCustomTypeFace(ds, newType);
        }

        @Override
        public void updateMeasureState(TextPaint paint) {
            applyCustomTypeFace(paint, newType);
        }

        private void applyCustomTypeFace(Paint paint, Typeface tf) {
            int oldStyle;
            Typeface old = paint.getTypeface();
            if (old == null) {
                oldStyle = 0;
            } else {
                oldStyle = old.getStyle();
            }

            int fake = oldStyle & ~tf.getStyle();
            if ((fake & Typeface.BOLD) != 0) {
                paint.setFakeBoldText(true);
            }

            if ((fake & Typeface.ITALIC) != 0) {
                paint.setTextSkewX(-0.25f);
            }

            paint.setTypeface(tf);
        }

    }
}
