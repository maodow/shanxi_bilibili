package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lib.kalu.leanback.presenter.bean.TvEpisodesItemBean;

@Keep
public class MediaBaseImageBean extends TvEpisodesItemBean implements Serializable {

    private String poster;
    private String newPicVt;
    private String newPicHz;

    @SerializedName("pic")
    private String vipUrl;


    public MediaBaseImageBean() {
    }

    public MediaBaseImageBean(String poster, String newPicVt, String newPicHz) {
        this.poster = poster;
        this.newPicVt = newPicVt;
        this.newPicHz = newPicHz;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setNewPicVt(String newPicVt) {
        this.newPicVt = newPicVt;
    }

    public void setNewPicHz(String newPicHz) {
        this.newPicHz = newPicHz;
    }

    public String getPicture(boolean hz) {
        if (null != poster && poster.length() > 0) {
            return poster;
        } else if (hz && null != newPicHz && newPicHz.length() > 0) {
            return newPicHz;
        } else {
            return newPicVt;
        }
//        return "http://123.59.196.222/hljbili/public/productCodePic/series91030010010000000000001819pic27875.jpg";
    }

    public void copyPic(MediaBaseImageBean data) {
        if (null == data)
            return;
        this.poster = data.poster;
        this.newPicHz = data.newPicHz;
        this.newPicVt = data.newPicVt;
//        this.picVtPath = data.picVtPath;
//        this.picHzPath = data.picHzPath;
    }

    public String getVipUrl() {
        return vipUrl;
    }

    public void setVipUrl(String vipUrl) {
        this.vipUrl = vipUrl;
    }
}