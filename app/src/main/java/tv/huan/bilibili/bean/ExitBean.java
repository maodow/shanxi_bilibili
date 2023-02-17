package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;

/**
 * Create by Richard
 * 2019-08-06 11:03
 */
@Keep
public class ExitBean implements Serializable, JumpBean {

    private ImgsBean imgs;
    private int id;
    private String name;
    private int etype;
    private String cid;

    public ImgsBean getImgs() {
        return imgs;
    }

    public void setImgs(ImgsBean imgs) {
        this.imgs = imgs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEtype() {
        return etype;
    }

    public void setEtype(int etype) {
        this.etype = etype;
    }

    @Override
    public int getToType() {
        return getEtype();
    }

    @Override
    public int getClassId() {
        return 0;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Keep
    public static class ImgsBean implements Serializable {

        private String poster;

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }
    }
}
