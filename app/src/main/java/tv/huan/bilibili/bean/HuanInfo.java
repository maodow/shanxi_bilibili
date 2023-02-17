package tv.huan.bilibili.bean;

import java.io.Serializable;

public class HuanInfo implements Serializable {
    private static final long serialVersionUID = 9141803756019087135L;

    private String vtoken ;
    private String huanId ;

    @Override
    public String toString() {
        return "HuanInfo{" +
                "vtoken='" + vtoken + '\'' +
                ", huanId='" + huanId + '\'' +
                '}';
    }


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
