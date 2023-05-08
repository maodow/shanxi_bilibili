package tv.huan.bilibili.widget.player.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import lib.kalu.mediaplayer.config.player.PlayerType;
import lib.kalu.mediaplayer.core.component.ComponentApi;
import tv.huan.bilibili.R;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.LogUtil;

public class PlayerComponentInitTemplate22 extends RelativeLayout implements ComponentApi {

    public PlayerComponentInitTemplate22(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.common_player_component_init_template22, this, true);
    }

    @Override
    public void callPlayerEvent(@NonNull int playState) {
        switch (playState) {
            case PlayerType.StateType.STATE_RESTAER:
            case PlayerType.StateType.STATE_INIT:
            case PlayerType.StateType.STATE_KERNEL_STOP:
            case PlayerType.StateType.STATE_CLOSE:
            case PlayerType.StateType.STATE_PAUSE:
            case PlayerType.StateType.STATE_PAUSE_IGNORE:
                LogUtil.log("PlayerComponentInitTemplate22[show] => playState = " + playState);
                show();
                break;
            case PlayerType.StateType.STATE_ERROR:
            case PlayerType.StateType.STATE_ERROR_IGNORE:
            case PlayerType.StateType.STATE_START:
            case PlayerType.StateType.STATE_RESUME:
            case PlayerType.StateType.STATE_RESUME_IGNORE:
            case PlayerType.StateType.STATE_KERNEL_RESUME:
                LogUtil.log("PlayerComponentInitTemplate22[gone] => playState = " + playState);
                gone();
                break;
        }
    }

    @Override
    public void gone() {
        try {
            findViewById(R.id.common_player_component_init_template22_img).setVisibility(View.GONE);
        } catch (Exception e) {
            LogUtil.log("PlayerComponentInitTemplate22 => gone => " + e.getMessage());
        }
    }

    @Override
    public void show() {
        try {
            bringToFront();
            findViewById(R.id.common_player_component_init_template22_img).setVisibility(View.VISIBLE);
        } catch (Exception e) {
            LogUtil.log("PlayerComponentInitTemplate22 => show => " + e.getMessage());
        }
    }

    public final void showImage(@NonNull String imgUrl) {
        try {
            ImageView imageView = findViewById(R.id.common_player_component_init_template22_img);
            GlideUtils.loadHz(imageView.getContext(), imgUrl, imageView);
        } catch (Exception e) {
            LogUtil.log("PlayerComponentInitTemplate22 => showImage => " + e.getMessage());
        }
    }
}