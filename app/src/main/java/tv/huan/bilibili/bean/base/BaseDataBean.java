package tv.huan.bilibili.bean.base;

import java.io.Serializable;

import lib.kalu.leanback.presenter.bean.TvEpisodesPlusItemBean;

public class BaseDataBean extends TvEpisodesPlusItemBean implements Serializable {

    private String movieCode;
    private String vid;
    private String cid;
    private int classId;
    private String recClassId;
    private int toType;
    private int id;

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

    public String getMovieCode() {
        return movieCode;
    }

    public void setMovieCode(String movieCode) {
        this.movieCode = movieCode;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
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
}
