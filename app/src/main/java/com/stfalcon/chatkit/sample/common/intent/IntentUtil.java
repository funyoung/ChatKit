package com.stfalcon.chatkit.sample.common.intent;

import static com.stfalcon.chatkit.sample.common.intent.Schema.PMOS_ACTION_IDREAD;
import static com.stfalcon.chatkit.sample.common.intent.Schema.PMOS_ACTION_LIVEFACE;
import static com.stfalcon.chatkit.sample.common.intent.Schema.PMOS_ACTION_SERVICE_DOC_SUMMARY;
import static com.stfalcon.chatkit.sample.common.intent.Schema.PMOS_ACTION_SERVICE_MORE;
import static com.stfalcon.chatkit.sample.common.intent.Schema.PMOS_ACTION_SERVICE_OCR;
import static com.stfalcon.chatkit.sample.common.intent.Schema.PMOS_ACTION_SERVICE_TRANSLATION;
import static com.stfalcon.chatkit.sample.common.intent.Schema.PMOS_ACTION_SERVICE_TTS;
import static com.stfalcon.chatkit.sample.common.intent.Schema.PMOS_ACTION_WIKI_LAW;
import static com.stfalcon.chatkit.sample.common.intent.Schema.PMOS_ACTION_WIKI_MINE;
import static com.stfalcon.chatkit.sample.common.intent.Schema.PMOS_ACTION_WIKI_TEAM;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.stfalcon.chatkit.sample.features.home.HomeActivity;
import com.stfalcon.chatkit.sample.features.main.MainActivity;
import com.stfalcon.chatkit.sample.features.chat.ChatMessagesActivity;
import com.stfalcon.chatkit.sample.features.wiki.MyWikiActivity;
import com.stfalcon.chatkit.sample.features.wiki.PublicWikiActivity;
import com.stfalcon.chatkit.sample.features.wiki.TeamWikiActivity;

import java.util.List;

public class IntentUtil {
    public static Intent buildIntent(String action) {
        return new Intent(action);
    }

    // 发出View的intent, uri的目标是schema和path。
    public static void dispatch(Activity activity, String schema, String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Schema.buildUri(schema, path));
        try {
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getSanitizedPath(Uri uri) {
        if (uri == null) return ""; // 防御空指针

        List<String> segments = uri.getPathSegments();
        if (segments.isEmpty()) {
            return "";
        }

        return String.join("/", segments);
    }

    public static boolean handleIntent(Activity activity, Uri uri) {
        if (uri != null) {
            String path = getSanitizedPath(uri);
            String schema = uri.getScheme();
            // 处理查询参数（例如：myapp://example.com/page1?param=value）
            String param = uri.getQueryParameter("param");  // todo????
            return handleIntent(activity, schema, path);
        }
        return false;
    }

    public static boolean handleIntent(Activity activity, String schema, String path) {
        if (Schema.APP_PMOS.equalsIgnoreCase(schema)) {
            return handlePmosIntent(activity, path);
        } else if (Schema.APP_WIKI.equalsIgnoreCase(schema)) {
            return handleWikiIntent(activity, path);
        } else if (Schema.APP_TOOL.equalsIgnoreCase(schema)) {
            return handleToolIntent(activity, path);
        } else {
            // unknown schema, skipped
        }
        return false;
    }

    // todo: 检查安装包，格式化启动intent.
    private static void startPmosFaceQuery(Activity activity) {
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("yourapp://example.com/page1"));
        Intent intent = new Intent(PMOS_ACTION_LIVEFACE);
        activity.startActivity(intent);
    }

    private static void startPmosIdVerify(Activity activity) {
        // 通过自定义 Action 发送 Intent
//        Intent intent = new Intent("com.example.ACTION2");
        Intent intent = new Intent(PMOS_ACTION_IDREAD);
        activity.startActivity(intent);
    }

    public static boolean handlePmosIntent(Activity activity, String path) {
        boolean result = false;
        if (path != null) {
            switch (path) {
                case Schema.PMOS_FACE:
                    startPmosFaceQuery(activity);
                    break;
                case Schema.PMOS_ID:
                    startPmosIdVerify(activity);
                    break;
                default:
                    // 处理未知路径或默认页
                    break;
            }
        }
        return result;
    }

    public static boolean handleWikiIntent(Activity activity, String path) {
        boolean result = false;
        if (path != null) {
            switch (path) {
                case Schema.WIKI_CHAT:
                    ChatMessagesActivity.open(activity);
                    result = true;
                    break;
                case Schema.WIKI_MINE:
                    MyWikiActivity.open(activity);
                    break;
                case Schema.WIKI_TEAM:
                    TeamWikiActivity.open(activity);
                    break;
                case Schema.WIKI_PUBLIC:
                    PublicWikiActivity.open(activity);
                    break;
                default:
                    // 处理未知路径或默认页
                    break;
            }
        }
        return result;
    }

    public static boolean handleToolIntent(Activity activity, String path) {
        boolean result = false;
        if (path != null) {
            switch (path) {
                case Schema.TOOL_OCR:
                    ChatMessagesActivity.openOcr(activity);
                    break;
                case Schema.TOOL_TTS:
                    ChatMessagesActivity.openTts(activity);
                    break;
                case Schema.TOOL_MORE:
                    MainActivity.open(activity);
                    result = true;
                    break;
                default:
                    // 处理未知路径或默认页
                    break;
            }
        }
        return result;
    }


    public static boolean handleIntentAction(Activity activity, String action) {
        try {
            if (PMOS_ACTION_WIKI_MINE.equalsIgnoreCase(action)) {
                emitActivity(activity, MyWikiActivity.class);
            } else if (PMOS_ACTION_WIKI_TEAM.equalsIgnoreCase(action)) {
                emitActivity(activity, TeamWikiActivity.class);
            } else if (PMOS_ACTION_WIKI_LAW.equalsIgnoreCase(action)) {
                emitActivity(activity, PublicWikiActivity.class);
            } else if (PMOS_ACTION_SERVICE_OCR.equalsIgnoreCase(action)) {
                ChatMessagesActivity.openOcr(activity);
            } else if (PMOS_ACTION_SERVICE_TTS.equalsIgnoreCase(action)) {
                ChatMessagesActivity.openTts(activity);
            } else if (PMOS_ACTION_SERVICE_TRANSLATION.equalsIgnoreCase(action)) {
                // todo
                Toast.makeText(activity, "翻译功能正在实现。", Toast.LENGTH_SHORT).show();
            } else if (PMOS_ACTION_SERVICE_DOC_SUMMARY.equalsIgnoreCase(action)) {
                // todo
                Toast.makeText(activity, "文档摘要功能即将上线", Toast.LENGTH_SHORT).show();
            } else if (PMOS_ACTION_SERVICE_MORE.equalsIgnoreCase(action)) {
                Toast.makeText(activity, "更多工具即将上线", Toast.LENGTH_SHORT).show();
                MainActivity.open(activity);
            } else {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static <T extends Activity> void emitActivity(Activity activity, Class<T> aClass) {
        activity.startActivity(new Intent(activity, aClass));
    }

    private static String pmosPackage = "com.fri.sonicom.pmospluss";
    //    private static String serviceName = "com.fri.sonicom.libraryfaceservice.second.service.PmosService";
    public final static String pmosWakeupActivity = "com.fri.sonicom.libraryfaceservice.second.wakeup.WakeupActivity";

    static public void startPmosApk(Context ctx) {
        startComponent(ctx, pmosPackage, pmosWakeupActivity);
    }

    static public void startComponent(Context ctx, String packageName, String activityName) {
        ComponentName componentName = new ComponentName(packageName, activityName);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(componentName);
        try {
            ctx.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
