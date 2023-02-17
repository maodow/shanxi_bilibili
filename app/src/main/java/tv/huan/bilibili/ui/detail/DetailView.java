package tv.huan.bilibili.ui.detail;

import tv.huan.bilibili.base.BaseViewImpl;

public interface DetailView extends BaseViewImpl {

    void refreshContent();

    void updateVip(boolean show);
}
