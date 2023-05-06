package tv.huan.bilibili.widget.player.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import lib.kalu.mediaplayer.config.player.PlayerType;
import lib.kalu.mediaplayer.core.component.ComponentApi;
import tv.huan.bilibili.R;

public class PlayerComponentNet extends RelativeLayout implements ComponentApi {

    public PlayerComponentNet(Context context) {
        super(context);
        LayoutInflater.from(getContext()).inflate(R.layout.common_player_component_net, this, true);
    }

    @Override
    public void callPlayerEvent(int playState) {
        switch (playState) {
            case PlayerType.StateType.STATE_BUFFERING_START:
                show();
                break;
            case PlayerType.StateType.STATE_INIT:
            case PlayerType.StateType.STATE_LOADING_STOP:
            case PlayerType.StateType.STATE_BUFFERING_STOP:
            case PlayerType.StateType.STATE_FAST_FORWARD_START:
            case PlayerType.StateType.STATE_FAST_REWIND_START:
                gone();
                break;
        }
    }

    @Override
    public void gone() {
        try {
            findViewById(R.id.common_player_component_net).setVisibility(View.GONE);
        } catch (Exception e) {
        }
    }

    @Override
    public void show() {
        try {
            bringToFront();
            findViewById(R.id.common_player_component_net).setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }
    }
}
