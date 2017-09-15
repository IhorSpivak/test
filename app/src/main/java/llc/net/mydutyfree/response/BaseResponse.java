package llc.net.mydutyfree.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;


/**
 * Created by gorf on 6/22/16.
 */
public class BaseResponse {
    public static final String SUCCESS = "success";
    public static final String FAILURE = "fail";

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isValid() {
        return getStatus().equalsIgnoreCase(SUCCESS);
    }
}
