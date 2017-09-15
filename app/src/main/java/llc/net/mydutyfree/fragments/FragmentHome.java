package llc.net.mydutyfree.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.List;

import llc.net.mydutyfree.adapters.GridWithHeaderAndFooterAdapter;
import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.base.RetrofitService;
import llc.net.mydutyfree.interfaces.GridWithHeaderAndFooterAdapterInterface;
import llc.net.mydutyfree.interfaces.PostInterfaceApi;
import llc.net.mydutyfree.newmdf.MainActivity;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.response.Banner;
import llc.net.mydutyfree.response.GetBannersResponse;
import llc.net.mydutyfree.response.GetCurrencyResponse;
import llc.net.mydutyfree.response.GetProductsResponse;
import llc.net.mydutyfree.response.Product;
import llc.net.mydutyfree.response.WishListAddOrRemoveProductResponse;
import llc.net.mydutyfree.utils.Const;
import llc.net.mydutyfree.utils.JsonCreator;
import llc.net.mydutyfree.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gorf on 7/26/16.
 */
public class FragmentHome extends Fragment implements GridWithHeaderAndFooterAdapterInterface{

    List<Product> listTopSellers;
    List<Product> listSpecialOffers;
    List<Banner> listBanners;
    RecyclerView recyclerView;
    private Tracker mTracker;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.screen_recycler_view, container, false);

        mTracker = ((MDFApplication)getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName("Home");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        ((MainActivity)getActivity()).setTitle(getResources().getString(R.string.app_name));

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
//        recyclerView.addItemDecoration(new MarginDecoration(this));
        recyclerView.setHasFixedSize(true);
        //add ItemDecoration
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration((int)Utils.dpToPx(5)));
        recyclerView.addItemDecoration(new HorizontalSpaceItemDecoration((int)Utils.dpToPx(5)));
        //or
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        //or
//        recyclerView.addItemDecoration(
//                new DividerItemDecoration(getActivity(), R.drawable.divider));


//        View header = LayoutInflater.from(getContext()).inflate(R.layout.home_header_view, recyclerView, false);
//        header.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "Header", Toast.LENGTH_SHORT).show();
//            }
//        });
//        View footer = LayoutInflater.from(getContext()).inflate(R.layout.home_footer_view, recyclerView, false);
//        footer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "Footer", Toast.LENGTH_SHORT).show();
//            }
//        });


        RetrofitService retrofitService = new RetrofitService();
        final PostInterfaceApi service = retrofitService.create();
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);

        int productsQuantity = 0;
        if (tabletSize)
            productsQuantity = 12;
        else
            productsQuantity = 8;
        final int finalProductsQuantity = productsQuantity;

        Call<GetProductsResponse> callGetSpecialOffers = service.getProducts(JsonCreator.GetSomeSpecialOffers(finalProductsQuantity, true));
        ((MainActivity)getActivity()).showProgress();
        callGetSpecialOffers.enqueue(new Callback<GetProductsResponse>() {
            @Override
            public void onResponse(Call<GetProductsResponse> call, Response<GetProductsResponse> response) {
                listSpecialOffers = response.body().getData();
                Call<GetProductsResponse> callGetTopSellers = service.getProducts(JsonCreator.GetSomeTopSellers(finalProductsQuantity, true));
                callGetTopSellers.enqueue(new Callback<GetProductsResponse>() {
                    @Override
                    public void onResponse(Call<GetProductsResponse> call, Response<GetProductsResponse> response) {
                                Call<GetCurrencyResponse> callGetCurrency = service.getCurrency(JsonCreator.GetCurrency());
                                callGetCurrency.enqueue(new Callback<GetCurrencyResponse>() {
                                    @Override
                                    public void onResponse(Call<GetCurrencyResponse> call, Response<GetCurrencyResponse> response) {
                                        ((MainActivity)getActivity()).hideProgress();
                                        ((MDFApplication) getActivity().getApplication()).setCurrencies(response.body().getData());
                                        String curCurrency = ((MDFApplication) getActivity().getApplication()).getCurrentCurrency();
                                        if (!response.body().getData().containsKey(curCurrency)) {
                                            curCurrency = (String) ((MDFApplication) getActivity().getApplication()).getCurrencies().keySet().toArray()[0];
                                            ((MDFApplication) getActivity().getApplication()).setCurrentCurrency(curCurrency);
                                        }
                                        runAll();
                                    }

                                    @Override
                                    public void onFailure(Call<GetCurrencyResponse> call, Throwable t) {
                                        ((MainActivity)getActivity()).hideProgress();
                                    }
                                });
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<GetBannersResponse> call, Throwable t) {
//                                ((MainActivity)getActivity()).hideProgress();
//                            }
//                        });

                    }

                    @Override
                    public void onFailure(Call<GetProductsResponse> call, Throwable t) {
                        ((MainActivity)getActivity()).hideProgress();
                    }
                });
            }

            @Override
            public void onFailure(Call<GetProductsResponse> call, Throwable t) {
                ((MainActivity)getActivity()).hideProgress();
            }
        });

        return rootView;
    }

    private void runAll() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(manager);
        String userAuthKey = ((MDFApplication)getActivity().getApplication()).getUserAuthKey();
        final GridWithHeaderAndFooterAdapter adapter = new GridWithHeaderAndFooterAdapter(listSpecialOffers, listTopSellers, listBanners, this, userAuthKey);
        recyclerView.setAdapter(adapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.isHeader(position) ? manager.getSpanCount() : (adapter.isFooter(position) ? manager.getSpanCount() : 1);
            }
        });

        ((MainActivity)getActivity()).updateCategoriesMenu();
    }

    @Override
    public void onBannerClicked(Banner banner) {

        Fragment fragment = null;
        if (banner.getLinkType().equalsIgnoreCase(Banner.BANNER_TYPE_PRODUCT)) {
            fragment = new FragmentItem();
            ((FragmentItem) fragment).setProductID(banner.getLinkID());
        }
        else if (banner.getLinkType().equalsIgnoreCase(Banner.BANNER_TYPE_CATEGORY)) {
            fragment = new FragmentCategory();
            ((FragmentCategory) fragment).setCategoryID(banner.getLinkID());
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
            transaction.replace(R.id.content_frame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onShowAllGoodsClicked(Boolean isNewArrivals) {
        Fragment fragment = new FragmentCategory();
        if (isNewArrivals)
            ((FragmentCategory) fragment).setExtraCategoryType(FragmentCategory.ExtraCategoryType.SPECIAL_OFFERS);
        else
            ((FragmentCategory) fragment).setExtraCategoryType(FragmentCategory.ExtraCategoryType.TOP_SELLERS);
        if (fragment != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
            transaction.replace(R.id.content_frame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
//        Intent intent = new Intent(Intent.CATEGORY_APP_EMAIL);
////        intent.setType("text/plain");
////        intent.setType("message/rfc822");
////        intent.putExtra(Intent.EXTRA_SUBJECT, "testsubj");
////        intent.putExtra(Intent.EXTRA_TEXT, "test message");
//        Intent mailer = Intent.createChooser(intent, null);
//        startActivity(mailer);
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_APP_EMAIL);
//        getActivity().startActivity(intent);
    }

    @Override
    public void onItemCLicked(Product product) {
        Fragment fragment = new FragmentItem();
        ((FragmentItem) fragment).setProductID(product.getID());
        if (fragment != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
            transaction.replace(R.id.content_frame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onItemWishCLicked(Product product) {
        final Product finalProduct = product;
        if (product.inWishlist()) {
            RetrofitService retrofitService = new RetrofitService();
            PostInterfaceApi service = retrofitService.create();
            Call<WishListAddOrRemoveProductResponse> call = service.wishListRemoveProduct(JsonCreator.WishList.RemoveProductWithID(product.getID()));
            ((MainActivity) getActivity()).showProgress();
            call.enqueue(new retrofit2.Callback<WishListAddOrRemoveProductResponse>() {
                @Override
                public void onResponse(Call<WishListAddOrRemoveProductResponse> call, Response<WishListAddOrRemoveProductResponse> response) {
                    ((MainActivity) getActivity()).hideProgress();
                    if (response.body().getStatus().equalsIgnoreCase(Const.Actions.Status.SUCCESS)) {
//                        inWishList = false;
//                        btnAddToWishList.setText(getResources().getString(R.string.wishlist_add).toUpperCase());
                        if (listSpecialOffers.contains(finalProduct)) {
                            listSpecialOffers.get(listSpecialOffers.indexOf(finalProduct)).setInWishList(false);
                        }
                        else if (listTopSellers.contains(finalProduct)) {
                            listTopSellers.get(listTopSellers.indexOf(finalProduct)).setInWishList(false);
                        }
                        ((GridWithHeaderAndFooterAdapter)recyclerView.getAdapter()).updateAll();
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<WishListAddOrRemoveProductResponse> call, Throwable t) {
                    ((MainActivity) getActivity()).hideProgress();
                }
            });
        } else {
            RetrofitService retrofitService = new RetrofitService();
            PostInterfaceApi service = retrofitService.create();
            Call<WishListAddOrRemoveProductResponse> call = service.wishListAddProduct(JsonCreator.WishList.AddProductWithID(product.getID()));
            ((MainActivity) getActivity()).showProgress();
            call.enqueue(new retrofit2.Callback<WishListAddOrRemoveProductResponse>() {
                @Override
                public void onResponse(Call<WishListAddOrRemoveProductResponse> call, Response<WishListAddOrRemoveProductResponse> response) {
                    ((MainActivity) getActivity()).hideProgress();
                    if (response.body().getStatus().equalsIgnoreCase(Const.Actions.Status.SUCCESS)) {
//                        inWishList = true;
//                        btnAddToWishList.setText(getResources().getString(R.string.wishlist_remove).toUpperCase());
                        if (listSpecialOffers.contains(finalProduct)) {
                            listSpecialOffers.get(listSpecialOffers.indexOf(finalProduct)).setInWishList(true);
                        }
                        else if (listTopSellers.contains(finalProduct)) {
                            listTopSellers.get(listTopSellers.indexOf(finalProduct)).setInWishList(true);
                        }
                        ((GridWithHeaderAndFooterAdapter)recyclerView.getAdapter()).updateAll();
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

    public static class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int verticalSpaceHeight;

        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
//            outRect.bottom = verticalSpaceHeight;

            if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
                outRect.bottom = verticalSpaceHeight;
            }
        }
    }

    public static class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int horizontalSpaceHeight;

        public HorizontalSpaceItemDecoration(int horizontalSpaceHeight) {
            this.horizontalSpaceHeight = horizontalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1 &&
                    parent.getChildAdapterPosition(view) != 0) {


                if (parent.getChildAdapterPosition(view) % 2 != 0) {
                    outRect.left = horizontalSpaceHeight;
                    outRect.right = horizontalSpaceHeight;
                }
                else
                {
                    outRect.right = horizontalSpaceHeight;
                }
            }


//            if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
//                outRect.bottom = horizontalSpaceHeight;
//            }
        }
    }

    public class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private final int[] ATTRS = new int[]{android.R.attr.listDivider};

        private Drawable divider;

        /**
         * Default divider will be used
         */
        public DividerItemDecoration(Context context) {
            final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
            divider = styledAttributes.getDrawable(0);
            styledAttributes.recycle();
        }

        /**
         * Custom divider will be used
         */
        public DividerItemDecoration(Context context, int resId) {
            divider = ContextCompat.getDrawable(context, resId);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + divider.getIntrinsicHeight();

                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        }
    }
}
