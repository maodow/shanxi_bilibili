package tv.huan.bilibili.ui.main.general.template;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.VerticalGridView;

import tv.huan.bilibili.R;
import tv.huan.bilibili.widget.GeneralGridView;

public class GeneralTemplateBottom extends Presenter {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        try {
            Context context = viewGroup.getContext();
            View view = LayoutInflater.from(context).inflate(R.layout.fragment_general_item_template_bottom, viewGroup, false);
            view.findViewById(R.id.general_item_template_bottom).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ViewParent parent = v.getParent().getParent();
                        if (parent instanceof GeneralGridView) {
                            ((GeneralGridView) parent).scrollTop();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return new ViewHolder(view);
        } catch (Exception e) {
            e.printStackTrace();
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