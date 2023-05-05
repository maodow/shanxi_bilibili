package tv.huan.bilibili.ui.main.general.template;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lib.kalu.leanback.presenter.ListTvGridPresenter;
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.FavBean;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;

public class GeneralTemplate20 extends ListTvGridPresenter<GetSubChannelsByChannelBean.ListBean.TemplateBean> {

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
        int offset = view.getResources().getDimensionPixelOffset(R.dimen.dp_12);
        int position = parent.getChildAdapterPosition(view);
        int bottom = view.getResources().getDimensionPixelOffset(R.dimen.dp_24);
        if (position == 0) {
            outRect.set(0, 0, offset, bottom);
        } else if (position == 1) {
            outRect.set(offset, 0, 0, bottom);
        } else if (position == 2) {
            outRect.set(0, 0, offset * 2, 0);
        } else if (position == 5) {
            outRect.set(offset * 2, 0, 0, 0);
        } else {
            outRect.set(offset, 0, offset, 0);
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

        // type1
        if (viewType == 1) {

            // item1
            try {
                View viewById = view.findViewById(R.id.general_template20b_item1);
                viewById.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean b) {
                        TextView v1 = v.findViewById(R.id.general_template20b_item1_name);
                        v1.setActivated(b);
                        v1.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                        v1.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                        TextView v2 = v.findViewById(R.id.general_template20b_item1_date);
                        v2.setActivated(b);
                        v2.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                        v2.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                    }
                });
                viewById.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = list.get(2);
                            FavBean tempFav = templateBean.getTempFav();
                            FavBean.ItemBean itemBean = tempFav.getRows().get(0);
                            itemBean.setToType(1);
                            itemBean.setCid(itemBean.getAlbum().getCid());
                            JumpUtil.next(view.getContext(), itemBean);
                        } catch (Exception e) {
                        }
                    }
                });
            } catch (Exception e) {
            }

            // item2
            try {
                View viewById = view.findViewById(R.id.general_template20b_item2);
                viewById.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean b) {
                        TextView v1 = v.findViewById(R.id.general_template20b_item2_name);
                        v1.setActivated(b);
                        v1.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                        v1.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                        TextView v2 = v.findViewById(R.id.general_template20b_item2_date);
                        v2.setActivated(b);
                        v2.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                        v2.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                    }
                });
                viewById.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = list.get(2);
                            FavBean tempFav = templateBean.getTempFav();
                            FavBean.ItemBean itemBean = tempFav.getRows().get(1);
                            itemBean.setToType(1);
                            itemBean.setCid(itemBean.getAlbum().getCid());
                            JumpUtil.next(view.getContext(), itemBean);
                        } catch (Exception e) {
                        }
                    }
                });
            } catch (Exception e) {
            }

            // item3
            try {
                View viewById = view.findViewById(R.id.general_template20b_item3);
                viewById.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean b) {
                        try {
                            TextView textView = v.findViewById(R.id.general_template20b_item3_name1);
                            textView.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                            textView.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                        } catch (Exception e) {
                        }
                        try {
                            TextView textView = v.findViewById(R.id.general_template20b_item3_name2);
                            textView.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                            textView.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                        } catch (Exception e) {
                        }
                    }
                });
                viewById.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        JumpUtil.nextCenter(view.getContext(), false);
                    }
                });
            } catch (Exception e) {
            }
        }
        // type0 type2
        else {
            try {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = viewHolder.getAbsoluteAdapterPosition();
                        if (position >= 0) {
                            GetSubChannelsByChannelBean.ListBean.TemplateBean bean = list.get(position);
                            JumpUtil.next(v.getContext(), bean);
                        }
                    }
                });
            } catch (Exception e) {
            }
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
    protected void onBindHolder(@NonNull View v, @NonNull GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean,
                                @NonNull int position, @NonNull int viewType) {

        // type1
        if (viewType == 1) {
            updateHistory(v, templateBean.getTempFav());
        }
        //  type2
        else {
            try {
                TextView textView = v.findViewById(R.id.common_poster_name);
                textView.setText(templateBean.getName());
            } catch (Exception e) {
            }
            try {
                ImageView imageView = v.findViewById(R.id.common_poster_img);
                GlideUtils.loadHz(imageView.getContext(), templateBean.getPicture(true), imageView);
            } catch (Exception e) {
            }
            try {
                ImageView imageView = v.findViewById(R.id.common_poster_vip);
                GlideUtils.loadVt(imageView.getContext(), templateBean.getVipUrl(), imageView);
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected int initItemViewType(int position, GetSubChannelsByChannelBean.
            ListBean.TemplateBean templateBean) {
        if (position <= 1) {
            return 0;
        } else if (position == 2) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    protected int initLayout(int viewType) {
        if (viewType == 0) {
            return R.layout.fragment_general_item_template20a;
        } else if (viewType == 1) {
            return R.layout.fragment_general_item_template20b;
        } else {
            return R.layout.fragment_general_item_template20c;
        }
    }

    @Override
    protected int initSpan() {
        return 4;
    }

    @Override
    protected int initSpanSize(int position) {
        return position <= 1 ? 2 : 1;
    }

    @Override
    protected int initMax() {
        return 6;
    }

    public void updateHistory(@NonNull View viewGroup, @NonNull FavBean data) {

        int lenght;
        try {
            lenght = data.getRows().size();
        } catch (Exception e) {
            lenght = 0;
        }

        // item1
        try {
            View viewById = viewGroup.findViewById(R.id.general_template20b_item1);
            viewById.setVisibility(lenght >= 1 ? View.VISIBLE : View.GONE);
            FavBean.ItemBean bean = data.getRows().get(0);
            TextView view1 = viewById.findViewById(R.id.general_template20b_item1_name);
            view1.setText(bean.getNameRec());
            TextView view2 = viewById.findViewById(R.id.general_template20b_item1_date);
            view2.setText(bean.getStatusRec());
        } catch (Exception e) {
        }

        // item2
        try {
            View viewById = viewGroup.findViewById(R.id.general_template20b_item2);
            viewById.setVisibility(lenght >= 2 ? View.VISIBLE : View.GONE);
            FavBean.ItemBean bean = data.getRows().get(1);
            TextView view1 = viewById.findViewById(R.id.general_template20b_item2_name);
            view1.setText(bean.getNameRec());
            TextView view2 = viewById.findViewById(R.id.general_template20b_item2_date);
            view2.setText(bean.getStatusRec());
        } catch (Exception e) {
        }

        // item3
        try {
            View view0 = viewGroup.findViewById(R.id.general_template20b_item3);
            view0.setBackgroundResource(lenght <= 0 ? R.drawable.bg_selector_common_template20_alla : R.drawable.bg_selector_common_template20_allb);
            View view1 = viewGroup.findViewById(R.id.general_template20b_item3_name1);
            view1.setVisibility(lenght <= 0 ? View.VISIBLE : View.GONE);
            View view2 = viewGroup.findViewById(R.id.general_template20b_item3_name2);
            view2.setVisibility(lenght > 0 ? View.VISIBLE : View.GONE);
        } catch (Exception e) {
        }

        // item3
        try {
            View view = viewGroup.findViewById(R.id.general_template20b_item3);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (null == layoutParams)
                throw new Exception();
            if (!(layoutParams instanceof LinearLayout.LayoutParams))
                throw new Exception();
            ((LinearLayout.LayoutParams) layoutParams).weight = lenght == 1 ? 2 : 1;
        } catch (Exception e) {
        }


//        // visable
//        try {
//            int num = data.getRows().size();
//            v.findViewById(R.id.general_template20b_type1_item1).setVisibility(num >= 2 ? View.VISIBLE : View.GONE);
//            v.findViewById(R.id.general_template20b_type1_item2).setVisibility(num >= 2 ? View.VISIBLE : View.GONE);
//            v.findViewById(R.id.general_template20b_type1_item3).setVisibility(num >= 2 ? View.VISIBLE : View.GONE);
//            v.findViewById(R.id.general_template20b_type2_item1).setVisibility(num == 1 ? View.VISIBLE : View.GONE);
//            v.findViewById(R.id.general_template20b_type2_item2).setVisibility(num == 1 ? View.VISIBLE : View.GONE);
//            v.findViewById(R.id.general_template20b_type3).setVisibility(num <= 0 ? View.VISIBLE : View.GONE);
//        } catch (Exception e) {
//            LogUtil.log("GeneralTemplate20 => updateHistory => " + e.getMessage());
//            v.findViewById(R.id.general_template20b_type1_item1).setVisibility(View.GONE);
//            v.findViewById(R.id.general_template20b_type1_item2).setVisibility(View.GONE);
//            v.findViewById(R.id.general_template20b_type1_item3).setVisibility(View.GONE);
//            v.findViewById(R.id.general_template20b_type2_item1).setVisibility(View.GONE);
//            v.findViewById(R.id.general_template20b_type2_item2).setVisibility(View.GONE);
//            v.findViewById(R.id.general_template20b_type3).setVisibility(View.VISIBLE);
//        }
//
//        // data
//        try {
//            List<FavBean.ItemBean> rows = data.getRows();
//            FavBean.ItemBean bean = rows.get(0);
//            TextView textName1 = v.findViewById(R.id.general_template20b_type1_item1_name);
//            textName1.setText(bean.getNameRec());
//            TextView textState1 = v.findViewById(R.id.general_template20b_type1_item1_date);
//            textState1.setText(bean.getStatusRec());
//            TextView textName2 = v.findViewById(R.id.general_template20b_type2_item1_name);
//            textName2.setText(bean.getNameRec());
//            TextView textState2 = v.findViewById(R.id.general_template20b_type2_item1_date);
//            textState2.setText(bean.getStatusRec());
//        } catch (Exception e) {
//            LogUtil.log("GeneralTemplate20 => updateHistory => " + e.getMessage());
//        }
//
//        // data
//        try {
//            List<FavBean.ItemBean> rows = data.getRows();
//            FavBean.ItemBean bean = rows.get(1);
//            TextView textName1 = v.findViewById(R.id.general_template20b_type1_item2_name);
//            textName1.setText(bean.getNameRec());
//            TextView textState1 = v.findViewById(R.id.general_template20b_type1_item2_date);
//            textState1.setText(bean.getStatusRec());
//            TextView textName2 = v.findViewById(R.id.general_template20b_type2_item2_name);
//            textName2.setText(bean.getNameRec());
//            TextView textState2 = v.findViewById(R.id.general_template20b_type2_item2_date);
//            textState2.setText(bean.getStatusRec());
//        } catch (Exception e) {
//            LogUtil.log("GeneralTemplate20 => updateHistory => " + e.getMessage());
//        }
    }

    public static class GeneralTemplate20List extends ArrayList {
    }
}
