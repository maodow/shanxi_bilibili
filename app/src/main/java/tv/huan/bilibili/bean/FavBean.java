package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

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

        private boolean showDel;
        private String favTime;
        private int classId;
        private String huanId;
        private int productId;
        private InfoBean album;
        private int id;
        private String cid;
        private int itemType = 1;

        private int icon;
        private String title;
        private int index;
        private int jumpType;

        public boolean isShowDel() {
            return showDel;
        }

        public void setShowDel(boolean showDel) {
            this.showDel = showDel;
        }

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
        public static class InfoBean extends ImageBean implements Serializable {

            private String code;
            private String title;
            private int type;
            private String productName;
            private int payStatus;
            private int productType;
            private String cid;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
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
