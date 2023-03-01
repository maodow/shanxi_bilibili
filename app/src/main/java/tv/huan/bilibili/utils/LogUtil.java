
package tv.huan.bilibili.utils;

import android.util.Log;

import androidx.annotation.NonNull;

public final class LogUtil {

    private static String TAG = "huan_log";
    private static boolean mEnable = false;

    public static void setLogger(boolean v) {
        mEnable = v;
    }

    public static void log(@NonNull String message) {
        log(TAG, message, null);
    }

    public static void log(@NonNull String tag, @NonNull String message) {
        log(tag, message, null);
    }

    public static void log(@NonNull String tag, @NonNull String message, @NonNull Throwable throwable) {

        if (null == throwable) {
            Log.e(tag, message);
        } else {
            Log.e(tag, message, throwable);
        }
    }
}

