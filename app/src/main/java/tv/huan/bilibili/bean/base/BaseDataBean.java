package tv.huan.bilibili.bean.base;

import java.io.Serializable;

import lib.kalu.leanback.presenter.bean.TvEpisodesItemBean;

public class BaseDataBean extends TvEpisodesItemBean implements Serializable {

    private String vid;
    private String cid;
    private int classId;
    private String recClassId;
    private int toType;
    private int id;

    private String name;
    private String title;

    private String playTime;
    private String playLength;
    private int pos;

    public long getSeek() {
        try {
            long l = Long.parseLong(playTime);
            if (l <= 0)
                throw new Exception();
            return l;
        } catch (Exception e) {
            return 0;
        }
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getPlayLength() {
        return playLength;
    }

    public void setPlayLength(String playLength) {
        this.playLength = playLength;
    }

    public int getPos() {
        return pos;
    }

    public int getPosition() {
        try {
            int i = getPos() - 1;
            if (i < 0)
                throw new Exception();
            return i;
        } catch (Exception e) {
            return 0;
        }
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getToType() {
        return toType;
    }

    public void setToType(int toType) {
        this.toType = toType;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getName() {
        if (null != name && name.length() > 0) {
            return name;
        } else if (null != title && title.length() > 0) {
            return title;
        } else {
            return "";
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecClassId() {
        return recClassId;
    }

    public void setRecClassId(String recClassId) {
        this.recClassId = recClassId;
    }
}
