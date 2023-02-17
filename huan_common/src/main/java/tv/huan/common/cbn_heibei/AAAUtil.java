package tv.huan.common.cbn_heibei;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import tv.huan.common.util.CaUtil;
import tv.huan.common.util.CommonUtil;
import tv.huan.common.util.SystemPropertiesUtil;

public final class AAAUtil {

    private static String ACTION_RESULT = "com.starcor.aaa.app.authorities.pay.result";
    private static String ACTION_PAY = "com.starcor.aaa.app.authorities.pay";
    private static String ACTION_MEDIA_PLAYER = "com.guoantvbox.VodMediaPlayer";

    /**********************/

    public static void openPlayer(@NonNull Context context, String title, String cid, String url) {
        openPlayer(context, 0, -1, title, cid, url);
    }

    public static void openPlayer(@NonNull Context context, long position, String title, String cid, String url) {
        openPlayer(context, position, -1, title, cid, url);
    }

    public static void openPlayer(@NonNull Context context, long position, long playTime, String title, String cid, String url) {

        CommonUtil.log("openPlayer => position = " + position);
        CommonUtil.log("openPlayer => playTime = " + playTime);
        CommonUtil.log("openPlayer => title = " + title);
        CommonUtil.log("openPlayer => cid = " + cid);
        CommonUtil.log("openPlayer => url = " + url);

        Intent intent = new Intent(ACTION_MEDIA_PLAYER);
        intent.putExtra("RequestUrl", url);
        intent.putExtra("IntentType", "tv.huan.hb.back.vod");
        if (position < 0) {
            intent.putExtra("StartTime", 0L);//先改成这个多少级
        } else {
            intent.putExtra("StartTime", position);//先改成这个多少级
        }
        intent.putExtra("EndTime", Long.MAX_VALUE);//回看节目结束时间
        intent.putExtra("ProgramId", cid);//节目ID   5da98e5c35db5e2d5d8b456c
        intent.putExtra("EventName", title);//节目名称
        if (playTime > 0) {
            intent.putExtra("PlayTime", playTime);
        }
//        intent.putExtra("pic", "http://hkdbh.hbtn.com:8080/thumb/216/320/1574235863177.jpg");

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(intent);
    }

    /**********************/

    public static void openPayZQ(@NonNull Context context,
                                 @NonNull String code,
                                 @NonNull OnPayChangeListener listener) {

        if (null != listener) {
            mBroadcast = new PayBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ACTION_RESULT);
            context.registerReceiver(mBroadcast, filter);
        }

        Intent intent = new Intent();
        intent.setAction(ACTION_PAY);//指定的获取支付的action
        intent.putExtra("session_id", UUID.randomUUID().toString());//请求前自行生成的UUID
        intent.putExtra("cp_id", "HSTV");//统一分配的CPID
        String packageName = context.getPackageName();
        intent.putExtra("package_name", packageName);//第三方CP应用包名
//            intent.putExtra("products", Constants.PRODUCT_ID);//产品包列表
        intent.putExtra("products", code);//产品包计费代码
        intent.putExtra("ex_data", "zq");//第三方附加参数
        try {
            Bundle bundle = intent.getExtras();
            for (String key : bundle.keySet()) {
                Object o = bundle.get(key);
                if (null == o)
                    continue;
                CommonUtil.log("openPayZQ => key = " + key + ", value = " + o);
            }
        } catch (Exception e) {
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_INCLUDE_STOPPED_PACKAGES | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.sendBroadcast(intent);
    }

    public static void openPayDD(@NonNull Context context,
                                 @NonNull int prodId,
                                 @NonNull int productType,
                                 @NonNull int payStatus,
                                 @NonNull int type,
                                 @NonNull String vid,
                                 @NonNull String cid,
                                 @NonNull String cid_name,
                                 @NonNull String product_id,
                                 @NonNull String product_name,
                                 @NonNull String order_price,
                                 @NonNull String ValidTerm,
                                 @NonNull String notify_url,
                                 @NonNull OnPayChangeListener listener) {

        if (null != listener) {
            mBroadcast = new PayBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ACTION_RESULT);
            context.registerReceiver(mBroadcast, filter);
        }

        Intent intent = new Intent();
        intent.setAction(ACTION_PAY);//指定的获取支付的action
        intent.putExtra("cp_id", "HSTV");//固定值

        intent.putExtra("nns_video_id", cid);//影片ID
        intent.putExtra("cp_video_id", cid);//目前影片的CID 专区传专区ID

        intent.putExtra("cp_name", cid_name);//影片名称
        intent.putExtra("cp_video_name", cid_name);//影片名称

//            intent.putExtra("product_id", "37291");//固定值
//            intent.putExtra("product_id", Constants.PRODUCT_SIN_ID);//固定值
        intent.putExtra("product_id", product_id);//计费代码
        intent.putExtra("product_name", product_name);//产品名字

        intent.putExtra("is_charge", "true");//传"true"即可

//        String price = String.valueOf((Float.parseFloat(detail.getPrice()) / 100));
        intent.putExtra("order_price", order_price);//精确到0.01，且必须为正确的金额0

        intent.putExtra("notify_url", notify_url);//回调地址
//            intent.putExtra("notify_url", "http://172.30.147.73:81/mediaApi/data/orderSync");//回调地址
//            intent.putExtra("cp_order_id", orderID + ";" + productid + ";" + ValidTerm + ";" + productName + ";" + (productType == 2 ? PayType : cp_video_id) + ";" + productType);//透传

        String ca = CaUtil.getCa();
        String cp_pay_id = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ca;
        intent.putExtra("cp_pay_id", cp_pay_id);

        // 透传参数
        StringBuilder cp_order_id = new StringBuilder();
        cp_order_id.append(cp_pay_id);
        cp_order_id.append(";");
        cp_order_id.append(product_id);
        cp_order_id.append(";");
        cp_order_id.append(ValidTerm);
        cp_order_id.append(";");
        cp_order_id.append(product_name);
        cp_order_id.append(";");
        if (productType == 2) {
            cp_order_id.append(payStatus);
        } else {
            cp_order_id.append(cid);
        }
        cp_order_id.append(";");
        cp_order_id.append(type);
        cp_order_id.append(";");
        try {
            cp_order_id.append("{\"soft_version\":\"");
            String sw_ver = SystemPropertiesUtil.getProperty("persist.sys.hwconfig.sw_ver", null);
            cp_order_id.append(sw_ver);
            cp_order_id.append("\",\"card_id\":\"");
            cp_order_id.append(ca);
            cp_order_id.append("\"}");
        } catch (Exception e) {
        }
        cp_order_id.append(";");
        cp_order_id.append(prodId);
        intent.putExtra("cp_order_id", cp_order_id.toString());//透传
        intent.putExtra("ex_data", vid);//透传

        try {
            Bundle bundle = intent.getExtras();
            for (String key : bundle.keySet()) {
                Object o = bundle.get(key);
                if (null == o)
                    continue;
                CommonUtil.log("openPayDD => key = " + key + ", value = " + o);
            }
        } catch (Exception e) {
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_INCLUDE_STOPPED_PACKAGES | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.sendBroadcast(intent);
    }

    private static OnPayChangeListener mListener = null;

    public void setOnPayChangeListener(@NonNull OnPayChangeListener listener) {
        mListener = listener;
    }

    public interface OnPayChangeListener {

        void onSucc(boolean zq);

        void onFail();
    }

    private static PayBroadcastReceiver mBroadcast = null;

    final static class PayBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (null != mBroadcast) {
                try {
                    context.unregisterReceiver(mBroadcast);
                } catch (Exception e) {
                }
            }

            try {
                Bundle bundle = intent.getExtras();
                for (String key : bundle.keySet()) {
                    Object o = bundle.get(key);
                    if (null == o)
                        continue;
                    CommonUtil.log("PayBroadcastReceiver => key = " + key + ", value = " + o);
                }
            } catch (Exception e) {
            }

            if (ACTION_RESULT.equals(intent.getAction())) {
                //状态-1=失败,0=成功
                String state = intent.getStringExtra("state");
                if (state.equals("0")) {
                    // 上报
                    try {
                        String ex_data = intent.getStringExtra("ex_data");
                        // 周期
                        if ("zq".equals(ex_data)) {
                            if (null != mListener) {
                                mListener.onSucc(true);
                            }
                        }
                        // 单点
                        else {
                            if (null != mListener) {
                                mListener.onSucc(false);
                            }
                        }
                    } catch (Exception e) {
                    }
                } else {
                    // 订购失败
                    if (null != mListener) {
                        mListener.onFail();
                    }
                }
            }
        }
    }
}
