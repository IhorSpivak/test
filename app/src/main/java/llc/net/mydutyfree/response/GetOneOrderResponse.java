package llc.net.mydutyfree.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gorf on 7/4/16.
 */
public class GetOneOrderResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private Order data;

    public Order getData() {
        return data;
    }

    public void setData(Order data) {
        this.data = data;
    }
}
