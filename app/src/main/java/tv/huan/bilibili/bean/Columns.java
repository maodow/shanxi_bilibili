package tv.huan.bilibili.bean;

import java.io.Serializable;

public class Columns implements Serializable {
    private static final long serialVersionUID = -5032273895368549622L;

    private int id;
    private String columnId;
    private String title;
    private int type;
    private String subtype;
    private String typeName;
    private String newPicVt;
    private String newPicHz;
    private String season;
    private String period;
    private String totalIssue;
    private String lastCover;
    private String lastPubtime;
    private String description;
    private String varietyType;
    private String updateTime;
    private int status;
    private int invalid;

    @Override
    public String toString() {
        return "Columns{" +
                "id=" + id +
                ", columnId='" + columnId + '\'' +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", subtype='" + subtype + '\'' +
                ", typeName='" + typeName + '\'' +
                ", newPicVt='" + newPicVt + '\'' +
                ", newPicHz='" + newPicHz + '\'' +
                ", season='" + season + '\'' +
                ", period='" + period + '\'' +
                ", totalIssue='" + totalIssue + '\'' +
                ", lastCover='" + lastCover + '\'' +
                ", lastPubtime='" + lastPubtime + '\'' +
                ", description='" + description + '\'' +
                ", varietyType='" + varietyType + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", status=" + status +
                ", invalid=" + invalid +
                '}';
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getNewPicVt() {
        return newPicVt;
    }

    public void setNewPicVt(String newPicVt) {
        this.newPicVt = newPicVt;
    }

    public String getNewPicHz() {
        return newPicHz;
    }

    public void setNewPicHz(String newPicHz) {
        this.newPicHz = newPicHz;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTotalIssue() {
        return totalIssue;
    }

    public void setTotalIssue(String totalIssue) {
        this.totalIssue = totalIssue;
    }

    public String getLastCover() {
        return lastCover;
    }

    public void setLastCover(String lastCover) {
        this.lastCover = lastCover;
    }

    public String getLastPubtime() {
        return lastPubtime;
    }

    public void setLastPubtime(String lastPubtime) {
        this.lastPubtime = lastPubtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVarietyType() {
        return varietyType;
    }

    public void setVarietyType(String varietyType) {
        this.varietyType = varietyType;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getInvalid() {
        return invalid;
    }

    public void setInvalid(int invalid) {
        this.invalid = invalid;
    }
}
