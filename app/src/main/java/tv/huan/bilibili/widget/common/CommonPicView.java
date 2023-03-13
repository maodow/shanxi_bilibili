package tv.huan.bilibili.widget.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@SuppressLint("AppCompatCustomView")
public class CommonPicView extends ImageView {

    public CommonPicView(Context context) {
        super(context);
        init();
    }

    public CommonPicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommonPicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CommonPicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height = MeasureSpec.getSize(heightMeasureSpec);
        try {
            Drawable drawable = getDrawable();
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
//            LogUtils.e("AutoImageView", "onMeasure => intrinsicWidth = " + intrinsicWidth + ", intrinsicHeight = " + intrinsicHeight);
            width = height * intrinsicWidth / intrinsicHeight;
        } catch (Exception e) {
            width = 0;
        }
//        LogUtils.e("AutoImageView", "onMeasure => width = " + width + ", height = " + height);
        setMeasuredDimension(width, height);
    }

//    @Override
//    public void setImageDrawable(@Nullable Drawable drawable) {
//        super.setImageDrawable(drawable);
//        invalidate();
//    }
//
//    @Override
//    public void setImageBitmap(Bitmap bm) {
//        super.setImageBitmap(bm);
//        invalidate();
//    }

    private void init() {
        setFocusable(false);
        setFocusableInTouchMode(false);
        setClickable(false);
        setScaleType(ScaleType.FIT_START);
    }
}
