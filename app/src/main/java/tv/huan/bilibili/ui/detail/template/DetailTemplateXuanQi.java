package tv.huan.bilibili.ui.detail.template;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
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

import lib.kalu.frame.mvp.util.WrapperUtil;
import lib.kalu.leanback.presenter.ListTvGridPresenter;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.Media;
import tv.huan.bilibili.ui.detail.DetailActivity;

public class DetailTemplateXuanQi extends ListTvGridPresenter<Media> {

    @Override
    protected String initRowTitle(Context context) {
        return context.getResources().getString(R.string.detail_xuanqi);
    }

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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAbsoluteAdapterPosition();
                if (position >= 0) {
                    Activity activity = WrapperUtil.getWrapperActivity(context);
                    if (null != activity && activity instanceof DetailActivity) {
                        ((DetailActivity) activity).nextPlayer(position);
                        ((DetailActivity) activity).delayPlayer(position);
                    }
                }
            }
        });
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                TextView textView = view.findViewById(R.id.detail_xuanqi_item_name);
                textView.setTextColor(view.getResources().getColor(b ? R.color.color_black : R.color.color_white));
            }
        });
    }

    @Override
    protected void onBindHolder(@NonNull View view, @NonNull Media media, @NonNull int i, @NonNull int i1) {
        try {
            TextView textView = view.findViewById(R.id.detail_xuanqi_item_name);
            textView.setText(media.getTitle());
        } catch (Exception e) {
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