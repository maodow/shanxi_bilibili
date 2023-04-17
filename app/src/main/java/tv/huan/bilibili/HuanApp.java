package tv.huan.bilibili;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import lib.kalu.frame.mvp.context.FrameContext;
import lib.kalu.frame.mvp.util.MvpUtil;
import lib.kalu.leanback.util.LeanBackUtil;
import lib.kalu.mediaplayer.config.player.PlayerBuilder;
import lib.kalu.mediaplayer.config.player.PlayerManager;
import lib.kalu.mediaplayer.config.player.PlayerType;
import tv.huan.bilibili.crash.HuanCrash;
import tv.huan.bilibili.utils.ADUtil;
import tv.huan.bilibili.utils.LogUtil;
import tv.huan.heilongjiang.HeilongjiangApi;

public final class HuanApp extends Application {

    public static Context getContext() {
        return FrameContext.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ADUtil.adInit(this);
        HeilongjiangApi.setLogger(true);
        LogUtil.setLogger(true);
        MvpUtil.setLogger(true);
        LeanBackUtil.setLogger(true);
        HuanCrash.getInstance().init();
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
//                LogUtil.log("HuanApp", throwable.getLocalizedMessage(), throwable);
            }
        });

        initPlayer();
    }

    private void initPlayer() {
        PlayerBuilder build = new PlayerBuilder.Builder()
                .setLog(false)
                .setKernel(PlayerType.KernelType.EXO)
                .setRender(PlayerType.RenderType.TEXTURE_VIEW)
                .setExoFFmpeg(PlayerType.FFmpegType.EXO_EXTENSION_RENDERER_OFF)
                .setCacheType(PlayerType.CacheType.NONE)
                .build();
        PlayerManager.getInstance().setConfig(build);
    }
}