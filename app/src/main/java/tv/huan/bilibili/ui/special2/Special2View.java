package tv.huan.bilibili.ui.special2;

import androidx.annotation.NonNull;

import tv.huan.bilibili.base.BaseViewImpl;

public interface Special2View extends BaseViewImpl {

    void refreshCards();

    void refreshBackground(@NonNull String url);

    void startPlayer(@NonNull String url, @NonNull String name, @NonNull String info);

    void requestDefault();
}
