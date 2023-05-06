package tv.huan.bilibili.widget.player;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.RequiresApi;

import lib.kalu.mediaplayer.core.component.ComponentError;
import lib.kalu.mediaplayer.core.component.ComponentLoading;
import lib.kalu.mediaplayer.widget.player.PlayerLayout;
import tv.huan.bilibili.widget.player.component.PlayerComponentInitTemplate22;
import tv.huan.bilibili.widget.player.component.PlayerComponentNet;
import tv.huan.bilibili.widget.player.component.PlayerComponentPauseTemplate;

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

    private void init() {
        // loading
        ComponentLoading loading = new ComponentLoading(getContext());
        loading.setComponentBackgroundColorInt(Color.parseColor("#000000"));
        addComponent(loading);
        // error
        ComponentError error = new ComponentError(getContext());
        loading.setComponentBackgroundColorInt(Color.parseColor("#000000"));
        addComponent(error);
        // pause
        PlayerComponentPauseTemplate pause = new PlayerComponentPauseTemplate(getContext());
        loading.setComponentBackgroundColorInt(Color.parseColor("#66000000"));
        addComponent(pause);
        // net
        PlayerComponentNet net = new PlayerComponentNet(getContext());
        addComponent(net);
        // init
        PlayerComponentInitTemplate22 initTemplate = new PlayerComponentInitTemplate22(getContext());
        addComponent(initTemplate);
    }
}