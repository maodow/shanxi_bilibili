package tv.huan.bilibili.utils;

import tv.huan.common.util.CaUtil;

public final class BoxUtil {

    public static int getProdId() {
        try {
            return 2;
        } catch (Exception e) {
            return 2;
        }
    }

    public static String getCa() {
        return CaUtil.getCa("00380035890");
    }
}
