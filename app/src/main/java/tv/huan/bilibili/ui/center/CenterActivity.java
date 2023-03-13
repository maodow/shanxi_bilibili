package tv.huan.bilibili.ui.center;

import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import lib.kalu.frame.mvp.BaseActivity;
import lib.kalu.leanback.clazz.ClassBean;
import lib.kalu.leanback.clazz.HorizontalClassLayout;
import tv.huan.bilibili.R;

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
        HorizontalClassLayout classLayout = findViewById(R.id.center_tabs);
        classLayout.update(data);
        classLayout.setOnCheckedChangeListener(new HorizontalClassLayout.OnCheckedChangeListener() {
            @Override
            public void onChecked(@NonNull int i, @NonNull String s, @NonNull String s1) {
                getPresenter().request(i);
            }
        });
        classLayout.requestFocus(select, true);
    }

    @Override
    public void refreshContent() {
        notifyDataSetChanged(R.id.center_list);
    }

    @Override
    public void updateFocus() {
        setFocusable(R.id.center_search, true);
        setFocusable(R.id.center_vip, true);
    }

    @Override
    public void checkNodata(boolean show) {
        setVisibility(R.id.center_nodata, show ? View.VISIBLE : View.GONE);
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
    public void requestTab() {
        HorizontalClassLayout classLayout = findViewById(R.id.center_tabs);
        classLayout.requestFocus(false);
    }
}
