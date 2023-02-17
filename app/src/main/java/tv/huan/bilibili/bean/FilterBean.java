package tv.huan.bilibili.bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import lib.kalu.leanback.clazz.ClassBean;
import lib.kalu.leanback.tags.model.TagBean;

public final class FilterBean implements Serializable {

    private int filterId;
    private String filterTitle;
//    private List<GetSubChannelsByChannelBean.ClassesBean> filterDatas;
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

    public int getFilterId() {
        return filterId;
    }

    public void setFilterId(int filterId) {
        this.filterId = filterId;
    }

    public String getFilterTitle() {
        return filterTitle;
    }

    public void setFilterTitle(String filterTitle) {
        this.filterTitle = filterTitle;
    }
//
//    public List<GetSubChannelsByChannelBean.ClassesBean> getFilterDatas() {
//        return filterDatas;
//    }
//
//    public void setFilterDatas(List<GetSubChannelsByChannelBean.ClassesBean> filterDatas) {
//        this.filterDatas = filterDatas;
//    }
}
