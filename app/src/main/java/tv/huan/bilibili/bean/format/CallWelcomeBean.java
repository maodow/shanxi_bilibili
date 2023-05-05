package tv.huan.bilibili.bean.format;

import java.io.Serializable;

import tv.huan.bilibili.bean.base.BaseDataBean;

public final class CallWelcomeBean extends BaseDataBean implements Serializable {

    private boolean initStatus;
    private int adTime;
    private String adUrl;
    private int select;
    private int type;
    private String secondTag;
    private String data;

    public boolean isInitStatus() {
        return initStatus;
    }

    public void setInitStatus(boolean initStatus) {
        this.initStatus = initStatus;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setAdTime(int adTime) {
        this.adTime = adTime;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSecondTag() {
        return secondTag;
    }

    public void setSecondTag(String secondTag) {
        this.secondTag = secondTag;
    }

    public boolean containsAd() {
        return null != adUrl && adUrl.length() > 0 && adTime > 0;
//        return true;
    }

    public int getAdTime() {
        return adTime;
//        return 10;
    }

    public String getAdUrl() {
//        return "https://picnew11.photophoto.cn/20170513/huwaidaxingguanggaopaiyangjizhanshihaibaotadengxiangjufuxuanchuan-26754205_1.jpg";
        return adUrl;
    }
}
