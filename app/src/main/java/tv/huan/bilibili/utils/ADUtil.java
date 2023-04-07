package tv.huan.bilibili.utils;

import android.app.Application;
import android.content.Context;

import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.scene.ad.opensdk.AdSlot;
import tv.scene.ad.opensdk.INormAd;
import tv.scene.ad.opensdk.SceneAdConfig;
import tv.scene.ad.opensdk.SceneAdSDK;
import tv.scene.ad.opensdk.basecallback.AbsAdListener;
import tv.scene.ad.opensdk.basecallback.INormAdFactory;

public final class ADUtil {

    private static INormAdFactory mINormAdFactory = null;

    private static void adCreateAdFactory(Context context) {
        adRelease();
        try {
            mINormAdFactory = SceneAdSDK.getAdManager().createAdFactory(context);
        } catch (Exception e) {
            LogUtil.log("ADUtil => adCreateAdFactory => " + e.getMessage());
        }
    }

    public static void adRelease() {
        try {
            if (null == mINormAdFactory)
                throw new Exception("waring mINormAdFactory is null");
            mINormAdFactory.releasePoint();
            LogUtil.log("ADUtil => adRelease => succ");
        } catch (Exception e) {
            LogUtil.log("ADUtil => adRelease => " + e.getMessage());
        }
    }

    public static void adInit(Application application) {
        try {

            SceneAdConfig config = new SceneAdConfig.Builder()
                    //appkey到后台申请，每个应用保证唯一性
                    .setAppKey("7dc15107ec1341ae80bf5f49fee16af2")
                    //应用名称，对接应用添加
                    .setAppName(application.getResources().getString(R.string.app_name))
                    //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                    .setOpenLog(BuildConfig.HUAN_LOG)
                    .setDebugUrl(BuildConfig.HUAN_LOG)
                    //厂商设置
                    .setManufacture("厂商渠道")
                    //机型设置
                    .setDeviceModel("机型")
                    .setRequestTimeOutSeconds(10)
                    .builder();
            SceneAdSDK.init(application, config);
            LogUtil.log("ADUtil => adInit => succ");
        } catch (Exception e) {
            LogUtil.log("ADUtil => adInit => " + e.getMessage());
        }
    }

    public static void adSplash(Context context) {

        AdSlot slot = new AdSlot.Builder()
                .setCodeId("test-kaiping")
                .setDisplayCountDown(false)
                .setMediaView(null)
                .build();

        adCreateAdFactory(context);
        mINormAdFactory.loadAd(slot, new AbsAdListener() {
            @Override
            public void onError(int code, String message) {
                LogUtil.log("ADUtil => adSplash => onError => code = " + code + ", message = " + message);
            }

            @Override
            public void onLoadAd(INormAd normAd) {
                LogUtil.log("ADUtil => adSplash => onLoadAd");
            }
        });
    }

    public static void adPlaying(Context context) {

        AdSlot slot = new AdSlot.Builder()
                .setCodeId("test-kaiping")
                .setDisplayCountDown(false)
                .setMediaView(null)
                .build();

        adCreateAdFactory(context);
        mINormAdFactory.loadAd(slot, new AbsAdListener() {
            @Override
            public void onError(int code, String message) {
                LogUtil.log("ADUtil => adPlaying => onError => code = " + code + ", message = " + message);
            }

            @Override
            public void onLoadAd(INormAd normAd) {
                LogUtil.log("ADUtil => adPlaying => onLoadAd");
            }
        });
    }

    public static void adPause(Context context) {

        AdSlot slot = new AdSlot.Builder()
                .setCodeId("test-zanting")
                .setDisplayCountDown(false)
                .setMediaView(null)
                .build();

        adCreateAdFactory(context);
        mINormAdFactory.loadAd(slot, new AbsAdListener() {
            @Override
            public void onError(int code, String message) {
                LogUtil.log("ADUtil => adPause => onError => code = " + code + ", message = " + message);
            }

            @Override
            public void onLoadAd(INormAd normAd) {
                LogUtil.log("ADUtil => adPause => onLoadAd");
            }
        });
    }

    public static void adExit(Context context) {

        AdSlot slot = new AdSlot.Builder()
                .setCodeId("test-tuichu")
                .setDisplayCountDown(false)
                .setMediaView(null)
                .build();

        adCreateAdFactory(context);
        mINormAdFactory.loadAd(slot, new AbsAdListener() {
            @Override
            public void onError(int code, String message) {
                LogUtil.log("ADUtil => adExit => onError => code = " + code + ", message = " + message);
            }

            @Override
            public void onLoadAd(INormAd normAd) {
                LogUtil.log("ADUtil => adExit => onLoadAd");
            }
        });
    }
}
