package tv.huan.bilibili.ui.main.mine;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import tv.huan.bilibili.base.BaseViewImpl;

@Keep
public interface MineView extends BaseViewImpl {

    void onHide();

    void onShow();

}
