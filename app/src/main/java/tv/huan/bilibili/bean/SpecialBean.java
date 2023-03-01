package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

@Keep
public class SpecialBean implements Serializable {

    private List<ItemBean> list;
    private ImgBean imgs;

    public List<ItemBean> getList() {
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
    public static class ItemBean extends ImageBean implements JumpBean, Serializable {
        private String bindName;
        private int payStatus; //8 免费，其它付费
        private int bindType;
        private int pos;
        private String baseId;
        private String cid;
        private int productType;

        private String pic;

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getProductType() {
            return productType;
        }

        public void setProductType(int productType) {
            this.productType = productType;
        }

        public String getBindName() {
            return bindName;
        }

        public void setBindName(String bindName) {
            this.bindName = bindName;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        public int getBindType() {
            return bindType;
        }

        public void setBindType(int bindType) {
            this.bindType = bindType;
        }

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        public String getBaseId() {
            return baseId;
        }

        public void setBaseId(String baseId) {
            this.baseId = baseId;
        }

        @Override
        public int getToType() {
            return 1;
        }

        @Override
        public int getClassId() {
            return 0;
        }

        public String getCid() {
            return cid;
        }

        @Override
        public String getName() {
            return getBindName();
        }

        public void setCid(String cid) {
            this.cid = cid;
        }
    }
}
