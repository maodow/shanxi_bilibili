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

import lib.kalu.leanback.presenter.ListRowPresenter;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.Album;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;

public class DetailTemplateFav extends ListRowPresenter<Album> {

    @Override
    public int initMagrinTop(@NonNull Context context) {
        int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_30);
        return offset;
    }

    @Override
    public int initHeadPadding(@NonNull Context context) {
        int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_10);
        return offset;
    }

    @Override
    public int initHeadTextSize(@NonNull Context context) {
        int offset = context.getResources().getDimensionPixelOffset(R.dimen.sp_24);
        return offset;
    }

    @Override
    protected void onCreateHolder(@NonNull Context context, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view, @NonNull List<Album> list) {
        try {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = viewHolder.getAbsoluteAdapterPosition();
                    Album album = list.get(position);
                    JumpUtil.next(v.getContext(), album);
                }
            });
        } catch (Exception e) {
        }
        try {
            view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    TextView textView = view.findViewById(R.id.detail_fav_item_name);
                    textView.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    protected void onBindHolder(@NonNull View view, @NonNull Album album, @NonNull int i, @NonNull int i1) {
        try {
            TextView textView = view.findViewById(R.id.detail_fav_item_name);
            textView.setText(album.getName());
        } catch (Exception e) {
        }
        try {
            ImageView imageView = view.findViewById(R.id.detail_fav_item_img);
            GlideUtils.loadVt(imageView.getContext(), album.getPicture(false), imageView);
        } catch (Exception e) {
        }
    }

    @Override
    protected int initLayout(int i) {
        return R.layout.activity_detail_item_fav;
    }

    @Override
    protected RecyclerView.ItemDecoration initItemDecoration() {
        return new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                Context context = view.getContext();
                int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_24);
                outRect.set(0, 0, offset, 0);
            }
        };
    }

    public static class DetailTemplateFavList extends ArrayList {
    }
}