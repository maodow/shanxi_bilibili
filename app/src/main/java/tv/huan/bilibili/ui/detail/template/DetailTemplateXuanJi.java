

package tv.huan.bilibili.ui.detail.template;


import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.VerticalGridView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import lib.kalu.leanback.presenter.ListTvEpisodesPresenter;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.Media;

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
    protected void onCreateViewHolderRange(@NonNull Context context, @NonNull View view) {
        try {
//            view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if (hasFocus) {
//                        refresh(viewHolder);
//                    }
//                }
//            });
//            view.setOnKeyListener(new View.OnKeyListener() {
//                @Override
//                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                    // left
//                    if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
//                        int position = viewHolder.getAbsoluteAdapterPosition();
//                        if (position > 0) {
//                            return true;
//                        }
//                    }
//                    // right
//                    else if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
//                        int position = viewHolder.getAbsoluteAdapterPosition();
//                        RecyclerView recyclerView = (RecyclerView) viewHolder.itemView.getParent();
//                        int count = recyclerView.getAdapter().getItemCount();
//                        if (position + 1 < count) {
//                            return true;
//                        }
//                    }
//                    // top
//                    else if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
//                    }
//                    return false;
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreateViewHolderEpisode(@NonNull Context context, @NonNull View view, @NonNull Media media, @NonNull int i) {
        //        try {
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Media media = list.get(i);
//                    startPosition(v, media);
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
    protected void onBindViewHolderRange(@NonNull Context context, @NonNull View view, @NonNull Media media, @NonNull int i) {
        try {
            TextView textView = view.findViewById(R.id.detail_xuanji2_item_name);
            textView.setText(media.getRangeName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onBindViewHolderEpisode(@NonNull Context context, @NonNull View view, @NonNull Media media, @NonNull int i) {
        try {
            TextView textView = view.findViewById(R.id.detail_xuanji1_item_name);
            textView.setText(media.getName());
        } catch (Exception e) {
            e.printStackTrace();
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
    protected int initEpisodePadding(@NonNull Context context) {
        int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_5);
        return offset;
    }

    @Override
    protected int initRangePadding(@NonNull Context context) {
        int offset = context.getResources().getDimensionPixelOffset(R.dimen.dp_5);
        return offset;
    }

    public static class DetailTemplateXuanJiList extends ArrayList<Media> {
    }
}