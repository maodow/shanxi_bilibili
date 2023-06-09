package tv.huan.bilibili.bean.base;

import java.io.Serializable;

import lib.kalu.leanback.presenter.bean.TvEpisodesPlusItemBean;
import tv.huan.bilibili.BuildConfig;

public class BaseDataBean extends TvEpisodesPlusItemBean implements Serializable {

    private String vid;
    private String cid;
    private int classId;
    private String recClassId;
    private int toType;
    private String jumpParam;
    private int id;
    private int type;

    private String brief;
    private String name;
    private String title;

    private String playTime;
    private String playLength;
    private int pos;
    private long seek;

    private boolean fromSearch = false;
    private String fromSearchKeys = null;

    private boolean fromSpecial = false;
    private int fromSpecialTopId = 0;
    private String fromSpecialTopName = null;

    public boolean isFromSpecial() {
        return fromSpecial;
    }

    public void setFromSpecial(boolean fromSpecial) {
        this.fromSpecial = fromSpecial;
    }

    public int getFromSpecialTopId() {
        return fromSpecialTopId;
    }

    public void setFromSpecialTopId(int fromSpecialTopId) {
        this.fromSpecialTopId = fromSpecialTopId;
    }

    public String getFromSpecialTopName() {
        return fromSpecialTopName;
    }

    public void setFromSpecialTopName(String fromSpecialTopName) {
        this.fromSpecialTopName = fromSpecialTopName;
    }

    public boolean isFromSearch() {
        return fromSearch;
    }

    public void setFromSearch(boolean fromSearch) {
        this.fromSearch = fromSearch;
    }

    public String getFromSearchKeys() {
        return fromSearchKeys;
    }

    public void setFromSearchKeys(String fromSearchKeys) {
        this.fromSearchKeys = fromSearchKeys;
    }

    public long getSeek() {
        return seek;
    }

    public void setSeek(long seek) {
        this.seek = seek;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getPlayLength() {
        return playLength;
    }

    public void setPlayLength(String playLength) {
        this.playLength = playLength;
    }

    public int getPos() {
        return pos;
    }

    public int getPosition() {
        try {
            int i = getPos() - 1;
            if (i < 0)
                throw new Exception();
            return i;
        } catch (Exception e) {
            return 0;
        }
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getToType() {
        return toType;
    }

    public void setToType(int toType) {
        this.toType = toType;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getName() {
        if (null != name && name.length() > 0) {
            return name;
        } else if (null != title && title.length() > 0) {
            return title;
        } else {
            return "";
        }
    }

    public String getName1() {
        try {
            if (type == BuildConfig.HUAN_TYPE_FILM) {
                throw new Exception();
            } else {
                int pos = getPos();
                if (pos < 0)
                    throw new Exception();
                return getName() + "(第" + (pos + 1) + "集)";
            }
        } catch (Exception e) {
            return "";
        }
    }

    public String getName2(int pos) {
        try {
            if (type == BuildConfig.HUAN_TYPE_FILM) {
                throw new Exception();
            } else {
                if (pos < 0)
                    throw new Exception();
                return getName() + "(第" + (pos + 1) + "集)";
            }
        } catch (Exception e) {
            return getName();
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getJumpParam() {
        return jumpParam;
    }

    public void setJumpParam(String jumpParam) {
        this.jumpParam = jumpParam;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecClassId() {
        return recClassId;
    }

    public void setRecClassId(String recClassId) {
        this.recClassId = recClassId;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
