package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;

import tv.huan.bilibili.bean.base.BaseDataBean;

/**
 * Create by Richard
 * 2019-08-06 11:03
 */
@Keep
public class ExitBean extends BaseDataBean implements Serializable {

    private ImgsBean imgs;
    private int etype;

    public ImgsBean getImgs() {
        return imgs;
    }

    public void setImgs(ImgsBean imgs) {
        this.imgs = imgs;
    }

    public int getEtype() {
        return etype;
    }

    public void setEtype(int etype) {
        this.etype = etype;
    }

    @Override
    public int getToType() {
        return getEtype();
    }

    @Keep
    public static class ImgsBean implements Serializable {

        private String icon;
        private String focus;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getFocus() {
            return focus;
        }

        public void setFocus(String focus) {
            this.focus = focus;
        }
    }
}
