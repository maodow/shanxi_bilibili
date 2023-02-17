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

public class GeneralTemplate10 extends ListGridPresenter<GetSubChannelsByChannelBean.ListBean.TemplateBean> {

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
            int txt = (i <= 1 ? R.id.album_item_name_template10a : R.id.album_item_name_template10b);
            TextView textView = view.findViewById(txt);
            textView.setText(templateBean.getName());
            @IdRes
            int img = (i <= 1 ? R.id.album_item_img_template10a : R.id.album_item_img_template10b);
            ImageView imageView = view.findViewById(img);
            if (i <= 1) {
                GlideUtils.loadHz(imageView.getContext(), templateBean.getNewPicHz(), imageView);
            } else {
                GlideUtils.loadVt(imageView.getContext(), templateBean.getNewPicVt(), imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int initSpan() {
        return 6;
    }

    @Override
    protected int initSpanSize(int position) {
        return position <= 1 ? 3 : 1;
    }

    @Override
    protected int initMax() {
        return 8;
    }

    @Override
    protected int initItemViewType(int position, GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean) {
        return position <= 1 ? 1 : 2;
    }

    @Override
    protected int initLayout(int viewType) {
        return viewType == 1 ? R.layout.fragment_general_item_template10a : R.layout.fragment_general_item_template10b;
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

                if (position <= 1) {
                    if (position == 1) {
                        outRect.set(20, 0, 0, 20);
                    } else {
                        outRect.set(0, 0, 20, 20);
                    }
                } else if (position <= 7) {
                    if (position == 7) {
                        outRect.set(20, 0, 0, 20);
                    } else {
                        outRect.set(0, 0, 20, 20);
                    }
                }

                if (position != 1 && position != 7 && position != 0 && position != 2) {
                    view.setTranslationX(10);
                }
            }
        };
    }

    public static class GeneralTemplate10List extends ArrayList {
    }
}
