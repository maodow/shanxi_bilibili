package tv.huan.bilibili.widget.player;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import lib.kalu.mediaplayer.core.component.ComponentError;
import lib.kalu.mediaplayer.core.component.ComponentLoading;
import lib.kalu.mediaplayer.core.component.ComponentNet;

public final class PlayerViewTemplate extends PlayerView {

    public PlayerViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

//    @Override
//    protected void checkOnWindowVisibilityChanged(int visibility) {
//    }


    @Override
    protected void init() {
        // loading
        ComponentLoading loading = new ComponentLoading(getContext());
        loading.setComponentBackgroundColorInt(Color.parseColor("#000000"));
        addComponent(loading);
        // error
        ComponentError error = new ComponentError(getContext());
        loading.setComponentBackgroundColorInt(Color.parseColor("#000000"));
        addComponent(error);
        // net
        PlayerComponentNet net = new PlayerComponentNet(getContext());
        addComponent(net);
        // init
        PlayerComponentInitTemplate initTemplate = new PlayerComponentInitTemplate(getContext());
        addComponent(initTemplate);
    }
}