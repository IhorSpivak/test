package llc.net.mydutyfree.newmdf;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import llc.net.mydutyfree.response.Category;
import llc.net.mydutyfree.utils.FontUtils;
import llc.net.mydutyfree.utils.Utils;

/**
 * Created by gorf on 12/8/15.
 */
public class CategoriesMenuAdapter extends BaseAdapter {
//    private ArrayList<String> listCountry;
    private ArrayList<Category> list;
    private Activity activity;

    public CategoriesMenuAdapter(Activity activity, ArrayList<Category> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    public void setList(ArrayList list){
        this.list = list;
        this.notifyDataSetInvalidated();
        this.notifyDataSetChanged();

    }

    public CategoriesMenuAdapter(Activity activity) {
        super();
        this.activity = activity;
    }

    @Override
    public int getCount() {
        if (list == null)
            return 20;
        else
            return list.size() + 4;
    }

    @Override
    public Category getItem(int position) {
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


    public static class ViewHolder
    {
        public TextView txtCategoryName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = convertView;
        ViewHolder view;
        LayoutInflater inflator = activity.getLayoutInflater();

        if(convertView==null)
        {
            view = new ViewHolder();
            v = inflator.inflate(R.layout.drawer_list_item, null);
            v.setTag(view);
            view.txtCategoryName = (TextView) v.findViewById(R.id.txtDrawerListItem);
        }
        else
        {
           view = (ViewHolder) v.getTag();
        }

//        FontUtils.setNormalFont(activity.getApplicationContext(), view.txtProduct);
//        FontUtils.setBoldFont(activity.getApplicationContext(), view.txtSpecialPrice);
//        FontUtils.setBoldFont(activity.getApplicationContext(), view.txtName);
//        FontUtils.setBoldFont(activity.getApplicationContext(), view.txtMainAttribute);

        if (list != null)
        {
            if ((position > 2) && (position < list.size() + 3)) {
                view.txtCategoryName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                if (list.get(position - 3).getParentID() == null) {
                    FontUtils.setBoldFont(activity.getApplicationContext(), view.txtCategoryName);
                    view.txtCategoryName.setPadding(
                            (int) Utils.dpToPx(15),
                            view.txtCategoryName.getPaddingTop(),
                            view.txtCategoryName.getPaddingRight(),
                            view.txtCategoryName.getPaddingBottom());
                } else {
                    FontUtils.setNormalFont(activity.getApplicationContext(), view.txtCategoryName);
                    view.txtCategoryName.setPadding(
                            (int) Utils.dpToPx(35),
                            view.txtCategoryName.getPaddingTop(),
                            view.txtCategoryName.getPaddingRight(),
                            view.txtCategoryName.getPaddingBottom());
                }
                view.txtCategoryName.setText(list.get(position - 3).getName());
            }
            else
            {
                if (position == list.size() + 3) {
                    FontUtils.setBoldFont(activity.getApplicationContext(), view.txtCategoryName);
                    view.txtCategoryName.setPadding(
                            (int) Utils.dpToPx(15),
                            view.txtCategoryName.getPaddingTop(),
                            view.txtCategoryName.getPaddingRight(),
                            view.txtCategoryName.getPaddingBottom());
                    view.txtCategoryName.setText(activity.getResources().getString(R.string.customer_support));
//                    view.txtCategoryName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_information, 0);
                }
                else if (position == 0) {
                    FontUtils.setBoldFont(activity.getApplicationContext(), view.txtCategoryName);
                    view.txtCategoryName.setPadding(
                            (int) Utils.dpToPx(15),
                            view.txtCategoryName.getPaddingTop(),
                            view.txtCategoryName.getPaddingRight(),
                            view.txtCategoryName.getPaddingBottom());
                    view.txtCategoryName.setText(activity.getResources().getString(R.string.back_to_main));
//                    view.txtCategoryName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_information, 0);
                }
                else if (position == 1) {
                    FontUtils.setBoldFont(activity.getApplicationContext(), view.txtCategoryName);
                    view.txtCategoryName.setPadding(
                            (int) Utils.dpToPx(15),
                            view.txtCategoryName.getPaddingTop(),
                            view.txtCategoryName.getPaddingRight(),
                            view.txtCategoryName.getPaddingBottom());
                    String title = activity.getResources().getString(R.string.special_offers_caps);
                    title = title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase();
                    view.txtCategoryName.setText(title);
//                    view.txtCategoryName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_information, 0);
                }
                else if (position == 2) {
                    FontUtils.setBoldFont(activity.getApplicationContext(), view.txtCategoryName);
                    view.txtCategoryName.setPadding(
                            (int) Utils.dpToPx(15),
                            view.txtCategoryName.getPaddingTop(),
                            view.txtCategoryName.getPaddingRight(),
                            view.txtCategoryName.getPaddingBottom());
                    view.txtCategoryName.setText(activity.getResources().getString(R.string.top_sellers));
//                    view.txtCategoryName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_information, 0);
                }
//                else if (position == 3) {
//                    FontUtils.setBoldFont(activity.getApplicationContext(), view.txtCategoryName);
//                    view.txtCategoryName.setPadding(
//                            (int) Utils.dpToPx(15),
//                            view.txtCategoryName.getPaddingTop(),
//                            view.txtCategoryName.getPaddingRight(),
//                            view.txtCategoryName.getPaddingBottom());
//                    view.txtCategoryName.setText("ТЕСТО");
////                    view.txtCategoryName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_information, 0);
//                }
            }
//            DecimalFormat decimalFormat = new DecimalFormat("#.##");
//            view.txtName.setText(list.get(position).getName());
//            view.txtProduct.setText(list.get(position).getBrandName());
//            view.txtMainAttribute.setText(list.get(position).getMainAttribute());
//            String priceString = decimalFormat.format(list.get(position).getPrice().get("USD"));
//            priceString = new StringBuilder().append(priceString).append(" $").toString();
//            view.txtPrice.setText(priceString);
//            String specialPriceString = decimalFormat.format(list.get(position).getSpecialPrice().get("USD"));
//            specialPriceString = new StringBuilder().append(specialPriceString).append(" $").toString();
//            view.txtSpecialPrice.setText(specialPriceString);
//
//            Picasso.with(activity.getBaseContext())
//                    .load(list.get(position).getImages().get(0))
//                    .placeholder(R.drawable.missing_product)
//                    .error(R.drawable.missing_product)
//                    .into(view.imgMainImage);
        }
//        view.txtViewTitle.setText(listCountry.get(position));
//        view.imgViewFlag.setImageResource(listFlag.get(position));
//        view.txtViewTitle.setPaintFlags(view.txtViewTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        return v;
    }
}
