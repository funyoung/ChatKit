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
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            //添加日志拦截器
            HttpLoggingInterceptor httpLoggingInterceptor = Config.LoggingInterceptor();

            //获取OkHttpClient
            OkHttpClient client = Config.buildHttpClient(CONNECT_TIMEOUT, WRITE_TIMEOUT, READ_TIMEOUT, httpLoggingInterceptor);

            //初始化retrofit
            retrofit = Config.buildRetrofit(client);
        }
        return retrofit;
    }

    public static  UploadService getService() {
        return getRetrofit().create(UploadService.class);
    }
}


