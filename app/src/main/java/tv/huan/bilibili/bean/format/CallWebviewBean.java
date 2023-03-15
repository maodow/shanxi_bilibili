package tv.huan.bilibili.bean.format;

import java.io.Serializable;

public class CallWebviewBean implements Serializable {

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
