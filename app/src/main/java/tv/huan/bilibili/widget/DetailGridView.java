package tv.huan.bilibili.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.VerticalGridView;

import lib.kalu.leanback.list.LeanBackVerticalGridView;
import lib.kalu.leanback.list.RecyclerView;
import lib.kalu.leanback.presenter.ListTvEpisodesPresenter;
import lib.kalu.leanback.util.LeanBackUtil;
import retrofit2.http.Streaming;
import tv.huan.bilibili.bean.Media;
import tv.huan.bilibili.ui.detail.template.DetailTemplatePlayer;
import tv.huan.bilibili.ui.detail.template.DetailTemplateXuanJi;
import tv.huan.bilibili.ui.detail.template.DetailTemplateXuanQi;
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

    /*********/

    public void stopPlayer() {
        try {
            ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) getAdapter();
            ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
            DetailTemplatePlayer.DetailTemplatePlayerObject playerObject = (DetailTemplatePlayer.DetailTemplatePlayerObject) objectAdapter.get(0);
            playerObject.setUpdateOnlyVideoStop(true);
            itemBridgeAdapter.notifyItemChanged(0);
        } catch (Exception e) {
        }
    }

    public void startPlayer(@NonNull int position) {
        try {
            ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) getAdapter();
            ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
            DetailTemplatePlayer.DetailTemplatePlayerObject playerObject = (DetailTemplatePlayer.DetailTemplatePlayerObject) objectAdapter.get(0);
            playerObject.setPlayingIndex(position);
            playerObject.setUpdateOnlyVideoPlaying(true);
            itemBridgeAdapter.notifyItemChanged(0);
        } catch (Exception e) {
        }
    }

    public boolean containsXuanJi() {
        boolean contains = false;
        try {
            ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) getAdapter();
            ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
            int size = objectAdapter.size();
            for (int i = 0; i < size; i++) {
                Object o = objectAdapter.get(i);
                if (null == o)
                    continue;
                if (o instanceof DetailTemplateXuanJi.DetailTemplateXuanJiList) {
                    contains = true;
                    break;
                }
            }
        } catch (Exception e) {
        }
        return contains;
    }

    public boolean containsXuanQi() {
        boolean contains = false;
        try {
            ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) getAdapter();
            ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
            int size = objectAdapter.size();
            for (int i = 0; i < size; i++) {
                Object o = objectAdapter.get(i);
                if (null == o)
                    continue;
                if (o instanceof DetailTemplateXuanQi.DetailTemplateXuanQiList) {
                    contains = true;
                    break;
                }
            }
        } catch (Exception e) {
        }
        return contains;
    }

    public void updateFavor(boolean status) {
        try {
            ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) getAdapter();
            ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
            DetailTemplatePlayer.DetailTemplatePlayerObject playerObject = (DetailTemplatePlayer.DetailTemplatePlayerObject) objectAdapter.get(0);
            playerObject.setUpdateOnlyFavor(true);
            playerObject.setFavorStatus(status);
            itemBridgeAdapter.notifyItemChanged(0);
        } catch (Exception e) {
        }
    }
}
