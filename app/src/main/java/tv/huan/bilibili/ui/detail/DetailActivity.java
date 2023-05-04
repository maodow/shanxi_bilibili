package tv.huan.bilibili.ui.detail;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import lib.kalu.frame.mvp.BaseActivity;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.MediaBean;
import tv.huan.bilibili.dialog.InfoDialog;
import tv.huan.bilibili.widget.DetailGridView;
import tv.huan.heilongjiang.HeilongjiangUtil;

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
    protected static final String INTENT_VID = "intent_vid";
    protected static final String INTENT_INDEX = "intent_index";
    protected static final String INTENT_REC_CLASSID = "intent_rec_classid";
    protected static final String INTENT_START_TIME = "intent_start_time";
    protected static final String INTENT_CUR_POSITION = "intent_cur_position";
    protected static final String INTENT_CUR_DURATION = "intent_cur_duration";

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return getPresenter().dispatchEvent(event) || super.dispatchKeyEvent(event);
    }

    @Override
    public void onBackPressed() {
        getPresenter().onBackPressed();
    }

    @Override
    public void finish() {
        getPresenter().releasePlayer();
        super.finish();
    }

    @Override
    public int initLayout() {
        putLongExtra(INTENT_START_TIME, System.currentTimeMillis());
        return R.layout.activity_detail;
    }

    @Override
    public void initData() {
        getPresenter().setAdapter();
        getPresenter().request();
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
    public void updatePlayerPosition(@NonNull MediaBean data) {
        getPresenter().releasePlayer();
        DetailGridView gridView = findViewById(R.id.detail_list);
        gridView.updatePlayerPosition(data);
    }

    @Override
    public boolean isPlayerPlayingPosition(@NonNull int position) {
        DetailGridView gridView = findViewById(R.id.detail_list);
        return gridView.isPlayerPlayingPosition(position);
    }

    @Override
    public void startPlayerPosition(@NonNull int position) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                DetailGridView gridView = findViewById(R.id.detail_list);
                gridView.startPlayerPosition(position);
            }
        }, 1000);
    }

    @Override
    public void startPlayerPosition(@NonNull MediaBean data) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                DetailGridView gridView = findViewById(R.id.detail_list);
                gridView.startPlayerPosition(data, 0, 0, false);
            }
        }, 1000);
    }

    @Override
    public void startPlayerPosition(@NonNull MediaBean data, @NonNull int pos, @NonNull long seek, boolean isFromUser) {
        putStringExtra(INTENT_VID, data.getVid());
        putStringExtra(INTENT_REC_CLASSID, data.getTempRecClassId());
        putIntExtra(INTENT_INDEX, pos);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                DetailGridView gridView = findViewById(R.id.detail_list);
                gridView.startPlayerPosition(data, pos, seek, isFromUser);
            }
        }, 1000);
    }

    @Override
    public void jumpVip() {
        Toast.makeText(getApplicationContext(), "观看当前视频, 需要开通会员, 即将跳转订购页面", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                HeilongjiangUtil.goShopping_WorkerThread(getApplicationContext());
            }
        }, 2000);
    }

    @Override
    public void huaweiAuth(String movieCode, long seek) {
        getPresenter().requestHuaweiAuth(movieCode, seek);
    }

    @Override
    public void huaweiSucc(String s, long seek) {
        DetailGridView gridView = findViewById(R.id.detail_list);
        gridView.startPlayer(s, seek);
    }

//    @Override
//    public int getPlayerNextPosition() {
//        return getPresenter().getPlayerNextPosition();
//    }

    @Override
    public void startFull() {
        DetailGridView gridView = findViewById(R.id.detail_list);
        gridView.startFull();
    }
}
