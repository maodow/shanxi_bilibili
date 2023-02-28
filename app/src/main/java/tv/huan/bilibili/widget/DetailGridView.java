package tv.huan.bilibili.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lib.kalu.leanback.list.LeanBackVerticalGridView;
import lib.kalu.leanback.list.RecyclerView;
import lib.kalu.leanback.presenter.ListTvEpisodesPresenter;
import lib.kalu.leanback.util.LeanBackUtil;
import tv.huan.bilibili.ui.detail.template.DetailTemplateXuanJi;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate17;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.LogUtil;

public final class DetailGridView extends LeanBackVerticalGridView {
    public DetailGridView(@NonNull Context context) {
        super(context);
        init();
    }

    public DetailGridView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DetailGridView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
    }

    public void requestFocusEpisodePlayingItem() {
        DetailTemplateXuanJi presenter = getPresenter(DetailTemplateXuanJi.class);
        LogUtil.log("DetailGridView", "requestCheckedEpisode => presenter = " + presenter);
        if (null == presenter)
            return;
        presenter.requestFocusChildOfEpisodeChecked(this);
    }
}
