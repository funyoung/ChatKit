package phos.fri.aiassistant.net;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class ApiClient {
    private static String TAG= "ApiClient";

    public static final long CONNECT_TIMEOUT = Config.TIMEOUT_SHORT;
    public static final long WRITE_TIMEOUT = Config.TIMEOUT_SHORT;
    public static final long READ_TIMEOUT = Config.TIMEOUT_SHORT;

    private static Retrofit retrofit;
    public static Retrofit getRetrofit() {
        return getRetrofit(Config.TOKEN);
    }

    public static Retrofit getRetrofit(String apiToken) {
        if (retrofit == null) {
            //添加日志拦截器
            HttpLoggingInterceptor httpLoggingInterceptor = Config.LoggingInterceptor();

            //获取OkHttpClient
            OkHttpClient client = Config.buildHttpClient(CONNECT_TIMEOUT, WRITE_TIMEOUT, READ_TIMEOUT, httpLoggingInterceptor, apiToken);

            //初始化retrofit
            retrofit = Config.buildRetrofit(client);

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

    public static  ApiService getService() {
        return getService(Config.TOKEN);
    }
    public static  ApiService getService(String apiToken) {
        return getRetrofit(apiToken).create(ApiService.class);
    }
}


