package tv.huan.bilibili.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextPaint;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import tv.huan.bilibili.HuanApp;


public class Tools {
    public static final int nextUpdateTimeDefault = 1000 * 60;
    private static long lastClickTime;
    //	public static final String FullWidthChars= "１２３４６５７８９０ 	ＱＡＺＷＳＸＥＤＣＲＦＶＴＧＢＹＨＮＵＪＭＩＫＯＬＰｑａｚｗｓｘｅｄｃｒｆｖｔｇｂｙｈｎｕｊｍｋｉｌｏ";

    public static String getBIDate(Date date) {
        DateFormat dFormat = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
        return dFormat.format(date);
    }

    //字体加粗
    public static void changeThick(TextView mTextView) {
        TextPaint tp = mTextView.getPaint();
        tp.setFakeBoldText(true);
    }

    public static String getDateTime(String date, String time) {
        if (date == null || time == null) {
            return null;
        }
        return date + " " + time + ":00";
    }

    public static Date getDateByString(String date) {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date ndate = null;
        try {
            ndate = sFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ndate;
    }

    /**
     * 0 = Sunday, 1 = Monday, 2 = Tuesday, 3 = Wednesday, 4 = Thursday, 5 = Friday, 6 = Saturday
     *
     * @param date 格式yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static int getWeek(String date) {
        int week = 0;
        if (date == null || "".equals(date))
            return week;
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date ndate = sFormat.parse(date);
            week = ndate.getDay();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return week;
    }

    /**
     * 根据 yyyy-MM-dd HH:mm:ss 格式的时间获取 HH:mm 的时间格式
     *
     * @param date yyyy-MM-dd HH:mm:ss
     * @return
     */

    public static String time_HH_MM_byData(String date) {
        String time = "";
        SimpleDateFormat sFormat0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sFormat = new SimpleDateFormat("HH:mm");
        try {
            Date ndate = sFormat0.parse(date);
            time = sFormat.format(ndate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * true 表示网络正常
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = mgr.getAllNetworkInfo();
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getNextUpdateDelay(String nextTime) {
        int time = -1;
        if (nextTime == null || "".equals(nextTime))
            return nextUpdateTimeDefault;

        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date ndate = sFormat.parse(nextTime);
            Date nowDate = new Date();
            time = (int) (ndate.getTime() - nowDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (time <= nextUpdateTimeDefault) {
            time = nextUpdateTimeDefault;
        }
        //		if(Debug.TEST) {
        //			time = 1000 * 60000;
        //		}
        return time;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] buffer = baos.toByteArray();
        baos.reset();
        return buffer;
    }

    public static Drawable loadDrawableByUrl(String url) {
        Drawable drawable = null;
        if (url == null) {
            return drawable;
        }
        try {
            URL m = new URL(url);
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File f = new File(url);
                if (f.exists()) {
                    drawable = Drawable.createFromPath(url);
                    return drawable;
                }
				/*InputStream i = (InputStream) m.getContent();
				drawable = Drawable.createFromStream(i, "src");
				i.close();
				return drawable;*/
                Bitmap bitmap = loadBitmapFromNet(url);
                drawable = new BitmapDrawable(bitmap);
                //Log.e("000========================", "url="+url);
                //Debug.log("000========================", "drawable="+drawable);
                return drawable;
            } else {
				/*InputStream i = (InputStream) m.getContent();
				drawable = Drawable.createFromStream(i, "src");
				Debug.log(TAG, "size="+i.available()+" url = "+url+" drawable="+drawable);
				i.close();*/
                Bitmap bitmap = loadBitmapFromNet(url);
                drawable = new BitmapDrawable(HuanApp.getContext().getResources(), bitmap);
                //Log.e("111========================", "url="+url);
                //Debug.log("111========================", "drawable="+drawable);
                return drawable;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Bitmap loadBitmapFromNet(String url) {
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            URL m = new URL(url);
            inputStream = (InputStream) m.getContent();

            int len = inputStream.available();

            //Debug.log("loadBitmapFromNet", "--------------- url="+url);
            byte[] data = new byte[1024 * 15];
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            int size = 0;
            while ((len = inputStream.read(data)) != -1) {
                bStream.write(data, 0, len);
                size += len;
                //Debug.log(TAG, "=========== len = "+len+" size="+size);
            }
            data = bStream.toByteArray();
            bStream.reset();
            String fileName = m.getFile();
            int index = fileName.lastIndexOf("/");
            fileName = fileName.substring(index + 1);

            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            //Debug.log("loadBitmap", " bitmap="+bitmap);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static Bitmap loadImage(String url) {
        Bitmap bitmap = null;
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File f = new File(url);
                if (f.exists()) {
                    bitmap = BitmapFactory.decodeFile(url);
                    return bitmap;
                }
				/*URL m = new URL(url);
				InputStream i = (InputStream) m.getContent();
				bitmap = BitmapFactory.decodeStream(i);
				i.close();
				return bitmap;*/
                return loadBitmapFromNet(url);
            } else {
				/*URL m = new URL(url);
				InputStream i = (InputStream) m.getContent();
				bitmap = BitmapFactory.decodeStream(i);
				Debug.log("loadImage", "size="+i.available()+"=================== bitmap="+bitmap);
				i.close();
				return bitmap;*/
                return loadBitmapFromNet(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static int matrixTime(String time) {
        String[] str = time.split(":");
        if (str[0].indexOf(" ") != -1) {
            str[0] = str[0].substring(str[0].indexOf(" ") + 1);
        }
        int hour = Integer.parseInt(str[0]);
        int minute = Integer.parseInt(str[1]);
        return (hour * 60 + minute);
    }

    public static String getNextTen() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        Format dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        date.setMinutes(date.getMinutes() + 5);
        //		Log.i(Const.TAG, "----- in getNextTen date = "+date.toString());
        return dateFormat.format(date);
    }

    public static String getNow() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();

        cal.setTime(date);
        Format dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        return dateFormat.format(new Date());
    }

    public static String getNowDate() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        Format dateFormat = new SimpleDateFormat("yyyy-MM-dd ");

        return dateFormat.format(cal.getTime());
    }

    /**
     * time 格式为：hh:mm
     *
     * @param time
     * @return
     */
    public static String time_HH_MM(String time) {
        if (time == null) {
            return "00:00";
        }

        String[] str = time.split(":");
        if (str[0].indexOf(" ") != -1) {
            str[0] = str[0].substring(str[0].indexOf(" ") + 1);
        }
        //int hour = Integer.parseInt(str[0]);
        //int minute = Integer.parseInt(str[1]);
        return str[0] + ":" + str[1];
    }

    public static int[] getCurrentProgress(String startTime, String endTime) {

        int[] len = new int[2];

        int start, end, now;

        Date date = new Date();
        now = date.getHours() * 60 + date.getMinutes();
        start = Tools.matrixTime(startTime);
        end = Tools.matrixTime(endTime);
        len[0] = end - start;//Max
        len[1] = now - start;//progress
        return len;
    }

    public static File getFileFromPath(String filePath) {
        File file = null;

        file = new File(filePath);
        //没有文件时，返回null
        if (!file.exists()) {
            return null;
        }

        return file;
    }

    public static String getStringFromFile(File file) {

        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
            }
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        return null;
    }

    static int voice = 0;

    /**
     * 恢复声音
     *
     * @param context
     */
    public static void resetVoice(Context context) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_SYSTEM, voice, 0);
    }

    /**
     * 设置静音
     *
     * @param context
     */
    public static void setVoiceMute(Context context) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        voice = am.getStreamVolume(AudioManager.STREAM_SYSTEM);
        am.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 0);
    }

    public static String fullCharTOHaltChar(String fullChars) {
        String haltChars = "";
        char[] str = fullChars.toCharArray();
        for (int i = 0; i < str.length; i++) {
            int code = str[i];// 获取当前字符的unicode编码
            if (code >= 65281 && code <= 65373) {// 在这个unicode编码范围中的是所有的英文字母以及各种字符
                haltChars += (char) (str[i] - 65248);// 把全角字符的unicode编码转换为对应半角字符的unicode码
            } else if (code == 12288) {// 空格
                haltChars += (char) (str[i] - 12288 + 32);
            } else if (code == 65377) {
                haltChars += (char) (12290);
            } else if (code == 12539) {
                haltChars += (char) (183);
            } else if (code == 8226) { // 特殊字符 ‘·’的转化
                haltChars += (char) (183);
            } else {
                haltChars += str[i];
            }
        }

        return haltChars;
    }

    public static boolean isEmpty(String s) {
        if (null == s)
            return true;
        if (s.length() == 0)
            return true;
        return s.trim().length() == 0;
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static String trimAllWhitespace(String str) {
        if (str == null || str.equals("")) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        int index = 0;
        while (sb.length() > index) {
            if (Character.isWhitespace(sb.charAt(index))) {
                sb.deleteCharAt(index);
            } else {
                index++;
            }
        }
        return sb.toString();
    }

    public static boolean isNetworkNotAvailable(Context context) {
        return !isNetworkAvailable(context);
    }
}