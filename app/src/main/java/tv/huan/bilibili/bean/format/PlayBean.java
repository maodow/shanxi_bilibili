package tv.huan.bilibili.bean.format;

import java.io.Serializable;

public final class PlayBean implements Serializable {

    private long position;
    private long duration;

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isEnd() {
        return position >= duration;
    }
}
