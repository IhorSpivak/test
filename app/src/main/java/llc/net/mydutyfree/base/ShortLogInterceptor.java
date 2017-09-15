package llc.net.mydutyfree.base;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by gorf on 7/22/16.
 */
public class ShortLogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String requestBodyString = bodyToString(chain.request());
        Response response = chain.proceed(request);
        String responseBodyString = response.body().string();
        Log.e(" !LOGGER! ", "Request Json: "+requestBodyString);
        Log.e(" !LOGGER! ", "Response Json: "+responseBodyString);
        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), responseBodyString))
                .build();
    }

    public static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

}
