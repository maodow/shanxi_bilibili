package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

import tv.huan.bilibili.bean.base.BaseDataBean;

public final class GetChannelsBean implements Serializable {

    private List<ItemBean> list;

    public List<ItemBean> getList() {
        return list;
    }

    public void setList(List<ItemBean> list) {
        this.list = list;
    }

    @Keep
    public static class ItemBean extends BaseDataBean implements Serializable {

        private ImgsBean imgs;
        private int show;

        public ImgsBean getImgs() {
            return imgs;
        }

        public void setImgs(ImgsBean imgs) {
            this.imgs = imgs;
        }

        public int getShow() {
            return show;
        }

        public void setShow(int show) {
            this.show = show;
        }

        @Keep
        public static class ImgsBean implements Serializable {

            private String focus;
            private String poster;
            private String icon;
            private String stay;

            public String getStay() {
                return stay;
            }

            public void setStay(String stay) {
                this.stay = stay;
            }

            public String getFocus() {
                return focus;
            }

            public void setFocus(String focus) {
                this.focus = focus;
            }

            public String getPoster() {
                return poster;
            }

            public void setPoster(String poster) {
                this.poster = poster;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }
    }
}