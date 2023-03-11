package tv.huan.bilibili.ui.main.general.template;


import android.content.Context;
import android.content.res.Resources;
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
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.FavBean;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.bean.MessageBean;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;
import tv.huan.bilibili.utils.LogUtil;

public class GeneralTemplate16 extends ListTvGridPresenter<GetSubChannelsByChannelBean.ListBean.TemplateBean> {

    @Override
    public String initRowTitle(Context context) {
        if (BuildConfig.HUAN_TEST_TEMPLATE_ENABLE) {
            return "模板16";
        }
        else{
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

    private final void refreshBanner(@NonNull View v, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list, @NonNull int position) {
        try {
            GetSubChannelsByChannelBean.ListBean.TemplateBean news = list.get(position);
            GetSubChannelsByChannelBean.ListBean.TemplateBean olds = list.get(1);
            olds.copyPic(news);
            notifyItemRangeChanged(v, 1, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final void refreshRight(@NonNull View v, @NonNull boolean hasFocus) {

        // tag info
//        @IdRes
//        int tag;
//        @IdRes
//        int info;
//        if (position == 2) {
//            tag = R.id.general_item_template17c_tag;
//            info = R.id.general_item_template17c_info;
//        } else if (position == 6) {
//            tag = R.id.general_item_template17e_tag;
//            info = R.id.general_item_template17e_info;
//        } else {
//            tag = R.id.general_item_template17d_tag;
//            info = R.id.general_item_template17d_info;
//        }
//        View vTag = v.findViewById(tag);
//        vTag.setVisibility(leave ? View.VISIBLE : View.INVISIBLE);
//        View vInfo = v.findViewById(info);
//        vInfo.setVisibility(leave || hasFocus ? View.VISIBLE : View.INVISIBLE);

        // refresh
        notifyItemRangeChanged(v, 2, 5);

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
        if (size > 7) {
            size = 7;
        }
        for (int i = 1; i < size; i++) {
            GetSubChannelsByChannelBean.ListBean.TemplateBean bean = list.get(i);
            if (null == bean)
                continue;
            bean.setTempChecked(i == position);
        }
    }

    private final int nextPosition(@NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list) {
        int position = -1;
        try {
            int size = list.size();
            if (size > 7) {
                size = 7;
            }
            for (int i = 2; i < size; i++) {
                GetSubChannelsByChannelBean.ListBean.TemplateBean bean = list.get(i);
                boolean selected = bean.isTempChecked();
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
            if (size > 7) {
                size = 7;
            }
            for (int i = 2; i < size; i++) {
                GetSubChannelsByChannelBean.ListBean.TemplateBean bean = list.get(i);
                boolean selected = bean.isTempChecked();
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
        LogUtil.log("GeneralTemplate16", "nextLoop => what = " + what);
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
        LogUtil.log("GeneralTemplate16", "cleanLoop =>");
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
            LogUtil.log("GeneralTemplate16", "handleMessage => what = " + msg.what);

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
                                position = 2;
                                refreshSelected(list, 2);
                            }
//                    Log.e("GeneralTemplate16", "handleMessage => position = " + position);
                            int size = list.size();
                            if (size > 7) {
                                size = 7;
                            }
                            // selected
                            refreshSelected(list, position);
                            // center
                            refreshBanner((View) objects[0], list, position);
                            // item
                            notifyItemRangeChanged((View) objects[0], 2, size - 2);
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
        LogUtil.log("GeneralTemplate16", "pauseMessage =>");
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
            LogUtil.log("GeneralTemplate16", "resumeMessage =>");
        }
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        LogUtil.log("GeneralTemplate16", "onViewAttachedToWindow =>");
        mHasFocus = false;
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        LogUtil.log("GeneralTemplate16", "onViewDetachedFromWindow =>");
        mHasFocus = true;
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        super.onUnbindViewHolder(viewHolder);
        LogUtil.log("GeneralTemplate16", "onUnbindViewHolder =>");
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        super.onBindViewHolder(viewHolder, item);
        cleanLoop();
        Context context = viewHolder.view.getContext();
        refreshRecs(context, (List<GetSubChannelsByChannelBean.ListBean.TemplateBean>) item);
        nextLoop(viewHolder.view, item, 0x10013, true);
    }

    public final void refreshRecs(@NonNull Context context, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list) {
//        try {
//            String s = CacheUtil.getCache(context, "user_look");
//            Type type = new TypeToken<ArrayList<LookBean>>() {
//            }.getType();
//            ArrayList<LookBean> recs = new Gson().fromJson(s, type);
//            if (null == recs) {
//                recs = new ArrayList<>();
//            }
//            GetSubChannelsByChannelBean.ListBean.TemplateBean bean = list.get(0);
//            bean.setGeneralTemplate17Recs(recs);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Toast.makeText(FrameContext.getApplicationContext(), "refreshRecs", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreateHolder(@NonNull Context context, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list, @NonNull int i) {
        try {

            // 左侧
            if (i == 1) {

                // onClick
                for (int m = 0; m < 7; m++) {
                    View v;
                    if (m == 0) {
                        v = view.findViewById(R.id.general_item_template16a_rec1);
                    } else if (m == 1) {
                        v = view.findViewById(R.id.general_item_template16a_rec2);
                    } else if (m == 2) {
                        v = view.findViewById(R.id.general_item_template16a_rec3);
                    } else if (m == 3) {
                        v = view.findViewById(R.id.general_item_template16a_rec4);
                    } else if (m == 4) {
                        v = view.findViewById(R.id.general_item_template16a_rec5);
                    } else if (m == 5) {
                        v = view.findViewById(R.id.general_item_template16a_all1);
                    } else {
                        v = view.findViewById(R.id.general_item_template16a_all2);
                    }

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int id = view.getId();
                            if (id == R.id.general_item_template16a_rec1) {
                                try {
                                    FavBean.ItemBean itemBean = list.get(0).getTempFav().getRows().get(0);
                                    itemBean.setToType(1);
                                    itemBean.setCid(itemBean.getAlbum().getCid());
                                    JumpUtil.next(view.getContext(), itemBean);
                                } catch (Exception e) {
                                    LogUtil.log("GeneralTemplate16 => onClick => " + e.getMessage());
                                }
                            } else if (id == R.id.general_item_template16a_rec2) {
                                try {
                                    FavBean.ItemBean itemBean = list.get(0).getTempFav().getRows().get(1);
                                    itemBean.setToType(1);
                                    itemBean.setCid(itemBean.getAlbum().getCid());
                                    JumpUtil.next(view.getContext(), itemBean);
                                } catch (Exception e) {
                                    LogUtil.log("GeneralTemplate16 => onClick => " + e.getMessage());
                                }
                            } else if (id == R.id.general_item_template16a_rec3) {
                                try {
                                    FavBean.ItemBean itemBean = list.get(0).getTempFav().getRows().get(2);
                                    itemBean.setToType(1);
                                    itemBean.setCid(itemBean.getAlbum().getCid());
                                    JumpUtil.next(view.getContext(), itemBean);
                                } catch (Exception e) {
                                    LogUtil.log("GeneralTemplate16 => onClick => " + e.getMessage());
                                }
                            } else if (id == R.id.general_item_template16a_rec4) {
                                try {
                                    FavBean.ItemBean itemBean = list.get(0).getTempFav().getRows().get(3);
                                    itemBean.setToType(1);
                                    itemBean.setCid(itemBean.getAlbum().getCid());
                                    JumpUtil.next(view.getContext(), itemBean);
                                } catch (Exception e) {
                                    LogUtil.log("GeneralTemplate16 => onClick => " + e.getMessage());
                                }
                            } else if (id == R.id.general_item_template16a_rec5) {
                                try {
                                    FavBean.ItemBean itemBean = list.get(0).getTempFav().getRows().get(4);
                                    itemBean.setToType(1);
                                    itemBean.setCid(itemBean.getAlbum().getCid());
                                    JumpUtil.next(view.getContext(), itemBean);
                                } catch (Exception e) {
                                    LogUtil.log("GeneralTemplate16 => onClick => " + e.getMessage());
                                }
                            } else {
                                JumpUtil.nextCenter(view.getContext(), false);
                            }
                        }
                    });
                }

                // onFocus
                for (int m = 0; m < 7; m++) {
                    View v;
                    if (m == 0) {
                        v = view.findViewById(R.id.general_item_template16a_rec1);
                    } else if (m == 1) {
                        v = view.findViewById(R.id.general_item_template16a_rec2);
                    } else if (m == 2) {
                        v = view.findViewById(R.id.general_item_template16a_rec3);
                    } else if (m == 3) {
                        v = view.findViewById(R.id.general_item_template16a_rec4);
                    } else if (m == 4) {
                        v = view.findViewById(R.id.general_item_template16a_rec5);
                    } else if (m == 5) {
                        v = view.findViewById(R.id.general_item_template16a_all1);
                    } else {
                        v = view.findViewById(R.id.general_item_template16a_all2);
                    }

                    v.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean b) {

                            @IdRes
                            int nameId;
                            @IdRes
                            int dataId;
                            @IdRes
                            int allId;

                            if (view.getId() == R.id.general_item_template16a_rec1) {
                                nameId = R.id.general_item_template16a_rec1_name;
                                dataId = R.id.general_item_template16a_rec1_position;
                                allId = 0;
                            } else if (view.getId() == R.id.general_item_template16a_rec2) {
                                nameId = R.id.general_item_template16a_rec2_name;
                                dataId = R.id.general_item_template16a_rec2_position;
                                allId = 0;
                            } else if (view.getId() == R.id.general_item_template16a_rec3) {
                                nameId = R.id.general_item_template16a_rec3_name;
                                dataId = R.id.general_item_template16a_rec3_position;
                                allId = 0;
                            } else if (view.getId() == R.id.general_item_template16a_rec4) {
                                nameId = R.id.general_item_template16a_rec4_name;
                                dataId = R.id.general_item_template16a_rec4_position;
                                allId = 0;
                            } else if (view.getId() == R.id.general_item_template16a_rec5) {
                                nameId = R.id.general_item_template16a_rec5_name;
                                dataId = R.id.general_item_template16a_rec5_position;
                                allId = 0;
                            } else if (view.getId() == R.id.general_item_template16a_all1) {
                                nameId = 0;
                                dataId = 0;
                                allId = R.id.general_item_template16a_all1_name;
                            } else {
                                nameId = 0;
                                dataId = 0;
                                allId = R.id.general_item_template16a_all2_name;
                            }

                            try {
                                TextView textName = v.findViewById(nameId);
                                textName.setActivated(b);
                                textName.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                                textName.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                                TextView textData = v.findViewById(dataId);
                                textData.setActivated(b);
                                textData.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                                textData.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                            } catch (Exception e) {
                            }

                            try {
                                TextView allName = v.findViewById(allId);
                                allName.setActivated(b);
                                allName.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                                allName.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                            } catch (Exception e) {
                            }
                        }
                    });
                }
            }
            // 中间
            else if (i == 2) {

            }
            // 右侧
            else {
                view.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
//                        // left
//                        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
//                            v.setTag(true);
//                        }
//                        // right
//                        else if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
//                            v.setTag(true);
//                        }
//                        // down
//                        else if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
//                            int position = viewHolder.getAbsoluteAdapterPosition();
//                            int size = list.size();
//                            if (position + 1 == size) {
//                                v.setTag(true);
//                            }
//                        }
//                        // up
//                        else if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
//                            int position = viewHolder.getAbsoluteAdapterPosition();
//                            if (position == 2) {
//                                v.setTag(true);
//                            }
//                        }
                        // up-in, right-in, down-in, left-in
//                        else if (event.getAction() == KeyEvent.ACTION_UP && (keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT || keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_DPAD_LEFT)) {
                        if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
//                            int position = selectPosition(list);
                            notifyItemRangeChanged(v, 2, 5);
                        }
                        return false;
                    }
                });
                view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        int position = viewHolder.getAbsoluteAdapterPosition();
                        // focus
                        mHasFocus = hasFocus;
                        // selected
                        refreshSelected(list, position);
                        // center
                        refreshBanner(v, list, position);
                        // right
                        refreshRight(v, hasFocus);
                    }
                });
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = viewHolder.getAbsoluteAdapterPosition();
                        GetSubChannelsByChannelBean.ListBean.TemplateBean bean = list.get(position);
                        JumpUtil.next(v.getContext(), bean);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onBindHolder(@NonNull View v, @NonNull GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean, @NonNull int position, @NonNull int viewType) {

        // left
        if (viewType == 1) {

            // visable
            try {
                FavBean fav = templateBean.getTempFav();
                int num = fav.getRows().size();
                v.findViewById(R.id.general_item_template16a_rec1).setVisibility(num >= 1 ? View.VISIBLE : View.GONE);
                v.findViewById(R.id.general_item_template16a_rec2).setVisibility(num >= 2 ? View.VISIBLE : View.GONE);
                v.findViewById(R.id.general_item_template16a_rec3).setVisibility(num >= 3 ? View.VISIBLE : View.GONE);
                v.findViewById(R.id.general_item_template16a_rec4).setVisibility(num >= 4 ? View.VISIBLE : View.GONE);
                v.findViewById(R.id.general_item_template16a_rec5).setVisibility(num >= 5 ? View.VISIBLE : View.GONE);
                v.findViewById(R.id.general_item_template16a_all1).setVisibility(num > 1 && num < 5 ? View.VISIBLE : View.GONE);
                v.findViewById(R.id.general_item_template16a_all2).setVisibility(num <= 0 ? View.VISIBLE : View.GONE);
            } catch (Exception e) {
                LogUtil.log("GeneralTemplate16 => onBindHolder => " + e.getMessage());
                v.findViewById(R.id.general_item_template16a_rec1).setVisibility(View.GONE);
                v.findViewById(R.id.general_item_template16a_rec2).setVisibility(View.GONE);
                v.findViewById(R.id.general_item_template16a_rec3).setVisibility(View.GONE);
                v.findViewById(R.id.general_item_template16a_rec4).setVisibility(View.GONE);
                v.findViewById(R.id.general_item_template16a_rec5).setVisibility(View.GONE);
                v.findViewById(R.id.general_item_template16a_all1).setVisibility(View.GONE);
                v.findViewById(R.id.general_item_template16a_all2).setVisibility(View.VISIBLE);
            }

            for (int m = 0; m < 5; m++) {
                TextView textName;
                TextView textDate;
                if (m == 0) {
                    textName = v.findViewById(R.id.general_item_template16a_rec1_name);
                    textDate = v.findViewById(R.id.general_item_template16a_rec1_position);
                } else if (m == 1) {
                    textName = v.findViewById(R.id.general_item_template16a_rec2_name);
                    textDate = v.findViewById(R.id.general_item_template16a_rec2_position);
                } else if (m == 2) {
                    textName = v.findViewById(R.id.general_item_template16a_rec3_name);
                    textDate = v.findViewById(R.id.general_item_template16a_rec3_position);
                } else if (m == 3) {
                    textName = v.findViewById(R.id.general_item_template16a_rec4_name);
                    textDate = v.findViewById(R.id.general_item_template16a_rec4_position);
                } else {
                    textName = v.findViewById(R.id.general_item_template16a_rec5_name);
                    textDate = v.findViewById(R.id.general_item_template16a_rec5_position);
                }

                try {
                    FavBean.ItemBean itemBean = templateBean.getTempFav().getRows().get(m);
                    textName.setText(itemBean.getNameRec());
                    textDate.setText(itemBean.getStatusRec());
                } catch (Exception e) {
                    LogUtil.log("GeneralTemplate16 => onBindHolder => " + e.getMessage());
                }
            }
        }
        // center
        else if (viewType == 2) {
            ImageView vImg = v.findViewById(R.id.general_item_template16_img);
            GlideUtils.load(vImg.getContext(), templateBean.getPicture(true), vImg, R.drawable.bg_shape_placeholder_template17b);
        }
        // right
        else {
            boolean hasFocus = v.hasFocus();
            boolean selected = templateBean.isTempChecked();
            // 2
            TextView vName = v.findViewById(R.id.general_template16_name);
            vName.setText(templateBean.getName());
            if (hasFocus) {
                vName.setTextColor(v.getResources().getColor(R.color.color_black));
            } else if (selected) {
                vName.setTextColor(v.getResources().getColor(R.color.color_ff6699));
            } else {
                vName.setTextColor(v.getResources().getColor(R.color.color_aaaaaa));
            }
            // 3
            TextView vDesc = v.findViewById(R.id.general_template16_desc);
            vDesc.setText(templateBean.getName());
            vDesc.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    protected int initSpan() {
        return 5;
    }

    @Override
    protected int initMax() {
        return 7;
    }

    @Override
    protected int initOrientation() {
        return RecyclerView.HORIZONTAL;
    }

    @Override
    protected int initSpanSize(int position) {
        if (position == 0) {
            return 5;
        } else if (position == 1) {
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
    protected int initItemViewType(int position, GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean) {
        if (position == 0) {
            return 1;
        } else if (position == 1) {
            return 2;
        } else if (position == 2) {
            return 3;
        } else if (position == 6) {
            return 4;
        } else {
            return 5;
        }
    }

    @Override
    protected int initLayout(int viewType) {
        if (viewType == 1) {
            return R.layout.fragment_general_item_template16a;
        } else if (viewType == 2) {
            return R.layout.fragment_general_item_template16b;
        } else if (viewType == 3) {
            return R.layout.fragment_general_item_template16c;
        } else if (viewType == 4) {
            return R.layout.fragment_general_item_template16e;
        } else {
            return R.layout.fragment_general_item_template16d;
        }
    }

    public static class GeneralTemplate16List extends ArrayList {
    }
}
