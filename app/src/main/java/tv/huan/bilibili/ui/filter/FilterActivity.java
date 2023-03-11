package tv.huan.bilibili.ui.filter;

import android.view.View;

import androidx.annotation.NonNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lib.kalu.frame.mvp.BaseActivity;
import lib.kalu.leanback.clazz.ClassBean;
import lib.kalu.leanback.clazz.VerticalClassLayout;
import lib.kalu.leanback.list.RecyclerView;
import lib.kalu.leanback.tags.TagsLayout;
import lib.kalu.leanback.tags.listener.OnTagsChangeListener;
import lib.kalu.leanback.tags.model.TagBean;
import tv.huan.bilibili.R;

/**
 * 筛选
 */
public class FilterActivity extends BaseActivity<FilterView, FilterPresenter> implements FilterView {

    public static final String INTENT_CLASSID = "intent_classid";
    public static final String INTENT_SECOND_TAG = "intent_second_tag";

    @Override
    public int initLayout() {
        return R.layout.activity_filter;
    }

    @Override
    public void initData() {
        // adapter
        getPresenter().setAdapter();
        // request
        getPresenter().request();
    }

    @Override
    public void refreshClass(@NonNull List<ClassBean> classApis, @NonNull String className) {
        setText(R.id.filter_title, className);
        VerticalClassLayout classLayout = findViewById(R.id.filter_second);
        classLayout.update(classApis, false);
        classLayout.setOnCheckedChangeListener(new lib.kalu.leanback.clazz.VerticalClassLayout.OnCheckedChangeListener() {
            @Override
            public void onChecked(@NonNull int i, @NonNull String s, @NonNull String s1) {
                checkTags(i == 0);
                if (i == 0) {
                    getPresenter().searchAlbumByTypeNews();
                } else {
                    getPresenter().requestContent(s1);
                }
            }
        });
    }

    @Override
    public void refreshContent(boolean hasFocus) {
        RecyclerView recyclerView = findViewById(R.id.filter_content);
        recyclerView.getAdapter().notifyDataSetChanged();
        if (!hasFocus)
            return;
        recyclerView.requestFocus();
    }

    @Override
    public void refreshTags(LinkedHashMap<String, List<TagBean>> filterTags) {
        TagsLayout tagsLayout = findViewById(R.id.filter_tags);
        tagsLayout.update(filterTags);
        tagsLayout.setOnTagsChangeListener(new OnTagsChangeListener() {
            @Override
            public void onChange(@NonNull int row, @NonNull int col, @NonNull Map<String, String> m) {
                getPresenter().searchAlbumByTypeNews();
            }
        });
    }

    @Override
    public void checkNodata(boolean show) {
        setVisibility(R.id.filter_nodata, show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void checkTags(boolean show) {
        setVisibility(R.id.filter_tags, show ? View.VISIBLE : View.GONE);
    }
}