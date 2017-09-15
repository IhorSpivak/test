package llc.net.mydutyfree.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 * Created by gorf on 6/22/16.
 */
public class OrderCheckoutResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private CheckoutResponseData data;

    public CheckoutResponseData getData() {
        return data;
    }

    public class CheckoutResponseData {
        @SerializedName("id")
        @Expose
        private Integer id;

        public Integer getId() {
            return id;
        }
    }
//    private Map<String, String> data;
//
//    public Map<String, String> getData() {
//        return data;
//    }
//
//    public String getOrderID() {
//        return data.get("id");
//    }
//
//    public void setData(Map<String, String> data) {
//        this.data = data;
//    }
}
