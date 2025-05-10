package phos.fri.aiassistant.net;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Config {
    public static final String LAW_DATA_ID = "286cb9ca16b111f0985f0242ac170005";
    public static final String DEFAULT_USER_ID = "u1001";
    public static final String DEFAULT_TEAM_ID = "t1001";
    public static final String DEFAULT_DATASET_ID = "90055a602cb411f08ac10242ac170005"; // d1001
    private static final String TAG = "Network_Config";
    //    protected static final String BASE_URL = "http://192.168.132.104:38082/";  // 内网
//    protected static final String BASE_URL = "http://101.200.152.119:38082";   // 互联网
    protected static final String BASE_URL = "http://20.0.52.93:38082";  // 2类区专网
    protected static final String TOKEN = "ragflow-UxMDE4MGMyMmIyMTExZjBhNjRkMDI0Mm";
    protected static final long TIMEOUT_SHORT = 6;
    protected static final long TIMEOUT_NORMAL = 20;
    protected static final long TIMEOUT_LONG = 30;

    protected static HttpLoggingInterceptor LoggingInterceptor() {
        return new HttpLoggingInterceptor(message -> {
            try {
                Log.d(TAG, "=========" + URLDecoder.decode(message, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }

//    public static OkHttpClient buildHttpClient(long connectTimeout, long writeTimeout, long readTimeout,
//                                               HttpLoggingInterceptor httpLoggingInterceptor) {
//        return buildHttpClient(connectTimeout, writeTimeout, readTimeout, httpLoggingInterceptor, null);
//    }
    public static OkHttpClient buildHttpClient(long connectTimeout, long writeTimeout, long readTimeout,
                                               HttpLoggingInterceptor httpLoggingInterceptor, String apiKey) {
        return new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addNetworkInterceptor(new HttpHeaderInterceptor(apiKey))
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory()) //设置不验https的证书
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier()) //设置不验https的证书
                .addNetworkInterceptor(httpLoggingInterceptor)  //注意放在header拦截器的后面，否则打印不出来拦截器里的header
                .build();
    }

    public static Retrofit buildRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   //支持RXJAVA平台
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .baseUrl(Config.BASE_URL)
                .client(client)
                .build();
    }

    public static class HttpHeaderInterceptor implements Interceptor {
        public HttpHeaderInterceptor(String apiKey) {
            this.apiKey = apiKey;
        }

        private final String apiKey;
        @NonNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder builder = request.newBuilder()
                    .header("Accept", "application/json")

                    .method(request.method(), request.body());
//            builder = builder.addHeader("channel",  URLEncoder.encode(UserConfig.getInstance(null).getChannel2(), "UTF-8"))
//                    .addHeader("transid",UUID.randomUUID().toString());
            if (apiKey != null) {
                builder.header("Authorization", "Bearer " + apiKey);
            }
            return chain.proceed(builder.build());
        }
    }
}
