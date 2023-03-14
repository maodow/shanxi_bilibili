package tv.huan.bilibili.ui.main.general.template;


import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lib.kalu.leanback.presenter.ListTvGridPresenter;
import lib.kalu.mediaplayer.config.start.StartBuilder;
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.widget.player.PlayerView;

public final class GeneralTemplate22 extends ListTvGridPresenter<GetSubChannelsByChannelBean.ListBean.TemplateBean> {

    @Override
    public String initRowTitle(Context context) {
        if (BuildConfig.HUAN_TEST_TEMPLATE_ENABLE) {
            return "模板22";
        } else {
            return super.initRowTitle(context);
        }
    }

    @Override
    public void initItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        int offset = view.getResources().getDimensionPixelOffset(R.dimen.dp_72) / 8;
        if (position == 0) {
            int margin = view.getResources().getDimensionPixelOffset(R.dimen.dp_24);
            outRect.set(0, 0, 0, margin);
        } else if (position == 1) {
            outRect.set(0, 0, offset * 2, 0);
        } else if (position == 4) {
            outRect.set(offset * 2, 0, 0, 0);
        } else {
            outRect.set(offset, 0, offset, 0);
        }

        int transX = offset * 2 / (3 * 2);
        if (position == 2) {
            view.setTranslationX(-transX);
        } else if (position == 3) {
            view.setTranslationX(transX);
        }
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
    protected void onCreateHolder(@NonNull Context context, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list, @NonNull int viewType) {
        // img
        if (viewType == 2) {
            try {
                view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        TextView textView = view.findViewById(R.id.common_poster_name);
                        textView.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                    }
                });
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void onBindHolder(@NonNull View view, @NonNull GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean, @NonNull int position, @NonNull int viewType) {

        // player
        if (viewType == 1) {
            startPlayer(view);
        }
        // img
        else if (viewType == 2) {
            try {
                TextView textView = view.findViewById(R.id.common_poster_name);
                textView.setText(templateBean.getName());
            } catch (Exception e) {
            }
            try {
                ImageView imageView = view.findViewById(R.id.common_poster_img);
                GlideUtils.loadHz(imageView.getContext(), templateBean.getPicture(true), imageView);
            } catch (Exception e) {
            }
            try {
                ImageView imageView = view.findViewById(R.id.common_poster_vip);
                GlideUtils.loadVt(imageView.getContext(), templateBean.getVipUrl(), imageView);
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected int initSpanSize(int position) {
        return position == 0 ? 4 : 1;
    }

    @Override
    protected int initItemViewType(int position, GetSubChannelsByChannelBean.ListBean.TemplateBean data) {
        if (position == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    protected int initLayout(int viewType) {
        if (viewType == 1) {
            return R.layout.fragment_general_item_template22a;
        } else {
            return R.layout.fragment_general_item_template22b;
        }
    }

    @Override
    protected int initSpan() {
        return 4;
    }

    @Override
    protected int initMax() {
        return 5;
    }

    private void pasuePlayer(View inflate) {

        if (null == inflate)
            return;

        PlayerView playerView = inflate.findViewById(R.id.general_template22_player);
        playerView.pause();
    }

    private void resumePlayer(View inflate) {

        if (null == inflate)
            return;

        PlayerView playerView = inflate.findViewById(R.id.general_template22_player);
        playerView.resume();
    }

    private void startPlayer(View inflate) {

        if (null == inflate)
            return;
        PlayerView playerView = inflate.findViewById(R.id.general_template22_player);
        String url = "http://39.134.19.248:6610/yinhe/2/ch00000090990000001335/index.m3u8?virtualDomain=yinhe.live_hls.zte.com";
        StartBuilder.Builder builder = new StartBuilder.Builder();
        builder.setLoop(true);
        builder.setDelay(3000);
        playerView.start(builder.build(), url);
    }

    public static class GeneralTemplate22List extends ArrayList {
    }
}