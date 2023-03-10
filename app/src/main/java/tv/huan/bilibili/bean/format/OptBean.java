package tv.huan.bilibili.bean.format;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public final class OptBean implements Serializable {

    private int optCode;

    public void setOptCode(int optCode) {
        this.optCode = optCode;
    }

    public boolean isSucc() {
        return 1 == optCode;
    }
}
