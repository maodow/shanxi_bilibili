package tv.huan.bilibili.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import tv.huan.bilibili.R;

@SuppressLint("AppCompatCustomView")
public class ShandowTextView extends TextView {
    public ShandowTextView(Context context) {
        super(context);
    }

    public ShandowTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShandowTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public ShandowTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (null == text || text.length() <= 0) {
            setBackground(null);
        } else {
            setBackgroundResource(R.drawable.bg_shape_common_shandow);
        }
    }
}
