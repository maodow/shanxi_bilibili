package tv.huan.bilibili.widget.player;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.RequiresApi;

import lib.kalu.mediaplayer.core.component.ComponentError;
import lib.kalu.mediaplayer.widget.player.PlayerLayout;
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

    private boolean jumpDetailBack = false;
    private boolean jumpDetail = false;

    public void setJumpDetail(boolean status) {
        this.jumpDetail = status;
    }

    @Override
    protected boolean enableWindowVisibilityChangedTodo(int visibility) {
        if (jumpDetail && visibility != View.VISIBLE) {
            return true;
        } else if (jumpDetail && visibility == View.VISIBLE) {
            jumpDetail = false;
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
        addComponent(initTemplate);
    }
}