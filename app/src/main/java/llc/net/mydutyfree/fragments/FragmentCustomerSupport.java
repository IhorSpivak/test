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
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.newmdf.MainActivity;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.utils.FontUtils;

/**
 * Created by gorf on 2/17/16.
 */
public class FragmentCustomerSupport  extends Fragment {

    Button btnFAQ;
    Button btnTechnicalProblems;
    Button btnCancelingOrder;
    Button btnChangeOrder;
    TextView txtTitle;
    private Tracker mTracker;

    public FragmentCustomerSupport() {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.screen_customer_support, container, false);
        setHasOptionsMenu(true);
        mTracker = ((MDFApplication)getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName("Customer Support Main");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        ((MainActivity)getActivity()).setTitle(getResources().getString(R.string.customer_support));
        btnFAQ = (Button)rootView.findViewById(R.id.btnFAQScreenCustomerSupport);
        btnTechnicalProblems = (Button)rootView.findViewById(R.id.btnTechnicalProblemsScreenCustomerSupport);
        btnCancelingOrder = (Button)rootView.findViewById(R.id.btnCancelingOrderScreenCustomerSupport);
        btnChangeOrder = (Button)rootView.findViewById(R.id.btnChangeOrderScreenCustomerSupport);
        txtTitle = (TextView)rootView.findViewById(R.id.txtTitleScreenCustomerSupport);

        FontUtils.setBoldFont(getContext(), txtTitle);
        FontUtils.setNormalFont(getContext(), btnCancelingOrder);
        FontUtils.setNormalFont(getContext(), btnChangeOrder);
        FontUtils.setNormalFont(getContext(), btnFAQ);
        FontUtils.setNormalFont(getContext(), btnTechnicalProblems);


        btnTechnicalProblems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentTechnicalProblems();
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

        btnChangeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentChangeOrder();
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


        btnCancelingOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentCancelOrder();
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


        btnFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentWebView();
                ((FragmentWebView)fragment).setContentType("support");
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
}
