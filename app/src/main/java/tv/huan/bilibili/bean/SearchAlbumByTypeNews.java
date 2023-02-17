package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

@Keep
public final class SearchAlbumByTypeNews implements Serializable {

    /**
     * page : 1
     * size : 10
     * total : 8458
     * pages : null
     * offset : 0
     * classification : null
     * albums : [{"cid":"kkbjc8znbxxvor6","title":"东北插班生2虎啸风声","type":1,"newPicVt":"http://puui.qpic.cn/vcover_vt_pic/0/kkbjc8znbxxvor61558075544/260","newPicHz":"http://puui.qpic.cn/vcover_hz_pic/0/kkbjc8znbxxvor61558075640/408","payStatus":6,"publishDate":"2019-05-21","picHzPath":"","picVtPath":""}]
     */

    private int page;
    private int size;
    private int total;
    private Object pages;
    private int offset;
    private Object classification;
    private List<GetSecondTagAlbumsBean.ItemBean> albums;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
//            return total;
        return 100;

    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object getPages() {
        return pages;
    }

    public void setPages(Object pages) {
        this.pages = pages;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Object getClassification() {
        return classification;
    }

    public void setClassification(Object classification) {
        this.classification = classification;
    }

    public List<GetSecondTagAlbumsBean.ItemBean> getAlbums() {
        return albums;
    }

    public void setAlbums(List<GetSecondTagAlbumsBean.ItemBean> albums) {
        this.albums = albums;
    }
}
