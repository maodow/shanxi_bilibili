package tv.huan.bilibili.bean;

/**
 * AAA认证
 */
public class DeviceAuthRequest extends PayBaseRequest{
    private String show_error_dialog; //是否展示认证不通过的错误提示对话框。0：不展示 1：展示，默认值为1

    public String getShow_error_dialog() {
        return show_error_dialog;
    }

    public void setShow_error_dialog(String show_error_dialog) {
        this.show_error_dialog = show_error_dialog;
    }
}
