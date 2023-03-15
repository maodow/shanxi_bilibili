package tv.huan.bilibili.bean.format;

import java.io.Serializable;
import java.util.List;

import tv.huan.bilibili.bean.SearchBean;

public class CallSearchBean implements Serializable {

    private String title;
    private List<tv.huan.bilibili.bean.SearchBean.KeyBean> tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SearchBean.KeyBean> getTags() {
        return tags;
    }

    public void setTags(List<SearchBean.KeyBean> tags) {
        this.tags = tags;
    }
}
