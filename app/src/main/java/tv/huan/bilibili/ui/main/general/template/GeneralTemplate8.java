package tv.huan.bilibili.ui.main.general.template;


import android.content.Context;
import android.graphics.Rect;
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

public class GeneralTemplate8 extends ListGridPresenter<GetSubChannelsByChannelBean.ListBean.TemplateBean> {

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
            @IdRes
            int txt = (i <= 1 ? R.id.album_item_name_template81 : R.id.album_item_name_template82);
            TextView textView = view.findViewById(txt);
            textView.setText(templateBean.getName());
            @IdRes
            int img = (i <= 1 ? R.id.album_item_img_template81 : R.id.album_item_img_template82);
            ImageView imageView = view.findViewById(img);
            GlideUtils.loadHz(imageView.getContext(), templateBean.getNewPicHz(), imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int initItemViewType(int position, GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean) {
        return position <= 1 ? 1 : 2;
    }

    @Override
    protected int initLayout(int i) {
        return i == 1 ? R.layout.fragment_general_item_template08a : R.layout.fragment_general_item_template08b;
    }

    @Override
    protected int initSpanSize(int position) {
        return position <= 1 ? 2 : 1;
    }

    @Override
    protected int initSpan() {
        return 4;
    }

    @Override
    protected int initMax() {
        return 6;
    }

    @Override
    public int initMagrinTop(@NonNull Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.dp_30);
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

                if (position <= 1) {
                    if (position == 1) {
                        outRect.set(offset, 0, 0, offset);
                    } else {
                        outRect.set(0, 0, offset, offset);
                    }
                } else if (position <= 5) {
                    if (position == 5) {
                        outRect.set(offset, 0, 0, offset);
                    } else {
                        outRect.set(0, 0, offset, offset);
                    }
                }

                if (position != 0 && position != 1 && position != 5 && position != 2) {
                    view.setTranslationX(offset / 2);
                }
            }
        };
    }

    public static class GeneralTemplate8List extends ArrayList {
    }
}
