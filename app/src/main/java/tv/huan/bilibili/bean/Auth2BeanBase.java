package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;

import tv.huan.bilibili.bean.base.BaseResponsedBean;

@Keep
public final class Auth2BeanBase extends BaseResponsedBean<Auth2BeanBase> implements Serializable {

    private String whiteListVip; // 白名单"1".equals(whiteListVip)

    public boolean isWhiteUser() {
        return "1".equals(whiteListVip);
    }
}