package tv.huan.bilibili.ui.search;

import lib.kalu.frame.mvp.BaseActivity;
import tv.huan.bilibili.utils.BoxUtil;
import tv.huan.bilibili.widget.keyboard.KeyboardView;
import tv.huan.bilibili.R;

/**
 * 搜索
 */
public class SearchActivity extends BaseActivity<SearchView, SearchPresenter> implements SearchView {
    @Override
    public int initLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void initData() {
        // adapter
        getPresenter().setAdapter();
        // request
        getPresenter().getSearchRecommend();
        // listener
        KeyboardView keyboardView = findViewById(R.id.search_keyboard);
        keyboardView.setProdId(BoxUtil.getProdId());
        keyboardView.setOnSearchListener(new KeyboardView.OnSearchListener() {
            @Override
            public void onSearchKeyResult(String result) {
                getPresenter().searchBySpell();
            }
        });
    }

    @Override
    public void refreshContent() {
        notifyDataSetChanged(R.id.search_list);
    }
}
