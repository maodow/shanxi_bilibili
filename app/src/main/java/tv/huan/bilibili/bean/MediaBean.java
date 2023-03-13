package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;

import lib.kalu.leanback.presenter.bean.TvPresenterRowBean;
import tv.huan.bilibili.bean.base.BaseImageBean;

@Keep
public class MediaBean extends BaseImageBean implements Serializable, TvPresenterRowBean {

    @Override
    public MediaBean clone() {
        try {
            return (MediaBean) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    private int tempIndex;
    private String tempVideoUrl;
    private String tempImageUrl;
    private int tempPlayType;
    private boolean tempVip;
    private boolean tempFavor; //收藏状态
    private String tempTag;
    private String tempTitle;
    private String temoInfo;
    private String tempRecClassId;
    private String[] tempPicList;
    private long tempSeek;

    public int getTempIndex() {
        return tempIndex;
    }

    public void setTempIndex(int tempIndex) {
        this.tempIndex = tempIndex;
    }

    public String getTempVideoUrl() {
        //        return "http://39.134.19.248:6610/yinhe/2/ch00000090990000001335/index.m3u8?virtualDomain=yinhe.live_hls.zte.com";
        return "http://wxsnsdy.tc.qq.com/105/20210/snsdyvideodownload?filekey=30280201010421301f0201690402534804102ca905ce620b1241b726bc41dcff44e00204012882540400&amp;bizid=1023&amp;hy=SH&amp;fileparam=302c020101042530230204136ffd93020457e3c4ff02024ef202031e8d7f02030f42400204045a320a0201000400";
//        return "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
//        return tempVideoUrl;
    }

    public void setTempVideoUrl(String tempVideoUrl) {
        this.tempVideoUrl = tempVideoUrl;
    }

    public String getTempImageUrl() {
        return tempImageUrl;
    }

    public void setTempImageUrl(String tempImageUrl) {
        this.tempImageUrl = tempImageUrl;
    }

    public int getTempPlayType() {
        return tempPlayType;
    }

    public void setTempPlayType(int tempPlayType) {
        this.tempPlayType = tempPlayType;
    }

    public boolean isTempVip() {
        return tempVip;
    }

    public void setTempVip(boolean tempVip) {
        this.tempVip = tempVip;
    }

    public boolean isTempFavor() {
        return tempFavor;
    }

    public void setTempFavor(boolean tempFavor) {
        this.tempFavor = tempFavor;
    }

    public String getTempTag() {
        return tempTag;
    }

    public void setTempTag(String tempTag) {
        this.tempTag = tempTag;
    }

    public String getTempTitle() {
        return tempTitle;
    }

    public void setTempTitle(String tempTitle) {
        this.tempTitle = tempTitle;
    }

    public String getTemoInfo() {
        return temoInfo;
    }

    public void setTemoInfo(String temoInfo) {
        this.temoInfo = temoInfo;
    }

    public String getTempRecClassId() {
        return tempRecClassId;
    }

    public void setTempRecClassId(String tempRecClassId) {
        this.tempRecClassId = tempRecClassId;
    }

    public String[] getTempPicList() {
        return tempPicList;
    }

    public void setTempPicList(String[] tempPicList) {
        this.tempPicList = tempPicList;
    }

    public long getTempSeek() {
        return tempSeek;
    }

    public void setTempSeek(long tempSeek) {
        this.tempSeek = tempSeek;
    }

    @Override
    public String getRowTitle() {
        return null;
    }
}
