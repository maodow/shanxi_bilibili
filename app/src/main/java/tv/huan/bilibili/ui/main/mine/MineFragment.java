package tv.huan.bilibili.ui.main.mine;

import android.view.View;

import androidx.annotation.Keep;

import lib.kalu.frame.mvp.BaseFragment;
import tv.huan.bilibili.R;

@Keep
public class MineFragment extends BaseFragment<MineView, MinePresenter> implements MineView {

    protected final static String BUNDLE_REFRESH = "bundle_refresh";

    @Override
    public int initLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initData() {
        getPresenter().setAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().request();
    }

    @Override
    public void onHide() {
        setVisibility(getView(), View.GONE);
    }

    @Override
    public void onShow() {
        setVisibility(getView(), View.VISIBLE);
    }
}
