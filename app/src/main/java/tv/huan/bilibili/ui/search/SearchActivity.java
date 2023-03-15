package tv.huan.bilibili.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import lib.kalu.frame.mvp.BaseActivity;
import lib.kalu.leanback.flexbox.FlexboxLayout;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.SearchBean;
import tv.huan.bilibili.utils.JumpUtil;
import tv.huan.keyboard.KeyboardLinearLayout;
import tv.huan.keyboard.listener.OnKeyboardInputListener;

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
        getPresenter().request(null);
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
                showInput(s);
                getPresenter().request(s);
            }
        });
    }

    @Override
    public void refreshContent() {
        notifyDataSetChanged(R.id.search_list);
    }

    @Override
    public void showTitle(@NonNull String s) {
        setText(R.id.keyboard_title, s);
    }

    @Override
    public void showInput(@NonNull String s) {
        setText(R.id.search_input, s);
    }

    @Override
    public void checkNodata(boolean show) {
        setVisibility(R.id.search_nodata, show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void updateKeys(@NonNull List<SearchBean.KeyBean> data) {
        FlexboxLayout flexboxLayout = findViewById(R.id.keyboard_tags);
        int childCount = flexboxLayout.getChildCount();
        if (childCount > 0)
            return;
        for (SearchBean.KeyBean t : data) {
            if (null == t)
                continue;
            TextView textView = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_search_tag, flexboxLayout, false);
            flexboxLayout.addView(textView);
            String name = t.getName();
            textView.setText(name);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JumpUtil.next(v.getContext(), t);
                }
            });
        }
    }

    @Override
    public void showKeys(boolean show) {
        setVisibility(R.id.keyboard_tags, show ? View.VISIBLE : View.GONE);
    }
}
