package tv.huan.bilibili.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import tv.huan.bilibili.R;

@SuppressLint("AppCompatCustomView")
public class ShandowTextView extends TextView {
    public ShandowTextView(Context context) {
        super(context);
        init();
    }

    public ShandowTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShandowTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("NewApi")
    public ShandowTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setLines(1);
        setSingleLine(true);
        setEllipsize(TextUtils.TruncateAt.END);
        int offset = getResources().getDimensionPixelOffset(R.dimen.dp_8);
        setPadding(offset, 0, offset, offset);
    }

    @Override
    public boolean isFocused() {
        return true;
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
