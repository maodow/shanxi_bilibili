
package tv.huan.bilibili.widget.template2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewParent;

import androidx.leanback.widget.VerticalGridView;

import lib.kalu.leanback.round.RoundRelativeLayout;
import tv.huan.bilibili.R;

public class RoundRelativeLayoutTemplate22 extends RoundRelativeLayout {
    public RoundRelativeLayoutTemplate22(Context context) {
        super(context);
        init();
    }

    public RoundRelativeLayoutTemplate22(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundRelativeLayoutTemplate22(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RoundRelativeLayoutTemplate22(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        int radius = getResources().getDimensionPixelOffset(R.dimen.dp_6);
        setRadius(radius, radius, radius, radius);
        setScale(1.1F);
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
            int offset1 = getResources().getDimensionPixelOffset(R.dimen.dp_60);
            int w = (int) ((int) ((width - left - right) / 6)) - offset1 / 4;
            int offset2 = getResources().getDimensionPixelOffset(R.dimen.dp_10);
            int h = (int) (int) (width * 0.46 / 2) - offset2;
            int specW = MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY);
            int specH = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
            super.onMeasure(specW, specH);
            setMeasuredDimension(w, h);
        } catch (Exception e) {
        }
    }
}
