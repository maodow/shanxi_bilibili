package tv.huan.bilibili.bean;

import java.util.List;

/**
 * Create by Richard
 * 2019-07-29 16:21
 */
public class BlackList {

    private List<String> mac_list;
    private List<PropListBean> prop_list;

    public List<String> getMac_list() {
        return mac_list;
    }

    public void setMac_list(List<String> mac_list) {
        this.mac_list = mac_list;
    }

    public List<PropListBean> getProp_list() {
        return prop_list;
    }

    public void setProp_list(List<PropListBean> prop_list) {
        this.prop_list = prop_list;
    }

    public static class PropListBean {
        /**
         * product : UM-UNT400C003
         * androidVer : 4.4.2
         * osVer : JLV201846P5508
         */

        private String product;
        private String androidVer;
        private String osVer;

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getAndroidVer() {
            return androidVer;
        }

        public void setAndroidVer(String androidVer) {
            this.androidVer = androidVer;
        }

        public String getOsVer() {
            return osVer;
        }

        public void setOsVer(String osVer) {
            this.osVer = osVer;
        }
    }
}
