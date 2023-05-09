package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;

import lib.kalu.leanback.presenter.bean.TvPresenterRowBean;
import tv.huan.bilibili.BuildConfig;
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

    private String tempVideoUrl;
    private String tempImageUrl;
    private int tempPlayType; //试看集数
    private boolean tempFavor; //收藏状态
    private String tempTag;
    private String tempTitle;
    private String temoInfo;
    private String tempRecClassId;
    private String[] tempPicList;
    private int tempType;
    private int tempLegnth;
    private String tempMoiveCode;

    private String movieCode; //中兴平台
    private String hwMovieCode; //华为平台

    private int tempPayType = -1; //付费类型  0免费;  1收费, apk自新加，处理详情页标签小图标


    public int getTempLegnth() {
        return tempLegnth;
    }

    public void setTempLegnth(int tempLegnth) {
        this.tempLegnth = tempLegnth;
    }

    public String getTempMoiveCode() {
        return tempMoiveCode;
    }

    public void setTempMoiveCode(String tempMoiveCode) {
        this.tempMoiveCode = tempMoiveCode;
    }

    public void setTempType(int tempType) {
        this.tempType = tempType;
    }

    public boolean isXuanQi() {
        //  选期 => 教育、体育、综艺
        if (tempType == BuildConfig.HUAN_TYPE_EDUCATION || tempType == BuildConfig.HUAN_TYPE_SPORTS || tempType == BuildConfig.HUAN_TYPE_VARIETY) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isXuanJi() {
        // 电影
        if (tempType == BuildConfig.HUAN_TYPE_FILM) {
            return false;
        }
        //  选期 => 教育、体育、综艺
        else if (tempType == BuildConfig.HUAN_TYPE_EDUCATION || tempType == BuildConfig.HUAN_TYPE_SPORTS || tempType == BuildConfig.HUAN_TYPE_VARIETY) {
            return false;
        }
        // 选集
        else {
            return true;
        }
    }

    public String getTempVideoUrl() {
        return tempVideoUrl;
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

    public int getTempPayType() {
        return tempPayType;
    }

    public void setTempPayType(int tempPayType) {
        this.tempPayType = tempPayType;
    }

    public int getTempPlayType() {
        return tempPlayType;
    }

    public void setTempPlayType(int tempPlayType) {
        this.tempPlayType = tempPlayType;
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

    public String getMovieCode() {
        return movieCode;
    }

    public void setMovieCode(String movieCode) {
        this.movieCode = movieCode;
    }

    public String getHwMovieCode() {
        return hwMovieCode;
    }

    public void setHwMovieCode(String hwMovieCode) {
        this.hwMovieCode = hwMovieCode;
    }

    @Override
    public String getRowTitle() {
        return null;
    }
}
