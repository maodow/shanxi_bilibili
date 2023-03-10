package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class HuanInfo implements Serializable {

    private String vtoken ;
    private String huanId ;

    public String getVtoken() {
        return vtoken;
    }

    public void setVtoken(String vtoken) {
        this.vtoken = vtoken;
    }

    public String getHuanId() {
        return huanId;
    }

    public void setHuanId(String huanId) {
        this.huanId = huanId;
    }
}
