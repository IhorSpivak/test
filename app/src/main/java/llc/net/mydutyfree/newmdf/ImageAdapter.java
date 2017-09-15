package llc.net.mydutyfree.newmdf;

import android.app.Activity;
import android.net.Uri;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.response.Product;
import llc.net.mydutyfree.utils.FontUtils;
import llc.net.mydutyfree.utils.SlashStrikedTextView;

/**
 * Created by gorf on 12/8/15.
 */
public class ImageAdapter extends BaseAdapter {
//    private ArrayList<String> listCountry;
    private List<Product> list;
    private Activity activity;
    String currentCurrency;
    String currencySymbol;

    public ImageAdapter(Activity activity, List<Product> list) {
        super();
        this.activity = activity;
        updateData();
        this.list = list;
    }

    public void setList(List list){
        updateData();
        this.list = list;
        this.notifyDataSetChanged();
        this.notifyDataSetInvalidated();
    }

    public ImageAdapter(Activity activity) {
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
            rowView = inflater.inflate(R.layout.product_item_cell, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.txtPrice = (TextView) rowView.findViewById(R.id.txtGridCellPrice);
            viewHolder.txtPriceOld = (SlashStrikedTextView) rowView.findViewById(R.id.txtGridCellPriceOld);
            viewHolder.imgMainImage = (ImageView) rowView.findViewById(R.id.imgGridCellImage);
            viewHolder.txtProduct = (TextView) rowView.findViewById(R.id.txtGridCellProduct);
            viewHolder.txtName = (TextView) rowView.findViewById(R.id.txtGridCellName);
            viewHolder.txtMainAttribute = (TextView) rowView.findViewById(R.id.txtGridCellMainAttribute);
            viewHolder.layoutPrices = (LinearLayout) rowView.findViewById(R.id.linearLayoutPricesGridCell);
            rowView.setTag(viewHolder);
        }

        final ViewHolder view = (ViewHolder) rowView.getTag();

//        view.txtPrice.setText(new String().format("00%d $", position));
//        view.txtPrice.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//        view.txtPrice.setPaintFlags(view.txtPrice.getPaintFlags() | View.TEXT_ALIGNMENT_CENTER);


        if (list != null)
        {
            FontUtils.setNormalFont(activity.getApplicationContext(), view.txtPriceOld);
            FontUtils.setNormalFont(activity.getApplicationContext(), view.txtProduct);
            FontUtils.setBoldFont(activity.getApplicationContext(), view.txtPrice);
            FontUtils.setBoldFont(activity.getApplicationContext(), view.txtName);
            FontUtils.setBoldFont(activity.getApplicationContext(), view.txtMainAttribute);
            view.txtPriceOld.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            view.txtPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            view.txtName.setText(list.get(position).getName());
            view.txtProduct.setText(list.get(position).getBrandName());
            view.txtMainAttribute.setText(list.get(position).getMainAttribute());

            String priceString = decimalFormat.format(list.get(position).getPrice().get(currentCurrency));
            priceString = new StringBuilder().append(priceString).append(" ").append(currencySymbol).toString();
            view.txtPrice.setText(priceString);

            String priceOldString = decimalFormat.format(list.get(position).getPriceOld().get(currentCurrency));
            priceOldString = new StringBuilder().append(priceOldString).append(" ").append(currencySymbol.toString()).toString();
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
