package llc.net.mydutyfree.adapters;

import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.interfaces.GridWishListAdapterInterface;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.response.Banner;
import llc.net.mydutyfree.response.Product;
import llc.net.mydutyfree.utils.Utils;

/**
 * Created by gorf on 7/27/16.
 */
public class GridWishListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>// implements BannersAdapterInterface
{
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;

    private List<Product> mainList;
    private List<Product> mListPresent;
    private List<Product> mListAbsent;
    private Boolean isSpecialOffer = true;
    private String currentCurrency;
    private String currentCurrencySymbol;
    private int currentBanner;
    private GridWishListAdapterInterface gridWishListAdapterInterface;
    float buttonTextSize = 0;
    float recyclerViewWidth = 0;

    public GridWishListAdapter(List<Product> listPresent, List<Product> listAbsent, GridWishListAdapterInterface gridWishListAdapterInterface) {
        float density = MDFApplication.getAppContext().getResources().getDisplayMetrics().density;
        if (density <= 1.5f)
            buttonTextSize = 13;
        else if ((density > 1.5f) && (density < 3.0f))
            buttonTextSize = 13;
        else if (density >= 3.0f)
            buttonTextSize = 12;

        this.gridWishListAdapterInterface = gridWishListAdapterInterface;
        currentCurrency = MDFApplication.getMDFApplication().getCurrentCurrency();
        currentCurrencySymbol = MDFApplication.getMDFApplication().getCurrencySymbols().get(currentCurrency);
        mListPresent = listPresent;
        mListAbsent = listAbsent;
//        this.labels = new ArrayList<String>(count);
//        for (int i = 0; i < count; ++i) {
//            labels.add(String.valueOf(i));
//        }
    }

    public boolean isHeader(int position) {
        if (mListPresent.size() > 0 && mListAbsent.size() > 0) {
            if (position == 0 || position == (mListPresent.size() + 1))
                return true;
        }
        else if ( (mListPresent.size() > 0 && mListAbsent.size() == 0) || (mListPresent.size() == 0 && mListAbsent.size() > 0) ) {
            if (position == 0)
                return true;
        }
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        recyclerViewWidth = parent.getWidth();
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            WishListHeaderViewHolder VH = new WishListHeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_header, parent, false));
            VH.txtHeader.setTypeface(MDFApplication.getBoldTypeface());
            VH.txtHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
            return VH;
        }
        else {
            GridCellViewHolderNew VH = new GridCellViewHolderNew(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_product_item, parent, false));
            VH.txtTitle.setTypeface(MDFApplication.getBoldTypeface());
//            VH.txtMainAttribute.setTypeface(MDFApplication.getBoldTypeface());
            VH.txtPrice.setTypeface(MDFApplication.getBoldTypeface());
            VH.txtPriceOld.setTypeface(MDFApplication.getNormalTypeface());
//            VH.txtProduct.setTypeface(MDFApplication.getNormalTypeface());
            return VH;
        }
    }

    public void updateAll() {
        this.notifyDataSetChanged();
//        this.notifyItemRangeChanged(1, this.getItemCount() - 1);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeader(position)) {
            if (mListPresent.size() > 0 && mListAbsent.size() > 0) {
                if (position == 0) ((WishListHeaderViewHolder)holder).txtHeader.setText(MDFApplication.getAppContext().getResources().getString(R.string.available));
                if (position == (mListPresent.size() + 1)) ((WishListHeaderViewHolder)holder).txtHeader.setText(MDFApplication.getAppContext().getResources().getString(R.string.inavailable));
            }
            else if (mListPresent.size() > 0 && mListAbsent.size() == 0) {
                ((WishListHeaderViewHolder)holder).txtHeader.setText(MDFApplication.getAppContext().getResources().getString(R.string.available));
            }
            else if (mListPresent.size() == 0 && mListAbsent.size() > 0) {
                ((WishListHeaderViewHolder)holder).txtHeader.setText(MDFApplication.getAppContext().getResources().getString(R.string.inavailable));
            }
        }
        else {
            Product item = null;
            Boolean isPresent = null;
            if (mListPresent.size() > 0 && mListAbsent.size() > 0) {
                if (position > 0 && position < mListPresent.size() + 1) {
                    item = mListPresent.get(position - 1);
                    isPresent = true;
                }
                if (position > mListPresent.size() + 1) {
                    item = mListAbsent.get(position - 2 - mListPresent.size());
                    isPresent = false;
                }
            }
            else if (mListPresent.size() > 0 && mListAbsent.size() == 0) {
                item = mListPresent.get(position - 1);
                isPresent = true;
            }
            else if (mListPresent.size() == 0 && mListAbsent.size() > 0) {
                item = mListAbsent.get(position - 1);
                isPresent = false;
            }

            final Product finalItem = item;
            final Boolean finalIsPresent = isPresent;
            ((GridCellViewHolderNew)holder).imgBtnWishList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalIsPresent)
                        mListPresent.remove(finalItem);
                    else
                        mListAbsent.remove(finalItem);
                    gridWishListAdapterInterface.onItemFavoriteButtonCLicked(finalItem);
                }
            });
            Picasso.with(MDFApplication.getAppContext())
                    .load(Uri.encode(item.getImages().get(0), "@#&=*+-_.,:!?()/~'%"))
                    .error(R.drawable.missing_product)
                    .into(((GridCellViewHolderNew)holder).imgMainImage);

            ((GridCellViewHolderNew)holder).txtTitle.setText(item.getTitle());
            if (item.getPrice() != null && item.getPriceOld() != null) {
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String priceString = decimalFormat.format(item.getPrice().get(currentCurrency));
                priceString = new StringBuilder().append(priceString).append(" ").append(currentCurrencySymbol).toString();
                ((GridCellViewHolderNew) holder).txtPrice.setText(priceString);

                String priceOldString = decimalFormat.format(item.getPriceOld().get(currentCurrency));
                priceOldString = new StringBuilder().append(priceOldString).append(" ").append(currentCurrencySymbol.toString()).toString();
                ((GridCellViewHolderNew) holder).txtPriceOld.setText(priceOldString);
            }
            else {
                ((GridCellViewHolderNew) holder).pricesLayout.setVisibility(View.GONE);
            }
            Log.e("item", finalIsPresent.toString());
            if (finalIsPresent) {
                ((GridCellViewHolderNew) holder).view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gridWishListAdapterInterface.onItemCLicked(finalItem);
                    }
                });
            }
            else {
                ((GridCellViewHolderNew) holder).view.setOnClickListener(null);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mListPresent.size() > 0 && mListAbsent.size() > 0) {
            count = mListPresent.size() + mListAbsent.size() + 2;
        }
        else if (mListPresent.size() > 0 && mListAbsent.size() == 0) {
            count = mListPresent.size() + 1;
        }
        else if (mListPresent.size() == 0 && mListAbsent.size() > 0) {
            count = mListAbsent.size() + 1;
        }
        return count;
    }

}
