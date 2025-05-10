package com.stfalcon.chatkit.sample.features.chat;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import phos.fri.aiassistant.entity.ApiException;
import phos.fri.aiassistant.entity.ChatListData;
import phos.fri.aiassistant.entity.FuckNewChatData;
import phos.fri.aiassistant.entity.NewChatData;
import phos.fri.aiassistant.entity.NewChatRequest;
import phos.fri.aiassistant.net.AiHelper;
import phos.fri.aiassistant.net.ApiClient;
import phos.fri.aiassistant.net.RxUtils;
import phos.fri.aiassistant.settings.Profile;

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
        ApiClient.getService(Profile.token, userId).getChatList(userId, datasetId).subscribeOn(Schedulers.io())
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
                    @Override
                    public void onComplete() {
                        // 隐藏 loading
                    }
                });
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
            NewChatRequest requestBody = AiHelper.createChat(datasetId);
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
                            chatId = data.getChatId();
                            String msg = "创建chat成功：chatId: " + new Gson().toJson(data);
                            listener.toast(msg);
                            Log.i(TAG, msg);

                        }
                        @Override
                        public void onError(Throwable e) {
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
                        @Override
                        public void onComplete() {
                            // 隐藏 loading
                        }
                    });
        } else {
            // todo: sending chat.
        }
    }
}
interface Listener {
    void toast(String msg);

    void chatCompleted();
}
