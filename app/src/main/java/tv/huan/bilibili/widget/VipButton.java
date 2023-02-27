package tv.huan.bilibili.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lib.kalu.leanback.plus.TextViewPlus;
import tv.huan.heilongjiang.HeilongjiangApi;
import tv.huan.heilongjiang.OnCheckVipChangeListener;

public class VipButton extends TextViewPlus {
    public VipButton(@NonNull Context context) {
        super(context);
        init();
    }
    public VipButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VipButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        setEnabled(false);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                HeilongjiangApi.jumpVip(getContext());
            }
        });
    }

    private void checkVip() {
        HeilongjiangApi.checkVip(getContext(), new OnCheckVipChangeListener() {
            @Override
            public void onPass() {
                setText("已开通vip");
            }

            @Override
            public void onFail() {
                setText("未开通vip");
            }
        });
    }
}
