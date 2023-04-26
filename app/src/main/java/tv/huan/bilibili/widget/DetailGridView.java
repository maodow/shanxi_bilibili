package tv.huan.bilibili.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ItemBridgeAdapter;

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
            if (null == viewHolder)
                throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer)
                throw new Exception("presenterPlayer error: null");
            return presenterPlayer.getPosition(viewHolder.itemView);
        } catch (Exception e) {
            return 0;
        }
    }

    public long getPlayerDuraion() {
        try {
            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
            if (null == viewHolder)
                throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer)
                throw new Exception("presenterPlayer error: null");
            return presenterPlayer.getDuration(viewHolder.itemView);
        } catch (Exception e) {
            return 0;
        }
    }

    public void stopPlayer() {
        try {
            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
            if (null == viewHolder)
                throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer)
                throw new Exception("presenterPlayer error: null");
            presenterPlayer.stopPlayer(viewHolder.itemView);
        } catch (Exception e) {
            LogUtil.log("DetailGridView => stopPlayer => " + e.getMessage());
        }
    }

    public void stopFull() {
        try {
            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
            if (null == viewHolder)
                throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer)
                throw new Exception("presenterPlayer error: null");
            presenterPlayer.stopFull(viewHolder.itemView);
        } catch (Exception e) {
            LogUtil.log("DetailGridView => stopFull => " + e.getMessage());
        }
    }

    public void startPlayer(@NonNull String s, long seek) {
        try {
            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
            if (null == viewHolder)
                throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer)
                throw new Exception("presenterPlayer error: null");
            presenterPlayer.startPlayer(viewHolder.itemView, s, seek);
        } catch (Exception e) {
            LogUtil.log("DetailGridView => startPlayer => " + e.getMessage());
        }
    }

    public void updateFavor(boolean status) {
        try {
            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
            if (null == viewHolder)
                throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer)
                throw new Exception("presenterPlayer error: null");
            presenterPlayer.updateFavor(viewHolder.itemView, status);
        } catch (Exception e) {
            LogUtil.log("DetailGridView => updateFavor => " + e.getMessage());
        }
    }

    public void updatePlayerPosition(@NonNull MediaBean data) {
        try {
            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
            if (null == viewHolder)
                throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer)
                throw new Exception("presenterPlayer error: null");
            presenterPlayer.updatePosition(viewHolder.itemView, data.getEpisodeIndex());
        } catch (Exception e) {
            LogUtil.log("DetailGridView => updatePlayerPosition => " + e.getMessage());
        }
    }

    public void startPlayerPosition(@NonNull MediaBean data, boolean isFromUser) {
        try {
            if (isFromUser) {
                checkPlayerPosition(data, true);
            } else {
                ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) getAdapter();
                ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
                Object o = objectAdapter.get(1);
                if (null == o)
                    throw new Exception("objectAdapter null");
                // 选集列表
                if (o instanceof DetailTemplateXuanJi.DetailTemplateXuanJiList) {
                    int index = data.getEpisodeIndex() + 1;
                    DetailTemplateXuanJi presenter = getPresenter(DetailTemplateXuanJi.class);
                    if (index > 0 && null != presenter) {
                        int position = --index;
                        ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplateXuanJi.DetailTemplateXuanJiList.class);
                        presenter.checkedPlayingPosition(viewHolder.itemView, position);
                    } else {
                        throw new Exception("xuanji error");
                    }
                }
                // 选期列表
                else if (o instanceof DetailTemplateXuanQi.DetailTemplateXuanQiList) {
                    int index = data.getEpisodeIndex() + 1;
                    DetailTemplateXuanQi presenter = getPresenter(DetailTemplateXuanQi.class);
                    if (index > 0 && null != presenter) {
                        int position = --index;
                        ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplateXuanQi.DetailTemplateXuanQiList.class);
                        presenter.checkedPlayingPosition(viewHolder.itemView, position);
                    } else {
                        throw new Exception("xuanqi error");
                    }
                }
                // 电影
                else {
                    checkPlayerPosition(data, false);
                }
            }
        } catch (Exception e) {
            LogUtil.log("DetailGridView", "startPlayerPosition => " + e.getMessage());
        }
    }

    private void checkPlayerPosition(@NonNull MediaBean data, boolean isFromUser) {
        try {
            int playType = data.getTempPlayType();
            int index = data.getEpisodeIndex() + 1;
            // 免费
            if (playType > 0 && index <= playType) {
                checkPlayerPositionHuawei(data);
            }
            // 收费
            else {
                checkPlayerPositionVipStatus(data, isFromUser);
            }
        } catch (Exception e) {
            LogUtil.log("DetailGridView", "checkPlayerPosition => " + e.getMessage());
        }
    }

    private void checkPlayerPositionVipStatus(@NonNull MediaBean data, boolean isFromUser) {

        LogUtil.log("DetailGridView => checkPlayerPositionVipStatus => isFromUser = " + isFromUser);

        try {
            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
            if (null == viewHolder)
                throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer)
                throw new Exception("presenterPlayer error: null");
            presenterPlayer.checkVipStatus(viewHolder.itemView, data, isFromUser);
        } catch (Exception e) {
            LogUtil.log("DetailGridView => checkPlayerPositionVipStatus => " + e.getMessage());
        }
    }

    private void checkPlayerPositionHuawei(@NonNull MediaBean data) {
        try {
            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
            if (null == viewHolder)
                throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer)
                throw new Exception("presenterPlayer error: null");
            presenterPlayer.startHuawei(viewHolder.itemView, data);
        } catch (Exception e) {
            LogUtil.log("DetailGridView => checkPlayerPositionHuawei => " + e.getMessage());
        }
    }

    public void startPlayer(@NonNull MediaBean data, boolean isFromUser) {
//        // 用户点击
//        if (isFromUser) {
//            checkPlayer(data, true);
//        }
//        // 默认
//        else {
//            try {
//                ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) getAdapter();
//                ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
//                Object o = objectAdapter.get(1);
//                if (null == o)
//                    throw new Exception("objectAdapter null");
//                // 选集列表
//                if (o instanceof DetailTemplateXuanJi.DetailTemplateXuanJiList) {
//                    int index = data.getEpisodeIndex() + 1;
//                    DetailTemplateXuanJi presenter = getPresenter(DetailTemplateXuanJi.class);
//                    if (index > 0 && null != presenter) {
//                        int position = --index;
//                        ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplateXuanJi.DetailTemplateXuanJiList.class);
//                        presenter.checkedPlayingPosition(viewHolder.itemView, position);
//                    }
//                }
//                // 选期列表
//                else if (o instanceof DetailTemplateXuanQi.DetailTemplateXuanQiList) {
//                    int index = data.getEpisodeIndex() + 1;
//                    DetailTemplateXuanQi presenter = getPresenter(DetailTemplateXuanQi.class);
//                    if (index > 0 && null != presenter) {
//                        int position = --index;
//                        presenter.checkedPlayingPosition(this, position);
//                    }
//                }
//                // 电影
//                else {
//                    checkPlayer(data, false);
//                }
//            } catch (Exception e) {
//                LogUtil.log("DetailGridView", "startPlayer => " + e.getMessage());
//            }
//        }
    }

//    public void updateInfo(@NonNull MediaBean data, boolean isFromUser) {
//        stopPlayer();
//        try {
//            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
//            if (null == viewHolder)
//                throw new Exception("viewHolder error: null");
//            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
//            if (null == presenterPlayer)
//                throw new Exception("presenterPlayer error: null");
//            presenterPlayer.updateInfo(viewHolder.itemView, data, isFromUser);
//        } catch (Exception e) {
//            LogUtil.log("BaseGridView => updateInfo => " + e.getMessage());
//        }
//    }

    public boolean dispatchKeyEvent(int direction) {

        try {
            ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) getAdapter();
            ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
            Object o = objectAdapter.get(1);
            if (null == o)
                throw new Exception("objectAdapter null");
            // 选集列表
            if (o instanceof DetailTemplateXuanJi.DetailTemplateXuanJiList) {
//                DetailTemplateXuanJi presenter = getPresenter(DetailTemplateXuanJi.class);
//                if (null != presenter && direction == View.FOCUS_DOWN) {
//                    return presenter.dispatchKeyEventCheckedPositionEpisode(this);
//                } else if (null != presenter && direction == View.FOCUS_UP) {
//                    return presenter.dispatchKeyEventCheckedPositionRange(this);
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

    public void checkedPlayerPositionNext() {
        LogUtil.log("DetailGridView => checkedPlayerPositionNext =>");
        try {
            ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) getAdapter();
            ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
            Object o = objectAdapter.get(1);
            if (null == o)
                throw new Exception("objectAdapter null");
            // 选集列表
            if (o instanceof DetailTemplateXuanJi.DetailTemplateXuanJiList) {
                DetailTemplateXuanJi presenter = getPresenter(DetailTemplateXuanJi.class);
                if (null != presenter) {
                    presenter.checkedPlayingPositionNext(this);
                }
            }
            // 选期列表
            else if (o instanceof DetailTemplateXuanQi.DetailTemplateXuanQiList) {
                DetailTemplateXuanQi presenter = getPresenter(DetailTemplateXuanQi.class);
                if (null != presenter) {
                    presenter.checkedPlayingPositionNext(this);
                }
            }
        } catch (Exception e) {
            LogUtil.log("DetailGridView", "checkedPlayerPositionNext => " + e.getMessage());
        }
    }

//    public void refreshPositionRange(@NonNull MediaBean data) {
//        DetailTemplateXuanJi presenter = getPresenter(DetailTemplateXuanJi.class);
//        LogUtil.log("DetailGridView", "updatePlayerPosition => presenter = " + presenter);
//        if (null == presenter)
//            return;
//        int index = data.getTempIndex();
//        LogUtil.log("DetailGridView", "updatePlayerPosition => index = " + index);
//        if (index <= 0)
//            return;
//        int position = --index;
//        presenter.refreshPositionRange(this, position);
//    }

    public int getPlayerNextPosition() {
        LogUtil.log("DetailGridView => getPlayerNextPosition =>");
        try {
            ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) getAdapter();
            ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
            Object o = objectAdapter.get(1);
            if (null == o)
                throw new Exception("objectAdapter null");
            // 选集列表
            if (o instanceof DetailTemplateXuanJi.DetailTemplateXuanJiList) {
                DetailTemplateXuanJi presenter = getPresenter(DetailTemplateXuanJi.class);
                if (null != presenter) {
                    return presenter.getPlayingPositionNext(this);
                }
            }
            // 选期列表
            else if (o instanceof DetailTemplateXuanQi.DetailTemplateXuanQiList) {
                DetailTemplateXuanQi presenter = getPresenter(DetailTemplateXuanQi.class);
                if (null != presenter) {
                    return presenter.getPlayingPositionNext();
                }
            }
            throw new Exception("not find");
        } catch (Exception e) {
            LogUtil.log("DetailGridView", "checkedPlayerPositionNext => " + e.getMessage());
            return -1;
        }
    }

    public boolean isPlayingEnd() {
        LogUtil.log("DetailGridView => isPlayingEnd =>");
        try {
            ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) getAdapter();
            ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
            Object o = objectAdapter.get(1);
            if (null == o)
                throw new Exception("objectAdapter null");
            // 选集列表
            if (o instanceof DetailTemplateXuanJi.DetailTemplateXuanJiList) {
                DetailTemplateXuanJi presenter = getPresenter(DetailTemplateXuanJi.class);
                if (null != presenter) {
                    return presenter.isPlayingPositionLast(this);
                }
            }
            // 选期列表
            else if (o instanceof DetailTemplateXuanQi.DetailTemplateXuanQiList) {
                DetailTemplateXuanQi presenter = getPresenter(DetailTemplateXuanQi.class);
                if (null != presenter) {
                    return presenter.isPlayingPositionLast();
                }
            }
            throw new Exception("not find");
        } catch (Exception e) {
            LogUtil.log("DetailGridView", "isPlayingEnd => " + e.getMessage());
            return false;
        }
    }
}
