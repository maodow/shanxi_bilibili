package tv.huan.bilibili.widget.player;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.RequiresApi;

import lib.kalu.mediaplayer.core.component.ComponentError;
import lib.kalu.mediaplayer.widget.player.PlayerLayout;
import tv.huan.bilibili.R;
import tv.huan.bilibili.widget.player.component.PlayerComponentInitTemplate22;
import tv.huan.bilibili.widget.player.component.PlayerComponentNet;

public final class PlayerViewTemplate22 extends PlayerLayout {

    public PlayerViewTemplate22(Context context) {
        super(context);
        init();
    }

    public PlayerViewTemplate22(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayerViewTemplate22(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayerViewTemplate22(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private boolean isClickJump = false;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER) {
            isClickJump = true;
        } else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            isClickJump = true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected boolean enableWindowVisibilityChangedTodo(int visibility) {
        if (isClickJump && visibility != View.VISIBLE) {
            return true;
        } else if (isClickJump && visibility == View.VISIBLE) {
            isClickJump = false;
            return true;
        }
        return false;
    }

    @Override
    protected boolean enableDetachedFromWindowTodo() {
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
        PlayerComponentInitTemplate22 initTemplate = new PlayerComponentInitTemplate22(getContext());
        initTemplate.setComponentBackgroundResource(R.drawable.bg_shape_placeholder);
        addComponent(initTemplate);
    }
}