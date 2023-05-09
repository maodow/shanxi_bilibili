package tv.huan.bilibili.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.Presenter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import lib.kalu.frame.mvp.util.CacheUtil;
import lib.kalu.leanback.list.LeanBackVerticalGridView;
import lib.kalu.leanback.util.LeanBackUtil;
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.bean.local.LocalBean;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate1;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate16;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate17;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate20;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate21;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate22;
import tv.huan.bilibili.utils.LogUtil;

public final class GeneralGridView extends LeanBackVerticalGridView {
    public GeneralGridView(@NonNull Context context) {
        super(context);
//        setItemViewCacheSize(100);
    }

    public GeneralGridView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        setItemViewCacheSize(100);
    }

    public GeneralGridView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        setItemViewCacheSize(100);
    }

//    private final OnScrollListener mOnScrollListener = new OnScrollListener() {
//        @Override
//        public void onScrollStateChanged(@NonNull androidx.recyclerview.widget.RecyclerView recyclerView, int newState) {
//            super.onScrollStateChanged(recyclerView, newState);
//            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                GlideUtils.resumeRequests(getContext());
//            } else {
//                GlideUtils.pauseRequests(getContext());
//            }
//        }
//    };

//    @Override
//    protected void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        addOnScrollListener(mOnScrollListener);
//    }
//
//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        removeOnScrollListener(mOnScrollListener);
//    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {

        // down => action_down
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            int repeatCount = event.getRepeatCount();
            int scrollState = getScrollState();
            if (repeatCount >= 1 || scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                return true;
            } else {
                if (null != mOnScrollTopListener) {
                    int focusedChildPosition = findFocusPosition();
                    if (focusedChildPosition >= 1) {
                        mOnScrollTopListener.onHide();
                    }
                }
            }
        }
        // down => action_up
        else if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            if (null != mOnScrollTopListener) {
                int focusedChildPosition = findFocusPosition();
                if (focusedChildPosition >= 1) {
                    mOnScrollTopListener.onHide();
                }
            }
        }
        // up => action_down
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            int repeatCount = event.getRepeatCount();
            int scrollState = getScrollState();
            if (repeatCount >= 1 || scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                return true;
            } else {
                if (null != mOnScrollTopListener) {
                    int focusPosition = findFocusPosition();
                    if (focusPosition <= 0) {
                        mOnScrollTopListener.onShow();
                    }
                }
            }
        }
        // up => action_up
        else if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            if (null != mOnScrollTopListener) {
                int focusPosition = findFocusPosition();
                if (focusPosition <= 0) {
                    mOnScrollTopListener.onShow();
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public View getFocusedChild() {
        try {
            View focusedChild = findFocus();
            if (null == focusedChild)
                throw new Exception("focusedChild is null");
            return focusedChild;
        } catch (Exception e) {
            LeanBackUtil.log("GeneralGridView => findFocusChild => " + e.getMessage());
            return null;
        }
    }

    private int findFocusPosition() {
        try {
            View focusedChild = getFocusedChild();
            if (null == focusedChild)
                throw new Exception("focusedChild is null");
            int position = -1;
            while (true) {
                ViewParent parent = focusedChild.getParent();
                if (parent instanceof GeneralGridView) {
                    position = getChildAdapterPosition(focusedChild);
                    break;
                }
                focusedChild = (View) parent;
            }
            if (position == -1)
                throw new Exception("position error: " + position);
            return position;
        } catch (Exception e) {
            LeanBackUtil.log("GeneralGridView => findFocusPosition => " + e.getMessage());
            return -1;
        }
    }

    @Override
    public void scrollTop(boolean hasFocus) {
        super.scrollTop(hasFocus);
        if (null != mOnScrollTopListener) {
            mOnScrollTopListener.onShow();
        }
    }

    public void pauseMessage() {
        GeneralTemplate17 template17 = getPresenter(GeneralTemplate17.class);
        if (null != template17) {
            template17.pauseMessage();
        }
        GeneralTemplate16 template16 = getPresenter(GeneralTemplate16.class);
        if (null != template16) {
            template16.pauseMessage();
        }
        GeneralTemplate1 template1 = getPresenter(GeneralTemplate1.class);
        if (null != template1) {
            template1.pauseMessage();
        }
    }

    public void resumeMessage() {
        GeneralTemplate17 template17 = getPresenter(GeneralTemplate17.class);
        if (null != template17) {
            template17.resumeMessage();
        }
        GeneralTemplate16 template16 = getPresenter(GeneralTemplate16.class);
        if (null != template16) {
            template16.resumeMessage();
        }
        GeneralTemplate1 template1 = getPresenter(GeneralTemplate1.class);
        if (null != template1) {
            template1.resumeMessage();
        }
    }

    public void cleanTemplatePlayerMessageDelayed() {
        GeneralTemplate21 template21 = getPresenter(GeneralTemplate21.class);
        if (null != template21) {
            ViewHolder viewHolder21 = findViewHolderForAdapterObject(GeneralTemplate21.GeneralTemplate21List.class);
            if (null != viewHolder21) {
                template21.cleanTemplatePlayerMessageDelayed();
            }
        }
        GeneralTemplate22 template22 = getPresenter(GeneralTemplate22.class);
        if (null != template22) {
            template22.cleanTemplatePlayerMessageDelayed();
        }
    }

    /***************/

    public boolean containsTemplateHistory() {
        try {
            ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) getAdapter();
            ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
            int size = objectAdapter.size();
            for (int i = 0; i < size; i++) {
                Object o = objectAdapter.get(i);
                if (null == o)
                    continue;
                // GeneralTemplate16
                if (o instanceof GeneralTemplate16.GeneralTemplate16List) {
                    return true;
                }
                // GeneralTemplate17
                else if (o instanceof GeneralTemplate17.GeneralTemplate17List) {
                    return true;
                }
                // GeneralTemplate20
                else if (o instanceof GeneralTemplate20.GeneralTemplate20List) {
                    return true;
                }
            }
            throw new Exception("not find");
        } catch (Exception e) {
            return false;
        }
    }

    public void updateTemplateHistory() {

        try {
            // 1 => search
            List<LocalBean> newList = new LinkedList<>();
            try {
                String s = CacheUtil.getCache(getContext(), BuildConfig.HUAN_JSON_LOCAL_HISTORY);
                if (null == s || s.length() <= 0)
                    throw new Exception();
                Type type = new TypeToken<List<LocalBean>>() {
                }.getType();
                List<LocalBean> list = new Gson().fromJson(s, type);
                if (null == list || list.size() <= 0)
                    throw new Exception();
                newList.addAll(list);
            } catch (Exception e) {
            }
            // 2 => update
            ItemBridgeAdapter itemBridgeAdapter = (ItemBridgeAdapter) getAdapter();
            if (null == itemBridgeAdapter)
                throw new Exception("itemBridgeAdapter error: null");
            ArrayObjectAdapter objectAdapter = (ArrayObjectAdapter) itemBridgeAdapter.getAdapter();
            if (null == objectAdapter)
                throw new Exception("objectAdapter error: null");
            int size = objectAdapter.size();
            if (size <= 0)
                throw new Exception("size error: " + size);
            for (int i = 0; i < size; i++) {
                Object o = objectAdapter.get(i);
                if (null == o)
                    continue;
                // GeneralTemplate16
                if (o instanceof GeneralTemplate16.GeneralTemplate16List) {
                    GeneralTemplate16 template = getPresenter(GeneralTemplate16.class);
                    if (null == template)
                        continue;
                    GetSubChannelsByChannelBean.ListBean.TemplateBean t = (GetSubChannelsByChannelBean.ListBean.TemplateBean) ((GeneralTemplate16.GeneralTemplate16List) o).get(0);
                    t.setTempHistoryLocalData(newList);
                    ViewHolder viewHolder = findViewHolderForAdapterPosition(i);
                    template.updateHistory(viewHolder.itemView, newList);
                }
                // GeneralTemplate17
                else if (o instanceof GeneralTemplate17.GeneralTemplate17List) {
                    GeneralTemplate17 template = getPresenter(GeneralTemplate17.class);
                    if (null == template)
                        continue;
                    GetSubChannelsByChannelBean.ListBean.TemplateBean t = (GetSubChannelsByChannelBean.ListBean.TemplateBean) ((GeneralTemplate17.GeneralTemplate17List) o).get(0);
                    t.setTempHistoryLocalData(newList);
                    ViewHolder viewHolder = findViewHolderForAdapterPosition(i);
                    template.updateHistory(viewHolder.itemView, newList);
                }
                // GeneralTemplate20
                else if (o instanceof GeneralTemplate20.GeneralTemplate20List) {
                    GeneralTemplate20 template = getPresenter(GeneralTemplate20.class);
                    if (null == template)
                        continue;
                    GetSubChannelsByChannelBean.ListBean.TemplateBean t = (GetSubChannelsByChannelBean.ListBean.TemplateBean) ((GeneralTemplate20.GeneralTemplate20List) o).get(2);
                    t.setTempHistoryLocalData(newList);
                    ViewHolder viewHolder = findViewHolderForAdapterPosition(i);
                    template.updateHistory(viewHolder.itemView, newList);
                }
            }
        } catch (Exception e) {
        }
    }

//    public void pausePlayer() {
//        GeneralTemplate21 template21 = getPresenter(GeneralTemplate21.class);
//        if (null != template21) {
//            ViewHolder viewHolder21 = findViewHolderForAdapterObject(GeneralTemplate21.GeneralTemplate21List.class);
//            if (null != viewHolder21) {
//                template21.pausePlayer(viewHolder21.itemView);
//            }
//        }
//        GeneralTemplate22 template22 = getPresenter(GeneralTemplate22.class);
//        if (null != template22) {
//            ViewHolder viewHolder22 = findViewHolderForAdapterObject(GeneralTemplate22.GeneralTemplate22List.class);
//            if (null != viewHolder22) {
//                template22.pausePlayer((ViewGroup) viewHolder22.itemView);
//            }
//        }
//    }

    public void restartPlayer() {
        GeneralTemplate21 template21 = getPresenter(GeneralTemplate21.class);
        if (null != template21) {
            ViewHolder viewHolder21 = findViewHolderForAdapterObject(GeneralTemplate21.GeneralTemplate21List.class);
            if (null != viewHolder21) {
                template21.resumePlayer((ViewGroup) viewHolder21.itemView);
            }
        }
        GeneralTemplate22 template22 = getPresenter(GeneralTemplate22.class);
        if (null != template22) {
            ViewHolder viewHolder22 = findViewHolderForAdapterObject(GeneralTemplate22.GeneralTemplate22List.class);
            if (null != viewHolder22) {
                template22.sendMessageDelayedRestartPlayer((ViewGroup) viewHolder22.itemView);
            }
        }
    }

    public void releasePlayer() {
        GeneralTemplate21 template21 = getPresenter(GeneralTemplate21.class);
        LogUtil.log("GeneralGridView => releasePlayer => template21 = " + template21);
        if (null != template21) {
            ViewHolder viewHolder21 = findViewHolderForAdapterObject(GeneralTemplate21.GeneralTemplate21List.class);
            LogUtil.log("GeneralGridView => releasePlayer => viewHolder21 = " + viewHolder21);
            if (null != viewHolder21) {
                template21.releasePlayer((ViewGroup) viewHolder21.itemView);
            }
        }
        GeneralTemplate22 template22 = getPresenter(GeneralTemplate22.class);
        LogUtil.log("GeneralGridView => releasePlayer => template22 = " + template22);
        if (null != template22) {
            ViewHolder viewHolder22 = findViewHolderForAdapterObject(GeneralTemplate22.GeneralTemplate22List.class);
            LogUtil.log("GeneralGridView => releasePlayer => viewHolder22 = " + viewHolder22);
            if (null != viewHolder22) {
                template22.releasePlayer((ViewGroup) viewHolder22.itemView);
            }
        }
    }

    public <T extends Presenter> void startPlayerFromHuawei(Class<T> cls, Class<?> obj, String s) {
        T t = getPresenter(cls);
        if (null != t && t instanceof GeneralTemplate21) {
            ViewHolder viewHolder = findViewHolderForAdapterObject(obj);
            if (null != viewHolder) {
                ((GeneralTemplate21) t).startPlayer(viewHolder.itemView, s);
            }
        } else if (null != t && t instanceof GeneralTemplate22) {
            ViewHolder viewHolder = findViewHolderForAdapterObject(obj);
            if (null != viewHolder) {
                ((GeneralTemplate22) t).startPlayer(viewHolder.itemView, s);
            }
        }
    }

    /**************************/

    private OnScrollTopListener mOnScrollTopListener;

    public void setOnScrollTopListener(OnScrollTopListener l) {
        mOnScrollTopListener = l;
    }

    public interface OnScrollTopListener {
        void onShow();

        void onHide();
    }
}
