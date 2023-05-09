package tv.huan.bilibili.http;

import androidx.annotation.NonNull;
import lib.kalu.frame.mvp.interceptor.OkhttpInterceptorStandard;
import okhttp3.Connection;
import okhttp3.HttpUrl;
import okhttp3.Request;
import tv.huan.bilibili.utils.BoxUtil;
import tv.huan.bilibili.utils.DevicesUtils;
import tv.huan.bilibili.utils.LogUtil;
import tv.huan.heilongjiang.BuildConfig;

public final class HttpInterceptor extends OkhttpInterceptorStandard {

    @Override
    public Request analysisRequest(@NonNull long requestTime, @NonNull Connection connection, @NonNull Request request) {

        try {
            boolean playUrlReq = request.url().toString().contains(BuildConfig.EPG_PATH);
            if (!playUrlReq){
                throw new Exception("not request playUrl");
            }
            String userToken = DevicesUtils.INSTANCE.getToken();
            LogUtil.log("HttpInterceptor => AuthToken: "+userToken);
            if (userToken.length() <= 0) {
                userToken = "";
            }
            Request req = request.newBuilder()
                    .addHeader("Authorization", userToken)
                    .build();
            return super.analysisRequest(requestTime, connection, req);
        } catch (Exception e) {
            HttpUrl build = request.url().newBuilder()
                    .addQueryParameter("userId", BoxUtil.getUserId())
                    .build();
            Request req = request.newBuilder().url(build).build();
            return super.analysisRequest(requestTime, connection, req);
        }
    }
}
