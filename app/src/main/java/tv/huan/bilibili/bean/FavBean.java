package tv.huan.bilibili.bean;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

import tv.huan.bilibili.bean.base.BaseDataBean;
import tv.huan.bilibili.bean.base.BaseImageBean;

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
    public static class ItemBean extends BaseDataBean implements Serializable {

        private boolean showDel;
        private String favTime;
        private String huanId;
        private int productId;
        private InfoBean album;
        private int itemType = 1;

        private int icon;
        private int index;
        private String bannerUrl;

        private String playTime;
        private String playLength;
        private int pos;
        private String albumName;

        public void setAlbumName(String albumName) {
            this.albumName = albumName;
        }

        public void setPlayTime(String playTime) {
            this.playTime = playTime;
        }

        public void setPlayLength(String playLength) {
            this.playLength = playLength;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        public String getBannerUrl() {
            return bannerUrl;
        }

        public void setBannerUrl(String bannerUrl) {
            this.bannerUrl = bannerUrl;
        }

        public boolean isShowDel() {
            return showDel;
        }

        public void setShowDel(boolean showDel) {
            this.showDel = showDel;
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

        public String getName() {
            try {
                return getAlbum().getTitle();
            } catch (Exception e) {
                return "";
            }
        }

        public String getNameRec() {
            try {
                if (pos <= 0) {
                    return getAlbum().getTitle();
                } else {
                    return getAlbum().getTitle() + "(第" + pos + "集)";
                }
            } catch (Exception e) {
                return "";
            }
        }

        public String getStatusRec() {
            try {
                long position = Long.parseLong(playTime);
                long duration = Long.parseLong(playLength);
                if (position <= 0 && duration <= 0)
                    throw new Exception();
                return "观看至 " + (int) position * 100 / duration + "%";
            } catch (Exception e) {
                return "已看完";
            }
        }

        public CharSequence getPositionRec() {
            try {
                String name = getName();
                int length = name.length();
                if (length > 8) {
                    name = name.substring(0, 9) + "...";
                }
                SpannableStringBuilder spannableString = new SpannableStringBuilder();
                spannableString.append(name);
                int length1 = spannableString.length();
                spannableString.setSpan(new AbsoluteSizeSpan(30), 0, length1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), 0, length1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                String status = getStatusRec();
                spannableString.append("  " + status);
                int length2 = spannableString.length();
                spannableString.setSpan(new AbsoluteSizeSpan(22), length1, length2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#303030")), length1, length2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                return spannableString;
            } catch (Exception e) {
                return "";
            }
        }

        @Keep
        public static class InfoBean extends BaseImageBean implements Serializable {

            private String code;
            private int type;
            private String productName;
            private int payStatus;
            private int productType;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
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
        }
    }
}
