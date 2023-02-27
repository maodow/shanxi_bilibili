package tv.huan.bilibili.widget.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class CommonEpisodeRelativeLayout extends RelativeLayout {
    public CommonEpisodeRelativeLayout(Context context) {
        super(context);
    }

    public CommonEpisodeRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonEpisodeRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public CommonEpisodeRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width * 0.6);
        setMeasuredDimension(width, height);
    }
}
