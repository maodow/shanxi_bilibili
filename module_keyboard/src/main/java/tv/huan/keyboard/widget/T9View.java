package tv.huan.keyboard.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import tv.huan.keyboard.R;

@SuppressLint("AppCompatCustomView")
public final class T9View extends TextView {

    public T9View(Context context) {
        super(context);
        init();
    }

    public T9View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public T9View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public T9View(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, width);
    }

    @Override
    public void setBackgroundResource(int resid) {
        if (0 == resid)
            return;
        try {
            super.setBackgroundResource(resid);
        } catch (Exception e) {
        }
    }

    @Override
    public void setTextColor(int color) {
        if (0 == color)
            return;
        try {
            super.setTextColor(color);
        } catch (Exception e) {
        }
    }

    private void init() {
        setFocusable(true);
        setGravity(Gravity.CENTER);
        setBackgroundResource(R.drawable.keyboard_selector_bg_t9_item);
        setTextColor(getResources().getColorStateList(R.color.keyboard_selector_text_t9_item));
    }
}
