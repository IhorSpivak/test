package llc.net.mydutyfree.interfaces;

import llc.net.mydutyfree.response.Banner;
import llc.net.mydutyfree.response.Product;

/**
 * Created by gorf on 7/27/16.
 */
public interface GridWithHeaderAndFooterAdapterInterface {
    public void onBannerClicked(Banner banner);
    public void onShowAllGoodsClicked(Boolean isNewArrivals);
    public void onItemCLicked(Product product);
    public void onItemWishCLicked(Product product);
}
