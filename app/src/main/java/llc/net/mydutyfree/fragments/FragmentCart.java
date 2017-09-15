package llc.net.mydutyfree.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import java.util.List;

import llc.net.mydutyfree.base.CartSingleton;
import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.newmdf.MainActivity;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.newmdf.ShoppingCartAdapter;
import llc.net.mydutyfree.response.Product;
import llc.net.mydutyfree.utils.Const;
import llc.net.mydutyfree.utils.FontUtils;

/**
 * Created by gorf on 11/27/15.
 */
public class FragmentCart extends Fragment {

    public FragmentCart() {
    }

    private ListView mDrawerList;
    private List<Product> mArray;
    private ImageView image1, image2;
    private TextView txtNoItemsRed, txtNoItemsBlack;
    private LinearLayout llEmtyCart;
    private  float density;
    private Tracker mTracker;


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
        String projectToken = Const.MIXPANEL_PROJECT_TOKEN;
        MixpanelAPI mixpanel = MixpanelAPI.getInstance(getContext(), projectToken);
        mixpanel.track("Cart View");
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.screen_cart, container, false);
        density = getResources().getDisplayMetrics().density;
        setHasOptionsMenu(true);

        mTracker = ((MDFApplication)getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName("Cart");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        String title = getResources().getString(R.string.cart_caps);
        title = title.substring(0,1).toUpperCase() + title.substring(1).toLowerCase();

        ((MainActivity)getActivity()).setTitle(title);
        ((MainActivity)getActivity()).closeSearch();

        mDrawerList = (ListView) rootView.findViewById(R.id.listScreenCart);

        txtNoItemsRed = (TextView)rootView.findViewById(R.id.txtEmptyCartMessageRedScreenCart);
        txtNoItemsBlack = (TextView)rootView.findViewById(R.id.txtEmptyCartMessageBlackScreenCart);

        FontUtils.setBoldFont(getContext(), txtNoItemsRed);
        FontUtils.setNormalFont(getContext(), txtNoItemsBlack);

        image1 = (ImageView)rootView.findViewById(R.id.imageView1ScreenCart);
        image2 = (ImageView)rootView.findViewById(R.id.imageView2ScreenCart);
        llEmtyCart = (LinearLayout)rootView.findViewById(R.id.linearLayoutEmptyCartMessageScreenCart);

        mArray = CartSingleton.getInstance().getProductList();

        if (mArray.size() < 1) {
            mDrawerList.setVisibility(View.GONE);
//            image1.setVisibility(View.VISIBLE);
//            image2.setVisibility(View.VISIBLE);
            llEmtyCart.setVisibility(View.VISIBLE);
        }
        else
        {
            mDrawerList.setVisibility(View.VISIBLE);
//            image1.setVisibility(View.GONE);
//            image2.setVisibility(View.GONE);
            llEmtyCart.setVisibility(View.GONE);
        }

//        View footerView =  ((LayoutInflater)getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_view, null, false);
//        mDrawerList.addFooterView(footerView);
        mDrawerList.setAdapter(new ShoppingCartAdapter(getContext(), mArray, new ShoppingCartAdapter.ShoppingCartAdapterListener() {
            @Override
            public void onItemClicked(Product item) {

            }

            @Override
            public void onContinueShopingClicked() {
                String projectToken = Const.MIXPANEL_PROJECT_TOKEN;
                MixpanelAPI mixpanel = MixpanelAPI.getInstance(getContext(), projectToken);
                mixpanel.track("Cart Continue shopping");
                int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();
                if (count < 3) {
                    getActivity().onBackPressed();
                }
                else
                    getActivity().getSupportFragmentManager().popBackStack(count - 3, 0);
            }

            @Override
            public void onMakeOrderClicked() {
                Fragment fragment = null;
                if (((MDFApplication)getActivity().getApplication()).getIsAirport()) {
                    fragment = new FragmentCheckout();
                }
                else {
                    fragment = new FragmentCheckoutBorderShop();
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
            public void onAllItemsDeleted() {
                mDrawerList.setVisibility(View.GONE);
                llEmtyCart.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).invalidateOptionsMenu();
            }
        }));
        return rootView;
    }
}
