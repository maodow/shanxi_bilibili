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

    public boolean isPlayerPlayingPosition(@NonNull int position) {
        try {
            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
            if (null == viewHolder) throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer) throw new Exception("presenterPlayer error: null");
            return presenterPlayer.checkPlayerPlayingPosition(viewHolder.itemView, position);
        } catch (Exception e) {
            LogUtil.log("DetailGridView => isPlayerPlayingPosition => " + e.getMessage());
            return false;
        }
    }

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

    public void startPlayerPosition(@NonNull MediaBean data, boolean isFromUser) {
        LogUtil.log("DetailGridView", "startPlayerPosition => isFromUser = " + isFromUser + ", data = " + new Gson().toJson(data));

        try {
            if (isFromUser) {
                checkPlayerPosition(data, true);
            } else {
                // 选集列表
                if (data.isXuanJi()) {
                    ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) getAdapter();
                    ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
                    Object o = objectAdapter.get(1);
                    if (null != o && o instanceof DetailTemplateXuanJi.DetailTemplateXuanJiList) {
                        int index = data.getEpisodeIndex() + 1;
                        DetailTemplateXuanJi presenter = getPresenter(DetailTemplateXuanJi.class);
                        if (index > 0 && null != presenter) {
                            int position = --index;
                            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplateXuanJi.DetailTemplateXuanJiList.class);
                            presenter.checkedPlayingPosition(viewHolder.itemView, position);
                        } else {
                            throw new Exception("xuanji error");
                        }
                    } else {
                        throw new Exception("objectAdapter null");
                    }
                }
                // 选期列表
                else if (data.isXuanQi()) {
                    ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) getAdapter();
                    ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
                    Object o = objectAdapter.get(1);
                    if (null != o && o instanceof DetailTemplateXuanQi.DetailTemplateXuanQiList) {
                        int index = data.getEpisodeIndex() + 1;
                        DetailTemplateXuanQi presenter = getPresenter(DetailTemplateXuanQi.class);
                        if (index > 0 && null != presenter) {
                            int position = --index;
                            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplateXuanQi.DetailTemplateXuanQiList.class);
                            presenter.checkedPlayingPosition(viewHolder.itemView, position);
                        } else {
                            throw new Exception("xuanqi error");
                        }
                    } else {
                        throw new Exception("objectAdapter null");
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
            LogUtil.log("DetailGridView", "checkPlayerPosition => playType = " + playType + ", index = " + index);
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
            if (null == viewHolder) throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer) throw new Exception("presenterPlayer error: null");
            presenterPlayer.checkVipStatus(viewHolder.itemView, data, isFromUser);
        } catch (Exception e) {
            LogUtil.log("DetailGridView => checkPlayerPositionVipStatus => " + e.getMessage());
        }
    }

    private void checkPlayerPositionHuawei(@NonNull MediaBean data) {
        try {
            ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplatePlayer.DetailTemplatePlayerObject.class);
            if (null == viewHolder) throw new Exception("viewHolder error: null");
            DetailTemplatePlayer presenterPlayer = getPresenter(DetailTemplatePlayer.class);
            if (null == presenterPlayer) throw new Exception("presenterPlayer error: null");
            presenterPlayer.startHuawei(viewHolder.itemView, data);
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

//    public void checkedPlayerPositionNext() {
//        LogUtil.log("DetailGridView => checkedPlayerPositionNext =>");
//        try {
//            ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) getAdapter();
//            ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
//            Object o = objectAdapter.get(1);
//            if (null == o)
//                throw new Exception("objectAdapter null");
//            // 选集列表
//            if (o instanceof DetailTemplateXuanJi.DetailTemplateXuanJiList) {
//                DetailTemplateXuanJi presenter = getPresenter(DetailTemplateXuanJi.class);
//                if (null != presenter) {
//                    presenter.checkedPlayingPositionNext(this);
//                }
//            }
//            // 选期列表
//            else if (o instanceof DetailTemplateXuanQi.DetailTemplateXuanQiList) {
//                DetailTemplateXuanQi presenter = getPresenter(DetailTemplateXuanQi.class);
//                if (null != presenter) {
//                    presenter.checkedPlayingPositionNext(this);
//                }
//            }
//        } catch (Exception e) {
//            LogUtil.log("DetailGridView", "checkedPlayerPositionNext => " + e.getMessage());
//        }
//    }

    public int getPlayerNextPosition() {
        LogUtil.log("DetailGridView => getPlayerNextPosition =>");
        try {
            ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) getAdapter();
            ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
            Object o = objectAdapter.get(1);
            if (null == o) throw new Exception("objectAdapter null");
            // 选集列表
            if (o instanceof DetailTemplateXuanJi.DetailTemplateXuanJiList) {
                ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplateXuanJi.DetailTemplateXuanJiList.class);
                if (null == viewHolder) throw new Exception("viewHolder error: null");
                DetailTemplateXuanJi presenter = getPresenter(DetailTemplateXuanJi.class);
                if (null == presenter) throw new Exception("presenter error: null");
                return presenter.getPlayingPositionNext(viewHolder.itemView);
            }
            // 选期列表
            else if (o instanceof DetailTemplateXuanQi.DetailTemplateXuanQiList) {
                DetailTemplateXuanQi presenter = getPresenter(DetailTemplateXuanQi.class);
                if (null == presenter) throw new Exception("presenter error: null");
                return presenter.getPlayingPositionNext();
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
            if (null == o) throw new Exception("objectAdapter null");
            // 选集列表
            if (o instanceof DetailTemplateXuanJi.DetailTemplateXuanJiList) {
                ViewHolder viewHolder = findViewHolderForAdapterObject(DetailTemplateXuanJi.DetailTemplateXuanJiList.class);
                if (null == viewHolder) throw new Exception("viewHolder error: null");
                DetailTemplateXuanJi presenter = getPresenter(DetailTemplateXuanJi.class);
                if (null == presenter) throw new Exception("presenter error: null");
                return presenter.isPlayingPositionLast(viewHolder.itemView);
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
