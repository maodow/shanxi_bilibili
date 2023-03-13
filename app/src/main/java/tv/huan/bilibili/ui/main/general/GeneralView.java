package tv.huan.bilibili.ui.main.general;

import androidx.annotation.Keep;

import tv.huan.bilibili.base.BaseViewImpl;
import tv.huan.bilibili.bean.FavBean;

@Keep
public interface GeneralView extends BaseViewImpl {

    void refreshContent();

    void onHide();

    void onShow();

    void updateTemplateHistory(FavBean data);
}
