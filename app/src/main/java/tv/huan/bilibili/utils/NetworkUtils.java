package tv.huan.bilibili.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import tv.huan.bilibili.HuanApp;


/**
 * @author：tqzhang  on 18/7/23 11:33
 */
public class NetworkUtils {

    /**
     * 网络是否可用
     *
     * @return
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager mgr = (ConnectivityManager) HuanApp.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
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

}
