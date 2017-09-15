package llc.net.mydutyfree.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by gorf on 7/4/16.
 */
public class GetStores extends BaseResponse {

    @SerializedName("data")
    @Expose
    private Map<String, Map<String, String>> data;

    public Map<String, Map<String, String>> getData() {
        return data;
    }

    public void setData(Map<String, Map<String, String>> data) {
        this.data = data;
    }
}
