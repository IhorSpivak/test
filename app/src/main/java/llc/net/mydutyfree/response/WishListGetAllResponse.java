package llc.net.mydutyfree.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by gorf on 7/4/16.
 */
public class WishListGetAllResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private Map<String, List<Product>> data;

    public Map<String, List<Product>> getData() {
        return data;
    }

    public void setData(Map<String, List<Product>> data) {
        this.data = data;
    }

    public List<Product> getPresentProducts() {
        return data.get("present");
    }

    public List<Product> getAbsentProducts() {
        return data.get("absent");
    }
}
