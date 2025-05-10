package phos.fri.aiassistant.net;


import io.reactivex.Observable;
import phos.fri.aiassistant.entity.ApiResponse;
import phos.fri.aiassistant.entity.AssignListData;
import phos.fri.aiassistant.entity.ChatCompletionRequest;
import phos.fri.aiassistant.entity.ChatListData;
import phos.fri.aiassistant.entity.NewChatRequest;
import phos.fri.aiassistant.entity.NewChatData;
import retrofit2.http.Body;
import retrofit2.http.GET;
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

    @Headers(CONTENT_TYPE)
    @POST("ai/create/api/v1/chats")
    Observable<ApiResponse<NewChatData>> createChat(@Body NewChatRequest request);


//    @POST("/ai/api/v1/chats_openai/{chatId}/chat/completions")
//    Observable<ApiResponse<NewChatData>> chatCompletion(@Body CreateChatRequest request);

    /**
     * 调用 OpenAI 风格的流式聊天完成接口
     *
     * @param chatId  路径中的聊天会话等标识
     * @param request    请求体，会以 JSON 发送
     * @return           ResponseBody，可通过流方式逐行读取 SSE 事件
     */
    @Streaming
    @Headers({
            "accept: text/event-stream;charset=UTF-8",
            "Content-Type: application/json"
    })
    @POST("ai/api/v1/chats_openai/{chatId}/chat/completions")
    Observable<ApiResponse<String>> streamChatCompletions(
            @Path("chatId") String chatId,
            @Body ChatCompletionRequest request
    );
}