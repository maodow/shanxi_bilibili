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

public final class GeneralGridView extends LeanBackVerticalGridView {
    public GeneralGridView(@NonNull Context context) {
        super(context);
    }

    public GeneralGridView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GeneralGridView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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
            if (state != RecyclerView.SCROLL_STATE_IDLE)
                return true;
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
