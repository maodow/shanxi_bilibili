package tv.huan.bilibili.http;

import androidx.annotation.NonNull;

import lib.kalu.frame.mvp.context.FrameContext;
import lib.kalu.frame.mvp.interceptor.OkhttpInterceptorStandard;
import okhttp3.Connection;
import okhttp3.HttpUrl;
import okhttp3.Request;
import tv.huan.bilibili.utils.BoxUtil;
import tv.huan.heilongjiang.BuildConfig;
import tv.huan.heilongjiang.HuaweiApi;

public final class HttpInterceptor extends OkhttpInterceptorStandard {

    @Override
    public Request analysisRequest(@NonNull long requestTime, @NonNull Connection connection, @NonNull Request request) {

        HttpUrl.Builder builder = request.url().newBuilder();

        try {
            boolean endsWith = request.url().toString().contains(BuildConfig.EPG_PATH);
            if (endsWith)
                throw new Exception("huawei auth request");
            builder.addQueryParameter("prodId", String.valueOf(BoxUtil.getProdId()));
            builder.addQueryParameter("phoneNumber", BoxUtil.getUserId());
            builder.addQueryParameter("uid", BoxUtil.getUserId());
            builder.addQueryParameter("userId", BoxUtil.getUserId());
            builder.addQueryParameter("huanId", BoxUtil.getUserId());
            builder.addQueryParameter("productId", String.valueOf(1));
        } catch (Exception e) {
        }

        String xAuth = request.header("xAuth");
        if ("1".equals(xAuth)) {
            String userToken = HuaweiApi.getUserToken(FrameContext.getApplicationContext());
            if (null == userToken || userToken.length() <= 0) {
                userToken = "null";
            }
            Request req = request.newBuilder()
                    .removeHeader("xAuth")
                    .addHeader("Authorization", userToken)
                    .url(builder.toString())
                    .build();
            return super.analysisRequest(requestTime, connection, req);
        } else {
            Request req = request.newBuilder().url(builder.toString()).build();
            return super.analysisRequest(requestTime, connection, req);
        }
    }
}
