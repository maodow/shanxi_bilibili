package tv.huan.bilibili.ui.main.general.template;


import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lib.kalu.leanback.presenter.ListTvRowPlusPresenter;
import lib.kalu.mediaplayer.config.start.StartBuilder;
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.widget.player.PlayerView;

public class GeneralTemplate21 extends ListTvRowPlusPresenter<GetSubChannelsByChannelBean.ListBean.TemplateBean> {

    @Override
    public String initRowTitle(Context context) {
        if (BuildConfig.HUAN_TEST_TEMPLATE_ENABLE) {
            return "模板20";
        } else {
            return super.initRowTitle(context);
        }
    }

    @Override
    public void initItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int margin = view.getResources().getDimensionPixelOffset(R.dimen.dp_24);
        outRect.set(0, 0, margin, 0);
    }

    @Override
    public int initPaddingBottom(@NonNull Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.dp_40);
    }

    @Override
    public int initTitlePaddingBottom(@NonNull Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.dp_12);
    }

    @Override
    protected void onCreateHolder(@NonNull Context context, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View contentView, @NonNull View view1, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list) {
        try {
            view1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean b) {
                    // 1
                    TextView textView = v.findViewById(R.id.common_poster_name);
                    textView.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);

                    // 2
                    if (b) {
                        try {
                            int position = viewHolder.getAbsoluteAdapterPosition();
                            if (position >= 0) {
                                GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = list.get(position);
                                ImageView imageView = contentView.findViewById(R.id.general_template21_img);
                                GlideUtils.loadHz(imageView.getContext(), templateBean.getPicture(true), imageView);
                            }
                        } catch (Exception e) {
                        }
                    } else {
                        switchContentUI(true, contentView);
                    }

                    // 3
                    if (b) {
                        stopPlayer(contentView);
                        startPlayer(contentView);
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    protected void onBindHolder(@NonNull View view, @NonNull View view1, @NonNull GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean, @NonNull int i, @NonNull int i1) {
        try {
            TextView textView = view1.findViewById(R.id.common_poster_name);
            textView.setText(templateBean.getName());
        } catch (Exception e) {
        }
        try {
            ImageView imageView = view1.findViewById(R.id.common_poster_img);
            GlideUtils.loadHz(imageView.getContext(), templateBean.getPicture(true), imageView);
        } catch (Exception e) {
        }
        try {
            ImageView imageView = view1.findViewById(R.id.common_poster_vip);
            GlideUtils.loadVt(imageView.getContext(), templateBean.getVipUrl(), imageView);
        } catch (Exception e) {
        }
    }

    @Override
    protected int initLayout(int i) {
        return R.layout.fragment_general_item_template21b;
    }

    @Override
    protected int initContent() {
        return R.layout.fragment_general_item_template21a;
    }

    @Override
    protected int initContentMarginBottom(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.dp_24);
    }

    private void pasuePlayer(View inflate) {

        if (null == inflate)
            return;

        PlayerView playerView = inflate.findViewById(R.id.general_template21_player);
        playerView.pause();
    }

    private void resumePlayer(View inflate) {

        if (null == inflate)
            return;

        PlayerView playerView = inflate.findViewById(R.id.general_template21_player);
        playerView.resume();
    }

    private void stopPlayer(View inflate) {

        if (null == inflate)
            return;

        PlayerView playerView = inflate.findViewById(R.id.general_template21_player);
        playerView.stop();
        playerView.release();
    }

    private void startPlayer(View inflate) {

        if (null == inflate)
            return;

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                switchContentUI(false, inflate);
            }
        }, 3000);

        PlayerView playerView = inflate.findViewById(R.id.general_template21_player);
        String url = "http://39.134.19.248:6610/yinhe/2/ch00000090990000001335/index.m3u8?virtualDomain=yinhe.live_hls.zte.com";
        StartBuilder.Builder builder = new StartBuilder.Builder();
        builder.setLoop(true);
        builder.setDelay(3000);
        playerView.start(builder.build(), url);
    }

    private void switchContentUI(boolean reset, View inflate) {
        try {
            View byId = inflate.findViewById(R.id.general_template21_img);
            byId.setVisibility(reset ? View.VISIBLE : View.GONE);
        } catch (Exception e) {
        }
        try {
            View byId = inflate.findViewById(R.id.general_template21_player);
            byId.setVisibility(reset ? View.GONE : View.VISIBLE);
        } catch (Exception e) {
        }
    }

    public static class GeneralTemplate21List extends ArrayList {
    }
}