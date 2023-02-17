package tv.huan.bilibili.ui.main.general;

import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.leanback.widget.VerticalGridView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lib.kalu.frame.mvp.BaseFragment;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.ui.main.MainActivity;

@Keep
public class GeneralFragment extends BaseFragment<GeneralView, GeneralPresenter> implements GeneralView {

    public static final String BUNDLE_CHANNELID = "bundle_channelId";
    public static final String BUNDLE_CLASSID = "bundle_classId";
    public static final String BUNDLE_NAME = "bundle_name";
    public static final String BUNDLE_INDEX = "bundle_index";

    @Override
    public int initLayout() {
        return R.layout.fragment_general;
    }

    @Override
    public void initData() {

        // adapter
        getPresenter().setAdapter();
        // request
        getPresenter().request();
        // listener
        VerticalGridView gridView = findViewById(R.id.general_list);
        gridView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // down
                if (dy > 0) {
                    RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForLayoutPosition(0);
                    if (null != viewHolder && null != viewHolder.itemView) {
                        ((MainActivity) getActivity()).hideTitle();
                    }
                }
                // up
                else if (dy < 0) {
                    RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForLayoutPosition(0);
                    if (null != viewHolder && null != viewHolder.itemView) {
                        ((MainActivity) getActivity()).showTitle();
                    }
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

//                // stop
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
////                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    View focus = recyclerView.findFocus();
//                    View under = recyclerView.findChildViewUnder(focus.getX(), focus.getY());
//                    int position = recyclerView.getChildAdapterPosition(under);
//                    Log.e("CommonFragment", "newState = " + newState + ", position = " + position + ", focus = " + focus + ", under = " + under);
////                    ((lib.kalu.frame.mvp.BaseActivity) getActivity()).onCall(position > 0 ? 1 : 0, null);
//                }
            }
        });
    }

    @Override
    public void clearMessage() {
        Log.e("GeneralFragment", "clearMessage => this = " + this);
    }

    @Override
    public void refreshContent() {
        Log.e("GeneralFragment", "refreshContent => " + R.id.general_list);
        notifyDataSetChanged(R.id.general_list);
    }
}
