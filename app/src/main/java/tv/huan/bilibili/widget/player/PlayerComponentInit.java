package tv.huan.bilibili.widget.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

import lib.kalu.mediaplayer.config.player.PlayerType;
import lib.kalu.mediaplayer.core.component.ComponentApi;
import lib.kalu.mediaplayer.util.MPLogUtil;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.MediaBean;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.LogUtil;

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
                MPLogUtil.log("ComponentInit[show] => playState = " + playState);
                bringToFront();
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

    public final void setData(@NonNull MediaBean data) {
        LogUtil.log("PlayerComponentInit => setData => data = " + new Gson().toJson(data));
        try {
            ImageView imageView = findViewById(R.id.detail_player_item_cover);
            GlideUtils.loadHz(imageView.getContext(), data.getTempImageUrl(), imageView);
        } catch (Exception e) {
        }
        try {
            TextView textView = findViewById(R.id.detail_player_item_data);
            String title = data.getTempTitle();
            int episodeIndex = data.getEpisodeIndex();
            if (episodeIndex < 0) {
                textView.setText(title);
            } else {
                int position = episodeIndex + 1;
                String string = textView.getResources().getString(R.string.detail_playing_index, title, position);
                textView.setText(string);
            }
        } catch (Exception e) {
        }
    }

    public final void updatePosition(@NonNull int position) {
        try {
            TextView textView = findViewById(R.id.detail_player_item_data);
            String s = String.valueOf(textView.getText());
            int index = s.indexOf("第");
            String substring = s.substring(0, index - 2);
            String string = textView.getResources().getString(R.string.detail_playing_index, substring, position + 1);
            textView.setText(string);
        } catch (Exception e) {
        }
    }

    public final boolean checkPlayerPlayingPosition(@NonNull int position) {
        try {
            TextView textView = findViewById(R.id.detail_player_item_data);
            String s = String.valueOf(textView.getText());
            int index = s.indexOf("第");
            String substring = s.substring(index + 1, index + 2);
            return Integer.parseInt(substring) == position + 1;
        } catch (Exception e) {
            return false;
        }
    }
}