package tv.huan.bilibili.widget.template17;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewParent;

import androidx.leanback.widget.VerticalGridView;

public class RoundRelativeLayoutTemplate173 extends RoundRelativeLayoutTemplate172 {
    public RoundRelativeLayoutTemplate173(Context context) {
        super(context);
    }

    public RoundRelativeLayoutTemplate173(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundRelativeLayoutTemplate173(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RoundRelativeLayoutTemplate173(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            ViewParent parent = getParent().getParent().getParent();
            VerticalGridView verticalGridView = (VerticalGridView) parent;
            int width = verticalGridView.getWidth();
            int left = verticalGridView.getPaddingLeft();
            int right = verticalGridView.getPaddingRight();
            int w = (int) (((width - left - right) / 10) * 2.5);
            int h = (width / 4) / 5;
            int specW = MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY);
            int specH = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
            super.onMeasure(specW, specH);
            setMeasuredDimension(w, h);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
