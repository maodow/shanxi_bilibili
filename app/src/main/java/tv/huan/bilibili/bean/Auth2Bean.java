package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

@Keep
public final class Auth2Bean extends BaseBean<Auth2Bean> implements Serializable {

    /**
     * {"userId":"00380035890","vip":"0","whiteListVip":"0","specialList":[],"vipSpecialList":[],"singleAuth":"0"}
     */

    private List<VipBean> vipSpecialList;
    private boolean free;
    private String vip;
    private String whiteListVip;
    private String singleAuth;

    @Override
    public Auth2Bean getData() {
        return this;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public List<VipBean> getVipSpecialList() {
        return vipSpecialList;
    }

    public void setVipSpecialList(List<VipBean> vipSpecialList) {
        this.vipSpecialList = vipSpecialList;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getWhiteListVip() {
        return whiteListVip;
    }

    public void setWhiteListVip(String whiteListVip) {
        this.whiteListVip = whiteListVip;
    }

    public String getSingleAuth() {
        return singleAuth;
    }

    public void setSingleAuth(String singleAuth) {
        this.singleAuth = singleAuth;
    }

    @Keep
    public static class VipBean implements Serializable {
        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
