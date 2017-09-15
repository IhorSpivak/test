package llc.net.mydutyfree.base;

//import com.devreactor.ibox.pro.dialogs.MDFProgressDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

public class AsyncTaskEx<T1, T3> extends AsyncTask<T1, String, T3> {

    protected Context mContext;
    protected boolean mShowProgress;
    protected boolean mCancelable;
    protected MDFProgressDialog mProgressDialog;
    
    public AsyncTaskEx(Context ctx, boolean showProgress) {
        mContext = ctx;
        mShowProgress = showProgress;
        mCancelable = true;
    }

    protected T3 doInBackground(T1... params) {
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {

    }

    @Override
    protected void onCancelled() {
    	if (mCancelable) {
	        try {
	            if (mShowProgress) {
	                mProgressDialog.cancel();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
    	}
    }

    @Override
    protected void onPostExecute(T3 result) {
        if (mShowProgress) {
            if (mProgressDialog.isShowing()) {
                try {
                    mProgressDialog.dismiss();
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPreExecute() {
        try {
            if (mShowProgress) {
                mProgressDialog = new MDFProgressDialog(mContext,false);
                mProgressDialog.show();
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.setCancelable(mCancelable);
				if (mCancelable) {
	                mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

						@Override
						public void onCancel(DialogInterface dialog) {
								AsyncTaskEx.this.cancel(true);
						}
					});
            	}
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setCancelable(boolean cancelable) {
    	mCancelable = cancelable;
    }
    
}
