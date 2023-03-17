package tv.huan.bilibili.ui.detail.template;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import lib.kalu.frame.mvp.util.WrapperUtil;
import lib.kalu.leanback.presenter.ListTvEpisodesGridPresenter;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.MediaBean;
import tv.huan.bilibili.ui.detail.DetailActivity;
import tv.huan.bilibili.utils.LogUtil;

public class DetailTemplateXuanQi extends ListTvEpisodesGridPresenter<MediaBean> {

    @Override
    public void initItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
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

    @Override
    public String initRowTitle(Context context) {
        return context.getResources().getString(R.string.detail_xuanqi);
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
    protected void onBindHolder(@NonNull Context context, @NonNull View v, @NonNull MediaBean t, @NonNull int position) {
        try {
            TextView textView = v.findViewById(R.id.detail_xuanqi_item_name);
            textView.setText(t.getName());
        } catch (Exception e) {
        }
        try {
            TextView textView = v.findViewById(R.id.detail_xuanqi_item_name);
            // hasFocus
            if (t.isFocus()) {
                textView.setTextColor(context.getResources().getColor(R.color.color_black));
            }
            // playing
            else if (t.isPlaying()) {
                textView.setTextColor(Color.RED);
            }
            // checked
            else if (t.isChecked()) {
                textView.setTextColor(context.getResources().getColor(R.color.color_ff6699));
            }
            // normal
            else {
                textView.setTextColor(context.getResources().getColor(R.color.color_aaaaaa));
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onClickHolder(@NonNull Context context, @NonNull View v, @NonNull MediaBean t, @NonNull int position, boolean isFromUser) {
        try {
            Activity activity = WrapperUtil.getWrapperActivity(context);
            if (null != activity && activity instanceof DetailActivity) {
                ((DetailActivity) activity).stopPlayer();
                ((DetailActivity) activity).updateVidAndClassId(t);
                ((DetailActivity) activity).updatePlayerInfo(t, true);
                ((DetailActivity) activity).delayStartPlayer(t, true);
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected int initLayout(int i) {
        return R.layout.activity_detail_item_xuanqi;
    }

    public static class DetailTemplateXuanQiList extends ArrayList<MediaBean> {
    }
}