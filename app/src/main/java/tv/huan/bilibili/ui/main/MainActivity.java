package tv.huan.bilibili.ui.main;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

import lib.kalu.frame.mvp.BaseActivity;
import lib.kalu.leanback.page.OnPageChangeListener;
import lib.kalu.leanback.page.PageView;
import lib.kalu.leanback.tab.TabLayout;
import lib.kalu.leanback.tab.listener.OnTabChangeListener;
import lib.kalu.leanback.tab.model.TabModel;
import tv.huan.bilibili.R;
import tv.huan.bilibili.dialog.ExitDialog;
import tv.huan.bilibili.widget.GeneralGridView;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {

    public static final String INTENT_SECOND_TAG = "intent_second_tag";
    public static final String INTENT_CID = "intent_cid";
    public static final String INTENT_CLASSID = "intent_classid";
    public static final String INTENT_TYPE = "intent_type";
    public static final int INTENT_TYPE_DEFAULT = 1;   // 首页
    public static final int INTENT_TYPE_DETAIL = 2;     // 专辑
    public static final int INTENT_TYPE_FILTER = 3;    // 筛选
    public static final int INTENT_TYPE_WEB = 4;    // 网页
    public static final int INTENT_TYPE_SPECIAL_1 = 5; // 专题1
    public static final int INTENT_TYPE_SPECIAL_2 = 6; // 专题2
    public static final int INTENT_TYPE_SPECIAL_3 = 7; // 专题3
    public static final int INTENT_TYPE_SPECIAL_4 = 8; // 专题4
    public static final int INTENT_TYPE_SPECIAL_5 = 9; // 专题5
    public static final int INTENT_TYPE_SPECIAL_6 = 10; // 专题6

    public static final String INTENT_SELECT = "intent_select";
    public static final String INTENT_TABS = "intent_tabs";

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return getPresenter().dispatchKey(event) || super.dispatchKeyEvent(event);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        // auto
        getPresenter().autoNext();
        // request
        getPresenter().showTabs();
        // listener
        PageView pageView = findViewById(R.id.main_content);
        pageView.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onLeft() {
                leftScroll();
            }

            @Override
            public void onRight() {
                rightScroll();
            }
        });
    }

    @Override
    public void refreshTabs(@NonNull List<TabModel> list, @NonNull int index) {
        TabLayout tabLayout = findViewById(R.id.main_tabs);
        tabLayout.setOnTabChangeListener(new OnTabChangeListener() {

            @Override
            public void onChecked(int i, int i1) {
                getPresenter().showBackground(i);
                getPresenter().showFragment(i);
            }
        });
        tabLayout.update(list, index);
    }

    @Override
    public void leftScroll() {
        TabLayout tabLayout = findViewById(R.id.main_tabs);
        tabLayout.scrollLeft();
        showTitle();
    }

    @Override
    public void rightScroll() {
        TabLayout tabLayout = findViewById(R.id.main_tabs);
        tabLayout.scrollRight();
        showTitle();
    }

    @Override
    public void tabScroll(int position) {
        TabLayout tabLayout = findViewById(R.id.main_tabs);
        tabLayout.scrollToPosition(1);
    }

    @Override
    public void showTitle() {
        setVisibility(R.id.main_search, View.VISIBLE);
        setVisibility(R.id.main_vip, View.VISIBLE);
        setVisibility(R.id.main_logo, View.VISIBLE);
    }

    @Override
    public void hideTitle() {
        setVisibility(R.id.main_search, View.GONE);
        setVisibility(R.id.main_vip, View.GONE);
        setVisibility(R.id.main_logo, View.GONE);
    }

    @Override
    public void contentScrollTop() {
        GeneralGridView gridView = findViewById(R.id.general_list);
        gridView.scrollTop();
    }

    @Override
    public void showDialog(@NonNull String data) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ExitDialog dialog = new ExitDialog();
        Bundle bundle = new Bundle();
        bundle.putString(ExitDialog.BUNDLE_DATA, data);
        dialog.setArguments(bundle);
        dialog.show(fragmentManager, "");
    }
}
