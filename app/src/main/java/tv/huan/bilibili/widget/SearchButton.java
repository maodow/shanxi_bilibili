package tv.huan.bilibili.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lib.kalu.leanback.plus.TextViewPlus;
import tv.huan.bilibili.utils.JumpUtil;

public class SearchButton extends TextViewPlus {
    public SearchButton(@NonNull Context context) {
        super(context);
        init();
    }

    public SearchButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpUtil.nextSearch(getContext());
            }
        });
    }
}
