package llc.net.mydutyfree.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gorf on 01/03/17.
 */
public class ProfileGetAttributes extends BaseResponse {

    @SerializedName("data")
    @Expose
    private Profile data;

    public Profile getData() {
        return data;
    }

    public void setData(Profile data) {
        this.data = data;
    }
}
