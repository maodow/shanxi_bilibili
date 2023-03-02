package tv.huan.bilibili.ui.search;

import androidx.annotation.NonNull;

import tv.huan.bilibili.base.BaseViewImpl;

public interface SearchView extends BaseViewImpl {

    void refreshContent();

    void showTitle(@NonNull String s);
}
