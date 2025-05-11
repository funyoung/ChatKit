package com.stfalcon.chatkit.sample.features.chat;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import phos.fri.aiassistant.entity.ApiException;
import phos.fri.aiassistant.entity.ChatCompletionChunk;
import phos.fri.aiassistant.entity.ChatListData;
import phos.fri.aiassistant.entity.FuckNewChatData;
import phos.fri.aiassistant.entity.NewChatData;
import phos.fri.aiassistant.entity.NewChatRequest;
import phos.fri.aiassistant.entity.OcrChatData;
import phos.fri.aiassistant.entity.OcrData;
import phos.fri.aiassistant.net.AiHelper;
import phos.fri.aiassistant.net.ApiClient;
import phos.fri.aiassistant.net.RxUtils;
import phos.fri.aiassistant.net.UploadClient;
import phos.fri.aiassistant.settings.Profile;

import phos.fri.aiassistant.entity.ChatRequest;

// 静态会话管理
public class ChatSession {
    private static final String TAG = "ChatSession";
    private String chatId = null;

    private String userId = null;
    private String datasetId = null;

    private final Listener listener;

    protected ChatSession(Listener listener) {
        this.listener = listener;
    }

    // 假设我们要第 1 页，每页 20 条
    protected void loadChatList() {
        ApiClient.getService(Profile.token, userId).getChatList(userId, datasetId)
                .subscribeOn(Schedulers.io())
                .compose(RxUtils.handleResponse())          // 业务 code 过滤
                .compose(RxUtils.applySchedulers())         // 线程切换
                .subscribe(new Observer<ChatListData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // 显示 loading
                    }
                    @Override
                    public void onNext(ChatListData data) {
                        // 更新 UI：data.getDataList()
                        String msg = new Gson().toJson(data);
                        listener.toast(msg);
                        //Toast.makeText(ChatMessagesActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        handleApiError("loadChatList", e);
                    }
                    @Override
                    public void onComplete() {
                        // 隐藏 loading
                    }
                });
    }

    private void handleApiError(String headline, Throwable e) {
        e.printStackTrace();
        Log.e(TAG, headline + e.getMessage());

        // 隐藏 loading
        if (e instanceof IOException) {
            // 网络错误提示
        } else if (e instanceof JsonParseException) {
            // 解析错误提示
        } else if (e instanceof ApiException) {
            // 业务错误提示：((ApiException)e).getMessage()
        } else {
            // 其他错误
        }
    }

    public void attach(String userId, String datasetId) {
        Log.i(TAG, "attach: user: " + userId + ", dataset: " + datasetId + ", chatId: " + chatId);
        if (null != userId && userId.equalsIgnoreCase(this.userId)
                && null != datasetId && datasetId.equalsIgnoreCase(this.datasetId)) {
            Log.i(TAG, "");
            return;
        }

        this.userId = userId;
        this.datasetId = datasetId;
        this.chatId = null;

        loadChatList();
    }

    // 点击提交消息，判断存在当前chatId, 直接提交，否则先创建成功后，提交
    public void submit(String msg) {
        Log.i(TAG, "submit message: " + msg + ", for chatId: " + chatId + ", userId: " + userId + ", datasetId: " + datasetId);

        if (null == datasetId) {
            String tips = "submit error datasetId: " + datasetId + " for msg: " + msg;
            listener.toast(tips);
            listener.chatCompleted();
            return;
        }

        if (null == chatId) {
            // todo:create chat
            NewChatRequest requestBody = AiHelper.getNewChatRequest(datasetId);
            Log.i(TAG, "new chat request body: " + new Gson().toJson(requestBody));

            ApiClient.getService(Profile.token, userId).createChat(requestBody).subscribeOn(Schedulers.io())
                    .compose(RxUtils.handleResponse())          // 业务 code 过滤
                    .compose(RxUtils.applySchedulers())         // 线程切换
                    .subscribe(new Observer<FuckNewChatData>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            // 显示 loading
                        }
                        @Override
                        public void onNext(FuckNewChatData dataWrapper) {
                            NewChatData data = dataWrapper.getData();
                            if (null == data) {
                                debugShow(dataWrapper.getMessage(), dataWrapper);
                                return;
                            }
                            chatId = data.getChatId();
                            String msg = "创建chat成功：chatId: " + new Gson().toJson(data);
                            listener.toast(msg);
                            Log.i(TAG, msg);
                            startChat(msg);
                        }
                        @Override
                        public void onError(Throwable e) {
                            handleApiError("loadChatList", e);
                        }
                        @Override
                        public void onComplete() {
                            // 隐藏 loading
                        }
                    });
        } else {
            // todo: sending chat.
            startChat(msg);
        }
    }

    private Disposable disposable;
    private void startChat(String msg) {
        if (null == chatId) {
            String tips = "无法聊天， chatId: " + chatId + ", datasetId: " + datasetId + ", msg: " + msg;
            listener.toast(tips);
            Log.e(TAG, tips);
            return;
        }

        // todo: 使用真实msg和chatId
        String testMsg = "方芳被诈骗案情况"; // msg;
        String testChatId = "d74002a42d4511f0b1990242ac170005"; // chatId;

        ChatRequest request = AiHelper.getChatRequest(testMsg, true);

        disposable = ApiClient.getService(Profile.token, userId).chatStream(testChatId, request)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMap(responseBody -> Observable.fromIterable(parseSSE(responseBody)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        chunkJson -> onChatJsonAppend(chunkJson),
                        throwable -> onChatError(throwable),
                        () -> onChatCompleted()
                );
    }

    private void onChatCompleted() {
        Log.i("SSE", "流接收完成");
        listener.onChatFinish();
    }

    private void onChatError(Throwable throwable) {
        Log.e("SSE", "流处理出错", throwable);
        listener.onChatError(throwable.getMessage());
    }

    private void onChatJsonAppend(String chunkJson) {
        // 收到每段完整 JSON 字符串时回调
        Log.i("SSE", "完整 JSON: " + chunkJson);
        ChatCompletionChunk chunk = new Gson().fromJson(chunkJson, ChatCompletionChunk.class);
        if (null != chunk) {
            String id = chunk.getId();
            String content = null;
            String finishReason = null;
            List<ChatCompletionChunk.Choice> choiceList = chunk.getChoices();
            if (null != choiceList && !choiceList.isEmpty()) {
                ChatCompletionChunk.Choice choice = choiceList.get(0);
                if (null != choice) {
                    finishReason = choice.getFinishReason();
                    ChatCompletionChunk.Delta delta = choice.getDelta();
                    if (null != delta) {
                        content = delta.getContent();
                    }
                }
            }
            listener.onChatUpdate(id, content, finishReason);
        }
    }

    public void stopChat() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * 将 ResponseBody 按 SSE 协议解析，输出一个完整 JSON 字符串的列表。
     */
    private List<String> parseSSE(ResponseBody body) throws IOException {
        List<String> completeJsons = new ArrayList<>();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(body.byteStream(), StandardCharsets.UTF_8)
        );
        String line;
        StringBuilder pending = new StringBuilder();
        int retryCount = 0;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("data:")) {
                String hex = line.substring(5).trim();
                if (hex.startsWith("data:")) {
                    hex = hex.substring(5).trim();
                }

                debugShow("read line: " + hex);
                if (hex.isEmpty()) {
                    continue;
                }

                if ("[DONE]".equals(hex)) {
                    listener.onChatResponded(completeJsons);
                    break;
                }

//                listener.onChatAppended(hex);

//                String fragment = new String(hexToBytes(hex), StandardCharsets.UTF_8);
                String fragment = hexToText(hex);
                debugShow("read fragment: " + fragment);
                debugShow("completeJsons before: " + completeJsons);
                if (isValidJson(fragment)) {
                    // 如果是完整 JSON，直接加入列表
                    completeJsons.add(fragment);
                    pending.setLength(0);
                    retryCount = 0;
                } else {
                    // 拼接缓存
                    pending.append(fragment);
                    if (isValidJson(pending.toString())) {
                        completeJsons.add(pending.toString());
                        pending.setLength(0);
                        retryCount = 0;
                    } else if (++retryCount > 3) {
                        // 超过次数，清空缓存
                        pending.setLength(0);
                        retryCount = 0;
                    }
                }
                debugShow("completeJsons after: " + completeJsons);
                Log.d(TAG, "----------------------------------\n");
            }
        }
        return completeJsons;
    }

    private void debugShow(String tips) {
        listener.toast(tips);
        Log.i(TAG, tips);
    }

    private boolean isValidJson(String s) {
        try {
            new JSONObject(s);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static String hexToText(String hexString) {
        hexString = hexString.replaceAll("\\s", ""); // 移除空格
        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            int index = i * 2;
            String hex = hexString.substring(index, index + 2);
            bytes[i] = (byte) Integer.parseInt(hex, 16);
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i+1), 16));
        }
        return data;
    }

    public void performOcr(String path) throws UnsupportedEncodingException {
        File file = new File(path);
        if (file.exists()) {
//            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
//            String filename = URLEncoder.encode(file.getName(), "UTF-8");;
//            MultipartBody.Part body = MultipartBody.Part.createFormData("image", filename, requestFile);
            // 创建文件请求体
            RequestBody requestFile = RequestBody.create(
                    MediaType.parse("multipart/form-data"),
                    file
            );

            MultipartBody.Part body = MultipartBody.Part.createFormData(
                    "file",
                    file.getName(),
                    requestFile
            );
            UploadClient.getService(Profile.token, Profile.userId).uploadOcrFile(body)
                    .subscribeOn(Schedulers.io())
                    .compose(RxUtils.handleResponse())          // 业务 code 过滤
                    .compose(RxUtils.applySchedulers())         // 线程切换
                    .subscribe(new Observer<OcrData>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            // 显示 loading
                        }
                        @Override
                        public void onNext(OcrData data) {
                            debugShow("", data);
                            String prompt = "你是文档总结助手，请用户输入内容";
                            String testContent = new Gson().toJson(data);
                            if (null != data) {
                                testContent = data.getOcrText();
                                debugShow(testContent);
                            }
                            performOcrSummary(prompt, testContent);
                        }
                        @Override
                        public void onError(Throwable e) {
                            handleApiError("loadChatList", e);
                        }
                        @Override
                        public void onComplete() {
                            // 隐藏 loading
                        }
                    });
        } else {
            listener.toast("文件不存在： " + path);
        }
    }

    protected void performOcrSummary(String prompt, String content) {
        content = "我公司于2023年9月开始应用无线通信服务网关系统，经过两月的使用，该系统安全、可靠，稳定性高。使用期间将集群通信资源统一接入公安移动信息网并对其统一管控，提供通信资源共享通道以及安全可信、统一标准的访问服务接口，支持集群通信专网和公安移动信息网之间语音业务互通，保证了网络融合通信业务的安全。现将该系统的使用体会报告如下";
        ChatRequest chatRequest = AiHelper.getChatRequest(prompt, content, false);
        ApiClient.getService(Profile.token, userId).ocrSummary(chatRequest)
                .subscribeOn(Schedulers.io())
                .compose(RxUtils.handleResponse())          // 业务 code 过滤
                .compose(RxUtils.applySchedulers())         // 线程切换
                .subscribe(new Observer<OcrChatData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // 显示 loading
                    }
                    @Override
                    public void onNext(OcrChatData data) {
                        // 更新 UI：data.getDataList()
                        String msg = new Gson().toJson(data);
                        listener.toast(msg);
                        //Toast.makeText(ChatMessagesActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        handleApiError("loadChatList", e);
                    }
                    @Override
                    public void onComplete() {
                        // 隐藏 loading
                    }
                });
    }


    private <T> void debugShow(String s, T data) {
        // 更新 UI：data.getDataList()
        String msg = s + new Gson().toJson(data);
        listener.toast(msg);
        Log.d(TAG, msg);
        //Toast.makeText(ChatMessagesActivity.this, msg, Toast.LENGTH_LONG).show();
    }
}

interface Listener {
    void toast(String msg);

    void chatCompleted();

    void onChatResponded(List<String> completeJsons);

    void onChatAppended(String hex);

    void onChatUpdate(String id, String content, String finishReason);

    void onChatFinish();

    void onChatError(String message);
}
