package tv.huan.bilibili.ui.detail.template;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;

import lib.kalu.frame.mvp.util.WrapperUtil;
import lib.kalu.leanback.presenter.ListTvEpisodesDoubleRowPresenter;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.MediaBean;
import tv.huan.bilibili.ui.detail.DetailActivity;
import tv.huan.bilibili.utils.LogUtil;

public class DetailTemplateXuanJi extends ListTvEpisodesDoubleRowPresenter<MediaBean> {

    @Override
    public int initPaddingTop(@NonNull Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.dp_40);
    }

    @Override
    public int initTitlePaddingBottom(@NonNull Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.dp_12);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        super.onBindViewHolder(viewHolder, item);
        LogUtil.log("DetailTemplateXuanJi => onBindViewHolder => data = " + new Gson().toJson(item));
    }

    @Override
    public void onBindHolderEpisode(@NonNull Context context, @NonNull View v, @NonNull MediaBean item, @NonNull int position) {
//        LogUtil.log("DetailTemplateXuanJi => onBindViewHolderEpisode => position = " + position + ", data = " + new Gson().toJson(item));
        try {
            View view = v.findViewById(R.id.detail_xuanji1_item_img);
            view.setVisibility(item.isPlaying() ? View.VISIBLE : View.GONE);
        } catch (Exception e) {
        }
        try {
            TextView textView = v.findViewById(R.id.detail_xuanji1_item_name);
            textView.setVisibility(item.isPlaying() ? View.GONE : View.VISIBLE);
            textView.setText(String.valueOf(item.getEpisodeIndex() + 1));
            textView.setTextColor(context.getResources().getColor(item.isChecked() ? R.color.color_ff6699 : R.color.color_aaaaaa));
        } catch (Exception e) {
        }
        try {
            TextView textView = v.findViewById(R.id.detail_xuanji1_item_popu);
            textView.setText(item.getName());
            textView.setVisibility(item.isFocus() ? View.VISIBLE : View.INVISIBLE);
            textView.setEllipsize(item.isFocus() ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
        } catch (Exception e) {
        }
        try {
            ImageView imageView = v.findViewById(R.id.detail_xuanji1_item_vip);
            // 播放策略
            int playType = item.getTempPlayType();
            int index = item.getEpisodeIndex() + 1;
            if (playType > 0 && index <= playType) {
                imageView.setVisibility(View.GONE);
            } else {
                imageView.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            ImageView imageView = v.findViewById(R.id.detail_xuanji1_item_vip);
            imageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBindHolderRange(@NonNull Context context, @NonNull View v, @NonNull MediaBean item, @NonNull int position) {
//        LogUtil.log("DetailTemplateXuanJi => onBindViewHolderRange => position = " + position + ", hasFocus = " + hasFocus + ", isPlaying = " + isPlaying + ", isChecked = " + isChecked);
        try {
            TextView textView = v.findViewById(R.id.detail_xuanji2_item_name);
            textView.setText(item.getRangeStart() + "-" + item.getRangeEnd());
        } catch (Exception e) {
        }
        try {
            TextView textView = v.findViewById(R.id.detail_xuanji2_item_name);
            textView.setTextColor(context.getResources().getColor(item.isChecked() ? R.color.color_ff6699 : R.color.color_aaaaaa));
        } catch (Exception e) {
        }
    }


    @Override
    public void onClickEpisode(@NonNull Context context, @NonNull View view, @NonNull MediaBean item, @NonNull int position, boolean isFromUser) {
        LogUtil.log("DetailTemplateXuanJi => onClickEpisode => position = " + position + ", isFromUser = " + isFromUser + ", data = " + new Gson().toJson(item));
        try {
            Activity activity = WrapperUtil.getWrapperActivity(context);
            if (null == activity)
                throw new Exception();
            if (!(activity instanceof DetailActivity))
                throw new Exception();
            if (isFromUser) {
                ((DetailActivity) activity).startFull();
            }
            boolean playingPosition = ((DetailActivity) activity).isPlayerPlayingPosition(position);
            if (playingPosition)
                throw new Exception();
            ((DetailActivity) activity).updatePlayerPosition(item);
            ((DetailActivity) activity).startPlayerPosition(item, position, 0, true);
        } catch (Exception e) {
        }
    }

    @Override
    protected int initRangeLayout() {
        return R.layout.activity_detail_item_xuanji_range;
    }

    @Override
    protected int initEpisodeLayout() {
        return R.layout.activity_detail_item_xuanji_episode;
    }

    @Override
    protected int initEpisodeNum() {
        return 10;
    }

    @Override
    protected int initRangeNum() {
        return 5;
    }

    @Override
    public String initRowTitle(Context context) {
        return context.getResources().getString(R.string.detail_xuanji);
    }

    @Override
    protected int initEpisodePadding(@NonNull Context context) {
        int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_4);
        return offset;
    }

    @Override
    protected int initRangePadding(@NonNull Context context) {
        int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_4);
        return offset;
    }

    @Override
    protected int initRangeMarginTop(@NonNull Context context) {
        int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_5);
        return offset;
    }

    public static class DetailTemplateXuanJiList extends ArrayList<MediaBean> {
    }
}