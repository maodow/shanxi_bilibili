package tv.huan.bilibili.ui.detail.template;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.VerticalGridView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lib.kalu.leanback.presenter.ListTvGridPresenter;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.Media;

public class DetailTemplateXuanQi extends ListTvGridPresenter<Media> {

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
    protected void onCreateHolder(@NonNull Context context, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view, @NonNull List<Media> list, @NonNull int i) {
        try {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Media media = list.get(i);
                    startPosition(v, media);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected final void startPosition(@NonNull View v, @NonNull Media media) {
        try {
            // 1
            int position = media.getIndex();
            Toast.makeText(v.getContext(), "=> " + position, Toast.LENGTH_SHORT).show();
            String cdnUrl = media.getCdnUrl();
            // 2
            VerticalGridView gridView = (VerticalGridView) v.getParent().getParent().getParent();
            ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) gridView.getAdapter();
            ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
            DetailTemplatePlayer.DetailTemplatePlayerObject playerObject = (DetailTemplatePlayer.DetailTemplatePlayerObject) objectAdapter.get(0);
            playerObject.setCdnUrl(cdnUrl);
            // 3
            gridView.smoothScrollToPosition(0);
            // 4
            itemBridgeAdapter.notifyItemChanged(0);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onBindHolder(@NonNull View view, @NonNull Media media, @NonNull int i, @NonNull int i1) {
        try {
            TextView textView = view.findViewById(R.id.detail_xuanqi_item_name);
            textView.setText(media.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int initLayout(int i) {
        return R.layout.activity_detail_item_xuanqi;
    }

    @Override
    protected int initSpan() {
        return 2;
    }

    @Override
    protected int initOrientation() {
        return RecyclerView.HORIZONTAL;
    }

    @Override
    protected int initMax() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected RecyclerView.ItemDecoration initItemDecoration() {
        return new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                Context context = view.getContext();
                int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_10);
                int position = parent.getChildAdapterPosition(view);
                if ((position & 2) == 0) {
                    int bottom = context.getResources().getDimensionPixelOffset(R.dimen.dp_12);
                    outRect.set(0, 0, offset, bottom);
                } else {
                    outRect.set(0, 0, offset, 0);
                }
            }
        };
    }

    public static class DetailTemplateXuanQiList extends ArrayList {
    }
}