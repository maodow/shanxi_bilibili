package tv.huan.bilibili.ui.detail;


import android.view.KeyEvent;

import androidx.leanback.widget.VerticalGridView;

import lib.kalu.frame.mvp.BaseActivity;
import tv.huan.bilibili.R;
import tv.huan.bilibili.ui.detail.template.DetailTemplatePlayer;

public class DetailActivity extends BaseActivity<DetailView, DetailPresenter> implements DetailView {

    public static final String INTENT_CID = "intent_cid";

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            boolean checkPlayer = getPresenter().checkPlayer();
            if (checkPlayer) {
                onBackPressed();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public void initData() {
        // adapter
        getPresenter().setAdapter();
        // request
        getPresenter().request();
        // look
        getPresenter().refreshLook();
    }

    @Override
    public void refreshContent() {
        notifyDataSetChanged(R.id.detail_list);
    }

    @Override
    public void updateVip(boolean show) {
        try {
            VerticalGridView gridView = findViewById(R.id.detail_list);
            DetailTemplatePlayer.DetailTemplatePlayerObject adapter = gridView.getAdapter(0);
            if (null != adapter) {
                adapter.setShowVip(show);
                gridView.smoothScrollToPosition(0);
                gridView.notifyItemChanged(0);
            }
        } catch (Exception e) {
        }
    }
}
