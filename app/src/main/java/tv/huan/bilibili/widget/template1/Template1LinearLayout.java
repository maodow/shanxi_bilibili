package tv.huan.bilibili.widget.template1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewParent;

import androidx.leanback.widget.VerticalGridView;

import lib.kalu.leanback.round.RoundLinearLayout;

public class Template1LinearLayout extends RoundLinearLayout {
    public Template1LinearLayout(Context context) {
        super(context);
    }

    public Template1LinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Template1LinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Template1LinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
            int w = (int) ((int) ((width - left - right) / 10) * 2.5);
            int h = (int) (width / 4) / 5;
            int specW = MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY);
            int specH = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
            super.onMeasure(specW, specH);
            setMeasuredDimension(w, h);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
