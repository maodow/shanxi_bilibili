package tv.huan.bilibili.widget.player;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
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

public class PlayerComponentInitTemplate extends RelativeLayout implements ComponentApi {

    public PlayerComponentInitTemplate(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.common_player_component_init_template21, this, true);
    }

    @Override
    public void callPlayerEvent(@NonNull int playState) {
        LogUtil.log("PlayerComponentInitTemplate => playState = " + playState);
        switch (playState) {
            case PlayerType.StateType.STATE_INIT:
            case PlayerType.StateType.STATE_KERNEL_STOP:
            case PlayerType.StateType.STATE_CLOSE:
            case PlayerType.StateType.STATE_PAUSE:
            case PlayerType.StateType.STATE_PAUSE_IGNORE:
                LogUtil.log("PlayerComponentInitTemplate[show] => playState = " + playState);
                show();
                break;
            case PlayerType.StateType.STATE_ERROR:
            case PlayerType.StateType.STATE_ERROR_IGNORE:
            case PlayerType.StateType.STATE_START:
            case PlayerType.StateType.STATE_RESUME:
            case PlayerType.StateType.STATE_RESUME_IGNORE:
            case PlayerType.StateType.STATE_KERNEL_RESUME:
                LogUtil.log("PlayerComponentInitTemplate[gone] => playState = " + playState);
                gone();
                break;
        }
    }

    @Override
    public void gone() {
        try {
            findViewById(R.id.common_player_component_init_template21_img).setVisibility(View.GONE);
        } catch (Exception e) {
        }
    }

    @Override
    public void show() {
        try {
            bringToFront();
            findViewById(R.id.common_player_component_init_template21_img).setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }
    }

    public final void showImage(@NonNull String imgUrl) {
        try {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    ImageView imageView = findViewById(R.id.common_player_component_init_template21_img);
                    GlideUtils.loadHz(imageView.getContext(), imgUrl, imageView);
                }
            });
        } catch (Exception e) {
            LogUtil.log("PlayerComponentInitTemplate => showImage => " + e.getMessage());
        }
    }
}