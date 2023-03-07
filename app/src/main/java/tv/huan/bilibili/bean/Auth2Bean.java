package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public final class Auth2Bean extends BaseBean<Auth2Bean> implements Serializable {

    private String whiteListVip; // 白名单"1".equals(whiteListVip)

    public boolean isWhiteUser() {
        return "1".equals(whiteListVip);
    }
}