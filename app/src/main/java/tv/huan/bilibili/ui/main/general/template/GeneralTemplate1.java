package tv.huan.bilibili.ui.main.general.template;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import lib.kalu.frame.mvp.util.CacheUtil;
import lib.kalu.leanback.presenter.ListTvGridPresenter;
import lib.kalu.leanback.round.RoundLinearLayout;
import lib.kalu.leanback.round.RoundRelativeLayout;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.bean.LookBean;
import tv.huan.bilibili.bean.MessageBean;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;
import tv.huan.bilibili.utils.LogUtil;

public class GeneralTemplate1 extends ListTvGridPresenter<GetSubChannelsByChannelBean.ListBean.TemplateBean> {

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        LogUtil.log("GeneralTemplate1", "onViewAttachedToWindow =>");
        mHasFocus = false;
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        LogUtil.log("GeneralTemplate1", "onViewDetachedFromWindow =>");
        mHasFocus = true;
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        super.onUnbindViewHolder(viewHolder);
        LogUtil.log("GeneralTemplate1", "onUnbindViewHolder =>");
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        super.onBindViewHolder(viewHolder, item);
        cleanLoop();
        nextLoop(viewHolder.view, item, 0x10013, true);
    }

    @Override
    protected void onCreateHolder(@NonNull Context context, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list, @NonNull int i) {

        // 图片
        if (i == 1) {
        }
        // 文字
        else {
            try {
                view.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                            notifyItemRangeChanged(v, 1, 5);
                        }
                        return false;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        int position = viewHolder.getAbsoluteAdapterPosition();
                        // focus
                        mHasFocus = hasFocus;
                        // center
                        refreshBanner(v, list, position);
                        // selected
                        refreshSelected(list, position);
                        // right
                        refreshRight(v, hasFocus);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = viewHolder.getAbsoluteAdapterPosition();
                        GetSubChannelsByChannelBean.ListBean.TemplateBean bean = list.get(position);
                        JumpUtil.next(v.getContext(), bean);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onBindHolder(@NonNull View v, @NonNull GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean, @NonNull int position, @NonNull int viewType) {

        // 图片
        if (viewType == 1) {
            ImageView vImg = v.findViewById(R.id.album_item_template01_img);
            GlideUtils.load(vImg.getContext(), templateBean.getPicture(true), vImg, R.drawable.bg_shape_placeholder_template17b);
        }
        // 文字
        else {
            boolean hasFocus = v.hasFocus();
            boolean selected = templateBean.isGeneralTemplate17Selected();
            // 2
            TextView vName = v.findViewById(R.id.album_item_template01_name);
            vName.setText(templateBean.getName());
            if (hasFocus) {
                vName.setTextColor(v.getResources().getColor(R.color.color_black));
            } else if (selected) {
                vName.setTextColor(v.getResources().getColor(R.color.color_ff6699));
            } else {
                vName.setTextColor(v.getResources().getColor(R.color.color_aaaaaa));
            }
            // 3
            TextView vDesc = v.findViewById(R.id.album_item_template01_desc);
            vDesc.setText(templateBean.getName());
            vDesc.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    protected int initItemViewType(int position, GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean) {
        if (position == 0) {
            return 1;
        } else if (position == 1) {
            return 2;
        } else if (position == 5) {
            return 3;
        } else {
            return 4;
        }
    }

    @Override
    protected int initLayout(int viewType) {
        if (viewType == 1) {
            return R.layout.fragment_general_item_template01a;
        } else if (viewType == 2) {
            return R.layout.fragment_general_item_template01b;
        } else if (viewType == 3) {
            return R.layout.fragment_general_item_template01d;
        } else {
            return R.layout.fragment_general_item_template01c;
        }
    }

    private final void refreshBanner(@NonNull View v, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list, @NonNull int position) {
        try {
            GetSubChannelsByChannelBean.ListBean.TemplateBean news = list.get(position);
            GetSubChannelsByChannelBean.ListBean.TemplateBean olds = list.get(0);
            olds.copyPic(news);
            notifyItemRangeChanged(v, 0, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final void refreshRight(@NonNull View v, @NonNull boolean hasFocus) {

        // refresh
        notifyItemRangeChanged(v, 1, 5);

        // RoundRelativeLayout
        if (v instanceof RoundRelativeLayout) {
            if (hasFocus) {
                Resources resources = v.getResources();
                int offset = resources.getDimensionPixelOffset(R.dimen.dp_8);
                ((RoundRelativeLayout) v).refreshRound(offset, offset, offset, offset);
            } else {
                ((RoundRelativeLayout) v).resetRound();
            }
        }
        // RoundLinearLayout
        else if (v instanceof RoundLinearLayout) {
            if (hasFocus) {
                Resources resources = v.getResources();
                int offset = resources.getDimensionPixelOffset(R.dimen.dp_8);
                ((RoundLinearLayout) v).refreshRound(offset, offset, offset, offset);
            } else {
                ((RoundLinearLayout) v).resetRound();
            }
        }
    }

    private final void refreshSelected(@NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list, @NonNull int position) {
        int size = list.size();
        if (size > 6) {
            size = 6;
        }
        for (int i = 1; i < size; i++) {
            GetSubChannelsByChannelBean.ListBean.TemplateBean bean = list.get(i);
            if (null == bean)
                continue;
            bean.setGeneralTemplate17Selected(i == position);
        }
    }

    private final int nextPosition(@NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list) {
        int position = -1;
        try {
            int size = list.size();
            if (size > 6) {
                size = 6;
            }
            for (int i = 1; i < size; i++) {
                GetSubChannelsByChannelBean.ListBean.TemplateBean bean = list.get(i);
                boolean selected = bean.isGeneralTemplate17Selected();
                if (selected) {
                    if (i >= 6) {
                        if (size >= 3) {
                            position = 2;
                        }
                    } else {
                        position = i + 1;
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return position;
    }

    private final int selectPosition(@NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list) {
        int position = -1;
        try {
            int size = list.size();
            if (size > 6) {
                size = 6;
            }
            for (int i = 1; i < size; i++) {
                GetSubChannelsByChannelBean.ListBean.TemplateBean bean = list.get(i);
                boolean selected = bean.isGeneralTemplate17Selected();
                if (selected) {
                    position = i;
                    break;
                }
            }
        } catch (Exception e) {
        }
        return position;
    }

    private final void nextLoop(@NonNull Object obj0, @NonNull Object obj1, @NonNull int what, boolean start) {
        LogUtil.log("GeneralTemplate1", "nextLoop => what = " + what);
        Message message = new Message();
        Object[] objects = new Object[2];
        objects[0] = obj0;
        objects[1] = obj1;
        message.obj = objects;
        message.what = what;
        mHandler.removeMessages(what);
        mHandler.sendMessageDelayed(message, start ? 0 : 2000);
    }

    private final void cleanLoop() {
        LogUtil.log("GeneralTemplate1", "cleanLoop =>");
        mHandler.removeMessages(0x10013);
        mHandler.removeMessages(0x10023);
        mHandler.removeMessages(0x10033);
        mHandler.removeCallbacksAndMessages(null);
    }

    private boolean mPause = false;
    private boolean mHasFocus = false;
    private MessageBean[] mMessage = new MessageBean[1];
    private final Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            LogUtil.log("GeneralTemplate1", "handleMessage => what = " + msg.what);

            if (mPause) {
                mHandler.removeCallbacksAndMessages(null);
                MessageBean bean = new MessageBean();
                bean.setWhat(msg.what);
                bean.setObj(msg.obj);
                bean.setArg1(msg.arg1);
                bean.setArg2(msg.arg2);
                mMessage[0] = bean;
            } else {
                // 0x10013
                if (msg.what == 0x10013) {

                    // not loop
                    if (mHasFocus) {
                        Object[] objects = (Object[]) msg.obj;
                        nextLoop(objects[0], objects[1], 0x10023, false);
                    }
                    // loop
                    else {
                        Object[] objects = (Object[]) msg.obj;
                        List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list = (List<GetSubChannelsByChannelBean.ListBean.TemplateBean>) objects[1];
                        if (null != list) {
                            int position = nextPosition(list);
                            if (position == -1) {
                                position = 1;
                                refreshSelected(list, 1);
                            }
//                    Log.e("GeneralTemplate1", "handleMessage => position = " + position);
                            int size = list.size();
                            if (size > 6) {
                                size = 6;
                            }
                            // selected
                            refreshSelected(list, position);
                            // center
                            refreshBanner((View) objects[0], list, position);
                            // item
                            notifyItemRangeChanged((View) objects[0], 1, size - 1);
                            // loop
                            nextLoop(objects[0], list, 0x10013, false);
                        }
                    }
                }
                // 0x10023
                else if (msg.what == 0x10023) {
                    // not loop
                    if (mHasFocus) {
                        Object[] objects = (Object[]) msg.obj;
                        nextLoop(objects[0], objects[1], 0x10023, false);
                    }
                    // loop
                    else {
                        Object[] objects = (Object[]) msg.obj;
                        nextLoop(objects[0], objects[1], 0x10033, true);
                    }
                }
                // 0x10033
                else if (msg.what == 0x10033) {
                    Object[] objects = (Object[]) msg.obj;
                    nextLoop(objects[0], objects[1], 0x10013, false);
                }
            }
        }
    };

    public final void pauseMessage() {
        mPause = true;
        LogUtil.log("GeneralTemplate1", "pauseMessage =>");
    }

    public final void resumeMessage() {
        mPause = false;
        if (null != mMessage && null != mMessage[0]) {
            Message message = new Message();
            message.what = mMessage[0].getWhat();
            message.obj = mMessage[0].getObj();
            message.arg1 = mMessage[0].getArg1();
            message.arg2 = mMessage[0].getArg2();
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendMessageDelayed(message, 2000);
            LogUtil.log("GeneralTemplate1", "resumeMessage =>");
        }
    }

    @Override
    protected int initSpan() {
        return 5;
    }

    @Override
    protected int initMax() {
        return 6;
    }

    @Override
    protected int initOrientation() {
        return RecyclerView.HORIZONTAL;
    }

    @Override
    protected int initSpanSize(int position) {
        if (position == 0) {
            return 5;
        } else {
            return 1;
        }
    }

    @Override
    protected boolean initScrollHorizontally() {
        return false;
    }

    @Override
    protected boolean initScrollVertically() {
        return false;
    }

    @Override
    public int initMagrinTop(@NonNull Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.dp_10);
    }

    @Override
    public int initHeadPadding(@NonNull Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.dp_10);
    }

    @Override
    public int initHeadTextSize(@NonNull Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.sp_24);
    }

    @Override
    public String initHeadAssetTTF(@NonNull Context context) {
        return null;
    }

    public static class GeneralTemplate1List extends ArrayList {
    }
}
