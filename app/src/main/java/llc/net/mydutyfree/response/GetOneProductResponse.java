package llc.net.mydutyfree.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gorf on 7/4/16.
 */
public class GetOneProductResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private Product data;

    public Product getData() {
        return data;
    }

    public void setData(Product data) {
        this.data = data;
    }
}
