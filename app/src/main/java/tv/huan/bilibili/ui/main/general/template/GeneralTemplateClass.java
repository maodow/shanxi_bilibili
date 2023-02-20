package tv.huan.bilibili.ui.main.general.template;


import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lib.kalu.leanback.presenter.ListGridPresenter;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.utils.JumpUtil;

public class GeneralTemplateClass extends ListGridPresenter<GetSubChannelsByChannelBean.ListBean.TemplateBean> {

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
                public void onFocusChange(View v, boolean hasFocus) {
                    TextView textView = v.findViewById(R.id.template_class_name);
                    textView.setEllipsize(hasFocus ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                    textView.setTextColor(v.getResources().getColor(hasFocus ? R.color.color_black : R.color.color_aaaaaa));
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    protected void onBindHolder(@NonNull View view, @NonNull GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean, @NonNull int i, @NonNull int i1) {
        try {
            TextView textView = view.findViewById(R.id.template_class_name);
            textView.setText(templateBean.getName());
        } catch (Exception e) {
        }
    }

    @Override
    protected int initSpan() {
        return 6;
    }

    @Override
    protected int initMax() {
        return 6;
    }

    @Override
    protected int initLayout(int viewType) {
        return R.layout.fragment_general_item_template_class;
    }

    @Override
    public int initMagrinTop(@NonNull Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.dp_10);
    }

    @Override
    protected RecyclerView.ItemDecoration initItemDecoration() {

        return new RecyclerView.ItemDecoration() {

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                Context context = view.getContext();
                int position = parent.getChildAdapterPosition(view);
                int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_5);

                // 0
                if (position == 0) {
                    outRect.set(0, 0, offset, 0);
                }
                // 5
                else if (position == 5) {
                    outRect.set(offset, 0, 0, 0);
                }
                // 4
                else {
                    outRect.set(offset, 0, offset, 0);
                }
            }
        };
    }

    public static class GeneralTemplateClassList extends ArrayList {
    }
}
