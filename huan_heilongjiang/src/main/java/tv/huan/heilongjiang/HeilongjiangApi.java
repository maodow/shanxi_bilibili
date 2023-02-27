package tv.huan.heilongjiang;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.istv.ystframework.callback.ResultCallBack;
import com.istv.ystframework.client.OrderClient;

import org.json.JSONException;
import org.json.JSONObject;

public final class HeilongjiangApi {

    public static boolean init(Context context) {
        return init(context, BuildConfig.APP_ID, BuildConfig.APP_KEY);
    }

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

    public static String getUserId(Context context) {
        return getUserId(context, BuildConfig.APP_ID, BuildConfig.APP_KEY);
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

    public static void checkVip(Context context,
                                OnCheckVipChangeListener l) {
        checkVip(context, BuildConfig.APP_ID, BuildConfig.APP_KEY, BuildConfig.PRODUCT_ID, l);
    }

    /**
     * 判断用户是否购买过内容对应的产品包，未订购的场合返回该内容所关联的产品包列表。
     *
     * @param context
     * @param appid
     * @param appkey
     * @param productId
     */
    public static void checkVip(Context context,
                                String appid,
                                String appkey,
                                String productId,
                                OnCheckVipChangeListener l) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String userId = getUserId(context, appid, appkey);
                Log.e("HeilongjiangApi", "checkVip => appid = " + appid);
                Log.e("HeilongjiangApi", "checkVip => appkey = " + appkey);
                Log.e("HeilongjiangApi", "checkVip => productId = " + productId);
                Log.e("HeilongjiangApi", "checkVip => userId = " + userId);

                OrderClient.authOrize_V3(context, appid, appkey, userId, "", productId, "1", new ResultCallBack() {
                    @Override
                    public void onResult(String s) {
                        Log.e("HeilongjiangApi", "isVip => s = " + s);
                        if (null != l) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    if (null != s && s.toLowerCase().contains("\"result\":\"ord-000\"")) {
                                        l.onPass();
                                    } else {
                                        l.onFail();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }).start();
    }

    public static void jumpVip(Context context) {
        jumpVip(context, BuildConfig.APP_ID, BuildConfig.APP_KEY, BuildConfig.PRODUCT_ID);
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
        String userId = getUserId(context, appid, appkey);
        Log.e("HeilongjiangApi", "jumpVip => appid = " + appid);
        Log.e("HeilongjiangApi", "jumpVip => appkey = " + appkey);
        Log.e("HeilongjiangApi", "jumpVip => productId = " + productId);
        Log.e("HeilongjiangApi", "jumpVip => userId = " + userId);

        OrderClient.goOrder_V3(context, appid, appkey, productId, "1", "测试Title", userId, null);
//        OrderClient.goOrder_V3(context, appid, appkey, productId, "1", "测试Title", userId, new ResultCallBack() {
//            @Override
//            public void onResult(String s) {
//                Log.e("HeilongjiangApi", "jumpVip => s = " + s);
//            }
//        });
    }
}
