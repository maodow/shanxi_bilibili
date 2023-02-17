package tv.huan.bilibili.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 支付回调
 */
public class BroadcastReceiverForPay extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String msg=intent.getAction().toString();
        LogUtil.log("BroadcastReceiverForPaymsg="+msg);
        if (Constants.PAY_BROADCAST_ACTION.equals(intent.getAction())) {
            //状态-1=失败,0=成功
            String state = intent.getStringExtra("state");
            LogUtil.log("pay state==> " + state);



//            ToastUtil.show(HuanApplication.getInstance(), "订购成功，进入订购状态查询阶段");
            //同步订单代做
        }
    }
}
