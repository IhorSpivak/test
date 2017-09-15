package llc.net.mydutyfree.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import llc.net.mydutyfree.newmdf.R;

/**
 * Created by gorf on 7/27/16.
 */
public class WishListHeaderViewHolder extends RecyclerView.ViewHolder {

    public TextView txtHeader;

    public WishListHeaderViewHolder(View view) {
        super(view);
        txtHeader = (TextView) view.findViewById(R.id.txtWishListHeader);
    }
}
