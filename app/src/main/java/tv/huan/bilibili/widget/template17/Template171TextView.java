package tv.huan.bilibili.widget.template17;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

@SuppressLint("AppCompatCustomView")
public class Template171TextView extends TextView {

    public Template171TextView(Context context) {
        super(context);
    }

    public Template171TextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Template171TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        ViewCompat.animate(this).scaleX(focused ? 1.1f : 1f).scaleY(focused ? 1.1f : 1f).setDuration(100).start();
    }
}
