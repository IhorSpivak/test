package llc.net.mydutyfree.utils;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.widget.Button;
/**
 * Created by gorf on 1/18/16.
 */
public class Utils {
    public static float dpToPx(int dp)
    {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static void scaleButtonDrawables(Button btn, double fitFactor) {
        Drawable[] drawables = btn.getCompoundDrawables();

        for (int i = 0; i < drawables.length; i++) {
            if (drawables[i] != null) {
                int imgWidth = drawables[i].getIntrinsicWidth();
                int imgHeight = drawables[i].getIntrinsicHeight();
                if ((imgHeight > 0) && (imgWidth > 0)) {    //might be -1
                    float scale;
                    if ((i == 0) || (i == 2)) { //left or right -> scale height
                        scale = (float) (btn.getHeight() * fitFactor) / imgHeight;
                    } else { //top or bottom -> scale width
                        scale = (float) (btn.getWidth() * fitFactor) / imgWidth;
                    }
                    if (scale < 1.0) {
                        Rect rect = drawables[i].getBounds();
                        int newWidth = (int)(imgWidth * scale);
                        int newHeight = (int)(imgHeight * scale);
                        rect.left = rect.left + (int)(0.5 * (imgWidth - newWidth));
                        rect.top = rect.top + (int)(0.5 * (imgHeight - newHeight));
                        rect.right = rect.left + newWidth;
                        rect.bottom = rect.top + newHeight;
                        drawables[i].setBounds(rect);
                    }
                }
            }
        }
    }
}
