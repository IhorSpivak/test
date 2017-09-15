package llc.net.mydutyfree.newmdf;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.models.BrandItem;
import llc.net.mydutyfree.response.Category;
import llc.net.mydutyfree.response.Product;
import llc.net.mydutyfree.utils.FontUtils;
import llc.net.mydutyfree.utils.Utils;

/**
 * Created by gorf on 12/8/15.
 */
public class FilterAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    public static final int BRAND_ITEM                      = 2;
    public static final int SUB_CATEGORY_ITEM               = 1;
    public static final int HEADER_ITEM 	                = 0;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mListener != null) {
            if (subCategoriesCount > 0) {
                if (position > 0 && position <= subCategoriesCount) {
                    mListener.onSubCategoryClick(mSubCategoriesList.get(position - 1));
                } else if (position > subCategoriesCount) {
                    mListener.onBrandClick(mBrandslist.get(position - (1 + subCategoriesCount)));
                }
            } else {
                if (position > 0) {
                    mListener.onBrandClick(mBrandslist.get(position - 1));
                }
            }
        }
    }

    public enum SortBy {
        DATE,
        PRICE_UP,
        PRICE_DOWN
    }

    private List<String> list;
    private Context context;
    private int brandsCount = 0;
    private int subCategoriesCount = 0;
    private float buttonTextSize = 0;
    private FilterAdapterListener mListener;
    private SortBy mSortType = SortBy.DATE;
    private List<Category> mSubCategoriesList;
    private List<BrandItem> mBrandslist;

    public interface FilterAdapterListener {
        void onBrandClick(BrandItem itemBrand);
        void onSubCategoryClick(Category subCategory);
        void onApplyClicked(String priceFrom, String priceTo, SortBy sortBy);
    }

    public FilterAdapter(Context context, ListView listView, List<Category> subCategoriesList, List<BrandItem> brandslist, FilterAdapterListener listener) {
        super();

        listView.setAdapter(this);
        listView.setOnItemClickListener(this);
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
            buttonTextSize = 11;
        else if (density >= 3.0f)
            buttonTextSize = 12;

        this.context = context;
        list = new ArrayList<String>();

        if (subCategoriesList != null) {
            subCategoriesCount = subCategoriesList.size();
            for (int i = 0; i < subCategoriesCount; i++) {
                list.add(((Category)subCategoriesList.get(i)).getName());
            }
        }
        if (brandslist != null) {
            brandsCount = brandslist.size();
            for (int i = 0; i < brandsCount; i++) {
                list.add(((BrandItem)brandslist.get(i)).getName());
            }
        }
        mListener = listener;
        mSubCategoriesList = subCategoriesList;
        mBrandslist = brandslist;
    }

    public void setListener(FilterAdapterListener listener) {
        mListener = listener;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (list == null)
            return 20;
        else
            return list.size() + 1;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        int retType = HEADER_ITEM;
        if (subCategoriesCount > 0) {
            if (position == 0) {
                retType = HEADER_ITEM;
            }
            else if (position > 0 && position <= subCategoriesCount) {
                retType = SUB_CATEGORY_ITEM;
            }
            else if (position > subCategoriesCount) {
                retType = BRAND_ITEM;
            }
        }
        else {
            if (position == 0) {
                retType = HEADER_ITEM;
            }
            else if (position > 0) {
                retType = BRAND_ITEM;
            }
        }
        return retType;
    }

    @Override
    public Product getItem(int position) {
        if (position == 0)
            return null;
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }


    public static class FilterHeaderViewHolder
    {
        private Button btnSortByDate;
        private Button btnSortByPriceUp;
        private Button btnSortByPriceDown;
        private Button btnApply;
        private EditText edtPriceFrom;
        private EditText edtPriceTo;
        private TextView txtSort;
        private TextView txtPrice;
    }

    public static class SimpleTextViewHolder
    {
        public TextView txtText;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = convertView;
        int type = getItemViewType(position);

        FilterHeaderViewHolder filterHeaderViewHolder = null;
        SimpleTextViewHolder brandNameViewHolder = null;
        SimpleTextViewHolder subCategoryNameViewHolder = null;

        if(v == null)
        {
            if (type == HEADER_ITEM)
            {
                filterHeaderViewHolder = new FilterHeaderViewHolder();
                v = LayoutInflater.from(context).inflate(R.layout.filter_header, null);
                filterHeaderViewHolder.btnSortByDate = (Button) v.findViewById(R.id.btnSortByDateFilterHeader);
                filterHeaderViewHolder.btnSortByPriceDown = (Button) v.findViewById(R.id.btnSortByPriceDownFilterHeader);
                filterHeaderViewHolder.btnSortByPriceUp = (Button) v.findViewById(R.id.btnSortByPriceUpFilterHeader);
                filterHeaderViewHolder.btnApply = (Button) v.findViewById(R.id.btnApplyFilterHeader);
                filterHeaderViewHolder.edtPriceFrom = (EditText) v.findViewById(R.id.edtPriceFromFilterHeader);
                filterHeaderViewHolder.edtPriceTo = (EditText) v.findViewById(R.id.edtPriceToFilterHeader);
                filterHeaderViewHolder.txtSort = (TextView) v.findViewById(R.id.txtSortFilterHeader);
                filterHeaderViewHolder.txtPrice = (TextView) v.findViewById(R.id.txtPriceFilterHeader);

                final FilterHeaderViewHolder finalFilterHeaderViewHolder = filterHeaderViewHolder;
                filterHeaderViewHolder.btnSortByDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSortType = SortBy.DATE;
                        v.setPressed(true);
                        finalFilterHeaderViewHolder.btnSortByDate.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed));
                        finalFilterHeaderViewHolder.btnSortByPriceUp.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTransparent));
                        finalFilterHeaderViewHolder.btnSortByPriceDown.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTransparent));
                    }
                });
                filterHeaderViewHolder.btnSortByPriceUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSortType = SortBy.PRICE_UP;
                        v.setPressed(true);
                        finalFilterHeaderViewHolder.btnSortByDate.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTransparent));
                        finalFilterHeaderViewHolder.btnSortByPriceUp.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed));
                        finalFilterHeaderViewHolder.btnSortByPriceDown.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTransparent));
                    }
                });
                filterHeaderViewHolder.btnSortByPriceDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSortType = SortBy.PRICE_DOWN;
                        v.setPressed(true);
                        finalFilterHeaderViewHolder.btnSortByDate.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTransparent));
                        finalFilterHeaderViewHolder.btnSortByPriceUp.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTransparent));
                        finalFilterHeaderViewHolder.btnSortByPriceDown.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed));
                    }
                });
                filterHeaderViewHolder.btnApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            String tmpToPrice = finalFilterHeaderViewHolder.edtPriceTo.getText().toString();
                            String tmpFromPrice = finalFilterHeaderViewHolder.edtPriceFrom.getText().toString();
                            if (tmpFromPrice.isEmpty())
                                tmpFromPrice = "0";
                            if (tmpToPrice.isEmpty())
                                tmpToPrice = "999999999";
                            mListener.onApplyClicked(
                                    tmpFromPrice,
                                    tmpToPrice,
                                    mSortType
                            );
                        }
                    }
                });


                v.setTag(filterHeaderViewHolder);
            }
            else if (type == SUB_CATEGORY_ITEM)
            {
                subCategoryNameViewHolder = new SimpleTextViewHolder();
                v = LayoutInflater.from(context).inflate(R.layout.drawer_list_item, null);
                subCategoryNameViewHolder.txtText = (TextView) v.findViewById(R.id.txtDrawerListItem);
                subCategoryNameViewHolder.txtText.setPadding(
                        (int)Utils.dpToPx(35),
                        subCategoryNameViewHolder.txtText.getPaddingTop(),
                        subCategoryNameViewHolder.txtText.getPaddingRight(),
                        subCategoryNameViewHolder.txtText.getPaddingBottom());
                v.setTag(subCategoryNameViewHolder);
            }
            else if (type == BRAND_ITEM)
            {
                brandNameViewHolder = new SimpleTextViewHolder();
                v = LayoutInflater.from(context).inflate(R.layout.drawer_list_item, null);
                brandNameViewHolder.txtText = (TextView) v.findViewById(R.id.txtDrawerListItem);
                brandNameViewHolder.txtText.setPadding((int)Utils.dpToPx(15),
                        brandNameViewHolder.txtText.getPaddingTop(),
                        brandNameViewHolder.txtText.getPaddingRight(),
                        brandNameViewHolder.txtText.getPaddingBottom());
                v.setTag(brandNameViewHolder);
            }
        }
        else
        {
            if (type == HEADER_ITEM)
            {
                filterHeaderViewHolder = (FilterHeaderViewHolder) v.getTag();
            }
            else if (type == SUB_CATEGORY_ITEM)
            {
                subCategoryNameViewHolder = (SimpleTextViewHolder) v.getTag();
            }
            else if (type == BRAND_ITEM)
            {
                brandNameViewHolder = (SimpleTextViewHolder) v.getTag();
            }
        }
        String currentCurrency = ((MDFApplication)(context.getApplicationContext())).getCurrentCurrency();
        String currencySymbol = ((MDFApplication)(context.getApplicationContext())).getCurrencySymbols().get(currentCurrency);
        if (type == HEADER_ITEM)
        {
            FontUtils.setBoldFont(context, filterHeaderViewHolder.txtPrice);
            FontUtils.setBoldFont(context, filterHeaderViewHolder.txtSort);

            FontUtils.setNormalFont(context, filterHeaderViewHolder.edtPriceFrom);
            FontUtils.setNormalFont(context, filterHeaderViewHolder.edtPriceTo);

            FontUtils.setBoldFont(context, filterHeaderViewHolder.btnApply);
            FontUtils.setBoldFont(context, filterHeaderViewHolder.btnSortByDate);
            FontUtils.setBoldFont(context, filterHeaderViewHolder.btnSortByPriceDown);
            FontUtils.setBoldFont(context, filterHeaderViewHolder.btnSortByPriceUp);

            filterHeaderViewHolder.btnApply.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
            filterHeaderViewHolder.btnSortByDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
            filterHeaderViewHolder.btnSortByPriceDown.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
            filterHeaderViewHolder.btnSortByPriceUp.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);

            if (mSortType == SortBy.DATE) {
                filterHeaderViewHolder.btnSortByDate.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed));
                filterHeaderViewHolder.btnSortByPriceUp.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTransparent));
                filterHeaderViewHolder.btnSortByPriceDown.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTransparent));
            }
            else if (mSortType == SortBy.PRICE_UP) {
                filterHeaderViewHolder.btnSortByDate.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTransparent));
                filterHeaderViewHolder.btnSortByPriceUp.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed));
                filterHeaderViewHolder.btnSortByPriceDown.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTransparent));
            }
            else if (mSortType == SortBy.PRICE_DOWN) {
                filterHeaderViewHolder.btnSortByDate.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTransparent));
                filterHeaderViewHolder.btnSortByPriceUp.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTransparent));
                filterHeaderViewHolder.btnSortByPriceDown.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed));
            }

//            final int quantityCurrent = CartSingleton.getInstance().getProductCount(list.get(position).getID());
//            cartItemHolder.txtQuantity.setText(String.format("%d x", quantityCurrent));
//            DecimalFormat decimalFormat = new DecimalFormat("#.00");
//            cartItemHolder.txtName.setText(list.get(position).getName());
//            cartItemHolder.txtProduct.setText(list.get(position).getBrandName());
//            cartItemHolder.txtMainAttribute.setText(list.get(position).getMainAttribute());
//            String priceString = decimalFormat.format(list.get(position).getPrice().get(currentCurrency));
//            priceString = new StringBuilder().append(priceString).append(" ").append(currencySymbol).toString();
//            cartItemHolder.txtPrice.setText(priceString);
//
//            Picasso.with(context)
//                    .load(list.get(position).getImages().get(0))
//                    .placeholder(R.drawable.missing_product)
//                    .error(R.drawable.missing_product)
//                    .into(cartItemHolder.imgMainImage);
//
//            cartItemHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    CartSingleton.getInstance().RemoveProductWithID(list.get(position).getID());
//                    if (list.size() < 1)
//                        if (mListener != null)
//                            mListener.onAllItemsDeleted();
////                    list.remove(position);
//                    notifyDataSetChanged();
//                }
//            });
//
//            final CartItemViewHolder finalCartItemHolder = cartItemHolder;
//            cartItemHolder.btnQuantity.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    NumberPicker myNumberPicker = new NumberPicker(context);
//                    myNumberPicker.setMaxValue(10);
//                    myNumberPicker.setMinValue(1);
//                    myNumberPicker.setValue(quantityCurrent);
//                    myNumberPicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
//                    oldValue = value;
//                    NumberPicker.OnValueChangeListener myValChangedListener = new NumberPicker.OnValueChangeListener() {
//                        @Override
//                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                            value = newVal;
////                            finalCartItemHolder.txtQuantity.setText(String.format("%d x", newVal));
//                        }
//                    };
//
//                    myNumberPicker.setOnValueChangedListener(myValChangedListener);
//
////                    new AlertDialog.Builder(context).setView(myNumberPicker).show();
//                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context).setView(myNumberPicker);
//                    dialogBuilder.setCancelable(true);
//                    dialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                        @Override
//                        public void onCancel(DialogInterface dialog) {
//                            Toast.makeText(context, "cancelled", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    dialogBuilder.setTitle("Select please");
//                    dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            finalCartItemHolder.txtQuantity.setText(String.format("%d x", value));
//                            CartSingleton.getInstance().ChangeProductCount(list.get(position), value);
//                            notifyDataSetChanged();
////                            Toast.makeText(context, value, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
////                            Toast.makeText(context, oldValue, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    AlertDialog dialog = dialogBuilder.create();
//                    dialog.show();
//                }
//            });
        }
        else if (type == SUB_CATEGORY_ITEM)
        {
            FontUtils.setNormalFont(context, subCategoryNameViewHolder.txtText);
            subCategoryNameViewHolder.txtText.setText(list.get(position - 1));
//            FontUtils.setNormalFont(context, brandNameViewHolder.txtText);
//            FontUtils.setBoldFont(context, cartFooterHolder.btnContinueShopping);
//            FontUtils.setBoldFont(context, cartFooterHolder.btnMakeOrder);
//            cartFooterHolder.btnContinueShopping.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
//            cartFooterHolder.btnMakeOrder.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
//            cartFooterHolder.btnMakeOrder.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mListener != null)
//                        mListener.onMakeOrderClicked();
//                }
//            });
//
//            DecimalFormat decimalFormat = new DecimalFormat("#.00");
//            String priceString = decimalFormat.format(CartSingleton.getInstance().getTotalAmount().get(currentCurrency));
//            priceString = new StringBuilder().append(priceString).append(" ").append(currencySymbol).toString();
//            cartFooterHolder.txtTotalAmount.setText(priceString);
////            cartFooterHolder.txtTotalAmount = (TextView) v.findViewById(R.id.txtTotalAmountCartFooter);
////            cartFooterHolder.txtTotalAmountText = (TextView) v.findViewById(R.id.txtTotalAmountTextCartFooter);
////            cartFooterHolder.btnContinueShopping = (Button) v.findViewById(R.id.btnContinueShoppingCartFooter);
////            cartFooterHolder.btnMakeOrder = (Button) v.findViewById(R.id.btnMakeOrderCartFooter);
        }
        else if (type == BRAND_ITEM) {
//            FontUtils.setBoldFont(context, brandNameViewHolder.txtText);
            FontUtils.setBoldFont(context, brandNameViewHolder.txtText);
            brandNameViewHolder.txtText.setText(list.get(position - 1));
        }

        return v;
    }
}
