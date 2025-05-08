package com.stfalcon.chatkit.sample.features.home;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.common.intent.IntentUtil;
import com.stfalcon.chatkit.sample.common.intent.Schema;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        handleIntent(getIntent());

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
        handleIntent(intent);
    }
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();
            if (IntentUtil.handleIntent(this, uri)) {
                finish();
            }
        }
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
                    //startApk(MainActivity.this);
                    showPersonSearch();
//                } else if (R.id.tv_person_auth == id) {
//                    viewImage2();
//                } else if (R.id.tv_person_compare == id) {
//                    actionImage1();
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

    private void dispatch(String schema, String path) {
        IntentUtil.dispatch(this, schema, path);
    }

    private void showMore() {
        dispatch(Schema.APP_TOOL, Schema.TOOL_MORE);
    }

    private void showTts() {
        dispatch(Schema.APP_TOOL, Schema.TOOL_TTS);
    }

    private void showOcr() {
        dispatch(Schema.APP_TOOL, Schema.TOOL_OCR);
    }

    private void showChat() {
        dispatch(Schema.APP_WIKI, Schema.WIKI_CHAT);
    }

    private void showLawConsult() {
        dispatch(Schema.APP_WIKI, Schema.WIKI_PUBLIC);
    }

    private void showSharedWiki() {
        dispatch(Schema.APP_WIKI, Schema.WIKI_TEAM);
    }

    private void showMyWiki() {
        dispatch(Schema.APP_WIKI, Schema.WIKI_MINE);
    }

    private void showIdVerify() {
        dispatch(Schema.APP_PMOS, Schema.PMOS_ID);
    }

    private void showPersonSearch() {
        dispatch(Schema.APP_PMOS, Schema.PMOS_FACE);
    }

    private void actionImage1() {
        // 通过自定义 Action 发送 Intent
        Intent intent = new Intent("com.example.ACTION1");
        startActivity(intent);
    }

    private void actionImage2() {
        // 通过自定义 Action 发送 Intent
        Intent intent = new Intent("com.example.ACTION2");
        startActivity(intent);
    }


    public static final String APP_PREFIX = "yourapp";
    public static final String APP_DOMAIN = "example.com";
    //    public static final String SCHEME = "yourapp://example.com/";
    public static final String SCHEME = APP_PREFIX + "://" + APP_DOMAIN + "/";
    public static Uri buildUri(String suffix) {
        return Uri.parse(SCHEME + suffix);
    }

    private void viewImage1() {
        // 通过 scheme 发送 Intent
        Intent intent = new Intent(Intent.ACTION_VIEW, buildUri("page1"));
        startActivity(intent);
    }

    private void viewImage2() {
        // 通过 scheme 发送 Intent
        Intent intent = new Intent(Intent.ACTION_VIEW, buildUri("page2"));
        startActivity(intent);
    }


    private static String packageName = "com.fri.sonicom.pmospluss";
//    private static String serviceName = "com.fri.sonicom.libraryfaceservice.second.service.PmosService";
    public final static String wakeupActivityName = "com.fri.sonicom.libraryfaceservice.second.wakeup.WakeupActivity";
    static public void startApk( Context ctx) {
        ComponentName componetName = new ComponentName(
                packageName,
                wakeupActivityName );
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(componetName);
        try {
            ctx.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}