package tv.huan.bilibili.widget.player;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import lib.kalu.frame.mvp.util.WrapperUtil;
import lib.kalu.mediaplayer.config.player.PlayerType;
import lib.kalu.mediaplayer.core.component.ComponentLoading;
import lib.kalu.mediaplayer.core.component.ComponentPause;
import lib.kalu.mediaplayer.core.component.ComponentSeek;
import lib.kalu.mediaplayer.core.component.ComponentSpeed;
import lib.kalu.mediaplayer.listener.OnPlayerChangeListener;
import lib.kalu.mediaplayer.widget.player.PlayerLayout;
import tv.huan.bilibili.R;
import tv.huan.bilibili.ui.detail.DetailActivity;
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


//    @Override
//    public void start(@NonNull String url) {
//        // 1
//        release();
//        // 2
//        StartBuilder.Builder builder = new StartBuilder.Builder();
//        builder.setLive(false);
//        builder.setLoop(true);
//        super.start(builder.build(), url);
//    }

    protected void addListeren() {
        LogUtil.log("PlayerView => addListeren =>");
        setPlayerChangeListener(new OnPlayerChangeListener() {

            @Override
            public void onProgress(@NonNull long position, @NonNull long duration) {
            }

            @Override
            public void onChange(int playState) {
                switch (playState) {
                    case PlayerType.StateType.STATE_END: //播放完成
                        Activity activity = WrapperUtil.getWrapperActivity(getContext());
                        LogUtil.log("PlayerView => addListeren => onChange => activity = " + activity);
                        if (null != activity && activity instanceof DetailActivity) {
                            ((DetailActivity) activity).completePlayer();
                        }
                        break;
                }
            }
        });
    }

    protected void init() {

        // 填充模式
//        if (getId() == R.id.qvoice_video || getId() == R.id.qsquare_video || getId() == R.id.qreport_video) {
        setScaleType(PlayerType.ScaleType.SCREEN_SCALE_16_9);
//        } else {
//            setScaleType(PlayerType.ScaleType.SCREEN_SCALE_MATCH_PARENT);
//        }

        // loading
        ComponentLoading loading = new ComponentLoading(getContext());
        addComponent(loading);

        // pause
        ComponentPause pause = new ComponentPause(getContext());
        pause.setPauseImageResource(R.mipmap.ic_launcher);
        pause.setBackgroundColor(Color.parseColor("#66000000"));
        addComponent(pause);

        // speed
        ComponentSpeed speed = new ComponentSpeed(getContext());
        addComponent(speed);

        // seekbar
        ComponentSeek seek = new ComponentSeek(getContext());
        addComponent(seek);

        // init
        PlayerComponentInit init = new PlayerComponentInit(getContext());
        addComponent(init);
    }
}