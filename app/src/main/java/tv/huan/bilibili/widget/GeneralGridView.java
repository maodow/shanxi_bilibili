package tv.huan.bilibili.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lib.kalu.leanback.list.LeanBackVerticalGridView;
import lib.kalu.leanback.list.RecyclerView;
import tv.huan.bilibili.utils.LogUtil;

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

        return super.dispatchKeyEvent(event);
    }

    public void scrollTop() {
        scrollUp(0);
    }
}
