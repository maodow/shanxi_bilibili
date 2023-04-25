package tv.huan.bilibili.widget.common;

import android.content.Context;
import android.util.AttributeSet;

import tv.huan.bilibili.R;

public class CommonRoundRelativeLayoutBig extends CommonRoundRelativeLayout {
    public CommonRoundRelativeLayoutBig(Context context) {
        super(context);
        init();
    }

    public CommonRoundRelativeLayoutBig(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommonRoundRelativeLayoutBig(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CommonRoundRelativeLayoutBig(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void init() {
//        super.init();
        int radius = getResources().getDimensionPixelOffset(R.dimen.dp_6);
        setRadius(radius, radius, radius, radius);
        setScale(1.05F);
        setBackgroundResource(R.drawable.bg_selector_common_highlight);
    }
}
