package tv.huan.bilibili.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtils {

    private static Calendar cal;
    private static Date date = new Date();

    private static DateFormat dateformatYMD;
    private static DateFormat dateformatyyMMddSPhhmmss;
    private static DateFormat dateformatyyMMddhhmmss;

    static {
        dateformatYMD = new SimpleDateFormat("yyyy-MM-dd");
        dateformatyyMMddSPhhmmss = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        dateformatyyMMddhhmmss = new SimpleDateFormat("yyyyMMddhhmmss");
    }

    public static void main(String[] args) {
//		System.out.println(duringTime("2013-06-17 10:45:00", "2013-06-17 10:48:29"));
    }

    public static String getBeforeDate() {
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
//		return dateformatYMD.format(cal.getTime());
        return "2013-09-08";
    }

    public static String getNowDate() {
        cal = Calendar.getInstance();
        cal.setTime(date);
        return dateformatYMD.format(cal.getTime());
    }

    public static String getNowDateTime() {
        cal = Calendar.getInstance();
        cal.setTime(date);
        return dateformatyyMMddSPhhmmss.format(cal.getTime());
    }

    public static String getAfterDate() {
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
//		return dateformatYMD.format(cal.getTime());
        return "2013-09-10";
    }

    public static int dateCompare(String now, String date) throws ParseException {
        Date nowD = dateformatYMD.parse(now);
        Date dateD = dateformatYMD.parse(date);
        if (nowD.getTime() > dateD.getTime()) {
            return 1;
        } else if (nowD.getTime() < dateD.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }

    public static String duringTime(String start, String end) {
        StringBuffer during = new StringBuffer();
        try {
            long sT = dateformatyyMMddSPhhmmss.parse(start).getTime();
            long eT = dateformatyyMMddSPhhmmss.parse(end).getTime();

            long interval = (eT - sT) / 1000;// 秒
            long day = interval / (24 * 3600);// 天
            long hour = interval % (24 * 3600) / 3600;// 小时
            long minute = interval % 3600 / 60;// 分钟
            long second = interval % 60;// 秒

            hour += 24 * day;

            during.append(prefixZero(hour));

            during.append(prefixZero(minute));

            during.append(prefixZero(second));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return during.toString();

    }

    private static String prefixZero(long hour) {
        String temp = null;
        if (hour < 0) {
            return null;
        }
        if ((hour + "").length() < 2) {
            temp = "0" + hour;
        } else {
            temp = hour + "";
        }
        return temp;
    }

    public static String stringToyyMMddhhmmss(String date) {
        try {
            date.trim();
            date = dateformatyyMMddhhmmss.format(date);
        } catch (Exception e) {
            //e.printStackTrace();
            date = date.replace("-", "");
            date = date.replace(" ", "");
            date = date.replace(":", "");
        }

        return date;
    }

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private static DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static DateFormat dateTimeFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private static DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public static final DateFormat getDateFormat() {
        return dateFormat;
    }

    public static final DateFormat getDateTimeFormat() {
        return dateTimeFormat;
    }

    public static final DateFormat getDateTimeFormat2() {
        return dateTimeFormat2;
    }

    /**
     * 返回指定日期的毫秒数
     *
     * @param dateStr 格式:2010-12-12 10:10:10
     * @return
     */
    public static final long getTimeInMillis(String dateStr) {
        Calendar calendar = Calendar.getInstance();
        Date date = parseDateTime(dateStr);
        if (null == date) {
            return 0;
        }
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 返回指定日期的毫秒数
     *
     * @param date Date
     * @return
     */
    public static final long getTimeInMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (null == date) {
            return 0;
        }
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 把指定日期按指定格式转换， 并最终返回该日期的毫秒数
     *
     * @param dateStr
     * @param dateFormat
     * @return
     */
    public static final long getTimeInMillis(String dateStr, String dateFormat) {
        Calendar calendar = Calendar.getInstance();
        Date date = parseDateTime(dateStr, dateFormat);
        if (null == date) {
            return 0;
        }
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    public static final Date nowDate(int timeZone) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+" + timeZone));
        return new Date();
    }

    public static final Date nowDate() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        return new Date();
    }

    public static final int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static final Date getDate(String date) {
        Date datetime = null;
        try {
            datetime = dateFormat.parse(date);
        } catch (ParseException e) {
            Log.e("DateUtil", "format date error");
        }
        return datetime;
    }

    public static final int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (calendar.get(Calendar.MONTH) + 1);
    }

    public static final int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static final Date getDate(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date);
        return calendar.getTime();
    }

    public static final Date getDateTime(int year, int month, int date, int hour, int minute,
                                         int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date, hour, minute, second);
        return calendar.getTime();
    }

    public static final Date addDate(Date oldDate, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldDate);
        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public static String addDate_1(int iDate) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, iDate);
        Date date = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 按传入的类型(年月日时分秒)添加时间
     *
     * @param type , ex: Calendar.HOUR, Calendar.MINUTE,
     * @param num
     * @return
     */
    public static final String addNowTimeByType(int type, int num) {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(type, num);

        return dateTimeFormat.format(nowTime.getTime());
    }

    /**
     * 增加天数
     *
     * @param format 格式化参数
     * @param date   日期ssss
     * @param n      天数
     * @return
     */
    public static final String addDay(String format, String date, int n) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(date));
            cd.add(Calendar.DATE, n);
            return sdf.format(cd.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    public static final Date addTime(Date oldDate, int year, int month, int day, int hour,
                                     int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldDate);
        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, minute);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    public static final Date addDay(Date date, int n) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.DATE, n);
        return cd.getTime();
    }

    /**
     * 取得日期的天数
     *
     * @param date
     * @return int
     */
    public static final int getDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        date = addDay(date, -1);
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }


    /**
     * 取得当前日期是多少周
     *
     * @param date Date
     * @return
     */
    public static final int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        date = addDay(date, -1);
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 取得当前日期是多少周
     *
     * @param str     日期
     * @param pattern 时间格式
     * @return
     * @throws ParseException
     */
    public static final int getWeekOfYear(String str, String pattern) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date date = formatter.parse(str);
        date = addDay(date, -1);
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 得到某一年周的总数
     *
     * @param year
     * @return
     */
    public static final int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        int day = c.get(Calendar.DAY_OF_WEEK);
        if (day == 7) {
            return getWeekOfYear(c.getTime());
        } else {
            c.set(year, Calendar.DECEMBER, 24, 23, 59, 59);
            return c.get(Calendar.WEEK_OF_YEAR);
        }
    }

    /**
     * 得到二个日期间的间隔天数
     *
     * @param stdate  开始日期
     * @param enddate 结束日期
     * @return
     */
    public static final String getTwoDay(String stdate, String enddate) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            Date date = myFormatter.parse(stdate);
            Date mydate = myFormatter.parse(enddate);
            if (date.after(mydate)) {
                Date cal = date;
                date = mydate;
                mydate = cal;
            }
            day = (mydate.getTime() - date.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 得到二个日期的差
     *
     * @param stdate  开始日期
     * @param enddate 结束日期
     * @return
     */
    public static final String getTwoDateDiff(String stdate, String enddate) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            Date date = myFormatter.parse(stdate);
            Date mydate = myFormatter.parse(enddate);
            day = (mydate.getTime() - date.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 获得两个时间的差值，返回的单位以秒计算,09-12-15
     */
    public static long getDifference(String starttime, String endtime) {
        long time = 0;
        Date startdate = null;
        Date enddate = null;
        try {
            startdate = DateUtils.parseDateTime(starttime);
            enddate = DateUtils.parseDateTime(endtime);
            /*
             * Calendar c1 = Calendar.getInstance(); c1.setTime(startdate);
             * Calendar c2 = Calendar.getInstance(); c2.setTime(enddate); long e
             * = c2.getTimeInMillis(); long s = c1.getTimeInMillis();
             * time=((e-s)/1000<0)?-1:(e-s)/1000;
             */
            long e = startdate.getTime();
            long s = enddate.getTime();
            time = Math.abs(e - s) / 1000;
        } catch (Exception e) {
            return -1;
        }
        return time;
    }

    public static final Date parseDate(String dateStr) {
        if ((dateStr == null) || (dateStr.length() == 0))
            return null;
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final Date parseDateTime(String dateStr) {
        if ((dateStr == null) || (dateStr.length() == 0))
            return null;
        try {
            return dateTimeFormat.parse(dateStr);
        } catch (ParseException e) {
            // e.printStackTrace();
        }
        return null;
    }

    //hh:mm
    public static final Date parseDateTime2(String dateStr) {
        if ((dateStr == null) || (dateStr.length() == 0))
            return null;
        try {
            return dateTimeFormat2.parse(dateStr);
        } catch (ParseException e) {
            // e.printStackTrace();
        }
        return null;
    }

    public static final Date parseDateTime(String dateStr, String format) {
        if ((dateStr == null) || (dateStr.length() == 0))
            return null;
        try {
            return new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatTime(Date date) {
        if (date == null)
            return "";
        return timeFormat.format(date);
    }

    /**
     * 格式化为HH:MM格式（24时制）
     *
     * @param
     * @param date
     * @return
     */
    public static String formatDateR(Date date) {
        if (date == null)
            return "";

        return String.format("%tR", date);
    }

    public static String formatDate(Date date) {
        if (date == null)
            return "";
        return dateFormat.format(date);
    }

    public static String formatDateTime(Date date) {
        if (date == null)
            return "";
        return dateTimeFormat.format(date);
    }

    public static DateFormat getTimeFormat() {
        return timeFormat;
    }

    public static void setTimeFormat(DateFormat timeFormat) {
        DateUtils.timeFormat = timeFormat;
    }


    /**
     * add by guoyanqiu
     *
     * @param nowTime
     * @return
     */
    public static String getTodayDateTime(String nowTime) {
        String todayDate = getNowDate();
        if ((!"".equals(todayDate)) && (null != todayDate)) {
            String todayDateTime = todayDate + " " + nowTime;
            return todayDateTime;
        }
        return nowTime;

    }

    public static long getTimestamp(String nowTime) {
        long timestamp = 0;
        try {
            Date d = dateTimeFormat.parse(nowTime);
            Log.v("timestamp", d + "");
            timestamp = d.getTime() / 1000;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return timestamp;

    }

    public static final String getTimeForMothAndDay1(int weekId) {
        Date nowDate = new Date();
        Date targetDate = DateUtils.addDate(nowDate, 0, 0, weekId);
        String date = dateTimeFormat2.format(targetDate);
        return date;
    }

    /**
     * 时间戳转换为时分秒
     *
     * @param time
     * @return
     */
    public static String ShowTime(int time) {
        time /= 1000;
        int minute = time / 60;
        int hour = minute / 60;
        int second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d:%02d", hour, minute, second);

    }

    /**
     * 比较两个是时间的差 ，当starttimed大于endtime是值为整数  否则负数
     *
     * @param
     * @return
     */
    public static long getDiff(String starttime, String endtime) {
        long time = 0;
        Date startdate = null;
        Date enddate = null;
        try {
            startdate = DateUtils.parseDateTime(starttime);
            enddate = DateUtils.parseDateTime(endtime);
            long e = startdate.getTime();
            long s = enddate.getTime();
            time = (e - s) / 1000;
        } catch (Exception e) {
            return -1;
        }
        return time;
    }

    /**
     * 将时间格式为 yyyy-MM-dd HH:mm:ss 转换成 MM-dd HH:mm
     *
     * @param
     * @return
     * @throws ParseException
     */
    public static String getUserDate(String sformat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(sformat);

        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
        formatter.applyPattern("MM-dd HH:mm");
        return formatter.format(date);

    }

    /**
     * 将时间格式为 yyyy-MM-dd HH:mm:ss 转换成 MM-dd HH:mm
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static String getUserDate_lb(String sformat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = sdf.parse(sformat);

        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
        formatter.applyPattern("MM-dd HH:mm");
        return formatter.format(date);

    }

    /**
     * 将时间格式为 yyyy-MM-dd HH:mm:ss 转换成 HH:mm
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static String getUserDate_hm(String sformat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(sformat);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        formatter.applyPattern("HH:mm");
        return formatter.format(date);

    }

    public static String getDateFormat(String dateTime) throws ParseException {
        Date date = dateTimeFormat.parse(dateTime);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);

    }


    public static String getTimeFormat(String sformat) throws ParseException {
        Date date = dateTimeFormat.parse(sformat);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(date);
    }

    public static String getDesignatedDate(int index) {
        Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
        calendar.add(Calendar.DATE, index);    //得到前一天
        String yestedayDate = new SimpleDateFormat("MM-dd").format(calendar.getTime());
        return yestedayDate;
    }

    public static String getDesignatedDates(int index) {
        Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
        calendar.add(Calendar.DATE, index);    //得到前一天
        String yestedayDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        return yestedayDate;
    }


    public static String getVipDate(String date) {


        String pat1 = "yyyy-MM-dd HH:mm:ss";
        String pat2 = "yyyy-MM-dd";
        SimpleDateFormat sdf1 = new SimpleDateFormat(pat1);
        SimpleDateFormat sdf2 = new SimpleDateFormat(pat2);
        Date d = null;
        try {
            d = sdf1.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdf2.format(d);
    }

    ;
}
