package tv.huan.bilibili.bean;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

import tv.huan.bilibili.BuildConfig;
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

        private InfoBean album;

        private String albumName;

        private boolean tempDel;

        public boolean isTempDel() {
            return tempDel;
        }

        public void setTempDel(boolean tempDel) {
            this.tempDel = tempDel;
        }

        public void setAlbumName(String albumName) {
            this.albumName = albumName;
        }

        public InfoBean getAlbum() {
            return album;
        }

        public void setAlbum(InfoBean album) {
            this.album = album;
        }

//        public String getName() {
//            super.getName();

//        }


        @Override
        public String getName() {
            String name = super.getName();
            if (null != name && name.length() > 0) {
                return name;
            } else {
                try {
                    return getAlbum().getName();
                } catch (Exception e) {
                    return "";
                }
            }
        }

        public String getName1() {
            try {
                int type = getAlbum().getType();
                if (type == BuildConfig.HUAN_TYPE_FILM) {
                    throw new Exception();
                } else {
                    int pos = getPos();
                    if (pos < 0)
                        throw new Exception();
                    return getAlbum().getName() + "(第" + (pos + 1) + "集)";
                }
            } catch (Exception e) {
                return getName();
            }
        }

        public String getStatus() {
            try {
                long position = Long.parseLong(getPlayTime());
                long duration = Long.parseLong(getPlayLength());
                if (position > 0 && duration > 0 && position == duration) {
                    return "已看完";
                } else if (position <= 0 && duration <= 0) {
                    return "观看至 1%";
                } else {
                    return "观看至 " + (int) position * 100 / duration + "%";
                }
            } catch (Exception e) {
                return "";
            }
        }

        public boolean isVisibility() {
            return null == getPlayTime() && null == getPlayLength();
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
                String status = getStatus();
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
            private String productName;
            private int payStatus;
            private int productType;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
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
