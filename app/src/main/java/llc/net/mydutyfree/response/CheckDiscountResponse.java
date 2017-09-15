package llc.net.mydutyfree.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gorf on 8/10/16.
 */
public class CheckDiscountResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private CheckDiscountResponseData data;

    public CheckDiscountResponseData getData() {
        return data;
    }

    public void setData(CheckDiscountResponseData data) {
        this.data = data;
    }

    public class CheckDiscountResponseData {
        @SerializedName("is_valid")
        @Expose
        private Boolean isValid;

        @SerializedName("discount")
        @Expose
        private Integer discount;

        @SerializedName("miles")
        @Expose
        private Integer miles;

        public Integer getDiscount() {
            return discount;
        }
        public Integer getMiles() {
            return miles;
        }

        public Boolean isValid() {
            return isValid;
        }
    }
}
