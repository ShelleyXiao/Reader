package com.xyl.architectrue.xrecylerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * User: ShaudXiao
 * Date: 2017-01-11
 * Time: 11:20
 * Company: zx
 * Description:
 * FIXME
 */


public class WrapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER_REFRESH = 0x01;
    private static final int TYPE_HEADER = 0x02;
    private static final int TYPE_NORMAL = 0x04;
    private static final int TYPE_FOOTER = 0x08;

    private RecyclerView.Adapter mAdapter;

    private SparseArray<View> mHeaderView;
    private SparseArray<View> mFooterView;

    private int mHeaderPosition = 1;

    public WrapAdapter(SparseArray<View> headerView, SparseArray<View> footerView, RecyclerView.Adapter adapter) {
        this.mHeaderView = headerView;
        this.mFooterView = footerView;
        this.mAdapter = adapter;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeader(position) || isFooter(position)) ? manager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && (isFooter(holder.getLayoutPosition()) || isHeader(holder.getLayoutPosition()))) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) lp;
            layoutParams.setFullSpan(true);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER_REFRESH) {
            return new SimpleViewHolder(mHeaderView.get(0));
        } else if (viewType == TYPE_HEADER) {
            return new SimpleViewHolder(mHeaderView.get(mHeaderPosition++));
        } else if (viewType == TYPE_FOOTER) {
            return new SimpleViewHolder(mFooterView.get(0));
        }

        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeader(position)) {
            return;
        }

        int ajustPosition = position - getHeaderCount();
        int adapterCount;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (ajustPosition < adapterCount) {
                mAdapter.onBindViewHolder(holder, ajustPosition);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (null != mAdapter) {
            return mHeaderView.size() + mFooterView.size() + mAdapter.getItemCount();
        } else {
            return mHeaderView.size() + mFooterView.size();
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (isRefreshHeader(position)) {
            return TYPE_HEADER_REFRESH;
        } else if (isHeader(position)) {
            return TYPE_HEADER;
        } else if (isFooter(position)) {
            return TYPE_FOOTER;
        }
        int adjPosition = position - getHeaderCount();
        int adapterCount;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }

        return TYPE_NORMAL;
    }

    @Override
    public long getItemId(int position) {
        if(mAdapter != null && position > mHeaderView.size()) {
            int adjPosition = position - getHeaderCount();
            int adpterCount = mAdapter.getItemCount();
            if(adjPosition < adpterCount) {
                return mAdapter.getItemId(adjPosition);
            }
        }

        return -1;
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        if(null != mAdapter) {
            mAdapter.registerAdapterDataObserver(observer);
        }
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        if(null != mAdapter) {
            mAdapter.unregisterAdapterDataObserver(observer);
        }
    }

    public boolean isHeader(int position) {
        return position >= 0 && (position < mHeaderView.size());
    }

    public boolean isFooter(int position) {
        return position < getItemCount() && position >= getItemCount() - mFooterView.size();
    }

    public boolean isRefreshHeader(int position) {
        return position == 0;
    }

    public int getHeaderCount() {
        return mHeaderView.size();
    }

    public int getFootterCount() {
        return mFooterView.size();
    }

    private class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }

}
