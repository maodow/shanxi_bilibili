package tv.huan.bilibili.ui.main.mine;

import androidx.annotation.Keep;

import tv.huan.bilibili.base.BaseViewImpl;

@Keep
public interface MineView extends BaseViewImpl {

    void refreshContent(boolean update, int size);

    void onUpdate();
}
