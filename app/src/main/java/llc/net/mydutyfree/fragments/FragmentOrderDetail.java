package llc.net.mydutyfree.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DecimalFormat;
import java.util.List;

import llc.net.mydutyfree.adapters.OrdersHistoryAdapter;
import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.base.RetrofitService;
import llc.net.mydutyfree.interfaces.PostInterfaceApi;
import llc.net.mydutyfree.newmdf.MainActivity;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.response.GetOneOrderResponse;
import llc.net.mydutyfree.response.GetOrdersResponse;
import llc.net.mydutyfree.response.Order;
import llc.net.mydutyfree.response.Product;
import llc.net.mydutyfree.utils.Const;
import llc.net.mydutyfree.utils.JsonCreator;
import llc.net.mydutyfree.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gorf on 12/15/16.
 */
public class FragmentOrderDetail extends Fragment {

    private Order mOrder;
    private ImageView mImage;
    private EditText edtFlight, edtDate, edtTime;
    private TextInputLayout tilFlight, tilDate, tilTime;
    private LinearLayout llFlightInfo, llProductList;
    private Tracker mTracker;

    public FragmentOrderDetail() {

    }

    public void setOrder(Order order) {
        mOrder = order;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.screen_order_detail, container, false);
        llFlightInfo = (LinearLayout)rootView.findViewById(R.id.llFlightInformationScreenOrderDetail);
        llProductList = (LinearLayout)rootView.findViewById(R.id.llProductsListScreenOrderDetail);
        edtFlight = (EditText) rootView.findViewById(R.id.edtFlightScreenOrderDetail);
        tilFlight = (TextInputLayout) rootView.findViewById(R.id.tilFlightScreenOrderDetail);
        edtDate = (EditText) rootView.findViewById(R.id.edtDateScreenOrderDetail);
        edtTime = (EditText) rootView.findViewById(R.id.edtTimeScreenOrderDetail);

        mTracker = ((MDFApplication)getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName("Order detail");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        RetrofitService retrofitService = new RetrofitService(true);
        PostInterfaceApi service = retrofitService.create();
        Call<GetOneOrderResponse> call = service.ordersGetOne(JsonCreator.Orders.GetOne(mOrder.getID()));
        ((MainActivity)getActivity()).showProgress();
        call.enqueue(new Callback<GetOneOrderResponse>() {
            @Override
            public void onResponse(Call<GetOneOrderResponse> call, Response<GetOneOrderResponse> response) {
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
            public void onFailure(Call<GetOneOrderResponse> call, Throwable t) {
                Log.e("!!!", "error!!!");
                ((MainActivity)getActivity()).hideProgress();
            }
        });


//        mList.setAdapter(new OrdersListAdapter<List<OrderItem>>(this.getContext(), R.layout.order_item_cell, mArray));
        return rootView;
    }

    private void connectAdapter(Response<GetOneOrderResponse> response) {
        mOrder = response.body().getData();
        edtFlight.setText(mOrder.getFlightNumber());
        if (mOrder.getFlightNumber().equalsIgnoreCase("--")) {
            tilFlight.setVisibility(View.GONE);
        }
        else {
            tilFlight.setVisibility(View.VISIBLE);
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dt = formatter.parseDateTime(mOrder.getDepartureDate());
        edtDate.setText(dt.toString("yyyy-MM-dd"));
        edtTime.setText(dt.toString("HH:mm"));
        edtFlight.setEnabled(false);
        edtDate.setEnabled(false);
        edtTime.setEnabled(false);
        String currentCurrency = ((MDFApplication)(getActivity().getApplicationContext())).getCurrentCurrency();
        String currencySymbol = ((MDFApplication)(getActivity().getApplicationContext())).getCurrencySymbols().get(currentCurrency);
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        for (int i = 0; i < mOrder.getProducts().size(); i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins((int)Utils.dpToPx(10), 0, (int)Utils.dpToPx(10), (int)Utils.dpToPx(10));
            View v = getActivity().getLayoutInflater().inflate(R.layout.order_detail_product_item, null, false);
            TextView txtQuantity = (TextView) v.findViewById(R.id.txtQuantityCellProduct);
            TextView txtTitle = (TextView) v.findViewById(R.id.txtTitleCellProduct);
            TextView txtPrice = (TextView) v.findViewById(R.id.txtPriceCellProduct);
            ImageView imgImage = (ImageView) v.findViewById(R.id.imgImageCellProduct);
            Product item = mOrder.getProducts().get(i);

            txtQuantity.setText(item.getQuantity().toString());
            String priceString = decimalFormat.format(item.getPrice().get(currentCurrency));
            priceString = new StringBuilder().append(priceString).append(" ").append(currencySymbol).toString();
            txtPrice.setText(priceString);
            txtTitle.setText(item.getTitle());
            Picasso.with(getContext())
                    .load(Uri.encode(item.getImageUrl(), "@#&=*+-_.,:!?()/~'%"))
                    .placeholder(R.drawable.missing_product)
                    .error(R.drawable.missing_product)
                    .into(imgImage);
            llProductList.addView(v, lp);
        }
//        if (response.body().getData().size() > 0) {
//            llEmtyList.setVisibility(View.GONE);
//            mDrawerList.setVisibility(View.VISIBLE);
//            mDrawerList.setAdapter(new OrdersHistoryAdapter(getContext(), response.body().getData(), new OrdersHistoryAdapter.OrderHistoryAdapterListener() {
//                @Override
//                public void onItemClicked(Order item) {
//                    Toast.makeText(getContext(), item.getDepartureDate(), Toast.LENGTH_SHORT).show();
//                }
//            }));
//        }
//        else {
//            llEmtyList.setVisibility(View.VISIBLE);
//            mDrawerList.setVisibility(View.GONE);
//        }
    }

}