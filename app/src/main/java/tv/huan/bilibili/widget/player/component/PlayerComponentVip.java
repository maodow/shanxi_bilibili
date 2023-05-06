package tv.huan.bilibili.widget.player.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lib.kalu.mediaplayer.config.player.PlayerType;
import lib.kalu.mediaplayer.core.component.ComponentApi;
import lib.kalu.mediaplayer.util.MPLogUtil;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.MediaBean;
import tv.huan.bilibili.utils.GlideUtils;

public class PlayerComponentVip extends RelativeLayout implements ComponentApi {

    public PlayerComponentVip(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PlayerComponentVip(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayerComponentVip(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.common_player_component_vip, this, true);
    }

    @Override
    public void callPlayerEvent(@NonNull int playState) {
        switch (playState) {
            case PlayerType.StateType.STATE_START:
                MPLogUtil.log("PlayerComponentVip[gone] => playState = " + playState);
                gone();
                break;
        }
    }

    @Override
    public void gone() {
        try {
            findViewById(R.id.common_player_component_vip_img).setVisibility(View.GONE);
            findViewById(R.id.common_player_component_vip_cover).setVisibility(View.GONE);
            findViewById(R.id.common_player_component_vip_text).setVisibility(View.GONE);
        } catch (Exception e) {
        }
    }

    @Override
    public void show() {
        try {
            bringToFront();
            findViewById(R.id.common_player_component_vip_img).setVisibility(View.VISIBLE);
            findViewById(R.id.common_player_component_vip_cover).setVisibility(View.VISIBLE);
            findViewById(R.id.common_player_component_vip_text).setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }
    }

    public final void setData(@NonNull MediaBean data) {
        try {
            ImageView imageView = findViewById(R.id.common_player_component_vip_img);
            GlideUtils.loadHz(imageView.getContext(), data.getTempImageUrl(), imageView);
        } catch (Exception e) {
        }
    }
}