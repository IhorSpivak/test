package llc.net.mydutyfree.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import llc.net.mydutyfree.newmdf.ImageAdapter;
import llc.net.mydutyfree.newmdf.R;

/**
 * Created by gorf on 11/27/15.
 */
public class FragmentFive extends Fragment {
    
    public  FragmentFive() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.screen_five, container, false);

        return rootView;
    }
}
