package llc.net.mydutyfree.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
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

/**
 * Created by gorf on 2/17/16.
 */
public class FragmentWebView  extends Fragment {

    WebView webView;
    String mContentType;
    private Tracker mTracker;

    public FragmentWebView() {
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
        View rootView = inflater.inflate(R.layout.screen_web_view, container, false);

        setHasOptionsMenu(true);
        mTracker = ((MDFApplication)getActivity().getApplication()).getDefaultTracker();
        mTracker.setScreenName("Content");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        webView = (WebView) rootView.findViewById(R.id.webViewScreenWebView);

        String URL = Const.URLS.URL;
        String actionGetPages = Const.Actions.Content.GET_PAGES;

        JSONObject bodyGetPages = new JSONObject();

        new AsyncCustomPost(getContext(), URL, actionGetPages, bodyGetPages).execute();
        return rootView;
    }

    class AsyncCustomPost extends AsyncTask<String, String, String> {
        private Context mContext;
        private JSONObject mBody;
        private String mAction;
        private String mURL;

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
                if (curAirport == null || curAirport.equalsIgnoreCase("0"))
                    storeID = "1";
                else
                    storeID = curAirport;

                jsonObject.accumulate("lang_code", curLang);
                jsonObject.accumulate("action", action);
                jsonObject.accumulate("body", body);
                jsonObject.accumulate("store_id", storeID);
                jsonObject.accumulate("auth_key", Const.API_KEY);

                json = jsonObject.toString();
                StringEntity se = new StringEntity(json, "UTF-8");
                httpPost.setEntity(se);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");
                HttpResponse httpResponse = httpclient.execute(httpPost);
                inputStream = httpResponse.getEntity().getContent();
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
//            if (!mProgressDialog.isShowing())
//            {
//                try {
//                    mProgressDialog.show();
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//            ((MDFApplication)getActivity().getApplication()).showProgress();
            MainActivity mainActivity = (MainActivity)getActivity();
            mainActivity.showProgress();

            Log.e("Ok","Ok");
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (mAction.equalsIgnoreCase(Const.Actions.Content.GET_PAGES))
                {
//                    ArrayList<BannerItem> arrayListBanners = new ArrayList<>();
                    JSONObject json = new JSONObject(result);
                    JSONArray data = json.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject object = data.getJSONObject(i);
                        if (object.getString("identifier").equalsIgnoreCase(mContentType))
                        {
                            ((MainActivity)getActivity()).setTitle(object.getString("title"));
                            String html = object.getString("content");
                            String mime = "text/html";
                            String encoding = "utf-8";

                            String pish = "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/fonts/GOTHIC.TTF\")}body {font-family: MyFont;font-size: x-small;text-align: justify;}</style></head><body>";
                            String pas = "</body></html>";
                            String myHtmlString = pish + html + pas;

                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.loadDataWithBaseURL(null, myHtmlString, mime, encoding, null);
                        }

//                        arrayListBanners.add(item);
                    }
                    //{"content":"<div class=\"mobapp-promo\">\r\n<div>\r\n<div>\r\n<h1>Try the new way of shopping with MyDutyFree service<\/h1>\r\n<hr \/>\r\n<p>Pre-order goods via our mobile app, fill out a simple form with your flight data and get your duty-free goods with discounts.<\/p>\r\n<a href=\"https:\/\/itunes.apple.com\/WebObjects\/MZStore.woa\/wa\/viewSoftware?id=1043556796&amp;mt=8\" target=\"_blank\"><img alt=\"\" src=\"http:\/\/mydutyfree.net\/skin\/frontend\/mdf\/default\/images\/app\/appstore.png\" \/><\/a><\/div>\r\n<img class=\"abs\" alt=\"\" src=\"http:\/\/mydutyfree.net\/skin\/frontend\/mdf\/default\/images\/app\/iphone1.png\" \/><\/div>\r\n<div>\r\n<p><span>MyDutyFree is an online service for pre-ordering goods<\/span> from duty free stores, that allows you to save your time, money, and provides new services for your convenience.<\/p>\r\n<\/div>\r\n<div><img alt=\"\" src=\"http:\/\/mydutyfree.net\/skin\/frontend\/mdf\/default\/images\/app\/iphone2.png\" \/>\r\n<div>\r\n<h2>Our mobile app allows you to:<\/h2>\r\n<ul>\r\n<li>Save your time.<\/li>\r\n<li>Enjoy our personal approach to each user of MDF mobile app.<\/li>\r\n<li>Appreciate a 10% or even 12% discount on all goods from MyDutyFree.<\/li>\r\n<li>Appreciate even greater seasonal or special offer discount of up to 30%.<\/li>\r\n<\/ul>\r\n<\/div>\r\n<\/div>\r\n<div>\r\n<div>\r\n<ul>\r\n<li>Earn air miles for your purchases<\/li>\r\n<li>Choose goods by categories<\/li>\r\n<li>Enjoy the simple user-friendly navigation<\/li>\r\n<li>See all prices in 3 main currencies - USD, EUR, UAH<\/li>\r\n<li>Enjoy much more privileges and further improvements<\/li>\r\n<\/ul>\r\n<\/div>\r\n<img alt=\"\" src=\"http:\/\/mydutyfree.net\/skin\/frontend\/mdf\/default\/images\/app\/iphone3.png\" \/><\/div>\r\n<div>\r\n<p>Mydutyfree app has a range of functions, which guarantees safe, comfortable and enjoyable shopping. Android version of our app will come within the first quarter of the next year.<\/p>\r\n<a href=\"https:\/\/itunes.apple.com\/WebObjects\/MZStore.woa\/wa\/viewSoftware?id=1043556796&amp;mt=8\" target=\"_blank\"><img alt=\"\" src=\"http:\/\/mydutyfree.net\/skin\/frontend\/mdf\/default\/images\/app\/appstore.png\" \/><\/a><\/div>\r\n<\/div>",
                    // "page_id":"26","title":"mobile app","is_active":"1","identifier":"mobileapps"}
                    Log.e("ATATAT", "bannersDone");
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
