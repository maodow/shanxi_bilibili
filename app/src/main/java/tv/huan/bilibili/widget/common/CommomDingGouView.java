package tv.huan.bilibili.widget.common;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import lib.kalu.leanback.plus.TextViewPlus;
import tv.huan.bilibili.R;
import tv.huan.heilongjiang.HeilongjiangApi;
import tv.huan.heilongjiang.OnCheckVipChangeListener;

public final class CommomDingGouView extends TextViewPlus {
    public CommomDingGouView(@NonNull Context context) {
        super(context);
        init();
    }

    public CommomDingGouView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommomDingGouView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.VISIBLE) {
            checkVip();
        }
    }

    private void init() {
        setVisibility(View.GONE);
    }

    private void checkVip() {
        HeilongjiangApi.checkVip(getContext(), new OnCheckVipChangeListener() {
            @Override
            public void onPass() {
                updateVisibility(true);
            }

            @Override
            public void onFail() {
                updateVisibility(false);
            }
        });
    }

    private void updateVisibility(boolean succ) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                setVisibility(succ ? View.VISIBLE : View.GONE);
            }
        });
    }
}
