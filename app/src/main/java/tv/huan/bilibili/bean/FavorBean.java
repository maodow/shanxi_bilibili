package tv.huan.bilibili.bean;

import java.io.Serializable;

public class FavorBean implements Serializable {

    private int optCode;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOptCode() {
        return optCode;
    }

    public void setOptCode(int optCode) {
        this.optCode = optCode;
    }

    public boolean isFavor() {
        return "已收藏".equals(description);
    }
}
