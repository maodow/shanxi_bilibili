package tv.huan.bilibili.utils;

import android.content.Context;

import lib.kalu.frame.mvp.context.FrameContext;
import tv.huan.bilibili.BuildConfig;
import tv.huan.heilongjiang.HeilongjiangApi;

public final class BoxUtil {

    public static int getProdId() {
        try {
            return 1;
        } catch (Exception e) {
            return 1;
        }
    }

    public static String getCa() {
        try {
            Context context = FrameContext.getApplicationContext();
            String userId = HeilongjiangApi.getUserId(context);
            if (null == userId || userId.length() <= 0)
                throw new Exception("userId error: null");
            return userId;
        } catch (Exception e) {
            LogUtil.log("BoxUtil => getCa => " + e.getMessage());
            return "00380035890";
//            return BuildConfig.HUAN_CHECK_USERID ? "null" : "00380035890";
        }
    }
}
