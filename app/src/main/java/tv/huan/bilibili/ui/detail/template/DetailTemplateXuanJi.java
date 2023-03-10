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
import lib.kalu.leanback.presenter.ListTvEpisodesPresenter;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.MediaBean;
import tv.huan.bilibili.ui.detail.DetailActivity;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.LogUtil;

public class DetailTemplateXuanJi extends ListTvEpisodesPresenter<MediaBean> {

    @Override
    public int initPaddingBottom(@NonNull Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.dp_40);
    }

    @Override
    public int initTitlePaddingBottom(@NonNull Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.dp_12);
    }

    @Override
    protected void onBindViewHolderEpisode(@NonNull Context context, @NonNull View v, @NonNull MediaBean item, @NonNull int position, boolean hasFocus, boolean isPlaying, boolean isChecked) {
        LogUtil.log("DetailTemplateXuanJi => onBindViewHolderEpisode => position = " + position + ", hasFocus = " + hasFocus + ", isPlaying = " + isPlaying + ", isChecked = " + isChecked + ", data = " + item.toString());
        try {
            View view = v.findViewById(R.id.detail_xuanji1_item_img);
            view.setVisibility(isPlaying ? View.VISIBLE : View.GONE);
        } catch (Exception e) {
        }
        try {
            TextView textView = v.findViewById(R.id.detail_xuanji1_item_name);
            textView.setVisibility(isPlaying ? View.GONE : View.VISIBLE);
            textView.setText(String.valueOf(item.getTempIndex()));
            textView.setTextColor(context.getResources().getColor(isChecked ? R.color.color_ff6699 : R.color.color_aaaaaa));
        } catch (Exception e) {
        }
        try {
            TextView textView = v.findViewById(R.id.detail_xuanji1_item_popu);
            textView.setText(item.getName());
            textView.setVisibility(hasFocus ? View.VISIBLE : View.INVISIBLE);
            textView.setEllipsize(hasFocus ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
        } catch (Exception e) {
        }
        try {
            ImageView imageView = v.findViewById(R.id.detail_xuanji1_item_vip);
            boolean isVip = item.isTempVip();
            // 免费
            if (isVip) {
                imageView.setVisibility(View.GONE);
            }
            // 播放策略
            else {
                int playType = item.getTempPlayType();
                int index = item.getTempIndex();
                if (playType > 0 && index <= playType) {
                    imageView.setVisibility(View.GONE);
                } else {
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onBindViewHolderRange(@NonNull Context context, @NonNull View v, @NonNull MediaBean item, @NonNull int position, boolean hasFocus, boolean isPlaying, boolean isChecked) {
        LogUtil.log("DetailTemplateXuanJi => onBindViewHolderRange => position = " + position + ", hasFocus = " + hasFocus + ", isPlaying = " + isPlaying + ", isChecked = " + isChecked);
        try {
            TextView textView = v.findViewById(R.id.detail_xuanji2_item_name);
            textView.setText(item.getStart() + "-" + item.getEnd());
        } catch (Exception e) {
        }
        try {
            TextView textView = v.findViewById(R.id.detail_xuanji2_item_name);
            textView.setTextColor(context.getResources().getColor(isChecked ? R.color.color_ff6699 : R.color.color_aaaaaa));
        } catch (Exception e) {
        }
    }

    @Override
    protected void onClickEpisode(@NonNull Context context, @NonNull View v, @NonNull MediaBean item, @NonNull int position, boolean isFromUser) {
        LogUtil.log("DetailTemplateXuanJi => onClickEpisode => position = " + position + ", isFromUser = " + isFromUser);
        try {
            Activity activity = WrapperUtil.getWrapperActivity(context);
            if (null != activity && activity instanceof DetailActivity) {
                ((DetailActivity) activity).stopPlayer();
                ((DetailActivity) activity).updateVidAndClassId(item);
                ((DetailActivity) activity).updateData(item, true);
                ((DetailActivity) activity).delayPlayer(item, true);
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected int initRangeLayout() {
        return R.layout.activity_detail_item_xuanji2;
    }

    @Override
    protected int initEpisodeLayout() {
        return R.layout.activity_detail_item_xuanji1;
    }

    @Override
    protected int initEpisodeNum() {
        return 10;
    }

    @Override
    public String initRowTitle(Context context) {
        return context.getResources().getString(R.string.detail_xuanji);
    }

    @Override
    protected int initEpisodePadding(@NonNull Context context) {
        int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_5);
        return offset;
    }

    @Override
    protected int initRangePadding(@NonNull Context context) {
        int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_10);
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