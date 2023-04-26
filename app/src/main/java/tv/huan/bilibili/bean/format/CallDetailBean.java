package tv.huan.bilibili.bean.format;

import java.io.Serializable;
import java.util.List;

import tv.huan.bilibili.bean.MediaBean;
import tv.huan.bilibili.bean.MediaDetailBean;
import tv.huan.bilibili.bean.RecMediaBean;
import tv.huan.bilibili.ui.detail.template.DetailTemplatePlayer;

public class CallDetailBean implements Serializable {

    private boolean favor; //收藏状态
    private int playType; // 播放策略, vip前几集免费看
    private String recClassId;

    private MediaDetailBean album; //媒资详情
    private List<MediaBean> medias; //剧集
    private List<RecMediaBean> recAlbums; //猜你喜欢
    private int defaultPosition;

    public int getDefaultPosition() {
        return defaultPosition;
    }

    public void setDefaultPosition(int defaultPosition) {
        this.defaultPosition = defaultPosition;
    }

    public MediaDetailBean getAlbum() {
        return album;
    }

    public void setAlbum(MediaDetailBean album) {
        this.album = album;
    }

    public List<MediaBean> getMedias() {
        return medias;
    }

    public void setMedias(List<MediaBean> medias) {
        this.medias = medias;
    }

    public List<RecMediaBean> getRecAlbums() {
        return recAlbums;
    }

    public void setRecAlbums(List<RecMediaBean> recAlbums) {
        this.recAlbums = recAlbums;
    }

    public int getPlayType() {
        return playType;
    }

    public void setPlayType(int playType) {
        this.playType = playType;
    }

    public String getRecClassId() {
        return recClassId;
    }

    public void setRecClassId(String recClassId) {
        this.recClassId = recClassId;
    }

    public boolean isFavor() {
        return favor;
    }

    public void setFavor(boolean favor) {
        this.favor = favor;
    }
}
