package tv.huan.bilibili.ui.main.general;

import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lib.kalu.frame.mvp.BaseFragment;
import tv.huan.bilibili.R;
import tv.huan.bilibili.ui.main.MainActivity;
import tv.huan.bilibili.widget.GeneralGridView;

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
        tv.huan.bilibili.widget.GeneralGridView  gridView = findViewById(R.id.general_list);
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
    public void clearMessage() {
        Log.e("GeneralFragment", "clearMessage => this = " + this);
    }

    @Override
    public void refreshContent() {
        Log.e("GeneralFragment", "refreshContent => " + R.id.general_list);
        notifyDataSetChanged(R.id.general_list);
    }
}
