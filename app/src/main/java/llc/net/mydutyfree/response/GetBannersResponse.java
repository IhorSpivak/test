package llc.net.mydutyfree.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gorf on 7/4/16.
 */
public class GetBannersResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private List<Banner> data;

    public List<Banner> getData() {
        return data;
    }

    public void setData(List<Banner> data) {
        this.data = data;
    }
}
