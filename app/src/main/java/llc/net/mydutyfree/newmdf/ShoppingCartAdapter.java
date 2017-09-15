package llc.net.mydutyfree.newmdf;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import llc.net.mydutyfree.base.CartListEntryItem;
import llc.net.mydutyfree.base.CartListItem;
import llc.net.mydutyfree.base.CartListSectionItem;
import llc.net.mydutyfree.base.CartSingleton;
import llc.net.mydutyfree.base.MDFApplication;
import llc.net.mydutyfree.response.Product;
import llc.net.mydutyfree.utils.Const;
import llc.net.mydutyfree.utils.FontUtils;

/**
 * Created by gorf on 12/8/15.
 */
public class ShoppingCartAdapter extends BaseAdapter {
//    private ArrayList<String> listCountry;

    public static final int CART_ITEM   	        = 0;
    public static final int FOOTER_ITEM 	        = 1;
    public static final int HEADER_SECTION_ITEM 	= 2;

    int value;
    int oldValue;

    ArrayList<ArrayList<Product>> mListOfListProds;
    ArrayList<CartListItem> cartItemsList;

    private List<Product> list;
    private Context context;

    private ShoppingCartAdapterListener mListener;

    public interface ShoppingCartAdapterListener {
        public void onItemClicked(Product item);
        public void onContinueShopingClicked();
        public void onMakeOrderClicked();
        public void onAllItemsDeleted();
    }

    public ShoppingCartAdapter(Context context, List<Product> list, ShoppingCartAdapterListener listener) {
        super();
        this.context = context;
        this.list = list;
        mListener = listener;
        updateList();
    }

    private void updateList() {
        mListOfListProds = new ArrayList<>();
        TreeMap<String, String> storesIDs = new TreeMap<>();
        for (int i = 0; i < list.size(); i++) {
            Product item = list.get(i);
            if (!storesIDs.containsKey(item.getPickUpStoreData().getId()))
                storesIDs.put(item.getPickUpStoreData().getId(), item.getPickUpStoreData().getName());
        }

        MDFApplication.getMDFApplication().setStoresMap(storesIDs);

        Log.e("!!!!!", storesIDs.toString());

        List<String> listIDs = new ArrayList<String>(storesIDs.keySet());
        Log.e("!!!!!", listIDs.toString());

        for (int j = 0; j < listIDs.size(); j++) {
            ArrayList<Product> tmpList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Product item = list.get(i);
                if (listIDs.get(j).equalsIgnoreCase(list.get(i).getPickUpStoreData().getId()))
                    tmpList.add(item);
            }
            mListOfListProds.add(tmpList);
        }
        Log.e("!!!!!", mListOfListProds.toString());

        cartItemsList = new ArrayList<CartListItem>();
        for (int i = 0; i < mListOfListProds.size(); i++) {
            cartItemsList.add(new CartListSectionItem(storesIDs.get(listIDs.get(i))));
            for (int j = 0; j < mListOfListProds.get(i).size() ; j++) {
                cartItemsList.add(new CartListEntryItem(mListOfListProds.get(i).get(j)));
            }
        }
        Log.e("!!!!!", "ShoppingCartAdapter: " + cartItemsList.toString());
    }

    public void setListener(ShoppingCartAdapterListener listener) {
        mListener = listener;
    }

    public void setList(ArrayList list){
        this.list = list;
        updateList();
        this.notifyDataSetInvalidated();
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (cartItemsList == null)
            return 20;
        else
            return cartItemsList.size() + 1;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == cartItemsList.size())
            return FOOTER_ITEM;
        else {
            if (cartItemsList.get(position).isSection())
                return HEADER_SECTION_ITEM;
            else return CART_ITEM;
        }
    }

    @Override
    public Object getItem(int position) {
        if (position == cartItemsList.size())
            return null;
        return cartItemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }


    public static class CartItemViewHolder
    {
        private ImageView imgMainImage;
        private TextView txtPrice;
        private TextView txtQuantity;
        private TextView txtProduct;
        private TextView txtName;
        private TextView txtMainAttribute;
        private ImageButton btnDelete;
        private Button btnQuantity;
    }

    public static class CartSectionHeaderViewHolder
    {
        private TextView txtTitle;
    }

    public static class CartFooterViewHolder
    {
        private TextView txtTotalAmount;
        private TextView txtTotalAmountText;
        private Button btnContinueShopping;
        private Button btnMakeOrder;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        float buttonTextSize = 0;
        float density = context.getResources().getDisplayMetrics().density;
//        Log.e("!DENSITY!", "DENSITY = " + density);

        if (density >= 4.0) {
            //xxxhdpi
//            Log.e("!DENSITY!", "xxxhdpi");
        }
        else if ((density >= 3.0) && (density < 4.0)) {
            //xxhdpi
//            Log.e("!DENSITY!", "xxhdpi");
        }
        else if ((density >= 2.0) && (density < 3.0)) {
            //xhdpi
//            Log.e("!DENSITY!", "xhdpi");
        }
        else if ((density >= 1.5) && (density < 2.0)) {
            //hdpi
//            Log.e("!DENSITY!", "hdpi");
        }
        else if ((density >= 1.0) && (density < 1.5)) {
            //mdpi
//            Log.e("!DENSITY!", "mdpi");
        }
        else if (density < 1.0) {
            //ldpi
//            Log.e("!DENSITY!", "ldpi");
        }

        if (density <= 1.5f)
            buttonTextSize = 10;
        else if ((density > 1.5f) && (density < 3.0f))
            buttonTextSize = 13;
        else if (density >= 3.0f)
            buttonTextSize = 12;

        View v = convertView;
        int type = getItemViewType(position);

        CartItemViewHolder cartItemHolder = null;
        CartFooterViewHolder cartFooterHolder = null;
        CartSectionHeaderViewHolder cartSectionHeaderHolder = null;

        if(v == null)
        {
            if (type == CART_ITEM)
            {
                cartItemHolder = new CartItemViewHolder();
                v = LayoutInflater.from(context).inflate(R.layout.cart_cell, null);
                cartItemHolder.txtPrice = (TextView) v.findViewById(R.id.txtPriceCartCell);
                cartItemHolder.txtQuantity = (TextView) v.findViewById(R.id.txtQuantityCartCell);
                cartItemHolder.imgMainImage = (ImageView) v.findViewById(R.id.imgImageCartCell);
                cartItemHolder.txtProduct = (TextView) v.findViewById(R.id.txtProductCartCell);
                cartItemHolder.txtName = (TextView) v.findViewById(R.id.txtNameCartCell);
                cartItemHolder.txtMainAttribute = (TextView) v.findViewById(R.id.txtMainAttributeCartCell);
                cartItemHolder.btnDelete = (ImageButton) v.findViewById(R.id.btnDeleteItemCartCell);
                cartItemHolder.btnQuantity = (Button) v.findViewById(R.id.btnChangeQuantityCartCell);
                v.setTag(cartItemHolder);
            }
            else if (type == FOOTER_ITEM)
            {
                cartFooterHolder = new CartFooterViewHolder();
                v = LayoutInflater.from(context).inflate(R.layout.cart_footer, null);
                cartFooterHolder.txtTotalAmount = (TextView) v.findViewById(R.id.txtTotalAmountCartFooter);
                cartFooterHolder.txtTotalAmountText = (TextView) v.findViewById(R.id.txtTotalAmountTextCartFooter);
                cartFooterHolder.btnContinueShopping = (Button) v.findViewById(R.id.btnContinueShoppingCartFooter);
                cartFooterHolder.btnMakeOrder = (Button) v.findViewById(R.id.btnMakeOrderCartFooter);
                v.setTag(cartFooterHolder);
            }
            else if (type == HEADER_SECTION_ITEM)
            {
                cartSectionHeaderHolder = new CartSectionHeaderViewHolder();
                v = LayoutInflater.from(context).inflate(R.layout.cart_section_header, null);
                cartSectionHeaderHolder.txtTitle = (TextView) v.findViewById(R.id.txtSectionTitleCartSectionHeader);
                v.setTag(cartSectionHeaderHolder);
            }
        }
        else
        {
            if (type == CART_ITEM)
            {
                cartItemHolder = (CartItemViewHolder) v.getTag();
            }
            else if (type == FOOTER_ITEM)
            {
                cartFooterHolder = (CartFooterViewHolder) v.getTag();
            }
            else if (type == HEADER_SECTION_ITEM)
            {
                cartSectionHeaderHolder = (CartSectionHeaderViewHolder) v.getTag();
            }
        }
        String currentCurrency = ((MDFApplication)(context.getApplicationContext())).getCurrentCurrency();
        String currencySymbol = ((MDFApplication)(context.getApplicationContext())).getCurrencySymbols().get(currentCurrency);
        if (type == CART_ITEM)
        {
            FontUtils.setBoldFont(context, cartItemHolder.txtPrice);
            FontUtils.setNormalFont(context, cartItemHolder.txtQuantity);
            FontUtils.setBoldFont(context, cartItemHolder.txtProduct);
            FontUtils.setNormalFont(context, cartItemHolder.txtName);
            FontUtils.setNormalFont(context, cartItemHolder.txtMainAttribute);
            FontUtils.setNormalFont(context, cartItemHolder.btnDelete);
            FontUtils.setNormalFont(context, cartItemHolder.btnQuantity);

            cartItemHolder.btnQuantity.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);

            final int quantityCurrent = CartSingleton.getInstance().getProductCount(((CartListEntryItem)cartItemsList.get(position)).getItem().getID());
            cartItemHolder.txtQuantity.setText(String.format("%d x", quantityCurrent));
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            cartItemHolder.txtName.setText(((CartListEntryItem)cartItemsList.get(position)).getItem().getName());
            cartItemHolder.txtProduct.setText(((CartListEntryItem)cartItemsList.get(position)).getItem().getBrandName());
            cartItemHolder.txtMainAttribute.setText(((CartListEntryItem)cartItemsList.get(position)).getItem().getMainAttribute());
            String priceString = decimalFormat.format(((CartListEntryItem)cartItemsList.get(position)).getItem().getPrice().get(currentCurrency));
            priceString = new StringBuilder().append(priceString).append(" ").append(currencySymbol).toString();
            cartItemHolder.txtPrice.setText(priceString);

            Picasso.with(context)
                    .load(Uri.encode(((CartListEntryItem)cartItemsList.get(position)).getItem().getImages().get(0), "@#&=*+-_.,:!?()/~'%"))
                    .placeholder(R.drawable.missing_product)
                    .error(R.drawable.missing_product)
                    .into(cartItemHolder.imgMainImage);

            cartItemHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String projectToken = Const.MIXPANEL_PROJECT_TOKEN;
                    MixpanelAPI mixpanel = MixpanelAPI.getInstance(context, projectToken);
                    try {
                        JSONObject properties = new JSONObject();
                        properties.put("product_name", ((CartListEntryItem)cartItemsList.get(position)).getItem().getName());
                        properties.put("product_id", ((CartListEntryItem)cartItemsList.get(position)).getItem().getID());
                        mixpanel.track("Cart Remove", properties);
                    } catch (JSONException e) {

                    }

                    CartSingleton.getInstance().RemoveProductWithID(((CartListEntryItem)cartItemsList.get(position)).getItem().getID());
                    updateList();
                    if (list.size() < 1)
                        if (mListener != null)
                            mListener.onAllItemsDeleted();
//                    list.remove(position);
                    notifyDataSetChanged();
                }
            });

            final CartItemViewHolder finalCartItemHolder = cartItemHolder;
            cartItemHolder.btnQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NumberPicker myNumberPicker = new NumberPicker(context);
                    myNumberPicker.setMaxValue(10);
                    myNumberPicker.setMinValue(1);
                    myNumberPicker.setValue(quantityCurrent);
                    if (value == 0)
                        value = quantityCurrent;
                    myNumberPicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                    oldValue = value;
                    NumberPicker.OnValueChangeListener myValChangedListener = new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                            value = newVal;
//                            finalCartItemHolder.txtQuantity.setText(String.format("%d x", newVal));
                        }
                    };

                    myNumberPicker.setOnValueChangedListener(myValChangedListener);

//                    new AlertDialog.Builder(context).setView(myNumberPicker).show();
                    int resStyleID = 0;
                    if (android.os.Build.VERSION.SDK_INT > 22)
                        resStyleID = R.style.MDFPickerDialog;

                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context, resStyleID).setView(myNumberPicker);
                    dialogBuilder.setCancelable(true);
                    dialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
//                            Toast.makeText(context, "cancelled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialogBuilder.setTitle(context.getResources().getString(R.string.quantity));
                    dialogBuilder.setPositiveButton(context.getResources().getString(R.string.ok_caps), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finalCartItemHolder.txtQuantity.setText(String.format("%d x", value));
                            CartSingleton.getInstance().ChangeProductCount(((CartListEntryItem)cartItemsList.get(position)).getItem(), value);
                            notifyDataSetChanged();
//                            Toast.makeText(context, value, Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialogBuilder.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(context, oldValue, Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog dialog = dialogBuilder.create();
                    dialog.show();
                }
            });
        }
        else if (type == FOOTER_ITEM)
        {
            FontUtils.setBoldFont(context, cartFooterHolder.txtTotalAmount);
            FontUtils.setNormalFont(context, cartFooterHolder.txtTotalAmountText);
            FontUtils.setBoldFont(context, cartFooterHolder.btnContinueShopping);
            FontUtils.setBoldFont(context, cartFooterHolder.btnMakeOrder);
            cartFooterHolder.btnContinueShopping.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);

            cartFooterHolder.btnMakeOrder.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
            cartFooterHolder.btnMakeOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String projectToken = Const.MIXPANEL_PROJECT_TOKEN;
                    MixpanelAPI mixpanel = MixpanelAPI.getInstance(context, projectToken);
                    try {
                        JSONObject properties = new JSONObject();
                        properties.put("positions", String.format("%d", CartSingleton.getInstance().getProductList().size()));
                        properties.put("quantity", String.format("%d", CartSingleton.getInstance().getProductsCount()));
                        properties.put("cost", String.format("%.2f", CartSingleton.getInstance().getTotalAmount().get("EUR")));
                        mixpanel.track("Checkout Submit order", properties);
                    } catch (JSONException e) {

                    }
                    if (mListener != null)
                        mListener.onMakeOrderClicked();
                }
            });
            cartFooterHolder.btnContinueShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null)
                        mListener.onContinueShopingClicked();
                }
            });

            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String priceString = decimalFormat.format(CartSingleton.getInstance().getTotalAmount().get(currentCurrency));
            priceString = new StringBuilder().append(priceString).append(" ").append(currencySymbol).toString();
            cartFooterHolder.txtTotalAmount.setText(priceString);
//            cartFooterHolder.txtTotalAmount = (TextView) v.findViewById(R.id.txtTotalAmountCartFooter);
//            cartFooterHolder.txtTotalAmountText = (TextView) v.findViewById(R.id.txtTotalAmountTextCartFooter);
//            cartFooterHolder.btnContinueShopping = (Button) v.findViewById(R.id.btnContinueShoppingCartFooter);
//            cartFooterHolder.btnMakeOrder = (Button) v.findViewById(R.id.btnMakeOrderCartFooter);
        }
        else if (type == HEADER_SECTION_ITEM) {
            cartSectionHeaderHolder.txtTitle.setText(((CartListSectionItem)cartItemsList.get(position)).getItem());
        }
        return v;
    }
}
