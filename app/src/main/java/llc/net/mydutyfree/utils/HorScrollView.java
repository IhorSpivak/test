package llc.net.mydutyfree.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by gorf on 1/15/16.
 */
public class HorScrollView extends HorizontalScrollView{

    public HorScrollView(Context context) {
        super(context);
    }

    public HorScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void  computeScroll  (){
        return;
    }

}
