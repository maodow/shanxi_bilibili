package tv.huan.bilibili.ui.detail;


import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.leanback.widget.VerticalGridView;

import lib.kalu.frame.mvp.BaseActivity;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.Media;
import tv.huan.bilibili.dialog.InfoDialog;
import tv.huan.bilibili.ui.detail.template.DetailTemplatePlayer;
import tv.huan.bilibili.utils.TimeUtils;
import tv.huan.bilibili.widget.DetailGridView;
import tv.huan.heilongjiang.HeilongjiangApi;

public class DetailActivity extends BaseActivity<DetailView, DetailPresenter> implements DetailView {

    public static final String INTENT_CID = "intent_cid";
    public static final String INTENT_FROM_SEARCH = "intent_from_search";
    public static final String INTENT_FROM_SEARCH_KEY = "intent_from_search_key";
    public static final String INTENT_FROM_WANLIU = "intent_from_wanliu";
    public static final String INTENT_FROM_WANLIU_KEY = "intent_from_wanliu_key";
    public static final String INTENT_FROM_SPECIAL = "intent_from_special";
    public static final String INTENT_FROM_SPECIAL_SCENEID = "intent_from_special_sceneid";
    public static final String INTENT_FROM_SPECIAL_TOPID = "intent_from_special_topid";
    public static final String INTENT_FROM_SPECIAL_TOPNAME = "intent_from_special_topname";
    private static final String INTENT_UPDATE = "intent_update";
    protected static final String INTENT_VID = "intent_vid";
    protected static final String INTENT_START_TIME = "intent_start_time";

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return getPresenter().dispatchEvent(event) || super.dispatchKeyEvent(event);
    }

    @Override
    public void finish() {
        boolean extra = getBooleanExtra(INTENT_UPDATE, false);
        if (extra) {
            setResult(2002);
        }
        stopPlayer();
        super.finish();
    }

    @Override
    public int initLayout() {
        putLongExtra(INTENT_START_TIME, System.currentTimeMillis());
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
        putBooleanExtra(INTENT_UPDATE, true);
        DetailGridView gridView = findViewById(R.id.detail_list);
        gridView.updateFavor(status);
    }

    @Override
    public void jumpVip() {
        HeilongjiangApi.jumpVip(getApplicationContext());
    }

    @Override
    public void delayPlayer(int position) {
        getPresenter().delayPlayer(position);
    }

    @Override
    public void startPlayer(int position) {
        DetailGridView gridView = findViewById(R.id.detail_list);
        gridView.startPlayer(position);
    }

    @Override
    public void stopPlayer() {
        DetailGridView gridView = findViewById(R.id.detail_list);
        gridView.stopPlayer();
    }

    @Override
    public void updatePlayerInfo(int position, String vid) {
        updateVid(vid);
        DetailGridView gridView = findViewById(R.id.detail_list);
        gridView.nextPlayer(position);
    }

    @Override
    public void updateVid(String vid) {
        putStringExtra(INTENT_VID, vid);
    }

}
