package tv.huan.bilibili.widget.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lib.kalu.leanback.plus.TextViewPlus;
import tv.huan.heilongjiang.HeilongjiangUtil;

public final class CommomDingGouView extends TextViewPlus {
    public CommomDingGouView(@NonNull Context context) {
        super(context);
        init();
    }

    public CommomDingGouView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommomDingGouView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        setVisibility(View.GONE);
    }

    private void checkVip() {
        boolean containsVip = HeilongjiangUtil.getVipStatus();
        setVisibility(!containsVip ? View.VISIBLE : View.GONE);
    }
}
