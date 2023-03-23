package tv.huan.bilibili.bean.format;

import java.io.Serializable;

public class CallPageBean implements Serializable {

    private int start;
    private int num;
    private boolean hasData;

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
