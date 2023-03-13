package tv.huan.bilibili.ui.welcome;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import lib.kalu.frame.mvp.BaseActivity;
import tv.huan.bilibili.bean.GetChannelsBean;
import tv.huan.bilibili.ui.main.MainActivity;
import tv.huan.bilibili.R;
import tv.huan.bilibili.utils.GlideUtils;

public class WelcomeActivity extends BaseActivity<WelcomeView, WelcomePresenter> implements WelcomeView {

    public static final String INTENT_SELECT = "intent_select";
    public static final String INTENT_TYPE = "intent_type";
    public static final String INTENT_SECOND_TAG = "intent_second_tag";
    public static final String INTENT_CID = "intent_cid";
    public static final String INTENT_CLASSID = "intent_classid";

    @Override
    public int initLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initData() {
        // request
        getPresenter().request();
    }

    @Override
    public void refreshAD(String backgroundUrl) {
        setVisibility(R.id.welcome_img, View.VISIBLE);
        ImageView imageView = findViewById(R.id.welcome_img);
        GlideUtils.load(getContext(), backgroundUrl, imageView);
    }

    @Override
    public void refreshTime(String str) {
        setVisibility(R.id.welcome_time, View.VISIBLE);
        setText(R.id.welcome_time, str);
    }

    @Override
    public void next() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(MainActivity.INTENT_ENABLE, false);
        startActivity(intent);
        finish();
    }

    @Override
    public void next(@NonNull String data, @NonNull int select, @NonNull int type, @NonNull String cid, @NonNull int classId, @NonNull String secondTag) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(MainActivity.INTENT_SELECT, select);
        intent.putExtra(MainActivity.INTENT_TABS, data);
        intent.putExtra(MainActivity.INTENT_TYPE, type);
        intent.putExtra(MainActivity.INTENT_CID, cid);
        intent.putExtra(MainActivity.INTENT_CLASSID, classId);
        intent.putExtra(MainActivity.INTENT_SECOND_TAG, secondTag);
        startActivity(intent);
        finish();
    }
}

