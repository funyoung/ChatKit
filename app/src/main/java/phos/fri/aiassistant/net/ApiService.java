package phos.fri.aiassistant.net;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import phos.fri.aiassistant.entity.ApiResponse;
import phos.fri.aiassistant.entity.AssignListData;
import phos.fri.aiassistant.entity.ChatCompletionRequest;
import phos.fri.aiassistant.entity.ChatListData;
import phos.fri.aiassistant.entity.ChatRequest;
import phos.fri.aiassistant.entity.FuckNewChatData;
import phos.fri.aiassistant.entity.NewChatRequest;
import phos.fri.aiassistant.entity.NewChatData;
import phos.fri.aiassistant.entity.OcrChatData;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * @author
 */
public interface ApiService {
//    String CONTENT_TYPE = "Content-Type: application/octet-stream";
    String CONTENT_TYPE = "Content-Type: application/json";

    @Headers(CONTENT_TYPE)
    @GET("chat/list/{userId}/{datasetId}")
    Observable<ApiResponse<ChatListData>> getChatList(
            @Path("userId") String userId,
            @Path("datasetId") String datasetId
    );

    @Headers(CONTENT_TYPE)
    @GET("assign/list/user/{userId}")
    Observable<ApiResponse<AssignListData>> getUserAssignmentList(
            @Path("userId") String userId,
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize
    );

    @Headers(CONTENT_TYPE)
    @GET("assign/list/team/{teamId}")
    Observable<ApiResponse<AssignListData>> getTeamAssignmentList(
            @Path("teamId") String teamId,
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize
    );

    @Headers({
            "userId: u1001",
            CONTENT_TYPE
    })
    @POST("ai/create/api/v1/chats")
    Observable<ApiResponse<FuckNewChatData>> createChat(@Body NewChatRequest request);


    @POST("ai/api/v1/chats_openai/{chatId}/chat/completions")
    @Headers({
            "Content-Type: application/json",
            "Accept: text/event-stream",
            "Authorization: Bearer <your-token>"  // 替换为你的 token
    })
    Observable<ResponseBody> chatStream(
            @Path("chatId") String chatId,
            //@Header("Authorization") String authorization,
            @Body ChatRequest request);
//    @Headers({
//            "Content-Type: application/json",
//            "Accept: text/event-stream"  // 重要：声明接受SSE
//    })
//    @POST("chats_openai/{chatId}/chat/completions")
//    Flowable<ResponseBody> streamChatCompletion(
//            @Path("chatId") String chatId,
//            @Header("Authorization") String authorization,
//            @Body RequestBody body
//    );
//    @POST("/ai/api/v1/chats_openai/{chatId}/chat/completions")
//    Observable<ApiResponse<NewChatData>> chatCompletion(@Body CreateChatRequest request);

    @POST("/ai/api/chat/completions")
    Observable<ApiResponse<OcrChatData>> ocrSummary(@Body ChatRequest request);
}