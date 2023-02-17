package tv.huan.bilibili.http;

import androidx.annotation.NonNull;

import lib.kalu.frame.mvp.interceptor.OkhttpInterceptorStandard;
import okhttp3.Connection;
import okhttp3.HttpUrl;
import okhttp3.Request;
import tv.huan.bilibili.utils.BoxUtil;

public final class HttpInterceptor extends OkhttpInterceptorStandard {

    @Override
    public Request analysisRequest(@NonNull long requestTime, @NonNull Connection connection, @NonNull Request request) {
        HttpUrl.Builder builder = request.url().newBuilder()
                .addQueryParameter("prodId", String.valueOf(BoxUtil.getProdId()))
                .addQueryParameter("userId", BoxUtil.getCa())
                .addQueryParameter("huanId", BoxUtil.getCa());
        Request req = request.newBuilder().url(builder.toString()).build();
        return super.analysisRequest(requestTime, connection, req);
    }
}
