package tv.huan.bilibili.ui.center;

import android.view.KeyEvent;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import lib.kalu.frame.mvp.BaseActivity;
import lib.kalu.leanback.clazz.ClassBean;
import lib.kalu.leanback.clazz.ClassScrollView;
import lib.kalu.leanback.clazz.OnCheckedChangeListener;
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
    protected void onRestart() {
        super.onRestart();
        ClassScrollView classLayout = findViewById(R.id.center_tabs);
        int checkedIndex = classLayout.getCheckedIndex();
        getPresenter().request(checkedIndex, true, true);
    }

    @Override
    public void updateTab(ArrayList<ClassBean> data, int select) {
        ClassScrollView classLayout = findViewById(R.id.center_tabs);
        classLayout.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onChecked(@NonNull boolean b, @NonNull int i, @NonNull String s, @NonNull String s1) {
                getPresenter().request(i, b, true);
            }
        });
        classLayout.update(data, select);
    }
}
