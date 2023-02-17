package tv.huan.bilibili.ui.center;

import android.view.KeyEvent;

import androidx.annotation.NonNull;

import java.util.List;

import lib.kalu.frame.mvp.BaseActivity;
import tv.huan.bilibili.R;

public class CenterActivity extends BaseActivity<CenterView, CenterPresenter> implements CenterView {

    public static final String INTENT_SELECT = "intent_select";

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
        getPresenter().setTabs();
        getPresenter().setAdapter();
        getPresenter().requestData();
    }

    @Override
    public void refreshContent() {
        notifyDataSetChanged(R.id.center_list);
    }

    @Override
    public void del(@NonNull int index) {
        notifyItemRemoved(R.id.center_list, index);
    }
}
