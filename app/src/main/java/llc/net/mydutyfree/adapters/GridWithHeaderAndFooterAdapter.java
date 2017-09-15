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
import llc.net.mydutyfree.interfaces.GridWithHeaderAndFooterAdapterInterface;
import llc.net.mydutyfree.newmdf.R;
import llc.net.mydutyfree.response.Banner;
import llc.net.mydutyfree.response.Product;
import llc.net.mydutyfree.utils.Utils;

/**
 * Created by gorf on 7/27/16.
 */
public class GridWithHeaderAndFooterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements BannersAdapterInterface{
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    private static final int ITEM_VIEW_TYPE_FOOTER = 2;

    private List<Product> mainList;
    private List<Product> mListSpecialOffers;
    private List<Product> mListTopSellers;
//    private List<Banner> mListBanners;
    private Boolean isSpecialOffer = true;
    private String currentCurrency;
    private String currentCurrencySymbol;
    private int currentBanner;
    private GridWithHeaderAndFooterAdapterInterface gridWithHeaderAndFooterAdapterInterface;
    float buttonTextSize = 0;
    float textTextSize = 0;
    float recyclerViewWidth = 0;
    private boolean isUserLogIn = false;

    public GridWithHeaderAndFooterAdapter(List<Product> listSpecialOffers, List<Product> listTopSellers, List<Banner> listBanners, GridWithHeaderAndFooterAdapterInterface gridWithHeaderAndFooterAdapterInterface, String userAuthKey) {
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

        this.gridWithHeaderAndFooterAdapterInterface = gridWithHeaderAndFooterAdapterInterface;
        currentCurrency = MDFApplication.getMDFApplication().getCurrentCurrency();
        currentCurrencySymbol = MDFApplication.getMDFApplication().getCurrencySymbols().get(currentCurrency);
//        mListBanners = listBanners;
        mListSpecialOffers = listSpecialOffers;
        mListTopSellers = listTopSellers;
//        this.labels = new ArrayList<String>(count);
//        for (int i = 0; i < count; ++i) {
//            labels.add(String.valueOf(i));
//        }
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    public boolean isFooter(int position) {
        return position == getItemCount() - 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        recyclerViewWidth = parent.getWidth();
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            GridHeaderViewHolder VH = new GridHeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_header_view, parent, false));
            VH.btnSpecialOffers.setTypeface(MDFApplication.getBoldTypeface());
            VH.btnTopSellers.setTypeface(MDFApplication.getBoldTypeface());
            VH.btnSpecialOffers.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
            VH.btnTopSellers.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
            return VH;
        }
        else if (viewType == ITEM_VIEW_TYPE_FOOTER) {
            GridFooterViewHolder VH = new GridFooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_footer_view, parent, false));
            VH.btnShowAllGoods.setTypeface(MDFApplication.getBoldTypeface());
            VH.btnShowAllGoods.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
            return VH;
        }
        else {
            GridCellViewHolderNew VH = new GridCellViewHolderNew(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_product_item, parent, false));
//            VH.txtTitle.setTypeface(MDFApplication.getBoldTypeface());
//            VH.imgBtnWishList.setTypeface(MDFApplication.getBoldTypeface());
//            VH.txtPrice.setTypeface(MDFApplication.getBoldTypeface());
//            VH.txtPriceOld.setTypeface(MDFApplication.getNormalTypeface());
//            VH.txtPriceText.setTypeface(MDFApplication.getNormalTypeface());
//            VH.txtPriceOldText.setTypeface(MDFApplication.getNormalTypeface());
            VH.txtPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, textTextSize);
            VH.txtPriceText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textTextSize);
            VH.txtPriceOld.setTextSize(TypedValue.COMPLEX_UNIT_SP, textTextSize);
            VH.txtPriceOldText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textTextSize);
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
//            FontUtils.setBoldFont(MDFApplication.getAppContext(), ((GridHeaderViewHolder)holder).btnNewArrivals);
//            FontUtils.setBoldFont(MDFApplication.getAppContext(), ((GridHeaderViewHolder)holder).btnTopSellers);
//            BannersAdapter pagerAdapter = new BannersAdapter(mListBanners, this);
//            ((GridHeaderViewHolder)holder).pager.setAdapter(pagerAdapter);
//            ((GridHeaderViewHolder)holder).pager.setCurrentItem(currentBanner);
//            ((GridHeaderViewHolder)holder).pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                }
//
//                @Override
//                public void onPageSelected(int position) {
//                    currentBanner = position;
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//
//                }
//            });
//            int r = ((GridHeaderViewHolder)holder).btnNewArrivals.getWidth();
            final GridHeaderViewHolder viewHolder = (GridHeaderViewHolder)holder;

//            ((GridHeaderViewHolder)holder).indicator.setViewPager(((GridHeaderViewHolder)holder).pager);

            ((GridHeaderViewHolder)holder).btnSpecialOffers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(viewHolder.btnTopSellers.getWidth(), (int)Utils.dpToPx(4));
//                    lp.setMargins(0, (int) Utils.dpToPx(3), 0, 0);
//                    lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                    viewHolder.viewRedLine.setLayoutParams(lp);


//                    float containerWidth = viewHolder.relativeLayoutRedLineContainer.getWidth();
//                    AnimationSet animationSet = new AnimationSet(true);
//                    TranslateAnimation translateAnimationBegin = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_PARENT, 0);
//                    translateAnimationBegin.setFillAfter(true);
//                    translateAnimationBegin.setDuration(1200);
//                    TranslateAnimation translateAnimation = new TranslateAnimation(0, containerWidth - view.getWidth(), 0, 0);
//                    translateAnimation.setDuration(1600);
//                    translateAnimation.setFillAfter(true);
//
//                    animationSet.addAnimation(translateAnimationBegin);
//                    animationSet.addAnimation(translateAnimation);
//                    animationSet.setFillAfter(true);
//                    viewHolder.viewRedLine.startAnimation(animationSet);

                    isSpecialOffer = true;
                    updateAll();
//                    view.setPressed(true);
//                    view.setBackgroundColor(ContextCompat.getColor(MDFApplication.getAppContext(), R.color.colorRed));
//                    viewHolder.btnTopSellers.setPressed(false);
//                    viewHolder.btnTopSellers.setBackgroundColor(ContextCompat.getColor(MDFApplication.getAppContext(), R.color.colorBlack));
                }
            });

            ((GridHeaderViewHolder)holder).btnTopSellers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(viewHolder.btnTopSellers.getWidth(), (int)Utils.dpToPx(4));
//                    lp.setMargins(0, (int) Utils.dpToPx(3), 0, 0);
//                    lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//                    viewHolder.viewRedLine.setLayoutParams(lp);


//                    float containerWidth = viewHolder.relativeLayoutRedLineContainer.getWidth();
//                    AnimationSet animationSet = new AnimationSet(true);
//                    TranslateAnimation translateAnimationBegin = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_PARENT, -(containerWidth - view.getWidth()), Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_PARENT, 0);
//                    translateAnimationBegin.setFillAfter(true);
//                    translateAnimationBegin.setDuration(1200);
//                    TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, 0);
//                    translateAnimation.setDuration(1600);
//                    translateAnimation.setFillAfter(true);
//
//                    animationSet.addAnimation(translateAnimationBegin);
//                    animationSet.addAnimation(translateAnimation);
//                    animationSet.setFillAfter(true);
//                    viewHolder.viewRedLine.startAnimation(animationSet);


                    isSpecialOffer = false;
                    updateAll();
//                    view.setPressed(true);
//                    view.setBackgroundColor(ContextCompat.getColor(MDFApplication.getAppContext(), R.color.colorRed));
//                    viewHolder.btnNewArrivals.setPressed(false);
//                    viewHolder.btnNewArrivals.setBackgroundColor(ContextCompat.getColor(MDFApplication.getAppContext(), R.color.colorBlack));

                }
            });
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(viewHolder.btnTopSellers.getWidth() == 0 ? (int)(recyclerViewWidth - (Utils.dpToPx(30))) / 2 : viewHolder.btnTopSellers.getWidth(), (int)Utils.dpToPx(4));
            lp.setMargins(0, (int) Utils.dpToPx(3), 0, 0);
            if (isSpecialOffer) {
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//                ((GridHeaderViewHolder)holder).btnNewArrivals.setPressed(true);
//                ((GridHeaderViewHolder)holder).btnNewArrivals.setBackgroundColor(ContextCompat.getColor(MDFApplication.getAppContext(), R.color.colorRed));
//                ((GridHeaderViewHolder)holder).btnTopSellers.setPressed(false);
//                ((GridHeaderViewHolder)holder).btnTopSellers.setBackgroundColor(ContextCompat.getColor(MDFApplication.getAppContext(), R.color.colorBlack));
            }
            else {
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                ((GridHeaderViewHolder)holder).btnTopSellers.setPressed(true);
//                ((GridHeaderViewHolder)holder).btnTopSellers.setBackgroundColor(ContextCompat.getColor(MDFApplication.getAppContext(), R.color.colorRed));
//                ((GridHeaderViewHolder)holder).btnNewArrivals.setPressed(false);
//                ((GridHeaderViewHolder)holder).btnNewArrivals.setBackgroundColor(ContextCompat.getColor(MDFApplication.getAppContext(), R.color.colorBlack));
            }
            viewHolder.viewRedLine.setLayoutParams(lp);

        }
        else if (isFooter(position)) {
//            FontUtils.setBoldFont(MDFApplication.getAppContext(), ((GridFooterViewHolder)holder).btnShowAllGoods);
            ((GridFooterViewHolder)holder).btnShowAllGoods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(MDFApplication.getAppContext(), "Show All", Toast.LENGTH_SHORT).show();
                    gridWithHeaderAndFooterAdapterInterface.onShowAllGoodsClicked(isSpecialOffer);
                }
            });
        }
        else {
            final Product item;
            if (isSpecialOffer)
                item = mListSpecialOffers.get(position - 1);
            else
                item = mListTopSellers.get(position - 1);

            if (isUserLogIn) {
                ((GridCellViewHolderNew)holder).imgBtnWishList.setVisibility(View.VISIBLE);
                ((GridCellViewHolderNew)holder).imgBtnWishList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridWithHeaderAndFooterAdapterInterface.onItemWishCLicked(item);
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
//                    .placeholder(R.drawable.missing_product)
                    .error(R.drawable.missing_product)
                    .into(((GridCellViewHolderNew)holder).imgMainImage);
//            FontUtils.setBoldFont(MDFApplication.getAppContext(), ((GridCellViewHolder)holder).txtName);
//            FontUtils.setBoldFont(MDFApplication.getAppContext(), ((GridCellViewHolder)holder).txtMainAttribute);
//            FontUtils.setBoldFont(MDFApplication.getAppContext(), ((GridCellViewHolder)holder).txtPrice);
//            FontUtils.setNormalFont(MDFApplication.getAppContext(), ((GridCellViewHolder)holder).txtPriceOld);
//            FontUtils.setNormalFont(MDFApplication.getAppContext(), ((GridCellViewHolder)holder).txtProduct);

            ((GridCellViewHolderNew)holder).txtTitle.setText(item.getTitle());
//            ((GridCellViewHolderNew)holder).txtMainAttribute.setText(item.getMainAttribute());
//            ((GridCellViewHolderNew)holder).txtProduct.setText(item.getBrandName());
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String priceString = decimalFormat.format(item.getPrice().get(currentCurrency));
            priceString = new StringBuilder().append(priceString).append(currentCurrencySymbol).toString();
            ((GridCellViewHolderNew)holder).txtPrice.setText(priceString);

            if (!item.isNoDiscount()) {
                String priceOldString = decimalFormat.format(item.getPriceOld().get(currentCurrency));
                priceOldString = new StringBuilder().append(priceOldString).append(currentCurrencySymbol.toString()).toString();
                ((GridCellViewHolderNew) holder).txtPriceOld.setTextColor(ContextCompat.getColor(MDFApplication.getAppContext(), R.color.colorBlack));
                ((GridCellViewHolderNew) holder).txtPriceOld.setText(priceOldString);
                ((GridCellViewHolderNew) holder).txtPriceOldText.setText(MDFApplication.getAppContext().getResources().getString(R.string.price_in_airport));
            }
            else {
                ((GridCellViewHolderNew) holder).txtPriceOld.setTextColor(ContextCompat.getColor(MDFApplication.getAppContext(), R.color.colorRed));
                ((GridCellViewHolderNew) holder).txtPriceOld.setText(MDFApplication.getAppContext().getResources().getString(R.string.no_discount));
                ((GridCellViewHolderNew) holder).txtPriceOldText.setText(null);
            }


            ((GridCellViewHolderNew)holder).view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(MDFApplication.getAppContext(), item.getID(), Toast.LENGTH_SHORT).show();
                    gridWithHeaderAndFooterAdapterInterface.onItemCLicked(item);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : (isFooter(position) ? ITEM_VIEW_TYPE_FOOTER : ITEM_VIEW_TYPE_ITEM);
    }

    @Override
    public int getItemCount() {
        int count;
        if (isSpecialOffer)
            count = mListSpecialOffers.size();
        else
            count = mListTopSellers.size();
        return count + 2;
    }

    @Override
    public void onBannerClicked(Banner banner) {
        gridWithHeaderAndFooterAdapterInterface.onBannerClicked(banner);
    }
}
