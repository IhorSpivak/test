package llc.net.mydutyfree.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import llc.net.mydutyfree.base.CartSingleton;
import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.response.Order;
import llc.net.mydutyfree.response.Product;
import llc.net.mydutyfree.utils.Const;
import llc.net.mydutyfree.utils.FontUtils;

/**
 * Created by gorf on 12/8/15.
 */
public class OrdersHistoryAdapter extends BaseAdapter {

    public static final int ITEM   	= 0;

    private List<Order> list;
    private Context context;

    private OrderHistoryAdapterListener mListener;

    public interface OrderHistoryAdapterListener {
        public void onItemClicked(Order item);
    }

    public OrdersHistoryAdapter(Context context, List<Order> list, OrderHistoryAdapterListener listener) {
        super();
        this.context = context;
        this.list = list;
        mListener = listener;
    }

    public void setListener(OrderHistoryAdapterListener listener) {
        mListener = listener;
    }

    public void setList(ArrayList list){
        this.list = list;
        this.notifyDataSetInvalidated();
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list == null)
            return 20;
        else
            return list.size();
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM;
    }

    @Override
    public Order getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public static class OrderHistoryItemViewHolder
    {
        private TextView txtDate;
        private TextView txtStatus;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        float buttonTextSize = 0;
        float density = context.getResources().getDisplayMetrics().density;
        Log.e("!DENSITY!", "DENSITY = " + density);

        if (density >= 4.0) {
            //xxxhdpi
            Log.e("!DENSITY!", "xxxhdpi");
        }
        else if ((density >= 3.0) && (density < 4.0)) {
            //xxhdpi
            Log.e("!DENSITY!", "xxhdpi");
        }
        else if ((density >= 2.0) && (density < 3.0)) {
            //xhdpi
            Log.e("!DENSITY!", "xhdpi");
        }
        else if ((density >= 1.5) && (density < 2.0)) {
            //hdpi
            Log.e("!DENSITY!", "hdpi");
        }
        else if ((density >= 1.0) && (density < 1.5)) {
            //mdpi
            Log.e("!DENSITY!", "mdpi");
        }
        else if (density < 1.0) {
            //ldpi
            Log.e("!DENSITY!", "ldpi");
        }

        if (density <= 1.5f)
            buttonTextSize = 10;
        else if ((density > 1.5f) && (density < 3.0f))
            buttonTextSize = 13;
        else if (density >= 3.0f)
            buttonTextSize = 12;

        View v = convertView;
        int type = getItemViewType(position);

        OrderHistoryItemViewHolder orderItemHolder = null;

        if(v == null)
        {
            if (type == ITEM)
            {
                orderItemHolder = new OrderHistoryItemViewHolder();
                v = LayoutInflater.from(context).inflate(R.layout.order_history_cell, null);
                orderItemHolder.txtDate = (TextView) v.findViewById(R.id.txtOrderDateOrderCell);
                orderItemHolder.txtStatus = (TextView) v.findViewById(R.id.txtOrderStatusOrderCell);
                v.setTag(orderItemHolder);
            }
        }
        else
        {
            if (type == ITEM)
            {
                orderItemHolder = (OrderHistoryItemViewHolder) v.getTag();
            }
        }
        String currentCurrency = ((MDFApplication)(context.getApplicationContext())).getCurrentCurrency();
        String currencySymbol = ((MDFApplication)(context.getApplicationContext())).getCurrencySymbols().get(currentCurrency);
        if (type == ITEM)
        {
            FontUtils.setBoldFont(context, orderItemHolder.txtDate);
            FontUtils.setBoldFont(context, orderItemHolder.txtStatus);

            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            DateTime dateTime = formatter.parseDateTime(list.get(position).getDepartureDate());
            String dateString = dateTime.toString("yyyy-MM-dd");
//            edtTime.setText(dateTime.toString("HH:mm"));

            orderItemHolder.txtDate.setText(dateString + "(#" + list.get(position).getID() + ")");

            String orderStatus = list.get(position).getOrderStatus();
            if (orderStatus.equalsIgnoreCase("new"))
            {
                orderItemHolder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.colorStatusNew));
                orderItemHolder.txtStatus.setText(context.getResources().getString(R.string.order_status_new));
            }
            else if (orderStatus.equalsIgnoreCase("confirmed"))
            {
                orderItemHolder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.colorStatusStandart));
                orderItemHolder.txtStatus.setText(context.getResources().getString(R.string.order_status_confirmed));
            }
            else if (orderStatus.equalsIgnoreCase("updated"))
            {
                orderItemHolder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.colorStatusUpdated));
                orderItemHolder.txtStatus.setText(context.getResources().getString(R.string.order_status_updated));
            }
            else if (orderStatus.equalsIgnoreCase("collected"))
            {
                orderItemHolder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.colorStatusCollected));
                orderItemHolder.txtStatus.setText(context.getResources().getString(R.string.order_status_collected));
            }
            else if (orderStatus.equalsIgnoreCase("completed"))
            {
                orderItemHolder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.colorStatusCompleted));
                orderItemHolder.txtStatus.setText(context.getResources().getString(R.string.order_status_completed));
            }
            else if (orderStatus.equalsIgnoreCase("cancelled"))
            {
                orderItemHolder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.colorStatusStandart));
                orderItemHolder.txtStatus.setText(context.getResources().getString(R.string.order_status_cancelled));
            }
            else if (orderStatus.equalsIgnoreCase("slave"))
            {
                orderItemHolder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.colorStatusStandart));
                orderItemHolder.txtStatus.setText(context.getResources().getString(R.string.order_status_slave));
            }

            orderItemHolder.txtStatus.setText(list.get(position).getOrderStatus().toString  ());
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked(list.get(position));
                }
            });
        }

        return v;
    }
}
