package phos.fri.aiassistant.net;


import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import phos.fri.aiassistant.entity.ApiResponse;
import phos.fri.aiassistant.entity.ChatListData;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author
 * 二类区总线服务访问。
 */
public interface ApiService {

//    String CONTENT_TYPE = "Content-Type: application/octet-stream";
    String CONTENT_TYPE = "Content-Type: application/json";

    @GET("chat/list/{userId}/{datasetId}")
    Observable<ApiResponse<ChatListData>> getChatList(
            @Path("userId") String userId,
            @Path("datasetId") String datasetId
    );

    @Headers(CONTENT_TYPE)
    @POST("portrait-api/gwface/terimnalAuth")
    Observable<ResponseBody> getZjMy(@Body RequestBody bytes);

    @Headers(CONTENT_TYPE)
    @POST("portrait-api/gwface/auth")
    Observable<ResponseBody> faceAuth(@Body RequestBody bytes);


    @Headers(CONTENT_TYPE)
    @POST("portrait-api/gwface/authOneToOne")
    Observable<ResponseBody> faceCompare(@Body RequestBody bytes);

    @Headers(CONTENT_TYPE)
    //@POST("prod-api/service-authfaces/face/query")
    @POST("portrait-api/gwface/query")
    Observable<ResponseBody> faceQuery(@Body RequestBody bytes);



    @Headers(CONTENT_TYPE)
    //@POST("prod-api/service-authfaces/face/query")
    @POST("portrait-api/gw/getAppInfo")
    Observable<ResponseBody> queryAppInfo(@Body RequestBody bytes);

    //身份证号在用户白名单里，同时imei在设备白名单里才能登录。上海现在都没管。
    @Headers(CONTENT_TYPE)
    @POST("portrait-api/gw/appLogin")
    Observable<ResponseBody> appLogin(@Body RequestBody bytes);

    @Headers(CONTENT_TYPE)
    @POST("portrait-api/gw/isAlive")
    Observable<ResponseBody> noAliveJudge(@Body RequestBody bytes);

}