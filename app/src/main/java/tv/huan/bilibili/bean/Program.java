package tv.huan.bilibili.bean;

import java.io.Serializable;

public class Program implements Serializable {

    private static final long serialVersionUID = -4261256634172280464L;
    private String albumName ;
    private String HuanId ;
    private int productId ;
    private Album album ;
    private int endFlag ;
    private String markTime ;
    private String vid ;
    private int classId ;
    private int playHistoryId ;
    private int pos ;
    private String clientCode ;
    private String pageNo ;
    private String fontSize ;
    private String playTime ;
    private int id ;
    private String mediaName ;
    private String cid ;


    @Override
    public String toString() {
        return "Program{" +
                "albumName='" + albumName + '\'' +
                ", HuanId='" + HuanId + '\'' +
                ", productId=" + productId +
                ", album=" + album +
                ", endFlag=" + endFlag +
                ", markTime='" + markTime + '\'' +
                ", vid='" + vid + '\'' +
                ", classId=" + classId +
                ", playHistoryId=" + playHistoryId +
                ", pos=" + pos +
                ", clientCode='" + clientCode + '\'' +
                ", pageNo='" + pageNo + '\'' +
                ", fontSize='" + fontSize + '\'' +
                ", playTime='" + playTime + '\'' +
                ", id=" + id +
                ", mediaName='" + mediaName + '\'' +
                ", cid='" + cid + '\'' +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getHuanId() {
        return HuanId;
    }

    public void setHuanId(String huanId) {
        HuanId = huanId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public int getEndFlag() {
        return endFlag;
    }

    public void setEndFlag(int endFlag) {
        this.endFlag = endFlag;
    }

    public String getMarkTime() {
        return markTime;
    }

    public void setMarkTime(String markTime) {
        this.markTime = markTime;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getPlayHistoryId() {
        return playHistoryId;
    }

    public void setPlayHistoryId(int playHistoryId) {
        this.playHistoryId = playHistoryId;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
