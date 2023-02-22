package tv.huan.bilibili.ui.special5;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import lib.kalu.frame.mvp.BaseActivity;
import tv.huan.bilibili.R;
import tv.huan.bilibili.utils.GlideUtils;

public class Special5Activity extends BaseActivity<Special5View, Special5Presenter> implements Special5View {

    public static final String INTENT_SPECIALID = "intent_specialid";

    @Override
    public int initLayout() {
        return R.layout.activity_special5;
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
        notifyDataSetChanged(R.id.special5_list);
        ImageView imageView = findViewById(R.id.special5_img);
        GlideUtils.load(getContext(), str, imageView);
    }
}
