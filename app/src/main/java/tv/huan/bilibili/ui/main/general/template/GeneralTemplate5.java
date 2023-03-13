package tv.huan.bilibili.ui.main.general.template;


import android.content.Context;
import android.graphics.Rect;
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

public class GeneralTemplate5 extends ListTvGridPresenter<GetSubChannelsByChannelBean.ListBean.TemplateBean> {

    @Override
    public String initRowTitle(Context context) {
        if (BuildConfig.HUAN_TEST_TEMPLATE_ENABLE) {
            return "模板5";
        } else {
            return super.initRowTitle(context);
        }
    }

    @Override
    public void initItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int offset = view.getResources().getDimensionPixelOffset(R.dimen.dp_72) / 8;
        int bottom;
        if (position <= 3) {
            bottom = view.getResources().getDimensionPixelOffset(R.dimen.dp_24);
        } else {
            bottom = 0;
        }

        if (position % 4 == 0) {
            outRect.set(0, 0, offset * 2, bottom);
        } else if (position % 4 == 3) {
            outRect.set(offset * 2, 0, 0, bottom);
        } else {
            outRect.set(offset, 0, offset, bottom);
        }

        int transX = offset * 2 / 6;
        if (position % 4 == 1) {
            view.setTranslationX(-transX);
        } else if (position % 4 == 2) {
            view.setTranslationX(transX);
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
                    GetSubChannelsByChannelBean.ListBean.TemplateBean bean = list.get(position);
                    JumpUtil.next(v.getContext(), bean);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
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
    protected int initSpan() {
        return 4;
    }

    @Override
    protected int initMax() {
        return 8;
    }

    @Override
    protected int initLayout(int viewType) {
        return R.layout.fragment_general_item_template05;
    }

    public static class GeneralTemplate5List extends ArrayList {
    }
}
