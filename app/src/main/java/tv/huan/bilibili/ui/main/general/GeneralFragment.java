package tv.huan.bilibili.ui.main.general;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.widget.Presenter;

import lib.kalu.frame.mvp.BaseFragment;
import tv.huan.bilibili.R;
import tv.huan.bilibili.ui.main.MainActivity;
import tv.huan.bilibili.widget.GeneralGridView;

@Keep
public class GeneralFragment extends BaseFragment<GeneralView, GeneralPresenter> implements GeneralView {

    public static final String BUNDLE_POSTER = "bundle_poster";
    public static final String BUNDLE_CHANNELID = "bundle_channelId";
    public static final String BUNDLE_CLASSID = "bundle_classId";
    public static final String BUNDLE_NAME = "bundle_name";
    public static final String BUNDLE_INDEX = "bundle_index";

    @Override
    public int initLayout() {
        return R.layout.fragment_general;
    }

    @Override
    public void initData() {
        getPresenter().setAdapter();
        getPresenter().request();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            tv.huan.bilibili.widget.GeneralGridView gridView = view.findViewById(R.id.general_list);
            gridView.setOnScrollTopListener(new GeneralGridView.OnScrollTopListener() {
                @Override
                public void onShow() {
                    ((MainActivity) getActivity()).showTitle();
                }

                @Override
                public void onHide() {
                    ((MainActivity) getActivity()).hideTitle();
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    public void onHide() {
        setVisibility(getView(), View.GONE);
        tv.huan.bilibili.widget.GeneralGridView gridView = findViewById(R.id.general_list);
        gridView.pauseMessage();
        gridView.cleanTemplatePlayerMessageDelayed();
        gridView.releasePlayer();
    }

    @Override
    public void onShow() {
        setVisibility(getView(), View.VISIBLE);
        getPresenter().showBackground();
        tv.huan.bilibili.widget.GeneralGridView gridView = findViewById(R.id.general_list);
//        gridView.scrollTop();
        gridView.updateTemplateHistory();
        gridView.resumeMessage();
        gridView.cleanTemplatePlayerMessageDelayed();
        gridView.restartPlayer();
    }

    @Override
    public void onRelease() {
        tv.huan.bilibili.widget.GeneralGridView gridView = findViewById(R.id.general_list);
        gridView.releasePlayer();
    }

    @Override
    public void scrollTop() {
        tv.huan.bilibili.widget.GeneralGridView gridView = findViewById(R.id.general_list);
        gridView.scrollTop(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        GeneralGridView gridView = findViewById(R.id.general_list);
        gridView.updateTemplateHistory();
    }

    @Override
    public <T extends Presenter> void startPlayerFromHuawei(Class<T> cls, Class<?> obj, String s) {
        GeneralGridView gridView = findViewById(R.id.general_list);
        gridView.startPlayerFromHuawei(cls, obj, s);
    }
}
