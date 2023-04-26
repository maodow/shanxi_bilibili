package tv.huan.bilibili.ui.main.general.template;


import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import lib.kalu.leanback.presenter.ListTvGridPresenter;
import lib.kalu.leanback.round.RoundLinearLayout;
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.bean.MessageBean;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;
import tv.huan.bilibili.utils.LogUtil;

public final class GeneralTemplate1 extends ListTvGridPresenter<GetSubChannelsByChannelBean.ListBean.TemplateBean> {
    @Override
    public String initRowTitle(Context context) {
        if (BuildConfig.HUAN_TEST_TEMPLATE_ENABLE) {
            return "模板1";
        } else {
            return super.initRowTitle(context);
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
        try {
            startLoop(viewHolder, 0x10013, 0);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onCreateHolder(@NonNull Context context, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list, @NonNull int viewType) {

        if (viewType == 1)
            return;
        // 跳转详情
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
        }
        // 获取焦点
        try {
            view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    int position = viewHolder.getAbsoluteAdapterPosition();
                    if (hasFocus) {
                        setNextCheckedPosition(position);
                        updatePositionImage(v, position);
                    }
                    notifyItemRangeChanged(v, 1, 5);
                    // focus
                    mHasFocus = hasFocus;
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    protected void onBindHolder(@NonNull View v, @NonNull GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean, @NonNull int position, @NonNull int viewType) {
        // 图片
        if (viewType == 1) {
            try {
                TextView textView = v.findViewById(R.id.common_poster_name);
                textView.setText(templateBean.getName());
            } catch (Exception e) {
            }
            try {
                String picture = templateBean.getPicture(true);
                if (null == picture || picture.length() <= 0)
                    throw new Exception();
                ImageView vImg = v.findViewById(R.id.common_poster_img);
                GlideUtils.load(vImg.getContext(), picture, vImg);
            } catch (Exception e) {
                ImageView vImg = v.findViewById(R.id.common_poster_img);
                vImg.setImageDrawable(null);
            }
            try {
                ImageView imageView = v.findViewById(R.id.common_poster_vip);
                GlideUtils.loadVt(imageView.getContext(), templateBean.getVipUrl(), imageView);
            } catch (Exception e) {
            }
        }
        // 文字
        else {
            boolean hasFocus = v.hasFocus();
            boolean selected = templateBean.isTempChecked();
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
            // 4
            try {
                if (hasFocus) {
                    Resources resources = v.getResources();
                    int offset = resources.getDimensionPixelOffset(R.dimen.dp_8);
                    ((RoundLinearLayout) v).refreshRound(offset, offset, offset, offset);
                } else {
                    ((RoundLinearLayout) v).resetRound();
                }
            } catch (Exception e) {
            }
        }
    }

    private void updatePositionImage(@NonNull View view, @NonNull int position) {
        try {
            GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = get(position);
            if (null == templateBean)
                throw new Exception();
            set(0, templateBean);
            notifyItemRangeChanged(view, 0, 1);
        } catch (Exception e) {
            LogUtil.log("GeneralTemplate1", "updatePositionImage => " + e.getMessage());
        }
    }

    private void updatePositionText(@NonNull View view) {
        try {
            int max = initMax();
            if (max <= 0)
                throw new Exception();
            notifyItemRangeChanged(view, 1, max - 1);
        } catch (Exception e) {
            LogUtil.log("GeneralTemplate1", "updatePositionText => " + e.getMessage());
        }
    }

    private void startLoop(@NonNull ViewHolder viewHolder, @NonNull int what, long delayMillis) {
        LogUtil.log("GeneralTemplate1", "startLoop => what = " + what);
        Message message = new Message();
        message.obj = viewHolder;
        message.what = what;
        cleanLoop();
        mHandler.sendMessageDelayed(message, delayMillis);
    }

    private void cleanLoop() {
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
                        startLoop((ViewHolder) msg.obj, 0x10023, 2000);
                    }
                    // loop
                    else {
                        int nextCheckedPosition = getNextCheckedPosition();
                        // center
                        updatePositionImage(((ViewHolder) msg.obj).view, nextCheckedPosition);
                        // item
                        updatePositionText(((ViewHolder) msg.obj).view);
                        // loop
                        startLoop((ViewHolder) msg.obj, 0x10013, 2000);
                    }
                }
                // 0x10023
                else if (msg.what == 0x10023) {
                    // not loop
                    if (mHasFocus) {
                        startLoop((ViewHolder) msg.obj, 0x10023, 2000);
                    }
                    // loop
                    else {
                        startLoop((ViewHolder) msg.obj, 0x10033, 0);
                    }
                }
                // 0x10033
                else if (msg.what == 0x10033) {
                    startLoop((ViewHolder) msg.obj, 0x10013, 2000);
                }
            }
        }
    };

    private int getCheckedPosition() {
        try {
            for (int i = 1; i < initMax(); i++) {
                GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = get(i);
                if (null == templateBean)
                    continue;
                if (templateBean.isTempChecked()) {
                    templateBean.setTempChecked(false);
                    return i;
                }
            }
            throw new Exception();
        } catch (Exception e) {
            LogUtil.log("GeneralTemplate1", "getCheckedPosition => " + e.getMessage());
            return 1;
        }
    }

    private int getNextCheckedPosition() {
        try {
            int nextCheckedPosition = getCheckedPosition() + 1;
            if (nextCheckedPosition >= initMax()) {
                nextCheckedPosition = 1;
            }
            GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = get(nextCheckedPosition);
            templateBean.setTempChecked(true);
            return nextCheckedPosition;
        } catch (Exception e) {
            LogUtil.log("GeneralTemplate1", "getNextCheckedPosition => " + e.getMessage());
            return 1;
        }
    }

    private void setNextCheckedPosition(int position) {
        for (int i = 0; i < initMax(); i++) {
            GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = get(i);
            if (null == templateBean)
                continue;
            templateBean.setTempChecked(position == i);
        }
    }

    public void pauseMessage() {
        mPause = true;
        LogUtil.log("GeneralTemplate1", "pauseMessage =>");
    }

    public void resumeMessage() {
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

    public static class GeneralTemplate1List extends ArrayList<GetSubChannelsByChannelBean.ListBean.TemplateBean> {
    }
}
