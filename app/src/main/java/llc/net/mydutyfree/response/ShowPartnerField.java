package llc.net.mydutyfree.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created  by gorf on 04/06/17.
 */
public class ShowPartnerField extends BaseResponse {

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
