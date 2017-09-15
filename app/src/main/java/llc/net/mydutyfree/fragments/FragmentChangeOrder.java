package llc.net.mydutyfree.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
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
public class FragmentChangeOrder extends Fragment {
    String mContentType;
    TextView txtTitle, txtText, txtPhone, txtEmail;
    Button btnPhone, btnEmail;
    private Tracker mTracker;

    public FragmentChangeOrder() {
    }

    public void setContentType(String type) {
        mContentType = type;
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
        View rootView = inflater.inflate(R.layout.screen_change_order, container, false);

        ((MainActivity)getActivity()).setTitle(getResources().getString(R.string.change_order));
        float buttonTextSize = 0;
        float density = getContext().getResources().getDisplayMetrics().density;
        Log.e("!DENSITY!", "DENSITY = " + density);

        if (density >= 4.0) {
            //xxxhdpi
            Log.e("!DENSITY!", "xxxhdpi");
        } else if ((density >= 3.0) && (density < 4.0)) {
            //xxhdpi
            Log.e("!DENSITY!", "xxhdpi");
        } else if ((density >= 2.0) && (density < 3.0)) {
            //xhdpi
            Log.e("!DENSITY!", "xhdpi");
        } else if ((density >= 1.5) && (density < 2.0)) {
            //hdpi
            Log.e("!DENSITY!", "hdpi");
        } else if ((density >= 1.0) && (density < 1.5)) {
            //mdpi
            Log.e("!DENSITY!", "mdpi");
        } else if (density < 1.0) {
            //ldpi
            Log.e("!DENSITY!", "ldpi");
        }

        if (density <= 1.5f)
            buttonTextSize = 13;
        else if ((density > 1.5f) && (density < 3.0f))
            buttonTextSize = 13;
        else if (density >= 3.0f)
            buttonTextSize = 12;

        txtEmail = (TextView) rootView.findViewById(R.id.txtOrEmailScreenChangeOrder);
        txtPhone = (TextView) rootView.findViewById(R.id.txtPhoneNumberScreenChangeOrder);
        txtTitle = (TextView) rootView.findViewById(R.id.txtTitleScreenChangeOrder);
        txtText = (TextView) rootView.findViewById(R.id.txtTextScreenChangeOrder);

        btnEmail = (Button) rootView.findViewById(R.id.btnEmailScreenChangeOrder);
        btnPhone = (Button) rootView.findViewById(R.id.btnPhoneScreenChangeOrder);

        FontUtils.setNormalFont(getContext(), txtEmail);
        FontUtils.setNormalFont(getContext(), txtPhone);
        FontUtils.setBoldFont(getContext(), txtTitle);
        FontUtils.setNormalFont(getContext(), txtText);

        FontUtils.setBoldFont(getContext(), btnPhone);
        FontUtils.setBoldFont(getContext(), btnEmail);

        btnEmail.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
        btnPhone.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
        txtEmail.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
        txtPhone.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + getResources().getString(R.string.phone_number_digits_trimmed)));
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                getActivity().startActivity(intent);
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",getResources().getString(R.string.email_address), null));
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        setHasOptionsMenu(true);
        mTracker = ((MDFApplication)getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName("Customer Suppor Change");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        return rootView;
    }

}
