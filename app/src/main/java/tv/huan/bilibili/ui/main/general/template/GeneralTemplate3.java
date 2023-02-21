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

import lib.kalu.leanback.presenter.ListGridPresenter;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;

public class GeneralTemplate3 extends ListGridPresenter<GetSubChannelsByChannelBean.ListBean.TemplateBean> {

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
                    int position = viewHolder.getAbsoluteAdapterPosition();
                    if (position >= 0) {
                        TextView textView = view.findViewById(R.id.general_template3_name);
                        textView.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    protected void onBindHolder(@NonNull View view, @NonNull GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean, @NonNull int i, @NonNull int i1) {
        try {
            TextView textView = view.findViewById(R.id.general_template3_name);
            textView.setText(templateBean.getName());
        } catch (Exception e) {
        }
        try {
            ImageView imageView = view.findViewById(R.id.general_template3_img);
            GlideUtils.loadHz(imageView.getContext(), templateBean.getNewPicHz(), imageView);
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

                int offset1 = view.getResources().getDimensionPixelOffset(R.dimen.dp_48);
                int v1 = offset1 / 6;
                int offset2 = view.getResources().getDimensionPixelOffset(R.dimen.dp_72);
                int v2 = offset2 / 8;

                int position = parent.getChildAdapterPosition(view);
                int i = position % 6;
                if (i == 0) {
                    outRect.set(0, 0, v1 * 2, 0);
                } else if (i == 1) {
                    outRect.set(v1, 0, v1, 0);
                } else if (i == 2) {
                    outRect.set(v1 * 2, 0, 0, 0);
                } else if (i == 3) {
                    outRect.set(0, 0, v2 * 2, 0);
                } else if (i == 4 || i == 5) {
                    outRect.set(v2, 0, v2, 0);
                } else if (i == 3) {
                    outRect.set(v2 * 2, 0, 0, 0);
                } else {
                    outRect.set(0, 0, 0, 0);
                }

                int x = v2 * 2 / 3;
                if (i == 4) {
                    view.setTranslationX(-x);
                } else if (i == 5) {
                    view.setTranslationX(x);
                } else {
                    view.setTranslationX(0);
                }
            }
        };
    }

    public static class GeneralTemplate3List extends ArrayList {
    }
}
