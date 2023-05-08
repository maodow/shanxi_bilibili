package tv.huan.bilibili.ui.main;

import androidx.annotation.NonNull;

import java.util.List;

import lib.kalu.leanback.tab.model.TabModel;
import tv.huan.bilibili.base.BaseViewImpl;

public interface MainView extends BaseViewImpl {

    void refreshTabs(@NonNull List<TabModel> list, @NonNull int index);

    void leftScroll();

    void rightScroll();

    void tabScroll(int position);

    void showTitle();

    void hideTitle();

    void showDialog(@NonNull String data);

    void startFullPlayer();

    void stopFullPlayer();

    <T extends androidx.leanback.widget.Presenter> void huaweiAuth(Class<T> cls, Class<?> obj, String cid);

    <T extends androidx.leanback.widget.Presenter> void huaweiSucc(Class<T> cls, Class<?> obj, String s);
}
