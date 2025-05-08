package com.stfalcon.chatkit.sample.common.intent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.stfalcon.chatkit.sample.features.chat.holder.HiSirMessagesActivity;
import com.stfalcon.chatkit.sample.features.main.MainActivity;

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

    public static boolean handlePmosIntent(Activity activity, String path) {
        boolean result = false;
        if (path != null) {
            switch (path) {
                case Schema.PMOS_FACE:
                    // 跳转到页面1

                    break;
                case Schema.PMOS_ID:
                    // 跳转到页面2或加载Fragment

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
                    HiSirMessagesActivity.open(activity);
                    result = true;
                    break;
                case Schema.WIKI_MINE:
                    // 跳转到页面2或加载Fragment

                    break;
                case Schema.WIKI_TEAM:
                    // 跳转到页面1

                    break;
                case Schema.WIKI_PUBLIC:
                    // 跳转到页面2或加载Fragment

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
                    break;
                case Schema.TOOL_TTS:
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
}
