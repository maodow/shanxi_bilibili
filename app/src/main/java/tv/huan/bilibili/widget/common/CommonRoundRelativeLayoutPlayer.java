package tv.huan.bilibili.widget.common;

import android.content.Context;
import android.util.AttributeSet;

import tv.huan.bilibili.R;

public class CommonRoundRelativeLayoutPlayer extends CommonRoundRelativeLayout {
    public CommonRoundRelativeLayoutPlayer(Context context) {
        super(context);
        init();
    }

    public CommonRoundRelativeLayoutPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommonRoundRelativeLayoutPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CommonRoundRelativeLayoutPlayer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    protected void init() {
//        int padding = getResources().getDimensionPixelOffset(R.dimen.dp_4);
//        setPadding(padding, padding, padding, padding);
        int radius = getResources().getDimensionPixelOffset(R.dimen.dp_6);
        setRadius(radius, radius, radius, radius);
        setScale(1F);
        setBackgroundResource(R.drawable.bg_selector_common_highlight);
    }
}
