package tv.huan.bilibili.bean.format;

import java.io.Serializable;

public class RefreshBean implements Serializable {

    private int start;
    private int length;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
