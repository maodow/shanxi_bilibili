package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

import tv.huan.bilibili.bean.base.BaseDataBean;
import tv.huan.bilibili.bean.base.BaseImageBean;

/**
 * Create by XIECHENG
 * 2019/3/18 2:06 PM
 */
@Keep
public class SearchBean implements Serializable {

    private List<ItemBean> albums;

    private List<KeyBean> keys;
    private List<ItemBean> recommends;

    public List<KeyBean> getKeys() {
        return keys;
    }

    public void setKeys(List<KeyBean> keys) {
        this.keys = keys;
    }

    public List<ItemBean> getRecommends() {
        return recommends;
    }

    public void setRecommends(List<ItemBean> recommends) {
        this.recommends = recommends;
    }

    public List<ItemBean> getAlbums() {
        return albums;
    }

    public void setAlbums(List<ItemBean> albums) {
        this.albums = albums;
    }

    @Keep
    public static class KeyBean extends BaseDataBean implements Serializable {

        @Override
        public int getToType() {
            return 1;
        }
    }

    @Keep
    public static class ItemBean extends BaseImageBean implements Serializable {

        @Override
        public int getToType() {
            return 1;
        }

    }
}
