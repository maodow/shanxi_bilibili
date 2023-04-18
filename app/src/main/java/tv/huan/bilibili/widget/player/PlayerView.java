package tv.huan.bilibili.widget.player;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import lib.kalu.frame.mvp.util.WrapperUtil;
import lib.kalu.mediaplayer.config.player.PlayerType;
import lib.kalu.mediaplayer.core.component.ComponentError;
import lib.kalu.mediaplayer.core.component.ComponentLoading;
import lib.kalu.mediaplayer.core.component.ComponentPause;
import lib.kalu.mediaplayer.core.component.ComponentSeek;
import lib.kalu.mediaplayer.core.component.ComponentSpeed;
import lib.kalu.mediaplayer.listener.OnPlayerChangeListener;
import lib.kalu.mediaplayer.widget.player.PlayerLayout;
import tv.huan.bilibili.R;
import tv.huan.bilibili.ui.detail.DetailActivity;
import tv.huan.bilibili.utils.ADUtil;
import tv.huan.bilibili.utils.LogUtil;

public class PlayerView extends PlayerLayout {
    public PlayerView(Context context) {
        super(context);
        init();
        addListeren();
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        addListeren();
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        addListeren();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        addListeren();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ADUtil.adRelease();
    }

    protected void addListeren() {
        setPlayerChangeListener(new OnPlayerChangeListener() {

            @Override
            public void onProgress(@NonNull long position, @NonNull long duration) {
            }

            @Override
            public void onChange(int playState) {
                switch (playState) {
                    case PlayerType.StateType.STATE_END: //播放完成
                        Activity activity = WrapperUtil.getWrapperActivity(getContext());
                        if (null != activity && activity instanceof DetailActivity) {
                            ((DetailActivity) activity).completePlayer();
                        }
                        break;
                }
            }
        });
    }

    protected void init() {
        // loading
        ComponentLoading loading = new ComponentLoading(getContext());
        loading.setComponentBackgroundColorInt(Color.parseColor("#000000"));
        addComponent(loading);
        // pause
        ComponentPause pause = new ComponentPause(getContext());
        pause.setComponentImageResource(R.mipmap.ic_launcher);
        pause.setComponentBackgroundColorInt(Color.parseColor("#66000000"));
        addComponent(pause);
        // error
        ComponentError error = new ComponentError(getContext());
        addComponent(error);
        // speed
        ComponentSpeed speed = new ComponentSpeed(getContext());
        addComponent(speed);
        // seekbar
        ComponentSeek seek = new ComponentSeek(getContext());
        addComponent(seek);
        // init
        PlayerComponentInit init = new PlayerComponentInit(getContext());
        addComponent(init);
        // adPause
        PlayerComponentADPause pauseAD = new PlayerComponentADPause(getContext());
        addComponent(pauseAD);
        // adStart
        PlayerComponentADStart startAD = new PlayerComponentADStart(getContext());
        addComponent(startAD);
    }
}