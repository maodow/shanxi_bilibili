package tv.huan.bilibili.widget.common;

import android.content.Context;
import android.util.AttributeSet;

import tv.huan.bilibili.R;

public class CommonRoundRelativeLayoutCircle extends lib.kalu.leanback.round.RoundRelativeLayout {
    public CommonRoundRelativeLayoutCircle(Context context) {
        super(context);
        init();
    }

    public CommonRoundRelativeLayoutCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommonRoundRelativeLayoutCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CommonRoundRelativeLayoutCircle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    protected void init() {
//        int padding = getResources().getDimensionPixelOffset(R.dimen.dp_4);
//        setPadding(padding, padding, padding, padding);
        int radius = getResources().getDimensionPixelOffset(R.dimen.dp_100);
        setRadius(radius, radius, radius, radius);
        setScale(1.1F);
        setBackgroundResource(R.drawable.bg_selector_common_circle);
    }
}
