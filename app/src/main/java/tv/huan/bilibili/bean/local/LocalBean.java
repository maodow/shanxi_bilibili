package tv.huan.bilibili.bean.local;

import java.io.Serializable;

import tv.huan.bilibili.bean.base.BaseDataBean;

public class LocalBean extends BaseDataBean implements Serializable {

    private String local_img;
    private String local_vip;
    private String local_status;

    private String local_banner;
    private int local_drawable;
    private int local_index;
    private int local_type;

    public String getLocal_status() {
        return local_status;
    }

    public void setLocal_status(String local_status) {
        this.local_status = local_status;
    }

    public String getLocal_banner() {
        return local_banner;
    }

    public void setLocal_banner(String local_banner) {
        this.local_banner = local_banner;
    }

    public int getLocal_drawable() {
        return local_drawable;
    }

    public void setLocal_drawable(int local_drawable) {
        this.local_drawable = local_drawable;
    }

    public int getLocal_index() {
        return local_index;
    }

    public void setLocal_index(int local_index) {
        this.local_index = local_index;
    }

    public int getLocal_type() {
        return local_type;
    }

    public void setLocal_type(int local_type) {
        this.local_type = local_type;
    }

    public String getLocal_img() {
        return local_img;
    }

    public void setLocal_img(String local_img) {
        this.local_img = local_img;
    }

    public String getLocal_vip() {
        return local_vip;
    }

    public void setLocal_vip(String local_vip) {
        this.local_vip = local_vip;
    }
}
