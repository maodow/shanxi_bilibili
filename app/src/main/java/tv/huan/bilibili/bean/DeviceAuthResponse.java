package tv.huan.bilibili.bean;

public class DeviceAuthResponse extends PayBaseResponse{
    private String webtoken;
    private String user_id;
    private String device_id;
    private String epg_host;
    private String tag;
    private String version;
    private String area_code;

    public String getWebtoken() {
        return webtoken;
    }

    public void setWebtoken(String webtoken) {
        this.webtoken = webtoken;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getEpg_host() {
        return epg_host;
    }

    public void setEpg_host(String epg_host) {
        this.epg_host = epg_host;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }
}
