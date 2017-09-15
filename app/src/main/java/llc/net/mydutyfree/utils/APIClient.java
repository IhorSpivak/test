package llc.net.mydutyfree.utils;

import android.content.Context;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
//import org.apache.http.entity.mime.HttpMultipart;
//import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.http.*;

import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * Created by gorf on 1/13/16.
 */
public class APIClient {
//
//    public static final class HistoryPeriod {
//
//    }
//        private String name;
//        private int value;
//
//        public static final HistoryPeriod Today = new HistoryPeriod(1, "today");
//        public static final HistoryPeriod CurrentWeek = new HistoryPeriod(2, "current-week");
//        public static final HistoryPeriod CurrentMonth = new HistoryPeriod(3, "current-month");
//        public static final HistoryPeriod Last30Days = new HistoryPeriod(4, "last-30-days");
//
//        private HistoryPeriod(int value, String name) {
//            this.name = name;
//            this.value = value;
//        }
//
//        @Override
//        public String toString() {
//            return name;
//        }
//
//        public int getValue() {
//            return value;
//        }
//    }
//
//    public static final class Consts {
//
//        private static final String Request = "request";
//        private static final String Command = "cmd";
//        private static final String Action = "action";
//
//        public static class TokenPing {
//            private static final String URL = "/tokenping.aspx";
//        }
//
//        public static final class ErrorCodes {
//            public static final int NoError = 0;
//            public static final int CommonError = 1;
//            public static final int CancelNotAvailable = 100;
//        }
//
//        private static class SendRequest {
//            private static final String Currency = "currency";
//            private static final String Email = "email";
//            private static final String Signature = "signature";
//        }
//
//        private static class HData {
//            private static final String AppId = "appId";
//            private static final String resId_AppId = "app_id";
//            private static final String DeviceId = "deviceId";
//            private static final String resId_DeviceId = "device_id";
//            private static final String DateTime = "datetime";
//            private static final String DateTimeFormat = "yyyyMMddHHmmss";
//            private static final String Language = "currentLanguage";
//            private static final String OS = "OS";
//            private static final String OSAndroid = "Android";
//            private static final String OSVersion = "OSVersion";
//            private static final String PhoneManufacturer = "PhoneManufacturer";
//            private static final String PhoneModel = "PhoneModel";
//            private static final String BuildNumber = "BuildNumber";
//            private static final String AppFramework = "appFramework";
//            private static final String Java = "java";
//
//            private static final String Lat = "lat";
//            private static final String Lng = "lng";
//        }
//
//        private static class GetServer {
//
//            private static final String DispatchServer1 = "https://dispatch0.i-box.ru";
//            private static final String DispatchServer2 = "https://dispatch.i-box.ru";
//
//            private static final String URL = "/GetServer.aspx";
//            private static final String Email = "email";
//            private static final String Instance = "instance";
//        }
//
//        private static class Login {
//            private static final String URL = "/Login.aspx";
//            private static final String Email = "email";
//            private static final String PasswordHash = "passwordHash";
//        }
//
//        private static class Check {
//            private static final String URL = "/payment.aspx";
//            private static final String Command = "CHECK";
//            private static final String TrID = "trId";
//            private static final String Service = "service";
//            private static final String Amount = "amount";
//            private static final String Taxes = "Taxes";
//            private static final String TaxId = "ID";
//            private static final String Description = "description";
//            private static final String Image = "photo";
//            private static final String InputType = "inputType";
//
//            private static class Reader {
//                private static final String Type = "type";
//                private static final String Data = "data";
//                private static final String Args = "readerArgs";
//
//            }
//
//            private static class Card {
//                private static final String Number = "cardNumber";
//                private static final String Expiration = "cardExpiration";
//                private static final String CVV2 = "cardCVV2";
//            }
//        }
//
//        private static class Pay {
//            private static final String Command = "PAY";
//            private static final String TrID = "trId";
//            private static final String Signature = "sign";
//            private static final String ReceiptEmail = "receiptEmail";
//            private static final String ReceiptPhone = "receiptPhone";
//            private static final String URL = "/payment.aspx";
//        }
//
//        private static class Adjust {
//            private static final String Command = "ADJUST";
//            private static final String TrID = "trId";
//            private static final String Signature = "sign";
//            private static final String ReceiptEmail = "receiptEmail";
//            private static final String ReceiptPhone = "receiptPhone";
//            private static final String URL = "/payment.aspx";
//        }
//
//        private static class Schedule
//        {
//            private static final String URL = "/Schedule.aspx";
//
//            private static final String CheckCommand = "CHECK";
//            private static final String SubmitCommand = "SUBMIT";
//            private static final String ScheduleId = "scheduleId";
//
//            private static final String Description = "description";
//
//            private static final String Service = "service";
//
//            private static final String Args = "args";
//
//            private static class Reader {
//                private static final String Type = "type";
//                private static final String Data = "data";
//                private static final String Args = "readerArgs";
//
//            }
//
//            private static final String InputType = "inputType";
//            private static final String Photo = "photo";
//
//            private static final String Signature = "sign";
//            private static final String ReceiptEmail = "receiptEmail";
//            private static final String ReceiptPhone = "receiptPhone";
//        }
//
//        private static class SendReceipt {
//            private static final String URL = "/payment.aspx";
//
//            private static final String Command = "RECEIPT";
//            private static final String TrID = "trId";
//
//            private static final String ReceiptEmail = "receiptEmail";
//            private static final String ReceiptPhone = "receiptPhone";
//        }
//
//        private static class CheckCancel {
//            private static final String URL = "/payment.aspx";
//
//            private static final String Command = "CHECK-SWIPE";
//            private static final String TrID = "trId";
//
//            private static final String InputType = "inputType";
//
//            private static class Card {
//                private static final String Number = "cardNumber";
//                private static final String Expiration = "cardExp";
//                private static final String CVV2 = "cardCVV2";
//            }
//
//            private static class Reader {
//                private static final String Args = "readerArgs";
//                private static final String Type = "type";
//                private static final String Data = "data";
//            }
//        }
//
//        private static class CancelPayment {
//            private static final String URL = "/payment.aspx";
//
//            // private static final String Command = "CANCEL";
//            private static final String TrID = "trId";
//
//            // private static final String CancelPaymentType = "cancelType";
//
//            private static final String Signature = "sign";
//
//            private static final String InputType = "inputType";
//
//            private static class Card {
//                private static final String Number = "cardNumber";
//                private static final String Expiration = "cardExp";
//                private static final String CVV2 = "cardCVV2";
//            }
//
//            private static class Reader {
//                private static final String Args = "readerArgs";
//                private static final String Type = "type";
//                private static final String Data = "data";
//            }
//
//            private static final String ReceiptEmail = "receiptEmail";
//            private static final String ReceiptPhone = "receiptPhone";
//        }
//
//        private static class GetHistory {
//            private static final String URL = "/getpayment.aspx";
//
//            private static final String Command = "GETPAYMENT";
//            private static final String Period = "period";
//            private static final String PageNum = "pageNum";
//            private static final String Mode = "mode";
//            private static final String Search = "search";
//            private static final String ByDay = "by-day";
//        }
//
//        public static class Services {
//            public static final String AcceptPayment = "CARDPORT.ACCEPT-PAYMENT";
//            public static final String InventoryPayment = "CARDPORT.ACCEPT-PAYMENT";
//        }
//
//        public static class iBoxApplication {
//            public static final String URL = "/SimpleAction.aspx";
//
//            public static final String Action = "ibox-application";
//            public static final String Name = "name";
//            public static final String Company = "company";
//            public static final String Email = "email";
//            public static final String Phone = "phone";
//            public static final String Args = "args";
//        }
//
//        public static class RemindPassword {
//            public static final String URL = "/SimpleAction.aspx";
//            public static final String Action = "password-remind";
//
//            public static final String Args = "args";
//            public static final String By = "by";
//            public static final String Email = "email";
//        }
//
//        private static class EMV {
//            private static final String CommandStartTransaction = "EMV-START-TRAN";
//            private static final String CommandGetMessage       = "EMV-GET-MSG";
//            private static final String CommandSendMessage      = "EMV-SEND-MSG";
//            private static final String EMVMessage              = "emvMessage";
//            private static final String EMVTranID               = "emvTranID";
//            private static final String Type                    = "Type";
//            private static final String Params                  = "Params";
//            private static final String ATR                     = "ATR";
//            private static final String APDU                    = "APDU";
//
//
//
//            private static final String TrID = "trId";
//
//            private static final String URL = "/payment.aspx";
//        }
//
//    }
//
//    public enum PaymentInputType {
//        MANUAL(1), SWIPE(2), CHIP(3), CASH(10);
//
//        private int mValue;
//
//        private PaymentInputType(int value) {
//            mValue = value;
//        }
//
//        public int getValue() {
//            return mValue;
//        }
//
//        public static PaymentInputType fromInteger(int x) {
//            switch (x) {
//                case 1:
//                    return MANUAL;
//                case 2:
//                    return SWIPE;
//                case 3:
//                    return CHIP;
//                case 10:
//                    return CASH;
//            }
//            return null;
//        }
//    }
//
//    public enum CancelPaymentType {
//        NONE(0, ""), CANCEL(1, "REVERSE-CANCEL"), RETURN(2, "REVERSE-RETURN");
//
//        private int mValue;
//        public String mName;
//
//        private CancelPaymentType(int value, String name) {
//            mValue = value;
//            mName = name;
//        }
//
//        public int getValue() {
//            return mValue;
//        }
//
//        public static CancelPaymentType fromInteger(int x) {
//            switch (x) {
//                case 1:
//                    return CANCEL;
//                case 2:
//                    return RETURN;
//            }
//            return null;
//        }
//    }
//
////    private static void AddHardwareDataToRequest(Context c, JSONObject request) throws JSONException, NullPointerException, NameNotFoundException {
////
////        request.put(Consts.HData.AppId, Consts.HData.GetString(c, Consts.HData.resId_AppId));
////        request.put(Consts.HData.DeviceId, Consts.HData.GetString(c, Consts.HData.resId_DeviceId));
////
////        SimpleDateFormat format1 = new SimpleDateFormat(Consts.HData.DateTimeFormat);
////        String strDate = format1.format(new Date());
////        request.put(Consts.HData.DateTime, strDate); //DateFormat.format(Consts.HData.DateTimeFormat, new Date()));
////
////        request.put(Consts.HData.BuildNumber, c.getPackageManager().getPackageInfo(c.getPackageName(), 0).versionName);
////        request.put(Consts.HData.Language, c.getResources().getConfiguration().locale.getLanguage());
////        request.put(Consts.HData.OS, Consts.HData.OSAndroid);
////        request.put(Consts.HData.OSVersion, android.os.Build.VERSION.RELEASE);
////        request.put(Consts.HData.PhoneManufacturer, android.os.Build.MANUFACTURER);
////        request.put(Consts.HData.PhoneModel, android.os.Build.MODEL);
////        request.put(Consts.HData.AppFramework, Consts.HData.Java);
////
////        LocationMngr.Location location = LocationMngr.getInstance().getCurrentLocation();
////        if (location != null) {
////            request.put(Consts.HData.Lat, location.getLatitude());
////            request.put(Consts.HData.Lng, location.getLongitude());
////        }
////    }
//
//    private static String SendRequest(Context c, String url, HashMap<String, Object> parameters) {
//        String result = null;
//        try {
//            HttpPost post = new HttpPost(url);
//
//            JSONObject request = new JSONObject();
//
//            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//            Charset chars = Charset.forName("UTF-8");
//            if (parameters != null) {
//                for (HashMap.Entry<String, Object> entry : parameters.entrySet()) {
//                    Object val = entry.getValue();
//                    if (val != null) {
//                        if (val instanceof byte[]) {
//                            ByteArrayBody body = new ByteArrayBody((byte[]) val, "application/octet-stream", entry.getKey());
//                            FormBodyPart formBodyPart = new FormBodyPart(entry.getKey(), body);
//                            entity.addPart(formBodyPart);
//                        }
//                        else if (val instanceof  JSONObject) {
//                            request.put(entry.getKey(), (JSONObject)val);
//                        } else if (val instanceof JSONArray){
//                            request.put(entry.getKey(), (JSONArray)val);
//                        } else {
//                            request.put(entry.getKey(), val.toString());
//                        }
//                    }
//                }
//            }

//            // hardware data
//            AddHardwareDataToRequest(c, request);
//            // hardware data end
//
//            if (session != null) {
//                if (sessi`on.getCurrency() != "")
//                    request.put(Consts.SendRequest.Currency, session.getCurrency());
//
//                if (session.getEmail() != "")
//                    request.put(Consts.SendRequest.Email, session.getEmail());
//
//                if (!session.getToken().equals("")) {
//                    String value = Utils.md5(request.toString() + session.getToken());
//                    entity.addPart(Consts.SendRequest.Signature, new StringBody(value));
//                }
//            }
//
//            String requestStr = request.toString();
//            //Log.i("SendRequest", requestStr);
//
//            StringBody body = new StringBody(requestStr, chars);
//            FormBodyPart part = new FormBodyPart(Consts.Request, body);
//            entity.addPart(part);
//
//            post.setEntity(entity);
//
//            HttpResponse response = HttpClientFactory.getThreadSafeClient(30000).execute(post);
//            result = EntityUtils.toString(response.getEntity());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//
//    public static APILoginResult Login(Context c, String email, String password) {
//
//        APILoginResult result = null;
//        String mail = null, serverInstance = null;
//
//        if (email.contains(":")) {
//            String[] components = email.split(":");
//            mail = components[0];
//            if (components.length >= 2)
//                serverInstance = components[1];
//        } else {
//            mail = email;
//            serverInstance = "";
//        }
//
//        APIProcessingServerResult processingServerResult = GetProcessingServer(c, mail, serverInstance);
//
//        if (processingServerResult.getValid() && processingServerResult.getErrorCode() == 0) {
//            String processingServerURL = processingServerResult.getProcessingServer();
//
//            HashMap<String, Object> params = new HashMap<String, Object>();
//            params.put(Consts.Login.Email, mail);
//            params.put(Consts.Login.PasswordHash, Utils.md5(password));
//
//            String response = SendRequest(c, processingServerURL + Consts.Login.URL, params, null);
//
//            result = new APILoginResult(email, processingServerURL, response);
//        }
//        //Log.i("APILoginResult", result.toString());
//        return result;
//    }
//
//    public static APICheckResult Check(Context c, String session, double amount, int [] taxesId, String description, byte[] image) {
//        return APIClient.Check(c, session, amount, taxesId, description, PaymentInputType.CASH, null, null, null, null, image);
//    }
//
//    public static APICheckResult Check(Context c, String session, double amount, int [] taxesId, String description, String readerData, byte[] image) {
//        return APIClient.Check(c, session, amount, taxesId, description, PaymentInputType.SWIPE, readerData, null, null, null, image);
//    }
//
//    public static APICheckResult Check(Context c, String session, double amount, int [] taxesId, String description, String cardNumber, String cardExpiration, String cardCVV2, byte[] image) {
//        return APIClient.Check(c, session, amount, taxesId, description, PaymentInputType.MANUAL, null, cardNumber, cardExpiration, cardCVV2, image);
//    }
//
//    public static APICheckResult CheckEMV(Context c, String session, double amount, int [] taxesId, String description, byte[] image) {
//        return APIClient.Check(c, session, amount, taxesId, description, PaymentInputType.CHIP, null, null, null, null, image);
//    }
//
//    private static APICheckResult Check(Context c, String session, double amount, int [] taxesId, String description, PaymentInputType inputType, String readerData, String cardNumber, String cardExpiration,
//                                        String cardCVV2, final byte[] image) {
//        APICheckResult result = null;
//
//        // TODO: ALERT!!!! HARDCODED STRINGS!!!
//        String ServiceID = "CARDPORT.ACCEPT-PAYMENT";
//        String ReaderType = "MSR001"; //"SS515";
// 
//        try {
//            Session ss = new Session(session);
//
//            HashMap<String, Object> params = new HashMap<String, Object>();
//
//            params.put(Consts.Command, Consts.Check.Command);
//            params.put(Consts.Check.TrID, UUID.randomUUID().toString().toUpperCase());
//            // TODO: Hardcoded ServiceID here
//            params.put(Consts.Check.Service, ServiceID);
//
//            params.put(Consts.Check.Amount, amount);
//            if (taxesId != null && taxesId.length > 0) {
//                JSONArray jTaxes = new JSONArray();
//                for (int i = 0; i < taxesId.length; i++) {
//                    JSONObject taxId = new JSONObject();
//                    taxId.put(Consts.Check.TaxId, taxesId[i]);
//                    jTaxes.put(taxId);
//                }
//                params.put(Consts.Check.Taxes, jTaxes);
//            }
//            params.put(Consts.Check.Description, description);
//
//            if (image != null)
//                params.put(Consts.Check.Image, image);
//
//            params.put(Consts.Check.InputType, inputType.mValue);
//            switch (inputType) {
//                case CASH:
//                    break;
//                case SWIPE:
//                    JSONObject readerArgs = new JSONObject();
//                    // TODO: Hardcoded ReaderType here
//                    readerArgs.put(Consts.Check.Reader.Type, ReaderType);
//                    readerArgs.put(Consts.Check.Reader.Data, readerData);
//                    params.put(Consts.Check.Reader.Args, readerArgs.toString());
//                    break;
//                case MANUAL:
//                    params.put(Consts.Check.Card.Number, cardNumber);
//                    params.put(Consts.Check.Card.Expiration, cardExpiration);
//                    params.put(Consts.Check.Card.CVV2, cardCVV2);
//                    break;
//                default:
//                    break;
//            }
//
//            String json = SendRequest(c, ss.getProcessingServer() + Consts.Check.URL, params, ss);
//            result = new APICheckResult(json);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        }
//
//        return result;
//    }
//
//    public static APIPayResult Pay(Context c, String session, String trID, byte[] signature, String receiptEmail, String receiptPhone) {
//        APIPayResult result = null;
//
//        try {
//            Session ss = new Session(session);
//
//            HashMap<String, Object> params = new HashMap<String, Object>();
//
//            params.put(Consts.Command, Consts.Pay.Command);
//            params.put(Consts.Pay.TrID, trID);
//
//            if (signature != null) {
//                params.put(Consts.Pay.Signature, signature);
//            }
//            if (receiptEmail != null && !TextUtils.isEmpty(receiptEmail)) {
//                params.put(Consts.Pay.ReceiptEmail, receiptEmail);
//            }
//            if (receiptPhone != null && !TextUtils.isEmpty(receiptPhone)) {
//                String adjustedPhone = receiptPhone.replace(" ", "");
//                params.put(Consts.Pay.ReceiptPhone, adjustedPhone);
//            }
//
//            String json = SendRequest(c, ss.getProcessingServer() + Consts.Pay.URL, params, ss);
//            result = new APIPayResult(json);
//        } catch (Exception e) {
//            e.printStackTrace();
//            result = null;
//        }
//
//        return result;
//    }
//
//    public static APIPayResult Adjust(Context c, String session, String trID, byte[] signature, String receiptEmail, String receiptPhone) {
//        APIPayResult result = null;
//
//        try {
//            Session ss = new Session(session);
//
//            HashMap<String, Object> params = new HashMap<String, Object>();
//
//            params.put(Consts.Command, Consts.Adjust.Command);
//            params.put(Consts.Adjust.TrID, trID);
//
//            if (signature != null) {
//                params.put(Consts.Adjust.Signature, signature);
//            }
//            if (receiptEmail != null && !TextUtils.isEmpty(receiptEmail)) {
//                params.put(Consts.Adjust.ReceiptEmail, receiptEmail);
//            }
//            if (receiptPhone != null && !TextUtils.isEmpty(receiptPhone)) {
//                String adjustedPhone = receiptPhone.replace(" ", "");
//                params.put(Consts.Adjust.ReceiptPhone, adjustedPhone);
//            }
//
//            String json = SendRequest(c, ss.getProcessingServer() + Consts.Adjust.URL, params, ss);
//            result = new APIPayResult(json);
//        } catch (Exception e) {
//            e.printStackTrace();
//            result = null;
//        }
//
//        return result;
//    }
//
//
//    public static APISendReceiptResult SendReceipt(Context c, String session, String trID, String receiptEmail, String receiptPhone) {
//        APISendReceiptResult result = null;
//
//        try {
//            Session ss = new Session(session);
//
//            HashMap<String, Object> params = new HashMap<String, Object>();
//
//            params.put(Consts.Command, Consts.SendReceipt.Command);
//            params.put(Consts.SendReceipt.TrID, trID);
//
//            if (!TextUtils.isEmpty(receiptEmail)) {
//                params.put(Consts.SendReceipt.ReceiptEmail, receiptEmail);
//            }
//            if (!TextUtils.isEmpty(receiptPhone)) {
//                String adjustedPhone = receiptPhone.replace(" ", "");
//                params.put(Consts.SendReceipt.ReceiptPhone, adjustedPhone);
//            }
//
//            String json = SendRequest(c, ss.getProcessingServer() + Consts.SendReceipt.URL, params, ss);
//            result = new APISendReceiptResult(json);
//        } catch (Exception e) {
//            e.printStackTrace();
//            result = null;
//        }
//
//        return result;
//    }
//
//    public static APICheckCancelResult CheckCancel(Context c, String session, String trId) {
//        return APIClient.CheckCancel(c, session, trId, PaymentInputType.CASH, null, null, null, null);
//    }
//
//    public static APICheckCancelResult CheckCancel(Context c, String session, String trId, String readerData) {
//        return APIClient.CheckCancel(c, session, trId, PaymentInputType.SWIPE, readerData, null, null, null);
//    }
//
//    public static APICheckCancelResult CheckCancel(Context c, String session, String trId, String cardNumber, String cardExpiration, String cardCVV2) {
//        return APIClient.CheckCancel(c, session, trId, PaymentInputType.MANUAL, null, cardNumber, cardExpiration, cardCVV2);
//    }
//
//    private static APICheckCancelResult CheckCancel(Context c, String session, String trId, PaymentInputType inputType, String readerData, String cardNumber, String cardExpiration, String cardCVV2) {
//        APICheckCancelResult result = null;
//
//        // TODO: ALERT!!!! HARDCODED STRINGS!!!
//        String ReaderType = "MSR001";
//
//        try {
//            Session ss = new Session(session);
//
//            HashMap<String, Object> params = new HashMap<String, Object>();
//
//            params.put(Consts.Command, Consts.CheckCancel.Command);
//            params.put(Consts.CheckCancel.TrID, trId);
//
//            params.put(Consts.CheckCancel.InputType, inputType.mValue);
//            switch (inputType) {
//                case CASH:
//                    break;
//                case SWIPE:
//                    JSONObject readerArgs = new JSONObject();
//                    // TODO: Hardcoded ReaderType here
//                    readerArgs.put(Consts.CheckCancel.Reader.Type, ReaderType);
//                    readerArgs.put(Consts.CheckCancel.Reader.Data, readerData);
//                    params.put(Consts.CheckCancel.Reader.Args, readerArgs.toString());
//                    break;
//                case MANUAL:
//                    params.put(Consts.CheckCancel.Card.Number, cardNumber);
//                    params.put(Consts.CheckCancel.Card.Expiration, cardExpiration);
//                    params.put(Consts.CheckCancel.Card.CVV2, cardCVV2);
//                    break;
//                default:
//                    break;
//            }
//
//            String json = SendRequest(c, ss.getProcessingServer() + Consts.CheckCancel.URL, params, ss);
//            result = new APICheckCancelResult(json);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        }
//
//        return result;
//    }
//
//    public static APICancelPaymentResult CancelPayment(Context c, String session, String trId, CancelPaymentType cancelType, final byte[] signature, String receiptEmail, String receiptPhone) {
//        return CancelPayment(c, session, trId, PaymentInputType.CASH, cancelType, null, null, null, null, signature, receiptEmail, receiptPhone);
//    }
//
//    public static APICancelPaymentResult CancelPayment(Context c, String session, String trId, CancelPaymentType cancelType, String readerData, final byte[] signature, String receiptEmail,
//                                                       String receiptPhone) {
//        return CancelPayment(c, session, trId, PaymentInputType.SWIPE, cancelType, readerData, null, null, null, signature, receiptEmail, receiptPhone);
//    }
//
//    public static APICancelPaymentResult CancelPayment(Context c, String session, String trId, CancelPaymentType cancelType, String cardNumber, String cardExpiration, String cardCVV2,
//                                                       final byte[] signature, String receiptEmail, String receiptPhone) {
//        return CancelPayment(c, session, trId, PaymentInputType.MANUAL, cancelType, null, cardNumber, cardExpiration, cardCVV2, signature, receiptEmail, receiptPhone);
//    }
//
//    private static APICancelPaymentResult CancelPayment(Context c, String session, String trId, PaymentInputType inputType, CancelPaymentType cancelType, String readerData, String cardNumber,
//                                                        String cardExpiration, String cardCVV2, final byte[] signature, String receiptEmail, String receiptPhone) {
//        APICancelPaymentResult result = null;
//
//        // TODO: ALERT!!!! HARDCODED STRINGS!!!
//        String ReaderType = "MSR001";
//
//        try {
//            Session ss = new Session(session);
//
//            HashMap<String, Object> params = new HashMap<String, Object>();
//
//            params.put(Consts.Command, cancelType.mName);
//            params.put(Consts.CancelPayment.TrID, trId);
//            params.put(Consts.CancelPayment.InputType, inputType.mValue);
//            switch (inputType) {
//                case CASH:
//                    break;
//                case SWIPE:
//                    JSONObject readerArgs = new JSONObject();
//                    // TODO: Hardcoded ReaderType here
//                    readerArgs.put(Consts.CancelPayment.Reader.Type, ReaderType);
//                    readerArgs.put(Consts.CancelPayment.Reader.Data, readerData);
//                    params.put(Consts.CancelPayment.Reader.Args, readerArgs.toString());
//                    break;
//                case MANUAL:
//                    params.put(Consts.CancelPayment.Card.Number, cardNumber);
//                    params.put(Consts.CancelPayment.Card.Expiration, cardExpiration);
//                    params.put(Consts.CancelPayment.Card.CVV2, cardCVV2);
//                    break;
//                default:
//                    break;
//            }
//
//            if (signature != null)
//                params.put(Consts.CancelPayment.Signature, signature);
//            if (!TextUtils.isEmpty(receiptEmail))
//                params.put(Consts.CancelPayment.ReceiptEmail, receiptEmail);
//            if (!TextUtils.isEmpty(receiptPhone))
//                params.put(Consts.CancelPayment.ReceiptPhone, receiptPhone);
//
//            String json = SendRequest(c, ss.getProcessingServer() + Consts.CancelPayment.URL, params, ss);
//            result = new APICancelPaymentResult(json);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//
//    public static APIScheduleCheckResult ScheduleCheck(Context c, String session, String description, String readerData, byte[] image) {
//
//
//        APIScheduleCheckResult result = null;
//
//        // TODO: ALERT!!!! HARDCODED STRINGS!!!
//        String ServiceID = "CARDPORT.ACCEPT-PAYMENT";
//        String ReaderType = "MSR001";
//
//        try {
//            Session ss = new Session(session);
//
//            HashMap<String, Object> params = new HashMap<String, Object>();
//
//            params.put(Consts.Command, Consts.Schedule.CheckCommand);
//            // TODO: Hardcoded ServiceID here
//            params.put(Consts.Schedule.Service, ServiceID);
//
//            params.put(Consts.Schedule.Description, description);
//
//            if (image != null)
//                params.put(Consts.Schedule.Photo, image);
//
//            JSONObject readerArgs = new JSONObject();
//            // TODO: Hardcoded ReaderType here
//            readerArgs.put(Consts.Schedule.Reader.Type, ReaderType);
//            readerArgs.put(Consts.Schedule.Reader.Data, readerData);
//            params.put(Consts.Schedule.Reader.Args, readerArgs.toString());
//
//            params.put(Consts.Schedule.InputType, PaymentInputType.SWIPE.mValue);
//
//            String json = SendRequest(c, ss.getProcessingServer() + Consts.Schedule.URL, params, ss);
//            result = new APIScheduleCheckResult(json);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    public static APIScheduleSubmitResult ScheduleSubmit(Context c, String session, String shID, byte[] signature, String receiptEmail, String receiptPhone) {
//
//        APIScheduleSubmitResult result = null;
//
//        try {
//            Session ss = new Session(session);
//
//            HashMap<String, Object> params = new HashMap<String, Object>();
//
//            params.put(Consts.Command, Consts.Schedule.SubmitCommand);
//            params.put(Consts.Schedule.ScheduleId, shID);
//
//            if (signature != null) {
//                params.put(Consts.Schedule.Signature, signature);
//            }
//            if (!TextUtils.isEmpty(receiptEmail)) {
//                params.put(Consts.Schedule.ReceiptEmail, receiptEmail);
//            }
//            if (!TextUtils.isEmpty(receiptPhone)) {
//                params.put(Consts.Schedule.ReceiptPhone, receiptPhone);
//            }
//
//            String json = SendRequest(c, ss.getProcessingServer() + Consts.Schedule.URL, params, ss);
//            result = new APIScheduleSubmitResult(json);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//
//    public static APIHistoryResult GetHistory(Context c, String session, int page, String search) {
//        APIHistoryResult result = null;
//
//        try {
//            Session ss = new Session(session);
//
//            HashMap<String, Object> params = new HashMap<String, Object>();
//
//            params.put(Consts.Command, Consts.GetHistory.Command);
//            params.put(Consts.GetHistory.Period, HistoryPeriod.CurrentMonth);
//            params.put(Consts.GetHistory.PageNum, page);
//            params.put(Consts.GetHistory.Mode, Consts.GetHistory.ByDay);
//
//            if (search != null && !search.equals("")) {
//                params.put(Consts.GetHistory.Search, search);
//            }
//
//            String json = SendRequest(c, ss.getProcessingServer() + Consts.GetHistory.URL, params, ss);
//            result = new APIHistoryResult(json);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            result = null;
//        }
//
//        return result;
//    }
//
//    private static APIProcessingServerResult GetProcessingServer(Context c, String email, String serverInstance) {
//
//        HashMap<String, Object> params = new HashMap<String, Object>();
//        params.put(Consts.GetServer.Email, email);
//
//        if (!TextUtils.isEmpty(serverInstance)) {
//            params.put(Consts.GetServer.Instance, serverInstance);
//        }
//
//        return new APIProcessingServerResult(SendRequest(c, Consts.GetServer.DispatchServer1 + Consts.GetServer.URL, params, null));
//    }
//
//    public static APIResult iBoxApplication(Context c, String name, String company, String email, String phone) {
//        APIResult res = null;
//
//        try {
//            HashMap<String, Object> parameters = new HashMap<String, Object>();
//
//            parameters.put(Consts.Action, Consts.iBoxApplication.Action);
//
//            JSONObject args = new JSONObject();
//            args.put(Consts.iBoxApplication.Name, name);
//            args.put(Consts.iBoxApplication.Company, company);
//            args.put(Consts.iBoxApplication.Email, email);
//            args.put(Consts.iBoxApplication.Phone, phone);
//            parameters.put(Consts.iBoxApplication.Args, args.toString());
//
//            APIProcessingServerResult processingServerResult = GetProcessingServer(c, null, null);
//
//            if (processingServerResult.getValid() && processingServerResult.getErrorCode() == 0) {
//                String processingServerURL = processingServerResult.getProcessingServer();
//
//                String result = SendRequest(c, processingServerURL + Consts.iBoxApplication.URL, parameters, null);
//                res = new APIResult(result);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            res = null;
//        }
//
//        return res;
//    }
//
//    public static APIResult RemindPassword(Context c, String email) {
//        APIResult res = null;
//
//        try {
//            HashMap<String, Object> parameters = new HashMap<String, Object>();
//
//            parameters.put(Consts.Action, Consts.RemindPassword.Action);
//
//            JSONObject args = new JSONObject();
//            args.put(Consts.RemindPassword.Email, email);
//            args.put(Consts.RemindPassword.By, Consts.RemindPassword.Email);
//            parameters.put(Consts.RemindPassword.Args, args.toString());
//
//            APIProcessingServerResult processingServerResult = GetProcessingServer(c, null, null);
//
//            if (processingServerResult.getValid() && processingServerResult.getErrorCode() == 0) {
//                String processingServerURL = processingServerResult.getProcessingServer();
//                String result = SendRequest(c, processingServerURL + Consts.RemindPassword.URL, parameters, null);
//                res = new APIResult(result);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            res = null;
//        }
//
//        return res;
//    }


//    public static APIResult StartEMV(Context c, String session, String trID) {
//        APIResult result = null;
//
//        try {
//            Session ss = new Session(session);
//
//            HashMap<String, Object> params = new HashMap<String, Object>();
//
//            params.put(Consts.Command, Consts.EMV.CommandStartTransaction);
//            params.put(Consts.Pay.TrID, trID);
//
//            String json = SendRequest(c, ss.getProcessingServer() + Consts.EMV.URL, params, ss);
//            result = new APIPayResult(json);
//        } catch (Exception e) {
//            e.printStackTrace();
//            result = null;
//        }
//
//        return result;
//    }

}
