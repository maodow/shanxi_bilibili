package tv.huan.bilibili.bean.base;

import java.io.Serializable;

import lib.kalu.leanback.presenter.bean.TvEpisodesItemBean;

public class BaseDataBean extends TvEpisodesItemBean implements Serializable {

    private String vid;
    private String cid;
    private int classId;
    private int toType;
    private int id;

    private String name;
    private String title;


    private int specialId;
    private int filterId;
    private String filterCheckedName;

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

    public int getFilterId() {
        return filterId;
    }

    public void setFilterId(int filterId) {
        this.filterId = filterId;
    }

    public String getFilterCheckedName() {
        return filterCheckedName;
    }

    public void setFilterCheckedName(String filterCheckedName) {
        this.filterCheckedName = filterCheckedName;
    }

    public int getSpecialId() {
        return specialId;
    }

    public void setSpecialId(int specialId) {
        this.specialId = specialId;
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
}
