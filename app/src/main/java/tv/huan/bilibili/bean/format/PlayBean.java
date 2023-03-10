package tv.huan.bilibili.bean.format;

import org.json.JSONObject;

import java.io.Serializable;

public final class PlayBean implements Serializable {

    private long position;
    private long duration;

    public void setPosition(long position) {
        this.position = position;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isEnd() {
        return position > 0 && duration > 0 && position >= duration;
    }

    public long getPosition() {
        return position;
    }

    public long getDuration() {
        return duration;
    }
}
