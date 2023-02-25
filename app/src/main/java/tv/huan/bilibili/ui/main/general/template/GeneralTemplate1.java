package tv.huan.bilibili.ui.main.general.template;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lib.kalu.leanback.presenter.ListTvGridPresenter;
import lib.kalu.leanback.round.RoundLinearLayout;
import lib.kalu.leanback.round.RoundRelativeLayout;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;

public class GeneralTemplate1 extends ListTvGridPresenter<GetSubChannelsByChannelBean.ListBean.TemplateBean> implements Handler.Callback {

    private boolean mHasFocus = false;
    private final Handler mHandler = new Handler(this);

    private final void refreshCenter(@NonNull View v, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list, @NonNull int position) {
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
                    if (i >= 5) {
                        if (size >= 2) {
                            position = 1;
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

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        // 0x10011
        if (msg.what == 0x10011) {

            // not loop
            if (mHasFocus) {
                Object[] objects = (Object[]) msg.obj;
                nextLoop(objects[0], objects[1], 0x10021, false);
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
//                    Log.e("GeneralTemplate17", "handleMessage => position = " + position);
                    int size = list.size();
                    if (size > 7) {
                        size = 7;
                    }
                    // selected
                    refreshSelected(list, position);
                    // center
                    refreshCenter((View) objects[0], list, position);
                    // item
                    notifyItemRangeChanged((View) objects[0], 2, size - 2);
                    // loop
                    nextLoop(objects[0], list, 0x10011, false);
                }
            }
        }
        // 0x10021
        else if (msg.what == 0x10021) {
            // not loop
            if (mHasFocus) {
                Object[] objects = (Object[]) msg.obj;
                nextLoop(objects[0], objects[1], 0x10021, false);
            }
            // loop
            else {
                Object[] objects = (Object[]) msg.obj;
                nextLoop(objects[0], objects[1], 0x10031, true);
            }
        }
        // 0x10031
        else if (msg.what == 0x10031) {
            Object[] objects = (Object[]) msg.obj;
            nextLoop(objects[0], objects[1], 0x10011, false);
        }
        return false;
    }

    private final void nextLoop(@NonNull Object obj0, @NonNull Object obj1, @NonNull int what, boolean start) {
        Message message = new Message();
        Object[] objects = new Object[2];
        objects[0] = obj0;
        objects[1] = obj1;
        message.obj = objects;
        message.what = what;
        mHandler.sendMessageDelayed(message, start ? 0 : 2000);
    }

    private final void cleanLoop() {
        mHandler.removeMessages(0x10011);
        mHandler.removeMessages(0x10021);
        mHandler.removeMessages(0x10031);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        super.onUnbindViewHolder(viewHolder);
        cleanLoop();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        super.onBindViewHolder(viewHolder, item);
//        refreshRecs(context, (List<TemplateBean>) item);
        nextLoop(viewHolder.view, item, 0x10011, true);
    }

    @Override
    protected void onCreateHolder(@NonNull Context context, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list, @NonNull int viewType) {
        try {
            if (viewType > 1) {
                view.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                            notifyItemRangeChanged(v, 1, 5);
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
                        refreshCenter(v, list, position);
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
            ImageView vImg = v.findViewById(R.id.album_item_template01a_img);
            GlideUtils.loadHz(vImg.getContext(), templateBean.getPicture(true), vImg);
        }
        // right
        else {
            @IdRes
            int name;
            @IdRes
            int info;
            @IdRes
            int tag;
            if (viewType == 2) {
                name = R.id.general_item_template01b_name;
                info = R.id.general_item_template01b_info;
                tag = R.id.general_item_template01b_tag;
            } else if (viewType == 4) {
                name = R.id.general_item_template01d_name;
                info = R.id.general_item_template01d_info;
                tag = R.id.general_item_template01d_tag;
            } else {
                name = R.id.general_item_template01c_name;
                info = R.id.general_item_template01c_info;
                tag = R.id.general_item_template01c_tag;
            }
            boolean hasFocus = v.hasFocus();
            boolean selected = templateBean.isGeneralTemplate17Selected();
            // 2
            View vTag = v.findViewById(tag);
            vTag.setVisibility(selected ? View.VISIBLE : View.INVISIBLE);
            // 3
            TextView vName = v.findViewById(name);
            vName.setText(templateBean.getName());
            vName.setTextColor(Color.parseColor(hasFocus ? "#3c3c82" : (selected ? "#ffc988" : "#ffffff")));
            // 4
            TextView vInfo = v.findViewById(info);
            vInfo.setText("" + position);
            vInfo.setTextColor(Color.parseColor(hasFocus ? "#3c3c82" : (selected ? "#ffc988" : "#ffffff")));
            vInfo.setVisibility(selected ? View.VISIBLE : View.INVISIBLE);
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
        return position == 0 ? 5 : 1;
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
        } else if (position == 5) {
            return 4;
        } else {
            return 3;
        }
    }

    @Override
    protected int initLayout(int viewType) {
        if (viewType == 1) {
            return R.layout.fragment_general_item_template01a;
        } else if (viewType == 2) {
            return R.layout.fragment_general_item_template01b;
        } else if (viewType == 4) {
            return R.layout.fragment_general_item_template01d;
        } else {
            return R.layout.fragment_general_item_template01c;
        }
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

//    @Override
//    protected RecyclerView.ItemDecoration initItemDecoration() {
//
//        return new RecyclerView.ItemDecoration() {
//
//            @Override
//            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//
//                Context context = view.getContext();
//                int position = parent.getChildAdapterPosition(view);
//
//                // 0
//                if (position == 0) {
//                    int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_12);
//                    outRect.set(0, 0, offset, 0);
//                }
//                // 1
//                else if (position == 1) {
//                    int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_6);
//                    outRect.set(offset, 0, offset / 3 * 2, offset / 3);
//                }
//                // 2
//                else if (position == 2) {
//                    int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_6);
//                    outRect.set(offset, offset / 3, offset / 3 * 2, 0);
//                }
//                // 3
//                else if (position == 3) {
//                    int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_4);
//                    outRect.set(offset, 0, 0, offset / 2);
//                }
//                // 4
//                else if (position == 4) {
//                    int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_4);
//                    outRect.set(offset, offset / 2, 0, 0);
//                }
//            }
//        };
//    }

    public static class GeneralTemplate1List extends ArrayList {
    }
}
