package tv.huan.bilibili.bean;

import java.io.Serializable;
import java.util.List;

public class ProgramInfo implements Serializable {

    private static final long serialVersionUID = -7523783096334495903L;
    private int total ;
    private int offset ;
    private int size ;
    private List<Program> rows ;
    private int page ;
    private String pages ;
    private HomeTag classification ;
    private List<MediaBean> albums ;

    @Override
    public String toString() {
        return "ProgramInfo{" +
                "total=" + total +
                ", offset=" + offset +
                ", size=" + size +
                ", rows=" + rows +
                ", page=" + page +
                ", pages='" + pages + '\'' +
                ", classification=" + classification +
                ", albums=" + albums +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Program> getRows() {
        return rows;
    }

    public void setRows(List<Program> rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public HomeTag getClassification() {
        return classification;
    }

    public void setClassification(HomeTag classification) {
        this.classification = classification;
    }

    public List<MediaBean> getAlbums() {
        return albums;
    }

    public void setAlbums(List<MediaBean> albums) {
        this.albums = albums;
    }
}
