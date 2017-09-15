package llc.net.mydutyfree.fragments;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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

import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.base.RetrofitService;
import llc.net.mydutyfree.interfaces.PostInterfaceApi;
import llc.net.mydutyfree.models.BrandItem;
import llc.net.mydutyfree.newmdf.FilterAdapter;
import llc.net.mydutyfree.newmdf.MainActivity;
import llc.net.mydutyfree.newmdf.NewImageAdapter;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.response.Category;
import llc.net.mydutyfree.response.GetBrandsResponse;
import llc.net.mydutyfree.response.GetCategoriesResponse;
import llc.net.mydutyfree.response.GetProductsResponse;
import llc.net.mydutyfree.utils.FontUtils;
import llc.net.mydutyfree.utils.JsonCreator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import llc.net.mydutyfree.fragments.FragmentCategory.ExtraCategoryType;
/**
 * Created by gorf on 2/11/16.
 */
public class FragmentCategoryOld extends Fragment {
    String userAuthKey;
    View rootView;
    GridView gridView;
    List<llc.net.mydutyfree.response.Product> mList;
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

    public FragmentCategoryOld() {
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
            mExtraCategoryType = ExtraCategoryType.TOP_SELLERS;
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
                List<llc.net.mydutyfree.response.Category> catList = response.body().getData();
                List<llc.net.mydutyfree.response.Category> longCatList = new ArrayList<llc.net.mydutyfree.response.Category>();
                outerLoop:
                for (Iterator<llc.net.mydutyfree.response.Category> i = catList.iterator(); i.hasNext(); ) {
                    llc.net.mydutyfree.response.Category item = i.next();
                    if (item.isAvailable()) {
//                                longCatList.add(item);
                        if (item.getID().equalsIgnoreCase(mCategoryID)) {
                            mCategory = item;
                            break outerLoop;
                        }
                        if (item.getChildren().size() > 0) {
                            for (Iterator<llc.net.mydutyfree.response.Category> j = item.getChildren().iterator(); j.hasNext(); ) {
                                llc.net.mydutyfree.response.Category subItem = j.next();
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
        RetrofitService retrofitService = new RetrofitService();
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
                gridView.setAdapter(new NewImageAdapter(getActivity(), mList, userAuthKey));
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
                    gridView.setAdapter(new NewImageAdapter(getActivity(), mList, userAuthKey));
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
        rootView = inflater.inflate(R.layout.screen_category, container, false);
        txtNoProducts = (TextView)rootView.findViewById(R.id.txtEmptyCategoryMessageScreenCategory);
        FontUtils.setNormalFont(getContext(), txtNoProducts);
        llNoProductsMessage = (LinearLayout)rootView.findViewById(R.id.linearLayoutEmptyCategoryMessageScreenCategory);
        gridView = (GridView) rootView.findViewById(R.id.gridViewScreenCategory);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String prodID = new String();
                prodID = mList.get(position).getID();

//                ((MDFApplication) getActivity().getApplication()).setString(Product.PRODUCT_ID, prodID);

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
            }
        });

        return rootView;
    }

    private void checkProductsCount() {
        if (mList.size() < 1) {
            gridView.setVisibility(View.GONE);
            llNoProductsMessage.setVisibility(View.VISIBLE);
        }
        else
        {
            gridView.setVisibility(View.VISIBLE);
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
}
