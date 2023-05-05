package tv.huan.bilibili.widget.player;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import lib.kalu.mediaplayer.config.player.PlayerType;
import lib.kalu.mediaplayer.core.component.ComponentApi;
import tv.huan.bilibili.R;

public class PlayerComponentPauseTemplate extends RelativeLayout implements ComponentApi {

    public PlayerComponentPauseTemplate(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.common_player_component_pause_template, this, true);
    }

    @Override
    public void callPlayerEvent(int playState) {
        switch (playState) {
            case PlayerType.StateType.STATE_PAUSE:
            case PlayerType.StateType.STATE_PAUSE_IGNORE:
                show();
                break;
            case PlayerType.StateType.STATE_LOADING_START:
            case PlayerType.StateType.STATE_START:
            case PlayerType.StateType.STATE_RESUME:
            case PlayerType.StateType.STATE_RESUME_IGNORE:
            case PlayerType.StateType.STATE_RESTAER:
            case PlayerType.StateType.STATE_FAST_FORWARD_START:
            case PlayerType.StateType.STATE_FAST_REWIND_START:
                gone();
                break;
        }
    }

    @Override
    public void gone() {
        try {
            findViewById(R.id.common_player_component_pause_template).setVisibility(View.GONE);
            findViewById(R.id.common_player_component_pause_bg).setVisibility(View.GONE);
        } catch (Exception e) {
        }
    }

    @Override
    public void show() {
        try {
            bringToFront();
            findViewById(R.id.common_player_component_pause_template).setVisibility(View.VISIBLE);
            findViewById(R.id.common_player_component_pause_bg).setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }
    }

    @Override
    public void setComponentBackgroundColorInt(int value) {
        try {
            setBackgroundColorInt(this, R.id.common_player_component_pause_bg, value);
        } catch (Exception e) {
        }
    }

    @Override
    public void setComponentBackgroundResource(int resid) {
        try {
            setBackgroundDrawableRes(this, R.id.common_player_component_pause_bg, resid);
        } catch (Exception e) {
        }
    }
}