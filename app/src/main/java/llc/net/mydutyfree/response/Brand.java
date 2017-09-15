package llc.net.mydutyfree.response;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by gorf on 1/19/16.
 */
public class Brand {
    private String name;
    private String ID;

    public Brand(String brandID, String brandName) {
        name = brandName;
        ID = brandID;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }
}
