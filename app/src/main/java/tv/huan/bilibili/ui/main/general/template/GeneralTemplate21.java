package tv.huan.bilibili.ui.main.general.template;


import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lib.kalu.frame.mvp.util.WrapperUtil;
import lib.kalu.leanback.presenter.ListTvRowHeadPresenter;
import lib.kalu.mediaplayer.config.start.StartBuilder;
import lib.kalu.mediaplayer.widget.player.PlayerLayout;
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.ui.main.MainActivity;
import tv.huan.bilibili.utils.BoxUtil;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;
import tv.huan.bilibili.utils.LogUtil;
import tv.huan.bilibili.widget.player.component.PlayerComponentInitTemplate21;

public class GeneralTemplate21 extends ListTvRowHeadPresenter<GetSubChannelsByChannelBean.ListBean.TemplateBean> {

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 20001) {
                try {
                    View viewGroup = (View) ((Object[]) msg.obj)[0];
                    String code = (String) ((Object[]) msg.obj)[1];
                    startHuawei(viewGroup, code);
                } catch (Exception e) {
                }
            } else if (msg.what == 20002) {
                try {
                    View viewGroup = (View) ((Object[]) msg.obj)[0];
                    String url = (String) ((Object[]) msg.obj)[1];
                    startPlayer(viewGroup, url);
                } catch (Exception e) {
                }
            }
        }
    };

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
    protected boolean canScrollHorizontally(int count) {
        return count > 4;
    }

    @Override
    protected void onCreateHeadHolder(@NonNull Context context, @NonNull View headView, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> data) {
        try {
            GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = data.get(0);
            switchHead(headView, true, templateBean);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onCreateItemHolder(@NonNull Context context, @NonNull View headView, @NonNull View itemView, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> data, @NonNull RecyclerView.ViewHolder holder) {
        try {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAbsoluteAdapterPosition();
                    if (position >= 0) {
                        GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = data.get(position);
                        JumpUtil.next(view.getContext(), templateBean);
                    }
                }
            });
        } catch (Exception e) {
        }
        try {
            itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    int position = holder.getAbsoluteAdapterPosition();
                    if (position >= 0) {
                        GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = data.get(position);
                        switchHead(headView, hasFocus, templateBean);
                    }
                }
            });
        } catch (Exception e) {
        }
        try {
            itemView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
                        mHandler.removeCallbacksAndMessages(null);
                    }
                    return false;
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    protected void onBindItemHolder(@NonNull Context context, @NonNull View headView, @NonNull View itemView, @NonNull GetSubChannelsByChannelBean.ListBean.TemplateBean item, @NonNull int position) {
        try {
            TextView textView = itemView.findViewById(R.id.common_poster_name);
            textView.setText(item.getName());
        } catch (Exception e) {
        }
        try {
            ImageView imageView = itemView.findViewById(R.id.common_poster_img);
            GlideUtils.loadHz(imageView.getContext(), item.getPicture(true), imageView);
        } catch (Exception e) {
        }
        try {
            ImageView imageView = itemView.findViewById(R.id.common_poster_vip);
            GlideUtils.loadVt(imageView.getContext(), item.getVipUrl(), imageView);
        } catch (Exception e) {
        }
    }

    @Override
    protected int initLayout() {
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

    public void releasePlayer(View viewGroup) {
        try {
            PlayerLayout playerView = viewGroup.findViewById(R.id.general_template21_player);
            playerView.pause();
            playerView.stop();
            playerView.release();
        } catch (Exception e) {
            LogUtil.log("GeneralTemplate21 => releasePlayer => " + e.getMessage());
        }
    }

    public void resumePlayer(View viewGroup) {
        try {
            PlayerLayout playerView = viewGroup.findViewById(R.id.general_template21_player);
            playerView.setPlayWhenReady(true);
            playerView.resume();
        } catch (Exception e) {
            LogUtil.log("GeneralTemplate21 => resumePlayer => " + e.getMessage());
        }
    }

    public void pausePlayer(View viewGroup) {
        try {
            PlayerLayout playerView = viewGroup.findViewById(R.id.general_template21_player);
            playerView.setPlayWhenReady(false);
            playerView.pause();
        } catch (Exception e) {
            LogUtil.log("GeneralTemplate21 => pausePlayer => " + e.getMessage());
        }
    }

    public void startPlayer(View viewGroup, String s) {
        try {
            if (null == s || s.length() <= 0)
                throw new Exception("url error: null");
            PlayerLayout playerView = viewGroup.findViewById(R.id.general_template21_player);
            StartBuilder.Builder builder = new StartBuilder.Builder();
            builder.setLoop(true);
            playerView.start(builder.build(), s);
        } catch (Exception e) {
            LogUtil.log("GeneralTemplate21 => startPlayer => " + e.getMessage());
        }
    }

    private void startHuawei(View viewGroup, String code) {
        try {
            Activity activity = WrapperUtil.getWrapperActivity(viewGroup.getContext());
            if (null != activity && activity instanceof MainActivity) {
                ((MainActivity) activity).huaweiAuth(GeneralTemplate21.class, GeneralTemplate21.GeneralTemplate21List.class, code);
            }
        } catch (Exception e) {
        }
    }

    private void switchHead(@NonNull View view,
                            @NonNull boolean hasFocus,
                            @NonNull GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean) {

        try {
            TextView textView = view.findViewById(R.id.common_poster_name);
            textView.setEllipsize(hasFocus ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
        } catch (Exception e) {
        }
        try {
            if (hasFocus) {
                resumePlayer((View) view.getParent().getParent());
            } else {
                pausePlayer((View) view.getParent().getParent());
            }
        } catch (Exception e) {
        }

        try {
            ImageView imageView = view.findViewById(R.id.general_template21_poster);
            boolean hasExtPoster = templateBean.hasExtPoster();
            if (hasExtPoster) {
                GlideUtils.loadHz(imageView.getContext(), templateBean.getExtPoster(), imageView);
            } else {
                imageView.setImageDrawable(null);
            }
        } catch (Exception e) {
        }
        try {
            TextView textView = view.findViewById(R.id.general_template21_title);
            textView.setVisibility(templateBean.hasExtPoster() ? View.GONE : View.VISIBLE);
            textView.setText(templateBean.getName());
        } catch (Exception e) {
        }
        try {
            TextView textView = view.findViewById(R.id.general_template21_cname);
            textView.setVisibility(templateBean.hasExtPoster() ? View.GONE : View.VISIBLE);
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
            PlayerLayout playerView = view.findViewById(R.id.general_template21_player);
            PlayerComponentInitTemplate21 initTemplate21 = playerView.findComponent(PlayerComponentInitTemplate21.class);
            if (null == initTemplate21)
                throw new Exception("component error: null");
            String picture = templateBean.getPicture(true);
            LogUtil.log("GeneralTemplate21 => showImage => picture = " + picture);
            initTemplate21.setComponentImageUrl(picture);
            LogUtil.log("GeneralTemplate21 => showImage => succ");
        } catch (Exception e) {
            LogUtil.log("GeneralTemplate21 => showImage => " + e.getMessage());
        }

        try {
            PlayerLayout playerView = view.findViewById(R.id.general_template21_player);
            playerView.pause();
            playerView.stop();
            playerView.release();
            LogUtil.log("GeneralTemplate21 => releasePlayer => succ");
        } catch (Exception e) {
            LogUtil.log("GeneralTemplate21 => releasePlayer => " + e.getMessage());
        }

        if (!hasFocus)
            return;

        if (BuildConfig.HUAN_HUAWEI_AUTH) {
            Message message = new Message();
            message.what = 20001;
            message.obj = new Object[]{view, templateBean.getHuaweiId()};
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendMessageDelayed(message, 4000);
        } else {
            Message message = new Message();
            message.what = 20002;
            message.obj = new Object[]{view, BoxUtil.getTestVideoUrl()};
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendMessageDelayed(message, 4000);
        }
    }

    public static class GeneralTemplate21List extends ArrayList {
    }
}