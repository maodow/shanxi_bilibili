package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public final class GetLastBookmark implements Serializable {

    private String playTime;
    private int pos;

    public long getSeek() {
        try {
            long l = Long.parseLong(playTime);
            if (l <= 0)
                throw new Exception();
            return l;
        } catch (Exception e) {
            return 0;
        }
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
