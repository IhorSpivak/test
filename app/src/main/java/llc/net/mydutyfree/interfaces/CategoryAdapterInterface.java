package llc.net.mydutyfree.interfaces;

import llc.net.mydutyfree.response.Banner;
import llc.net.mydutyfree.response.Product;

/**
 * Created by gorf on 02/01/17.
 */
public interface CategoryAdapterInterface {
    public void onItemCLicked(Product product);
    public void onItemWishCLicked(Product product);
}
