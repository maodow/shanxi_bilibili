package tv.huan.bilibili.ui.center;

import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import lib.kalu.frame.mvp.BaseActivity;
import lib.kalu.leanback.clazz.ClassBean;
import lib.kalu.leanback.clazz.ClassScrollView;
import lib.kalu.leanback.clazz.OnCheckedChangeListener;
import tv.huan.bilibili.R;
import tv.huan.bilibili.utils.LogUtil;

public class CenterActivity extends BaseActivity<CenterView, CenterPresenter> implements CenterView {

    public static final String INTENT_FAVORY = "intent_favory"; // 我的收藏

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return getPresenter().dispatchKey(event) || super.dispatchKeyEvent(event);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_center;
    }

    @Override
    public void initData() {
        getPresenter().showWarning();
        getPresenter().setAdapter();
        getPresenter().requestTabs();
    }

    @Override
    public void updateTab(ArrayList<ClassBean> data, int select) {
        ClassScrollView classLayout = findViewById(R.id.center_tabs);
        classLayout.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onChecked(@NonNull boolean b, @NonNull int i, @NonNull String s, @NonNull String s1) {
                LogUtil.log("CenterActivity => onChecked => isFromUser = " + b + ", index = " + i + ", name = " + s);
                getPresenter().request(i, b, true);
            }
        });
        classLayout.update(data, select);
    }

    @Override
    public void refreshContent(int start, int num) {
        notifyItemRangeInserted(R.id.center_list, start, num);
    }

    @Override
    public void updateFocus() {
        setFocusable(R.id.center_search, true);
        setFocusable(R.id.center_vip, true);
    }

    @Override
    public void checkNodata(boolean hasData) {
        setVisibility(R.id.center_nodata, hasData ? View.GONE : View.VISIBLE);
    }

    @Override
    public void updatePosition(int position) {
        notifyItemRangeChanged(R.id.center_list, position, 1);
    }

    @Override
    public void deletePosition(int position) {
        notifyItemRangeRemoved(R.id.center_list, position, 1);
    }

    @Override
    public void reqFocus(boolean hasData) {
        requestFocus(hasData ? R.id.center_list : R.id.center_tabs);
    }
}
