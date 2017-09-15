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
public class Banner {
    public static final String BANNER_TYPE_PRODUCT             =  "p";
    public static final String BANNER_TYPE_CATEGORY            =  "c";
    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("link")
    @Expose
    private JsonObject link;

    private String linkID= null;
    private String linkType = null;

    public String getImage() {
        return image;
    }

    public String getLinkType() {
        if (linkType == null)
            updateLinkType();
        return linkType;
    }

    public String getLinkID() {
        if (linkID == null)
            updateLinkType();
        return linkID;
    }

    private void updateLinkType() {
        if (link.has(BANNER_TYPE_PRODUCT)) {
            linkType = BANNER_TYPE_PRODUCT;
            linkID = link.get(BANNER_TYPE_PRODUCT).getAsString();
        }
        else if (link.has(BANNER_TYPE_CATEGORY)) {
            linkType = BANNER_TYPE_CATEGORY;
            linkID = link.get(BANNER_TYPE_CATEGORY).getAsString();
        }
    }
}
