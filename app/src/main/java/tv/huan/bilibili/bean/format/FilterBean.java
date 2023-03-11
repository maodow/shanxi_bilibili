package tv.huan.bilibili.bean.format;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import lib.kalu.leanback.clazz.ClassBean;
import lib.kalu.leanback.tags.model.TagBean;
import tv.huan.bilibili.bean.base.BaseDataBean;

public final class FilterBean extends BaseDataBean implements Serializable {

    private int filterCheckPosition;
    private String filterTitle;
    private List<ClassBean> filterClass;
    private LinkedHashMap<String, List<TagBean>> filterTags;

    public LinkedHashMap<String, List<TagBean>> getFilterTags() {
        return filterTags;
    }

    public void setFilterTags(LinkedHashMap<String, List<TagBean>> filterTags) {
        this.filterTags = filterTags;
    }

    public List<ClassBean> getFilterClass() {
        return filterClass;
    }

    public void setFilterClass(List<ClassBean> filterClass) {
        this.filterClass = filterClass;
    }

    public String getFilterTitle() {
        return filterTitle;
    }

    public void setFilterTitle(String filterTitle) {
        this.filterTitle = filterTitle;
    }

    public int getFilterCheckPosition() {
        return filterCheckPosition;
    }

    public void setFilterCheckPosition(int filterCheckPosition) {
        this.filterCheckPosition = filterCheckPosition;
    }
}
