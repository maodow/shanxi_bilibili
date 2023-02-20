package tv.huan.bilibili.ui.main.general.template;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.VerticalGridView;

import tv.huan.bilibili.R;
import tv.huan.bilibili.utils.JumpUtil;
import tv.huan.bilibili.widget.GeneralGridView;

public class GeneralTemplateBottom extends Presenter {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        try {
            Context context = viewGroup.getContext();
            View inflate = LayoutInflater.from(context).inflate(R.layout.fragment_general_item_template_bottom, viewGroup, false);

            // 返回顶部
            try {
                inflate.findViewById(R.id.template_bottom_top).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            ((GeneralGridView) v.getParent().getParent()).scrollTop();
                        } catch (Exception e) {
                        }
                    }
                });
            } catch (Exception e) {
            }

            // 搜索
            try {
                inflate.findViewById(R.id.template_bottom_search).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JumpUtil.nextSearch(v.getContext());
                    }
                });
            } catch (Exception e) {
            }
            return new ViewHolder(inflate);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object o) {

    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }
}