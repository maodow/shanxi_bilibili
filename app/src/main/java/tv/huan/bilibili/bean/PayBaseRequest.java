package tv.huan.bilibili.bean;

public class PayBaseRequest {
    private String package_name;
    private String cp_id; //后台分配给每个cp的id

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getCp_id() {
        return cp_id;
    }

    public void setCp_id(String cp_id) {
        this.cp_id = cp_id;
    }
}
