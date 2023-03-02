package tv.huan.bilibili.ui.main.mine;

import androidx.annotation.Keep;

import lib.kalu.frame.mvp.BaseFragment;
import tv.huan.bilibili.R;

@Keep
public class MineFragment extends BaseFragment<MineView, MinePresenter> implements MineView {

    @Override
    public int initLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initData() {
        // adapter
        getPresenter().setAdapter();
        // http
        getPresenter().request(false);
    }

    @Override
    public void refreshContent() {
        notifyDataSetChanged(R.id.mine_list);
    }

    @Override
    public void onUpdate() {
        getPresenter().request(true);
    }
}
