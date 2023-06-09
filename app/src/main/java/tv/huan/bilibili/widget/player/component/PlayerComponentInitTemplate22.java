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
            case PlayerType.StateType.STATE_RELEASE:
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

    @Override
    public void setComponentImageUrl(@NonNull String url) {
        try {
            gone();
            ImageView imageView = findViewById(R.id.common_player_component_init_template22_img);
            imageView.setImageDrawable(null);
            GlideUtils.loadHz(imageView.getContext(), url, imageView);
            show();
        } catch (Exception e) {
            LogUtil.log("PlayerComponentInitTemplate21 => setComponentImageUrl => " + e.getMessage());
        }
    }

    @Override
    public void setComponentBackgroundResource(int resid) {
        try {
            gone();
            ImageView imageView = findViewById(R.id.common_player_component_init_template22_img);
            imageView.setImageDrawable(null);
            imageView.setBackgroundResource(resid);
            show();
        } catch (Exception e) {
            LogUtil.log("PlayerComponentInitTemplate22 => setComponentBackgroundResource => " + e.getMessage());
        }
    }
}