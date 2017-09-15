package llc.net.mydutyfree.newmdf;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.response.Product;
import llc.net.mydutyfree.utils.FontUtils;
import llc.net.mydutyfree.utils.SlashStrikedTextView;
import llc.net.mydutyfree.utils.Utils;

/**
 * Created by gorf on 2/16/16.
 */
public class GridElementAdapter extends RecyclerView.Adapter<GridElementAdapter.SimpleViewHolder> {// implements AdapterView.OnItemClickListener{

    private Context context;
    private List<String> elements;
    private List<Product> mList;
    private Activity mActivity;
    public MyAdapterItemClick mListener;

    public static interface MyAdapterItemClick {
        public void onItemClick(View view, int position);

    }

    public GridElementAdapter(Activity activity, Context context, List<Product> list, MyAdapterItemClick listener) {
        this.context = context;
        mListener = listener;
        mActivity = activity;
        this.elements = new ArrayList<String>();
        mList = list;
        // Fill dummy list
        for (int i = 0; i < 40; i++) {
            this.elements.add(i, "Position : " + i);
        }
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(context, String.format("%d", position), Toast.LENGTH_SHORT).show();
//    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imgMainImage;
        public SlashStrikedTextView txtPriceOld;
        public TextView txtPrice;
        public TextView txtProduct;
        public TextView txtName;
        public TextView txtMainAttribute;
        RelativeLayout root;
        public MyViewHolderClicks mListener;

        public SimpleViewHolder(View view, MyViewHolderClicks listener) {
            super(view);
            mListener = listener;
            view.setOnClickListener(this);
            txtPriceOld = (SlashStrikedTextView) view.findViewById(R.id.txtGridCellPriceOld);
//            txtPrice.setLayoutParams(txtPrice.getLayoutParams());
            txtPrice = (TextView) view.findViewById(R.id.txtGridCellPrice);
            imgMainImage = (ImageView) view.findViewById(R.id.imgGridCellImage);
            txtProduct = (TextView) view.findViewById(R.id.txtGridCellProduct);
//            txtProduct.setLayoutParams(txtProduct.getLayoutParams());
            txtName = (TextView) view.findViewById(R.id.txtGridCellName);
//            txtName.setLayoutParams(txtName.getLayoutParams());
            txtMainAttribute = (TextView) view.findViewById(R.id.txtGridCellMainAttribute);
//            txtMainAttribute.setLayoutParams(txtMainAttribute.getLayoutParams());

        }

        @Override
        public void onClick(View v) {
            mListener.onPotato(v, getAdapterPosition());
        }

        public static interface MyViewHolderClicks {
            public void onPotato(View caller, int position);

        }
    }

    public void setList(List<Product> mList) {
        this.mList = mList;
        this.notifyDataSetChanged();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        RelativeLayout view = (RelativeLayout)LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_cell, parent, false);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_cell, parent, false);

        view.getLayoutParams ().width = (parent.getWidth () / 2) - (int)Utils.dpToPx(1);
        WindowManager windowManager = (WindowManager)parent.getContext().getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displaymetrics);
        int heightInPixels = displaymetrics.heightPixels;
        int widthInPixels = displaymetrics.widthPixels;

//        view.setLayoutParams(new StaggeredGridLayoutManager.LayoutParams(widthInPixels, RecyclerView.LayoutParams.MATCH_PARENT));

//        view.setMinimumWidth(350);
        SimpleViewHolder simpleHolder = new SimpleViewHolder(view, new SimpleViewHolder.MyViewHolderClicks() {
            @Override
            public void onPotato(View caller, int position) {
                mListener.onItemClick(caller, position);
//                Toast.makeText(context, String.format("%d", position), Toast.LENGTH_SHORT).show();
            }
        });
        return simpleHolder;
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        if (mList != null)
        {
            Product item = mList.get(position);
            String currentCurrency = ((MDFApplication)mActivity.getApplication()).getCurrentCurrency();
            String currencySymbol = ((MDFApplication)(mActivity.getApplication())).getCurrencySymbols().get(currentCurrency);
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            holder.txtName.setText(mList.get(position).getName());
            holder.txtProduct.setText(mList.get(position).getBrandName());
            holder.txtMainAttribute.setText(mList.get(position).getMainAttribute());
            String priceString = decimalFormat.format(mList.get(position).getPrice().get(currentCurrency));
            priceString = new StringBuilder().append(priceString).append(" ").append(currencySymbol).toString();
            holder.txtPrice.setText(priceString);
            String priceOldString = decimalFormat.format(mList.get(position).getPriceOld().get(currentCurrency));
            priceOldString = new StringBuilder().append(priceOldString).append(" ").append(currencySymbol).toString();
            holder.txtPriceOld.setText(priceOldString);
            FontUtils.setNormalFont(context, holder.txtPriceOld);
            FontUtils.setNormalFont(context, holder.txtProduct);
            FontUtils.setBoldFont(context, holder.txtPrice);
            FontUtils.setBoldFont(context, holder.txtName);
            FontUtils.setBoldFont(context, holder.txtMainAttribute);
            Picasso.with(mActivity.getBaseContext())
                    .load(Uri.encode(mList.get(position).getImages().get(0), "@#&=*+-_.,:!?()/~'%"))
                    .placeholder(R.drawable.missing_product)
                    .error(R.drawable.missing_product)
                    .into(holder.imgMainImage);
        }
//        holder.button.setText(elements.get(position));
//        holder.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "Position =" + position, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (mList != null && mList.size() > 0)
            return mList.size();
        else
            return this.elements.size();
    }

}