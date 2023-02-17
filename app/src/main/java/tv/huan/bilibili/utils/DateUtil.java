package tv.huan.bilibili.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static final String DATE_FORMAT   = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_1 = "HH:mm:ss";

    /**
     * 获取当前时间
     */
    public static String getDateTime() {
        return getDateTime(DATE_FORMAT);
    }

    public static String getDateTime(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 格式化播放时间
     * @param seconds 秒
     */
    public static String formatSeconds(long seconds){
        String standardTime;
        if (seconds <= 0){
            standardTime = "00:00:00";
        } else if (seconds < 60) {
            standardTime = String.format(Locale.getDefault(), "00:00:%02d", seconds % 60);
        } else if (seconds < 3600) {
            standardTime = String.format(Locale.getDefault(), "00:%02d:%02d", seconds / 60, seconds % 60);
        } else {
            standardTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", seconds / 3600, seconds % 3600 / 60, seconds % 60);
        }
        return standardTime;
    }
}
