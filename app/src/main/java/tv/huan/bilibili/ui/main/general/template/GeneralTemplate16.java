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
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;

public class GeneralTemplate16 extends ListTvGridPresenter<GetSubChannelsByChannelBean.ListBean.TemplateBean> implements Handler.Callback {

    private final void refreshCenter(@NonNull View v, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list, @NonNull int position) {
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
            bean.setGeneralTemplate17Selected(i == position);
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
            if (size > 7) {
                size = 7;
            }
            for (int i = 2; i < size; i++) {
                GetSubChannelsByChannelBean.ListBean.TemplateBean bean = list.get(i);
                boolean selected = bean.isGeneralTemplate17Selected();
                if (selected) {
                    position = i;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return position;
    }

    private boolean mHasFocus = false;
    private final Handler mHandler = new Handler(this);

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        // 0x10012
        if (msg.what == 0x10012) {

            // not loop
            if (mHasFocus) {
                Object[] objects = (Object[]) msg.obj;
                nextLoop(objects[0], objects[1], 0x10022, false);
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
                    nextLoop(objects[0], list, 0x10012, false);
                }
            }
        }
        // 0x10022
        else if (msg.what == 0x10022) {
            // not loop
            if (mHasFocus) {
                Object[] objects = (Object[]) msg.obj;
                nextLoop(objects[0], objects[1], 0x10022, false);
            }
            // loop
            else {
                Object[] objects = (Object[]) msg.obj;
                nextLoop(objects[0], objects[1], 0x10032, true);
            }
        }
        // 0x10032
        else if (msg.what == 0x10032) {
            Object[] objects = (Object[]) msg.obj;
            nextLoop(objects[0], objects[1], 0x10012, false);
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
        mHandler.removeMessages(0x10012);
        mHandler.removeMessages(0x10022);
        mHandler.removeMessages(0x10032);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        super.onUnbindViewHolder(viewHolder);
        cleanLoop();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        super.onBindViewHolder(viewHolder, item);
        Context context = viewHolder.view.getContext();
        refreshRecs(context, (List<GetSubChannelsByChannelBean.ListBean.TemplateBean>) item);
        nextLoop(viewHolder.view, item, 0x10012, true);
    }

    public final void refreshRecs(@NonNull Context context, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list) {
        try {
            String s = CacheUtil.getCache(context, "user_look");
            Type type = new TypeToken<ArrayList<LookBean>>() {
            }.getType();
            ArrayList<LookBean> recs = new Gson().fromJson(s, type);
            if (null == recs) {
                recs = new ArrayList<>();
            }
            GetSubChannelsByChannelBean.ListBean.TemplateBean bean = list.get(0);
            bean.setGeneralTemplate17Recs(recs);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Toast.makeText(FrameContext.getApplicationContext(), "refreshRecs", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreateHolder(@NonNull Context context, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list, @NonNull int viewType) {
        try {

            // 左侧
            if (viewType == 1) {
                int[] ids = new int[]{R.id.general_item_template16a_rec1,
                        R.id.general_item_template16a_rec2,
                        R.id.general_item_template16a_rec3,
                        R.id.general_item_template16a_rec4,
                        R.id.general_item_template16a_rec5};
                int length = ids.length;
                for (int j = 0; j < length; j++) {
                    view.findViewById(ids[j]).setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            // right
                            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                                try {
                                    int position = selectPosition(list);
                                    requestFocus(v, position);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return true;
                            }
                            return false;
                        }
                    });
                    view.findViewById(ids[j]).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            int id = v.getId();
                            int index = -1;
                            if (id == R.id.general_item_template16a_rec1) {
                                index = 0;
                            } else if (id == R.id.general_item_template16a_rec2) {
                                index = 1;
                            } else if (id == R.id.general_item_template16a_rec3) {
                                index = 2;
                            } else if (id == R.id.general_item_template16a_rec4) {
                                index = 3;
                            } else if (id == R.id.general_item_template16a_rec5) {
                                index = 4;
                            }
                            if (index == -1)
                                return;

                            int position = viewHolder.getAbsoluteAdapterPosition();
                            GetSubChannelsByChannelBean.ListBean.TemplateBean bean = list.get(position);
                            List<LookBean> lookBeans = bean.getGeneralTemplate17Recs();
                            LookBean lookBean = lookBeans.get(index);
                            JumpUtil.next(v.getContext(), lookBean);
                        }
                    });
                }
            }
            // 中间
            else if (viewType == 2) {

            }
            // 右侧
            else {
                view.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
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
            // 1
            List<LookBean> list = templateBean.getGeneralTemplate17Recs();
            int size = list.size();
            // 2
            if (size <= 0) {

            } else {
                @IdRes
                int nameId;
                @IdRes
                int positionId;
                for (int i = 0; i < 5; i++) {
                    if (i + 1 >= size)
                        continue;
                    if (i == 0) {
                        nameId = R.id.general_item_template16a_rec1_name;
                        positionId = R.id.general_item_template16a_rec1_position;
                    } else if (i == 1) {
                        nameId = R.id.general_item_template16a_rec2_name;
                        positionId = R.id.general_item_template16a_rec2_position;
                    } else if (i == 2) {
                        nameId = R.id.general_item_template16a_rec3_name;
                        positionId = R.id.general_item_template16a_rec3_position;
                    } else if (i == 3) {
                        nameId = R.id.general_item_template16a_rec4_name;
                        positionId = R.id.general_item_template16a_rec4_position;
                    } else {
                        nameId = R.id.general_item_template16a_rec5_name;
                        positionId = R.id.general_item_template16a_rec5_position;
                    }
                    LookBean lookBean = list.get(i);
                    if (null == lookBean)
                        continue;
                    TextView vName = v.findViewById(nameId);
                    vName.setText(lookBean.getCid());
                    TextView vPosition = v.findViewById(positionId);
                    vPosition.setText("看至" + i + "%");
                }
            }
        }
        // center
        else if (viewType == 2) {
            ImageView vImg = v.findViewById(R.id.general_item_template16b_img);
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
            if (viewType == 3) {
                name = R.id.general_item_template16c_name;
                info = R.id.general_item_template16c_info;
                tag = R.id.general_item_template16c_tag;
            } else if (viewType == 4) {
                name = R.id.general_item_template16e_name;
                info = R.id.general_item_template16e_info;
                tag = R.id.general_item_template16e_tag;
            } else {
                name = R.id.general_item_template16d_name;
                info = R.id.general_item_template16d_info;
                tag = R.id.general_item_template16d_tag;
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

    public static class GeneralTemplate16List extends ArrayList {
    }
}
