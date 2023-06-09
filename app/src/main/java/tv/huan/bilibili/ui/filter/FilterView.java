package tv.huan.bilibili.ui.filter;

import androidx.annotation.NonNull;

import java.util.LinkedHashMap;
import java.util.List;

import lib.kalu.leanback.clazz.ClassBean;
import lib.kalu.leanback.tags.model.TagBean;
import tv.huan.bilibili.base.BaseViewImpl;

public interface FilterView extends BaseViewImpl {

    void refreshClass(@NonNull List<ClassBean> classApis, @NonNull String className, @NonNull int checkedIndex);

    void refreshTags(LinkedHashMap<String, List<TagBean>> filterTags);

    void checkTags();

    void cleanFocusClass();
}
