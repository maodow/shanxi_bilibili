package tv.huan.bilibili.widget.common;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lib.kalu.leanback.plus.TextViewPlus;
import tv.huan.bilibili.R;

public class InputView extends TextViewPlus {
    public InputView(@NonNull Context context) {
        super(context);
    }

    public InputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int offset = getResources().getDimensionPixelOffset(R.dimen.dp_1);
        getPaint().setStrokeWidth(offset);
        int startY = getHeight() - offset;
        int endX = getWidth();
        canvas.drawLine(0, endX, startY, startY, getPaint());
    }
}
