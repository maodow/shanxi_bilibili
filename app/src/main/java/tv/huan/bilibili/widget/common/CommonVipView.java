package tv.huan.bilibili.widget.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@SuppressLint("AppCompatCustomView")
public class CommonVipView extends ImageView {
    public CommonVipView(Context context) {
        super(context);
        init();
    }

    public CommonVipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommonVipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CommonVipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setFocusable(false);
        setVisibility(View.GONE);
        setScaleType(ScaleType.FIT_END);
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        setVisibility(View.VISIBLE);
        super.setImageDrawable(drawable);
    }
}
