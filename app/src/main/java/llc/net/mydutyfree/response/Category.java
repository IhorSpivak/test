package llc.net.mydutyfree.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gorf on 1/19/16.
 */
public class Category {
//Category List Keys
    public static final String CATEGORY_ID          =  "id";
    public static final String PARENT_ID            =  "parent_id";
    public static final String NAME                 =  "title";
    public static final String CHILDREN             =  "children";
    public static final String AVAILABLE            =  "available";

    @SerializedName(CATEGORY_ID)
    @Expose
    private String ID;

    @SerializedName(NAME)
    @Expose
    private String name;

    @SerializedName(PARENT_ID)
    @Expose
    private String parentID;

    @SerializedName(CHILDREN)
    @Expose
    private List<Category> children;

    @SerializedName(AVAILABLE)
    @Expose
    private Number available;
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getParentID() {
        return parentID;
    }

    public List<Category> getChildren() {
        return children;
    }

    public Boolean isAvailable() {
        return (available.intValue() != 0);
    }
}
