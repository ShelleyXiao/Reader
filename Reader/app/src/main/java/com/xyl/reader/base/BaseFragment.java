package com.xyl.reader.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xyl.architectrue.utils.LogUtils;
import com.xyl.reader.R;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * User: ShaudXiao
 * Date: 2017-01-12
 * Time: 16:27
 * Company: zx
 * Description:
 * FIXME
 */


public abstract class BaseFragment<BV extends ViewDataBinding> extends Fragment {

    protected BV bindingContentView;

    protected boolean mIsVisiable = false;

    private LinearLayout mLvErrorRefresh;
    private LinearLayout mLvProgressBar;

    private RelativeLayout mContainer;

    private AnimationDrawable mAnimationDrawable;
    private CompositeSubscription mCompositeSubscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View containerRoot = inflater.inflate(R.layout.fragment_base, container ,false);
        bindingContentView = DataBindingUtil.inflate(getActivity().getLayoutInflater(), setContent(), null, false);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        bindingContentView.getRoot().setLayoutParams(layoutParams);
        mContainer = (RelativeLayout) containerRoot.findViewById(R.id.container);
        mContainer.addView(bindingContentView.getRoot());
        LogUtils.e("   ");
        return containerRoot;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.e("  setUserVisibleHint  ");
        if(getUserVisibleHint()) {
            mIsVisiable = true;
            loadData();
        } else {
            mIsVisiable = false;
            isInVisiable();
        }
    }

    /*
    * 显示加载数据
    * 生命周期先会执行一次 setUserVisibleHint 在执行 onActivityCreated
    * 在onActivityCreated 之后第一次显示加载数据，只加载一次
    * */
    protected void loadData() {

    }

    protected  void isInVisiable() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLvProgressBar = getView(R.id.ll_progress_bar);
        ImageView progress = getView(R.id.img_process);
        mAnimationDrawable = (AnimationDrawable) progress.getDrawable();
        if(! mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }

        mLvErrorRefresh = getView(R.id.ll_error_refresh);
        mLvErrorRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                onRefresh();
            }
        });

        bindingContentView.getRoot().setVisibility(View.GONE);
    }

    /*
    * 加载失败后的操作
    * */
    protected void onRefresh() {

    }


    protected <T extends  View> T getView(int id) {
        return (T) getView().findViewById(id);
    }

    /*
    * 内容布局
    * */
    protected  abstract int setContent();

    protected void showLoading() {
        if(mLvProgressBar.getVisibility() != View.VISIBLE) {
            mLvProgressBar.setVisibility(View.VISIBLE);
        }

        if(! mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }

        if(bindingContentView.getRoot().getVisibility() != View.GONE) {
            bindingContentView.getRoot().setVisibility(View.GONE);
        }

        if(mLvErrorRefresh.getVisibility() != View.GONE) {
            mLvErrorRefresh.setVisibility(View.GONE);
        }
    }

    protected void showContentView() {
        if(mLvProgressBar.getVisibility() != View.GONE) {
            mLvProgressBar.setVisibility(View.GONE);
        }

        if( mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }

        if(mLvErrorRefresh.getVisibility() != View.GONE) {
            mLvErrorRefresh.setVisibility(View.GONE);
        }

        if(bindingContentView.getRoot().getVisibility() != View.VISIBLE) {
            bindingContentView.getRoot().setVisibility(View.VISIBLE);
        }
    }

    protected void showErrorRefresh() {
        if(mLvProgressBar.getVisibility() != View.GONE) {
            mLvProgressBar.setVisibility(View.GONE);
        }

        if( mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }

        if(bindingContentView.getRoot().getVisibility() != View.GONE) {
            bindingContentView.getRoot().setVisibility(View.GONE);
        }

        if(mLvErrorRefresh.getVisibility() != View.VISIBLE) {
            mLvErrorRefresh.setVisibility(View.VISIBLE);
        }

    }

    protected void addSubscription(Subscription s) {
        if(this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(this.mCompositeSubscription != null && this.mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
