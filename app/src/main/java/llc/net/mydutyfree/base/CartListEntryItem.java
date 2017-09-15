package llc.net.mydutyfree.base;

import llc.net.mydutyfree.response.Product;

/**
 * Created by gorf on 8/9/17.
 */

public class CartListEntryItem implements CartListItem {
    public final Product item;

    public CartListEntryItem(Product item) {
        this.item = item;
    }

    @Override
    public boolean isSection() {
        return false;
    }

    public Product getItem() {
        return item;
    }
}
