package llc.net.mydutyfree.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.utils.SlashStrikedTextView;

/**
 * Created by gorf on 7/27/16.
 */
public class GridCellViewHolder extends RecyclerView.ViewHolder {

    public ImageView imgMainImage;
    public SlashStrikedTextView txtPriceOld;
    public TextView txtPrice;
    public TextView txtProduct;
    public TextView txtName;
    public TextView txtMainAttribute;
    public View view;

    public GridCellViewHolder(View view) {
        super(view);
        this.view = view;
        txtPriceOld = (SlashStrikedTextView) view.findViewById(R.id.txtGridCellPriceOld);
        txtPrice = (TextView) view.findViewById(R.id.txtGridCellPrice);
        imgMainImage = (ImageView) view.findViewById(R.id.imgGridCellImage);
        txtProduct = (TextView) view.findViewById(R.id.txtGridCellProduct);
        txtName = (TextView) view.findViewById(R.id.txtGridCellName);
        txtMainAttribute = (TextView) view.findViewById(R.id.txtGridCellMainAttribute);
    }
}
