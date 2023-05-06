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

public class PlayerComponentADPause extends RelativeLayout implements ComponentApi {

    public PlayerComponentADPause(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PlayerComponentADPause(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayerComponentADPause(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
            case PlayerType.StateType.STATE_PAUSE:
                MPLogUtil.log("PlayerComponentADPause[show] => playState = " + playState);
                ADUtil.adPause(getContext().getApplicationContext());
                break;
            default:
                break;
        }
    }

//    private INormAdFactory mINormAdFactory = null;


    @Override
    public void gone() {
//        ADUtil.adRelease();
//        if (null != mINormAdFactory) {
//            mINormAdFactory.releasePoint();
//        }
    }

    @Override
    public void show() {
        ADUtil.adPause(getContext().getApplicationContext());
//        if (null != mINormAdFactory) {
//            mINormAdFactory.releasePoint();
//        }
//        mINormAdFactory = SceneAdSDK.getAdManager().createAdFactory(getContext().getApplicationContext());
//
//        mINormAdFactory.loadAd(new AdSlot.Builder()
//                .setCodeId("test-kaiping")
//                .setDisplayCountDown(false)
//                .setMediaView(null)
//                .setAutoRelease(false)
//                .setAutoRequestFocus(false)
//                .build(), new AbsAdListener() {
//            @Override
//            public void onError(int code, String message) {
//                LogUtil.log("ADUtil => adPlaying => onError => code = " + code + ", message = " + message);
//            }
//
//            @Override
//            public void onLoadAd(INormAd normAd) {
//                LogUtil.log("ADUtil => adPlaying => onLoadAd => view = " + normAd.getAdView());
//                normAd.startPlay(PlayerComponentPauseAD.this);
//            }
//
//            @Override
//            public void onComplete(View view) {
//                LogUtil.log("ADUtil => adPlaying => onComplete => view = " + view);
//            }
//        });
    }
}