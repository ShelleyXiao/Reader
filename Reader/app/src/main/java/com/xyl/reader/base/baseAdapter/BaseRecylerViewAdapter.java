package com.xyl.reader.base.baseAdapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-01-18
 * Time: 15:49
 * Company: zx
 * Description:
 * FIXME
 */


public abstract class BaseRecylerViewAdapter<T> extends RecyclerView.Adapter<BaseRecylerViewHolder> {

    protected List<T> data = new ArrayList<>();
    protected OnItemClickListener<T> mOnItemClickListener;
    protected OnItemLongClikListener<T> mOnItemLongClikListener;


    @Override
    public void onBindViewHolder(BaseRecylerViewHolder holder, int position) {
        holder.onBaseBindViewHolder(data.get(position), position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addAll(List<T> datas) {
        data.addAll(datas);
    }

    public void clear() {
        data.clear();
    }

    public void remove(T t) {
        data.remove(t);
    }

    public void remove(int position) {
        data.remove(position);
    }

    public void removeAll(List<T> datas) {
        data.removeAll(datas);
    }

    public List<T> getData(){
        return data;
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClikListener(OnItemLongClikListener<T> onItemLongClikListener) {
        mOnItemLongClikListener = onItemLongClikListener;
    }
}
