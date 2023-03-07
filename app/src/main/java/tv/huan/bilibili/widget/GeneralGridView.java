package tv.huan.bilibili.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lib.kalu.leanback.list.LeanBackVerticalGridView;
import lib.kalu.leanback.list.RecyclerView;
import lib.kalu.leanback.util.LeanBackUtil;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate1;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate16;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate17;
import tv.huan.bilibili.utils.GlideUtils;

public final class GeneralGridView extends LeanBackVerticalGridView {
    public GeneralGridView(@NonNull Context context) {
        super(context);
        init();
    }

    public GeneralGridView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GeneralGridView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
//        addOnScrollListener(new OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull androidx.recyclerview.widget.RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    GlideUtils.resumeRequests(getContext());
//                } else {
//                    GlideUtils.pauseRequests(getContext());
//                }
//            }
//        });
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {

//        try {
//            int repeatCount = event.getRepeatCount();
//            if (repeatCount > 0)
//                return true;
//        } catch (Exception e) {
//        }

        try {
            // 停止滚动 SCROLL_STATE_IDLE
            // 正在被外部拖拽,一般为用户正在用手指滚动 SCROLL_STATE_DRAGGING
            // 自动滚动开始 SCROLL_STATE_SETTLING
            int state = getScrollState();
            if (state != RecyclerView.SCROLL_STATE_IDLE) {
                //GlideUtils.pauseRequests(getContext());
                return true;
            } else {
                //GlideUtils.resumeRequests(getContext());
            }
        } catch (Exception e) {
        }

        // down
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            if (null != mOnScrollTopListener) {
                int focusedChildPosition = getFocusedChildPosition();
                if (focusedChildPosition >= 1) {
                    mOnScrollTopListener.onHide();
                }
            }
        }
        // up
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            if (null != mOnScrollTopListener) {
                ViewHolder viewHolder = findViewHolderForLayoutPosition(1);
                if (null != viewHolder && null != viewHolder.itemView && viewHolder.itemView.getVisibility() == View.VISIBLE) {
                    mOnScrollTopListener.onShow();
                }
            }
        }

        return super.dispatchKeyEvent(event);
    }

    private int getFocusedChildPosition() {
        try {
            View focusedChild = getFocusedChild();
            if (null == focusedChild)
                throw new Exception("focusedChild is null");
            int position = -1;
            while (true) {
                ViewParent parent = focusedChild.getParent();
                if (parent instanceof GeneralGridView) {
                    position = getChildAdapterPosition(focusedChild);
                    break;
                }
                focusedChild = (View) parent;
            }
            if (position == -1)
                throw new Exception("position error: " + position);
            return position;
        } catch (Exception e) {
            LeanBackUtil.log("GeneralGridView => getFocusedChildPosition => " + e.getMessage());
            return -1;
        }
    }

    public void scrollTop() {
        if (null != mOnScrollTopListener) {
            mOnScrollTopListener.onShow();
        }
        scrollUp(0);
    }

//    public boolean isTop() {
//        try {
//            ViewHolder viewHolder = findViewHolderForLayoutPosition(0);
//            viewHolder.itemView;
//        } catch (Exception e) {
//            return false;
//        }
//    }

    public void pauseMessage() {
        GeneralTemplate17 template17 = getPresenter(GeneralTemplate17.class);
        if (null != template17) {
            template17.pauseMessage();
        }
        GeneralTemplate16 template16 = getPresenter(GeneralTemplate16.class);
        if (null != template16) {
            template16.pauseMessage();
        }
        GeneralTemplate1 template1 = getPresenter(GeneralTemplate1.class);
        if (null != template1) {
            template1.pauseMessage();
        }
    }

    public void resumeMessage() {
        GeneralTemplate17 template17 = getPresenter(GeneralTemplate17.class);
        if (null != template17) {
            template17.resumeMessage();
        }
        GeneralTemplate16 template16 = getPresenter(GeneralTemplate16.class);
        if (null != template16) {
            template16.resumeMessage();
        }
        GeneralTemplate1 template1 = getPresenter(GeneralTemplate1.class);
        if (null != template1) {
            template1.resumeMessage();
        }
    }

    /**************************/

    private OnScrollTopListener mOnScrollTopListener;

    public void setOnScrollTopListener(OnScrollTopListener l) {
        mOnScrollTopListener = l;
    }

    public interface OnScrollTopListener {
        void onShow();

        void onHide();
    }
}
