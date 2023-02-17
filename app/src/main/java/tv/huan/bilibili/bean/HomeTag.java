package tv.huan.bilibili.bean;

import java.io.Serializable;

public class HomeTag implements Serializable {

    private static final long serialVersionUID = -8655538652626397489L;

    private int id ;
    private int productId ;
    private String name ;
    private int parentId ;
    private int hasNext ;
    private int pos ;
    private int status ;


    @Override
    public String toString() {
        return "HomeTag{" +
                "id=" + id +
                ", productId=" + productId +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", hasNext=" + hasNext +
                ", pos=" + pos +
                ", status=" + status +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getHasNext() {
        return hasNext;
    }

    public void setHasNext(int hasNext) {
        this.hasNext = hasNext;
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
}
