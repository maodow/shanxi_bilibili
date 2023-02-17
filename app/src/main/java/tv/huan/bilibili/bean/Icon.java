package tv.huan.bilibili.bean;

/**
 * Create by Richard
 * 2019-07-29 16:21
 */
public class Icon {
//    {"dirs":"http://172.30.147.71/resource/public/app/images/20200401/","update_flag":"true","version":"1.0.2","version_code":"20200702"}
    /**
     * dirs : http://posterjsydhuashi.huan.tv:81/public/app/images/20200301/
     * id : 1
     * params : {}
     * update_flag : true
     * version : 1.0.0.1
     * version_code : 1001
     */

    private String dirs;
    private int id;
    private ParamsBean params;
    private String update_flag;
    private String version;
    private String version_code;

    public String getDirs() {
        return dirs;
    }

    public void setDirs(String dirs) {
        this.dirs = dirs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public String getUpdate_flag() {
        return update_flag;
    }

    public void setUpdate_flag(String update_flag) {
        this.update_flag = update_flag;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public static class ParamsBean {
    }
}
