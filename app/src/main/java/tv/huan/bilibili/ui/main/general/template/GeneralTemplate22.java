package tv.huan.bilibili.ui.main.general.template;


import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.leanback.widget.Presenter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lib.kalu.mediaplayer.core.player.VideoLayout;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.widget.player.PlayerView;

public class GeneralTemplate22 extends Presenter {

    private List mData = new ArrayList();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        try {
            Context context = viewGroup.getContext();
            View inflate = LayoutInflater.from(context).inflate(R.layout.fragment_general_item_template22, viewGroup, false);

            // 1
            setControl(inflate);
            // 2
            setAdapter(inflate);
            return new ViewHolder(inflate);
        } catch (Exception e) {
            return null;
        }
    }

    private void setControl(View inflate) {
        try {
            inflate.findViewById(R.id.general_template22_control).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (b) {
                        resumePlayer(view);
                    } else {
                        pasuePlayer(view);
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object o) {
        try {
            // 1
            startPlayer(viewHolder.view);
            // 2
            addAll(viewHolder.view, o);
        } catch (Exception e) {
        }
    }

    private void addAll(View view, Object o) {
        try {
            mData.clear();
            mData.addAll((List) o);
            RecyclerView recyclerView = view.findViewById(R.id.general_template22_list);
            recyclerView.getAdapter().notifyItemRangeChanged(0, mData.size());
        } catch (Exception e) {
        }
    }

    private void pasuePlayer(View inflate) {

        if (null == inflate)
            return;

        VideoLayout playerView = inflate.findViewById(R.id.general_template22_player);
        playerView.pause();
    }

    private void resumePlayer(View inflate) {

        if (null == inflate)
            return;

        VideoLayout playerView = inflate.findViewById(R.id.general_template22_player);
        playerView.resume();
    }

    private void startPlayer(View inflate) {

        if (null == inflate)
            return;

        VideoLayout playerView = inflate.findViewById(R.id.general_template22_player);
        String url = "http://39.134.19.248:6610/yinhe/2/ch00000090990000001335/index.m3u8?virtualDomain=yinhe.live_hls.zte.com";
        playerView.start(url);
    }

    private void setAdapter(View view) {

        if (null == view)
            return;

        RecyclerView recyclerView = view.findViewById(R.id.general_template22_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int offset = view.getResources().getDimensionPixelOffset(R.dimen.dp_25);
                outRect.set(0, 0, offset, 0);
            }
        });
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_general_item_template22_item, null);
                return new RecyclerView.ViewHolder(inflate) {
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

                try {
                    GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = (GetSubChannelsByChannelBean.ListBean.TemplateBean) mData.get(position);
                    TextView textView = holder.itemView.findViewById(R.id.general_template22_item_name);
                    textView.setText(templateBean.getName());
                } catch (Exception e) {
                }
                try {
                    GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = (GetSubChannelsByChannelBean.ListBean.TemplateBean) mData.get(position);
                    ImageView imageView = holder.itemView.findViewById(R.id.general_template22_item_img);
                    GlideUtils.loadHz(imageView.getContext(), templateBean.getPicture(true), imageView);
                } catch (Exception e) {
                }
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        });
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    public static class GeneralTemplate22List extends ArrayList {
    }
}