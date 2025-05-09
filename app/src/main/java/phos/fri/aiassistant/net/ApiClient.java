package phos.fri.aiassistant.net;


import android.util.Log;

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

public class ApiClient {
    private static String TAG= "ApiClient";
//    private static final String BASE_URL = "http://192.168.132.104:38082/";
    private static final String BASE_URL = "http://101.200.152.119:38082";

    public static final long CONNECT_TIMEOUT = 6;
    public static final long WRITE_TIMEOUT = 6;
    public static final long READ_TIMEOUT = 6;

//    ApiService apiService;
    private static Retrofit retrofit;
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            //添加日志拦截器
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    try {
                        Log.d(TAG, "=========" + URLDecoder.decode(message, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY);

            //获取OkHttpClient
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .addNetworkInterceptor(new HttpHeaderInterceptor())
                    .sslSocketFactory(SSLSocketClient.getSSLSocketFactory()) //设置不验https的证书
                    .hostnameVerifier(SSLSocketClient.getHostnameVerifier()) //设置不验https的证书
                    .addNetworkInterceptor(httpLoggingInterceptor)  //注意放在header拦截器的后面，否则打印不出来拦截器里的header
                    .build();

            //初始化retrofit
            retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   //支持RXJAVA平台
                    .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                    .baseUrl(BASE_URL)
                    .client(client)
                    .build();

//            // 日志拦截
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//            // 缓存（可选），需在 Application 中初始化 cacheDir
//            Cache cache = new Cache(new File(App.getContext().getCacheDir(), "http_cache"),
//                    10 * 1024 * 1024); // 10MB
//
//            OkHttpClient client = new OkHttpClient.Builder()
//                    .connectTimeout(10, TimeUnit.SECONDS)
//                    .readTimeout(10, TimeUnit.SECONDS)
//                    .writeTimeout(10, TimeUnit.SECONDS)
//                    .retryOnConnectionFailure(true)
//                    .addInterceptor(logging)
//                    // 公共参数／鉴权拦截
//                    .addInterceptor(chain -> {
//                        Request original = chain.request();
//                        HttpUrl url = original.url().newBuilder()
//                                // .addQueryParameter("token", TokenManager.getToken())
//                                .build();
//                        Request request = original.newBuilder()
//                                .url(url)
//                                .build();
//                        return chain.proceed(request);
//                    })
//                    // 缓存策略拦截（无网时读取缓存）
//                    .addNetworkInterceptor(chain -> {
//                        Response response = chain.proceed(chain.request());
//                        // 在线时下面这个 header 生效；离线可再加一个 offlineIntercepter
//                        return response.newBuilder()
//                                .header("Cache-Control", "public, max-age=" + 60)
//                                .build();
//                    })
//                    .cache(cache)
//                    .build();
//
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .client(client)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(
//                            RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
//                    )
//                    .build();
        }
        return retrofit;
    }

    public static  ApiService getApiService() {
        return getRetrofit().create(ApiService.class);
    }



    public static class HttpHeaderInterceptor implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder builder = request.newBuilder()
                    .header("Accept", "application/json")
                    .method(request.method(), request.body());
//            builder = builder.addHeader("channel",  URLEncoder.encode(UserConfig.getInstance(null).getChannel2(), "UTF-8"))
//                    .addHeader("transid",UUID.randomUUID().toString());

            return chain.proceed(builder.build());
        }
    }
}


