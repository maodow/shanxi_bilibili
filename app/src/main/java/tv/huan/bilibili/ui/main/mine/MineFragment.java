package tv.huan.bilibili.ui.main.mine;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.recyclerview.widget.RecyclerView;

import lib.kalu.frame.mvp.BaseFragment;
import lib.kalu.leanback.list.RecyclerViewVertical;
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
        getPresenter().request();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().updateFavor();
        getPresenter().updateHistory();
    }

    @Override
    public void onHide() {
        setVisibility(getView(), View.GONE);
    }

    @Override
    public void onShow() {
        setVisibility(getView(), View.VISIBLE);
        getPresenter().updateFavor();
        getPresenter().updateHistory();
    }

    @Override
    public void requestFocusPosition(int position, int id) {
        Toast.makeText(getContext(), "刷新焦点 => " + position, Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                RecyclerViewVertical recyclerView = getView().findViewById(R.id.mine_list);
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                viewHolder.itemView.requestFocus();
//                recyclerView.requestFocusChild(position, id);
            }
        }, 100);
    }
}
