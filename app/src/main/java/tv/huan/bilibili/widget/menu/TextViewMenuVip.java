package tv.huan.bilibili.widget.menu;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import lib.kalu.leanback.plus.TextViewPlus;

public class TextViewMenuVip extends TextViewPlus {
    public TextViewMenuVip(@NonNull Context context) {
        super(context);
    }

    public TextViewMenuVip(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewMenuVip(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        ViewCompat.animate(this).scaleX(focused ? 1.05f : 1.0f).scaleY(focused ? 1.05f : 1.0f).setDuration(200).start();
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        // up
//        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
//            return true;
//        }
//        // right
//        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
//            return true;
//        }
//        return super.dispatchKeyEvent(event);
//    }
}
