package tv.huan.bilibili.widget.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import tv.huan.bilibili.R;

public class HuanViewPager extends ViewPager {

    public HuanViewPager(@NonNull Context context) {
        super(context);
        setOffscreenPageLimit(0);
    }

    public HuanViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOffscreenPageLimit(0);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // left
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
            View focus = findFocus();
            View nextFocus = FocusFinder.getInstance().findNextFocus(this, focus, View.FOCUS_LEFT);
            if (null == nextFocus) {
                int count = event.getRepeatCount();
                if (count <= 0) {

                    long time;
                    long downTime = event.getDownTime();
                    int id = getId();

                    try {
                        time = (long) getTag(id);
                    } catch (Exception e) {
                        time = 0;
                    }

                    if (time <= 0 || (downTime - Math.abs(time) > 4000)) {
                        shakeAnim();
                        setTag(id, downTime);
                    } else {
                        onPageLft();
                        setTag(id, 0);
                        super.executeKeyEvent(event);
                    }
                }
                return true;
            }
        }
        // right
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
            View focus = findFocus();
            View nextFocus = FocusFinder.getInstance().findNextFocus(this, focus, View.FOCUS_RIGHT);
            if (null == nextFocus) {
                int count = event.getRepeatCount();
                if (count <= 0) {

                    long time;
                    long downTime = event.getDownTime();
                    int id = getId();

                    try {
                        time = (long) getTag(id);
                    } catch (Exception e) {
                        time = 0;
                    }

                    if (time >= 0 || (downTime - Math.abs(time) > 4000)) {
                        shakeAnim();
                        setTag(id, -downTime);
                    } else {
                        onPageRight();
                        setTag(id, 0);
                        super.executeKeyEvent(event);
                    }
                }
                return true;
            }
        } else if (event.getAction() == KeyEvent.ACTION_DOWN) {
            setTag(getId(), 0);
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void setCurrentItem(int item) {
        if (null != mListener) {
            int i = getCurrentItem();
            PagerAdapter adapter = getAdapter();
            mListener.onUnSelected(i, adapter);
            Log.e("HuanViewPager", "setCurrentItem => i = " + i);
        }
        super.setCurrentItem(item);
    }

    private void onPageLft() {
        if (null != mListener) {
            int i = getCurrentItem();
            PagerAdapter adapter = getAdapter();
            mListener.onUnSelected(i, adapter);
            Log.e("HuanViewPager", "onPageLft => i = " + i);
        }
    }

    private void onPageRight() {
        if (null != mListener) {
            int i = getCurrentItem();
            PagerAdapter adapter = getAdapter();
            mListener.onUnSelected(i, adapter);
            Log.e("HuanViewPager", "onPageRight => i = " + i);
        }
    }

    private final void shakeAnim() {
//        View focus = findFocus();
//        if (null == focus)
//            return;
//        Context context = getContext().getApplicationContext();
//        Animation shake = AnimationUtils.loadAnimation(context, R.anim.anim_shake);
//        focus.startAnimation(shake);
    }

    private OnUnSelectedChangeListener mListener;

    public void setOnUnSelectedChangeListener(OnUnSelectedChangeListener l) {
        mListener = l;
    }

    public interface OnUnSelectedChangeListener {
        void onUnSelected(int position, PagerAdapter adapter);
    }
}
