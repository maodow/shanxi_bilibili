package tv.huan.bilibili.bean;

/**
 * Created by Knight-yzm on 2020/7/6.
 *
 * action 动作 String 业务动作类型
 * MEDIA_AUTH：媒资鉴权；MEDIA_AUTH_AND_URL：媒资鉴权并获取播放地址；PRODUCT_AUTH：产品包鉴权
 * 是
 * video_id 媒资ID String 视达科自有cms平台视频ID（别称 内容ID，媒资ID ，媒资视频ID） 媒资类鉴权必须
 * video_index 分集号 String 视达科自有cms平台分集索引号,为0代表第一个分集
 * 可选,当没有此参数时,默认为媒资拥有的所有分集中，编号最小的一个。
 * （别称 分片）
 * 否
 * video_type 媒资类型 String 视达科自有cms平台
 * 点播：0直播：1回看：1+时间参数（day、begin、time_len）时移：1+时间参数（day、begin）
 * 媒资类鉴权必须
 * day 日期 String 当为回看时，可传递回看日期，格式为：yyyyMMdd。 如：20140418 时移、回看必须
 * begin 开始时间 String 可传递回看时间，格式为：HHmmss。如：150000 时移、回看必须
 * time_len 时长 String 当为回看时, 可传递回看时长，单位为秒s 回看必须
 * is_preview 是否试看 String 1-试看 0-正常观看 你热不0 否
 * area_code 地区码 String 用户对应的地区码 否
 * cp_id cp的id String cp标识id 是
 * cp_video_id cp注入视频id string CP注入视频ID， 否
 * cp_expand CP媒资扩展字段 string CP媒资扩展字段 否
 * cp_index_id CP注入分集ID string CP注入分集ID 否
 * cp_media_id CP注入片源ID string CP注入片源ID，未指定时,由服务器端自动选择一个适合的媒体 
 * 如果终端没有确认需要该参数，则不建议填写此参数,此参数可以让终端强制选择指定片源进行播放。
 * 否
 * product_id 产品id string 产品包鉴权时传入的产品包id（此处支持多个产品ID,以英文逗号分隔，如果传入为空则进行基础包鉴权） 产品鉴权必须
 * product_auth_type 产品鉴权类型 string 区分是用户产品鉴权还是设备产品鉴权
 * device/user 如果为空则默认为用户+设备
 * 否
 * quality 片源品质 String 非必要参数，作为筛选条件筛选片源类型
 * 不传为不进行筛选，
 * STD为标清
 * HD为高清
 * LOW为低清
 * 4K 4K影片
 * SD 超清
 * AUTO 自动
 * 否
 * is_batch_auth_index
 */
public class GetMediaAuthInfo {
    private String action;
    private String video_id;
    private String video_index;
    private String video_type;
    private String day;
    private String begin;
    private String time_len;
//    private String is_preview;
//    private String area_code;
    private String cp_id;
    private String cp_video_id;
//    private String cp_expand;
    private String cp_index_id;
//    private String cp_media_id;
    private String product_id;
//    private String product_auth_type;
//    private String quality;
//    private String is_batch_auth_index;


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getVideo_index() {
        return video_index;
    }

    public void setVideo_index(String video_index) {
        this.video_index = video_index;
    }

    public String getVideo_type() {
        return video_type;
    }

    public void setVideo_type(String video_type) {
        this.video_type = video_type;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getTime_len() {
        return time_len;
    }

    public void setTime_len(String time_len) {
        this.time_len = time_len;
    }

    public String getCp_id() {
        return cp_id;
    }

    public void setCp_id(String cp_id) {
        this.cp_id = cp_id;
    }

    public String getCp_video_id() {
        return cp_video_id;
    }

    public void setCp_video_id(String cp_video_id) {
        this.cp_video_id = cp_video_id;
    }

    public String getCp_index_id() {
        return cp_index_id;
    }

    public void setCp_index_id(String cp_index_id) {
        this.cp_index_id = cp_index_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
