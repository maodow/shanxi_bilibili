package tv.huan.bilibili.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ItemBridgeAdapter;

import com.google.gson.Gson;

import lib.kalu.leanback.list.LeanBackVerticalGridView;
import tv.huan.bilibili.bean.MediaBean;
import tv.huan.bilibili.ui.detail.template.DetailTemplatePlayer;
import tv.huan.bilibili.ui.detail.template.DetailTemplateXuanJi;
import tv.huan.bilibili.ui.detail.template.DetailTemplateXuanQi;
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

    public long getPlayerPosition() {
        try {
            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
            if (null == viewHolder) throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer) throw new Exception("presenterPlayer error: null");
            return presenterPlayer.getPosition(viewHolder.itemView);
        } catch (Exception e) {
            return 0;
        }
    }

    public long getPlayerDuraion() {
        try {
            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
            if (null == viewHolder) throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer) throw new Exception("presenterPlayer error: null");
            return presenterPlayer.getDuration(viewHolder.itemView);
        } catch (Exception e) {
            return 0;
        }
    }

    public void stopFull() {
        try {
            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
            if (null == viewHolder) throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer) throw new Exception("presenterPlayer error: null");
            presenterPlayer.stopFull(viewHolder.itemView);
        } catch (Exception e) {
            LogUtil.log("DetailGridView => stopFull => " + e.getMessage());
        }
    }

    public void startFull() {
        try {
            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
            if (null == viewHolder) throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer) throw new Exception("presenterPlayer error: null");
            presenterPlayer.startFull(viewHolder.itemView);
        } catch (Exception e) {
            LogUtil.log("DetailGridView => startFull => " + e.getMessage());
        }
    }

    public void startPlayer(@NonNull String s, long seek) {
        try {
            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
            if (null == viewHolder) throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer) throw new Exception("presenterPlayer error: null");
            presenterPlayer.startPlayer(viewHolder.itemView, s, seek);
        } catch (Exception e) {
            LogUtil.log("DetailGridView => startPlayer => " + e.getMessage());
        }
    }

    public void updateFavor(boolean status) {
        try {
            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
            if (null == viewHolder) throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer) throw new Exception("presenterPlayer error: null");
            presenterPlayer.updateFavor(viewHolder.itemView, status);
        } catch (Exception e) {
            LogUtil.log("DetailGridView => updateFavor => " + e.getMessage());
        }
    }

    public void updatePlayerPosition(@NonNull MediaBean data) {
        try {
            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
            if (null == viewHolder) throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer) throw new Exception("presenterPlayer error: null");
            presenterPlayer.updatePosition(viewHolder.itemView, data.getEpisodeIndex());
        } catch (Exception e) {
            LogUtil.log("DetailGridView => updatePlayerPosition => " + e.getMessage());
        }
    }

//    public boolean isPlayerPlayingPosition(@NonNull int position) {
//        try {
//            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
//            if (null == viewHolder) throw new Exception("viewHolder error: null");
//            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
//            if (null == presenterPlayer) throw new Exception("presenterPlayer error: null");
//            return presenterPlayer.checkPlayerPlayingPosition(viewHolder.itemView, position);
//        } catch (Exception e) {
//            LogUtil.log("DetailGridView => isPlayerPlayingPosition => " + e.getMessage());
//            return false;
//        }
//    }

    public void startPlayerPosition(@NonNull int position) {
        LogUtil.log("DetailGridView", "startPlayerPosition => position = " + position);
        try {
            ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) getAdapter();
            ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
            // 选集列表
            if (objectAdapter.size() > 1 && null != objectAdapter.get(1) && objectAdapter.get(1) instanceof DetailTemplateXuanJi.DetailTemplateXuanJiList) {
                DetailTemplateXuanJi presenter = getPresenter(DetailTemplateXuanJi.class);
                if (null != presenter) {
                    ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplateXuanJi.DetailTemplateXuanJiList.class);
                    presenter.checkedPlayingPosition(viewHolder.itemView, position);
                } else {
                    throw new Exception("xuanji error");
                }
            }
            // 选期列表
            else if (objectAdapter.size() > 1 && null != objectAdapter.get(1) && objectAdapter.get(1) instanceof DetailTemplateXuanQi.DetailTemplateXuanQiList) {
                DetailTemplateXuanQi presenter = getPresenter(DetailTemplateXuanQi.class);
                if (null != presenter) {
                    ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplateXuanQi.DetailTemplateXuanQiList.class);
                    presenter.checkedPlayingPosition(viewHolder.itemView, position);
                } else {
                    throw new Exception("xuanqi error");
                }
            }
            // 电影
            else {
                throw new Exception("player error");
            }
        } catch (Exception e) {
            LogUtil.log("DetailGridView", "startPlayerPosition => " + e.getMessage());
        }
    }

    public void startPlayerPosition(@NonNull MediaBean data, @NonNull int pos, @NonNull long seek, boolean isFromUser) {
        LogUtil.log("DetailGridView", "startPlayerPosition => isFromUser = " + isFromUser + ", data = " + new Gson().toJson(data));

        try {
            if (isFromUser) {
                checkPlayerPosition(data, seek, true);
            } else {
                // 选集列表
                if (data.isXuanJi()) {
                    LogUtil.log("DetailGridView", "startPlayerPosition => 选集列表");
                    DetailTemplateXuanJi presenter = getPresenter(DetailTemplateXuanJi.class);
                    if (null != presenter) {
                        ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplateXuanJi.DetailTemplateXuanJiList.class);
                        presenter.checkedPlayingPosition(viewHolder.itemView, pos);
                    } else {
                        throw new Exception("xuanji error");
                    }
                }
                // 选期列表
                else if (data.isXuanQi()) {
                    LogUtil.log("DetailGridView", "startPlayerPosition => 选期列表");
                    DetailTemplateXuanQi presenter = getPresenter(DetailTemplateXuanQi.class);
                    if (null != presenter) {
                        ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplateXuanQi.DetailTemplateXuanQiList.class);
                        presenter.checkedPlayingPosition(viewHolder.itemView, pos);
                    } else {
                        throw new Exception("xuanqi error");
                    }
                }
                // 电影
                else {
                    LogUtil.log("DetailGridView", "startPlayerPosition => 电影");
                    checkPlayerPosition(data, seek, false);
                }
            }
        } catch (Exception e) {
            LogUtil.log("DetailGridView", "startPlayerPosition => " + e.getMessage());
        }
    }

    private void checkPlayerPosition(@NonNull MediaBean data, long seek, boolean isFromUser) {
        try {

            int playType = data.getTempPlayType();
            int index = data.getEpisodeIndex() + 1;
            LogUtil.log("DetailGridView", "checkPlayerPosition => playType = " + playType + ", index = " + index);
            // 免费
            if (playType > 0 && index <= playType) {
                checkPlayerPositionHuawei(data, seek);
            }
            // 收费
            else {
                checkPlayerPositionVipStatus(data, seek, isFromUser);
            }
        } catch (Exception e) {
            LogUtil.log("DetailGridView", "checkPlayerPosition => " + e.getMessage());
        }
    }

    private void checkPlayerPositionVipStatus(@NonNull MediaBean data, long seek, boolean isFromUser) {

        LogUtil.log("DetailGridView => checkPlayerPositionVipStatus => isFromUser = " + isFromUser);

        try {
            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
            if (null == viewHolder) throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer) throw new Exception("presenterPlayer error: null");
            presenterPlayer.checkVipStatus(viewHolder.itemView, data, seek, isFromUser);
        } catch (Exception e) {
            LogUtil.log("DetailGridView => checkPlayerPositionVipStatus => " + e.getMessage());
        }
    }

    private void checkPlayerPositionHuawei(@NonNull MediaBean data, long seek) {
        try {
            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
            if (null == viewHolder) throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer) throw new Exception("presenterPlayer error: null");
            presenterPlayer.startHuawei(viewHolder.itemView, data, seek);
        } catch (Exception e) {
            LogUtil.log("DetailGridView => checkPlayerPositionHuawei => " + e.getMessage());
        }
    }

    public boolean dispatchKeyEvent(int direction) {

        try {
            ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) getAdapter();
            ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
            Object o = objectAdapter.get(1);
            if (null == o) throw new Exception("objectAdapter null");
            // 选集列表
            if (o instanceof DetailTemplateXuanJi.DetailTemplateXuanJiList) {
//                DetailTemplateXuanJi presenter = getPresenter(DetailTemplateXuanJi.class);
//                if (null != presenter && direction == View.FOCUS_DOWN) {
//                    return presenter.dispatchKeyEventCheckedPositionEpisode(this);
//                } else if (null != presenter && direction == View.FOCUS_UP) {
//                    return presenter.dispatchKeyEventCheckedPositionRange(this);
//                }
//                DetailTemplateXuanJi presenter = getPresenter(DetailTemplateXuanJi.class);
//                if (null != presenter) {
//                    return presenter.dispatchKeyEventCheckedPosition(this);
//                }
            }
            // 选期列表
            else if (o instanceof DetailTemplateXuanQi.DetailTemplateXuanQiList) {
                DetailTemplateXuanQi presenter = getPresenter(DetailTemplateXuanQi.class);
                if (null != presenter) {
                    return presenter.dispatchKeyEventCheckedPosition(this);
                }
            }
            throw new Exception("not find");
        } catch (Exception e) {
            LogUtil.log("DetailGridView", "dispatchKeyEvent => " + e.getMessage());
            return false;
        }
    }
}
