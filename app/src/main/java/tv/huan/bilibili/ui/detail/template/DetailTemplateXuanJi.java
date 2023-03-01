

package tv.huan.bilibili.ui.detail.template;


import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.VerticalGridView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import lib.kalu.leanback.presenter.ListTvEpisodesPresenter;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.Media;
import tv.huan.bilibili.utils.LogUtil;

public class DetailTemplateXuanJi extends ListTvEpisodesPresenter<Media> {

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
    protected void onBindViewHolderEpisode(@NonNull Context context, @NonNull View v, @NonNull Media item, @NonNull int position, boolean hasFocus, boolean isPlaying, boolean isChecked) {
        LogUtil.log("DetailTemplateXuanJi => onBindViewHolderEpisode => position = " + position + ", hasFocus = " + hasFocus + ", isPlaying = " + isPlaying + ", isChecked = " + isChecked + ", data = " + item.toString());
        try {
            View view = v.findViewById(R.id.detail_xuanji1_item_img);
            view.setVisibility(isPlaying ? View.VISIBLE : View.GONE);
        } catch (Exception e) {
        }
        try {
            TextView textView = v.findViewById(R.id.detail_xuanji1_item_name);
            textView.setVisibility(isPlaying ? View.GONE : View.VISIBLE);
            textView.setText(item.getName());
            textView.setTextColor(context.getResources().getColor(isChecked ? R.color.color_ff6699 : R.color.color_aaaaaa));
        } catch (Exception e) {
        }
        try {
            TextView textView = v.findViewById(R.id.detail_xuanji1_item_popu);
            textView.setText(item.getTitle());
            textView.setVisibility(hasFocus ? View.VISIBLE : View.INVISIBLE);
            textView.setEllipsize(hasFocus ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onBindViewHolderRange(@NonNull Context context, @NonNull View v, @NonNull Media item, @NonNull int position, boolean hasFocus, boolean isPlaying, boolean isChecked) {
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
    protected void onClickEpisode(@NonNull Context context, @NonNull View v, @NonNull Media item, @NonNull int position) {
        try {
            startPosition(v, item, position);
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
    protected String initHead(Context context) {
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

    public static class DetailTemplateXuanJiList extends ArrayList<Media> {
    }

    /*****/

    protected final void startPosition(@NonNull View v, @NonNull Media media, @NonNull int psoition) {
        try {
            // 1
            String cdnUrl = media.getCdnUrl();
            Toast.makeText(v.getContext(), "position => " + cdnUrl, Toast.LENGTH_SHORT).show();
            // 2
            VerticalGridView gridView = (VerticalGridView) v.getParent().getParent().getParent();
            ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) gridView.getAdapter();
            ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
            DetailTemplatePlayer.DetailTemplatePlayerObject playerObject = (DetailTemplatePlayer.DetailTemplatePlayerObject) objectAdapter.get(0);
            playerObject.setPlayingIndex(psoition);
            // 3
            gridView.smoothScrollToPosition(0);
            // 4
            itemBridgeAdapter.notifyItemChanged(0);
        } catch (Exception e) {
        }
    }
}