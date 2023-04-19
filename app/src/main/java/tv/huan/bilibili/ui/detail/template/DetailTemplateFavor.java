package tv.huan.bilibili.ui.detail.template;


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

import lib.kalu.leanback.presenter.ListTvRowPresenter;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.MediaBean;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;

public class DetailTemplateFavor extends ListTvRowPresenter<MediaBean> {

    @Override
    public void initItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        Context context = view.getContext();
        int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_24);
        outRect.set(0, 0, offset, 0);
    }

    @Override
    public String initRowTitle(Context context) {
        return context.getResources().getString(R.string.detail_cai);
    }

    @Override
    public int initPaddingTop(@NonNull Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.dp_40);
    }

    @Override
    public int initTitlePaddingBottom(@NonNull Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.dp_12);
    }

    @Override
    protected void onCreateHolder(@NonNull Context context, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view, @NonNull List<MediaBean> list) {
        try {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = viewHolder.getAbsoluteAdapterPosition();
                    MediaBean album = list.get(position);
                    JumpUtil.next(v.getContext(), album);
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
    protected void onBindHolder(@NonNull View view, @NonNull MediaBean album, @NonNull int i, @NonNull int i1) {
        try {
            TextView textView = view.findViewById(R.id.common_poster_name);
            textView.setText(album.getName());
        } catch (Exception e) {
        }
        try {
            ImageView imageView = view.findViewById(R.id.common_poster_img);
            GlideUtils.loadHz(imageView.getContext(), album.getPicture(true), imageView);
        } catch (Exception e) {
        }
        try {
            ImageView imageView = view.findViewById(R.id.common_poster_vip);
            GlideUtils.loadVt(imageView.getContext(), album.getVipUrl(), imageView);
        } catch (Exception e) {
        }
    }

    @Override
    protected int initLayout(int i) {
        return R.layout.activity_detail_item_favor;
    }

    public static class DetailTemplateFavList extends ArrayList {
    }
}