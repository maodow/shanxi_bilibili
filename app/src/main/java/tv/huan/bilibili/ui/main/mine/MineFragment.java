package tv.huan.bilibili.ui.main.mine;

import androidx.annotation.Keep;

import lib.kalu.frame.mvp.BaseFragment;
import tv.huan.bilibili.R;
import tv.huan.bilibili.utils.LogUtil;

@Keep
public class MineFragment extends BaseFragment<MineView, MinePresenter> implements MineView {

    @Override
    public int initLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initData() {
        getPresenter().setAdapter();
        getPresenter().request();
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.log("MineFragment => onResume => " + this);
        getPresenter().refresh();
    }

    @Override
    public void refreshContent() {
        notifyDataSetChanged(R.id.mine_list);
    }

    @Override
    public void refreshContent(int start, int length) {
        LogUtil.log("MineFragment => onResume => start = " + start + ", length = " + length);
        notifyItemRangeChanged(R.id.mine_list, start, length);
    }
}
