package tv.huan.bilibili.widget.player;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.RequiresApi;

import lib.kalu.mediaplayer.core.component.ComponentComplete;
import lib.kalu.mediaplayer.core.component.ComponentError;
import lib.kalu.mediaplayer.core.component.ComponentLoading;
import lib.kalu.mediaplayer.core.component.ComponentNet;
import lib.kalu.mediaplayer.core.component.ComponentPause;
import lib.kalu.mediaplayer.core.component.ComponentSeek;
import lib.kalu.mediaplayer.widget.player.PlayerLayout;
import tv.huan.bilibili.utils.ADUtil;

public class PlayerView extends PlayerLayout {
    public PlayerView(Context context) {
        super(context);
        init();
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ADUtil.adRelease();
    }

    protected void init() {
        // loading
        ComponentLoading loading = new ComponentLoading(getContext());
        loading.setComponentBackgroundColorInt(Color.parseColor("#000000"));
        addComponent(loading);
        // pause
        ComponentPause pause = new ComponentPause(getContext());
        pause.setComponentBackgroundColorInt(Color.parseColor("#66000000"));
        addComponent(pause);
        // error
        ComponentError error = new ComponentError(getContext());
        addComponent(error);
        // net
        ComponentNet net = new ComponentNet(getContext());
        addComponent(net);
        // seekbar
        ComponentSeek seek = new ComponentSeek(getContext());
        addComponent(seek);
        // init
        PlayerComponentInit init = new PlayerComponentInit(getContext());
        addComponent(init);
        // complete
        ComponentComplete complete = new ComponentComplete(getContext());
        addComponent(complete);
        // adPause
        PlayerComponentADPause pauseAD = new PlayerComponentADPause(getContext());
        addComponent(pauseAD);
        // adStart
        PlayerComponentADStart startAD = new PlayerComponentADStart(getContext());
        addComponent(startAD);
    }
}