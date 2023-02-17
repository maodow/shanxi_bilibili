package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;
import java.util.TreeSet;

/**
 * Create by XIECHENG
 * 2019/3/20 4:40 PM
 */
@Keep
public class FavBean implements Serializable {

    private int total;
    private int offset;
    private int size;
    private List<ItemBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<ItemBean> getRows() {
        return rows;
    }

    public void setRows(List<ItemBean> rows) {
        this.rows = rows;
    }

    @Keep
    public static class ItemBean implements Serializable, JumpBean {

        private String favTime;
        private int classId;
        private String huanId;
        private int productId;
        private InfoBean album;
        private int id;
        private String cid;
        private int itemType = 1;
        private boolean del;

        private int icon;
        private String title;
        private int index;
        private int jumpType;

        public int getJumpType() {
            return jumpType;
        }

        public void setJumpType(int jumpType) {
            this.jumpType = jumpType;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isDel() {
            return del;
        }

        public void setDel(boolean del) {
            this.del = del;
        }

        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public String getFavTime() {
            return favTime;
        }

        public void setFavTime(String favTime) {
            this.favTime = favTime;
        }

        @Override
        public int getToType() {
            return 1;
        }

        @Override
        public int getClassId() {
            return classId;
        }

        public void setClassId(int classId) {
            this.classId = classId;
        }

        public String getHuanId() {
            return huanId;
        }

        public void setHuanId(String huanId) {
            this.huanId = huanId;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public InfoBean getAlbum() {
            return album;
        }

        public void setAlbum(InfoBean album) {
            this.album = album;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String getCid() {
            return cid;
        }

        @Override
        public String getName() {
            try {
                return getAlbum().getTitle();
            } catch (Exception e) {
                return "";
            }
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        @Keep
        public static class InfoBean implements Serializable {

            private String code;
            private String newPicHz;
            private Object publishDate;
            private String pic;
            private String title;
            private int type;
            private String productName;
            private String picHzPath;
            private String picVtPath;
            private Object price;
            private String newPicVt;
            private Object validTerm;
            private String pic2;
            private int payStatus;
            private int productType;
            private String cid;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getNewPicHz() {
                return newPicHz;
            }

            public void setNewPicHz(String newPicHz) {
                this.newPicHz = newPicHz;
            }

            public Object getPublishDate() {
                return publishDate;
            }

            public void setPublishDate(Object publishDate) {
                this.publishDate = publishDate;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getPicHzPath() {
                return picHzPath;
            }

            public void setPicHzPath(String picHzPath) {
                this.picHzPath = picHzPath;
            }

            public String getPicVtPath() {
                return picVtPath;
            }

            public void setPicVtPath(String picVtPath) {
                this.picVtPath = picVtPath;
            }

            public Object getPrice() {
                return price;
            }

            public void setPrice(Object price) {
                this.price = price;
            }

            public String getNewPicVt() {
                return newPicVt;
            }

            public void setNewPicVt(String newPicVt) {
                this.newPicVt = newPicVt;
            }

            public Object getValidTerm() {
                return validTerm;
            }

            public void setValidTerm(Object validTerm) {
                this.validTerm = validTerm;
            }

            public String getPic2() {
                return pic2;
            }

            public void setPic2(String pic2) {
                this.pic2 = pic2;
            }

            public int getPayStatus() {
                return payStatus;
            }

            public void setPayStatus(int payStatus) {
                this.payStatus = payStatus;
            }

            public int getProductType() {
                return productType;
            }

            public void setProductType(int productType) {
                this.productType = productType;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }
        }
    }
}
