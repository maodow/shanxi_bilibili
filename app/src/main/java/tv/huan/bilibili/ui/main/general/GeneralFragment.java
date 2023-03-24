package tv.huan.bilibili.ui.main.general;

import androidx.annotation.Keep;
import androidx.leanback.widget.Presenter;

import lib.kalu.frame.mvp.BaseFragment;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.FavBean;
import tv.huan.bilibili.ui.main.MainActivity;
import tv.huan.bilibili.widget.GeneralGridView;

@Keep
public class GeneralFragment extends BaseFragment<GeneralView, GeneralPresenter> implements GeneralView {

    public static final String BUNDLE_CHANNELID = "bundle_channelId";
    public static final String BUNDLE_CLASSID = "bundle_classId";
    public static final String BUNDLE_NAME = "bundle_name";
    public static final String BUNDLE_INDEX = "bundle_index";
    protected static final String BUNDLE_RESEAT = "bundle_reseat";

    @Override
    public int initLayout() {
        return R.layout.fragment_general;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().requestBookmark();
    }

    @Override
    public void initData() {
        // adapter
        getPresenter().setAdapter();
        // request
        getPresenter().request();
        // listener
        tv.huan.bilibili.widget.GeneralGridView gridView = findViewById(R.id.general_list);
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
    }

    @Override
    public void onHide() {
        tv.huan.bilibili.widget.GeneralGridView gridView = findViewById(R.id.general_list);
        gridView.pausePlayer();
    }

    @Override
    public void onShow() {
        tv.huan.bilibili.widget.GeneralGridView gridView = findViewById(R.id.general_list);
        gridView.resumePlayer();
    }

    @Override
    public void updateTemplateHistory(FavBean data) {
        GeneralGridView gridView = findViewById(R.id.general_list);
        gridView.updateTemplateHistory(data);
    }

    @Override
    public <T extends Presenter> void startPlayerFromHuawei(Class<T> cls, Class<?> obj, String s) {
        GeneralGridView gridView = findViewById(R.id.general_list);
        gridView.startPlayerFromHuawei(cls, obj, s);
    }
}
