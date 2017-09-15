package llc.net.mydutyfree.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.utils.SlashStrikedTextView;

/**
 * Created by gorf on 7/27/16.
 */
public class GridCellViewHolderNew extends RecyclerView.ViewHolder {

    public ImageView imgMainImage;
    public TextView txtPriceOld, txtPriceOldText;
    public TextView txtPrice, txtPriceText;
    public TextView txtTitle;
    public View view;
    public ImageButton imgBtnWishList;
    public LinearLayout pricesLayout;

    public GridCellViewHolderNew(View view) {
        super(view);
        this.view = view;
        txtPriceOld = (TextView) view.findViewById(R.id.txtGridCellPriceOld);
        txtPrice = (TextView) view.findViewById(R.id.txtGridCellPrice);
        txtPriceOldText = (TextView) view.findViewById(R.id.txtGridCellPriceOldText);
        txtPriceText = (TextView) view.findViewById(R.id.txtGridCellPriceText);
        imgMainImage = (ImageView) view.findViewById(R.id.imgGridCellImage);
        imgBtnWishList = (ImageButton) view.findViewById(R.id.imgBtnWishListGridCellImage);
        txtTitle = (TextView) view.findViewById(R.id.txtGridCellTitle);
        pricesLayout = (LinearLayout) view.findViewById(R.id.linearLayoutPricesGridCell);
    }
}
