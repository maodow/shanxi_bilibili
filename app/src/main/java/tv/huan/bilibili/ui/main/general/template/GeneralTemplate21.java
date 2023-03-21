package tv.huan.bilibili.ui.main.general.template;


import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lib.kalu.frame.mvp.util.WrapperUtil;
import lib.kalu.leanback.presenter.ListTvRowPlusPresenter;
import lib.kalu.mediaplayer.config.start.StartBuilder;
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.ui.main.MainActivity;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;
import tv.huan.bilibili.widget.player.PlayerComponentInitTemplate21;
import tv.huan.bilibili.widget.player.PlayerView;
import tv.huan.bilibili.widget.player.PlayerViewTemplate21;

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
                public void onFocusChange(View v, boolean hasFocus) {

                    // content
                    if (hasFocus) {
                        try {
                            int position = viewHolder.getAbsoluteAdapterPosition();
                            if (position >= 0) {
                                GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = list.get(position);
                                updateContent(contentView, templateBean);
                            }
                        } catch (Exception e) {
                        }
                    } else {
                        stopPlayer(contentView);
                    }

                    // name
                    TextView textView = v.findViewById(R.id.common_poster_name);
                    textView.setEllipsize(hasFocus ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                }
            });
        } catch (Exception e) {
        }
        try {
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = viewHolder.getAbsoluteAdapterPosition();
                        if (position >= 0) {
                            GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = list.get(position);
                            JumpUtil.next(v.getContext(), templateBean);
                        }
                    } catch (Exception e) {
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    protected void onBindHolder(@NonNull View contentView, @NonNull View view1, @NonNull GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean, @NonNull int position, @NonNull int viewType) {

        if (position == 0) {
            boolean playerUrlNull = isPlayerUrlNull(contentView);
            if (playerUrlNull) {
                updateContent(contentView, templateBean);
            }
        }

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
    protected boolean canScrollHorizontally(int count) {
        return false;
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

    private void updateContent(View view, @NonNull GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean) {
        try {
            ImageView imageView = view.findViewById(R.id.general_template21_poster);
            imageView.setVisibility(templateBean.hasExtPoster() ? View.VISIBLE : View.GONE);
            GlideUtils.loadHz(imageView.getContext(), templateBean.getExtPoster(), imageView);
        } catch (Exception e) {
        }
        try {
            TextView textView = view.findViewById(R.id.general_template21_title);
            textView.setText(templateBean.getName());
        } catch (Exception e) {
        }
        try {
            TextView textView = view.findViewById(R.id.general_template21_cname);
            textView.setText(templateBean.getCname());
        } catch (Exception e) {
        }
        try {
            TextView textView = view.findViewById(R.id.general_template21_tag);
            textView.setText(templateBean.getTag());
        } catch (Exception e) {
        }
        try {
            TextView textView = view.findViewById(R.id.general_template21_score);
            textView.setText(templateBean.getScore());
        } catch (Exception e) {
        }
        try {
            TextView textView = view.findViewById(R.id.general_template21_info);
            textView.setText(templateBean.getDescription());
        } catch (Exception e) {
        }
        try {
            PlayerViewTemplate21 playerView = view.findViewById(R.id.general_template21_player);
            PlayerComponentInitTemplate21 component = playerView.findComponent(PlayerComponentInitTemplate21.class);
            if (null != component) {
                component.showImage(templateBean.getPicture(true));
            }
        } catch (Exception e) {
        }
        try {
            Activity activity = WrapperUtil.getWrapperActivity(view.getContext());
            if (null != activity && activity instanceof MainActivity) {
                ((MainActivity) activity).huaweiAuth(GeneralTemplate21.class, GeneralTemplate21.GeneralTemplate21List.class, templateBean.getHuaweiId());
            }
        } catch (Exception e) {
        }
    }

    private boolean isPlayerUrlNull(View v) {
        try {
            PlayerView playerView = v.findViewById(R.id.general_template21_player);
            String url = playerView.getUrl();
            return null == url || url.length() <= 0;
        } catch (Exception e) {
            return true;
        }
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
        try {
            PlayerView playerView = inflate.findViewById(R.id.general_template21_player);
            playerView.stop();
            playerView.release();
        } catch (Exception e) {
        }
    }

    public void pausePlayer(ViewGroup viewGroup) {
        try {
            PlayerView playerView = viewGroup.findViewById(R.id.general_template21_player);
            playerView.pause();
        } catch (Exception e) {
        }
    }

    public void resumePlayer(ViewGroup viewGroup) {
        try {
            PlayerView playerView = viewGroup.findViewById(R.id.general_template21_player);
            playerView.resume();
        } catch (Exception e) {
        }
    }

    public void startPlayer(View inflate, String s) {
        try {
            if (null == s || s.length() <= 0)
                throw new Exception("url error: null");
            PlayerView playerView = inflate.findViewById(R.id.general_template21_player);
            StartBuilder.Builder builder = new StartBuilder.Builder();
            builder.setLoop(true);
            playerView.start(builder.build(), s);
        } catch (Exception e) {
        }
    }

    public static class GeneralTemplate21List extends ArrayList {
    }
}