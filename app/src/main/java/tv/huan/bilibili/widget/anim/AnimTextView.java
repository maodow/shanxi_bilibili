package tv.huan.bilibili.widget.anim;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import lib.kalu.leanback.plus.TextViewPlus;

public class AnimTextView extends TextViewPlus {
    public AnimTextView(@NonNull Context context) {
        super(context);
    }

    public AnimTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        ViewCompat.animate(this).scaleX(focused ? 1.1f : 1.0f).scaleY(focused ? 1.1f : 1.0f).setDuration(200).start();
    }
}
