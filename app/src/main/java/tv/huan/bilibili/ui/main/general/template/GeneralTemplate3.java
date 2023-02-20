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
            e.printStackTrace();
        }
    }

    @Override
    protected void onBindHolder(@NonNull View view, @NonNull GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean, @NonNull int i, @NonNull int i1) {
        try {
            TextView textView = view.findViewById(R.id.album_item_name_template3);
            textView.setText(templateBean.getName());
            ImageView imageView = view.findViewById(R.id.album_item_img_template3);
            GlideUtils.loadHz(imageView.getContext(), templateBean.getNewPicHz(), imageView);
        } catch (Exception e) {
            e.printStackTrace();
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

                int position = parent.getChildAdapterPosition(view);
                int offset = view.getResources().getDimensionPixelOffset(R.dimen.dp_10);

                if (position <= 2) {
                    if (position == 2) {
                        outRect.set(offset, 0, 0, offset);
                    } else {
                        outRect.set(0, 0, offset, offset);
                    }
                } else if (position <= 6) {
                    if (position == 6) {
                        outRect.set(offset, 0, 0, offset);
                    } else {
                        outRect.set(0, 0, offset, offset);
                    }
                }

                if (position != 0 && position != 2 && position != 6 && position != 3) {
                    view.setTranslationX(offset / 2);
                }
            }
        };
    }

    public static class GeneralTemplate3List extends ArrayList {
    }
}
