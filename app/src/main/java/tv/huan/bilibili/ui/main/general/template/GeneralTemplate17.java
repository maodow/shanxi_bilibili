package tv.huan.bilibili.ui.main.general.template;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
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
import lib.kalu.leanback.presenter.ListGridPresenter;
import lib.kalu.leanback.round.RoundLinearLayout;
import lib.kalu.leanback.round.RoundRelativeLayout;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.bean.LookBean;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;

public class GeneralTemplate17 extends ListGridPresenter<GetSubChannelsByChannelBean.ListBean.TemplateBean> {

    private final void refreshCenter(@NonNull View v, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list, @NonNull int position) {
        try {
            GetSubChannelsByChannelBean.ListBean.TemplateBean news = list.get(position);
            GetSubChannelsByChannelBean.ListBean.TemplateBean olds = list.get(1);
            olds.setNewPicHz(news.getNewPicHz());
            olds.setNewPicVt(news.getNewPicVt());
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
    private final Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.e("GeneralTemplate17", "handleMessage => what = " + msg.what);
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
    };

    private final void nextLoop(@NonNull Object obj0, @NonNull Object obj1, @NonNull int what, boolean start) {
        Log.e("GeneralTemplate17", "nextLoop => what = " + what);
        Message message = new Message();
        Object[] objects = new Object[2];
        objects[0] = obj0;
        objects[1] = obj1;
        message.obj = objects;
        message.what = what;
        mHandler.removeMessages(what);
        mHandler.sendMessageDelayed(message, start ? 0 : 2000);
    }

    public final void cleanLoop() {
        Log.e("GeneralTemplate17", "cleanLoop =>");
        mHandler.removeMessages(0x10013);
        mHandler.removeMessages(0x10023);
        mHandler.removeMessages(0x10033);
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        Log.e("GeneralTemplate17", "onViewAttachedToWindow =>");
        mHasFocus = false;
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        Log.e("GeneralTemplate17", "onViewDetachedFromWindow =>");
        mHasFocus = true;
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        super.onUnbindViewHolder(viewHolder);
        Log.e("GeneralTemplate17", "onUnbindViewHolder =>");
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
    protected void onCreateHolder(@NonNull Context context, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list, @NonNull int i) {
        try {

            // 左侧
            if (i == 1) {
                // 1
//                ((Template171LinearLayout) view).setOnWindowVisibilityChangedListener(new Template171LinearLayout.OnWindowVisibilityChangedListener() {
//                    @Override
//                    public void onChanged(int visibility) {
//                        Toast.makeText(view.getContext(), "onChanged => " + visibility, Toast.LENGTH_SHORT).show();
//                    }
//                });
                // 2
                int[] ids = new int[]{R.id.general_item_template17a_img,
                        R.id.general_item_template17a_class1_all,
                        R.id.general_item_template17a_class2_all,
                        R.id.general_item_template17a_class2_rec1,
                        R.id.general_item_template17a_class3_all,
                        R.id.general_item_template17a_class3_rec1,
                        R.id.general_item_template17a_class3_rec2};
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
                            // img
                            if (id == R.id.general_item_template17a_img) {
                                int position = viewHolder.getAbsoluteAdapterPosition();
                                GetSubChannelsByChannelBean.ListBean.TemplateBean bean = list.get(position);
                                JumpUtil.next(v.getContext(), bean);
                            }
                            // all
                            else if (id == R.id.general_item_template17a_class1_all || id == R.id.general_item_template17a_class2_all || id == R.id.general_item_template17a_class3_all) {
                                JumpUtil.nextCenter(v.getContext(), true);
                            }
                            // rec1
                            else if (id == R.id.general_item_template17a_class2_rec1 || id == R.id.general_item_template17a_class3_rec1) {
                                int position = viewHolder.getAbsoluteAdapterPosition();
                                GetSubChannelsByChannelBean.ListBean.TemplateBean bean = list.get(position);
                                List<LookBean> lookBeans = bean.getGeneralTemplate17Recs();
                                LookBean lookBean = lookBeans.get(0);
                                JumpUtil.next(v.getContext(), lookBean);
                            }
                            // rec2
                            else if (id == R.id.general_item_template17a_class3_rec2) {
                                int position = viewHolder.getAbsoluteAdapterPosition();
                                GetSubChannelsByChannelBean.ListBean.TemplateBean bean = list.get(position);
                                List<LookBean> lookBeans = bean.getGeneralTemplate17Recs();
                                LookBean lookBean = lookBeans.get(1);
                                JumpUtil.next(v.getContext(), lookBean);
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

//        Log.e("GeneralTemplate17", "onBindHolder => position = " + i + ", data = " + new Gson().toJson(templateBean));
//        Log.e("GeneralPresenter", "onBindHolder => i = "+i+", templateBean = "+templateBean);

        // left
        if (viewType == 1) {
            // 1
            List<LookBean> list = templateBean.getGeneralTemplate17Recs();
//            Log.e("GeneralPresenter", "onBindHolder => " + new Gson().toJson(list));
            int size = list.size();
            v.findViewById(R.id.general_item_template17a_class1_all).setVisibility(size <= 0 ? View.VISIBLE : View.GONE);
            v.findViewById(R.id.general_item_template17a_class2).setVisibility(size == 1 ? View.VISIBLE : View.GONE);
            v.findViewById(R.id.general_item_template17a_class3).setVisibility(size >= 2 ? View.VISIBLE : View.GONE);
            // 2
            if (size == 1) {
                LookBean lookBean = list.get(0);
                TextView vName = v.findViewById(R.id.general_item_template17a_class2_rec1_name);
                vName.setText(lookBean.getCid());
                TextView vPosition = v.findViewById(R.id.general_item_template17a_class2_rec1_position);
                vPosition.setText("1");
            } else if (size >= 2) {
                @IdRes
                int nameId;
                @IdRes
                int positionId;
                for (int m = 0; m < 2; m++) {
                    if (m == 0) {
                        nameId = R.id.general_item_template17a_class3_rec1_name;
                        positionId = R.id.general_item_template17a_class3_rec1_position;
                    } else {
                        nameId = R.id.general_item_template17a_class3_rec2_name;
                        positionId = R.id.general_item_template17a_class3_rec2_position;
                    }
                    LookBean lookBean = list.get(m);
                    TextView vName = v.findViewById(nameId);
                    vName.setText(lookBean.getCid());
                    TextView vPosition = v.findViewById(positionId);
                    vPosition.setText("看至" + m + "%");
                }
            }
            // 3
            ImageView vImg = v.findViewById(R.id.general_item_template17a_img);
            GlideUtils.loadHz(vImg.getContext(), templateBean.getNewPicHz(), vImg);
            // 4
            TextView vTxt = v.findViewById(R.id.general_item_template17a_name);
            vTxt.setText(templateBean.getName());
        }
        // center
        else if (viewType == 2) {
            ImageView vImg = v.findViewById(R.id.general_item_template17b_img);
            String newPicHz = templateBean.getNewPicHz();
            if (null == newPicHz || newPicHz.length() <= 0) {
                vImg.setImageDrawable(null);
            } else {
                GlideUtils.loadHz(vImg.getContext(), newPicHz, vImg);
            }
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
                name = R.id.general_item_template17c_name;
                info = R.id.general_item_template17c_info;
                tag = R.id.general_item_template17c_tag;
            } else if (viewType == 4) {
                name = R.id.general_item_template17e_name;
                info = R.id.general_item_template17e_info;
                tag = R.id.general_item_template17e_tag;
            } else {
                name = R.id.general_item_template17d_name;
                info = R.id.general_item_template17d_info;
                tag = R.id.general_item_template17d_tag;
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
            return R.layout.fragment_general_item_template17a;
        } else if (viewType == 2) {
            return R.layout.fragment_general_item_template17b;
        } else if (viewType == 3) {
            return R.layout.fragment_general_item_template17c;
        } else if (viewType == 4) {
            return R.layout.fragment_general_item_template17e;
        } else {
            return R.layout.fragment_general_item_template17d;
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

    @Override
    protected RecyclerView.ItemDecoration initItemDecoration() {

        return new RecyclerView.ItemDecoration() {

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                Context context = view.getContext();
                int position = parent.getChildAdapterPosition(view);

                if (position == 0) {
                    int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_26);
                    outRect.set(0, 0, offset, 0);
                }
//                else if (position == 2) {
//                    int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_1);
//                    outRect.set(0, 0, 0, offset);
//                } else if (position == 6) {
//                    int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_1);
//                    outRect.set(0, offset, 0, 0);
//                } else if (position == 3 || position == 4 || position == 5) {
//                    int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_1);
//                    outRect.set(0, offset, 0, offset);
//                }
            }
        };
    }

    public static class GeneralTemplate17List extends ArrayList {
    }
}
