package llc.net.mydutyfree.newmdf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import llc.net.mydutyfree.base.CartSingleton;
import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.utils.Const;
import llc.net.mydutyfree.utils.FontUtils;

/**
 * Created by gorf on 3/4/16.
 */
public class ThankActivity extends Activity {

    TextView txtOrderNumber;
    TextView txtOrderHint;
    Button btnCheckMail;
    Button btnContinue;
    private Tracker mTracker;

    @SuppressLint("StringFormatInvalid")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank);
        txtOrderNumber = (TextView)findViewById(R.id.txtOrderNumberThankYouScreen);
        txtOrderHint = (TextView)findViewById(R.id.txtOrderHintThankYouScreen);
        btnCheckMail = (Button)findViewById(R.id.btnCheckMailThankYouScreen);
        btnContinue = (Button)findViewById(R.id.btnContinueThankYouScreen);

        FontUtils.setNormalFont(this, txtOrderNumber);
        FontUtils.setNormalFont(this, txtOrderHint);
        FontUtils.setBoldFont(this, btnCheckMail);
        FontUtils.setBoldFont(this, btnContinue);
        Intent intent = getIntent();
        int orderID = intent.getIntExtra("orderID", 0); //if it's a string you stored.
        double totalPrice = intent.getDoubleExtra("totalPrice", 0); //if it's a string you stored.
        String discountCode = intent.getStringExtra("discountCode");
        boolean mIsS7 = intent.getBooleanExtra("isS7", false);
        int mIsS7Miles = intent.getIntExtra("isS7miles", 0);
        int mIsS7Discount = intent.getIntExtra("isS7discount", 0);


        String projectToken = Const.MIXPANEL_PROJECT_TOKEN;
        MixpanelAPI mixpanel = MixpanelAPI.getInstance(this, projectToken);
        try {
            JSONObject properties = new JSONObject();
            properties.put("order_id", String.format("%d", orderID));
            properties.put("cost", String.format("%.2f", totalPrice));
            if (discountCode != null && !discountCode.isEmpty())
                properties.put("discount_code", discountCode);
            mixpanel.track("Checkout Successful", properties);
        } catch (JSONException e) {

        }

        txtOrderNumber.append(String.format(" %d", orderID));
        if (mIsS7) {
            txtOrderNumber.append(String.format(getResources().getString(R.string.s7_thank_black), mIsS7Miles ,mIsS7Discount));
        }

        mTracker = ((MDFApplication)getApplication()).getDefaultTracker();
        mTracker.setScreenName("Thank");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("ui_action")
                .setAction("Order sent")
                .setLabel(String.format("Order number: %d", orderID))
                .build());

//        if (isOnline() == false) {
//            MainActivity.this.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//        }
//        mProgressDialog = new MDFProgressDialog(this, false);
//        mProgressDialog.setCanceledOnTouchOutside(false);
//        mProgressDialog.setCancelable(false);
//        mProgressDialogLinkCount = 0;

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCheckMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto", "", null));
                        startActivity(Intent.createChooser(emailIntent, "Check email..."));
            }
        });
    }
}