package com.xyl.reader.base.baseAdapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * User: ShaudXiao
 * Date: 2017-01-18
 * Time: 15:43
 * Company: zx
 * Description:
 * FIXME
 */


public abstract class BaseRecylerViewHolder<T, D extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public D binding;

    public BaseRecylerViewHolder(ViewGroup viewGroup, int layoutId) {
        super(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), layoutId, viewGroup, false).getRoot());
        binding = DataBindingUtil.getBinding(this.itemView);
    }

    public abstract void onBindingViewHolder(T object, final int position);

    /**
     * 当数据改变是，binding会在下一帧改变数据， 如果需要立即改变则要手动调用 executePendingBindings；
     * */
    void onBaseBindViewHolder(T object , final int position) {
        onBindingViewHolder(object, position);
        binding.executePendingBindings();
    }

}
