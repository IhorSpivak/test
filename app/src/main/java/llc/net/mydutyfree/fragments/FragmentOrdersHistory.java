package llc.net.mydutyfree.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.List;

import llc.net.mydutyfree.adapters.OrdersHistoryAdapter;
import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.base.RetrofitService;
import llc.net.mydutyfree.interfaces.PostInterfaceApi;
import llc.net.mydutyfree.newmdf.MainActivity;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.response.GetOrdersResponse;
import llc.net.mydutyfree.response.Order;
import llc.net.mydutyfree.response.Product;
import llc.net.mydutyfree.response.WishListGetAllResponse;
import llc.net.mydutyfree.utils.Const;
import llc.net.mydutyfree.utils.JsonCreator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gorf on 12/15/16.
 */
public class FragmentOrdersHistory extends Fragment {

    private ListView mDrawerList;
    private List<Order> mArray;
    private ImageView image1, image2;
    private TextView txtEmptyList;
    private LinearLayout llEmtyList;
    private  float density;
    private Tracker mTracker;

    public FragmentOrdersHistory() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.screen_orders_history, container, false);
        mDrawerList = (ListView) rootView.findViewById(R.id.listScreenOrdersHistory);
        llEmtyList = (LinearLayout)rootView.findViewById(R.id.linearLayoutEmptyCartMessageScreenOrdersHistory);
        txtEmptyList = (TextView)rootView.findViewById(R.id.txtEmptyListScreenOrdersHistory);

        mTracker = ((MDFApplication)getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName("Orders history");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        RetrofitService retrofitService = new RetrofitService();
        PostInterfaceApi service = retrofitService.create();
        Call<GetOrdersResponse> call = service.ordersGetAll(JsonCreator.Orders.GetAll());
        ((MainActivity)getActivity()).showProgress();
        call.enqueue(new Callback<GetOrdersResponse>() {
            @Override
            public void onResponse(Call<GetOrdersResponse> call, Response<GetOrdersResponse> response) {
                Log.e("!!!", "!!!");
                ((MainActivity) getActivity()).hideProgress();
                if (response.body().getStatus().equalsIgnoreCase(Const.Actions.Status.SUCCESS)) {
                    connectAdapter(response);
                }
                else {
//                        Toast.makeText(getActivity(), "Error: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("!!!", "!!!");
                }
            }
            @Override
            public void onFailure(Call<GetOrdersResponse> call, Throwable t) {
                Log.e("!!!", "error!!!");
                ((MainActivity)getActivity()).hideProgress();
            }
        });


//        mList.setAdapter(new OrdersListAdapter<List<OrderItem>>(this.getContext(), R.layout.order_item_cell, mArray));
        return rootView;
    }

    private void connectAdapter(Response<GetOrdersResponse> response) {
        if (response.body().getData().size() > 0) {
            llEmtyList.setVisibility(View.GONE);
            mDrawerList.setVisibility(View.VISIBLE);
            mDrawerList.setAdapter(new OrdersHistoryAdapter(getContext(), response.body().getData(), new OrdersHistoryAdapter.OrderHistoryAdapterListener() {
                @Override
                public void onItemClicked(Order item) {
//                    Toast.makeText(getContext(), item.getDepartureDate(), Toast.LENGTH_SHORT).show();
                    Fragment fragment = new FragmentOrderDetail();
                    ((FragmentOrderDetail)fragment).setOrder(item);
                    if (fragment != null) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                        transaction.replace(R.id.content_frame, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            }));
        }
        else {
            llEmtyList.setVisibility(View.VISIBLE);
            mDrawerList.setVisibility(View.GONE);
        }
    }

}