package llc.net.mydutyfree.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gorf on 8/10/16.
 */
public class GetFlightsResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<Flight> data;

    public List<Flight> getData() {
        return data;
    }

    public void setData(List<Flight> data) {
        this.data = data;
    }

}
