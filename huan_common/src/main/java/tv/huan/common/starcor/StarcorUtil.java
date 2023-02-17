package tv.huan.common.starcor;

import android.content.Context;

import tv.huan.common.util.CommonUtil;

public final class StarcorUtil {

    public static boolean mediaAuth(String productId) {
        try {
            return StarcorProvider.mediaAuth(productId);
        } catch (Exception e) {
            CommonUtil.log("HEBEI_AIDL => mediaAuth => " + e.getMessage());
            return false;
        }
    }

    public static boolean auth(Context context) {
        try {
            return StarcorProvider.auth(context);
        } catch (Exception e) {
            CommonUtil.log("HEBEI_AIDL => auth => " + e.getMessage());
            return false;
        }
    }
}