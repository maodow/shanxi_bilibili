package tv.huan.bilibili.ui.main.general.template;


import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lib.kalu.leanback.presenter.ListGridPresenter;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;

public class GeneralTemplate2 extends ListGridPresenter<GetSubChannelsByChannelBean.ListBean.TemplateBean> {

    @Override
    protected void onCreateHolder(@NonNull Context context, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list, @NonNull int i) {
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
        try {
            view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    int position = viewHolder.getAbsoluteAdapterPosition();
                    if (position >= 0) {
                        TextView textView = view.findViewById(R.id.general_template2_name);
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
            TextView textView = view.findViewById(R.id.general_template2_name);
            textView.setText(templateBean.getName());
        } catch (Exception e) {
        }
        try {
            ImageView imageView = view.findViewById(R.id.general_template2_img);
            GlideUtils.loadVt(imageView.getContext(), templateBean.getNewPicVt(), imageView);
        } catch (Exception e) {
        }
    }

    @Override
    protected int initMax() {
        return 9;
    }

//    @Override
//    protected int initMax() {
//        return 9;
//    }

    @Override
    protected int initOrientation() {
        return RecyclerView.HORIZONTAL;
    }

    @Override
    protected int initSpan() {
        return 2;
    }

    @Override
    protected int initSpanSize(int position) {
        return position == 0 ? 2 : 1;
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
    protected int initLayout(int viewType) {
        return viewType == 0 ? R.layout.fragment_general_item_template02a : R.layout.fragment_general_item_template02b;
    }

    @Override
    protected int initItemViewType(int position, GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean) {
        return position == 0 ? 0 : 1;
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
                int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_10);

                if (position == 0) {
                    outRect.set(0, 0, offset * 2, 0);
                } else if (position % 2 == 1) {
                    outRect.set(position <= 2 ? 0 : offset * 2, 0, 0, offset);
                } else if (position % 2 == 0) {
                    outRect.set(position <= 2 ? 0 : offset * 2, offset, 0, 0);
                }
            }
        };
    }

    public static class GeneralTemplate2List extends ArrayList {
    }
}
