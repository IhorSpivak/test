package llc.net.mydutyfree.newmdf;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.utils.FontUtils;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by gorf on 3/4/16.
 */
public class FullScreenImageActivity extends Activity {

    ImageView mImageView;
    private Tracker mTracker;
    PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        mImageView = (ImageView) findViewById(R.id.full_screen_image_view);

        mTracker = ((MDFApplication)getApplication()).getDefaultTracker();
        mTracker.setScreenName("FullScreen Image");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        Intent intent = getIntent();

        Picasso.Builder picassoLoader = new Picasso.Builder(this);
        Picasso picasso = picassoLoader.build();
        picasso
                .load(Uri.encode(intent.getStringExtra("image"), "@#&=*+-_.,:!?()/~'%"))
                .error(R.drawable.missing_product)
                .into(mImageView, new Callback() {
                    @Override
                    public void onSuccess() {
//                        mImageView.invalidate();
                        mAttacher = new PhotoViewAttacher(mImageView);
                    }

                    @Override
                    public void onError() {
//                        Log.e("!!!!!!", "!!!");
                    }
                });

//        Picasso.with(getBaseContext())
//                .load(intent.getStringExtra("image"))
//                .error(R.drawable.missing_product)
//                .into(mImageView);


    }
}