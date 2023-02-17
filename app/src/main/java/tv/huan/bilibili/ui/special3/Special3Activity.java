package tv.huan.bilibili.ui.special3;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import lib.kalu.frame.mvp.BaseActivity;
import tv.huan.bilibili.R;
import tv.huan.bilibili.utils.GlideUtils;

public class Special3Activity extends BaseActivity<Special3View, Special3Presenter> implements Special3View {

    public static final String INTENT_SPECIALID = "intent_specialid";

    @Override
    public int initLayout() {
        return R.layout.activity_special3;
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
        notifyDataSetChanged(R.id.special3_list);
        ImageView imageView = findViewById(R.id.special3_img);
        GlideUtils.loadNot(getContext(), str, imageView);
    }
}
