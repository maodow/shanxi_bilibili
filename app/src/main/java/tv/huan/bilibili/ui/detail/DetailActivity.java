package tv.huan.bilibili.ui.detail;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import lib.kalu.frame.mvp.BaseActivity;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.MediaBean;
import tv.huan.bilibili.dialog.InfoDialog;
import tv.huan.bilibili.utils.LogUtil;
import tv.huan.bilibili.widget.DetailGridView;

public class DetailActivity extends BaseActivity<DetailView, DetailPresenter> implements DetailView {

    public static final String INTENT_POSITION = "intent_position";
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
    protected static final String INTENT_REC_CLASSID = "intent_rec_classid";
    protected static final String INTENT_START_TIME = "intent_start_time";

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return getPresenter().dispatchEvent(event) || super.dispatchKeyEvent(event);
    }

    @Override
    public void onBackPressed() {
        getPresenter().uploadBackupPress();
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
    public void updateVidAndClassId(@NonNull MediaBean data) {
        putStringExtra(INTENT_REC_CLASSID, data.getTempRecClassId());
        putIntExtra(INTENT_POSITION, data.getTempIndex());
        putStringExtra(INTENT_VID, data.getVid());
    }

    @Override
    public void updateData(@NonNull MediaBean data, boolean isFromUser) {
        DetailGridView gridView = findViewById(R.id.detail_list);
        gridView.updatePlayer(data);
        if (!isFromUser) {
            gridView.updatePlayerPosition(data);
        }
    }

    @Override
    public void delayPlayer(@NonNull MediaBean data, boolean isFromUser) {
        getPresenter().delayPlayer(data, isFromUser);
    }

    @Override
    public void startPlayer(@NonNull MediaBean data, boolean isFromUser) {
        DetailGridView gridView = findViewById(R.id.detail_list);
        gridView.startPlayer(data, isFromUser);
    }

    @Override
    public void startPlayerNext1() {
        DetailGridView gridView = findViewById(R.id.detail_list);
        gridView.startNext1();
    }

    @Override
    public void stopPlayer() {
        DetailGridView gridView = findViewById(R.id.detail_list);
        gridView.stopPlayer();
    }

    @Override
    public void completePlayer() {
        getPresenter().checkPlayerNext();
    }

    @Override
    public void jumpVip() {
//        HeilongjiangApi.jumpVip(getApplicationContext());
    }

    @Override
    public void stopFull() {
        DetailGridView gridView = findViewById(R.id.detail_list);
        gridView.stopFull();
    }
}
