package tv.huan.bilibili.widget.player.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lib.kalu.mediaplayer.config.player.PlayerType;
import lib.kalu.mediaplayer.core.component.ComponentApi;
import lib.kalu.mediaplayer.util.MPLogUtil;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.MediaBean;
import tv.huan.bilibili.utils.GlideUtils;

public class PlayerComponentInit extends RelativeLayout implements ComponentApi {

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
        LayoutInflater.from(context).inflate(R.layout.common_player_component_init_detail, this, true);
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
            case PlayerType.StateType.STATE_END:
                MPLogUtil.log("ComponentInit[show] => playState = " + playState);
                show();
                break;
            case PlayerType.StateType.STATE_ERROR:
            case PlayerType.StateType.STATE_ERROR_IGNORE:
            case PlayerType.StateType.STATE_LOADING_START:
                MPLogUtil.log("ComponentInit[gone] => playState = " + playState);
                gone();
                break;
        }
    }

    @Override
    public void gone() {
        try {
            findViewById(R.id.detail_player_item_center).setVisibility(View.GONE);
            findViewById(R.id.detail_player_item_cover).setVisibility(View.GONE);
            findViewById(R.id.detail_player_item_shadow).setVisibility(View.GONE);
            findViewById(R.id.detail_player_item_playing).setVisibility(View.GONE);
            findViewById(R.id.detail_player_item_warning).setVisibility(View.GONE);
            findViewById(R.id.detail_player_item_data).setVisibility(View.GONE);
            findViewById(R.id.detail_player_item_logo).setVisibility(View.GONE);
        } catch (Exception e) {
        }
    }

    @Override
    public void show() {
        try {
            bringToFront();
            findViewById(R.id.detail_player_item_center).setVisibility(View.VISIBLE);
            findViewById(R.id.detail_player_item_cover).setVisibility(View.VISIBLE);
            findViewById(R.id.detail_player_item_shadow).setVisibility(View.VISIBLE);
            findViewById(R.id.detail_player_item_playing).setVisibility(View.VISIBLE);
            findViewById(R.id.detail_player_item_warning).setVisibility(View.VISIBLE);
            findViewById(R.id.detail_player_item_data).setVisibility(View.VISIBLE);
            findViewById(R.id.detail_player_item_logo).setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }
    }

    public final void setData(@NonNull MediaBean data) {
        try {
            ImageView imageView = findViewById(R.id.detail_player_item_cover);
            GlideUtils.loadHz(imageView.getContext(), data.getTempImageUrl(), imageView);
        } catch (Exception e) {
        }
        try {
            TextView textView = findViewById(R.id.detail_player_item_data_name);
            textView.setText(data.getTempTitle());
        } catch (Exception e) {
        }
    }

    public final void updatePosition(@NonNull int position) {
        try {
            TextView textView = findViewById(R.id.detail_player_item_data_position);
            String string = textView.getResources().getString(R.string.detail_playing_position, position + 1);
            textView.setText(string);
        } catch (Exception e) {
        }
    }

//    public final boolean checkPlayerPlayingPosition(@NonNull int position) {
//        try {
//            TextView textView = findViewById(R.id.detail_player_item_data_position);
//            String s = String.valueOf(textView.getText());
//            int start = s.indexOf("第");
//            int end = s.indexOf("集");
//            String substring = s.substring(start + 1, end);
//            Toast.makeText(getContext(), ""+substring, Toast.LENGTH_SHORT).show();
//            return Integer.parseInt(substring) == position + 1;
//        } catch (Exception e) {
//            return false;
//        }
//    }

    public final int getPosition() {
        try {
            TextView textView = findViewById(R.id.detail_player_item_data_position);
            String s = String.valueOf(textView.getText());
            int index = s.indexOf("第");
            String substring = s.substring(index + 1, index + 2);
            return Integer.parseInt(substring);
        } catch (Exception e) {
            return -1;
        }
    }
}