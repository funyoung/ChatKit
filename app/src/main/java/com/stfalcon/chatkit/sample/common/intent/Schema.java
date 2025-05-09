package com.stfalcon.chatkit.sample.common.intent;

import android.net.Uri;

import java.util.Map;

//
//1. 配置AndroidManifest.xml
//在MainActivity中添加Intent Filter，处理VIEW动作并定义自定义URI结构：
//<activity
//android:name=".MainActivity"
//android:launchMode="singleTop">
//    <intent-filter>
//        <action android:name="android.intent.action.VIEW" />
//        <category android:name="android.intent.category.DEFAULT" />
//        <category android:name="android.intent.category.BROWSABLE" />
//        <!-- 定义URI scheme和host -->
//        <data android:scheme="hisir" android:host="aiassistant.fri.phos" />
//    </intent-filter>
//</activity>
//2. 处理Intent的接收
//        在MainActivity中，通过onCreate和onNewIntent处理传入的Intent：
//private void handleIntent(Intent intent) {
//    if (Intent.ACTION_VIEW.equals(intent.getAction())) {
//        Uri uri = intent.getData();
//        if (uri != null) {
//            String path = uri.getPath();
//            if (path != null) {
//                switch (path) {
//                    case "/page1":
//                        // 跳转到页面1
//                        startActivity(new Intent(this, Page1Activity.class));
//                        break;
//                    case "/page2":
//                        // 跳转到页面2或加载Fragment
//                        loadFragment(new Page2Fragment());
//                        break;
//                    default:
//                        // 处理未知路径或默认页
//                        break;
//                }
//            }
//            // 处理查询参数（例如：myapp://example.com/page1?param=value）
//            String param = uri.getQueryParameter("param");
//        }
//    }
//}
//3. 安全考虑（可选）
//        验证调用者身份，防止恶意应用调用：
//private boolean verifyCaller() {
//    String callerPackage = getCallingPackage();
//    // 检查callerPackage是否在白名单中
//    return trustedPackages.contains(callerPackage);
//}
//
//// 在handleIntent中添加验证
//if (!verifyCaller()) {
//finish();
//    return;
//            }
//4. 测试Intent
//        使用ADB命令测试不同路径的Intent：
//# 跳转到page1
//        adb shell am start -d "myapp://example.com/page1" -a android.intent.action.VIEW
//
//        # 跳转到page2并带参数
//        adb shell am start -d "myapp://example.com/page2?param=test" -a android.intent.action.VIEW
//import android.net.Uri;
//关键点说明
//launchMode设置：使用singleTop避免重复创建Activity实例。
//
//URI解析：通过Uri.getPath()和getQueryParameter()获取路径和参数。
//
//安全性：必要时验证调用包名，防止未授权访问。
//
//Fragment处理：根据路径动态加载Fragment实现页面切换。

public class Schema {
    public static final String PMOS_ACTION_LIVEFACE = "phos.fri.aiassistant.ACTION.FACE_QUERY";

    public static final String PMOS_ACTION_IDREAD = "com.fri.idcard.ACTION.IDREAD";

    // phos.fri.aiassistant
    public static final String APP_WIKI = "wiki";  // 知识库
    public static final String APP_PMOS = "pmos";  // 人像核验和身份证
    public static final String APP_TOOL = "tool";  // ocr, tts, 更多

    public static final String WIKI_CHAT = "chat";  // 无知识库尬聊
    public static final String WIKI_MINE = "mine";  // 我的个人知识库
    public static final String WIKI_TEAM = "team";  // 团队共享知识库
    public static final String WIKI_PUBLIC = "public"; // 法律法规公开知识库


    public static final String PMOS_FACE = "face";  // 人像脸核验
    public static final String PMOS_ID = "id";  // 身份证核查

    public static final String TOOL_OCR = "ocr"; // 文件ocr识别
    public static final String TOOL_TTS = "tts"; // 文本转语音识别
    public static final String TOOL_MORE = "more"; // 更多

    public static final String DOMAIN = "aiassistant.fri.phos";
//    public static final String SCHEME = "hisir://aiassistant.fri.phos/";
//    public static final String SCHEME = APP_HISIR + "://" + APP_DOMAIN + "/";

//    public static Uri buildUri(String suffix) {
//        return buildUri(APP_WIKI, suffix);
//    }

    public static Uri buildUri(String appPrefix, String suffix) {
//        return Uri.parse(appPrefix + "://" + APP_DOMAIN + "/" + suffix);
        return buildUri(appPrefix, DOMAIN, suffix);
    }
    public static Uri buildUri(String scheme, String domain, String path) {
        return new Uri.Builder()
                .scheme(scheme)
                .authority(domain)
                .appendPath(path)
                .build();
    }
    public static Uri buildUriWithPaths(String scheme, String... pathSegments) {
        Uri.Builder builder = new Uri.Builder().scheme(scheme).authority(DOMAIN);
        for (String segment : pathSegments) {
            builder.appendPath(segment);
        }
        return builder.build();
    }
// 使用：buildUriWithPaths("myapp", "api", "v1", "users")
// 生成：myapp://example.com/api/v1/users

    public static Uri buildUriWithQuery(String scheme, String path, Map<String, String> params) {
        Uri.Builder builder = new Uri.Builder()
                .scheme(scheme)
                .authority(DOMAIN)
                .appendPath(path);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }

        return builder.build();
    }
}
