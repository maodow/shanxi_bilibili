package tv.huan.bilibili.ui.special4;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import lib.kalu.frame.mvp.BaseActivity;
import tv.huan.bilibili.R;
import tv.huan.bilibili.utils.GlideUtils;

public class Special4Activity extends BaseActivity<Special4View, Special4Presenter> implements Special4View {

    public static final String INTENT_SPECIALID = "intent_specialid";

    @Override
    public int initLayout() {
        return R.layout.activity_special4;
    }

    @Override
    public void initData() {
        // adapter
        getPresenter().setAdapter();
        // request
        getPresenter().request();
    }

    @Override
    public void refreshContent(@NonNull String str) {
        notifyDataSetChanged(R.id.special4_list);
        ImageView imageView = findViewById(R.id.special4_img);
        GlideUtils.load(getContext(), str, imageView);
    }
}
