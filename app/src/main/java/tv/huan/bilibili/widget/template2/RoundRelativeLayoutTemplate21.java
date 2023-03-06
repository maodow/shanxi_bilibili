package tv.huan.bilibili.widget.template2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewParent;

import androidx.leanback.widget.VerticalGridView;

import lib.kalu.leanback.round.RoundRelativeLayout;
import tv.huan.bilibili.R;

public class RoundRelativeLayoutTemplate21 extends RoundRelativeLayout {
    public RoundRelativeLayoutTemplate21(Context context) {
        super(context);
        init();
    }

    public RoundRelativeLayoutTemplate21(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundRelativeLayoutTemplate21(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RoundRelativeLayoutTemplate21(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        int radius = getResources().getDimensionPixelOffset(R.dimen.dp_6);
        setRadius(radius, radius, radius, radius);
        setScale(1.06F);
        setBackgroundResource(R.drawable.bg_selector_common_highlight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            ViewParent parent = getParent().getParent().getParent();
            VerticalGridView verticalGridView = (VerticalGridView) parent;
            int width = verticalGridView.getWidth();
            int left = verticalGridView.getPaddingLeft();
            int right = verticalGridView.getPaddingRight();
            int offset = getResources().getDimensionPixelOffset(R.dimen.dp_20);
            int w = (((width - left - right) / 6) * 2) - offset;
            int h = (int) (width * 0.46);
            int specW = MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY);
            int specH = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
            super.onMeasure(specW, specH);
            setMeasuredDimension(w, h);
        } catch (Exception e) {
        }
    }
}
