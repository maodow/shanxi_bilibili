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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
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
