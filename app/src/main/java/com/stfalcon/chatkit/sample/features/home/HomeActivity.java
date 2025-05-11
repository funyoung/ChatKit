package com.stfalcon.chatkit.sample.features.home;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.common.intent.IntentUtil;
import com.stfalcon.chatkit.sample.common.intent.Schema;
import com.stfalcon.chatkit.sample.features.chat.ChatMessagesActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (handleIntent(getIntent())) {
            return;
        }

        // 为功能按钮设置点击事件
        setFeatureButtonClickListener(R.id.hi_chat, "点我聊天");
        setFeatureButtonClickListener(R.id.tv_person_search, "人像查询");
        setFeatureButtonClickListener(R.id.tv_id_verify, "证件核查");
        setFeatureButtonClickListener(R.id.tv_my_knowledge, "我的知识库");
        setFeatureButtonClickListener(R.id.tv_shared_knowledge, "共享知识库");
        setFeatureButtonClickListener(R.id.tv_law_consult, "法律法规咨询");
        setFeatureButtonClickListener(R.id.tv_ocr, "OCR识别");
        setFeatureButtonClickListener(R.id.tv_tts, "TTS");
//        setFeatureButtonClickListener(R.id.tv_translation, "中英翻译");
        setFeatureButtonClickListener(R.id.tv_more, "更多");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent); // 更新当前Intent
        if (handleIntent(intent)) {
            return;
        }
    }
    private boolean handleIntent(Intent intent) {
//        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
//            Uri uri = intent.getData();
//            if (IntentUtil.handleIntent(this, uri)) {
//                finish();
//            }
//        }
        if (IntentUtil.handleIntentAction(this, intent.getAction())) {
            finish();
            return true;
        }
        return false;
    }
    private void setFeatureButtonClickListener(int viewId, String featureName) {
        findViewById(viewId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理功能按钮点击事件
                // 这里可以添加跳转到对应功能页面的代码
                System.out.println("Clicked on " + featureName);
                int id = v.getId();
                if (R.id.hi_chat == id) {
                    showChat();
                } else if (R.id.tv_person_search == id) {
                    showPersonSearch();
                } else if (R.id.tv_id_verify == id) {
                    showIdVerify();
                } else if (R.id.tv_my_knowledge == id) {
                    showMyWiki();
                } else if (R.id.tv_shared_knowledge == id) {
                    showSharedWiki();
                } else if (R.id.tv_law_consult == id) {
                    showLawConsult();
                } else if (R.id.tv_ocr == id) {
                    showOcr();
                } else if (R.id.tv_tts == id) {
                    showTts();
//                } else if (R.id.tv_translation == id) {
//                    showTranslation();
                } else {
                    showMore();
                }
            }
        });
    }

//    private void dispatch(String schema, String path) {
//        IntentUtil.dispatch(this, schema, path);
//    }
    private void dispatchAction(String action) {
        try {
            startActivity(new Intent(action));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "目标应用未安装:" + action, Toast.LENGTH_SHORT).show();
        }
    }

    private void showMore() {
//        dispatch(Schema.APP_TOOL, Schema.TOOL_MORE);
        dispatchAction(Schema.PMOS_ACTION_SERVICE_MORE);
    }

    private void showTts() {
//        dispatch(Schema.APP_TOOL, Schema.TOOL_TTS);
        dispatchAction(Schema.PMOS_ACTION_SERVICE_TTS);
    }

    private void showOcr() {
//        dispatch(Schema.APP_TOOL, Schema.TOOL_OCR);
        dispatchAction(Schema.PMOS_ACTION_SERVICE_OCR);
    }

    private void showChat() {
//        dispatch(Schema.APP_WIKI, Schema.WIKI_CHAT);
//        ChatMessagesActivity.open(this);
    }

    private void showLawConsult() {
//        dispatch(Schema.APP_WIKI, Schema.WIKI_PUBLIC);
        dispatchAction(Schema.PMOS_ACTION_WIKI_LAW);
    }

    private void showSharedWiki() {
//        dispatch(Schema.APP_WIKI, Schema.WIKI_TEAM);
        dispatchAction(Schema.PMOS_ACTION_WIKI_TEAM);
    }

    private void showMyWiki() {
//        dispatch(Schema.APP_WIKI, Schema.WIKI_MINE);
        dispatchAction(Schema.PMOS_ACTION_WIKI_MINE);
    }

    private void showIdVerify() {
//        dispatch(Schema.APP_PMOS, Schema.PMOS_ID);
        dispatchAction(Schema.PMOS_ACTION_IDREAD);
    }

    private void showPersonSearch() {
//        dispatch(Schema.APP_PMOS, Schema.PMOS_FACE);
        dispatchAction(Schema.PMOS_ACTION_LIVEFACE);
    }
}