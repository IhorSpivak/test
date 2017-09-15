package llc.net.mydutyfree.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import llc.net.mydutyfree.newmdf.R;

public class MDFProgressDialog extends Dialog {
	ProgressBar roller;
	RelativeLayout barHolder;
	RelativeLayout rootLayout;
	RelativeLayout lightRootLayout;
	Context cntx;
	TextView progressText;
	String isDialogLightOrDark;
	ProgressBar progressBarHour, progressBarMinute;
	ImageView clockImage;
	
	public MDFProgressDialog(Context context, boolean empty){
		this(context,empty,"light");
	}
	
    public MDFProgressDialog(Context context, boolean empty, String lightOrDark) {
        super(context, R.style.MDFAlertDialog);
        cntx = context;
        isDialogLightOrDark = lightOrDark;
        if (isDialogLightOrDark.equalsIgnoreCase("light")) {
        	this.setContentView(R.layout.mdf_progress_dialog_light);
        	lightRootLayout = (RelativeLayout)findViewById(R.id.mdf_progress_dialog_light_root);
        	lightRootLayout.setBackgroundColor(Color.parseColor("#90000000"));
        }
        else if (isDialogLightOrDark.equalsIgnoreCase("dark")) {
        	this.setContentView(R.layout.mdf_progress_dialog_dark);
        }
        
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        getWindow().getAttributes().windowAnimations = R.style.AlertDialogAnimation; 
        barHolder = (RelativeLayout)findViewById(R.id.mdf_progress_dialog_bar_holder);
        rootLayout = (RelativeLayout)findViewById(R.id.mdf_progress_dialog_root_layout);
        progressText = (TextView)findViewById(R.id.mdf_progress_dialog_text);
        clockImage = (ImageView)findViewById(R.id.mdf_progress_dialog_clock);
        if (empty) {
        rootLayout.setBackgroundColor(Color.TRANSPARENT);
        progressText.setText(null);
        if (isDialogLightOrDark.equalsIgnoreCase("light")) lightRootLayout.setBackgroundColor(Color.TRANSPARENT);
        } else {if (isDialogLightOrDark.equalsIgnoreCase("light")) lightRootLayout.setBackgroundColor(Color.parseColor("#90000000"));}
    }

	@Override
	protected void onStart() {
		WindowManager wm = (WindowManager)this.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int displayHeight = display.getHeight();
		int displayWidth = display.getWidth();
        progressText.setText(cntx.getResources().getString(R.string.please_wait));
		int TOOLBAR_H = 0;		
        if (isDialogLightOrDark.equalsIgnoreCase("light")) {
        	RelativeLayout.LayoutParams rootParams;
       
        	float scaleFactor = 0.5f;
//        	if (Utils.isTablet(getContext())) {
//        		TOOLBAR_H = Utils.dpToPx(getContext(), 50);
//        		scaleFactor = 0.25f;
//        		if ((getContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
//                 		 == Configuration.SCREENLAYOUT_SIZE_LARGE)
//        			scaleFactor = 0.35f;
//        	}
        	
        	int width = displayHeight > displayWidth ? displayWidth : (displayHeight + TOOLBAR_H);
        	int dialogHeigh = (int) (width * scaleFactor);
        	int dialogWidth = (int) (width * scaleFactor);
        	
//        	if (Utils.isTablet(cntx) && cntx.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//        		dialogHeigh = (int) (height * 0.5);
//            	dialogWidth= (int) (height * 0.5);
//        	}
        	
        	rootParams = new RelativeLayout.LayoutParams(
            		dialogWidth, dialogHeigh);
        	
            rootParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            RelativeLayout.LayoutParams paramsArrow = new RelativeLayout.LayoutParams((int)(dialogWidth*0.30), (int) (dialogHeigh*0.30));
            paramsArrow.addRule(RelativeLayout.CENTER_IN_PARENT);
            RelativeLayout.LayoutParams paramsClock = new RelativeLayout.LayoutParams((int)(dialogWidth*0.5), (int) (dialogHeigh*0.5));
            paramsClock.addRule(RelativeLayout.CENTER_IN_PARENT);
            paramsClock.setMargins(0, 00, 0, 0);
            progressBarHour = new ProgressBar(cntx);
            progressBarMinute = new ProgressBar(cntx);
            rootLayout.setLayoutParams(rootParams);
            progressBarMinute.setLayoutParams(paramsArrow);
            progressBarHour.setLayoutParams(paramsArrow);
            clockImage.setLayoutParams(paramsClock);
        }
        else if (isDialogLightOrDark.equalsIgnoreCase("dark")) {
        	FrameLayout.LayoutParams rootParams;
            if ((displayHeight > 330) || (displayWidth > 250)) {
    			rootParams = new FrameLayout.LayoutParams(
    					LayoutParams.FILL_PARENT, (int) (displayHeight * 0.4));
    		} else {
    			rootParams = new FrameLayout.LayoutParams(
    					LayoutParams.FILL_PARENT, (int) (displayHeight * 0.5));
    		}
            rootParams.gravity=Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(displayHeight*0.2), (int) (displayHeight*0.2));
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            progressBarHour = new ProgressBar(cntx);
            progressBarMinute = new ProgressBar(cntx);
            rootLayout.setLayoutParams(rootParams);
            progressBarHour.setLayoutParams(params);
            progressBarMinute.setLayoutParams(params);
            progressBarHour.setBackgroundColor(Color.TRANSPARENT);
            progressBarMinute.setBackgroundColor(Color.TRANSPARENT);
        }
        
        if (isDialogLightOrDark.equalsIgnoreCase("light")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progressBarHour.setIndeterminateDrawable(cntx.getResources().getDrawable(R.drawable.mdf_progress_dialog_rotating_clock_arrow_hour, cntx.getTheme()));
                progressBarMinute.setIndeterminateDrawable(cntx.getResources().getDrawable(R.drawable.mdf_progress_dialog_rotating_clock_arrow_minute, cntx.getTheme()));
            } else {
                progressBarHour.setIndeterminateDrawable(cntx.getResources().getDrawable(R.drawable.mdf_progress_dialog_rotating_clock_arrow_hour));
                progressBarMinute.setIndeterminateDrawable(cntx.getResources().getDrawable(R.drawable.mdf_progress_dialog_rotating_clock_arrow_minute));
            }
        }
        else if (isDialogLightOrDark.equalsIgnoreCase("dark")) {
        	progressBarHour.setIndeterminateDrawable(cntx.getResources().getDrawable(R.drawable.mdf_progress_dialog_rotating_blue_arrow));
        }

        barHolder.addView(progressBarHour);
        barHolder.addView(progressBarMinute);
		super.onStart();
	}

}
