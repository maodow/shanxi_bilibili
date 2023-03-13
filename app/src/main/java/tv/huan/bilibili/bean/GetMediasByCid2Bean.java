package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

@Keep
public final class GetMediasByCid2Bean implements Serializable {

    private String recClassId; //?
    private MediaDetailBean album; //媒资详情
    private List<MediaBean> medias; //剧集
    private List<RecMediaBean> recAlbums; //猜你喜欢

    public String getRecClassId() {
        return recClassId;
    }

    public void setRecClassId(String recClassId) {
        this.recClassId = recClassId;
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
}