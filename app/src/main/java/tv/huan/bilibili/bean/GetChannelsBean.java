package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

public final class GetChannelsBean implements Serializable {

    private List<ItemBean> list;

    public List<ItemBean> getList() {
        return list;
    }

    public void setList(List<ItemBean> list) {
        this.list = list;
    }

    @Keep
    public static class ItemBean implements Serializable {

        private ImgsBean imgs;
        private int id;
        private String name;
        private int show;
        private int classId;

        public ImgsBean getImgs() {
            return imgs;
        }

        public void setImgs(ImgsBean imgs) {
            this.imgs = imgs;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getShow() {
            return show;
        }

        public void setShow(int show) {
            this.show = show;
        }

        public int getClassId() {
            return classId;
        }

        public void setClassId(int classId) {
            this.classId = classId;
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