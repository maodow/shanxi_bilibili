package tv.huan.bilibili.ui.main.general.template;


import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lib.kalu.leanback.presenter.ListTvGridPresenter;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;

public class GeneralTemplate20 extends ListTvGridPresenter<GetSubChannelsByChannelBean.ListBean.TemplateBean> {

    @Override
    protected void onCreateHolder(@NonNull Context context, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list, @NonNull int i) {

        // type0 type2
        if (i != 1) {
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
        }
        // type1
        else {
            int num = 2;
            view.findViewById(R.id.general_template20b_type1_item1).setVisibility(num >= 2 ? View.VISIBLE : View.GONE);
            view.findViewById(R.id.general_template20b_type1_item2).setVisibility(num >= 2 ? View.VISIBLE : View.GONE);
            view.findViewById(R.id.general_template20b_type1_item3).setVisibility(num >= 2 ? View.VISIBLE : View.GONE);
            view.findViewById(R.id.general_template20b_type2_item1).setVisibility(num == 1 ? View.VISIBLE : View.GONE);
            view.findViewById(R.id.general_template20b_type2_item2).setVisibility(num == 1 ? View.VISIBLE : View.GONE);
            view.findViewById(R.id.general_template20b_type3).setVisibility(num <= 0 ? View.VISIBLE : View.GONE);

            // type1
            try {
                view.findViewById(R.id.general_template20b_type1_item1).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean b) {
                        TextView v1 = v.findViewById(R.id.general_template20b_type1_item1_name);
                        v1.setActivated(b);
                        v1.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                        v1.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                        TextView v2 = v.findViewById(R.id.general_template20b_type1_item1_date);
                        v2.setActivated(b);
                        v2.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                        v2.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                    }
                });
            } catch (Exception e) {
            }
            // type1
            try {
                view.findViewById(R.id.general_template20b_type1_item2).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean b) {
                        TextView v1 = v.findViewById(R.id.general_template20b_type1_item2_name);
                        v1.setActivated(b);
                        v1.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                        v1.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                        TextView v2 = v.findViewById(R.id.general_template20b_type1_item2_date);
                        v2.setActivated(b);
                        v2.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                        v2.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                    }
                });
            } catch (Exception e) {
            }
            // type1
            try {
                view.findViewById(R.id.general_template20b_type1_item3).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean b) {
                        TextView v1 = v.findViewById(R.id.general_template20b_type1_item3_name);
                        v1.setActivated(b);
                        v1.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                        v1.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                    }
                });
            } catch (Exception e) {
            }

            // type2
            try {
                view.findViewById(R.id.general_template20b_type2_item1).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean b) {
                        TextView v1 = v.findViewById(R.id.general_template20b_type2_item1_name);
                        v1.setActivated(b);
                        v1.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                        v1.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                        TextView v2 = v.findViewById(R.id.general_template20b_type2_item1_date);
                        v2.setActivated(b);
                        v2.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                        v2.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                    }
                });
            } catch (Exception e) {
            }
            // type2
            try {
                view.findViewById(R.id.general_template20b_type2_item2).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean b) {
                        TextView v1 = v.findViewById(R.id.general_template20b_type2_item2_name);
                        v1.setActivated(b);
                        v1.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                        v1.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                        TextView v2 = v.findViewById(R.id.general_template20b_type2_item2_info);
                        v2.setActivated(b);
                        v2.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                        v2.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                    }
                });
            } catch (Exception e) {
            }

            // type3
            try {
                view.findViewById(R.id.general_template20b_type3).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean b) {
                        TextView textView = v.findViewById(R.id.general_template20b_type3_name);
                        textView.setActivated(b);
                        textView.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                        textView.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                    }
                });
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void onBindHolder(@NonNull View v, @NonNull GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean, @NonNull int i, @NonNull int i1) {

        // type0
        if (i1 == 0) {
            try {
                TextView textView = v.findViewById(R.id.general_template20a_name);
                textView.setText(templateBean.getName());
            } catch (Exception e) {
            }
            try {
                ImageView imageView = v.findViewById(R.id.general_template20a_img);
                GlideUtils.loadHz(imageView.getContext(), templateBean.getPicture(true), imageView);
            } catch (Exception e) {
            }
        }
        // type1
        else if (i1 == 1) {
        }
        //  type2
        else {
            try {
                TextView textView = v.findViewById(R.id.general_template20c_name);
                textView.setText(templateBean.getName());
            } catch (Exception e) {
            }
            try {
                ImageView imageView = v.findViewById(R.id.general_template20c_img);
                GlideUtils.loadHz(imageView.getContext(), templateBean.getPicture(true), imageView);
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected int initItemViewType(int position, GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean) {
        if (position <= 1) {
            return 0;
        } else if (position == 2) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    protected int initLayout(int i) {
        if (i == 0) {
            return R.layout.fragment_general_item_template20a;
        } else if (i == 1) {
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
    protected RecyclerView.ItemDecoration initItemDecoration() {

        return new RecyclerView.ItemDecoration() {

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                int offset = view.getResources().getDimensionPixelOffset(R.dimen.dp_12);
                int position = parent.getChildAdapterPosition(view);
                if (position == 0) {
                    outRect.set(0, 0, offset, 0);
                } else if (position == 1) {
                    outRect.set(offset, 0, 0, 0);
                } else if (position == 2) {
                    outRect.set(0, 0, offset * 2, 0);
                } else if (position == 5) {
                    outRect.set(offset * 2, 0, 0, 0);
                } else {
                    outRect.set(offset, 0, offset, 0);
                }
            }
        };
    }

    public static class GeneralTemplate20List extends ArrayList {
    }
}
