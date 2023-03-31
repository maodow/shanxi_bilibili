package tv.huan.bilibili.ui.webview;

import androidx.annotation.NonNull;

import tv.huan.bilibili.base.BaseViewImpl;

public interface WebviewView extends BaseViewImpl {

    void showUrl(@NonNull String s);

    void showHtml(@NonNull String s);
}
