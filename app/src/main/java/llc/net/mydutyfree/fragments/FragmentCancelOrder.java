package llc.net.mydutyfree.fragments;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.newmdf.MainActivity;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.utils.Const;
import llc.net.mydutyfree.utils.FontUtils;
import llc.net.mydutyfree.utils.Utils;

/**
 * Created by gorf on 2/17/16.
 */
public class FragmentCancelOrder extends Fragment {
    String mContentType;
    TextView txtTitle, txtText, txtEmail, txtReason, txtOrderID;
    EditText edtEmail, edtReason, edtOrderID;
    Button btnSend;
    private Tracker mTracker;

    public FragmentCancelOrder() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.screen_cancel_order, container, false);

        ((MainActivity)getActivity()).setTitle(getResources().getString(R.string.cancelling_order));
        txtEmail = (TextView)rootView.findViewById(R.id.txtEmailScreenCancelOrder);
        txtOrderID = (TextView)rootView.findViewById(R.id.txtOrderIDScreenCancelOrder);
        txtReason = (TextView)rootView.findViewById(R.id.txtReasonScreenCancelOrder);
        txtTitle = (TextView)rootView.findViewById(R.id.txtTitleScreenCancelOrder);
        txtText = (TextView)rootView.findViewById(R.id.txtTextScreenCancelOrder);

        edtEmail = (EditText)rootView.findViewById(R.id.edtEmailScreenCancelOrder);
        edtOrderID = (EditText)rootView.findViewById(R.id.edtOrderIDScreenCancelOrder);
        edtReason = (EditText)rootView.findViewById(R.id.edtReasonScreenCancelOrder);

        btnSend = (Button)rootView.findViewById(R.id.btnSendScreenCancelOrder);

        FontUtils.setNormalFont(getContext(), txtEmail);
        FontUtils.setNormalFont(getContext(), txtReason);
        FontUtils.setNormalFont(getContext(), txtOrderID);
        FontUtils.setBoldFont(getContext(), txtTitle);
        FontUtils.setNormalFont(getContext(), txtText);

        FontUtils.setNormalFont(getContext(), edtEmail);
        FontUtils.setNormalFont(getContext(), txtReason);
        FontUtils.setNormalFont(getContext(), txtOrderID);

        FontUtils.setBoldFont(getContext(), btnSend);

        setHasOptionsMenu(true);

        mTracker = ((MDFApplication)getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName("Customer Support Cancelling");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    JSONObject bodyCancelOrder = new JSONObject();
                    try {
                        if (!edtReason.getText().toString().equalsIgnoreCase(""))
                            bodyCancelOrder.accumulate("reason", edtReason.getText().toString());
                        bodyCancelOrder.accumulate("email", edtEmail.getText().toString());
                        bodyCancelOrder.accumulate("order_id", edtOrderID.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    new AsyncCustomPost(getContext(), Const.URLS.URL, Const.Actions.Order.CANCEL_REQUEST, bodyCancelOrder).execute();
                }
            }
        });

        return rootView;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private Boolean isValid() {
        Boolean isValid = true;
        String errorString = "";

        if (edtOrderID.getText().toString().equalsIgnoreCase("")) {
            errorString = errorString.concat("\n" + getResources().getString(R.string.please_enter_valid_order_ID));
            isValid = false;
        }
        if (!isValidEmail(edtEmail.getText())) {
            errorString = errorString.concat("\n" + getResources().getString(R.string.please_enter_valid_email));
            isValid = false;
        }
        if (!errorString.equalsIgnoreCase(""))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(false);
            builder.setPositiveButton(getResources().getString(R.string.ok_caps), null);
            TextView message = new TextView(getContext());
            message.setText(errorString);
            message.setGravity(Gravity.CENTER_HORIZONTAL);
            message.setPadding(
                    (int) Utils.dpToPx(10),
                    (int) Utils.dpToPx(10),
                    (int) Utils.dpToPx(10),
                    (int) Utils.dpToPx(10)
            );
            FontUtils.setBoldFont(getContext(), message);
            builder.setView(message);
            builder.show();
        }

        return isValid;
    }

    class AsyncCustomPost extends AsyncTask<String, String, String> {
        private Context mContext;
        private JSONObject mBody;
        private String mAction;
        private String mURL;
        private Boolean mIsNewArrivals;

        public AsyncCustomPost(Context context, String url, String action, JSONObject body) {
            mContext = context;
            mURL = url;
            mAction = action;
            mBody = body;
        }

        private String POST(String url, String action, JSONObject body){
            InputStream inputStream = null;
            String result = "";
            try {

                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost(url);

                String json = "";

                JSONObject jsonObject = new JSONObject();
                String curLang = ((MDFApplication) getActivity().getApplication()).getCurrentLanguage();
                String curAirport = ((MDFApplication) getActivity().getApplication()).getAirport();
                String storeID;
                if (curAirport.equalsIgnoreCase("0"))
                    storeID = "1";
                else
                    storeID = curAirport;

                jsonObject.accumulate("lang_code", curLang);
                jsonObject.accumulate("action", action);
                jsonObject.accumulate("body", body);
                jsonObject.accumulate("store_id", storeID);
                jsonObject.accumulate("auth_key", Const.API_KEY);

                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();

                // ** Alternative way to convert Person object to JSON string usin Jackson Lib
                // ObjectMapper mapper = new ObjectMapper();
                // json = mapper.writeValueAsString(person);

                // 5. set json to StringEntity
                StringEntity se = new StringEntity(json, "UTF-8");

                // 6. set httpPost Entity
                httpPost.setEntity(se);

                // 7. Set some headers to inform server about the type of the content
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                // 8. Execute POST request to the given URL
                HttpResponse httpResponse = httpclient.execute(httpPost);

                // 9. receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

                // 10. convert inputstream to string
                if(inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            // 11. return result
            return result;
        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity mainActivity = (MainActivity)getActivity();
            mainActivity.showProgress();
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (mAction.equalsIgnoreCase(Const.Actions.Order.CANCEL_REQUEST))
                {
                    JSONObject json = new JSONObject(result);
                    if (json.getString("status").equalsIgnoreCase("success")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setCancelable(false);
                        builder.setPositiveButton(getResources().getString(R.string.ok_caps), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().getSupportFragmentManager().popBackStack();
                            }
                        });
                        TextView message = new TextView(getContext());
                        message.setText(R.string.order_cancel_ok);
                        message.setGravity(Gravity.CENTER_HORIZONTAL);
                        message.setPadding(
                                (int) Utils.dpToPx(10),
                                (int) Utils.dpToPx(10),
                                (int) Utils.dpToPx(10),
                                (int) Utils.dpToPx(10)
                        );
                        FontUtils.setBoldFont(getContext(), message);
                        builder.setView(message);
                        builder.show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Error json", e.toString());
            }
            Log.d("Result", result);

            MainActivity mainActivity = (MainActivity)getActivity();
            mainActivity.hideProgress();
            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected String doInBackground(String... params) {
            return POST(mURL, mAction, mBody);
        }
    }
}
