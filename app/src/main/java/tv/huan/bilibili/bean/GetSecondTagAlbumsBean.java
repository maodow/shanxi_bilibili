package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

/**
 * Create by XIECHENG
 * 2019/4/1 3:39 PM
 */
@Keep
public class GetSecondTagAlbumsBean implements Serializable {

    private List<ItemBean> albums;

    public List<ItemBean> getAlbums() {
        return albums;
    }

    public void setAlbums(List<ItemBean> albums) {
        this.albums = albums;
    }

    @Keep
    public static class ItemBean extends MediaBaseImageBean implements JumpBean, Serializable {
        private String cid;
        private String title;
        private int type;

        @Override
        public int getToType() {
            return 1;
        }

        @Override
        public int getClassId() {
            return getType();
        }

        @Override
        public String getCid() {
            return cid;
        }

        @Override
        public String getName() {
            return getTitle();
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
