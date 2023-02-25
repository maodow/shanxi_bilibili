package tv.huan.bilibili.bean;

import java.io.Serializable;

import lib.kalu.leanback.presenter.ListTvEpisodesPresenter;
import lib.kalu.leanback.presenter.ListTvGridPresenter;

public class Media extends ListTvEpisodesPresenter.ItemBean implements Serializable, ListTvGridPresenter.ListGridBean {

    private int id;
    private String vid;
    private String cid;
    private String title;
    private int drm; //0免费视频 1普通付费视频 2 drm付费视频
    private int duration;
    private String pic;
    private String picPath;
    private String publishDate;//发布时间
    private String cdnUrl;
    private String zxPlayUrl;
    private String hwPlayUrl;
    private String encryptionType;

    private int playType = 0;
    private int position;
    private int positionSelect;

    private String name;
    private int index = -1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPositionSelect() {
        return positionSelect;
    }

    public void setPositionSelect(int positionSelect) {
        this.positionSelect = positionSelect;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPlayType() {
        return playType;
    }

    public void setPlayType(int playType) {
        this.playType = playType;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrm() {
        return drm;
    }

    public void setDrm(int drm) {
        this.drm = drm;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getCdnUrl() {
        //                            object.setUrl("http://114.118.13.20:8197/data_source/test/001/06/06.m3u8");
//                            object.setUrl("https://vkceyugu.cdn.bspapp.com/VKCEYUGU-uni4934e7b/c4d93960-5643-11eb-a16f-5b3e54966275.m3u8");
        return "http://39.134.19.248:6610/yinhe/2/ch00000090990000001335/index.m3u8?virtualDomain=yinhe.live_hls.zte.com";
//        return cdnUrl;
    }

    public void setCdnUrl(String cdnUrl) {
        this.cdnUrl = cdnUrl;
    }

    public String getZxPlayUrl() {
        return zxPlayUrl;
    }

    public void setZxPlayUrl(String zxPlayUrl) {
        this.zxPlayUrl = zxPlayUrl;
    }

    public String getHwPlayUrl() {
        return hwPlayUrl;
    }

    public void setHwPlayUrl(String hwPlayUrl) {
        this.hwPlayUrl = hwPlayUrl;
    }

    @Override
    public Media clone() {
        try {
            return (Media) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getGridHead() {
        return getTitle();
    }
}
