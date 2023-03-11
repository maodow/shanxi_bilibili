package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

import tv.huan.bilibili.bean.base.BaseDataBean;
import tv.huan.bilibili.bean.base.BaseImageBean;

@Keep
public class SpecialBean extends BaseDataBean implements Serializable {

    private ImgBean imgs;

    private List<ItemBean> list;

    public List<ItemBean> getList() {
        if (null != this.list) {
            for (ItemBean t : this.list) {
                t.setTempTopicId(getId());
                t.setTempTopicName(getName());
            }
        }
        return list;
    }

    public void setList(List<ItemBean> list) {
        this.list = list;
    }

    public ImgBean getImgs() {
        return imgs;
    }

    public void setImgs(ImgBean imgs) {
        this.imgs = imgs;
    }

    @Keep
    public static class ImgBean implements Serializable {
        private String icon;
        private String poster;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }
    }

    @Keep
    public static class ItemBean extends BaseImageBean implements Serializable {
        private String bindName;
        private int tempTopicId;
        private String tempTopicName;

        public void setBindName(String bindName) {
            this.bindName = bindName;
        }

        @Override
        public String getName() {
            return bindName;
        }

        public int getTempTopicId() {
            return tempTopicId;
        }

        public void setTempTopicId(int tempTopicId) {
            this.tempTopicId = tempTopicId;
        }

        public String getTempTopicName() {
            return tempTopicName;
        }

        public void setTempTopicName(String tempTopicName) {
            this.tempTopicName = tempTopicName;
        }

        @Override
        public int getToType() {
            return 1;
        }
    }
}
