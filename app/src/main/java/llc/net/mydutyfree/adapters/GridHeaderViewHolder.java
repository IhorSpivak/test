package llc.net.mydutyfree.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.viewpagerindicator.CirclePageIndicator;

import llc.net.mydutyfree.base.WrapContentHeightViewPager;
import llc.net.mydutyfree.newmdf.R;

/**
 * Created by gorf on 7/27/16.
 */
public class GridHeaderViewHolder extends RecyclerView.ViewHolder {

    public WrapContentHeightViewPager pager;
    public CirclePageIndicator indicator;
    public Button btnSpecialOffers;
    public Button btnTopSellers;
    public View viewRedLine;
    public RelativeLayout relativeLayoutRedLineContainer;

    public GridHeaderViewHolder(View view) {
        super(view);
        pager = (WrapContentHeightViewPager) view.findViewById(R.id.viewPager);
        indicator = (CirclePageIndicator) view.findViewById(R.id.pageIndicator);
        btnSpecialOffers = (Button) view.findViewById(R.id.headerSpecialOffers);
        btnTopSellers = (Button) view.findViewById(R.id.headerTopSellers);
        viewRedLine = view.findViewById(R.id.viewRedLine);
        relativeLayoutRedLineContainer = (RelativeLayout) view.findViewById(R.id.relativeLayoutRedLineContainer);
    }
}
