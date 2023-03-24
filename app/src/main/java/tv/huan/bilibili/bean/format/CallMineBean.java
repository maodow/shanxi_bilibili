package tv.huan.bilibili.bean.format;

import java.io.Serializable;

public class CallMineBean implements Serializable {

    private boolean containsData;
    private int length;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isContainsData() {
        return containsData;
    }

    public void setContainsData(boolean containsData) {
        this.containsData = containsData;
    }
}
