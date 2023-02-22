package tv.huan.bilibili.ui.special6;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import lib.kalu.frame.mvp.BaseActivity;
import tv.huan.bilibili.R;
import tv.huan.bilibili.utils.GlideUtils;

public class Special6Activity extends BaseActivity<Special6View, Special6Presenter> implements Special6View {

    public static final String INTENT_SPECIALID = "intent_specialid";

    @Override
    public int initLayout() {
        return R.layout.activity_special6;
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
        notifyDataSetChanged(R.id.special6_list);
        ImageView imageView = findViewById(R.id.special6_img);
        GlideUtils.load(getContext(), str, imageView);
    }
}
