package tv.huan.bilibili.ui.main.mine;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
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
        getPresenter().updateCard();
    }

    @Override
    public void onHide() {
        mHandler.removeCallbacksAndMessages(null);
        setVisibility(getView(), View.GONE);
    }

    @Override
    public void onShow() {
        setVisibility(getView(), View.VISIBLE);
        getPresenter().updateCard();
    }

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 3001) {
                RecyclerViewVertical recyclerView = getView().findViewById(R.id.mine_list);
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(msg.arg1);
                viewHolder.itemView.requestFocus();
            }
        }
    };

    @Override
    public void requestFocusPosition(int position) {
        Message message = new Message();
        message.what = 3001;
        message.arg1 = position;
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(message, 100);
    }
}
