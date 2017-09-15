package llc.net.mydutyfree.controls;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageButton;

import llc.net.mydutyfree.newmdf.R;

public class RoundImageButton extends ImageButton
{
	Context mContext;
	Bitmap bmp;
	int bmpheight;
    boolean flag=true;
    private ColorMatrixColorFilter mColorFilter;

	public RoundImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}
	
	private void init(){
		bmp = ((BitmapDrawable)this.getDrawable()).getBitmap();
		bmpheight = bmp.getHeight();
		setImageBitmap(getRoundedCornerBitmap(bmp, bmpheight));	
		ColorMatrix cm = new ColorMatrix();
	    cm.setSaturation(0f);
	    cm.set(new float[] 
	    	    {
	            0.4f, 0, 0, 0, 0,
	            0, 0.4f, 0, 0, 0,
	            0, 0, 0.4f, 0, 0,
	            0, 0, 0, 1, 0,
	            0, 0, 0, 0, 0
	    }); 
	    mColorFilter = new ColorMatrixColorFilter(cm);
	}
	
	public Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) 
    {
		bitmap = cropBitmap(bitmap);
        Bitmap output = null;

        if(bitmap != null)
        {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = pixels;

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
        }

        return output;
    }
	
	
	public Bitmap getRoundedCornerBitmap(Context context, int resource, int pixels) 
    {
        return getRoundedCornerBitmap(BitmapFactory.decodeResource(context.getResources(), resource),pixels);
    }

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		//canvas.clipPath(getRoundRectPath());
		super.dispatchDraw(canvas);
	}

	@Override
	public void draw(Canvas canvas) {
		//canvas.clipPath(getRoundRectPath());
		super.draw(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		int oldWidth = getMeasuredWidth();
//		int oldHeight = getMeasuredHeight();
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		int newWidth = getMeasuredWidth();
//		int newHeight = getMeasuredHeight();
//		if (newWidth != oldWidth || newHeight != oldHeight) {
//			isPathValid = false;
//		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int controlWidth = getWidth();
		int controlHeight = getHeight();
		int semiWidth = controlWidth / 2;
		int semiHeight = controlHeight / 2;
		int dx = (int) (event.getX() - semiWidth);
		int dy = (int) (event.getY() - semiHeight);
		double delta = Math.sqrt((dx * dx) + (dy * dy));
		float radius = (controlWidth > controlHeight ? semiHeight : semiWidth)+5;
		if (delta < radius){
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				this.setColorFilter(mColorFilter);
				flag=false;
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				this.setColorFilter(null);
				flag=true;
				
			}
			return super.onTouchEvent(event);
			} else {
				if (flag==false) {
					this.setColorFilter(null);
					flag=true;
				}
		return false;
			}
	}


	public Bitmap changeBitmapColor(Bitmap sourceBitmap)
	{   
	    int bmpWidth, bmpHeight;
	    bmpHeight = sourceBitmap.getHeight();
	    bmpWidth = sourceBitmap.getWidth();    
	    Bitmap bmpGrayscale = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);
	    Canvas mCanvas = new Canvas(bmpGrayscale);
	    Paint paint = new Paint();
	    ColorMatrix colrMatr = new ColorMatrix();
	    colrMatr.setSaturation(0f);
	    colrMatr.set(new float[] 
	    	    {
	    	            0.4f, 0, 0, 0, 0,
	    	            0, 0.4f, 0, 0, 0,
	    	            0, 0, 0.4f, 0, 0,
	    	            0, 0, 0, 1, 0,
	    	            0, 0, 0, 0, 0
	    	    }); 
	    ColorMatrixColorFilter colMatrFltr = new ColorMatrixColorFilter(colrMatr);
	    paint.setColorFilter(colMatrFltr);
	    mCanvas.drawBitmap(sourceBitmap, 0, 0, paint);
	    return bmpGrayscale;
	}

	private Bitmap cropBitmap(Bitmap srcBmp) {
		//Bitmap dstBmp = null; 
		if (srcBmp.getWidth() >= srcBmp.getHeight()){

			srcBmp = Bitmap.createBitmap(
			     srcBmp, 
			     srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
			     0,
			     srcBmp.getHeight(), 
			     srcBmp.getHeight()
			     );

			}else{

				srcBmp = Bitmap.createBitmap(
			     srcBmp,
			     0, 
			     srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
			     srcBmp.getWidth(),
			     srcBmp.getWidth() 
			     );
			}
		//srcBmp.recycle();
		return srcBmp;
	}

    public void SetImageURL(String url) {
        Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.cat);
        bmpheight = icon.getHeight();
        bmp = icon;
        setImageBitmap(getRoundedCornerBitmap(bmp, bmpheight));
        init();
    }
    
}