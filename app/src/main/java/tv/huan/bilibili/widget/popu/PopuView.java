package tv.huan.bilibili.widget.popu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import lib.kalu.leanback.marquee.MarqueeTextView;
import tv.huan.bilibili.R;

@SuppressLint("AppCompatCustomView")
public final class PopuView extends MarqueeTextView {
    public PopuView(Context context) {
        super(context);
    }

    public PopuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PopuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PopuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // popu
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        int offset = getResources().getDimensionPixelOffset(R.dimen.dp_4);
        int left = getLeft();
        int right = getRight();
        int top = getTop();
        int bottom = getBottom();
        RectF rectF = new RectF(left, top, right, bottom * 0.9F);
        // 1
        canvas.drawRoundRect(rectF, offset / 2, offset / 2, paint);
        // 2
        int x3 = getWidth() / 2;
        int x1 = x3 - offset;
        int x2 = x3 + offset;
        int y1 = (int) (bottom*0.9F);
        int y2 = bottom;
        Path path = new Path();
        path.moveTo(x1, y1);
        path.lineTo(x2, y1);
        path.lineTo(x3, y2);
        path.lineTo(x1, y1);
        path.close();
        canvas.drawPath(path, paint);
    }

    @Override
    public void setBackground(Drawable background) {
    }
}
