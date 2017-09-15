package llc.net.mydutyfree.newmdf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.utils.Const;
import llc.net.mydutyfree.utils.FontUtils;

public class NewThankActivity extends Activity {

    TextView
            txtOrderNumber,
            txtDate,
            txtAmount,
            txtBonus,
            txtGreen,
            txtBlack,
            txtRed,
            txtGray
                    ;
    LinearLayout
            llOrder,
            llAmount,
            llDate,
            llBonus;
    Button btnCheckMail;
    Button btnContinue;
    private Tracker mTracker;

    @SuppressLint("StringFormatInvalid")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_new);
        txtOrderNumber = (TextView)findViewById(R.id.txtOrderNumberThankYouScreen);
        txtAmount = (TextView)findViewById(R.id.txtOrderAmountThankYouScreen);
        txtDate = (TextView)findViewById(R.id.txtOrderDateThankYouScreen);
        txtBonus = (TextView)findViewById(R.id.txtOrderBonusThankYouScreen);

        txtGreen = (TextView)findViewById(R.id.txtGreenTextThankYouScreen);
        txtBlack = (TextView)findViewById(R.id.txtBlackTextThankYouScreen);
        txtRed = (TextView)findViewById(R.id.txtRedTextThankYouScreen);
        txtGray = (TextView)findViewById(R.id.txtGrayTextThankYouScreen);

        llOrder = (LinearLayout) findViewById(R.id.llOrderNumberThankYouScreen);
        llAmount = (LinearLayout) findViewById(R.id.llOrderAmountThankYouScreen);
        llDate = (LinearLayout) findViewById(R.id.llOrderDateThankYouScreen);
        llBonus = (LinearLayout) findViewById(R.id.llOrderBonusThankYouScreen);

        btnCheckMail = (Button)findViewById(R.id.btnCheckMailThankYouScreen);
        btnContinue = (Button)findViewById(R.id.btnContinueThankYouScreen);

        FontUtils.setNormalFont(this, txtOrderNumber);
        FontUtils.setNormalFont(this, txtDate);
        FontUtils.setNormalFont(this, txtAmount);
        FontUtils.setNormalFont(this, txtBonus);
        FontUtils.setBoldFont(this, btnCheckMail);
        FontUtils.setBoldFont(this, btnContinue);
        Intent intent = getIntent();
        int orderID = intent.getIntExtra("orderID", 0); //if it's a string you stored.
        double totalPrice = intent.getDoubleExtra("totalPrice", 0); //if it's a string you stored.
        String discountCode = intent.getStringExtra("discountCode");
        boolean mIsS7 = intent.getBooleanExtra("isS7", false);
        int mIsS7Miles = intent.getIntExtra("isS7miles", 0);
        int mIsS7Discount = intent.getIntExtra("isS7discount", 0);

        ThankPageType type = (ThankPageType) intent.getSerializableExtra("thankType");
        String amount = intent.getStringExtra("amount");
        String date = intent.getStringExtra("date");

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

        TreeMap<String, String> tmpMap = MDFApplication.getMDFApplication().getStoresMap();
        List<String> tmpList = new ArrayList<String>(tmpMap.values());
        if (tmpList.size() > 1) {

            String stringRed = getResources().getString(R.string.thank_text_red);
            StringBuilder stringBuildBlack = new StringBuilder();
            for (int i = 0; i < tmpList.size(); i++) {
                stringBuildBlack.append("\n").append(tmpList.get(i));
            }
            String stringBlack = stringBuildBlack.toString();
            String all = String.format("%s%s", stringRed, stringBlack);
            Spannable spanString = new SpannableString(all);

//            tipBlack.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorBlack)), 0, stringBlack.toString().length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorRed)), all.indexOf(stringRed), all.indexOf(stringRed) + all.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorBlack)), all.indexOf(stringBlack), all.indexOf(stringBlack) + stringBlack.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//            tipBlack.setSpan(new CustomTypefaceSpan("", MDFApplication.getBoldTypeface()), stringBlack.indexOf(stringRed), stringBlack.indexOf(stringRed) + stringRed.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            txtRed.setText(spanString);
            txtRed.setVisibility(View.VISIBLE);
        }

        if (type == ThankPageType.THANK_TYPE_BORDERSHOP) {
            llOrder.setVisibility(View.VISIBLE);
            txtOrderNumber.setText(String.format("#%d", orderID));
            llDate.setVisibility(View.VISIBLE);
            txtDate.setText(date);
            llAmount.setVisibility(View.VISIBLE);
            txtAmount.setText(amount);
            txtGreen.setVisibility(View.VISIBLE);
            txtGreen.setText(getResources().getString(R.string.thank_text_green));
            txtBlack.setVisibility(View.VISIBLE);
            txtBlack.setText(getResources().getString(R.string.thank_text_black_bordershop));
        }
        else if (type == ThankPageType.THANK_TYPE_DOMODEDOVO) {
            llOrder.setVisibility(View.VISIBLE);
            txtOrderNumber.setText(String.format("#%d", orderID));
            llDate.setVisibility(View.VISIBLE);
            txtDate.setText(date);
            llAmount.setVisibility(View.VISIBLE);
            txtAmount.setText(amount);
            txtGreen.setVisibility(View.VISIBLE);
            txtGreen.setText(getResources().getString(R.string.thank_text_green));
            txtBlack.setVisibility(View.VISIBLE);
            txtBlack.setText(getResources().getString(R.string.thank_text_black_domodedovo));
            txtGray.setVisibility(View.VISIBLE);
            txtGray.setText(getResources().getString(R.string.thank_text_gray));
        }
        else if (type == ThankPageType.THANK_TYPE_BAKU) {
            llOrder.setVisibility(View.VISIBLE);
            txtOrderNumber.setText(String.format("#%d", orderID));
            llDate.setVisibility(View.VISIBLE);
            txtDate.setText(date);
            llAmount.setVisibility(View.VISIBLE);
            txtAmount.setText(amount);
            txtGreen.setVisibility(View.VISIBLE);
            txtGreen.setText(getResources().getString(R.string.thank_text_green));
            txtBlack.setVisibility(View.VISIBLE);
            txtBlack.setText(getResources().getString(R.string.thank_text_black));
            txtGray.setVisibility(View.VISIBLE);
            txtGray.setText(getResources().getString(R.string.thank_text_gray_baku));
        }
        else if (type == ThankPageType.THANK_TYPE_FRIENDS_CART) {
            llDate.setVisibility(View.VISIBLE);
            txtDate.setText(date);
            llAmount.setVisibility(View.VISIBLE);
            txtAmount.setText(amount);
            txtGreen.setVisibility(View.VISIBLE);
            txtGreen.setText(getResources().getString(R.string.thank_text_green_friends_cart));
            txtBlack.setVisibility(View.VISIBLE);
            txtBlack.setText(getResources().getString(R.string.thank_text_black_friends_cart));
            txtGray.setVisibility(View.GONE);
            txtGray.setText(null);
        }
        else {
            llOrder.setVisibility(View.VISIBLE);
            txtOrderNumber.setText(String.format("#%d", orderID));
            llDate.setVisibility(View.VISIBLE);
            txtDate.setText(date);
            llAmount.setVisibility(View.VISIBLE);
            txtAmount.setText(amount);
            txtGreen.setVisibility(View.VISIBLE);
            txtGreen.setText(getResources().getString(R.string.thank_text_green));
            txtBlack.setVisibility(View.VISIBLE);
            txtBlack.setText(getResources().getString(R.string.thank_text_black));
            txtGray.setVisibility(View.VISIBLE);
            txtGray.setText(getResources().getString(R.string.thank_text_gray));
        }

//        txtOrderNumber.append(String.format(" %d", orderID));
        if (mIsS7) {
            llBonus.setVisibility(View.VISIBLE);
            txtBonus.append(String.format(getResources().getString(R.string.s7_thank_black), mIsS7Miles ,mIsS7Discount));
        }
        else {
            llBonus.setVisibility(View.GONE);
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