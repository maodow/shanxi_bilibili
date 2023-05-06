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

public class PlayerComponentInitTemplate21 extends RelativeLayout implements ComponentApi {

    public PlayerComponentInitTemplate21(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.common_player_component_init_template21, this, true);
    }

    @Override
    public void callPlayerEvent(@NonNull int playState) {
        switch (playState) {
            case PlayerType.StateType.STATE_INIT:
            case PlayerType.StateType.STATE_KERNEL_STOP:
            case PlayerType.StateType.STATE_CLOSE:
            case PlayerType.StateType.STATE_PAUSE:
//                LogUtil.log("PlayerComponentInitTemplate21[show] => playState = " + playState);
                show();
                break;
            case PlayerType.StateType.STATE_ERROR:
            case PlayerType.StateType.STATE_ERROR_IGNORE:
            case PlayerType.StateType.STATE_START:
            case PlayerType.StateType.STATE_RESUME:
            case PlayerType.StateType.STATE_KERNEL_RESUME:
//                LogUtil.log("PlayerComponentInitTemplate21[gone] => playState = " + playState);
                gone();
                break;
        }
    }

    @Override
    public void gone() {
        try {
            findViewById(R.id.common_player_component_init_template21_img).setVisibility(View.GONE);
            LogUtil.log("PlayerComponentInitTemplate21 => gone => succ");
        } catch (Exception e) {
            LogUtil.log("PlayerComponentInitTemplate21 => gone => " + e.getMessage());
        }
    }

    @Override
    public void show() {
        try {
            bringToFront();
            findViewById(R.id.common_player_component_init_template21_img).setVisibility(View.VISIBLE);
            LogUtil.log("PlayerComponentInitTemplate21 => show => succ");
        } catch (Exception e) {
            LogUtil.log("PlayerComponentInitTemplate21 => show => " + e.getMessage());
        }
    }

    @Override
    public void setComponentImageUrl(@NonNull String url) {
        try {
            gone();
            ImageView imageView = findViewById(R.id.common_player_component_init_template21_img);
            imageView.setImageDrawable(null);
            LogUtil.log("PlayerComponentInitTemplate21 => setComponentImageUrl => imageView = " + imageView + ", imgUrl = " + url);
            GlideUtils.loadHz(imageView.getContext(), url, imageView);
            show();
        } catch (Exception e) {
            LogUtil.log("PlayerComponentInitTemplate21 => setComponentImageUrl => " + e.getMessage());
        }
    }
}