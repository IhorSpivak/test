package llc.net.mydutyfree.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by gorf on 1/11/16.
 */
public class SlashStrikedTextView extends TextView {
    public SlashStrikedTextView(Context context)
    {
        super(context);
    }

    public SlashStrikedTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SlashStrikedTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        this.setGravity(Gravity.CENTER);
        super.onDraw(canvas);
        String string = getText().toString();
        Rect bounds = new Rect();
        Paint textPaint = this.getPaint();
        textPaint.setStrokeWidth(dpToPx(1));
        textPaint.setTypeface(this.getTypeface());
        textPaint.setTextSize(this.getTextSize());
        textPaint.setAntiAlias(true);
        textPaint.getTextBounds(string, 0, string.length(), bounds);
//        bounds.offsetTo((getWidth() / 2) - (bounds.width() / 2), (getHeight() / 2) - (bounds.height() / 2));

//        canvas.drawText(string, bounds.left, +bounds.top, textPaint);
//        bounds.top += 5;
//        bounds.bottom -= 5;
        bounds.right += 5;
        bounds.left -= 5;
//        canvas.drawLine(bounds.left, bounds.top, bounds.right, bounds.bottom, textPaint);

//        Paint redPaint = new Paint();
//        redPaint.setColor(Color.RED);
//        redPaint.setStrokeWidth(dpToPx(1));
        int de = (this.getWidth() - bounds.width()) / 2;

        canvas.drawLine(de, dpToPx(7), this.getWidth() - de , this.getHeight() - dpToPx(7), textPaint);
    }

    public static float dpToPx(int dp)
    {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

}
