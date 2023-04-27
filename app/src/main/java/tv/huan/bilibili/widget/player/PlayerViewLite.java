package tv.huan.bilibili.widget.player;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.RequiresApi;

import lib.kalu.mediaplayer.config.player.PlayerType;
import lib.kalu.mediaplayer.core.component.ComponentLoading;
import lib.kalu.mediaplayer.core.component.ComponentNet;

public final class PlayerViewLite extends PlayerView {

    public PlayerViewLite(Context context) {
        super(context);
        init();
    }

    public PlayerViewLite(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayerViewLite(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayerViewLite(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

//    @Override
//    public void start(@NonNull String url) {
//        // 1
//        release();
//        // 2
//        StartBuilder.Builder builder = new StartBuilder.Builder();
//        builder.setLive(false);
//        builder.setLoop(true);
//        super.start(builder.build(), url);
//    }

    @Override
    protected void init() {

        // 填充模式
//        if (getId() == R.id.qvoice_video || getId() == R.id.qsquare_video || getId() == R.id.qreport_video) {
        setScaleType(PlayerType.ScaleType.SCREEN_SCALE_16_9);

        // loading
        ComponentLoading loading = new ComponentLoading(getContext());
        addComponent(loading);

        // net
        ComponentNet net = new ComponentNet(getContext());
        addComponent(net);
    }
}