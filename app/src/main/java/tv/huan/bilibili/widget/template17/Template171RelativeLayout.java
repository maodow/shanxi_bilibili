package tv.huan.bilibili.widget.template17;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewParent;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.leanback.widget.VerticalGridView;

import tv.huan.bilibili.R;

public class Template171RelativeLayout extends RelativeLayout {
    public Template171RelativeLayout(Context context) {
        super(context);
    }

    public Template171RelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Template171RelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Template171RelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
            int w = (int) ((int) ((width - left - right) / 10) * 2.5) - offset;
            int h = (int) width / 4;
            int specW = MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY);
            int specH = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
            super.onMeasure(specW, specH);
            setMeasuredDimension(w, h);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (null != mOnWindowVisibilityChangedListener) {
            mOnWindowVisibilityChangedListener.onChanged(visibility);
        }
    }

    private OnWindowVisibilityChangedListener mOnWindowVisibilityChangedListener;

    public void setOnWindowVisibilityChangedListener(@NonNull OnWindowVisibilityChangedListener listener) {
        this.mOnWindowVisibilityChangedListener = listener;
    }

    public interface OnWindowVisibilityChangedListener {
        void onChanged(int visibility);
    }
}
