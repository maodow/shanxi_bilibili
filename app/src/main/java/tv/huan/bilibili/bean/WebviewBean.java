package tv.huan.bilibili.bean;

import java.io.Serializable;

public class WebviewBean implements Serializable {

    private boolean data;
    private String value;

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
