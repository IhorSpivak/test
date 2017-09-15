package llc.net.mydutyfree.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import llc.net.mydutyfree.adapters.StoreListSettingsAdapter;
import llc.net.mydutyfree.base.CartSingleton;
import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.base.RetrofitService;
import llc.net.mydutyfree.interfaces.PostInterfaceApi;
import llc.net.mydutyfree.newmdf.MainActivity;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.response.GetLanguagesResponse;
import llc.net.mydutyfree.response.GetStores;
import llc.net.mydutyfree.utils.JsonCreator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gorf on 04/28/17.
 */
public class FragmentSelectAirport extends Fragment {

    public FragmentSelectAirport() {
    }

    private ListView mDrawerList;
    private List<String> mStoreNamesAir;
    private List<String> mStoreCodesAir;
    private List<String> mStoreNamesBorder;
    private List<String> mStoreCodesBorder;
    private GetStores responseAirports;
    private GetStores responseBorderShops;
    private float density;
    private Tracker mTracker;
    private String curLanguage;
    ArrayList<String> mLanguagesCodesList;
    ArrayList<String> mLanguagesNamesList;
    Map<String, String> mLanguagesMap;
    private String mSelectedStore;


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
//        String projectToken = Const.MIXPANEL_PROJECT_TOKEN;
//        MixpanelAPI mixpanel = MixpanelAPI.getInstance(getContext(), projectToken);
//        mixpanel.track("Cart View");
        super.onResume();
    }

    public void setLists(List<String> storeNamesAir, List<String> storeCodesAir, List<String> storeNamesBorder, List<String> storeCodesBorder) {
        mStoreCodesAir = storeCodesAir;
        mStoreCodesBorder = storeCodesBorder;
        mStoreNamesAir = storeNamesAir;
        mStoreNamesBorder = storeNamesBorder;
    }

    public void setResponses (GetStores airports, GetStores bordershops) {
        responseAirports = airports;
        responseBorderShops = bordershops;
    }

    private void updateLists() {
        final String curLang = ((MDFApplication) getActivity().getApplication()).getCurrentLanguage();
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
                responseAirports = response.body();
//                ((MDFApplication) getActivity().getApplication()).setStores(response.body());

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
                    if (namesStore.get(curLang) != null) {
                        mStoreNamesAir.add(namesStore.get(curLang));
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
                        responseBorderShops = response.body();
//                        ((MDFApplication) getActivity().getApplication()).setStores(response.body());

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
                            if (namesStore.get(curLang) != null) {
                                mStoreNamesBorder.add(namesStore.get(curLang));
                                mStoreCodesBorder.add(entry.getKey());
                                Log.e("!!!", "!!!");
                            } else {
                                Log.e("!!!", "!!!");
                            }
                        }
////////////
                        mDrawerList.setAdapter(new StoreListSettingsAdapter(getContext(), mStoreNamesAir, mStoreNamesBorder));
                        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(getContext(), String.format("%d", position), Toast.LENGTH_SHORT).show();
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

            @Override
            public void onFailure(Call<GetStores> call, Throwable t) {
                Log.e("!!!", "error!!!");
                ((MainActivity)getActivity()).hideProgress();
            }
        });
    }

    private void reloadApp() {
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

                if (!mLanguagesCodesList.contains(curLanguage)) {
                    curLanguage = "ru";
                    ((MDFApplication) getActivity().getApplication()).setCurrentLanguage(curLanguage);
                    Locale locale2 = new Locale(curLanguage);
                    Locale.setDefault(locale2);
                    Configuration config2 = new Configuration();
                    config2.locale = locale2;
                    getActivity().getResources().updateConfiguration(config2, null);
                }

                ((MainActivity)getActivity()).hideProgress();
                CartSingleton.getInstance().ClearCart();
                //TODO:fix
                getActivity().getSupportFragmentManager().popBackStack("main", 0);
            }
            @Override
            public void onFailure(Call<GetLanguagesResponse> call, Throwable t) {
                Log.e("!!!", "error!!!");
                ((MainActivity)getActivity()).hideProgress();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.screen_list, container, false);
        density = getResources().getDisplayMetrics().density;
        setHasOptionsMenu(true);

        mTracker = ((MDFApplication)getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName("Select airport");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        curLanguage = ((MDFApplication) getActivity().getApplication()).getCurrentLanguage();
        String title = getResources().getString(R.string.select_airport);
        title = title.substring(0,1).toUpperCase() + title.substring(1).toLowerCase();

        ((MainActivity)getActivity()).setTitle(title);
        ((MainActivity)getActivity()).closeSearch();

        mDrawerList = (ListView) rootView.findViewById(R.id.listListView);

        if (mStoreNamesAir.size() < 1)
            updateLists();
        else {
            mDrawerList.setAdapter(new StoreListSettingsAdapter(getContext(), mStoreNamesAir, mStoreNamesBorder));
            mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Toast.makeText(getContext(), String.format("%d", position), Toast.LENGTH_SHORT).show();
                    if (mStoreNamesAir.size() > 0 && mStoreNamesBorder.size() > 0) {
                        if (position == 0) {
                        } else if (position > 0 && position < mStoreNamesAir.size() + 1) {
                            mSelectedStore = mStoreCodesAir.get(position - 1);
                            ((MDFApplication) getActivity().getApplication()).setAirport(mSelectedStore);
                            ((MDFApplication) getActivity().getApplication()).setIsAirport(true);
                            ((MDFApplication) getActivity().getApplication()).setStores(responseAirports);

                            reloadApp();
                        } else if (position == mStoreNamesAir.size() + 1) {
                        } else if (position > mStoreNamesAir.size() + 1) {
                            mSelectedStore = mStoreCodesBorder.get(position - 2 - mStoreCodesAir.size());
                            ((MDFApplication) getActivity().getApplication()).setAirport(mSelectedStore);
                            ((MDFApplication) getActivity().getApplication()).setIsAirport(false);
                            ((MDFApplication) getActivity().getApplication()).setStores(responseBorderShops);

                            reloadApp();
                        }
                    }
                    ///!!!!!!!!!
                }
            });
        }

//        FontUtils.setBoldFont(getContext(), txtNoItemsRed);
//        FontUtils.setNormalFont(getContext(), txtNoItemsBlack);

//        image1 = (ImageView)rootView.findViewById(R.id.imageView1ScreenCart);
//        image2 = (ImageView)rootView.findViewById(R.id.imageView2ScreenCart);
//        llEmtyCart = (LinearLayout)rootView.findViewById(R.id.linearLayoutEmptyCartMessageScreenCart);
//
//        mArray = CartSingleton.getInstance().getProductList();
//
//        if (mArray.size() < 1) {
//            mDrawerList.setVisibility(View.GONE);
////            image1.setVisibility(View.VISIBLE);
////            image2.setVisibility(View.VISIBLE);
//            llEmtyCart.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            mDrawerList.setVisibility(View.VISIBLE);
////            image1.setVisibility(View.GONE);
////            image2.setVisibility(View.GONE);
//            llEmtyCart.setVisibility(View.GONE);
//        }
//
////        View footerView =  ((LayoutInflater)getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_view, null, false);
////        mDrawerList.addFooterView(footerView);
//        mDrawerList.setAdapter(new ShoppingCartAdapter(getContext(), mArray, new ShoppingCartAdapter.ShoppingCartAdapterListener() {
//            @Override
//            public void onItemClicked(Product item) {
//
//            }
//
//            @Override
//            public void onContinueShopingClicked() {
//                String projectToken = Const.MIXPANEL_PROJECT_TOKEN;
//                MixpanelAPI mixpanel = MixpanelAPI.getInstance(getContext(), projectToken);
//                mixpanel.track("Cart Continue shopping");
//                int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();
//                if (count < 3) {
//                    getActivity().onBackPressed();
//                }
//                else
//                    getActivity().getSupportFragmentManager().popBackStack(count - 3, 0);
//            }
//
//            @Override
//            public void onMakeOrderClicked() {
//                Fragment fragment = new FragmentCheckout();
//                if (fragment != null) {
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction transaction = fragmentManager.beginTransaction();
//                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
//                    transaction.replace(R.id.content_frame, fragment);
//                    transaction.addToBackStack(null);
//                    transaction.commit();
//                }
//            }
//
//            @Override
//            public void onAllItemsDeleted() {
//                mDrawerList.setVisibility(View.GONE);
//                llEmtyCart.setVisibility(View.VISIBLE);
//                ((MainActivity)getActivity()).invalidateOptionsMenu();
//            }
//        }));
        return rootView;
    }
}
