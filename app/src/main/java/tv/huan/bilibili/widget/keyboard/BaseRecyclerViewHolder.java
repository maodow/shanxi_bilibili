package tv.huan.bilibili.widget.keyboard;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: yhw
 */

public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder{
    private SparseArray<View> mViews;

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        mViews=new SparseArray<>();
    }

    /**
     * 通过该方法获取view
     * @param viewId view id
     * @param <T> view 对象
     */
    public <T extends View> T getView(int viewId){
        View view=mViews.get(viewId);
        if(view==null){
            view=itemView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T)view;
    }

    public void setText(int viewId,String text){
        TextView textView=getView(viewId);
        textView.setText(text);
    }
    public void setText(int viewId,int textId){
        TextView textView=getView(viewId);
        textView.setText(textId);
    }

    public void setImageResource(int viewId,int resId){
        ImageView imageView=getView(viewId);
        imageView.setImageResource(resId);
    }
}
