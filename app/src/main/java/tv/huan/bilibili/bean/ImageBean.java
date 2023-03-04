package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class ImageBean implements Serializable {

    //  return null == newPicVt || newPicVt.isEmpty() ? picVtPath : newPicVt;
//    private String pic;
    private String poster;
    private String newPicVt;
    private String newPicHz;

//    private String picVtPath;
//    private String picHzPath;


    public ImageBean() {
    }

    public ImageBean(String poster, String newPicVt, String newPicHz) {
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

//    public void setPic(String pic) {
//        this.pic = pic;
//    }

    public String getPicture(boolean hz) {
        if (null != poster && poster.length() > 0) {
            return poster;
        } else if (hz && null != newPicHz && newPicHz.length() > 0) {
            return newPicHz;
        } else {
            return newPicVt;
        }
    }

//    public String getPictureFavor() {
//        if (null != poster && poster.length() > 0) {
//            return poster;
//        } else {
//            return pic;
//        }
//    }

    public void copyPic(ImageBean data) {
        if (null == data)
            return;
        this.poster = data.poster;
        this.newPicHz = data.newPicHz;
        this.newPicVt = data.newPicVt;
//        this.picVtPath = data.picVtPath;
//        this.picHzPath = data.picHzPath;
    }
}