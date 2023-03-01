
package tv.huan.bilibili.ui.webview;

import android.graphics.Color;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import lib.kalu.frame.mvp.BaseActivity;
import tv.huan.bilibili.R;

public final class WebviewActivity extends BaseActivity<WebviewView, WebviewPresenter> implements WebviewView {

    public static String INTENT_URL = "intent_url";
    public static String INTENT_HELP = "intent_help";
    public static String INTENT_HELP_TYPE = "intent_help_type";

    public int initLayout() {
        return R.layout.activity_webview;
    }

    @Override
    public void initData() {
        getPresenter().initWebView();
        getPresenter().request();
    }

    @Override
    public void showUrl(@NonNull String s) {
        if (null == s || s.length() <= 0)
            return;
        WebView webView = findViewById(R.id.webview_html);
        webView.loadUrl(s);
    }

//    @Override
//    public void showHtml(@NonNull String s) {
//        if (null == s || s.length() <= 0)
//            return;
//        WebView webView = findViewById(R.id.webview);
//        webView.loadData(s, "text/html", "UTF-8");
//    }
}