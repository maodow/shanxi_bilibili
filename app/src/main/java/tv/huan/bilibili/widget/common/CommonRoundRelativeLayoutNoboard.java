package tv.huan.bilibili.widget.common;

import android.content.Context;
import android.util.AttributeSet;

import tv.huan.bilibili.R;

public class CommonRoundRelativeLayoutNoboard extends CommonRoundRelativeLayout {

    public CommonRoundRelativeLayoutNoboard(Context context) {
        super(context);
    }

    public CommonRoundRelativeLayoutNoboard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonRoundRelativeLayoutNoboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CommonRoundRelativeLayoutNoboard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
