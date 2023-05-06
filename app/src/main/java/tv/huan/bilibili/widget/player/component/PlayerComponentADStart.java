package tv.huan.bilibili.widget.player.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lib.kalu.mediaplayer.config.player.PlayerType;
import lib.kalu.mediaplayer.core.component.ComponentApi;
import lib.kalu.mediaplayer.util.MPLogUtil;
import tv.huan.bilibili.R;
import tv.huan.bilibili.utils.ADUtil;

public class PlayerComponentADStart extends RelativeLayout implements ComponentApi {

    public PlayerComponentADStart(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PlayerComponentADStart(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayerComponentADStart(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.common_player_component_ad, this, true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void callPlayerEvent(@NonNull int playState) {
        switch (playState) {
            case PlayerType.StateType.STATE_START:
                MPLogUtil.log("PlayerComponentADStart[show] => playState = " + playState);
                ADUtil.adPlaying(getContext().getApplicationContext());
                break;
            default:
                break;
        }
    }

    @Override
    public void gone() {
    }

    @Override
    public void show() {
    }
}