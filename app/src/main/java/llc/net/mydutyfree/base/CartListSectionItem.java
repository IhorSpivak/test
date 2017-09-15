package llc.net.mydutyfree.base;

/**
 * Created by gorf on 8/9/17.
 */

public class CartListSectionItem implements CartListItem {
    private final String title;

    public CartListSectionItem(String title) {
        this.title = title;
    }

    @Override
    public boolean isSection() {
        return true;
    }

    public String getItem() {
        return title;
    }
}
