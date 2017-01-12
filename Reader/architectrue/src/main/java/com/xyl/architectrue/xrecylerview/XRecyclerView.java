package com.xyl.architectrue.xrecylerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.xyl.architectrue.utils.NetworkUtils;

/**
 * User: ShaudXiao
 * Date: 2017-01-11
 * Time: 11:15
 * Company: zx
 * Description:
 * FIXME
 */


public class XRecyclerView extends RecyclerView {

    private LoadingListener mLoadingListener;
    private WrapAdapter mWrapAdapter;
    private LoadingMoreFooter mLoadingMoreFooter;
    private YunRefreshHeader mYunRefreshHeader;

    private SparseArray<View> mHeaderView;
    private SparseArray<View> mFooterView;

    private boolean isPullRefreshEnable = true;
    private boolean isLoadMoreEnable = true;

    private boolean isLoadingData;

    private int previousTotal;

    public boolean isnomore;

    private float lastY = -1;

    private static final float DRAG_RATE = 1.75F;

    private boolean isOther = false;


    public XRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public XRecyclerView(Context context) {
        this(context, null);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);

        if (state == RecyclerView.SCROLL_STATE_IDLE && mLoadingListener != null
                && !isLoadingData && isLoadMoreEnable) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisiableItemPosition;
            if (layoutManager instanceof GridLayoutManager) {
                lastVisiableItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                lastVisiableItemPosition = findMax(into);
            } else {
                lastVisiableItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }

            if (layoutManager.getChildCount() > 0
                    && lastVisiableItemPosition >= layoutManager.getItemCount() - 1
                    && layoutManager.getItemCount() > layoutManager.getChildCount()
                    && !isnomore
                    && mYunRefreshHeader.getState() < YunRefreshHeader.STATE_REFRESHING) {
                View footView = mFooterView.get(0);
                isLoadingData = true;
                if (footView instanceof LoadingMoreFooter) {
                    ((LoadingMoreFooter) footView).setSate(LoadingMoreFooter.STATE_LOAING);
                } else {
                    footView.setVisibility(View.VISIBLE);
                }

                if (NetworkUtils.isConnected()) {
                    mLoadingListener.onLoadMore();
                } else {
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mLoadingListener.onLoadMore();
                        }
                    }, 1000);
                }
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (lastY == -1) {
            lastY = e.getRawY();
        }

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = e.getRawY() - lastY;
                mYunRefreshHeader.onMore(deltaY / DRAG_RATE);
                if (mYunRefreshHeader.getVisiableHeight() > 0 && mYunRefreshHeader.getState() < YunRefreshHeader.STATE_REFRESHING) {
                    return false;
                }
                break;
            default:
                lastY = -1;
                if (isOnTop() && isPullRefreshEnable) {
                    if (mYunRefreshHeader.releaseAction()) {
                        if (mLoadingListener != null) {
                            mLoadingListener.onRefresh();
                            isnomore = false;
                            previousTotal = 0;
                            final View view = mHeaderView.get(0);
                            if (view instanceof LoadingMoreFooter) {
                                if (view.getVisibility() != View.GONE) {
                                    view.setVisibility(View.GONE);
                                }
                            }
                        }
                    }

                }
                break;
        }


        return super.onTouchEvent(e);
    }

    private final RecyclerView.AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            mWrapAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    };

    public boolean isOnTop() {
        if (mHeaderView == null || mHeaderView.size() == 0) {
            return false;
        }

        View view = mHeaderView.get(0);
        if (view.getParent() != null) {
            return true;
        } else {
            return false;
        }
    }

    private void initView(Context context) {
        if (isPullRefreshEnable) {
            YunRefreshHeader yunRefreshHeader = new YunRefreshHeader(context);
            mHeaderView.put(0, yunRefreshHeader);
            this.mYunRefreshHeader = yunRefreshHeader;
        }

        LoadingMoreFooter footer = new LoadingMoreFooter(context);
        addFootView(footer, false);
        mFooterView.get(0).setVisibility(View.GONE);

    }

    public void addHeaderView(View view) {
        if (isPullRefreshEnable && !(mHeaderView.get(0) instanceof YunRefreshHeader)) {
            YunRefreshHeader yunRefreshHeader = new YunRefreshHeader(getContext());
            mHeaderView.put(0, yunRefreshHeader);
            this.mYunRefreshHeader = yunRefreshHeader;
        }

        mHeaderView.put(mHeaderView.size(), view);
    }

    public void clearHeaderView() {
        mHeaderView.clear();
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int height = (int) (1.0f * scale + 0.5f);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        View view = new View(getContext());
        view.setLayoutParams(lp);
        mHeaderView.put(0, view);
    }

    public void addFootView(View view, boolean isOther) {
        mFooterView.clear();
        mFooterView.put(0, view);
        this.isOther = isOther;
    }

    public void noMoreLoading() {
        isLoadingData = false;
        final View footView = mFooterView.get(0);
        isnomore = true;
        if (footView instanceof LoadingMoreFooter) {
            ((LoadingMoreFooter) footView).setSate(LoadingMoreFooter.STATE_NOMORE);
        } else {
            footView.setVisibility(View.GONE);
        }

        if (isOther) {
            footView.setVisibility(View.VISIBLE);
        }
    }

    public void refreshComplete() {
        if (isLoadingData) {
            loadMoreComplete();
        } else {
            mYunRefreshHeader.refreshComplete();
        }
    }


    public void setLoadingListener(LoadingListener loadingListener) {
        mLoadingListener = loadingListener;
    }

    public void setPullRefreshEnable(boolean pullRefreshEnable) {
        isPullRefreshEnable = pullRefreshEnable;
    }

    public void setLoadMoreEnable(boolean loadMoreEnable) {
        isLoadMoreEnable = loadMoreEnable;
        if (!isLoadMoreEnable) {
            if (mFooterView != null) {
                mFooterView.remove(0);
            }

        } else {
            if (mFooterView != null) {
                LoadingMoreFooter footer = new LoadingMoreFooter(getContext());
                mFooterView.put(0, footer);
            }
        }
    }

    public void setLoadMoreGong() {
        if (mFooterView != null) {
            View footView = mFooterView.get(0);
            if (null != footView && footView instanceof LoadingMoreFooter) {
                mFooterView.remove(0);
            }
        }
    }

    public void reset() {
        isnomore = false;
        final View footView = mFooterView.get(0);
        if (footView instanceof LoadingMoreFooter) {
            ((LoadingMoreFooter) footView).reset();
        }
    }

    public interface LoadingListener {

        void onRefresh();

        void onLoadMore();

    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int values : lastPositions) {
            if (values > max) {
                max = values;
            }
        }

        return max;
    }

    private int findMin(int[] firstPositions) {
        int min = firstPositions[0];
        for (int values : firstPositions) {
            if (values < min) {
                min = values;
            }
        }

        return min;
    }

    private void loadMoreComplete() {
        isLoadingData = false;
        View footView = mFooterView.get(0);
        if (previousTotal < getLayoutManager().getItemCount()) {
            if (footView instanceof LoadingMoreFooter) {
                ((LoadingMoreFooter) footView).setSate(LoadingMoreFooter.STATE_COMPLETE);
            } else {
                footView.setVisibility(View.GONE);
            }
        } else {
            if (footView instanceof LoadingMoreFooter) {
                ((LoadingMoreFooter) footView).setSate(LoadingMoreFooter.STATE_NOMORE);
            } else {
                footView.setVisibility(View.GONE);
            }
        }
        previousTotal = getLayoutManager().getItemCount();
    }
}
