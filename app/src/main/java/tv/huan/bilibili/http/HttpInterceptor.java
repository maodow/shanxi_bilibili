package tv.huan.bilibili.http;

import androidx.annotation.NonNull;

import lib.kalu.frame.mvp.context.FrameContext;
import lib.kalu.frame.mvp.interceptor.OkhttpInterceptorStandard;
import okhttp3.Connection;
import okhttp3.HttpUrl;
import okhttp3.Request;
import tv.huan.bilibili.utils.BoxUtil;
import tv.huan.heilongjiang.BuildConfig;
import tv.huan.heilongjiang.HeilongjiangUtil;

public final class HttpInterceptor extends OkhttpInterceptorStandard {

    @Override
    public Request analysisRequest(@NonNull long requestTime, @NonNull Connection connection, @NonNull Request request) {

        try {
            boolean endsWith = request.url().toString().contains(BuildConfig.EPG_PATH);
            // huawei
            if (!endsWith)
                throw new Exception("not huawei");
            String userToken = HeilongjiangUtil.getUserToken(FrameContext.getApplicationContext());
            if (null == userToken || userToken.length() <= 0) {
                userToken = "";
            }
            Request req = request.newBuilder()
                    .addHeader("Authorization", userToken)
                    .build();
            return super.analysisRequest(requestTime, connection, req);
        } catch (Exception e) {
            HttpUrl build = request.url()
                    .newBuilder()
                    .addQueryParameter("prodId", String.valueOf(BoxUtil.getProdId()))
                    .addQueryParameter("businessId", String.valueOf(BoxUtil.getProdId()))
                    .addQueryParameter("phoneNumber", BoxUtil.getUserId())
                    .addQueryParameter("uid", BoxUtil.getUserId())
                    .addQueryParameter("userId", BoxUtil.getUserId())
                    .addQueryParameter("huanId", BoxUtil.getUserId())
                    .addQueryParameter("productId", String.valueOf(1)).build();
            Request req = request.newBuilder().url(build).build();
            return super.analysisRequest(requestTime, connection, req);
        }
    }
}
