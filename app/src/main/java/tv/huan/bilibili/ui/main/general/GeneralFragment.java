package tv.huan.bilibili.ui.main.general;

import androidx.annotation.Keep;

import lib.kalu.frame.mvp.BaseFragment;
import tv.huan.bilibili.R;
import tv.huan.bilibili.ui.main.MainActivity;
import tv.huan.bilibili.utils.LogUtil;
import tv.huan.bilibili.widget.GeneralGridView;
import tv.huan.bilibili.widget.player.PlayerView;

@Keep
public class GeneralFragment extends BaseFragment<GeneralView, GeneralPresenter> implements GeneralView {

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
    public void refreshContent() {
        notifyDataSetChanged(R.id.general_list);
    }

    @Override
    public void onHide() {
        LogUtil.log("GeneralFragment => onHide => " + this);
        PlayerView v1 = findViewById(R.id.general_template21_player);
        if (null != v1) {
            v1.pause();
        }
        PlayerView v2 = findViewById(R.id.general_template22_player);
        if (null != v2) {
            v2.pause();
        }
        GeneralGridView v3 = findViewById(R.id.general_list);
        if (null != v3) {
            v3.pauseMessage();
        }
    }

    @Override
    public void onShow() {
        LogUtil.log("GeneralFragment => onShow => " + this);
        PlayerView v1 = findViewById(R.id.general_template21_player);
        if (null != v1) {
            v1.resume();
        }
        PlayerView v2 = findViewById(R.id.general_template22_player);
        if (null != v2) {
            v2.resume();
        }
        GeneralGridView v3 = findViewById(R.id.general_list);
        if (null != v3) {
            v3.resumeMessage();
        }
    }
}
