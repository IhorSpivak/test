package llc.net.mydutyfree.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gorf on 7/14/16.
 */
public class WishListAddOrRemoveProductResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private boolean data;

    public boolean getData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
