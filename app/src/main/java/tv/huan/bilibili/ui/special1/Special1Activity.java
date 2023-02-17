package tv.huan.bilibili.ui.special1;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import lib.kalu.frame.mvp.BaseActivity;
import tv.huan.bilibili.R;
import tv.huan.bilibili.utils.GlideUtils;

public class Special1Activity extends BaseActivity<Special1View, Special1Presenter> implements Special1View {

    public static final String INTENT_SPECIALID = "intent_specialid";

    @Override
    public int initLayout() {
        return R.layout.activity_special1;
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
        notifyDataSetChanged(R.id.special1_list);
        ImageView imageView = findViewById(R.id.special1_img);
        GlideUtils.loadNot(getContext(), str, imageView);
    }
}
