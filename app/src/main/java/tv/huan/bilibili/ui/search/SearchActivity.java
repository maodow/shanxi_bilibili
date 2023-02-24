package tv.huan.bilibili.ui.search;

import android.view.View;

import tv.huan.keyboard.KeyboardLinearLayout;
import tv.huan.keyboard.listener.OnKeyboardInputListener;

import lib.kalu.frame.mvp.BaseActivity;
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
        findViewById(R.id.search_clean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardLinearLayout layout = findViewById(R.id.search_keyboard);
                layout.clear();
            }
        });
        findViewById(R.id.search_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardLinearLayout layout = findViewById(R.id.search_keyboard);
                layout.delete();
            }
        });
        KeyboardLinearLayout keyboardView = findViewById(R.id.search_keyboard);
        keyboardView.setOnKeyboardInputListener(new OnKeyboardInputListener() {
            @Override
            public void onInput(String s) {
                setText(R.id.search_input, s);
                getPresenter().searchBySpell();
            }
        });
    }

    @Override
    public void refreshContent() {
        notifyDataSetChanged(R.id.search_list);
    }
}
