package tv.huan.common.cmcc;

import tv.huan.common.util.SystemPropertiesUtil;

public final class CmccUtil {

    private static String RO_MAC = "ro.mac";
    private static String RO_SERIALNO = "ro.serialno";
    private static String STBPLATFORM = "persist.sys.ch.stbplatform";


    public static String getMac() {
        return SystemPropertiesUtil.getProperty(RO_MAC, null);
    }

    public static String getSerialno() {
        return SystemPropertiesUtil.getProperty(RO_SERIALNO, null);
    }

    public static String getPlatform() {
        return SystemPropertiesUtil.getProperty(STBPLATFORM, null);
    }
}
