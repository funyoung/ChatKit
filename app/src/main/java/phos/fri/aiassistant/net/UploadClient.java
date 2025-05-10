package phos.fri.aiassistant.net;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class UploadClient {
    private static String TAG= "UploadClient";

    public static final long CONNECT_TIMEOUT = Config.TIMEOUT_NORMAL;
    public static final long WRITE_TIMEOUT = Config.TIMEOUT_NORMAL;
    public static final long READ_TIMEOUT = Config.TIMEOUT_NORMAL;


    private static Retrofit retrofit;
    public static Retrofit getRetrofit(String apiToken, String userId) {
        if (retrofit == null) {
            //添加日志拦截器
            HttpLoggingInterceptor httpLoggingInterceptor = Config.LoggingInterceptor();

            //获取OkHttpClient
            OkHttpClient client = Config.buildHttpClient(CONNECT_TIMEOUT, WRITE_TIMEOUT, READ_TIMEOUT, httpLoggingInterceptor, apiToken, userId);

            //初始化retrofit
            retrofit = Config.buildRetrofit(client);
        }
        return retrofit;
    }

    public static  UploadService getService(String apiToken, String userId) {
        return getRetrofit(apiToken, userId).create(UploadService.class);
    }
}


