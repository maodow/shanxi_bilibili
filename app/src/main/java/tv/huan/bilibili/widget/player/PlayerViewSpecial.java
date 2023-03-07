package tv.huan.bilibili.widget.player;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import lib.kalu.mediaplayer.config.player.PlayerType;
import lib.kalu.mediaplayer.core.controller.ControllerEmpty;
import lib.kalu.mediaplayer.core.controller.component.ComponentLoading;
import lib.kalu.mediaplayer.core.player.VideoLayout;

public class PlayerViewSpecial extends PlayerView {

    public PlayerViewSpecial(Context context) {
        super(context);
    }

    public PlayerViewSpecial(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerViewSpecial(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayerViewSpecial(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {

        // 填充模式
//        if (getId() == R.id.qvoice_video || getId() == R.id.qsquare_video || getId() == R.id.qreport_video) {
        setScaleType(PlayerType.ScaleType.SCREEN_SCALE_16_9);
//        } else {
//            setScaleType(PlayerType.ScaleType.SCREEN_SCALE_MATCH_PARENT);
//        }

        // loading
        ComponentLoading loading = new ComponentLoading(getContext());

        // component
        ControllerEmpty controller = new ControllerEmpty(getContext());
        controller.addComponent(loading);

        // add
        setControllerLayout(controller);
    }
}
