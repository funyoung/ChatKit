package com.stfalcon.chatkit.sample.features.wiki;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.features.chat.ChatMessagesActivity;
import com.stfalcon.chatkit.sample.utils.AppUtils;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import phos.fri.aiassistant.entity.ApiException;
import phos.fri.aiassistant.entity.ApiResponse;
import phos.fri.aiassistant.entity.AssignListData;
import phos.fri.aiassistant.entity.DatasetItem;
import phos.fri.aiassistant.net.ApiClient;
import phos.fri.aiassistant.net.ApiService;
import phos.fri.aiassistant.net.RxUtils;
import phos.fri.aiassistant.settings.Profile;

public abstract class BaseWikiActivity extends AppCompatActivity
        implements WikiListAdapter.OnDialogClickListener,
        WikiListAdapter.OnDialogLongClickListener {

    protected ImageLoader imageLoader;
    private WikiList wikiList;
    protected WikiListAdapter wikiAdapter;

    protected abstract Observable<ApiResponse<AssignListData>> getWikiList(ApiService api);

    private ApiService api = ApiClient.getApiService();

    protected String getTitleStr() {
        return null;
    }

    protected  @StringRes int getTitleId() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_list);


        Observable<ApiResponse<AssignListData>> observable = getWikiList(api);
        if (null == observable) {
            // 直接进入固定的法律咨询.
            ChatMessagesActivity.openWiki(this, Profile.lawDatasetId);
            finish();
            return;
        }


        imageLoader = (imageView, url, payload) -> Picasso.get().load(url).into(imageView);

        String title = getTitleStr();
        if (null != title) {
            setTitle(title);
        }

        int titleId = getTitleId();
        if (titleId != 0) {
            setTitle(titleId);
        }

        wikiList = findViewById(R.id.wikiList);
        observable.subscribeOn(Schedulers.io())
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
                        refreshUi(data.getDataList());
                        String msg = new Gson().toJson(data);
                        Toast.makeText(BaseWikiActivity.this, msg, Toast.LENGTH_LONG).show();
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
    public void onDialogClick(DatasetItem dialog) {
        ChatMessagesActivity.openWiki(this, dialog.getDatasetId());
    }

    @Override
    public void onDialogLongClick(DatasetItem dialog) {
        AppUtils.showToast(
                this,
                dialog.getDatasetName(),
                false);
    }

    private void initAdapter() {
        wikiAdapter = new WikiListAdapter(R.layout.item_wiki, imageLoader);
        wikiAdapter.setOnDialogClickListener(this);
        wikiAdapter.setOnDialogLongClickListener(this);
        wikiList.setAdapter(wikiAdapter);
    }

    private void refreshUi(List<DatasetItem> data) {
//        List<Dialog> items = DialogsFixtures.getDialogs();
//        super.dialogsAdapter.setItems(items);
        wikiAdapter.setItems(data);
    }
}
