package tv.huan.bilibili.widget.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import tv.huan.bilibili.R;
import tv.huan.heilongjiang.HeilongjiangUtil;

@SuppressLint("AppCompatCustomView")
public final class CommomDataView extends TextView {
    public CommomDataView(@NonNull Context context) {
        super(context);
        init();
    }

    public CommomDataView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommomDataView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.VISIBLE) {
            checkVip();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        checkVip();
    }


    private void init() {
        setVisibility(View.INVISIBLE);
    }

    private void checkVip() {
        boolean containsVip = HeilongjiangUtil.getVipStatus();
        setVisibility(containsVip ? View.VISIBLE : View.INVISIBLE);
    }
}
