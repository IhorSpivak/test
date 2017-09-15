package llc.net.mydutyfree.newmdf;

import android.app.Activity;
import android.net.Uri;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import llc.net.mydutyfree.adapters.GridCellViewHolderNew;
import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.response.Product;
import llc.net.mydutyfree.utils.FontUtils;
import llc.net.mydutyfree.utils.SlashStrikedTextView;

/**
 * Created by gorf on 12/8/15.
 */
public class NewImageAdapter extends BaseAdapter {
    private List<Product> list;
    private Activity activity;
    String currentCurrency;
    String currencySymbol;
    float buttonTextSize = 0;
    float textTextSize = 0;
    private boolean isUserLogIn = false;

    public NewImageAdapter(Activity activity, List<Product> list, String userAuthKey) {
        super();
        this.activity = activity;
        if (userAuthKey.length() > 10)
            isUserLogIn = true;
        else
            isUserLogIn = false;
        float density = MDFApplication.getAppContext().getResources().getDisplayMetrics().density;
        boolean isTablet = MDFApplication.getAppContext().getResources().getBoolean(R.bool.isTablet);

        if (isTablet) {
            buttonTextSize = 14;
            textTextSize = 15;
        }
        else {
            if (density <= 1.5f) {
                buttonTextSize = 12;
                textTextSize = 10;
            }
            else if ((density > 1.5f) && (density < 3.0f)) {
                buttonTextSize = 12;
                textTextSize = 11;
            }
            else if (density >= 3.0f) {
                buttonTextSize = 12;
                textTextSize = 11;
            }
        }
        updateData();
        this.list = list;
    }

    public void setList(List list){
        updateData();
        this.list = list;
        this.notifyDataSetChanged();
        this.notifyDataSetInvalidated();
    }

    public NewImageAdapter(Activity activity) {
        super();
        this.activity = activity;
        updateData();
    }

    private void updateData() {
        currentCurrency = ((MDFApplication)(activity.getApplication())).getCurrentCurrency();
        currencySymbol = ((MDFApplication)(activity.getApplication())).getCurrencySymbols().get(currentCurrency);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (list == null)
            return 20;
        else
            return list.size();
    }

    @Override
    public Product getItem(int position) {
        // TODO Auto-generated method stub
        if (list == null)
            return null;
        else
            return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }


    static class ViewHolder
    {
        public ImageView imgMainImage;
        public SlashStrikedTextView txtPriceOld;
        public TextView txtPrice;
        public TextView txtProduct;
        public TextView txtName;
        public TextView txtMainAttribute;
        public LinearLayout layoutPrices;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View rowView = convertView;

        if(convertView==null)
        {
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.new_product_item, parent, false);

            GridCellViewHolderNew viewHolder = new GridCellViewHolderNew(rowView);
//            viewHolder.txtPrice = (TextView) rowView.findViewById(R.id.txtGridCellPrice);
//            viewHolder.txtPriceOld = (TextView) rowView.findViewById(R.id.txtGridCellPriceOld);
//            viewHolder.imgMainImage = (ImageView) rowView.findViewById(R.id.imgGridCellImage);
//            viewHolder.txtProduct = (TextView) rowView.findViewById(R.id.txtGridCellProduct);
//            viewHolder.txtName = (TextView) rowView.findViewById(R.id.txtGridCellName);
//            viewHolder.txtMainAttribute = (TextView) rowView.findViewById(R.id.txtGridCellMainAttribute);
//            viewHolder.layoutPrices = (LinearLayout) rowView.findViewById(R.id.linearLayoutPricesGridCell);
            viewHolder.txtPriceOld = (TextView) rowView.findViewById(R.id.txtGridCellPriceOld);
            viewHolder.txtPrice = (TextView) rowView.findViewById(R.id.txtGridCellPrice);
            viewHolder.txtPriceOldText = (TextView) rowView.findViewById(R.id.txtGridCellPriceOldText);
            viewHolder.txtPriceText = (TextView) rowView.findViewById(R.id.txtGridCellPriceText);
            viewHolder.imgMainImage = (ImageView) rowView.findViewById(R.id.imgGridCellImage);
            viewHolder.imgBtnWishList = (ImageButton) rowView.findViewById(R.id.imgBtnWishListGridCellImage);
            viewHolder.txtTitle = (TextView) rowView.findViewById(R.id.txtGridCellTitle);
            viewHolder.pricesLayout = (LinearLayout) rowView.findViewById(R.id.linearLayoutPricesGridCell);
            rowView.setTag(viewHolder);
        }

        final GridCellViewHolderNew view = (GridCellViewHolderNew) rowView.getTag();

//        view.txtPrice.setText(new String().format("00%d $", position));
//        view.txtPrice.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//        view.txtPrice.setPaintFlags(view.txtPrice.getPaintFlags() | View.TEXT_ALIGNMENT_CENTER);


        if (list != null)
        {
            view.txtTitle.setTypeface(MDFApplication.getBoldTypeface());
//            VH.imgBtnWishList.setTypeface(MDFApplication.getBoldTypeface());
            view.txtPrice.setTypeface(MDFApplication.getBoldTypeface());
            view.txtPriceOld.setTypeface(MDFApplication.getNormalTypeface());
            view.txtPriceText.setTypeface(MDFApplication.getNormalTypeface());
            view.txtPriceOldText.setTypeface(MDFApplication.getNormalTypeface());
            view.txtPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, textTextSize);
            view.txtPriceText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textTextSize);
            view.txtPriceOld.setTextSize(TypedValue.COMPLEX_UNIT_SP, textTextSize);
            view.txtPriceOldText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textTextSize);

            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            view.txtTitle.setText(list.get(position).getTitle());

            String priceString = decimalFormat.format(list.get(position).getPrice().get(currentCurrency));
            priceString = new StringBuilder().append(priceString).append(currencySymbol).toString();
            view.txtPrice.setText(priceString);

            String priceOldString = decimalFormat.format(list.get(position).getPriceOld().get(currentCurrency));
            priceOldString = new StringBuilder().append(priceOldString).append(currencySymbol.toString()).toString();
            view.txtPriceOld.setText(priceOldString);

            Picasso.with(activity.getBaseContext())
                    .load(Uri.encode(list.get(position).getImages().get(0), "@#&=*+-_.,:!?()/~'%"))
//                    .placeholder(R.drawable.missing_product)
                    .error(R.drawable.missing_product)
                    .into(view.imgMainImage);
        }
//        view.txtViewTitle.setText(listCountry.get(position));
//        view.imgViewFlag.setImageResource(listFlag.get(position));
//        view.txtViewTitle.setPaintFlags(view.txtViewTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        return rowView;
    }
}
