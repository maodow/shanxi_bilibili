package tv.huan.bilibili.ui.main.general;

import androidx.annotation.Keep;

import tv.huan.bilibili.base.BaseViewImpl;

@Keep
public interface GeneralView extends BaseViewImpl {

    void refreshContent();

    void clearMessage();
}
