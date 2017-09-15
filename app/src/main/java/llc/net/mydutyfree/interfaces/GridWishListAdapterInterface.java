package llc.net.mydutyfree.interfaces;

import llc.net.mydutyfree.response.Banner;
import llc.net.mydutyfree.response.Product;

/**
 * Created by gorf on 7/27/16.
 */
public interface GridWishListAdapterInterface {
    public void onItemCLicked(Product product);
    public void onItemFavoriteButtonCLicked(Product product);
}
