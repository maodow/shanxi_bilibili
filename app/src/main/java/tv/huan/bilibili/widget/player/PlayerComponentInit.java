
package tv.huan.bilibili.widget.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lib.kalu.mediaplayer.config.player.PlayerType;
import lib.kalu.mediaplayer.core.controller.base.ControllerWrapper;
import lib.kalu.mediaplayer.core.controller.impl.ComponentApi;
import lib.kalu.mediaplayer.util.MPLogUtil;
import tv.huan.bilibili.R;
import tv.huan.bilibili.ui.detail.template.DetailTemplatePlayer;
import tv.huan.bilibili.utils.GlideUtils;

public class PlayerComponentInit extends RelativeLayout implements ComponentApi {

    private ControllerWrapper mControllerWrapper;

    public PlayerComponentInit(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PlayerComponentInit(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayerComponentInit(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.common_player_component_init, this, true);
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
    public void attach(@NonNull ControllerWrapper controllerWrapper) {
        mControllerWrapper = controllerWrapper;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onVisibilityChanged(boolean isVisible, Animation anim) {

    }

    @Override
    public void onPlayStateChanged(int playState) {
        switch (playState) {
            case PlayerType.StateType.STATE_INIT:
                MPLogUtil.log("ComponentInit[show] => playState = " + playState);
                bringToFront();
                show();
                break;
            default:
                MPLogUtil.log("ComponentInit[gone] => playState = " + playState);
                gone();
                break;
        }
    }

    @Override
    public void onWindowStateChanged(int playerState) {
    }

    @Override
    public void onLockStateChanged(boolean isLocked) {
    }

    @Override
    public void gone() {
        findViewById(R.id.detail_player_item_center).setVisibility(View.GONE);
        findViewById(R.id.detail_player_item_cover).setVisibility(View.GONE);
        findViewById(R.id.detail_player_item_playing).setVisibility(View.GONE);
        findViewById(R.id.detail_player_item_warning).setVisibility(View.GONE);
        findViewById(R.id.detail_player_item_data).setVisibility(View.GONE);
        findViewById(R.id.detail_player_item_logo).setVisibility(View.GONE);
    }

    @Override
    public void show() {
        findViewById(R.id.detail_player_item_center).setVisibility(View.VISIBLE);
        findViewById(R.id.detail_player_item_cover).setVisibility(View.VISIBLE);
        findViewById(R.id.detail_player_item_playing).setVisibility(View.VISIBLE);
        findViewById(R.id.detail_player_item_warning).setVisibility(View.VISIBLE);
        findViewById(R.id.detail_player_item_data).setVisibility(View.VISIBLE);
        findViewById(R.id.detail_player_item_logo).setVisibility(View.VISIBLE);
//        findViewById(R.id.module_mediaplayer_component_init_txt).setVisibility(View.VISIBLE);
//        findViewById(R.id.module_mediaplayer_component_init_bg).setVisibility(View.VISIBLE);
    }

    public final void setData(@NonNull String imgUrl, @NonNull String title, @NonNull int position) {
        try {
            ImageView imageView = findViewById(R.id.detail_player_item_cover);
            GlideUtils.loadHz(imageView.getContext(), imgUrl, imageView);
        } catch (Exception e) {
        }
        try {
            TextView textView = findViewById(R.id.detail_player_item_data);
            if (position >= 1) {
                String string = textView.getResources().getString(R.string.detail_playing_index, title, position);
                textView.setText(string);
            } else {
                textView.setText(title);
            }
        } catch (Exception e) {
        }
    }
}