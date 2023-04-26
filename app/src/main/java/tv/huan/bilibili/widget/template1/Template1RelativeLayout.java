package tv.huan.bilibili.widget.template1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewParent;

import androidx.leanback.widget.VerticalGridView;

import lib.kalu.leanback.round.RoundRelativeLayout;
import tv.huan.bilibili.R;
import tv.huan.bilibili.utils.LogUtil;

public class Template1RelativeLayout extends RoundRelativeLayout {
    public Template1RelativeLayout(Context context) {
        super(context);
    }

    public Template1RelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Template1RelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Template1RelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
            float w = (width - left - right) * 0.75F;
            float h = getResources().getDimensionPixelOffset(R.dimen.dp_225);
            int specW = MeasureSpec.makeMeasureSpec((int) w, MeasureSpec.EXACTLY);
            int specH = MeasureSpec.makeMeasureSpec((int) h, MeasureSpec.EXACTLY);
            super.onMeasure(specW, specH);
        } catch (Exception e) {
            LogUtil.log("Template1RelativeLayoutLeft => onMeasure => " + e.getMessage());
        }
    }
}
