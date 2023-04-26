package tv.huan.bilibili.widget.template1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewParent;

import androidx.leanback.widget.VerticalGridView;

import lib.kalu.leanback.round.RoundLinearLayout;
import tv.huan.bilibili.R;
import tv.huan.bilibili.utils.LogUtil;

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
            float w = (width - left - right) * 0.25F;
            float h = getResources().getDimensionPixelOffset(R.dimen.dp_43);
            int specW = MeasureSpec.makeMeasureSpec((int) w, MeasureSpec.EXACTLY);
            int specH = MeasureSpec.makeMeasureSpec((int) h, MeasureSpec.EXACTLY);
            super.onMeasure(specW, specH);
        } catch (Exception e) {
            LogUtil.log("Template1RelativeLayoutRight => onMeasure => " + e.getMessage());
        }
    }
}
