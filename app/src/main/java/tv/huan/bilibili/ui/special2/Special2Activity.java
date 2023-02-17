package tv.huan.bilibili.ui.special2;

import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lib.kalu.frame.mvp.BaseActivity;
import tv.huan.bilibili.widget.player.PlayerView;
import tv.huan.bilibili.R;
import tv.huan.bilibili.utils.GlideUtils;

public class Special2Activity extends BaseActivity<Special2View, Special2Presenter> implements Special2View {

    public static final String INTENT_SPECIALID = "intent_specialid";

    @Override
    public int initLayout() {
        return R.layout.activity_special2;
    }

    @Override
    public void initData() {
        // adapter
        getPresenter().setAdapter();
        // request
        getPresenter().request();
    }

    @Override
    public void refreshCards() {
        notifyDataSetChanged(R.id.special2_list);
    }

    @Override
    public void refreshBackground(@NonNull String url) {
        ImageView imageView = findViewById(R.id.special2_img);
        GlideUtils.loadNot(getContext(), url, imageView);
    }

    @Override
    public void startPlayer(@NonNull String url, @NonNull String name, @NonNull String info) {
        setText(R.id.special2_name, name);
        setText(R.id.special2_info, info);
        PlayerView videoLayout = findViewById(R.id.special2_player);
        videoLayout.start(url);
    }

    @Override
    public void requestDefault() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RecyclerView recyclerView = findViewById(R.id.special2_list);
                recyclerView.getLayoutManager().findViewByPosition(0).requestFocus();
            }
        }, 40);
    }
}
