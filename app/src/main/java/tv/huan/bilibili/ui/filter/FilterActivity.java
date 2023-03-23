package tv.huan.bilibili.ui.filter;

import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lib.kalu.frame.mvp.BaseActivity;
import lib.kalu.leanback.clazz.ClassBean;
import lib.kalu.leanback.clazz.ClassScrollView;
import lib.kalu.leanback.clazz.OnCheckedChangeListener;
import lib.kalu.leanback.tags.TagsLayout;
import lib.kalu.leanback.tags.listener.OnTagsChangeListener;
import lib.kalu.leanback.tags.model.TagBean;
import tv.huan.bilibili.R;
import tv.huan.bilibili.utils.LogUtil;

/**
 * 筛选
 */
public class FilterActivity extends BaseActivity<FilterView, FilterPresenter> implements FilterView {

    public static final String INTENT_CLASSID = "intent_classid";
    public static final String INTENT_SECOND_TAG = "intent_second_tag";

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return getPresenter().dispatchEvent(event) || super.dispatchKeyEvent(event);
    }

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
    public void refreshClass(@NonNull List<ClassBean> classApis, @NonNull String className, @NonNull int checkedIndex) {
        setText(R.id.filter_title, className);
        ClassScrollView classLayout = findViewById(R.id.filter_class);
        classLayout.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onChecked(@NonNull boolean b, @NonNull int i, @NonNull String s, @NonNull String s1) {
                if (i == 0) {
                    getPresenter().searchAlbumByTypeNews(true);
                } else {
                    getPresenter().requestContent(true);
                }
            }
        });
        classLayout.update(classApis, checkedIndex);
    }

    @Override
    public void refreshTags(LinkedHashMap<String, List<TagBean>> filterTags) {
        TagsLayout tagsLayout = findViewById(R.id.filter_tags);
        tagsLayout.update(filterTags);
        tagsLayout.setOnTagsChangeListener(new OnTagsChangeListener() {
            @Override
            public void onChange(@NonNull int row, @NonNull int col, @NonNull Map<String, String> m) {
                getPresenter().searchAlbumByTypeNews(true);
            }
        });
    }

    @Override
    public void checkNodata(boolean hasData) {
        setVisibility(R.id.filter_nodata, hasData ? View.GONE : View.VISIBLE);
    }

    @Override
    public void checkTags() {
        ClassScrollView classLayout = findViewById(R.id.filter_class);
        int checkedIndex = classLayout.getCheckedIndex();
        setVisibility(R.id.filter_tags, checkedIndex == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void refreshContent(int start, int num) {
        notifyItemRangeInserted(R.id.filter_content, start, num);
    }

    @Override
    public void requestFocusList() {
        requestFocus(R.id.filter_content);
    }

    @Override
    public void cleanFocusClass() {
        ClassScrollView classLayout = findViewById(R.id.filter_class);
        classLayout.clearFocus();
    }
}