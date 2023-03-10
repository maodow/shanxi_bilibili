package tv.huan.bilibili.utils;

import android.content.Context;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import lib.kalu.frame.mvp.context.FrameContext;
import lib.kalu.frame.mvp.util.CacheUtil;
import tv.huan.bilibili.BuildConfig;
import tv.huan.heilongjiang.HeilongjiangApi;

public final class BoxUtil {

    public static int getProdId() {
        try {
            return 1;
        } catch (Exception e) {
            return 1;
        }
    }

    public static String getUserId() {
        try {
            Context context = FrameContext.getApplicationContext();
            String userId = HeilongjiangApi.getUserId(context);
            if (null == userId || userId.length() <= 0)
                throw new Exception("userId error: null");
            return userId;
        } catch (Exception e) {
            LogUtil.log("BoxUtil => getCa => " + e.getMessage());
            return BuildConfig.HUAN_CHECK_USERID ? "null" : "00380035890";
        }
    }

//    public static String getHuanId() {
//        Context context = FrameContext.getApplicationContext();
//        return CacheUtil.getCache(context, "huanId");
//    }

    public static String getEtherNetIP() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            LogUtil.log("IpAddress", ex.toString());
        }
        return "0.0.0.0";
    }
}
