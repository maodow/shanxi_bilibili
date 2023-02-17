package tv.huan.common.util;

import androidx.annotation.Keep;

import java.lang.reflect.Method;

@Keep
public final class SystemPropertiesUtil {

    private static String NAME = "android.os.SystemProperties";
    private static String GET = "get";
    private static String SET = "set";

    public static String getProperty(String key, String defaultValue) {
        String value;
        try {
            Class<?> c = Class.forName(NAME);
            Method get = c.getMethod(GET, String.class, String.class);
            value = (String) (get.invoke(c, key, defaultValue));
        } catch (Exception e) {
            value = defaultValue;
        }
        return value;
    }

    public static void setProperty(String key, String value) {
        try {
            Class<?> c = Class.forName(NAME);
            Method set = c.getMethod(SET, String.class, String.class);
            set.invoke(c, key, value);
        } catch (Exception e) {
        }
    }
}
