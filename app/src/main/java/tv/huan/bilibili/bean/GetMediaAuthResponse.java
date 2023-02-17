package tv.huan.bilibili.bean;

/**
 * Created by Knight-yzm on 2020/7/6.
 */
public class GetMediaAuthResponse {
    private String state;//0通过6免费
    private String reason;
    private String playurl;
    private String video_id;
    private String media_id;
    private String media_quality;
    private String name;
    private String cp_id;
    private String buy_info;
    private String is_support_coupon;
    private String coupon_info;
    private String coupon_type_ids;
    private String preview;
    private String preview_seconds;
    private String preview_infos;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPlayurl() {
        return playurl;
    }

    public void setPlayurl(String playurl) {
        this.playurl = playurl;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getMedia_quality() {
        return media_quality;
    }

    public void setMedia_quality(String media_quality) {
        this.media_quality = media_quality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCp_id() {
        return cp_id;
    }

    public void setCp_id(String cp_id) {
        this.cp_id = cp_id;
    }

    public String getBuy_info() {
        return buy_info;
    }

    public void setBuy_info(String buy_info) {
        this.buy_info = buy_info;
    }

    public String getIs_support_coupon() {
        return is_support_coupon;
    }

    public void setIs_support_coupon(String is_support_coupon) {
        this.is_support_coupon = is_support_coupon;
    }

    public String getCoupon_info() {
        return coupon_info;
    }

    public void setCoupon_info(String coupon_info) {
        this.coupon_info = coupon_info;
    }

    public String getCoupon_type_ids() {
        return coupon_type_ids;
    }

    public void setCoupon_type_ids(String coupon_type_ids) {
        this.coupon_type_ids = coupon_type_ids;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getPreview_seconds() {
        return preview_seconds;
    }

    public void setPreview_seconds(String preview_seconds) {
        this.preview_seconds = preview_seconds;
    }

    public String getPreview_infos() {
        return preview_infos;
    }

    public void setPreview_infos(String preview_infos) {
        this.preview_infos = preview_infos;
    }
}
