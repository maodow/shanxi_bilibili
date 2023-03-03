package tv.huan.bilibili.widget.common;

import android.content.Context;
import android.util.AttributeSet;

import tv.huan.bilibili.R;

public class CommonRoundRelativeLayoutXuanQi extends CommonRoundRelativeLayout {

    public CommonRoundRelativeLayoutXuanQi(Context context) {
        super(context);
    }

    public CommonRoundRelativeLayoutXuanQi(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonRoundRelativeLayoutXuanQi(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CommonRoundRelativeLayoutXuanQi(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {
        int radius = getResources().getDimensionPixelOffset(R.dimen.dp_6);
        setRadius(radius, radius, radius, radius);
        setScale(1.05F);
        setBackgroundResource(R.drawable.bg_selector_common_noboard);
    }
}
