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
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;

public class GeneralTemplate3 extends ListTvGridPresenter<GetSubChannelsByChannelBean.ListBean.TemplateBean> {

    @Override
    public String initRowTitle(Context context) {
        if (BuildConfig.HUAN_TEST_TEMPLATE_ENABLE) {
            return "模板3";
        } else {
            return super.initRowTitle(context);
        }
    }

    @Override
    public void initItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (position <= 2) {
            int offset = view.getResources().getDimensionPixelOffset(R.dimen.dp_48) / 6;
            int bottom = view.getResources().getDimensionPixelOffset(R.dimen.dp_24);
            if (position == 0) {
                outRect.set(0, 0, offset * 2, bottom);
            } else if (position == 2) {
                outRect.set(offset * 2, 0, 0, bottom);
            } else {
                outRect.set(offset, 0, offset, bottom);
            }
        } else if (position <= 6) {

            int offset = view.getResources().getDimensionPixelOffset(R.dimen.dp_72) / 8;
            if (position == 3) {
                outRect.set(0, 0, offset * 2, 0);
            } else if (position == 6) {
                outRect.set(offset * 2, 0, 0, 0);
            } else {
                outRect.set(offset, 0, offset, 0);
            }

            int transX = offset * 2 / (3 * 2);
            if (position == 4) {
                view.setTranslationX(-transX);
            } else if (position == 5) {
                view.setTranslationX(transX);
            }
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
    protected void onCreateHolder(@NonNull Context context, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list, @NonNull int i) {
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

    @Override
    protected void onBindHolder(@NonNull View view, @NonNull GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean, @NonNull int i, @NonNull int i1) {
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

    @Override
    protected int initLayout(int i) {
        return R.layout.fragment_general_item_template03;
    }

    @Override
    protected int initSpanSize(int position) {
        return position <= 2 ? 4 : 3;
    }

    @Override
    protected int initSpan() {
        return 12;
    }

    @Override
    protected int initMax() {
        return 7;
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
//
//            }
//        };
//    }

    public static class GeneralTemplate3List extends ArrayList {
    }
}
