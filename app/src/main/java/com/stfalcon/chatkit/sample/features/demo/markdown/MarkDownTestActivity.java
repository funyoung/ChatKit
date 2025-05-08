package com.stfalcon.chatkit.sample.features.demo.markdown;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.stfalcon.chatkit.sample.R;

import io.noties.markwon.Markwon;
import io.noties.markwon.ext.tables.TableAwareMovementMethod;
import io.noties.markwon.ext.tables.TablePlugin;
import io.noties.markwon.linkify.LinkifyPlugin;
import io.noties.markwon.movement.MovementMethodPlugin;

public class MarkDownTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        TextView text = findViewById(R.id.text);
        render(text);
    }

    private void renderMarkdown(TextView textView, String markdown) {
        Markwon markwon = Markwon.builder(getApplicationContext())
                .usePlugin(TablePlugin.create(getApplicationContext()))
                .build();
        markwon.setMarkdown(textView, markdown);
    }

    private void render(TextView textView) {
        String md2 = "| 房型        | 门市价   | 房型总数 | 退房订单数 | 入住订单数      | 空闲房间数 | 入住率 | 房型收入 |\n" +
                "|:----------:|:------:|:---:|:---:|:---:|:---:|:---:|:---:|\n" +
                "| 商务大床房 | 158 | 20 | 8 | 13 | 7 | 65.0% | 1989.0 |\n" +
                "| 标准大床房 | 158 | 19 | 7 | 19 | 0 | 100.0% | 2358.0 |\n" +
                "| 精致双床房 | 188 | 2 | 1 | 2 | 0 | 100.0% | 292.0 |\n" +
                "| 豪华商务双床房 | 198 | 5 | 4 | 5 | 0 | 100.0% | 782.0 |\n\n" +
                "测试\n\n" +
                "[link link](https://link.link)";

        Markwon markwon = Markwon.builder(getApplicationContext())
                .usePlugin(LinkifyPlugin.create())
                .usePlugin(TablePlugin.create(getApplicationContext()))
                .usePlugin(MovementMethodPlugin.create(TableAwareMovementMethod.create()))
                .build();

        markwon.setMarkdown(textView, md2);
    }
}

