package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

/**
 * Create by XIECHENG
 * 2019/3/18 2:48 PM
 */
@Keep
public class SearchRecommendBean implements Serializable {

    private List<KeyBean> keys;
    private List<RecommendsBean> recommends;

    public List<KeyBean> getKeys() {
        return keys;
    }

    public void setKeys(List<KeyBean> keys) {
        this.keys = keys;
    }

    public List<RecommendsBean> getRecommends() {
        return recommends;
    }

    public void setRecommends(List<RecommendsBean> recommends) {
        this.recommends = recommends;
    }

    @Keep
    public static class KeyBean implements Serializable {

        private int id;
        private String name;
        private String poster;
        private int pos;
        private int status;
        private int classId;
        private String cid;
        private String jumpUrl;
        private int platformId;
        private String type;
        private String payStatus;

        public String getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(String payStatus) {
            this.payStatus = payStatus;
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

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public String getJumpUrl() {
            return jumpUrl;
        }

        public void setJumpUrl(String jumpUrl) {
            this.jumpUrl = jumpUrl;
        }

        public int getPlatformId() {
            return platformId;
        }

        public void setPlatformId(int platformId) {
            this.platformId = platformId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    @Keep
    public static class RecommendsBean extends SearchBean.ItemBean {

        private int id;
        private String name;
        private String poster;
        private int pos;
        private int status;
        private int classId;
        private String jumpUrl;
        private int platformId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public int getClassId() {
            return classId;
        }

        public void setClassId(int classId) {
            this.classId = classId;
        }

        public String getJumpUrl() {
            return jumpUrl;
        }

        public void setJumpUrl(String jumpUrl) {
            this.jumpUrl = jumpUrl;
        }

        public int getPlatformId() {
            return platformId;
        }

        public void setPlatformId(int platformId) {
            this.platformId = platformId;
        }
    }
}
