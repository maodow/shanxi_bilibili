package tv.huan.heilongjiang;

import android.content.Context;
import android.util.Log;

import com.istv.ystframework.callback.ResultCallBack;
import com.istv.ystframework.client.OrderClient;

import tv.huan.common.cmcc.CmccUtil;
import tv.huan.common.huawei.HuaweiUtil;

public final class HeilongjiangApi {

    public static boolean init(Context context, String appId, String appKey) {
        try {
            Log.e("HeilongjiangApi", "init =>");
            OrderClient.init(context, appId, appKey, new ResultCallBack() {
                @Override
                public void onResult(String s) {
                    Log.e("HeilongjiangApi", "init => s = " + s);
                }
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getUserId(Context context,
                                   String appid,
                                   String appkey) {
        try {
            return OrderClient.getUserId(context, appid, appkey);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getPhoneNo(Context context) {
        try {
            return OrderClient.getPhoneNo(context);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 判断用户是否购买过内容对应的产品包，未订购的场合返回该内容所关联的产品包列表。
     *
     * @param context
     * @param appid
     * @param appkey
     * @param contentId
     * @param productId
     */
    public static void isVip(Context context,
                             String appid,
                             String appkey,
                             String contentId,
                             String productId) {
        String userId = getUserId(context, appid, appkey);
        Log.e("HeilongjiangApi", "isVip => userId = " + userId);
        OrderClient.authOrize_V3(context, appid, appkey, userId, contentId, productId, "1", new ResultCallBack() {
            @Override
            public void onResult(String s) {
                Log.e("HeilongjiangApi", "isVip => s = " + s);
            }
        });
    }

    /**
     * 跳转SDK中原生支付页面，支付结果在callback里返回（建议支付完成后，最好重新走鉴权逻辑，看是否订购成功）
     *
     * @param context
     * @param appid
     * @param appkey
     * @param productId
     */
    public static void jumpVip(Context context,
                               String appid,
                               String appkey,
                               String productId) {
        String phoneNo = getPhoneNo(context);
        String userId = getUserId(context, appid, appkey);
        Log.e("HeilongjiangApi", "jumpVip => phoneNo = " + phoneNo + ", userId = " + userId);
        OrderClient.goOrder_V3(context, appid, appkey, productId, "测试Title", userId, phoneNo, new ResultCallBack() {
            @Override
            public void onResult(String s) {
                Log.e("HeilongjiangApi", "jumpVip => s = " + s);
            }
        });
    }
}
