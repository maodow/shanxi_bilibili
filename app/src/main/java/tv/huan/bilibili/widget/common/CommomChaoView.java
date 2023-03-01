package tv.huan.bilibili.widget.common;

import android.content.Context;
import android.graphics.Rect;
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

public class CommomChaoView extends TextViewPlus {
    public CommomChaoView(@NonNull Context context) {
        super(context);
        init();
    }

    public CommomChaoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommomChaoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        ViewCompat.animate(this).scaleX(focused ? 1.1f : 1.0f).scaleY(focused ? 1.1f : 1.0f).setDuration(200).start();
    }

    private final void init() {
        setText(getResources().getString(R.string.menu_chao_no));
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "click", Toast.LENGTH_SHORT).show();
                HeilongjiangApi.jumpVip(getContext());
            }
        });
    }

    private void checkVip() {
        HeilongjiangApi.checkVip(getContext(), new OnCheckVipChangeListener() {
            @Override
            public void onPass() {
                setText(getResources().getString(R.string.menu_chao_yes));
            }

            @Override
            public void onFail() {
                setText(getResources().getString(R.string.menu_chao_no));
            }
        });
    }
}
