package llc.net.mydutyfree.base;

import java.util.concurrent.TimeUnit;

import llc.net.mydutyfree.interfaces.PostInterfaceApi;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gorf on 7/22/16.
 */
public class RetrofitService {
    private static Retrofit client;

    public RetrofitService() {
        this(false);
    }

    public RetrofitService(Boolean bodyLogOut) {
        if (bodyLogOut) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS);
            httpClient.addInterceptor(new ShortLogInterceptor());
            client = new Retrofit.Builder()
//                    .baseUrl("http://test.api.mydutyfree.net/")
                    .baseUrl("http://app.mydutyfree.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        } else {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS);
            client = new Retrofit.Builder()
//                    .baseUrl("http://test.api.mydutyfree.net/")
                    .baseUrl("http://app.mydutyfree.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
    }

    public static PostInterfaceApi create() {
        return client.create(PostInterfaceApi.class);
    }
}
