package llc.net.mydutyfree.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by gorf on 7/14/16.
 */
public class GetStoreDiscountResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private int data;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
