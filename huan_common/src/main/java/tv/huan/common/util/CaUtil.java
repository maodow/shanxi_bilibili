package tv.huan.common.util;


import androidx.annotation.Keep;
import androidx.annotation.NonNull;

@Keep
public final class CaUtil {

    private static String CARD_ID = "persist.sys.ca.card_id";
    private static String STB_ID = "persist.sys.hwconfig.stb_id";

    public static String getCa() {
        return getCa(null);
    }

    public static String getCa(@NonNull String defaultCa) {
        try {
            CommonUtil.log("CaUtil => getCa => defaultCa = " + defaultCa);
            String card_id = SystemPropertiesUtil.getProperty(CARD_ID, null);
            String stb_id = SystemPropertiesUtil.getProperty(STB_ID, null);
            CommonUtil.log("CaUtil => getCa => card_id = " + card_id);
            CommonUtil.log("CaUtil => getCa => stb_id = " + stb_id);
            if (null != card_id && card_id.length() > 0) {
                return card_id;
            } else if (null != stb_id && stb_id.length() > 0) {
                return stb_id;
            } else {
                throw new Exception("not find ca from SystemProperties");
            }
        } catch (Exception e) {
            CommonUtil.log("CaUtil => getCa => " + e.getMessage());
            return defaultCa;
        }
    }
}
