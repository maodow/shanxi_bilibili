package tv.huan.keyboard.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@SuppressLint("AppCompatCustomView")
public final class RowT9LinearLayout extends LinearLayout {

    public RowT9LinearLayout(Context context) {
        super(context);
    }

    public RowT9LinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RowT9LinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RowT9LinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width / 3;
        setMeasuredDimension(width, height);
    }
}