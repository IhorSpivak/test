package llc.net.mydutyfree.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.interfaces.BannersAdapterInterface;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.response.Banner;

/**
 * Created by gorf on 7/27/16.
 */
public class BannersAdapter extends PagerAdapter {
    List<Banner> pages = null;
    BannersAdapterInterface bannersAdapterInterface;
//        boolean preload = true;

    public BannersAdapter(List<Banner> pages, BannersAdapterInterface bannersAdapterInterface){
        this.pages = pages;
        this.bannersAdapterInterface = bannersAdapterInterface;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        LayoutInflater inflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View page = inflater.inflate(R.layout.view_pager_page_banner, container, false);
        final ImageView image = (ImageView)page.findViewById(R.id.imageViewBannerPage);
        image.setTag(position);
        Picasso
                .with(MDFApplication.getAppContext())
                .load(pages.get(position).getImage())
                .into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("!!!Tah",String.format("succ %d", (int)image.getTag()));
                    }

                    @Override
                    public void onError() {
//                            Log.e("!!!Tah",String.format("nosucc %d", pos));
                    }
                });
        final String linkID = pages.get(position).getLinkID();
        final int pos = position;
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bannersAdapterInterface.onBannerClicked(pages.get(pos));
////                    Toast.makeText(getContext(), linkID, Toast.LENGTH_SHORT).show();
//                Fragment fragment = new FragmentItem();
//                ((FragmentItem) fragment).setProductID(linkID);
//                if (fragment != null) {
//                    FragmentManager fragmentManager = ((MainActivity)this).getSupportFragmentManager();
//                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
//                    transaction.replace(R.id.content_frame, fragment);
//                    transaction.addToBackStack(null);
//                    transaction.commit();
//                }
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
