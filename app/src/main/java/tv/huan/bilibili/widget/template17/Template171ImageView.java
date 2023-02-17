package tv.huan.bilibili.widget.template17;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import lib.kalu.leanback.round.RoundImageView;

public class Template171ImageView extends RoundImageView {

    public Template171ImageView(Context context) {
        super(context);
    }

    public Template171ImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Template171ImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width * 0.56);
        int specH = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, specH);
        setMeasuredDimension(width, height);
    }
}
