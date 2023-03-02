package tv.huan.bilibili.widget.player;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import lib.kalu.mediaplayer.config.player.PlayerType;
import lib.kalu.mediaplayer.config.start.StartBuilder;
import lib.kalu.mediaplayer.core.controller.ControllerEmpty;
import lib.kalu.mediaplayer.core.controller.component.ComponentLoading;
import lib.kalu.mediaplayer.core.controller.component.ComponentPause;
import lib.kalu.mediaplayer.core.controller.component.ComponentSeek;
import lib.kalu.mediaplayer.core.controller.component.ComponentSpeed;
import lib.kalu.mediaplayer.core.player.VideoLayout;
import tv.huan.bilibili.R;

public final class PlayerView extends VideoLayout {

    public PlayerView(Context context) {
        super(context);
        init();
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    public void start(@NonNull String url) {
        // 1
        release();
        // 2
        StartBuilder.Builder builder = new StartBuilder.Builder();
        builder.setLive(false);
        builder.setLoop(true);
        super.start(builder.build(), url);
    }

    private void init() {

        // 填充模式
//        if (getId() == R.id.qvoice_video || getId() == R.id.qsquare_video || getId() == R.id.qreport_video) {
        setScaleType(PlayerType.ScaleType.SCREEN_SCALE_16_9);
//        } else {
//            setScaleType(PlayerType.ScaleType.SCREEN_SCALE_MATCH_PARENT);
//        }

        // controller
        ControllerEmpty controller = new ControllerEmpty(getContext());
        setControllerLayout(controller);

        // loading
        ComponentLoading loading = new ComponentLoading(getContext());
        controller.addComponent(loading);

        // pause
        ComponentPause pause = new ComponentPause(getContext());
        pause.setPauseImageResource(R.mipmap.ic_launcher);
        pause.setBackgroundColor(Color.parseColor("#66000000"));
        controller.addComponent(pause);

        // speed
        ComponentSpeed speed = new ComponentSpeed(getContext());
        controller.addComponent(speed);

        // seekbar
        ComponentSeek seek = new ComponentSeek(getContext());
        controller.addComponent(seek);

        // 监听器
        setOnChangeListener(new lib.kalu.mediaplayer.listener.OnChangeListener() {

            @Override
            public void onProgress(@NonNull long position, @NonNull long durning) {
            }

            @Override
            public void onChange(int playState) {
                switch (playState) {
                    //播放未开始，初始化
                    case PlayerType.StateType.STATE_INIT:
                        break;
                    //开始播放中止
                    case PlayerType.StateType.STATE_START_ABORT:
                        break;
                    //播放准备就绪
                    case PlayerType.StateType.STATE_LOADING_STOP:
                        break;
                    //播放错误
                    case PlayerType.StateType.STATE_ERROR:
                        break;
                    //正在缓冲
                    case PlayerType.StateType.STATE_BUFFERING_START:
                        break;
                    //播放准备中
                    case PlayerType.StateType.STATE_LOADING_START:
                        //正在播放
                    case PlayerType.StateType.STATE_START:
                        break;
                    //暂停播放
                    case PlayerType.StateType.STATE_PAUSE:
                        break;
                    //暂停缓冲
                    case PlayerType.StateType.STATE_BUFFERING_STOP:
                        break;
                    //播放完成
                    case PlayerType.StateType.STATE_END:
                        break;
                }
            }
        });
    }
}