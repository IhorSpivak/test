package llc.net.mydutyfree.adapters;

import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.interfaces.BannersAdapterInterface;
import llc.net.mydutyfree.interfaces.CategoryAdapterInterface;
import llc.net.mydutyfree.interfaces.GridWithHeaderAndFooterAdapterInterface;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.response.Banner;
import llc.net.mydutyfree.response.Product;
import llc.net.mydutyfree.utils.Utils;

/**
 * Created by gorf on 7/27/16.
 */
public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Product> mainList;
    private String currentCurrency;
    private String currentCurrencySymbol;
    private CategoryAdapterInterface categoryAdapterInterface;
    float buttonTextSize = 0;
    float textTextSize = 0;
    float recyclerViewWidth = 0;
    private boolean isUserLogIn = false;

    public CategoryAdapter(List<Product> productsList, CategoryAdapterInterface categoryAdapterInterface, String userAuthKey) {
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

        this.categoryAdapterInterface = categoryAdapterInterface;
        currentCurrency = MDFApplication.getMDFApplication().getCurrentCurrency();
        currentCurrencySymbol = MDFApplication.getMDFApplication().getCurrencySymbols().get(currentCurrency);
        mainList = productsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        recyclerViewWidth = parent.getWidth();
        GridCellViewHolderNew VH = new GridCellViewHolderNew(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_product_item, parent, false));
        VH.txtTitle.setTypeface(MDFApplication.getBoldTypeface());
        VH.txtPrice.setTypeface(MDFApplication.getBoldTypeface());
        VH.txtPriceOld.setTypeface(MDFApplication.getNormalTypeface());
        VH.txtPriceText.setTypeface(MDFApplication.getNormalTypeface());
        VH.txtPriceOldText.setTypeface(MDFApplication.getNormalTypeface());
        VH.txtPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, textTextSize);
        VH.txtPriceText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textTextSize);
        VH.txtPriceOld.setTextSize(TypedValue.COMPLEX_UNIT_SP, textTextSize);
        VH.txtPriceOldText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textTextSize);
        return VH;
    }

    public void updateAll() {
        this.notifyDataSetChanged();
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
            final Product item = mainList.get(position);

            if (isUserLogIn) {
                ((GridCellViewHolderNew)holder).imgBtnWishList.setVisibility(View.VISIBLE);
                ((GridCellViewHolderNew)holder).imgBtnWishList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        categoryAdapterInterface.onItemWishCLicked(item);
                    }
                });
                if (item.inWishlist()) {
                    ((GridCellViewHolderNew)holder).imgBtnWishList.setImageResource(R.drawable.icon_favorite_yellow_filled);
                }
                else {
                    ((GridCellViewHolderNew)holder).imgBtnWishList.setImageResource(R.drawable.icon_favorite_yellow_border);
                }
            }
            else {
                ((GridCellViewHolderNew)holder).imgBtnWishList.setOnClickListener(null);
                ((GridCellViewHolderNew)holder).imgBtnWishList.setVisibility(View.GONE);
            }

            Picasso.with(MDFApplication.getAppContext())
                    .load(Uri.encode(item.getImages().get(0), "@#&=*+-_.,:!?()/~'%"))
                    .error(R.drawable.missing_product)
                    .into(((GridCellViewHolderNew)holder).imgMainImage);

            ((GridCellViewHolderNew)holder).txtTitle.setText(item.getTitle());
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String priceString = decimalFormat.format(item.getPrice().get(currentCurrency));
            priceString = new StringBuilder().append(priceString).append(currentCurrencySymbol).toString();
            ((GridCellViewHolderNew)holder).txtPrice.setText(priceString);

        if (!item.isNoDiscount()) {
            String priceOldString = decimalFormat.format(item.getPriceOld().get(currentCurrency));
            priceOldString = new StringBuilder().append(priceOldString).append(currentCurrencySymbol.toString()).toString();
//            ((GridCellViewHolderNew) holder).txtPriceOld.setTextColor(ContextCompat.getColor(MDFApplication.getAppContext(), R.color.colorBlack));
            ((GridCellViewHolderNew) holder).txtPriceOld.setText(priceOldString);
            ((GridCellViewHolderNew) holder).txtPriceOldText.setText(MDFApplication.getAppContext().getResources().getString(R.string.price_in_airport));
        }
        else {
//            ((GridCellViewHolderNew) holder).txtPriceOld.setTextColor(ContextCompat.getColor(MDFApplication.getAppContext(), R.color.colorRed));
            ((GridCellViewHolderNew) holder).txtPriceOld.setText(MDFApplication.getAppContext().getResources().getString(R.string.no_discount));
            ((GridCellViewHolderNew) holder).txtPriceOldText.setText(null);
        }

            ((GridCellViewHolderNew)holder).view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    categoryAdapterInterface.onItemCLicked(item);
                }
            });
    }

    @Override
    public int getItemCount() {
        int count;
            count = mainList.size();
        return count;
    }
}
