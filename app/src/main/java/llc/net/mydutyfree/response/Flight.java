package llc.net.mydutyfree.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gorf on 8/10/16.
 */
public class Flight {

    //Products List Keys
    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("destination")
    @Expose
    private String destination;

    public String getDate() {
        return date;
    }

    public String getCode() {
        return code;
    }

    public String getDestination() {
        return destination;
    }

    public Flight(String date, String code, String destination) {
        this.date = date;
        this.code = code;
        this.destination = destination;
    }
}