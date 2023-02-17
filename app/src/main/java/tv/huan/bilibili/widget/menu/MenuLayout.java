package tv.huan.bilibili.widget.menu;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;

import tv.huan.bilibili.R;
import tv.huan.bilibili.utils.JumpUtil;

public final class MenuLayout extends LinearLayout {
    public MenuLayout(Context context) {
        super(context);
        init();
    }

    public MenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MenuLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setFocusable(false);
        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(getContext()).inflate(R.layout.common_layout_menu, this, true);

        // 搜索
        findViewById(R.id.common_menu_search).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtil.nextSearch(v.getContext());
            }
        });
    }
}
