package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

import tv.huan.bilibili.bean.base.BaseImageBean;

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
    public static class ItemBean extends BaseImageBean implements Serializable {
        private int type;

        @Override
        public int getToType() {
            return 1;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
