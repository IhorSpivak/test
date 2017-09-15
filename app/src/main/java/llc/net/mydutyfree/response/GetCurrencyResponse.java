package llc.net.mydutyfree.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by gorf on 6/22/16.
 */
public class GetCurrencyResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private Map<String, String> data;

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
