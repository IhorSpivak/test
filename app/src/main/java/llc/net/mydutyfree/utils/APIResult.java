package llc.net.mydutyfree.utils;

import org.json.JSONObject;

/**
 * Created by gorf on 1/13/16.
 */
public class APIResult {
    private static class Consts {
        private static String ErrorCode = "Error";
        private static String PublicMessage = "PublicMessage";
        private static String MSG_SERVER_ERROR = "\u041E\u0448\u0438\u0431\u043A\u0430\20\u0441\u0435\u0440\u0432\u0435\u0440\u0430"; //Server Error - russian
        private final static String TrID = "trId";
    }

    protected JSONObject mJSON;

    private boolean mValid;

    public boolean getValid() {
        if(this.getErrorCode() != 0)
            this.mValid = false;
        return this.mValid;
    }

    public int getErrorCode() {
        try {
            return this.mJSON.getInt(Consts.ErrorCode);
        } catch (Exception ex) {
            return -1;
        }
    }

    public String getMessage() {
        try {
            return this.mJSON.getString(Consts.PublicMessage);
        } catch (Exception ex) {
            return Consts.MSG_SERVER_ERROR;
        }
    }

    public String getTrId() {
        try {
            return this.mJSON.getString(Consts.TrID);
        } catch (Exception ex) {
            return null;
        }
    }

    public String getValue(String name) {
        try {
            return this.mJSON.getString(name);
        } catch (Exception ex) {
            return null;
        }
    }

    protected APIResult(String json) {
        this.mValid = true;
        try {
            mJSON = new JSONObject(json);
        } catch (Exception ex) {
            this.mValid = false;
        }
    }



    @Override
    public final String toString() {
        return mJSON.toString();
    }
}
