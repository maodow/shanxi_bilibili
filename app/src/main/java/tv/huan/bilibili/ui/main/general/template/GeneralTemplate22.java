package tv.huan.bilibili.ui.main.general.template;


import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
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
import lib.kalu.leanback.presenter.ListTvGridPresenter;
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
import tv.huan.bilibili.widget.player.component.PlayerComponentInitTemplate22;

public final class GeneralTemplate22 extends ListTvGridPresenter<GetSubChannelsByChannelBean.ListBean.TemplateBean> {

    private final android.os.Handler mHandler = new android.os.Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 5001) {
                try {
                    resumePlayer((View) msg.obj);
                } catch (Exception e) {
                }
            } else if (msg.what == 5002) {
                try {
                    restartPlayer((View) msg.obj);
                } catch (Exception e) {
                }
            }
        }
    };

    @Override
    public String initRowTitle(Context context) {
        if (BuildConfig.HUAN_TEST_TEMPLATE_ENABLE) {
            return "模板21";
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
        // 图片
        if (viewType == 22_2) {
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
            try {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = viewHolder.getAbsoluteAdapterPosition();
                        if (position >= 0) {
                            GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = list.get(position);
                            JumpUtil.next(v.getContext(), templateBean);
                        }
                    }
                });
            } catch (Exception e) {
            }
        }
        // 视频
        else if (viewType == 22_1) {

            // 点击监听
            try {
                view.findViewById(R.id.general_template22_root).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = viewHolder.getAbsoluteAdapterPosition();
                        if (position >= 0) {
                            GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = list.get(position);
                            JumpUtil.next(v.getContext(), templateBean);
                        }
                    }
                });
            } catch (Exception e) {
            }

            // 下移监听
            try {
                view.findViewById(R.id.general_template22_root).setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                        if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                            releasePlayer(view);
                        }
                        return false;
                    }
                });
            } catch (Exception e) {
            }

            // 焦点监听
            try {
                view.findViewById(R.id.general_template22_root).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        try {
                            if (!b)
                                throw new Exception();
                            PlayerLayout playerView = view.findViewById(R.id.general_template22_player);
                            boolean playing = playerView.isPlaying();
                            if (playing)
                                throw new Exception();
                            sendMessageDelayedRestartPlayer(view);
                        } catch (Exception e) {
                        }
                    }
                });
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void onBindHolder(@NonNull View view, @NonNull GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean, @NonNull int position, @NonNull int viewType) {
        // player
        if (viewType == 22_1) {
            try {
                PlayerLayout playerView = view.findViewById(R.id.general_template22_player);
                PlayerComponentInitTemplate22 component = playerView.findComponent(PlayerComponentInitTemplate22.class);
                if (null != component) {
                    component.setComponentImageUrl(templateBean.getPicture(true));
                }
            } catch (Exception e) {
            }

            try {
                PlayerLayout playerView = view.findViewById(R.id.general_template22_player);
                boolean containsKernel = playerView.containsKernel();
                if (containsKernel)
                    throw new Exception();
                if (BuildConfig.HUAN_HUAWEI_AUTH) {
                    Activity activity = WrapperUtil.getWrapperActivity(view.getContext());
                    if (null != activity && activity instanceof MainActivity) {
                        ((MainActivity) activity).huaweiAuth(GeneralTemplate22.class, GeneralTemplate22.GeneralTemplate22List.class, templateBean.getHuaweiId());
                    }
                } else {
                    String url = BoxUtil.getTestVideoUrl();
                    startPlayer(view, url);
                    sendMessageDelayedStartPlayer(view);
                }
            } catch (Exception e) {
            }
        }
        // img
        else if (viewType == 22_2) {
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
            return 22_1;
        } else {
            return 22_2;
        }
    }

    @Override
    protected int initLayout(int viewType) {
        if (viewType == 22_1) {
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

    public void releasePlayer(View viewGroup) {
        try {
            mHandler.removeCallbacksAndMessages(null);
        } catch (Exception e) {
        }
        try {
            PlayerLayout playerLayout = viewGroup.findViewById(R.id.general_template22_player);
            playerLayout.release();
        } catch (Exception e) {
            LogUtil.log("GeneralTemplate22 => releasePlayer => " + e.getMessage());
        }
    }

//    public void pausePlayer(View viewGroup) {
//        try {
//            mHandler.removeCallbacksAndMessages(null);
//        } catch (Exception e) {
//        }
//        try {
//            PlayerLayout playerView = viewGroup.findViewById(R.id.general_template22_player);
//            playerView.setPlayWhenReady(true);
//            playerView.pause();
//            LogUtil.log("GeneralTemplate22 => pausePlayer => thread = " + Thread.currentThread().getName());
//        } catch (Exception e) {
//            LogUtil.log("GeneralTemplate22 => pausePlayer => " + e.getMessage());
//        }
//    }

    private void resumePlayer(View viewGroup) {
        try {
            PlayerLayout playerView = viewGroup.findViewById(R.id.general_template22_player);
            playerView.setPlayWhenReady(true);
            playerView.resume();
            LogUtil.log("GeneralTemplate22 => resumePlayer => thread = " + Thread.currentThread().getName());
        } catch (Exception e) {
            LogUtil.log("GeneralTemplate22 => resumePlayer => " + e.getMessage());
        }
    }

    public void restartPlayer(View viewGroup) {
        try {
            PlayerLayout playerView = viewGroup.findViewById(R.id.general_template22_player);
            playerView.setPlayWhenReady(true);
            playerView.restart();
            LogUtil.log("GeneralTemplate22 => restartPlayer => thread = " + Thread.currentThread().getName());
        } catch (Exception e) {
            LogUtil.log("GeneralTemplate22 => restartPlayer => " + e.getMessage());
        }
    }

    public void startPlayer(View inflate, String s) {
        try {
            if (null == s || s.length() <= 0)
                throw new Exception("url error: null");
            PlayerLayout playerView = inflate.findViewById(R.id.general_template22_player);
            if (null == playerView)
                throw new Exception("playerView error: null");
            StartBuilder.Builder builder = new StartBuilder.Builder();
            builder.setLoop(true);
            builder.setPlayWhenReady(false);
            playerView.start(builder.build(), s);
        } catch (Exception e) {
            LogUtil.log("GeneralTemplate22 => startPlayer => " + e.getMessage());
        }
    }

    public void sendMessageDelayedRestartPlayer(View viewGroup) {
        cleanTemplatePlayerMessageDelayed();
        try {
            Message message = new Message();
            message.what = 5002;
            message.obj = viewGroup;
            mHandler.sendMessageDelayed(message, 1000);
        } catch (Exception e) {
        }
    }

    private void sendMessageDelayedStartPlayer(View viewGroup) {
        cleanTemplatePlayerMessageDelayed();
        try {
            Message message = new Message();
            message.what = 5002;
            message.obj = viewGroup;
            mHandler.sendMessageDelayed(message, 1000);
        } catch (Exception e) {
        }
    }

    public void cleanTemplatePlayerMessageDelayed() {
        try {
            mHandler.removeCallbacksAndMessages(null);
        } catch (Exception e) {
        }
    }

    public static class GeneralTemplate22List extends ArrayList {
    }
}