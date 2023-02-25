package tv.huan.bilibili.bean;

import java.io.Serializable;

import lib.kalu.leanback.presenter.ListTvGridPresenter;
import lib.kalu.leanback.presenter.ListTvRowPresenter;

public class Album extends PosterBean implements Serializable, ListTvRowPresenter.ListRowBean, ListTvGridPresenter.ListGridBean, JumpBean {

    private static final long serialVersionUID = 2233263498353702029L;
    private String title;
    private int type;
    private int payStatus;
    private String cid;
    private int productType;
    private String pic;
    private String head;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    @Override
    public int getToType() {
        return 1;
    }

    @Override
    public int getClassId() {
        return 0;
    }

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

    @Override
    public String getGridHead() {
        return getTitle();
    }

    @Override
    public String getRowHead() {
        return getTitle();
    }
}
