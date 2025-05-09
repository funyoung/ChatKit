package com.stfalcon.chatkit.sample.features.wiki;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.common.data.model.Dialog;
import com.stfalcon.chatkit.sample.features.chat.ChatMessagesActivity;
import com.stfalcon.chatkit.sample.features.demo.DemoDialogsActivity;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import phos.fri.aiassistant.entity.ApiException;
import phos.fri.aiassistant.entity.AssignListData;
import phos.fri.aiassistant.entity.ChatListData;
import phos.fri.aiassistant.net.ApiClient;
import phos.fri.aiassistant.net.ApiService;
import phos.fri.aiassistant.net.RxUtils;
import phos.fri.aiassistant.settings.Profile;

public abstract class AbstractWikiActivity extends DemoDialogsActivity {
    private DialogsList dialogsList;

    protected abstract List<Dialog> getDialogs();
    protected abstract String getUserId();

    private ApiService api = ApiClient.getApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_layout_dialogs);

        String userId = getUserId();
        if (null == userId) {
            // 直接进入固定的法律咨询.
            ChatMessagesActivity.openWiki(this, Profile.lawDatasetId);
            finish();
            return;
        }

        dialogsList = findViewById(R.id.dialogsList);
        api.getAssignmentList(userId, 1, 20).subscribeOn(Schedulers.io())
                .compose(RxUtils.handleResponse())          // 业务 code 过滤
                .compose(RxUtils.applySchedulers())         // 线程切换
                .subscribe(new Observer<AssignListData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // 显示 loading
                    }
                    @Override
                    public void onNext(AssignListData data) {
                        // 更新 UI：data.getDataList()
                        String msg = new Gson().toJson(data);
                        Toast.makeText(AbstractWikiActivity.this, msg, Toast.LENGTH_LONG).show();
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
        initAdapter();
    }

    @Override
    public void onDialogClick(Dialog dialog) {
        ChatMessagesActivity.open(this);
    }

    private void initAdapter() {
        super.dialogsAdapter = new DialogsListAdapter<>(R.layout.item_custom_dialog, super.imageLoader);
        super.dialogsAdapter.setItems(getDialogs());

        super.dialogsAdapter.setOnDialogClickListener(this);
        super.dialogsAdapter.setOnDialogLongClickListener(this);

        dialogsList.setAdapter(super.dialogsAdapter);
    }
}
