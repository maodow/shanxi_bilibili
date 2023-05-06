package tv.huan.bilibili.bean;

import java.io.Serializable;

public class FavorBean implements Serializable {

    private int optCode;

    public void setOptCode(int optCode) {
        this.optCode = optCode;
    }

    public boolean isSucc() {
        return optCode == 1;
    }
}
