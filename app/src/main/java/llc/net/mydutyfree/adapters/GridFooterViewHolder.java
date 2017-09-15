package llc.net.mydutyfree.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import llc.net.mydutyfree.newmdf.R;

/**
 * Created by gorf on 7/27/16.
 */
public class GridFooterViewHolder extends RecyclerView.ViewHolder {

    public Button btnShowAllGoods;

    public GridFooterViewHolder(View view) {
        super(view);
        btnShowAllGoods = (Button) view.findViewById(R.id.btnHomeFooterShowAllGoods);
    }
}
