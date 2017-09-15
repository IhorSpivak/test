package llc.net.mydutyfree.fragments;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
import java.text.DecimalFormat;
import java.util.List;

import llc.net.mydutyfree.base.CartSingleton;
import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.base.RetrofitService;
import llc.net.mydutyfree.interfaces.PostInterfaceApi;
import llc.net.mydutyfree.newmdf.FullScreenImageActivity;
import llc.net.mydutyfree.newmdf.GridElementAdapter;
import llc.net.mydutyfree.newmdf.MainActivity;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.response.GetOneProductResponse;
import llc.net.mydutyfree.response.Product;
import llc.net.mydutyfree.response.WishListAddOrRemoveProductResponse;
import llc.net.mydutyfree.utils.Const;
import llc.net.mydutyfree.utils.CustomTypefaceSpan;
import llc.net.mydutyfree.utils.FontUtils;
import llc.net.mydutyfree.utils.JsonCreator;
import llc.net.mydutyfree.utils.Utils;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by gorf on 11/27/15.
 */
public class FragmentItem extends Fragment {
    String mProdID;
    Product mItem;
    boolean inWishList = false;
//    Button btnDescription;
//    Button btnConsist;
    Button btnAddToWishList;
    Button btnAddToCart;
    Button btnNewArrivals;
    Button btnTopSellers;
//    TextSwitcher txtDescriptionConsist;
    TextView txtPrice, txtPriceText;
    TextView txtPriceOld, txtPriceOldText;
    TextView txtDescription, txtConsist, txtWherePickUp;
    TextView txtProductTitle;
    ImageView imgMainImage;
    TextView txtIsNew;
    TextView txtIsBestOffer;
    Boolean isNewArrivals;
    ScrollView rootScrollView;
    private HorizontalGridView horizontalGridView;
//    private GridView horizontalGridView1;
//    ArrayList<Product> list;
    List<Product> listNewArrivals;
    List<Product> listTopSellers;
    private  float density;
    GridElementAdapter adapter;
    private Tracker mTracker;
//    ImageAdapter imageAdapter;
    int widthInPixels;

    public FragmentItem() {

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//
//        StrictMode.setThreadPolicy(policy);

    }

    public void setProductID(String productID) {
        mProdID = productID;
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
        final View rootView = inflater.inflate(R.layout.screen_item, container, false);
        density = getResources().getDisplayMetrics().density;
        setHasOptionsMenu(true);

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displaymetrics);
        widthInPixels = displaymetrics.widthPixels;

        mTracker = ((MDFApplication)getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName("Item");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("ui_action")
                .setAction("Item view")
                .setLabel(mProdID)
                .build());


//        mProdID = ((MDFApplication) getActivity().getApplication()).getString(Product.PRODUCT_ID);

        String URL = "http://api.mydutyfree.net?key=GTg7hgrRF43D5fgTGHN";
//        horizontalGridView1 = (GridView)rootView.findViewById(R.id.horizontal_gridView);
//        horizontalGridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                String prodID = new String();
//                if (isNewArrivals)
//                    prodID = listNewArrivals.get(position).getID();
//                else
//                    prodID = listTopSellers.get(position).getID();
//
//                Fragment fragment = new FragmentItem();
//                ((FragmentItem)fragment).setProductID(prodID);
//                if (fragment != null) {
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
//                    transaction.replace(R.id.content_frame, fragment);
//                    transaction.addToBackStack(null);
//                    transaction.commit();
//                }
//            }
//        });
//        connect(URL, action);
        rootScrollView = (ScrollView)rootView.findViewById(R.id.rootScrollViewScreenItem);
//        btnDescription = (Button)rootView.findViewById(R.id.btnDescriptionScreenItem);
//        btnConsist = (Button)rootView.findViewById(R.id.btnConsistScreenItem);
        btnAddToCart = (Button)rootView.findViewById(R.id.btnAddToCartScreenItem);
        btnAddToWishList = (Button)rootView.findViewById(R.id.btnAddToWishListScreenItem);
        btnNewArrivals = (Button)rootView.findViewById(R.id.btnNewArrivalsScreenItem);
        btnTopSellers = (Button)rootView.findViewById(R.id.btnTopSellersScreenItem);
//        txtDescriptionConsist = (TextSwitcher)rootView.findViewById(R.id.txtDescriptionConsistScreenItem);
//        txtDescriptionConsist.setInAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_in);
//        txtDescriptionConsist.setOutAnimation(getContext(), android.support.v7.appcompat.R.anim.abc_fade_out);
//        txtDescriptionConsist.setInAnimation(getContext(), R.anim.fade_in_fast);
//        txtDescriptionConsist.setOutAnimation(getContext(), R.anim.fade_out_fast);
//        txtDescriptionConsist.addView(new TextView(getContext()));
//        txtDescriptionConsist.addView(new TextView(getContext()));

//        txtDescriptionConsist.setFactory(new ViewSwitcher.ViewFactory() {
//            @Override
//            public View makeView() {
//                TextView textView = new TextView(getContext());
//                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
//                textView.setTextColor(Color.GRAY);
////                textView.setGravity(Gravity.CENTER_HORIZONTAL);
//                textView.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/GOTHIC.TTF"));
////                textView.setShadowLayer(10, 10, 10, Color.BLACK);
//                return textView;
//            }
//        });


        String userAuthKey = MDFApplication.getMDFApplication().getUserAuthKey();
        if (userAuthKey.length() > 10) {
            btnAddToWishList.setVisibility(View.VISIBLE);
            btnAddToWishList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (inWishList) {
                        RetrofitService retrofitService = new RetrofitService();
                        PostInterfaceApi service = retrofitService.create();
                        Call<WishListAddOrRemoveProductResponse> call = service.wishListRemoveProduct(JsonCreator.WishList.RemoveProductWithID(mItem.getID()));
                        ((MainActivity) getActivity()).showProgress();
                        call.enqueue(new retrofit2.Callback<WishListAddOrRemoveProductResponse>() {
                            @Override
                            public void onResponse(Call<WishListAddOrRemoveProductResponse> call, Response<WishListAddOrRemoveProductResponse> response) {
                                ((MainActivity) getActivity()).hideProgress();
                                if (response.body().getStatus().equalsIgnoreCase(Const.Actions.Status.SUCCESS)) {
                                    inWishList = false;
                                    btnAddToWishList.setText(getResources().getString(R.string.wishlist_add).toUpperCase());
                                } else {
                                }
                            }

                            @Override
                            public void onFailure(Call<WishListAddOrRemoveProductResponse> call, Throwable t) {
                                ((MainActivity) getActivity()).hideProgress();
                            }
                        });
                    } else {
                        MixpanelAPI mixpanel = MixpanelAPI.getInstance(getContext(), Const.MIXPANEL_PROJECT_TOKEN);
                        try {
                            JSONObject properties = new JSONObject();
                            properties.put("product_id", mItem.getID());
                            properties.put("product_name", mItem.getName());
                            mixpanel.track("Wishlist Add", properties);
                        } catch (JSONException e) {}
                        RetrofitService retrofitService = new RetrofitService();
                        PostInterfaceApi service = retrofitService.create();
                        Call<WishListAddOrRemoveProductResponse> call = service.wishListAddProduct(JsonCreator.WishList.AddProductWithID(mItem.getID()));
                        ((MainActivity) getActivity()).showProgress();
                        call.enqueue(new retrofit2.Callback<WishListAddOrRemoveProductResponse>() {
                            @Override
                            public void onResponse(Call<WishListAddOrRemoveProductResponse> call, Response<WishListAddOrRemoveProductResponse> response) {
                                ((MainActivity) getActivity()).hideProgress();
                                if (response.body().getStatus().equalsIgnoreCase(Const.Actions.Status.SUCCESS)) {
                                    inWishList = true;
                                    btnAddToWishList.setText(getResources().getString(R.string.wishlist_remove).toUpperCase());
                                } else {
                                }
                            }

                            @Override
                            public void onFailure(Call<WishListAddOrRemoveProductResponse> call, Throwable t) {
                                ((MainActivity) getActivity()).hideProgress();
                            }
                        });
                    }
                }
            });
        }
        else
            btnAddToWishList.setVisibility(View.GONE);

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curAirport = ((MDFApplication) getActivity().getApplication()).getAirport();
                if (curAirport.equalsIgnoreCase("0")) {
                    AlertDialog.Builder alertSelectAirport = new AlertDialog.Builder(getContext());
                    alertSelectAirport.setCancelable(false);
                    alertSelectAirport.setNegativeButton(getResources().getString(R.string.ok_caps), null);
                    TextView message = new TextView(getContext());
                    message.setText(getResources().getString(R.string.select_airport_hint));
                    message.setGravity(Gravity.CENTER_HORIZONTAL);
                    message.setPadding(
                            (int) Utils.dpToPx(10),
                            (int) Utils.dpToPx(10),
                            (int) Utils.dpToPx(10),
                            (int) Utils.dpToPx(10)
                    );
                    FontUtils.setBoldFont(getContext(), message);
                    alertSelectAirport.setView(message);
                    alertSelectAirport.show();
                }
                else {
                    MixpanelAPI mixpanel = MixpanelAPI.getInstance(getContext(), Const.MIXPANEL_PROJECT_TOKEN);
                    try {
                        JSONObject properties = new JSONObject();
                        properties.put("product_id", mItem.getID());
                        properties.put("product_name", mItem.getName());
                        mixpanel.track("Cart Add", properties);
                    } catch (JSONException e) {

                    }

                    CartSingleton.getInstance().AddProduct(mItem);
                    ((MainActivity) getActivity()).updateHotCount(CartSingleton.getInstance().getProductsCount());
                    Fragment fragment = new FragmentCart();
                    if (fragment != null) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                        transaction.replace(R.id.content_frame, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            }
        });

        FontUtils.setNormalFont(getContext(), rootView);
        txtConsist = (TextView)rootView.findViewById(R.id.txtConsistScreenItem);
        txtWherePickUp = (TextView)rootView.findViewById(R.id.txtWherePickUpScreenItem);
        txtDescription = (TextView)rootView.findViewById(R.id.txtDescriptionScreenItem);
        FontUtils.setNormalFont(getContext(), txtConsist);
        FontUtils.setNormalFont(getContext(), txtDescription);
        FontUtils.setNormalFont(getContext(), txtWherePickUp);

        txtPriceOld = (TextView)rootView.findViewById(R.id.txtPriceScreenItem);
        FontUtils.setNormalFont(getContext(), txtPriceOld);
        txtPrice = (TextView)rootView.findViewById(R.id.txtSpecialPriceScreenItem);
        FontUtils.setBoldFont(getContext(), txtPrice);
        txtPriceOldText = (TextView)rootView.findViewById(R.id.txtPriceTextScreenItem);
        FontUtils.setNormalFont(getContext(), txtPriceOld);
        txtPriceText = (TextView)rootView.findViewById(R.id.txtSpecialPriceTextScreenItem);
        FontUtils.setBoldFont(getContext(), txtPrice);

        txtIsNew = (TextView)rootView.findViewById(R.id.txtNewScreenItem);
        FontUtils.setBoldFont(getContext(), txtIsNew);
        txtIsBestOffer = (TextView)rootView.findViewById(R.id.txtBestOfferScreenItem);
        FontUtils.setBoldFont(getContext(), txtIsBestOffer);


        txtProductTitle = (TextView)rootView.findViewById(R.id.txtTitleScreenItem);
        FontUtils.setBoldFont(getContext(), txtProductTitle);

//        txtProductName = (TextView)rootView.findViewById(R.id.txtProductScreenItem);
//        FontUtils.setBoldFont(getContext(), txtProductName);
//
//        txtName = (TextView)rootView.findViewById(R.id.txtNameScreenItem);
//        FontUtils.setBoldFont(getContext(), txtName);
//
//        txtMainAttribute = (TextView)rootView.findViewById(R.id.txtMainAttributeScreenItem);
//        FontUtils.setNormalFont(getContext(), txtMainAttribute);

        imgMainImage = (ImageView)rootView.findViewById(R.id.imgMainImageScreenItem);

//          Typeface tf= Typeface.createFromAsset(getContext().getAssets(), "fonts/GOTHIC.TTF");
//
//        btnAddToCart.setTypeface(tf);


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

        FontUtils.setBoldFont(getContext(), btnNewArrivals);
        FontUtils.setBoldFont(getContext(), btnTopSellers);
//        FontUtils.setBoldFont(getContext(), btnConsist);
//        FontUtils.setBoldFont(getContext(), btnDescription);
        FontUtils.setBoldFont(getContext(), btnAddToCart);
        btnAddToCart.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
        FontUtils.setBoldFont(getContext(), btnAddToWishList);
        btnAddToWishList.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
//        btnConsist.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
//        btnDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
        btnNewArrivals.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
        btnTopSellers.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);

        JSONObject bodyGetNewArrivals = new JSONObject();
        try {
            JSONObject filter = new JSONObject();
            filter.accumulate("extra_flag", "new");
            filter.accumulate("random", "1");
//            filter.accumulate("limit", "8");
            bodyGetNewArrivals.accumulate("filter", filter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject bodyGetTopSellers = new JSONObject();
        try {
            JSONObject filter = new JSONObject();
            filter.accumulate("extra_flag", "top");
            filter.accumulate("random", "1");
//            filter.accumulate("limit", "8");
            bodyGetTopSellers.accumulate("filter", filter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONObject bodyGetProduct = new JSONObject();
        JSONObject filter = new JSONObject();
        try {
            filter.accumulate("id", mProdID);
            bodyGetProduct.accumulate("filter", filter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        new AsyncCustomPost(getContext(), Const.URLS.URL, Const.Actions.Products.GET_ONE, filter, false).execute(URL);
        RetrofitService retrofitService = new RetrofitService(true);
        final PostInterfaceApi service = retrofitService.create();
        Call<GetOneProductResponse> callGetOneProduct = service.getOneProduct(JsonCreator.GetOneProduct(mProdID));
        ((MainActivity) getActivity()).showProgress();
        callGetOneProduct.enqueue(new retrofit2.Callback<GetOneProductResponse>() {
            @Override
            public void onResponse(Call<GetOneProductResponse> call, Response<GetOneProductResponse> response) {
                mItem = response.body().getData();

                if (mItem.inWishlist()) {
                    inWishList = true;
                    btnAddToWishList.setText(getResources().getString(R.string.wishlist_remove).toUpperCase());
                }
                else {
                    inWishList = false;
                    btnAddToWishList.setText(getResources().getString(R.string.wishlist_add).toUpperCase());
                }

                txtProductTitle.setText(mItem.getTitle());
//                txtMainAttribute.setText(mItem.getMainAttribute());
//                txtName.setText(mItem.getName());
//                txtProductName.setText(mItem.getBrandName());

                String currentCurrency = ((MDFApplication)(getActivity().getApplication())).getCurrentCurrency();
                String currencySymbol = ((MDFApplication)(getActivity().getApplication())).getCurrencySymbols().get(currentCurrency);

                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String priceOldString = decimalFormat.format(mItem.getPriceOld().get(currentCurrency));
                priceOldString = new StringBuilder().append(priceOldString).append(" ").append(currencySymbol).toString();
                txtPriceOld.setText(priceOldString);
                String priceString = decimalFormat.format(mItem.getPrice().get(currentCurrency));
                priceString = new StringBuilder().append(priceString).append(" ").append(currencySymbol).toString();
                txtPrice.setText(priceString);
                txtPriceText.setText(MDFApplication.getAppContext().getResources().getString(R.string.price_in_mydutyfree));

                if (!mItem.isNoDiscount()) {
                    priceOldString = decimalFormat.format(mItem.getPriceOld().get(currentCurrency));
                    priceOldString = new StringBuilder().append(priceOldString).append(currencySymbol.toString()).toString();
                    txtPriceOld.setTextColor(ContextCompat.getColor(MDFApplication.getAppContext(), R.color.colorGray));
                    txtPriceOld.setText(priceOldString);
                    txtPriceOldText.setText(MDFApplication.getAppContext().getResources().getString(R.string.price_in_airport));
                    txtPriceOld.setPaintFlags(txtPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else {
                    txtPriceOld.setTextColor(ContextCompat.getColor(MDFApplication.getAppContext(), R.color.colorRed));
                    txtPriceOld.setText(MDFApplication.getAppContext().getResources().getString(R.string.no_discount));
                    txtPriceOldText.setText(null);
                }


                Picasso.Builder picassoLoader = new Picasso.Builder(getContext());
                Picasso picasso = picassoLoader.build();
                picasso
                        .load(Uri.encode(mItem.getImages().get(0), "@#&=*+-_.,:!?()/~'%"))
                        .error(R.drawable.missing_product)
                        .into(imgMainImage, new Callback() {
                            @Override
                            public void onSuccess() {
                                imgMainImage.getDrawable();
                                imgMainImage.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent myIntent = new Intent(getContext(), FullScreenImageActivity.class);
                                        myIntent.putExtra("image", mItem.getImages().get(1)); //Optional parameters
                                        Bundle bndlanimation =
                                                ActivityOptions.makeCustomAnimation(getContext(), R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
                                        getActivity().startActivity(myIntent, bndlanimation);
                                    }
                                });
//                                Log.e("!!!!!!", "!!!");
                            }

                            @Override
                            public void onError() {
//                                Log.e("!!!!!!", "!!!");
                            }
                        });

                Log.e("e", "e");

                ((MainActivity)getActivity()).setTitle(mItem.getName());

                if (mItem.isNew() && mItem.isBestOffer()) {
                    txtIsNew.setVisibility(View.GONE);
                    txtIsBestOffer.setVisibility(View.VISIBLE);
                }
                else if (mItem.isNew() && (!mItem.isBestOffer())) {
                    txtIsNew.setVisibility(View.VISIBLE);
                    txtIsBestOffer.setVisibility(View.GONE);
                }
                else if ((!mItem.isNew()) && mItem.isBestOffer()) {
                    txtIsNew.setVisibility(View.GONE);
                    txtIsBestOffer.setVisibility(View.VISIBLE);
                }
                else {
                    txtIsNew.setVisibility(View.GONE);
                    txtIsBestOffer.setVisibility(View.GONE);
                }

//                btnDescription.setPressed(true);
//                btnDescription.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
//                txtDescriptionConsist.setText(mItem.getDescription());
//
//                btnDescription.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        v.setPressed(true);
//                        btnConsist.setPressed(false);
//                        btnConsist.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
//                        btnDescription.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
//                        txtDescriptionConsist.setText(mItem.getDescription());
//                        return true;
//                    }
//                });
//
//                btnConsist.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        v.setPressed(true);
//                        btnDescription.setPressed(false);
//                        txtDescriptionConsist.setText(mItem.getConsist());
//                        btnConsist.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
//                        btnDescription.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
//                        return true;
//                    }
//                });

                if (mItem.getDescription().length()>0) {
                    String stringDescription = getResources().getString(R.string.description_caps);
                    stringDescription = stringDescription.substring(0, 1).toUpperCase() + stringDescription.substring(1).toLowerCase() + ":";
                    String strDescription = mItem.getDescription();
                    String finalDescription = stringDescription + "\n" + strDescription;
                    Spannable spanDesc = new SpannableString(finalDescription);
                    spanDesc.setSpan(new CustomTypefaceSpan("", MDFApplication.getBoldTypeface()), finalDescription.indexOf(stringDescription), finalDescription.indexOf(stringDescription) + stringDescription.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    txtDescription.setText(spanDesc);
                }
                else
                    txtDescription.setVisibility(View.GONE);

                if (mItem.getConsist().length()>0) {
                    String stringConsist = getResources().getString(R.string.consist_caps);
                    stringConsist = stringConsist.substring(0, 1).toUpperCase() + stringConsist.substring(1).toLowerCase() + ":";
                    String strConsist = mItem.getConsist();
                    String finalConsist = stringConsist + "\n" + strConsist;
                    Spannable spanConsist = new SpannableString(finalConsist);
                    spanConsist.setSpan(new CustomTypefaceSpan("", MDFApplication.getBoldTypeface()), finalConsist.indexOf(stringConsist), finalConsist.indexOf(stringConsist) + stringConsist.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    txtConsist.setText(spanConsist);
                }
                else
                    txtConsist.setVisibility(View.GONE);

                txtWherePickUp.setText(null);
                txtWherePickUp.setText(getResources().getString(R.string.this_item_is_in));
                txtWherePickUp.append("\n");
                txtWherePickUp.append(mItem.getPickUpStoreData().getName());

//                Call<GetProductsResponse> callGetNewArrivals = service.getProducts(JsonCreator.GetSomeSpecialOffers(0, true));
//                callGetNewArrivals.enqueue(new retrofit2.Callback<GetProductsResponse>() {
//                    @Override
//                    public void onResponse(Call<GetProductsResponse> call, Response<GetProductsResponse> response) {
//                        listNewArrivals = response.body().getData();
//                        Call<GetProductsResponse> callGetTopSellers = service.getProducts(JsonCreator.GetSomeTopSellers(0, true));
//                        callGetTopSellers.enqueue(new retrofit2.Callback<GetProductsResponse>() {
//                            @Override
//                            public void onResponse(Call<GetProductsResponse> call, Response<GetProductsResponse> response) {
                                ((MainActivity) getActivity()).hideProgress();
//                                listTopSellers = response.body().getData();
//
//                                horizontalGridView.setNumRows(1);
//                                horizontalGridView.setHorizontalMargin(0);
//                                horizontalGridView.setHasFixedSize(false);
//                                horizontalGridView.setAdapter(adapter);
//                                btnNewArrivals.setPressed(true);
//                                btnNewArrivals.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
//                                adapter.setList(listNewArrivals);
//                                isNewArrivals = true;
//                                btnNewArrivals.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        isNewArrivals = true;
//                                        btnNewArrivals.setPressed(true);
//                                        btnNewArrivals.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
//                                        btnTopSellers.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
//                                        btnTopSellers.setPressed(false);
//                                        adapter.setList(listNewArrivals);
//                                    }
//                                });
//                                btnTopSellers.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        isNewArrivals = false;
//                                        btnTopSellers.setPressed(true);
//                                        btnTopSellers.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
//                                        btnNewArrivals.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
//                                        btnNewArrivals.setPressed(false);
//                                        adapter.setList(listTopSellers);
//                                    }
//                                });
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<GetProductsResponse> call, Throwable t) {
//                                ((MainActivity) getActivity()).hideProgress();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onFailure(Call<GetProductsResponse> call, Throwable t) {
//                        ((MainActivity) getActivity()).hideProgress();
//                    }
//                });
            }

            @Override
            public void onFailure(Call<GetOneProductResponse> call, Throwable t) {
                ((MainActivity) getActivity()).hideProgress();
            }
        });

//        new AsyncCustomPost(getContext(), Const.URLS.URL, Const.Actions.Products.GET_FILTERED, bodyGetNewArrivals, true).execute(URL);
//        new AsyncCustomPost(getContext(), Const.URLS.URL, Const.Actions.Products.GET_FILTERED, bodyGetTopSellers, false).execute(URL);

//        horizontalGridView = (HorizontalGridView) rootView.findViewById(R.id.gridViewScreenItem);
//        adapter = new GridElementAdapter(getActivity(), getContext(), null, new GridElementAdapter.MyAdapterItemClick() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Fragment fragment = new FragmentItem();
//                String productID = null;
//                if (isNewArrivals)
//                    productID = listNewArrivals.get(position).getID();
//                else
//                    productID = ((Product)listTopSellers.get(position)).getID();
//                if (productID.equalsIgnoreCase(mProdID)) {
//                    ObjectAnimator objectAnimator = ObjectAnimator.ofInt(rootScrollView, "scrollY", rootView.getScrollY(), 0).setDuration(300);
//                    objectAnimator.start();;
//                }
//                else {
//                    ((FragmentItem) fragment).setProductID(productID);
//                    if (fragment != null) {
//                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                        FragmentTransaction transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
//                        transaction.replace(R.id.content_frame, fragment);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
//                    }
//                }
//            }
//        });
//        horizontalGridView.setHorizontalMargin((int)Utils.dpToPx(2));
//        horizontalGridView.setNumRows(1);
//        horizontalGridView.setHorizontalMargin(0);
//        horizontalGridView.setHasFixedSize(false);
//        horizontalGridView.setAdapter(adapter);


//        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics displaymetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displaymetrics);
        int heightInPixels = displaymetrics.heightPixels;
        int widthInPixels = displaymetrics.widthPixels;


//        GridView gridview = (GridView) rootView.findViewById(R.id.gridViewScreenItem1);
//        gridview.setLayoutParams(new FrameLayout.LayoutParams((int) Utils.dpToPx(220), 300));
//        gridview.setGravity(Gravity.CENTER);
//        gridview.setMinimumHeight((int)Utils.dpToPx(260));
//        gridview.setColumnWidth((int)(Utils.dpToPx(widthInPixels)) / 2);

//        gridview.setNumColumns(8);
//        gridview.setAdapter(new ImageAdapter(getActivity(), list));
//        gridview.setRotation(-90);
        return rootView;
    }

    class AsyncCustomPost extends AsyncTask<String, String, String> {
        private Context mContext;
        private JSONObject mBody;
        private String mAction;
        private String mURL;
        private Boolean mIsNewArrivals;

        public AsyncCustomPost(Context context, String url, String action, JSONObject body, boolean isNewArrivals) {
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

                jsonObject.accumulate("lang_code", curLang);
                jsonObject.accumulate("action", action);
                jsonObject.accumulate("body", body);
                jsonObject.accumulate("store_id", storeID);
                jsonObject.accumulate("auth_key", Const.API_KEY);

                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();

                // ** Alternative way to convert Person object to JSON string usin Jackson Lib
                // ObjectMapper mapper = new ObjectMapper();
                // json = mapper.writeValueAsString(person);

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
//                if (mAction.equalsIgnoreCase(Const.Actions.Products.GET_ONE))
//                {
                    JSONObject json = new JSONObject(result);
//                    mItem = new Product(json.getJSONObject("data"));
//                    txtMainAttribute.setText(mItem.getMainAttribute());
//                    txtName.setText(mItem.getName());
//                    txtProductName.setText(mItem.getBrandName());
//
//                    String currentCurrency = ((MDFApplication)(getActivity().getApplication())).getCurrentCurrency();
//                    String currencySymbol = ((MDFApplication)(getActivity().getApplication())).getCurrencySymbols().get(currentCurrency);
//
//                    DecimalFormat decimalFormat = new DecimalFormat("#.00");
//                    String priceOldString = decimalFormat.format(mItem.getPriceOld().get(currentCurrency));
//                    priceOldString = new StringBuilder().append(priceOldString).append(" ").append(currencySymbol).toString();
//                    txtPriceOld.setText(priceOldString);
//                    String priceString = decimalFormat.format(mItem.getPrice().get(currentCurrency));
//                    priceString = new StringBuilder().append(priceString).append(" ").append(currencySymbol).toString();
//                    txtPrice.setText(priceString);
//
//                    Picasso.Builder picassoLoader = new Picasso.Builder(getContext());
//                    Picasso picasso = picassoLoader.build();
//                    picasso
//                            .load(mItem.getImages().get(0))
//                            .error(R.drawable.missing_product)
////                            .centerCrop()
////                            .noPlaceholder()
//                            .into(imgMainImage, new Callback() {
//                                @Override
//                                public void onSuccess() {
//                                    imgMainImage.getDrawable();
//                                    imgMainImage.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            Intent myIntent = new Intent(getContext(), FullScreenImageActivity.class);
//                                            myIntent.putExtra("image", mItem.getImages().get(1)); //Optional parameters
//                                            Bundle bndlanimation =
//                                                    ActivityOptions.makeCustomAnimation(getContext(), R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
//                                            getActivity().startActivity(myIntent, bndlanimation);
//                                        }
//                                    });
////                                    imgBigImageOne.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
////                                    gridview.invalidate();
////                                    imageAdapter.notifyDataSetInvalidated();
////                                    imageAdapter.notifyDataSetChanged();
////                                    imgBigImageOne.setVisibility(View.VISIBLE);
//                                    Log.e("!!!!!!", "!!!");
//                                }
//
//                                @Override
//                                public void onError() {
//                                    Log.e("!!!!!!", "!!!");
//                                }
//                            });
//
////                    Picasso.with(getActivity().getBaseContext())
////                            .load(mItem.getImages().get(1))
////                            .error(R.drawable.missing_product)
////                            .into(imgMainImage);
//                    Log.e("e", "e");
//
//                    ((MainActivity)getActivity()).setTitle(mItem.getName());
//
//                    if (mItem.isNew() && mItem.isBestOffer()) {
//                        txtIsNew.setVisibility(View.GONE);
//                        txtIsBestOffer.setVisibility(View.VISIBLE);
//                    }
//                    else if (mItem.isNew() && (!mItem.isBestOffer())) {
//                        txtIsNew.setVisibility(View.VISIBLE);
//                        txtIsBestOffer.setVisibility(View.GONE);
//                    }
//                    else if ((!mItem.isNew()) && mItem.isBestOffer()) {
//                        txtIsNew.setVisibility(View.GONE);
//                        txtIsBestOffer.setVisibility(View.VISIBLE);
//                    }
//                    else {
//                        txtIsNew.setVisibility(View.GONE);
//                        txtIsBestOffer.setVisibility(View.GONE);
//                    }
//
//                    btnDescription.setPressed(true);
//                    btnDescription.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
//                    txtDescriptionConsist.setText(mItem.getDescription());
//
//                    btnDescription.setOnTouchListener(new View.OnTouchListener() {
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event) {
//                            v.setPressed(true);
//                            btnConsist.setPressed(false);
//                            btnConsist.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
//                            btnDescription.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
//                            txtDescriptionConsist.setText(mItem.getDescription());
//                            return true;
//                        }
//                    });
//
//                    btnConsist.setOnTouchListener(new View.OnTouchListener() {
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event) {
//                            v.setPressed(true);
//                            btnDescription.setPressed(false);
//                            txtDescriptionConsist.setText(mItem.getConsist());
//                            btnConsist.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
//                            btnDescription.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
//                            return true;
//                        }
//                    });
//                    Log.e("ATATAT", "newArrivalsDone");
//                }
//                else if (mAction.equalsIgnoreCase(Const.Actions.Products.GET_FILTERED))
//                {
//                    if (mIsNewArrivals)
//                    {
//                        listNewArrivals = new ArrayList<>();
//                        JSONObject json = new JSONObject(result);
//                        JSONArray data = json.getJSONArray("data");
//                        for (int i = 0; i < data.length(); i++) {
//                            JSONObject product = data.getJSONObject(i);
//                            Product item = new Product(product);
//                            listNewArrivals.add(item);
//                        }
//
////                        adapter = new GridElementAdapter(getActivity(), getContext(), null);
//                        horizontalGridView.setNumRows(1);
//                        horizontalGridView.setHorizontalMargin(0);
//                        horizontalGridView.setHasFixedSize(false);
//                        horizontalGridView.setAdapter(adapter);
//                        btnSpecialOffres.setPressed(true);
//                        btnSpecialOffres.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
//                        adapter.setList(listNewArrivals);
//                        isNewArrivals = true;
//                        Log.e("ATATAT", "newArrivalsDone");
//
////                        imageAdapter = new ImageAdapter(getActivity(), listNewArrivals);
////                        horizontalGridView1.setAdapter(imageAdapter);
////                        imageAdapter.setList(listNewArrivals);
////                        size=listNewArrivals.size();
////                        horizontalGridView1.setNumColumns(size);
////                        gridViewSetting(horizontalGridView1);
//
//                    }
//                    else
//                    {
//                        listTopSellers = new ArrayList<>();
//                        JSONObject json = new JSONObject(result);
//                        JSONArray data = json.getJSONArray("data");
//                        for (int i = 0; i < data.length(); i++) {
//                            JSONObject product = data.getJSONObject(i);
//                            Product item = new Product(product);
//                            listTopSellers.add(item);
//                        }
//                        Log.e("ATATAT", "topSellersDone");
//                    }
//                    btnSpecialOffres.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            isNewArrivals = true;
//                            btnSpecialOffres.setPressed(true);
//                            btnSpecialOffres.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
//                            btnTopSellers.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
//                            btnTopSellers.setPressed(false);
//                            adapter.setList(listNewArrivals);
////                            size=listNewArrivals.size();
////                            horizontalGridView1.setNumColumns(size);
////                            gridViewSetting(horizontalGridView1);
////                            imageAdapter.setList(listNewArrivals);
//                        }
//                    });
//                    btnTopSellers.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            isNewArrivals = false;
//                            btnTopSellers.setPressed(true);
//                            btnTopSellers.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
//                            btnSpecialOffres.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
//                            btnSpecialOffres.setPressed(false);
//                            adapter.setList(listTopSellers);
//
////                            size=listTopSellers.size();
////                            horizontalGridView1.setNumColumns(size);
////                            gridViewSetting(horizontalGridView1);
////                            imageAdapter.setList(listTopSellers);
//
//                        }
//                    });
//                }
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
}
