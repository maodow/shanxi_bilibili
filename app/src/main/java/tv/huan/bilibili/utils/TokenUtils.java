package tv.huan.bilibili.utils;

import android.util.Log;

import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by zhangxuefeng on 2018/5/16.
 */

public class TokenUtils {

    public static Method getMethod;

    private static final String TAG = TokenUtils.class.getSimpleName() + "--ShanYuHai--";


    public static String get(final String key, final String def) {
        try {
            if (getMethod == null) {
                getMethod = Class.forName("android.os.SystemProperties").getMethod("get", String.class, String.class);
            }

            return (String) getMethod.invoke(null, key, def);
        } catch (Exception e) {
            Log.e(TAG, "Platform error: " + e.toString());
            return def;
        }
    }

    public static String getResutl() {
        //构造需要加密的字段到token实体中
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setUserId(get("STBSN", null));
        //构造加密器
        TokenGenerator tokenGenerator_1 = new TokenGenerator("i am ottKey", 60);

        Log.e(TAG, "getLocalIpAddress==" + getLocalIpAddress());

        //加密
        String result_1 = tokenGenerator_1.generateToken(getLocalIpAddress(), null, tokenInfo);
        return result_1;

    }

    public static String getLocalIpAddress() {

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
//            Log.e(TAG, ex.toString());
            ex.printStackTrace();
        }
        return null;
    }

}
