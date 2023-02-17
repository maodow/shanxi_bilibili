package tv.huan.heilongjiang;

import android.content.Context;

import com.istv.ystframework.callback.ResultCallBack;
import com.istv.ystframework.client.OrderClient;

import tv.huan.common.cmcc.CmccUtil;
import tv.huan.common.huawei.HuaweiUtil;

public final class HeilongjiangApi {

    public static void init(Context context) {
        OrderClient.init(context, BuildConfig.APP_ID, BuildConfig.APP_KEY, null);
    }

    public static void auth(Context context, String productid, String contentName) {
        String mac = CmccUtil.getMac();
        String userName = HuaweiUtil.getUserName(context);
        OrderClient.goOrder_V3(context, BuildConfig.APP_ID, BuildConfig.APP_KEY, productid, contentName, mac, userName, new ResultCallBack() {
            @Override
            public void onResult(String s) {
                //支付结果
            }
        });
    }
}
