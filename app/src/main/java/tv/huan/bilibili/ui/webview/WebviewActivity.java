
package tv.huan.bilibili.ui.webview;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import tv.huan.bilibili.R;

public final class WebviewActivity extends Activity {

    public static String INTENT_URL = "intent_url";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        String extra = getIntent().getStringExtra(INTENT_URL);
        if (null == extra || extra.length() <= 0) {
            onBackPressed();
        } else {
            WebView webView = findViewById(R.id.webview);
            webView.loadUrl(extra);
        }
    }
}