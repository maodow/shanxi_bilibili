package tv.huan.bilibili.widget.keyboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * RecyclerView 适配器基类
 * Author: yhw
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public BaseRecyclerViewAdapter(Context context){
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public BaseRecyclerViewAdapter(Context context,List<T> datas){
        this(context);
        setDatas(datas);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new BaseRecyclerViewHolder(mInflater.inflate(onLayoutId(viewType),parent,false));
    }

    /**
     * 获取布局文件
     * @param viewType 布局类型
     */
    public abstract int onLayoutId(int viewType);

    @Override
    public abstract void onBindViewHolder(BaseRecyclerViewHolder holder, int position) ;

    @Override
    public int getItemCount() {
        return this.mDatas==null? 0: this.mDatas.size();
    }

    public void setDatas(List<T> datas) {
        this.mDatas = datas;
    }

    /**
     * 清除数据
     */
    public void clearDatas(){
        this.mDatas.clear();
        this.notifyDataSetChanged();
    }

    /**
     * 追加数据
     */
    public void appendDatas(List<T> datas) {
        if(null == datas) return;
        int size = mDatas.size();
        this.mDatas.addAll(datas);
        notifyItemRangeInserted(size, datas.size());
    }

    /**
     * 移除数据
     * @param postion 索引
     */
    public void removeItem(int postion) {
        if(null != mDatas && postion < mDatas.size()) {
            mDatas.remove(postion);
            notifyItemRemoved(postion);
        }
    }

    /**
     * 移动数据
     * @param form 从第几个
     * @param to 移动到第几个
     */
    public void movedItem(int form, int to) {
        if(null != mDatas && !mDatas.isEmpty()) {
            if (form < 0 || form >= getItemCount() || to < 0 || to >= getItemCount()) {
                return;
            }
            T item = mDatas.get(form);
            mDatas.remove(form);
            mDatas.add(to-1, item);
            notifyItemMoved(form, to);
        }
    }
}
