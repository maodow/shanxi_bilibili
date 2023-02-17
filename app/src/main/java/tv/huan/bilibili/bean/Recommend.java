package tv.huan.bilibili.bean;

import java.io.Serializable;

public class Recommend implements Serializable {

    private static final long serialVersionUID = -3001717101291812095L;

    private int id ;
    private String name ;
    private String poster ;
    private int pos ;
    private int status ;
    private int classId ;
    private String cid ;
    private String jumpUrl ;
    private int platformId ;
    private String type ;

    @Override
    public String toString() {
        return "Recommend{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", poster='" + poster + '\'' +
                ", pos=" + pos +
                ", status=" + status +
                ", classId=" + classId +
                ", cid='" + cid + '\'' +
                ", jumpUrl='" + jumpUrl + '\'' +
                ", platformId=" + platformId +
                ", type='" + type + '\'' +
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
