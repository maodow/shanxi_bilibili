package tv.huan.bilibili.widget.player;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import lib.kalu.mediaplayer.config.player.PlayerType;
import lib.kalu.mediaplayer.core.controller.ControllerEmpty;
import lib.kalu.mediaplayer.core.controller.component.ComponentLoading;
import lib.kalu.mediaplayer.core.player.VideoLayout;

public class PlayerViewSpecial extends VideoLayout {

    public PlayerViewSpecial(Context context) {
        super(context);
        init();
    }

    public PlayerViewSpecial(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayerViewSpecial(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayerViewSpecial(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        // 填充模式
//        if (getId() == R.id.qvoice_video || getId() == R.id.qsquare_video || getId() == R.id.qreport_video) {
        setScaleType(PlayerType.ScaleType.SCREEN_SCALE_16_9);
//        } else {
//            setScaleType(PlayerType.ScaleType.SCREEN_SCALE_MATCH_PARENT);
//        }

        // loading
        ComponentLoading loading = new ComponentLoading(getContext());

        // component
        ControllerEmpty controller = new ControllerEmpty(getContext());
        controller.addComponent(loading);

        // add
        setControllerLayout(controller);

        // 监听器
        setOnChangeListener(new lib.kalu.mediaplayer.listener.OnChangeListener() {

            @Override
            public void onProgress(@NonNull long position, @NonNull long durning) {
//                MediaLogUtil.log("onProgress11 => position = " + position+", durning = "+durning);

//                if (null != mOnListener) {
//
//                    // 1
//                    // ms => s
//                    long c = position / 1000;
//                    long c1 = c / 60;
//                    long c2 = c % 60;
//                    StringBuilder stringBuilder = new StringBuilder();
//                    if (c1 < 10) {
//                        stringBuilder.append("0");
//                    }
//                    stringBuilder.append(c1);
//                    stringBuilder.append(":");
//                    if (c2 < 10) {
//                        stringBuilder.append("0");
//                    }
//                    stringBuilder.append(c2);
//                    stringBuilder.append("/");
//
//                    // 2
//                    // ms => s
//                    long d = durning / 1000;
//                    long d1 = d / 60;
//                    long d2 = d % 60;
//                    if (d1 < 10) {
//                        stringBuilder.append("0");
//                    }
//                    stringBuilder.append(d1);
//                    stringBuilder.append(":");
//                    if (d2 < 10) {
//                        stringBuilder.append("0");
//                    }
//                    stringBuilder.append(d2);
//                    String s = stringBuilder.toString();
////                    mOnListener.onProgress((int) position, (int) durning, s);
//                }

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
//                        try {
//                            int count = getChildCount();
//                            getChildAt(count - 1).setVisibility(View.GONE);
//                        } catch (Exception e) {
//                        }
                        break;
                    //暂停播放
                    case PlayerType.StateType.STATE_PAUSE:
//                        try {
//                            int count = getChildCount();
//                            getChildAt(count - 1).setVisibility(View.VISIBLE);
//                        } catch (Exception e) {
//                        }
                        break;
                    //暂停缓冲
                    case PlayerType.StateType.STATE_BUFFERING_STOP:
                        break;
                    //播放完成
                    case PlayerType.StateType.STATE_END:
//                        if (null != mOnListener) {
//                            Message message = new Message();
//                            message.what = 1002;
//                            mHandler.sendMessage(message);
//                            mOnListener.onCompletion();
//                        }
                        break;
                }
            }
        });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return false;
    }
}
