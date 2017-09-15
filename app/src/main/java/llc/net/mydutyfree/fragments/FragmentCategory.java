package llc.net.mydutyfree.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import llc.net.mydutyfree.adapters.CategoryAdapter;
import llc.net.mydutyfree.adapters.GridWithHeaderAndFooterAdapter;
import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.base.RetrofitService;
import llc.net.mydutyfree.interfaces.CategoryAdapterInterface;
import llc.net.mydutyfree.interfaces.PostInterfaceApi;
import llc.net.mydutyfree.models.BrandItem;
import llc.net.mydutyfree.newmdf.FilterAdapter;
import llc.net.mydutyfree.newmdf.MainActivity;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.response.Category;
import llc.net.mydutyfree.response.GetBrandsResponse;
import llc.net.mydutyfree.response.GetCategoriesResponse;
import llc.net.mydutyfree.response.GetProductsResponse;
import llc.net.mydutyfree.response.Product;
import llc.net.mydutyfree.response.WishListAddOrRemoveProductResponse;
import llc.net.mydutyfree.utils.Const;
import llc.net.mydutyfree.utils.FontUtils;
import llc.net.mydutyfree.utils.JsonCreator;
import llc.net.mydutyfree.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gorf on 2/11/16.
 */
public class FragmentCategory extends Fragment implements CategoryAdapterInterface {
    public enum ExtraCategoryType {
        TOP_SELLERS,
        SPECIAL_OFFERS
    }
    String userAuthKey;
    View rootView;
    RecyclerView recyclerView;
    List<Product> mList;
    Category mCategory;
    String mCategoryID;
    String mLimit;
    String mPriceTo;
    String mPriceFrom;
    String mCurrency;
    String mBrandFilter;
    ArrayList<BrandItem> mBrandList;
    ExtraCategoryType mExtraCategoryType;
    FilterAdapter.SortBy mSortBy;
    String mSearchString;
    TextView txtNoProducts;
    LinearLayout llNoProductsMessage;
    private Boolean mIsSearch;
    private Tracker mTracker;

    public FragmentCategory() {
        mIsSearch = false;
        mCategory = null;
        mCategoryID = null;
        mExtraCategoryType = null;
        mLimit = null;
        mPriceTo = null;
        mPriceFrom = null;
        mCurrency = null;
        mBrandFilter = null;
        mBrandList = null;
        mSortBy = null;
        mBrandFilter = null;
    }

    public void setCategory(Category category) {
        mCategory = category;
    }

    public void setCategoryID(String categoryID) {
        mCategoryID = categoryID;
        if (categoryID.equalsIgnoreCase("top")) {
            mExtraCategoryType = FragmentCategory.ExtraCategoryType.TOP_SELLERS;
        }
        else if (categoryID.equalsIgnoreCase("sale")) {
            mExtraCategoryType = ExtraCategoryType.SPECIAL_OFFERS;
        }
//        else if (categoryID.equalsIgnoreCase("new")) {
//            mExtraCategoryType = ExtraCategoryType.NEW_ARRIVALS;
//        }
    }

    public void setExtraCategoryType(ExtraCategoryType extraCategoryType) {
        mExtraCategoryType = extraCategoryType;
        if (extraCategoryType == ExtraCategoryType.TOP_SELLERS) {
            mCategoryID = "top";
        }
        else if (extraCategoryType == ExtraCategoryType.SPECIAL_OFFERS) {
            mCategoryID = "sale";
        }
//        else if (extraCategoryType == ExtraCategoryType.NEW_ARRIVALS) {
//            mCategoryID = "new";
//        }
    }
    public ExtraCategoryType getExtraCategoryType() {
        return mExtraCategoryType;
    }

    public void setSortBy(FilterAdapter.SortBy sortBy) {
        mSortBy = sortBy;
    }

    public void setCurrency(String currency) {
        mCurrency = currency;
    }
//    public void setBrands(ArrayList<String> brands) {
////        mBrandFilter = brand;
//    }

    public void setBrand(String brand) {
        mIsSearch = true;
        mBrandFilter = brand;
    }
    public boolean isSearch() {
        return mIsSearch;
    }

    public void setPriceTo(String priceTo) {
        mPriceTo = priceTo;
    }

    public void setPriceFrom(String priceFrom) {
        mPriceFrom = priceFrom;
    }

    public void setLimit(String limit) {
        mLimit = limit;
    }

    public void setSearchString(String searchString) {
        mIsSearch = true;
        mSearchString = searchString;
    }

    public ArrayList<BrandItem> getBrandsList() {
        return mBrandList;
    }

    public Category getCategory() {
        return mCategory;
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

    private void updateCategory() {
        RetrofitService retrofitService = new RetrofitService();
        PostInterfaceApi service = retrofitService.create();
        Call<GetCategoriesResponse> callGetCategories = service.getCategories(JsonCreator.GetCategories());
        ((MainActivity) getActivity()).showProgress();
        callGetCategories.enqueue(new Callback<GetCategoriesResponse>() {
            @Override
            public void onResponse(Call<GetCategoriesResponse> call, Response<GetCategoriesResponse> response) {
                ((MainActivity) getActivity()).hideProgress();
                List<Category> catList = response.body().getData();
                List<Category> longCatList = new ArrayList<Category>();
                outerLoop:
                for (Iterator<Category> i = catList.iterator(); i.hasNext(); ) {
                    Category item = i.next();
                    if (item.isAvailable()) {
//                                longCatList.add(item);
                        if (item.getID().equalsIgnoreCase(mCategoryID)) {
                            mCategory = item;
                            break outerLoop;
                        }
                        if (item.getChildren().size() > 0) {
                            for (Iterator<Category> j = item.getChildren().iterator(); j.hasNext(); ) {
                                Category subItem = j.next();
                                if (subItem.isAvailable()) {
//                                            longCatList.add(subItem);
                                    if (subItem.getID().equalsIgnoreCase(mCategoryID)) {
                                        mCategory = subItem;
                                        break outerLoop;
                                    }

                                }
                            }
                        }
                    }
                }

                if (mCategory != null) {
                    updateBrandsList();
                    updateProductsList();
                }
            }

            @Override
            public void onFailure(Call<GetCategoriesResponse> call, Throwable t) {
                ((MainActivity) getActivity()).hideProgress();
            }
        });
    }

    private void updateProductsList() {
        String nameForAnalytics = null;
        String categoryID = null;
        if (mExtraCategoryType != null) {
//            if (mExtraCategoryType == ExtraCategoryType.NEW_ARRIVALS) {
//                ((MainActivity)getActivity()).setTitle(getResources().getString(R.string.new_arrivals));
//                nameForAnalytics = getResources().getString(R.string.new_arrivals);
//            }
//            else
            if (mExtraCategoryType == ExtraCategoryType.TOP_SELLERS) {
                ((MainActivity)getActivity()).setTitle(getResources().getString(R.string.top_sellers));
                nameForAnalytics = getResources().getString(R.string.top_sellers);
            }
            else if (mExtraCategoryType == ExtraCategoryType.SPECIAL_OFFERS) {
                ((MainActivity)getActivity()).setTitle(getResources().getString(R.string.special_offers));
                nameForAnalytics = getResources().getString(R.string.special_offers);
            }
        }
        if (mCategory != null) {
            ((MainActivity)getActivity()).setTitle(mCategory.getName());
            nameForAnalytics = mCategory.getName();
            categoryID = mCategory.getID();
        }

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("ui_action")
                .setAction("Select Category")
                .setLabel(nameForAnalytics)
                .build());
        RetrofitService retrofitService = new RetrofitService(true);
        PostInterfaceApi service = retrofitService.create();
        Call<GetProductsResponse> callGetFiltered = service.getProducts(JsonCreator.GetFilteredProducts(
                categoryID,
                mBrandFilter,
                mPriceFrom,
                mPriceTo,
                mSortBy,
                mExtraCategoryType,
                mLimit
        ));
        ((MainActivity)getActivity()).showProgress();
        callGetFiltered.enqueue(new Callback<GetProductsResponse>() {
            @Override
            public void onResponse(Call<GetProductsResponse> call, Response<GetProductsResponse> response) {
                ((MainActivity)getActivity()).hideProgress();
                Log.e("", "");
                mList = response.body().getData();
                checkProductsCount();
                runAll();
            }

            @Override
            public void onFailure(Call<GetProductsResponse> call, Throwable t) {
                ((MainActivity)getActivity()).hideProgress();
                Log.e("", "");
            }
        });
    }

    private void updateBrandsList() {
        RetrofitService retrofitService = new RetrofitService();
        PostInterfaceApi service = retrofitService.create();
        String categoryIDString = null;
        if (mExtraCategoryType != null)
            categoryIDString = mCategoryID;
        else
            categoryIDString = mCategory.getID();
        Call<GetBrandsResponse> callGetBrands = service.getBrands(JsonCreator.GetBrandsForCategory(categoryIDString));
        ((MainActivity)getActivity()).showProgress();
        callGetBrands.enqueue(new Callback<GetBrandsResponse>() {
            @Override
            public void onResponse(Call<GetBrandsResponse> call, Response<GetBrandsResponse> response) {
                ((MainActivity)getActivity()).hideProgress();
                Map<String, String> map = response.body().getData();
                if (map.size() > 0) {
                    mBrandList = sortMapToList(map);
                }
                else
                    mIsSearch = true;
            }

            @Override
            public void onFailure(Call<GetBrandsResponse> call, Throwable t) {
                ((MainActivity)getActivity()).hideProgress();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("FragmentCategory LOG", "onCreateView");
        setHasOptionsMenu(true);
        userAuthKey = ((MDFApplication)getActivity().getApplication()).getUserAuthKey();
        mTracker = ((MDFApplication)getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName("Category");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        rootView = inflater.inflate(R.layout.screen_recycler_view_category, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recuclerView);
        recyclerView.setHasFixedSize(true);
        //add ItemDecoration
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration((int) Utils.dpToPx(5)));
        recyclerView.addItemDecoration(new HorizontalSpaceItemDecoration((int)Utils.dpToPx(5)));

        if (mSearchString != null) {
            RetrofitService retrofitService = new RetrofitService();
            PostInterfaceApi service = retrofitService.create();
            Call<GetProductsResponse> callSearch = service.getProducts(JsonCreator.SearchWithKeyword(mSearchString));
            ((MainActivity)getActivity()).showProgress();
            callSearch.enqueue(new Callback<GetProductsResponse>() {
                @Override
                public void onResponse(Call<GetProductsResponse> call, Response<GetProductsResponse> response) {
                    ((MainActivity)getActivity()).hideProgress();
                    mList = response.body().getData();
                    checkProductsCount();
                    ((MainActivity)getActivity()).invalidateOptionsMenu();
//                    gridView.setAdapter(new NewImageAdapter(getActivity(), mList, userAuthKey));
                    runAll();
                }

                @Override
                public void onFailure(Call<GetProductsResponse> call, Throwable t) {
                    ((MainActivity)getActivity()).hideProgress();
                }
            });

        mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("ui_action")
                    .setAction("Search button click")
                    .setLabel(String.format("Keyword: '%s'", mSearchString))
                    .build());
            String search = getResources().getString(R.string.search_caps);
            search = search.substring(0,1).toUpperCase() + search.substring(1).toLowerCase();
            ((MainActivity)getActivity()).setTitle(search + " \"" + mSearchString + "\"");
        }
        else {
            if (mCategory == null && mExtraCategoryType == null && (mCategoryID != null)) {
                updateCategory();
            }
            else {
                updateBrandsList();
                updateProductsList();
            }
        }

        txtNoProducts = (TextView)rootView.findViewById(R.id.txtEmptyCategoryMessageScreenCategory);
        FontUtils.setNormalFont(getContext(), txtNoProducts);
        llNoProductsMessage = (LinearLayout)rootView.findViewById(R.id.linearLayoutEmptyCategoryMessageScreenCategory);
//        gridView = (GridView) rootView.findViewById(R.id.gridViewScreenCategory);
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                String prodID = new String();
//                prodID = mList.get(position).getID();
//
////                ((MDFApplication) getActivity().getApplication()).setString(Product.PRODUCT_ID, prodID);
//
//                Fragment fragment = new FragmentItem();
//                ((FragmentItem)fragment).setProductID(prodID);
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

        return rootView;
    }

    private void runAll() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(manager);
        String userAuthKey = ((MDFApplication)getActivity().getApplication()).getUserAuthKey();
        final CategoryAdapter adapter = new CategoryAdapter(mList, this, userAuthKey);
        recyclerView.setAdapter(adapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
    }


    private void checkProductsCount() {
        if (mList.size() < 1) {
            recyclerView.setVisibility(View.GONE);
            llNoProductsMessage.setVisibility(View.VISIBLE);
        }
        else
        {
            recyclerView.setVisibility(View.VISIBLE);
            llNoProductsMessage.setVisibility(View.GONE);
        }
    }

    private static Map<String, String> sortMapToMap(Map<String, String> unsortMap) {

        // Convert Map to List
        List<Map.Entry<String, String>> list =
                new LinkedList<Map.Entry<String, String>>(unsortMap.entrySet());

        // Sort list with comparator, to compare the Map values
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // Convert sorted map back to a Map
        Map<String, String> sortedMap = new LinkedHashMap<String, String>();
        for (Iterator<Map.Entry<String, String>> it = list.iterator(); it.hasNext();) {
            Map.Entry<String, String> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    private static ArrayList<BrandItem> sortMapToList(Map<String, String> unsortMap) {

        // Convert Map to List
        List<Map.Entry<String, String>> list =
                new LinkedList<Map.Entry<String, String>>(unsortMap.entrySet());

        // Sort list with comparator, to compare the Map values
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // Convert sorted map back to a Map
//        Map<String, String> sortedMap = new LinkedHashMap<String, String>();
        ArrayList<BrandItem> sortedList= new ArrayList<>();
        for (Iterator<Map.Entry<String, String>> it = list.iterator(); it.hasNext();) {
            Map.Entry<String, String> entry = it.next();
            sortedList.add(new BrandItem(entry.getKey(), entry.getValue()));
        }
        return sortedList;
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
                        if (mList.contains(finalProduct)) {
                            mList.get(mList.indexOf(finalProduct)).setInWishList(false);
                        }
                        ((CategoryAdapter)recyclerView.getAdapter()).updateAll();
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
                        if (mList.contains(finalProduct)) {
                            mList.get(mList.indexOf(finalProduct)).setInWishList(true);
                        }
                        ((CategoryAdapter)recyclerView.getAdapter()).updateAll();
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

            if (parent.getChildAdapterPosition(view) == 0 || parent.getChildAdapterPosition(view) == 1) {
                outRect.top = verticalSpaceHeight;
                outRect.bottom = verticalSpaceHeight;
            }
            else {
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
                if (parent.getChildAdapterPosition(view) % 2 != 0) {
                    outRect.right = horizontalSpaceHeight;
                }
                else
                {
                    outRect.left = horizontalSpaceHeight;
                    outRect.right = horizontalSpaceHeight;
                }
        }
    }
}
