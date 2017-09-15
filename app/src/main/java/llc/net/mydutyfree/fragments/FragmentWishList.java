package llc.net.mydutyfree.fragments;

/**
 * Created by gorf on 1/12/17.
 */

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import llc.net.mydutyfree.adapters.GridWishListAdapter;
import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.base.RetrofitService;
import llc.net.mydutyfree.interfaces.GridWishListAdapterInterface;
import llc.net.mydutyfree.interfaces.PostInterfaceApi;
import llc.net.mydutyfree.newmdf.MainActivity;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.response.Product;
import llc.net.mydutyfree.response.WishListAddOrRemoveProductResponse;
import llc.net.mydutyfree.response.WishListGetAllResponse;
import llc.net.mydutyfree.utils.Const;
import llc.net.mydutyfree.utils.JsonCreator;
import llc.net.mydutyfree.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentWishList extends Fragment implements GridWishListAdapterInterface{

    private ListView mDrawerList;
    private String[] mArray;
    private RecyclerView recyclerView;
    private GridWishListAdapter adapter;
    private Tracker mTracker;


    public  FragmentWishList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.screen_recycler_view, container, false);

        mTracker = ((MDFApplication)getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName("Wishlist");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration((int) Utils.dpToPx(5)));
        recyclerView.addItemDecoration(new HorizontalSpaceItemDecoration((int)Utils.dpToPx(5)));

        String userAuthKey = ((MDFApplication)(getActivity().getApplication())).getUserAuthKey();

        if (userAuthKey.length() > 0) {
            RetrofitService retrofitService = new RetrofitService();
            PostInterfaceApi service = retrofitService.create();
            Call<WishListGetAllResponse> call = service.wishListGetAll(JsonCreator.WishList.GetAll());
            ((MainActivity)getActivity()).showProgress();
            call.enqueue(new Callback<WishListGetAllResponse>() {
                @Override
                public void onResponse(Call<WishListGetAllResponse> call, Response<WishListGetAllResponse> response) {
                    ((MainActivity) getActivity()).hideProgress();
                    if (response.body().getStatus().equalsIgnoreCase(Const.Actions.Status.SUCCESS)) {
                        connectAdapter(response);
                    }
                    else {
                    }
                }
                @Override
                public void onFailure(Call<WishListGetAllResponse> call, Throwable t) {
                    Log.e("!!!", "error!!!");
                    ((MainActivity)getActivity()).hideProgress();
                }
            });

        }

        return rootView;
    }

    private void connectAdapter(Response<WishListGetAllResponse> response) {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(manager);
        adapter = new GridWishListAdapter(response.body().getPresentProducts(), response.body().getAbsentProducts(), this);
        recyclerView.setAdapter(adapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.isHeader(position) ? manager.getSpanCount() : 1;
            }
        });
    }

    @Override
    public void onItemCLicked(Product product) {
        Fragment fragment = new FragmentItem();
        ((FragmentItem)fragment).setProductID(product.getID());
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
    public void onItemFavoriteButtonCLicked(Product product) {
//        Toast.makeText(getContext(), product.getID()+" i WISH", Toast.LENGTH_SHORT).show();
        RetrofitService retrofitService = new RetrofitService();
        PostInterfaceApi service = retrofitService.create();
        Call<WishListAddOrRemoveProductResponse> call = service.wishListRemoveProduct(JsonCreator.WishList.RemoveProductWithID(product.getID()));
        ((MainActivity)getActivity()).showProgress();
        call.enqueue(new Callback<WishListAddOrRemoveProductResponse>() {
            @Override
            public void onResponse(Call<WishListAddOrRemoveProductResponse> call, Response<WishListAddOrRemoveProductResponse> response) {
//                Log.e("!!!", "!!!");
                ((MainActivity) getActivity()).hideProgress();
                if (response.body().getStatus().equalsIgnoreCase(Const.Actions.Status.SUCCESS)) {
//                    connectAdapter(response);
                    adapter.updateAll();
                }
                else {
//                        Toast.makeText(getActivity(), "Error: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    Log.e("!!!", "!!!");
                }
            }
            @Override
            public void onFailure(Call<WishListAddOrRemoveProductResponse> call, Throwable t) {
//                Log.e("!!!", "error!!!");
                ((MainActivity)getActivity()).hideProgress();
            }
        });
    }

    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int verticalSpaceHeight;

        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) != 0) {
                outRect.bottom = verticalSpaceHeight;
            }
        }
    }

    public class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int horizontalSpaceHeight;
        public HorizontalSpaceItemDecoration(int horizontalSpaceHeight) {
            this.horizontalSpaceHeight = horizontalSpaceHeight;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) != 0 &&
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
        }
    }
}
