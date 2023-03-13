package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class RecMediaBean extends MediaBean implements Serializable {

    @Override
    public int getToType() {
        return 1;
    }
}
