package tv.huan.bilibili.widget.player;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.RequiresApi;

import lib.kalu.mediaplayer.core.component.ComponentError;
import lib.kalu.mediaplayer.core.component.ComponentLoading;
import lib.kalu.mediaplayer.widget.player.PlayerLayout;
import tv.huan.bilibili.R;
import tv.huan.bilibili.widget.player.component.PlayerComponentInitTemplate21;
import tv.huan.bilibili.widget.player.component.PlayerComponentNet;
import tv.huan.bilibili.widget.player.component.PlayerComponentPauseTemplate;

public final class PlayerViewTemplate21 extends PlayerLayout {

    public PlayerViewTemplate21(Context context) {
        super(context);
        init();
    }

    public PlayerViewTemplate21(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayerViewTemplate21(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayerViewTemplate21(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected boolean enableWindowVisibilityChangedTodo(int visibility) {
        return false;
    }

    @Override
    protected boolean enableAttachedToWindowTodo() {
        return false;
    }

    private void init() {
        // error
        ComponentError error = new ComponentError(getContext());
        error.setComponentBackgroundColorInt(Color.parseColor("#000000"));
        addComponent(error);
        // net
        PlayerComponentNet net = new PlayerComponentNet(getContext());
        addComponent(net);
        // init
        PlayerComponentInitTemplate21 initTemplate = new PlayerComponentInitTemplate21(getContext());
        initTemplate.setComponentBackgroundResource(R.drawable.bg_shape_placeholder);
        addComponent(initTemplate);
    }
}