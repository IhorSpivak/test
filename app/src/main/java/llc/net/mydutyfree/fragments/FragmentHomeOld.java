package llc.net.mydutyfree.fragments;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;


import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
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
import java.util.List;

import in.srain.cube.views.GridViewWithHeaderAndFooter;
import llc.net.mydutyfree.base.MDFProgressDialog;
import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.base.RetrofitService;
import llc.net.mydutyfree.base.WrapContentHeightViewPager;
import llc.net.mydutyfree.interfaces.PostInterfaceApi;
import llc.net.mydutyfree.newmdf.ImageAdapter;
import llc.net.mydutyfree.newmdf.MainActivity;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.response.Banner;
import llc.net.mydutyfree.response.GetBannersResponse;
import llc.net.mydutyfree.response.Product;
import llc.net.mydutyfree.utils.Const;
import llc.net.mydutyfree.utils.FontUtils;
import llc.net.mydutyfree.utils.HorScrollView;
import llc.net.mydutyfree.utils.JsonCreator;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by gorf on 11/27/15.
 */
public class FragmentHomeOld extends Fragment implements View.OnClickListener, View.OnTouchListener{
    protected MDFProgressDialog mProgressDialog;
    protected boolean isSpecialOffresDone, isTopsellersDone, isBannersDone;
    int numbr;
    static final int MIN_DISTANCE = 10;
    private float downX, downY, upX, upY;


    public FragmentHomeOld() {
    }

//    RadioGroup radioGroup;
//    RadioButton radioButton1,radioButton2,radioButton3;
    WrapContentHeightViewPager pager;
    CirclePageIndicator indicator;

    Button btnSpecialOffres;
    Button btnTopSellers;
    Button btnShowAllGoods;
    List<Banner> bannerList;

    ImageAdapter imageAdapter;
    public static String LOG_TAG = "my_log";

    Boolean isSpecialOffersSelected;
    Boolean isHeaderAdded;

    int screenHeightInPixels;
    int screenWidthInPixels;
//    ImageView imgBigImageOne;
//    ImageView imgBigImageTwo;
//    ImageView imgBigImageThree;

//    ImageView imgSmallImageOne;
//    ImageView imgSmallImageTwo;
//    ImageView imgSmallImageThree;

//    Button btnBannerOne;
//    Button btnBannerTwo;
//    Button btnBannerThree;

    View headerView;
    View footerView;
    View rootView;
    GridViewWithHeaderAndFooter gridview;
    List<Product> arrayListTopSellers;
    List<Product> arrayListSpecialOffers;
    HorScrollView touchListener;
    int pageBanner;
    private  float density;
    boolean shouldClick = false;
    private Tracker mTracker;


    @Override
    public void onResume() {
        super.onResume();
        density = getResources().getDisplayMetrics().density;
        Log.e("FragmentHome LOG", "onResume");

//        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GOTHIC.TTF");
//        SpannableStringBuilder SS = new SpannableStringBuilder(getResources().getString(R.string.app_name));
//        SS.setSpan(new CustomTypefaceSpan("", font2), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ((MainActivity)getActivity()).setTitle(getResources().getString(R.string.app_name));

        headerView = ((LayoutInflater)getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE)).inflate(R.layout.home_header_view, null);
        footerView = ((LayoutInflater)getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE)).inflate(R.layout.home_footer_view, null);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displaymetrics);
        int heightInPixels = displaymetrics.heightPixels;
        int widthInPixels = displaymetrics.widthPixels;
        screenHeightInPixels = heightInPixels;
        screenWidthInPixels = widthInPixels;

        pager = (WrapContentHeightViewPager) headerView.findViewById(R.id.viewPager);
        indicator = (CirclePageIndicator) headerView.findViewById(R.id.pageIndicator);

//        radioGroup = (RadioGroup) headerView.findViewById(R.id.radioGroupPageIndicatorhomeHeader);
//        radioButton1 = (RadioButton) headerView.findViewById(R.id.radioGroupPageIndicatorhomeHeaderradioButton1);
//        radioButton2 = (RadioButton) headerView.findViewById(R.id.radioGroupPageIndicatorhomeHeaderradioButton2);
//        radioButton3 = (RadioButton) headerView.findViewById(R.id.radioGroupPageIndicatorhomeHeaderradioButton3);

//        final HorScrollView bottom = (HorScrollView)headerView.findViewById(R.id.homeHeaderHorizontalScrollViewBottom);
//        final HorScrollView top = (HorScrollView)headerView.findViewById(R.id.homeHeaderHorizontalScrollViewTop);
//        touchListener = (HorScrollView)headerView.findViewById(R.id.homeHeaderHorizontalScrollViewTouchListener);
//
//        top.removeAllViews();
//        top.setHorizontalScrollBarEnabled(false);
//        top.setVerticalScrollBarEnabled(false);
////        bottom.removeAllViews();
//        bottom.setHorizontalScrollBarEnabled(false);
//        bottom.setVerticalScrollBarEnabled(false);
//        bottom.setBackgroundColor(Color.TRANSPARENT);
//
//        touchListener.removeAllViews();
//        touchListener.setHorizontalScrollBarEnabled(false);
//        touchListener.setVerticalScrollBarEnabled(false);
//
//        LinearLayout.LayoutParams layoutParamsForLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        LinearLayout linearLayoutParentBottom = new LinearLayout(getContext());
//        linearLayoutParentBottom.setOrientation(LinearLayout.HORIZONTAL);
//        linearLayoutParentBottom.setLayoutParams(layoutParamsForLinearLayout);
//
//        LinearLayout linearLayoutParentTop = new LinearLayout(getContext());
//        linearLayoutParentTop.setOrientation(LinearLayout.HORIZONTAL);
//        linearLayoutParentTop.setLayoutParams(layoutParamsForLinearLayout);
//
//        RelativeLayout.LayoutParams layoutParamsForRelativeLayoutBottom = new RelativeLayout.LayoutParams(widthInPixels, ViewGroup.LayoutParams.WRAP_CONTENT);
//        RelativeLayout.LayoutParams layoutParamsForRelativeLayoutTop = new RelativeLayout.LayoutParams((int) (widthInPixels / 0.7f), ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        RelativeLayout relativeLayoutOneBig = new RelativeLayout(getContext());
//        relativeLayoutOneBig.setPadding(0,0,0,0);
//        relativeLayoutOneBig.setLayoutParams(layoutParamsForRelativeLayoutBottom);
//        RelativeLayout relativeLayoutTwoBig = new RelativeLayout(getContext());
//        relativeLayoutTwoBig.setPadding(0,0,0,0);
//        relativeLayoutTwoBig.setLayoutParams(layoutParamsForRelativeLayoutBottom);
//        RelativeLayout relativeLayoutThreeBig = new RelativeLayout(getContext());
//        relativeLayoutThreeBig.setPadding(0,0,0,0);
//        relativeLayoutThreeBig.setLayoutParams(layoutParamsForRelativeLayoutBottom);
//
//        RelativeLayout relativeLayoutOneSmall = new RelativeLayout(getContext());
//        relativeLayoutOneSmall.setLayoutParams(layoutParamsForRelativeLayoutTop);
//        RelativeLayout relativeLayoutTwoSmall = new RelativeLayout(getContext());
//        relativeLayoutTwoSmall.setLayoutParams(layoutParamsForRelativeLayoutTop);
//        RelativeLayout relativeLayoutThreeSmall = new RelativeLayout(getContext());
//        relativeLayoutThreeSmall.setLayoutParams(layoutParamsForRelativeLayoutTop);
//
//        RelativeLayout.LayoutParams layoutParamsImageBig = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        layoutParamsImageBig.setMargins(0, 0, 0, 0);
//
//        RelativeLayout.LayoutParams layoutParamsImageSmall = new RelativeLayout.LayoutParams((int) Utils.dpToPx(200),(int) Utils.dpToPx(75));
//        layoutParamsImageSmall.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        layoutParamsImageSmall.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//
//        float margin = (((widthInPixels / 0.7f) - widthInPixels) / 2) + (widthInPixels / 5);
//
//        layoutParamsImageSmall.setMargins(0, 0, (int) margin, 0);
////        widthInPixels = widthInPixels + 0.5f;
//
//        imgBigImageOne = new ImageView(getContext());
////        imgBigImageOne.setMaxWidth(widthInPixels);
////        imgBigImageOne.setMinimumWidth(widthInPixels);
////        imgBigImageOne.setPadding(0, 0, 0, 0);
//        imgBigImageOne.setLayoutParams(layoutParamsImageBig);
//        imgBigImageOne.setImageResource(R.drawable.slide_1_big);
//        imgBigImageOne.setAdjustViewBounds(true);
//        imgBigImageOne.setScaleType(ImageView.ScaleType.FIT_START);
////        imgBigImageOne.setCropToPadding(false);
////        imgBigImageOne.setPaddingRelative(0, 0, 0, 0);
////        imgBigImageOne.offsetLeftAndRight(10);
//
//        imgBigImageTwo = new ImageView(getContext());
////        imgBigImageTwo.setMaxWidth(widthInPixels);
////        imgBigImageTwo.setMinimumWidth(widthInPixels);
////        imgBigImageTwo.setPadding(0, 0, 0, 0);
//        imgBigImageTwo.setLayoutParams(layoutParamsImageBig);
//        imgBigImageTwo.setAdjustViewBounds(true);
//        imgBigImageTwo.setScaleType(ImageView.ScaleType.FIT_START);
//        imgBigImageTwo.setImageResource(R.drawable.slide_2_big);
////        imgBigImageTwo.setCropToPadding(false);
////        imgBigImageTwo.setPaddingRelative(0, 0, 0, 0);
////        imgBigImageTwo.offsetLeftAndRight(0);
//
//        imgBigImageThree = new ImageView(getContext());
////        imgBigImageThree.setMaxWidth(widthInPixels);
////        imgBigImageThree.setMinimumWidth(widthInPixels);
////        imgBigImageThree.setPadding(0, 0, 0, 0);
//        imgBigImageThree.setLayoutParams(layoutParamsImageBig);
//        imgBigImageThree.setAdjustViewBounds(true);
//        imgBigImageThree.setScaleType(ImageView.ScaleType.FIT_START);
//        imgBigImageThree.setImageResource(R.drawable.slide_3_big);
////        imgBigImageThree.setCropToPadding(false);
////        imgBigImageThree.setPaddingRelative(0, 0, 0, 0);
////        imgBigImageThree.offsetLeftAndRight(0);
//
//        imgBigImageOne.setVisibility(View.INVISIBLE);
//        imgBigImageTwo.setVisibility(View.INVISIBLE);
//        imgBigImageThree.setVisibility(View.INVISIBLE);
//
//        relativeLayoutOneBig.addView(imgBigImageOne, layoutParamsImageBig);
//        linearLayoutParentBottom.addView(relativeLayoutOneBig);
//        relativeLayoutTwoBig.addView(imgBigImageTwo, layoutParamsImageBig);
//        linearLayoutParentBottom.addView(relativeLayoutTwoBig);
//        relativeLayoutThreeBig.addView(imgBigImageThree, layoutParamsImageBig);
//        linearLayoutParentBottom.addView(relativeLayoutThreeBig);
////        linearLayoutParentBottom.addView(imgBigImageOne);
////        linearLayoutParentBottom.addView(imgBigImageTwo);
////        linearLayoutParentBottom.addView(imgBigImageThree);
//        bottom.addView(linearLayoutParentBottom);
//
////        ((RelativeLayout)top.getParent()).removeView(top);
//
//        imgSmallImageOne = new ImageView(getContext());
//        imgSmallImageOne.setLayoutParams(layoutParamsImageSmall);
//        imgSmallImageOne.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        imgSmallImageOne.setImageResource(R.drawable.slide_1_small);
//
//        imgSmallImageTwo = new ImageView(getContext());
//        imgSmallImageTwo.setLayoutParams(layoutParamsImageSmall);
//        imgSmallImageTwo.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        imgSmallImageTwo.setImageResource(R.drawable.slide_2_small);
//
//        imgSmallImageThree = new ImageView(getContext());
//        imgSmallImageThree.setLayoutParams(layoutParamsImageSmall);
//        imgSmallImageThree.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        imgSmallImageThree.setImageResource(R.drawable.slide_3_small);
//
////        relativeLayoutOneBig.getChildAt(0).setAlpha(0);
////        relativeLayoutOneBig.setBackgroundColor(Color.WHITE);
////        relativeLayoutTwoBig.getChildAt(0).setAlpha(0);
////        relativeLayoutTwoBig.setBackgroundColor(Color.GREEN);
////        relativeLayoutThreeBig.getChildAt(0).setAlpha(0);
////        relativeLayoutThreeBig.setBackgroundColor(Color.BLACK);
//
//
//
//        relativeLayoutOneSmall.addView(imgSmallImageOne, layoutParamsImageSmall);
//        linearLayoutParentTop.addView(relativeLayoutOneSmall);
//        relativeLayoutTwoSmall.addView(imgSmallImageTwo, layoutParamsImageSmall);
//        linearLayoutParentTop.addView(relativeLayoutTwoSmall);
//        relativeLayoutThreeSmall.addView(imgSmallImageThree, layoutParamsImageSmall);
//        linearLayoutParentTop.addView(relativeLayoutThreeSmall);
//        top.addView(linearLayoutParentTop);
//
//
//        RelativeLayout.LayoutParams newParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) Utils.dpToPx(90));
//        newParams.setMargins(0, (int) Utils.dpToPx(-50), 0, 0);
//        newParams.addRule(RelativeLayout.BELOW, bottom.getId());
//        top.setLayoutParams(newParams);
//        top.invalidate();
//
//        RelativeLayout.LayoutParams touchListenerParams = new RelativeLayout.LayoutParams(widthInPixels, ViewGroup.LayoutParams.MATCH_PARENT);
//        touchListenerParams.addRule(RelativeLayout.ALIGN_BOTTOM, top.getId());
//        touchListenerParams.addRule(RelativeLayout.ALIGN_TOP, bottom.getId());
//        touchListener.setLayoutParams(touchListenerParams);
//
//        LinearLayout touchListenerLinearLayout = new LinearLayout(getContext());
//        touchListener.addView(touchListenerLinearLayout, new LinearLayout.LayoutParams(widthInPixels * 3, ViewGroup.LayoutParams.WRAP_CONTENT));
//        btnBannerOne = new Button(getContext(), null, R.style.buttonClear);
////        btnBannerOne.setBackgroundColor(Color.argb(100, 255, 0, 0));
//        btnBannerTwo = new Button(getContext(), null, R.style.buttonClear);
//        btnBannerThree = new Button(getContext(), null, R.style.buttonClear);
//        touchListenerLinearLayout.setClipChildren(true);
//        touchListenerLinearLayout.setClipToPadding(true);
//
//        View view = new View(getContext());
//        touchListenerLinearLayout.addView(btnBannerOne, new LinearLayout.LayoutParams(widthInPixels, 700));
//
//        touchListenerLinearLayout.addView(btnBannerTwo, new LinearLayout.LayoutParams(widthInPixels, 700));
//        touchListenerLinearLayout.addView(btnBannerThree, new LinearLayout.LayoutParams(widthInPixels, 700));



//        top.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//                    @Override
//                    public void onScrollChanged() {
//                        int scrollX = top.getScrollX(); //for horizontalScrollView
//                        int scrollY = top.getScrollY(); //for verticalScrollView
//                        //DO SOMETHING WITH THE SCROLL COORDINATES
//                        bottom.scrollTo((int) (scrollX * 0.7), scrollY);
//
//                        float currentPositionBottom = bottom.getScrollX();
//                        float pagesCountBottom = 3;
//                        float pageLengthInPxBottom = screenWidthInPixels;
//                        float currentPageBottom = currentPositionBottom / pageLengthInPxBottom;
//                        float wholeScrollHeight = bottom.getChildAt(0).getHeight();
//                        float wholeScrollWidth = bottom.getChildAt(0).getWidth();
//
//
//                        float currentPosition = top.getScrollX();
//                        float pagesCount = 3;
//                        float pageLengthInPx = screenWidthInPixels / 0.7f;
//                        float currentPage = currentPosition / pageLengthInPx;
//
//                        int page = (int) (Math.floor((currentPosition - pageLengthInPx / 2) / pageLengthInPx) + 1);
////                Log.e("dddd", "dedddd");
//                    }
//                });
//
//        top.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
////                    float currentPosition = top.getScrollX();
////                    float pagesCount = 3;//_horizontalBar.getChildCount();
////                    float pageLengthInPx = screenWidthInPixels / 0.7f;
////                    float currentPage = currentPosition/pageLengthInPx;
////
////                    Boolean isBehindHalfScreen =  currentPage - (int)currentPage > 0.5;
////
////                    float edgePosition = 0;
////                    if(isBehindHalfScreen)
////                    {
////                        edgePosition = (int)(currentPage+1)*pageLengthInPx;
////                    }
////                    else
////                    {
////                        edgePosition = (int)currentPage*pageLengthInPx;
////                    }
////
////                    ObjectAnimator objectAnimator = ObjectAnimator.ofInt(top, "scrollX", top.getScrollX(), (int)edgePosition).setDuration(300);
////                    objectAnimator.start();
//////                    top.scrollTo((int)edgePosition, 0);
//                    float currentPosition = top.getScrollX();
//                    float pagesCount = 3;
//                    float pageLengthInPx = screenWidthInPixels / 0.7f;
//                    float currentPage = currentPosition / pageLengthInPx;
//
//                    int page = (int) (Math.floor((currentPosition - pageLengthInPx / 2) / pageLengthInPx) + 1);
////                    top.scrollTo((int) (page * pageLengthInPx), 0);
//                    ObjectAnimator objectAnimator = ObjectAnimator.ofInt(top, "scrollX", top.getScrollX(), (int) (page * pageLengthInPx)).setDuration(300);
//                    objectAnimator.start();
//
//                }
//                return false;
//            }
//        });

//        touchListenerLinearLayout.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
//
//            @Override
//            public void onSwipeLeft() {
//                Toast.makeText(getContext(), "swipe Left", Toast.LENGTH_SHORT).show();
//                // Whatever
//            }
//
//            @Override
//            public void onSwipeRight() {
//                Toast.makeText(getContext(), "swipe Right", Toast.LENGTH_SHORT).show();
//                // Whatever
//            }
//
//        });

//        btnBannerOne.setOnTouchListener(new View.OnTouchListener() {
//            public final void onRightToLeftSwipe() {
//                Log.i("!!!","RightToLeftSwipe!");
////                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(touchListener, "scrollX", touchListener.getScrollX(), (int) (0)).setDuration(401);
////                objectAnimator.start();
//            }
//
//            public void onLeftToRightSwipe(){
//                Log.i("!!!", "scroll x " + touchListener.getScrollX());
////                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(touchListener, "scrollX", touchListener.getScrollX(), (int) (screenWidthInPixels)).setDuration(401);
////                objectAnimator.start();
//                Log.i("!!!", "btnLeftToRightSwipe!");
//            }
//
//            public void onTopToBottomSwipe(){
//                Log.i("!!!", "onTopToBottomSwipe!");
//            }
//
//            public void onBottomToTopSwipe(){
//                Log.i("!!!", "onBottomToTopSwipe!");
//            }
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch(event.getAction()){
//                    case MotionEvent.ACTION_DOWN: {
//                        downX = event.getX();
//                        downY = event.getY();
//                           return true;
//                    }
//                    case MotionEvent.ACTION_CANCEL: {
//                        upX = event.getX();
//                        upY = event.getY();
//
//
//                        Log.i("!!!", " - - - "+ downX + " - - - " + upX);
//                        float deltaX = downX - upX;
//                        float deltaY = downY - upY;
//
//                        // swipe horizontal?
//                        if(Math.abs(deltaX) > MIN_DISTANCE){
//                            // left or right
//                            if(deltaX > 0) { this.onLeftToRightSwipe(); return true; }
//                            if(deltaX < 0) { this.onRightToLeftSwipe(); return true; }
//                        } else { Log.i("!!!",  "Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE); }
//
//                        // swipe vertical?
//                        if(Math.abs(deltaY) > MIN_DISTANCE){
//                            // top or down
//                            if(deltaY < 0) { this.onTopToBottomSwipe(); return true; }
//                            if(deltaY > 0) { this.onBottomToTopSwipe(); return true; }
//                        } else { Log.i("!!!",  "Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE); }
//
//                        downX = 0;
//                             return true;
//                    }
//                }
//                return false;
//            }
//        });
//
//        btnBannerOne.setOnTouchListener(this);

//        touchListener.setOnTouchListener(new View.OnTouchListener() {
//
//            public final void onRightToLeftSwipe() {
//                Log.i("!!!", "RightToLeftSwipe!");
//            }
//
//            public void onLeftToRightSwipe() {
//                Log.i("!!!", "touchLeftToRightSwipe!");
//            }
//
//            public void onTopToBottomSwipe() {
//                Log.i("!!!", "onTopToBottomSwipe!");
//            }
//
//            public void onBottomToTopSwipe() {
//                Log.i("!!!", "onBottomToTopSwipe!");
//            }
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    float currentPosition = touchListener.getScrollX();
//                    float pagesCount = 3;
//                    float pageLengthInPx = screenWidthInPixels;
//                    float currentPage = currentPosition / pageLengthInPx;
//
//                    int page = (int) (Math.floor((currentPosition - pageLengthInPx / 2) / pageLengthInPx) + 1);
////                    top.scrollTo((int) (page * pageLengthInPx), 0);
//                    ObjectAnimator objectAnimator = ObjectAnimator.ofInt(touchListener, "scrollX", touchListener.getScrollX(), (int) (page * pageLengthInPx)).setDuration(300);
//                    objectAnimator.start();
//                    return true;
//                }
//                return false;
//            }
//                switch(event.getAction()){
//                    case MotionEvent.ACTION_DOWN: {
//                        downX = event.getX();
//                        downY = event.getY();
//                           return true;
//                    }
//                    case MotionEvent.ACTION_UP: {
//                        upX = event.getX();
//                        upY = event.getY();
//
//
//                        Log.i("!!!", " - - - "+ downX + " - - - " + upX);
//                        float deltaX = downX - upX;
//                        float deltaY = downY - upY;
//
//                        // swipe horizontal?
//                        if(Math.abs(deltaX) > MIN_DISTANCE){
//                            // left or right
//                            if(deltaX > 0) { this.onLeftToRightSwipe(); return true; }
//                            if(deltaX < 0) { this.onRightToLeftSwipe(); return true; }
//                        } else { Log.i("!!!",  "Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE); }
//
//                        // swipe vertical?
//                        if(Math.abs(deltaY) > MIN_DISTANCE){
//                            // top or down
//                            if(deltaY < 0) { this.onTopToBottomSwipe(); return true; }
//                            if(deltaY > 0) { this.onBottomToTopSwipe(); return true; }
//                        } else { Log.i("!!!",  "Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE); }
//
//                             return true;
//                    }
//                }
//                return false;
//            }
//        });

//        touchListener.setOnTouchListener(this);

//        touchListener.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                int scrollX = touchListener.getScrollX(); //for horizontalScrollView
//                int scrollY = touchListener.getScrollY(); //for verticalScrollView
//                bottom.scrollTo(scrollX, scrollY);
//                top.scrollTo((int) (scrollX / 0.7), scrollY);
//                float currentPositionBottom = touchListener.getScrollX();
//                float pagesCountBottom = 3;
//                float pageLengthInPxBottom = screenWidthInPixels;
//                float currentPageBottom = currentPositionBottom / pageLengthInPxBottom;
//                float wholeScrollHeight = touchListener.getChildAt(0).getHeight();
//                float wholeScrollWidth = touchListener.getChildAt(0).getWidth();
//
//                float currentPosition = touchListener.getScrollX();
//                float pagesCount = 3;
//                float pageLengthInPx = screenWidthInPixels;
//                float currentPage = currentPosition / pageLengthInPx;
//
//                int page = (int) (Math.floor((currentPosition - pageLengthInPx / 2) / pageLengthInPx) + 1);
//                if (page == 0)
//                    radioButton1.setChecked(true);
//                else if (page == 1)
//                    radioButton2.setChecked(true);
//                if (page == 2)
//                    radioButton3.setChecked(true);
//            }
//        });

        btnSpecialOffres = (Button) headerView.findViewById(R.id.headerSpecialOffers);
        btnTopSellers = (Button)headerView.findViewById(R.id.headerTopSellers);
        btnShowAllGoods = (Button)footerView.findViewById(R.id.btnHomeFooterShowAllGoods);
        FontUtils.setBoldFont(getContext(), btnSpecialOffres);
        FontUtils.setBoldFont(getContext(), btnTopSellers);
        FontUtils.setBoldFont(getContext(), btnShowAllGoods);

        btnSpecialOffres.setSingleLine();
//        AutofitHelper.create(btnSpecialOffres);
        btnTopSellers.setSingleLine();
        btnShowAllGoods.setSingleLine();

//        AutofitHelper.create(btnTopSellers);
        float buttonTextSize = 0;
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

        btnTopSellers.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
        btnSpecialOffres.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
        btnShowAllGoods.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);


//        final Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out_fast);
//        GridLayoutAnimationController controller = new GridLayoutAnimationController(animation);
//        gridview.setAnimation(animation);

        btnSpecialOffres.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setPressed(true);
                v.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                isSpecialOffersSelected = true;
//                imageAdapter.notifyDataSetChanged();
                imageAdapter.setList(arrayListSpecialOffers);
//                gridview.startAnimation(animation);
                btnTopSellers.setPressed(false);
                btnTopSellers.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                return true;
            }
        });
//        btnSpecialOffres.setOnTouchListener(this);


        btnTopSellers.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setPressed(true);
                v.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                isSpecialOffersSelected = false;
//                imageAdapter.notifyDataSetChanged();
                imageAdapter.setList(arrayListTopSellers);
                btnSpecialOffres.setPressed(false);
                btnSpecialOffres.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));

//                gridview.startAnimation(animation);
                return true;
            }
        });

//        btnTopSellers.setOnTouchListener(this);
        btnShowAllGoods.setOnTouchListener(this);


//        if (gridview.getHeaderViewCount() == 0)
        if (!isHeaderAdded)
        {
            gridview.addHeaderView(headerView);
            gridview.addFooterView(footerView);
            isHeaderAdded = true;
        }

        btnSpecialOffres.setPressed(true);
        btnSpecialOffres.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
        btnTopSellers.setPressed(false);
        isSpecialOffersSelected = true;

        String URL = Const.URLS.URL;

        String actionGetNewArrivals = Const.Actions.Products.GET_FILTERED;
        String actionGetTopSellers  = Const.Actions.Products.GET_FILTERED;

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);

        String productsQuantity = null;
        if (tabletSize) {
            productsQuantity = String.format("%d", 12);
        }
        else {
            productsQuantity = String.format("%d", 8);
        }

        JSONObject bodyGetNewArrivals = new JSONObject();
        try {
            JSONObject filter = new JSONObject();
            filter.accumulate("extra_flag", "new");
            filter.accumulate("limit", productsQuantity);
            filter.accumulate("random", "1");
            bodyGetNewArrivals.accumulate("filter", filter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject bodyGetTopSellers = new JSONObject();
        try {
            JSONObject filter = new JSONObject();
            filter.accumulate("extra_flag", "top");
            filter.accumulate("limit", productsQuantity);
            filter.accumulate("random", "1");
            bodyGetTopSellers.accumulate("filter", filter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject bodyGetBanners = new JSONObject();

        new AsyncCustomPost(getContext(), URL, actionGetNewArrivals, bodyGetNewArrivals, true).execute();
        new AsyncCustomPost(getContext(), URL, actionGetTopSellers, bodyGetTopSellers, false).execute();

        RetrofitService retrofitService = new RetrofitService();
        PostInterfaceApi service = retrofitService.create();
        Call<GetBannersResponse> callGetBanners = service.getBanners(JsonCreator.GetBanners());
        ((MainActivity)getActivity()).showProgress();
        callGetBanners.enqueue(new retrofit2.Callback<GetBannersResponse>() {
            @Override
            public void onResponse(Call<GetBannersResponse> call, Response<GetBannersResponse> response) {
                Log.e("!!!", "!!!");
                bannerList = response.body().getData();
                if (bannerList.size() > 0) {
                    SamplePagerAdapter pagerAdapter = new SamplePagerAdapter(bannerList);
                    pager.setAdapter(pagerAdapter);
                    pager.setCurrentItem(0);
                    indicator.setViewPager(pager);
                    ((MainActivity) getActivity()).invalidateOptionsMenu();
                }
                ((MainActivity) getActivity()).hideProgress();
            }

            @Override
            public void onFailure(Call<GetBannersResponse> call, Throwable t) {
                Log.e("!!!", "error!!!");
                ((MainActivity)getActivity()).hideProgress();
            }
        });



//        new AsyncCustomPost(getContext(), URL, actionGetBanners, bodyGetBanners, false).execute();
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
        Log.e("FragmentHome LOG", "onCreateView");

        mTracker = ((MDFApplication)getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName("Home");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        isHeaderAdded = false;

        isBannersDone = false;
        isSpecialOffresDone = false;
        isTopsellersDone = false;

        setHasOptionsMenu(true);
        mProgressDialog = new MDFProgressDialog(getContext(), false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);

        String actionGetCurrencySymbols = Const.Actions.Content.GET_CURRENCY_LIST;
        JSONObject bodyGetCurrencies = new JSONObject();
        new AsyncCustomPost(getContext(), Const.URLS.URL, actionGetCurrencySymbols, bodyGetCurrencies, false).execute();

        rootView = inflater.inflate(R.layout.screen_home, container, false);

        gridview = (GridViewWithHeaderAndFooter) rootView.findViewById(R.id.hgridview);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String prodID = new String();
                if (isSpecialOffersSelected)
                    prodID = arrayListSpecialOffers.get(position).getID();
                else
                    prodID = arrayListTopSellers.get(position).getID();

                Fragment fragment = new FragmentItem();
                ((FragmentItem)fragment).setProductID(prodID);
                if (fragment != null) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                    transaction.replace(R.id.content_frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                numbr = position;
            }
        });

        return rootView;
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getContext().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public final void onRightToLeftSwipe() {
        Log.i("!!!", "RightToLeftSwipe!");
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(touchListener, "scrollX", touchListener.getScrollX(), (int) (pageBanner - 1) * (screenWidthInPixels)).setDuration(401);
                objectAnimator.start();
    }

    public void onLeftToRightSwipe() {
        Log.i("!!!", "scroll x " + touchListener.getScrollX());
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(touchListener, "scrollX", touchListener.getScrollX(), (int) (pageBanner  + 1) * (screenWidthInPixels)).setDuration(401);
                objectAnimator.start();
        Log.i("!!!", "btnLeftToRightSwipe!");
    }

    public void onTopToBottomSwipe(){
        Log.i("!!!", "onTopToBottomSwipe!");
    }

    public void onBottomToTopSwipe(){
        Log.i("!!!", "onBottomToTopSwipe!");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

//        if (v == touchListener) { // || v == btnBannerOne || v == btnBannerTwo || v == btnBannerThree) {
//            switch(event.getAction()){
//                case MotionEvent.ACTION_DOWN: {
//                    touchListener.requestDisallowInterceptTouchEvent(true);
//                    downX = event.getX();
//                    downY = event.getY();
//
////                    if (v == btnBannerTwo)
////                        downX += btnBannerOne.getWidth();
////                    if (v == btnBannerThree) {
////                        downX += btnBannerOne.getWidth();
////                        downX += btnBannerTwo.getWidth();
////                    }
//                    return  true;
//                }
//                case MotionEvent.ACTION_UP: {
//                    touchListener.requestDisallowInterceptTouchEvent(false);
//                    upX = event.getX();
//                    upY = event.getY();
//
//
//                    Log.i("!!!", " - - - "+ downX + " - - - " + upX);
//                    float deltaX = downX - upX;
//                    float deltaY = downY - upY;
//
//                    // swipe horizontal?
//                    if(Math.abs(deltaX) > MIN_DISTANCE){
//                        // left or right
//                        if(deltaX > 0) { this.onLeftToRightSwipe(); return true; }
//                        if(deltaX < 0) { this.onRightToLeftSwipe(); return true; }
//                    } else { Log.i("!!!",  "Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE); }
//
//                    // swipe vertical?
////                    if(Math.abs(deltaY) > MIN_DISTANCE){
////                        // top or down
////                        if(deltaY < 0) { this.onTopToBottomSwipe(); return true; }
////                        if(deltaY > 0) { this.onBottomToTopSwipe(); return true; }
////                    } else { Log.i("!!!",  "Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE); }
//
//                    return true;
//                }
//            }
//
//        }
        if (v == touchListener)
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                shouldClick = true;
                downX = event.getX();
                downY = event.getY();

//                return true;
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                upX = event.getX();
                upY = event.getY();


                Log.i("!!!", " - - - "+ downX + " - - - " + upX);
                float deltaX = downX - upX;
                float deltaY = downY - upY;
                if (deltaX > 5 && deltaY > 5)
                    shouldClick = false;
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                float currentPosition = touchListener.getScrollX();
                float pagesCount = 3;
                float pageLengthInPx = screenWidthInPixels;
                float currentPage = currentPosition / pageLengthInPx;

                pageBanner = (int) (Math.floor((currentPosition - pageLengthInPx / 2) / pageLengthInPx) + 1);
                if (shouldClick)
                {
//                    if (pageBanner == 0)
////                        onBannerButtonOneClick();
//                    if (pageBanner == 1)
////                        onBannerButtonTwoClick();
//                    if (pageBanner == 2)
////                        onBannerButtonThreeClick();
                }
//                    top.scrollTo((int) (page * pageLengthInPx), 0);
                    ObjectAnimator objectAnimator = ObjectAnimator.ofInt(touchListener, "scrollX", touchListener.getScrollX(), (int) (pageBanner * pageLengthInPx)).setDuration(300);
                    objectAnimator.start();
                return true;
            }
            Log.e("!!!", "touchListener");
        }
//        else if (v == btnBannerOne)
//        {
//            Log.e("!!!", "btnBannerOne");
//        }
//        else if (v == btnBannerTwo)
//        {
//            Log.e("!!!", "btnBannerTwo");
//        }
        else if (v == btnSpecialOffres)
        {
            v.setPressed(true);
            v.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
            isSpecialOffersSelected = true;
            imageAdapter.setList(arrayListSpecialOffers);
            btnTopSellers.setPressed(false);
            btnTopSellers.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
            Log.e("!!!", "btnSpecialOffres");
            return true;
        }
        else if (v == btnTopSellers)
        {
            v.setPressed(true);
            v.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
            isSpecialOffersSelected = false;
            imageAdapter.setList(arrayListTopSellers);
            btnSpecialOffres.setPressed(false);
            btnSpecialOffres.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
            Log.e("!!!", "btnTopSellers");
            return true;
        }
        else if (v == btnShowAllGoods)
        {
            Log.e("!!!", "btnShowAllGoods");
            Fragment fragment = new FragmentCategory();
            if (isSpecialOffersSelected)
                ((FragmentCategory)fragment).setExtraCategoryType(FragmentCategory.ExtraCategoryType.SPECIAL_OFFERS);
            else
                ((FragmentCategory)fragment).setExtraCategoryType(FragmentCategory.ExtraCategoryType.TOP_SELLERS);
            if (fragment != null) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
            return true;
        }



        return false;
    }


    @Override
    public void onClick(View view) {

        String URL = "http://api.mydutyfree.net?key=GTg7hgrRF43D5fgTGHN";

        switch(view.getId()){
            case R.id.btnPost:
//                if(!validate())
//                    Toast.makeText(getContext(), "Enter some data!", Toast.LENGTH_LONG).show();
                // call AsynTask to perform network operation on separate thread
//                new HttpAsyncTask(getContext(), true).execute(URL);
                break;
        }

    }

//    private boolean validate(){
//        if(etName.getText().toString().trim().equals(""))
//            return false;
//        else if(etCountry.getText().toString().trim().equals(""))
//            return false;
//        else if(etTwitter.getText().toString().trim().equals(""))
//            return false;
//        else
//            return true;
//    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context context) {
            gestureDetector = new GestureDetector(context, new GestureListener());
        }

        public void onSwipeLeft() {
        }

        public void onSwipeRight() {
        }

        public void onTooch() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_DISTANCE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float distanceX = e2.getX() - e1.getX();
                float distanceY = e2.getY() - e1.getY();
                if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (distanceX > 0)
                        onSwipeRight();
                    else
                        onSwipeLeft();
                    return true;
                }
                else if (e2.getAction() == MotionEvent.ACTION_UP) {
                    onTooch();
                    return true;
                }
                return false;
            }
        }
    }

    class AsyncCustomPost extends AsyncTask<String, String, String> {
        private Context mContext;
        private JSONObject mBody;
        private String mAction;
        private String mURL;
        private Boolean mIsNewArrivals;

        public AsyncCustomPost(Context context, String url, String action, JSONObject body, Boolean isNewArrivals) {
            mContext = context;
            mURL = url;
            mAction = action;
            mBody = body;
            mIsNewArrivals = isNewArrivals;
        }

        private String POST(String url, String action, JSONObject body){
            InputStream inputStream = null;
            String result = "";
            try {

                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost(url);

                String json = "";

                JSONObject filter = new JSONObject();
//            if (isNewArrivalsLoad)
//                filter.accumulate("category_id", "4");
//            else
//                filter.accumulate("category_id", "3");
//            filter.accumulate("limit", "8");
                JSONObject jsonObject = new JSONObject();
                String curLang = ((MDFApplication) getActivity().getApplication()).getCurrentLanguage();
                String curAirport = ((MDFApplication) getActivity().getApplication()).getAirport();
                String storeID;
                if (curAirport.equalsIgnoreCase("0"))
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

//            jsonObject.accumulate("name", person.getName());
//            jsonObject.accumulate("country", person.getCountry());
//            jsonObject.accumulate("twitter", person.getTwitter());

                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();

                // ** Alternative way to convert Person object to JSON string usin Jackson Lib
                // ObjectMapper mapper = new ObjectMapper();
                // json = mapper.writeValueAsString(person);

                // 5. set json to StringEntity
                StringEntity se = new StringEntity(json);

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
                if (mAction.equalsIgnoreCase(Const.Actions.Products.GET_FILTERED))
                {
//                    if (mIsNewArrivals)
//                    {
//                        arrayListSpecialOffers = new ArrayList<>();
//                        JSONObject json = new JSONObject(result);
//                        JSONArray data = json.getJSONArray("data");
//                        for (int i = 0; i < data.length(); i++) {
//                            JSONObject product = data.getJSONObject(i);
//                            Product item = new Product(product);
//                            arrayListSpecialOffers.add(item);
//                        }
//                        imageAdapter = new ImageAdapter(getActivity(), arrayListSpecialOffers);
//                        gridview.setAdapter(imageAdapter);
//                        imageAdapter.setList(arrayListSpecialOffers);
//                        isSpecialOffresDone = true;
//                        Log.e("ATATAT", "newArrivalsDone");
////                    imageAdapter = new ImageAdapter(getActivity(), arrayListSpecialOffers);
////                    gridview.setAdapter(imageAdapter);
//                    }
//                    else
//                    {
//                        arrayListTopSellers = new ArrayList<>();
//                        JSONObject json = new JSONObject(result);
//                        JSONArray data = json.getJSONArray("data");
//                        for (int i = 0; i < data.length(); i++) {
//                            JSONObject product = data.getJSONObject(i);
//                            Product item = new Product(product);
//                            arrayListTopSellers.add(item);
//                        }
//                        isTopsellersDone = true;
//                        Log.e("ATATAT", "topSellersDone");
//                        ((MainActivity)getActivity()).updateCategoriesMenu();
//                    }
                }
//                else if (mAction.equalsIgnoreCase(Const.Actions.Content.GET_BANNERS))
//                {
//                    final ArrayList<BannerItem> arrayListBanners = new ArrayList<>();
//                    JSONObject json = new JSONObject(result);
//                    JSONArray data = json.getJSONArray("data");
//                    for (int i = 0; i < data.length(); i++) {
//                        JSONObject banner = data.getJSONObject(i);
//                        BannerItem item = new BannerItem(banner);
//                        arrayListBanners.add(item);
//                    }
//
//                    Picasso.Builder picassoLoader = new Picasso.Builder(getContext());
//                    Picasso picasso = picassoLoader.build();
//
//                    if (arrayListBanners.size() > 0) {
//                        picasso
//                                .load(arrayListBanners.get(0).getImage())
//                                .resize(imgBigImageOne.getMeasuredWidth() + 150, imgBigImageOne.getMeasuredHeight())
//                                .centerCrop()
//                                .noPlaceholder()
//                                .into(imgBigImageOne, new Callback() {
//                                    @Override
//                                    public void onSuccess() {
//                                        imgBigImageOne.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
//                                        gridview.invalidate();
//                                        imageAdapter.notifyDataSetInvalidated();
//                                        imageAdapter.notifyDataSetChanged();
//                                        imgBigImageOne.setVisibility(View.VISIBLE);
//                                    }
//
//                                    @Override
//                                    public void onError() {
//
//                                    }
//                                });
//                        picasso
//                                .load(arrayListBanners.get(0).getLabel())
////                            .placeholder(R.drawable.missing_product)
////                            .error(R.drawable.missing_product)
//                                .into(imgSmallImageOne);
//                    }
//
//                    if (arrayListBanners.size() > 1) {
//                        picasso
//                                .load(arrayListBanners.get(1).getImage())
//                                .resize(imgBigImageTwo.getMeasuredWidth() + 150, imgBigImageTwo.getMeasuredHeight())
//                                .centerCrop()
//                                .noPlaceholder()
//                                .into(imgBigImageTwo, new Callback() {
//                                    @Override
//                                    public void onSuccess() {
//                                        imgBigImageTwo.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
//                                        gridview.invalidate();
//                                        imageAdapter.notifyDataSetInvalidated();
//                                        imageAdapter.notifyDataSetChanged();
//                                        imgBigImageTwo.setVisibility(View.VISIBLE);
//                                    }
//
//                                    @Override
//                                    public void onError() {
//
//                                    }
//                                });
//                        picasso
//                                .load(arrayListBanners.get(1).getLabel())
////                            .placeholder(R.drawable.missing_product)
////                            .error(R.drawable.missing_product)
//                                .into(imgSmallImageTwo);
//
//                    }
//
//                    if (arrayListBanners.size() > 2) {
//                        picasso
//                                .load(arrayListBanners.get(2).getImage())
//                                .resize(imgBigImageThree.getMeasuredWidth() + 150, imgBigImageThree.getMeasuredHeight())
//                                .centerCrop()
//                                .noPlaceholder()
//                                .into(imgBigImageThree, new Callback() {
//                                    @Override
//                                    public void onSuccess() {
//                                        imgBigImageThree.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
//                                        gridview.invalidate();
//                                        imageAdapter.notifyDataSetInvalidated();
//                                        imageAdapter.notifyDataSetChanged();
//                                        imgBigImageThree.setVisibility(View.VISIBLE);
//                                    }
//
//                                    @Override
//                                    public void onError() {
//
//                                    }
//                                });
//                        picasso
//                                .load(arrayListBanners.get(2).getLabel())
////                            .placeholder(R.drawable.missing_product)
////                            .error(R.drawable.missing_product)
//                                .into(imgSmallImageThree);
//
//                    }
//
//
////                    imgBigImageOne.setAdjustViewBounds(true);
////                    imgBigImageTwo.setAdjustViewBounds(true);
////                    imgBigImageThree.setAdjustViewBounds(true);
//                    imageAdapter.setList(arrayListSpecialOffers);
//
//                    btnBannerOne.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Fragment fragment = new FragmentItem();
//                            ((FragmentItem) fragment).setProductID(arrayListBanners.get(0).getProductID());
//                            if (fragment != null) {
//                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
//                                transaction.replace(R.id.content_frame, fragment);
//                                transaction.addToBackStack(null);
//                                transaction.commit();
//                            }
//                        }
//                    });
//
//                    btnBannerTwo.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Fragment fragment = new FragmentItem();
//                            ((FragmentItem)fragment).setProductID(arrayListBanners.get(1).getProductID());
//                            if (fragment != null) {
//                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
//                                transaction.replace(R.id.content_frame, fragment);
//                                transaction.addToBackStack(null);
//                                transaction.commit();
//                            }
//                        }
//                    });
//
//                    btnBannerThree.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Fragment fragment = new FragmentItem();
//                            ((FragmentItem)fragment).setProductID(arrayListBanners.get(2).getProductID());
//                            if (fragment != null) {
//                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
//                                transaction.replace(R.id.content_frame, fragment);
//                                transaction.addToBackStack(null);
//                                transaction.commit();
//                            }
//                        }
//                    });
//
//
//                    isBannersDone = true;
//                    gridview.invalidateRowHeight();
//                    Log.e("ATATAT", "bannersDone");
//                    ((MainActivity)getActivity()).invalidateOptionsMenu();
//                }
                else if (mAction.equalsIgnoreCase(Const.Actions.Content.GET_CURRENCY_LIST))
                {
                    ArrayList<Product> arrayListTopSellers = new ArrayList<>();
                    JSONObject json = new JSONObject(result);
                    JSONObject data = json.getJSONObject("data");
//                    ((MDFApplication) getActivity().getApplication()).setCurrencySymbols(data);
                    String curCurrency = ((MDFApplication) getActivity().getApplication()).getCurrentCurrency();
                    if (!data.has(curCurrency)) {
                        curCurrency = (String) ((MDFApplication) getActivity().getApplication()).getCurrencySymbols().keySet().toArray()[0];
                        ((MDFApplication) getActivity().getApplication()).setCurrentCurrency(curCurrency);
                    }
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

    public class SamplePagerAdapter extends PagerAdapter {

        List<Banner> pages = null;
//        boolean preload = true;

        public SamplePagerAdapter(List<Banner> pages){
            this.pages = pages;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position){
            LayoutInflater inflater = (LayoutInflater) container.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View page = inflater.inflate(R.layout.view_pager_page_banner, container, false);
//            final int pos = position;
            final ImageView image = (ImageView)page.findViewById(R.id.imageViewBannerPage);
            image.setTag(position);
            Picasso
                    .with(getContext())
                    .load(bannerList.get(position).getImage())
                    .resize(760, 350)
                    .centerCrop()
//                    .fit() // will explain later
                    .into(image, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.e("!!!Tah",String.format("succ %d", (int)image.getTag()));
                            imageAdapter.notifyDataSetInvalidated();
                            imageAdapter.notifyDataSetChanged();
//                            if (preload) {
//                                if ((int)image.getTag() < (bannerList.size() - 1)) {
//                                    pager.setCurrentItem((int)image.getTag() + 1);
//                                }
//                                else {
//                                    preload = false;
//                                    pager.setCurrentItem(0);
//                                    Log.e("!!!Tah", "preloadOff");
//                                }
//                            }
                        }

                        @Override
                        public void onError() {
//                            Log.e("!!!Tah",String.format("nosucc %d", pos));
                        }
                    });
            final String linkID = bannerList.get(position).getLinkID();
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(getContext(), linkID, Toast.LENGTH_SHORT).show();
                    Fragment fragment = new FragmentItem();
                    ((FragmentItem) fragment).setProductID(linkID);
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
//            imageAdapter.notifyDataSetInvalidated();
//            imageAdapter.notifyDataSetChanged();
            container.addView(page);
            return (page);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public int getCount(){
            return pages.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object){
            return view.equals(object);
        }

    }
}
