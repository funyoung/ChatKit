package com.stfalcon.chatkit.sample.features.home;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.stfalcon.chatkit.sample.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 为功能按钮设置点击事件
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

    private void setFeatureButtonClickListener(int viewId, String featureName) {
        findViewById(viewId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理功能按钮点击事件
                // 这里可以添加跳转到对应功能页面的代码
                System.out.println("Clicked on " + featureName);
                int id = v.getId();
                if (R.id.tv_person_search == id) {
                    //startApk(MainActivity.this);
                    viewImage1();
//                } else if (R.id.tv_person_auth == id) {
//                    viewImage2();
//                } else if (R.id.tv_person_compare == id) {
//                    actionImage1();
                } else if (R.id.tv_id_verify == id) {
                    actionImage2();
                } else if (R.id.tv_my_knowledge == id) {
                } else if (R.id.tv_shared_knowledge == id) {
                } else if (R.id.tv_law_consult == id) {

                } else if (R.id.tv_ocr == id) {
                } else if (R.id.tv_tts == id) {
//                } else if (R.id.tv_translation == id) {
                } else {

                }
            }
        });
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

    private void viewImage1() {
        // 通过 scheme 发送 Intent
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("yourapp://example.com/page1"));
        startActivity(intent);
    }

    private void viewImage2() {
        // 通过 scheme 发送 Intent
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("yourapp://example.com/page2"));
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