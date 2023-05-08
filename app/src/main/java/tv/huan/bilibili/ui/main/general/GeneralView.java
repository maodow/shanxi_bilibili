package tv.huan.bilibili.ui.main.general;

import androidx.annotation.Keep;

import tv.huan.bilibili.base.BaseViewImpl;
import tv.huan.bilibili.bean.FavBean;

@Keep
public interface GeneralView extends BaseViewImpl {

    void onHide();

    void onShow();

    void onRelease();

    void updateTemplateHistory(FavBean data);

    <T extends androidx.leanback.widget.Presenter> void startPlayerFromHuawei(Class<T> cls, Class<?> obj, String s);
}
