package tv.huan.bilibili.bean.format;

import java.io.Serializable;

public class CallCenterDelBaen implements Serializable {

    private int position;
    private boolean empty;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
