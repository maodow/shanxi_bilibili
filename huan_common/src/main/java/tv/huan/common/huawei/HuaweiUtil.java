package tv.huan.common.huawei;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.Keep;

import tv.huan.common.util.CommonUtil;

@Keep
public final class HuaweiUtil {

    private static String EPG_SERVER = "epg_server";
    private static String USER_TOKEN = "user_token";
    private static String USER_NAME = "username";

    public static String getEpgServer(Context context) {
        return getEpgServer(context, EPG_SERVER);
    }

    public static String getEpgServer(Context context, String key) {
        return getValue(context, key, null);
    }

    public static String getUserToken(Context context) {
        return getUserToken(context, USER_TOKEN);
    }

    public static String getUserToken(Context context, String key) {
        return getValue(context, key, null);
    }

    public static String getUserName(Context context) {
        return getUserName(context, USER_NAME);
    }

    public static String getUserName(Context context, String key) {
        return getValue(context, key, null);
    }

    private static String getValue(Context context, String key, String defaults) {

        String v = null;
        try {
            // content://stbconfig/authentication
            Uri uri = Uri.parse("content://stbconfig/authentication/" + key);

            Cursor cursor = context.getContentResolver().query(uri, null, "name = ?", new String[]{key}, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndex("value");
                v = cursor.getString(index);
            }
            if (null != cursor) {
                cursor.close();
            }
            if (null == v || v.length() <= 0) {
                v = defaults;
            }
        } catch (Exception e) {
            v = defaults;
        }
        CommonUtil.log("HuaweiUtil => getValue => key = " + key + ", value = " + v);
        return v;
    }
}
