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

import com.google.gson.Gson;

import lib.kalu.leanback.list.LeanBackVerticalGridView;
import lib.kalu.leanback.list.RecyclerView;
import lib.kalu.leanback.util.LeanBackUtil;
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.bean.FavBean;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
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
        init();
    }

    public GeneralGridView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GeneralGridView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
//        addOnScrollListener(new OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull androidx.recyclerview.widget.RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    GlideUtils.resumeRequests(getContext());
//                } else {
//                    GlideUtils.pauseRequests(getContext());
//                }
//            }
//        });
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {

//        try {
//            int repeatCount = event.getRepeatCount();
//            if (repeatCount > 0)
//                return true;
//        } catch (Exception e) {
//        }

        try {
            // 停止滚动 SCROLL_STATE_IDLE
            // 正在被外部拖拽,一般为用户正在用手指滚动 SCROLL_STATE_DRAGGING
            // 自动滚动开始 SCROLL_STATE_SETTLING
            int state = getScrollState();
            if (state != RecyclerView.SCROLL_STATE_IDLE) {
                //GlideUtils.pauseRequests(getContext());
                return true;
            } else {
                //GlideUtils.resumeRequests(getContext());
            }
        } catch (Exception e) {
        }

        // down
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            if (null != mOnScrollTopListener) {
                int focusedChildPosition = getFocusedChildPosition();
                if (focusedChildPosition >= 1) {
                    mOnScrollTopListener.onHide();
                }
            }
        }
        // up
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            if (null != mOnScrollTopListener) {
                ViewHolder viewHolder = findViewHolderForLayoutPosition(1);
                if (null != viewHolder && null != viewHolder.itemView && viewHolder.itemView.getVisibility() == View.VISIBLE) {
                    mOnScrollTopListener.onShow();
                }
            }
        }

        return super.dispatchKeyEvent(event);
    }

    private int getFocusedChildPosition() {
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
            LeanBackUtil.log("GeneralGridView => getFocusedChildPosition => " + e.getMessage());
            return -1;
        }
    }

    public void scrollTop() {
        if (null != mOnScrollTopListener) {
            mOnScrollTopListener.onShow();
        }
        scrollUp(0);
    }

//    public boolean isTop() {
//        try {
//            ViewHolder viewHolder = findViewHolderForLayoutPosition(0);
//            viewHolder.itemView;
//        } catch (Exception e) {
//            return false;
//        }
//    }

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

    public void updateTemplateHistory(@NonNull FavBean data) {

        if (BuildConfig.DEBUG) {
            LogUtil.log("GeneralGridView => updateTemplateHistory => " + new Gson().toJson(data));
        }

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
                    try {
                        GetSubChannelsByChannelBean.ListBean.TemplateBean t = (GetSubChannelsByChannelBean.ListBean.TemplateBean) ((GeneralTemplate16.GeneralTemplate16List) o).get(0);
                        t.setTempFav(data);
                        // 刷新
                        GeneralTemplate16 template = getPresenter(GeneralTemplate16.class);
                        if (null != template) {
                            ViewHolder viewHolder = findViewHolderForAdapterPosition(i);
                            template.updateHistory(viewHolder.itemView, data);
                        }
                    } catch (Exception e) {
                        LogUtil.log("GeneralGridView => updateTemplateHistory => " + e.getMessage());
                    }
                }
                // GeneralTemplate17
                else if (o instanceof GeneralTemplate17.GeneralTemplate17List) {
                    try {
                        GetSubChannelsByChannelBean.ListBean.TemplateBean t = (GetSubChannelsByChannelBean.ListBean.TemplateBean) ((GeneralTemplate17.GeneralTemplate17List) o).get(0);
                        t.setTempFav(data);
                        // 刷新
                        GeneralTemplate17 template = getPresenter(GeneralTemplate17.class);
                        if (null != template) {
                            ViewHolder viewHolder = findViewHolderForAdapterPosition(i);
                            template.updateHistory(viewHolder.itemView, data);
                        }
                    } catch (Exception e) {
                        LogUtil.log("GeneralGridView => updateTemplateHistory => " + e.getMessage());
                    }
                }
                // GeneralTemplate20
                else if (o instanceof GeneralTemplate20.GeneralTemplate20List) {
                    try {
                        GetSubChannelsByChannelBean.ListBean.TemplateBean t = (GetSubChannelsByChannelBean.ListBean.TemplateBean) ((GeneralTemplate20.GeneralTemplate20List) o).get(2);
                        t.setTempFav(data);
                        // 刷新
                        GeneralTemplate20 template = getPresenter(GeneralTemplate20.class);
                        if (null != template) {
                            ViewHolder viewHolder = findViewHolderForAdapterPosition(i);
                            template.updateHistory(viewHolder.itemView, data);
                        }
                    } catch (Exception e) {
                        LogUtil.log("GeneralGridView => updateTemplateHistory => " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public void pausePlayer() {
        GeneralTemplate21 template21 = getPresenter(GeneralTemplate21.class);
        if (null != template21) {
            ViewHolder viewHolder21 = findViewHolderForAdapterObject(GeneralTemplate21.GeneralTemplate21List.class);
            if (null != viewHolder21) {
                template21.pausePlayer((ViewGroup) viewHolder21.itemView);
            }
        }
        GeneralTemplate22 template22 = getPresenter(GeneralTemplate22.class);
        if (null != template22) {
            ViewHolder viewHolder22 = findViewHolderForAdapterObject(GeneralTemplate22.GeneralTemplate22List.class);
            if (null != viewHolder22) {
                template22.pausePlayer((ViewGroup) viewHolder22.itemView);
            }
        }
    }

    public void resumePlayer() {
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
                template22.resumePlayer((ViewGroup) viewHolder22.itemView);
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
