package tv.huan.bilibili.widget.player;

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
import tv.huan.bilibili.R;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.LogUtil;

public class PlayerComponentInitTemplate extends RelativeLayout implements ComponentApi {

    public PlayerComponentInitTemplate(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PlayerComponentInitTemplate(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayerComponentInitTemplate(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.common_player_component_init_template21, this, true);
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
            case PlayerType.StateType.STATE_INIT:
                LogUtil.log("PlayerComponentInitTemplate21[show] => playState = " + playState);
                bringToFront();
                show();
                break;
            case PlayerType.StateType.STATE_ERROR:
            case PlayerType.StateType.STATE_ERROR_IGNORE:
            case PlayerType.StateType.STATE_ERROR_NET:
            case PlayerType.StateType.STATE_LOADING_START:
                LogUtil.log("PlayerComponentInitTemplate21[gone] => playState = " + playState);
                gone();
                break;
        }
    }

    @Override
    public void gone() {
        findViewById(R.id.common_player_component_init_template21_img).setVisibility(View.GONE);
    }

    @Override
    public void show() {
        findViewById(R.id.common_player_component_init_template21_img).setVisibility(View.VISIBLE);
    }

    public final void showImage(@NonNull String imgUrl) {
        try {
            ImageView imageView = findViewById(R.id.common_player_component_init_template21_img);
            GlideUtils.loadHz(imageView.getContext(), imgUrl, imageView);
        } catch (Exception e) {
            LogUtil.log("PlayerComponentInitTemplate => showImage => " + e.getMessage());
        }
    }
}