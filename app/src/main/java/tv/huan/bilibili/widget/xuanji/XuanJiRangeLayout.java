package tv.huan.bilibili.widget.xuanji;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import tv.huan.bilibili.R;
import tv.huan.bilibili.widget.common.CommonRoundRelativeLayout;

public class XuanJiRangeLayout extends CommonRoundRelativeLayout {
    public XuanJiRangeLayout(Context context) {
        super(context);
    }

    public XuanJiRangeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XuanJiRangeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public XuanJiRangeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
//            DisplayMetrics dm = getResources().getDisplayMetrics();
//            int srcWidth = dm.widthPixels;
            ViewGroup viewGroup = (ViewGroup) getParent().getParent().getParent();
//            int offset1 = getResources().getDimensionPixelOffset(R.dimen.dp_48);
            int offset2 = getResources().getDimensionPixelOffset(R.dimen.dp_8);
            int width = (viewGroup.getWidth() - offset2 * 4) / 5;
            int specW = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
            super.onMeasure(specW, heightMeasureSpec);
//            int height = MeasureSpec.getSize(heightMeasureSpec);
//            setMeasuredDimension(width, height);
        } catch (Exception e) {
        }
    }

    @Override
    protected void init() {
        //        int padding = getResources().getDimensionPixelOffset(R.dimen.dp_4);
//        setPadding(padding, padding, padding, padding);
        int radius = getResources().getDimensionPixelOffset(R.dimen.dp_6);
        setRadius(radius, radius, radius, radius);
        setScale(1.05F);
        setBackgroundResource(R.drawable.bg_selector_common_noboard);
    }
}
