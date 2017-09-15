package llc.net.mydutyfree.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by gorf on 2/10/16.
 */
public class BrandItem {
    private String name;
    private String ID;

    public BrandItem(String brandID, String brandName) {
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
