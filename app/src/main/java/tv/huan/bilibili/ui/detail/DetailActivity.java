package tv.huan.bilibili.ui.detail;


import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.leanback.widget.VerticalGridView;

import lib.kalu.frame.mvp.BaseActivity;
import tv.huan.bilibili.R;
import tv.huan.bilibili.dialog.InfoDialog;
import tv.huan.bilibili.ui.detail.template.DetailTemplatePlayer;
import tv.huan.bilibili.widget.DetailGridView;
import tv.huan.heilongjiang.HeilongjiangApi;

public class DetailActivity extends BaseActivity<DetailView, DetailPresenter> implements DetailView {

    public static final String INTENT_CID = "intent_cid";

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return getPresenter().dispatchEvent(event) || super.dispatchKeyEvent(event);
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

    @Override
    public void showDialog(@NonNull String title, @NonNull String data1, @NonNull String data2) {
        Bundle bundle = new Bundle();
        bundle.putString(InfoDialog.BUNDLE_NAME, title);
        bundle.putString(InfoDialog.BUNDLE_DATA1, data1);
        bundle.putString(InfoDialog.BUNDLE_DATA2, data2);
        InfoDialog dialog = new InfoDialog();
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public void cancleFavor(@NonNull String cid, @NonNull String recClassId) {
        getPresenter().cancleFavor(cid);
    }

    @Override
    public void addFavor(@NonNull String cid, @NonNull String recClassId) {
        getPresenter().addFavor(cid, recClassId);
    }

    @Override
    public void updateFavor(boolean status) {
        DetailGridView gridView = findViewById(R.id.detail_list);
        gridView.updateFavor(status);
    }

    @Override
    public void jumpVip() {
        HeilongjiangApi.jumpVip(getApplicationContext());
    }
}
