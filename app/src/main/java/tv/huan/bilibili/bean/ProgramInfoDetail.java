package tv.huan.bilibili.bean;

import java.io.Serializable;
import java.util.List;

public class ProgramInfoDetail implements Serializable {

    private boolean isFavor;
    private boolean vipFree;
    private boolean vipPassDanDian;
    private boolean vipLinkDanDian;
    private boolean vipPassZhouQi;
    private boolean vipLinkZhouQi;
    private boolean vipPassZhuanQu;
    private boolean vipLinkZhuanQu;

    public boolean isFavor() {
        return isFavor;
    }

    public void setFavor(boolean favor) {
        isFavor = favor;
    }

    public boolean isVipLinkDanDian() {
        return vipLinkDanDian;
    }

    public void setVipLinkDanDian(boolean vipLinkDanDian) {
        this.vipLinkDanDian = vipLinkDanDian;
    }

    public boolean isVipLinkZhouQi() {
        return vipLinkZhouQi;
    }

    public void setVipLinkZhouQi(boolean vipLinkZhouQi) {
        this.vipLinkZhouQi = vipLinkZhouQi;
    }

    public boolean isVipLinkZhuanQu() {
        return vipLinkZhuanQu;
    }

    public void setVipLinkZhuanQu(boolean vipLinkZhuanQu) {
        this.vipLinkZhuanQu = vipLinkZhuanQu;
    }

    public boolean isVipFree() {
        return vipFree;
    }

    public void setVipFree(boolean vipFree) {
        this.vipFree = vipFree;
    }

    public boolean isVipPassDanDian() {
        return vipPassDanDian;
    }

    public void setVipPassDanDian(boolean vipPassDanDian) {
        this.vipPassDanDian = vipPassDanDian;
    }

    public boolean isVipPassZhouQi() {
        return vipPassZhouQi;
    }

    public void setVipPassZhouQi(boolean vipPassZhouQi) {
        this.vipPassZhouQi = vipPassZhouQi;
    }

    public boolean isVipPassZhuanQu() {
        return vipPassZhuanQu;
    }

    public void setVipPassZhuanQu(boolean vipPassZhuanQu) {
        this.vipPassZhuanQu = vipPassZhuanQu;
    }

    private ProgramDetail album;
    private Columns column;
    private List<Media> medias;
    private List<Album> recAlbums;
    private String recClassId;
//    private String auth; //1鉴权通过 其它不通过目前是空
    private String classId;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public List<Album> getRecAlbums() {
        return recAlbums;
    }

    public void setRecAlbums(List<Album> recAlbums) {
        this.recAlbums = recAlbums;
    }

    public ProgramDetail getAlbum() {
        return album;
    }

    public void setAlbum(ProgramDetail album) {
        this.album = album;
    }

    public Columns getColumn() {
        return column;
    }

    public void setColumn(Columns column) {
        this.column = column;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    public String getRecClassId() {
        return recClassId;
    }

    public void setRecClassId(String recClassId) {
        this.recClassId = recClassId;
    }
}
