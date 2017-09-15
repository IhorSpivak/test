package llc.net.mydutyfree.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import llc.net.mydutyfree.newmdf.R;

/**
 * Created by gorf on 11/27/15.
 */
public class FragmentFour extends Fragment {

    private ListView mDrawerList;
    private String[] mArray;

    public  FragmentFour() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.screen_four, container, false);
        mDrawerList = (ListView) rootView.findViewById(R.id.listList);

        mArray = getResources().getStringArray(R.array.screen_array);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this.getContext(),
                R.layout.cart_cell, R.id.txtProductCartCell, mArray));
        View footerView =  ((LayoutInflater)getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE)).inflate(R.layout.cart_footer, null, false);
        mDrawerList.addFooterView(footerView);
        return rootView;
    }
}
