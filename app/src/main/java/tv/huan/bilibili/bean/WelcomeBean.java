package tv.huan.bilibili.bean;

import java.io.Serializable;

public final class WelcomeBean implements Serializable {

    private int adTime;
    private String adUrl;
    private int select;
    private int type;
    private String cid;
    private int classId;
    private String secondTag;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getAdTime() {
        return adTime;
    }

    public void setAdTime(int adTime) {
        this.adTime = adTime;
    }

    public String getAdUrl() {
        return adUrl;
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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getSecondTag() {
        return secondTag;
    }

    public void setSecondTag(String secondTag) {
        this.secondTag = secondTag;
    }

    public boolean containsAd() {
        return null != adUrl && adUrl.length() > 0 && adTime > 0;
    }
}
