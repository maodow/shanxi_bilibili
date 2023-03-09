package tv.huan.bilibili.bean.format;

import androidx.annotation.Keep;

import java.io.Serializable;

import tv.huan.bilibili.bean.JumpBean;

@Keep
public final class LookBean implements Serializable, JumpBean {

    private String name;
    private String cid;

    private int index;
    private long durning;
    private long position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getToType() {
        return 1;
    }

    @Override
    public int getClassId() {
        return 0;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public long getDurning() {
        return durning;
    }

    public void setDurning(long durning) {
        this.durning = durning;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }
}
