package tv.huan.common.util;

import android.util.Log;

public final class CommonUtil {

    private static String mTAG = "huan_common";
    private static boolean mEnable = true;

    public static void setLogger(boolean enable) {
        mEnable = enable;
    }

    public static void log(String message) {
        log(message, null);
    }

    public static void log(String message, Throwable throwable) {
        if (!mEnable || null == message)
            return;
        if (null == throwable) {
            Log.e(mTAG, message);
        } else {
            Log.e(mTAG, message, throwable);
        }
    }
}