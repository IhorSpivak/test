package llc.net.mydutyfree.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.response.Category;
import llc.net.mydutyfree.utils.FontUtils;
import llc.net.mydutyfree.utils.Utils;

/**
 * Created by gorf on 12/8/15.
 */
public class StoreListAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;

    public StoreListAdapter(Context context, List<String> list) {
        super();
        this.context = context;
        this.list = list;
    }

    public void setList(ArrayList list){
        this.list = list;
        this.notifyDataSetInvalidated();
        this.notifyDataSetChanged();

    }

    public StoreListAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list == null)
            return 20;
        else
            return list.size();
    }

    @Override
    public String getItem(int position) {
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
        public TextView txtStoreName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = convertView;
        ViewHolder view;
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
        {
            view = new ViewHolder();
            v = inflator.inflate(R.layout.drawer_list_item, null);
            v.setTag(view);
            view.txtStoreName = (TextView) v.findViewById(R.id.txtDrawerListItem);
        }
        else
        {
           view = (ViewHolder) v.getTag();
        }

        if (list != null)
        {
//            if ((position > 2) && (position < list.size() + 3)) {
                view.txtStoreName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//                if (list.get(position - 3).getParentID() == null) {
//                    FontUtils.setBoldFont(activity.getApplicationContext(), view.txtCategoryName);
//                    view.txtCategoryName.setPadding(
//                            (int) Utils.dpToPx(15),
//                            view.txtCategoryName.getPaddingTop(),
//                            view.txtCategoryName.getPaddingRight(),
//                            view.txtCategoryName.getPaddingBottom());
//                } else {
                    FontUtils.setNormalFont(context, view.txtStoreName);
                    view.txtStoreName.setPadding(
                            (int) Utils.dpToPx(35),
                            view.txtStoreName.getPaddingTop(),
                            view.txtStoreName.getPaddingRight(),
                            view.txtStoreName.getPaddingBottom());
                }
                view.txtStoreName.setText(list.get(position));
//            }
//            else
//            {
//                if (position == list.size() + 3) {
//                    FontUtils.setBoldFont(activity.getApplicationContext(), view.txtCategoryName);
//                    view.txtCategoryName.setPadding(
//                            (int) Utils.dpToPx(15),
//                            view.txtCategoryName.getPaddingTop(),
//                            view.txtCategoryName.getPaddingRight(),
//                            view.txtCategoryName.getPaddingBottom());
//                    view.txtCategoryName.setText(activity.getResources().getString(R.string.customer_support));
//                    view.txtCategoryName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_information, 0);
//                }
//                else if (position == 0) {
//                    FontUtils.setBoldFont(activity.getApplicationContext(), view.txtCategoryName);
//                    view.txtCategoryName.setPadding(
//                            (int) Utils.dpToPx(15),
//                            view.txtCategoryName.getPaddingTop(),
//                            view.txtCategoryName.getPaddingRight(),
//                            view.txtCategoryName.getPaddingBottom());
//                    view.txtCategoryName.setText(activity.getResources().getString(R.string.back_to_main));
//                }
//                else if (position == 1) {
//                    FontUtils.setBoldFont(activity.getApplicationContext(), view.txtCategoryName);
//                    view.txtCategoryName.setPadding(
//                            (int) Utils.dpToPx(15),
//                            view.txtCategoryName.getPaddingTop(),
//                            view.txtCategoryName.getPaddingRight(),
//                            view.txtCategoryName.getPaddingBottom());
//                    String title = activity.getResources().getString(R.string.special_offers_caps);
//                    title = title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase();
//                    view.txtCategoryName.setText(title);
//                }
//                else if (position == 2) {
//                    FontUtils.setBoldFont(activity.getApplicationContext(), view.txtCategoryName);
//                    view.txtCategoryName.setPadding(
//                            (int) Utils.dpToPx(15),
//                            view.txtCategoryName.getPaddingTop(),
//                            view.txtCategoryName.getPaddingRight(),
//                            view.txtCategoryName.getPaddingBottom());
//                    view.txtCategoryName.setText(activity.getResources().getString(R.string.top_sellers));
//                }
//            }
//        }
        return v;
    }
}
