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
import tv.huan.bilibili.utils.LogUtil;

@Keep
public class MineFragment extends BaseFragment<MineView, MinePresenter> implements MineView {

    protected final static String BUNDLE_JUMP_HISTRRY = "bundle_jump_histrry";
    protected final static String BUNDLE_JUMP_FAV = "bundle_jump_fav";

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

    @Override
    public void scrollTop() {
        RecyclerViewVertical recyclerView = getView().findViewById(R.id.mine_list);
        recyclerView.scrollTop(false);
    }

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 3001) {
                try {
                    RecyclerViewVertical recyclerView = getView().findViewById(R.id.mine_list);
                    RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(msg.arg1);
                    if (null == viewHolder)
                        throw new Exception("viewHolder error: null");
                    viewHolder.itemView.requestFocus();
                } catch (Exception e) {
                    LogUtil.log("MineFragment => handleMessage => " + e.getMessage());
                }
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
