package tv.huan.bilibili.widget.image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import tv.huan.bilibili.R;

@SuppressLint("AppCompatCustomView")
public class HzImageView extends ImageView {
    public HzImageView(Context context) {
        super(context);
        init();
    }

    public HzImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HzImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("NewApi")
    public HzImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setImageResource(R.drawable.bg_shape_placeholder_hz);
    }
}
