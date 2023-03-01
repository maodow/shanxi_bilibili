package tv.huan.bilibili.widget.common;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import lib.kalu.leanback.plus.TextViewPlus;
import tv.huan.bilibili.utils.JumpUtil;

public final class CommonSearchView extends TextViewPlus {
    public CommonSearchView(@NonNull Context context) {
        super(context);
        init();
    }

    public CommonSearchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommonSearchView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        ViewCompat.animate(this).scaleX(focused ? 1.1f : 1.0f).scaleY(focused ? 1.1f : 1.0f).setDuration(200).start();
    }

    private void init() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpUtil.nextSearch(getContext());
            }
        });
    }
}
