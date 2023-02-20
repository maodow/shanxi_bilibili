package tv.huan.bilibili.widget.template17;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewParent;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.leanback.widget.VerticalGridView;

import tv.huan.bilibili.R;

public class Template171LinearLayout extends LinearLayout {
    public Template171LinearLayout(Context context) {
        super(context);
    }

    public Template171LinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Template171LinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Template171LinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
            int offset = getResources().getDimensionPixelOffset(R.dimen.dp_26);
            int w = (int) (((width - left - right) / 10) * 2.5) - offset;
            int h = width / 4;
            int specW = MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY);
            int specH = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
            super.onMeasure(specW, specH);
            setMeasuredDimension(w, h);
        } catch (Exception e) {
        }
    }
}
