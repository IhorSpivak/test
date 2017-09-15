package llc.net.mydutyfree.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gorf on 8/10/16.
 */
public class Profile {

    //Products List Keys
    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("phone_number")
    @Expose
    private String phone;

    @SerializedName("avatar_url")
    @Expose
    private String avatar;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("user_id")
    @Expose
    private String userID;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }
}