package com.xyl.reader.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jaeger.library.StatusBarUtil;
import com.xyl.architectrue.utils.CommonUtils;
import com.xyl.reader.R;
import com.xyl.reader.databinding.ActivitiyBaseBinding;
import com.xyl.reader.utils.PerfectClickListener;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * User: ShaudXiao
 * Date: 2017-01-12
 * Time: 17:30
 * Company: zx
 * Description:
 * FIXME
 */


public class BaseActivity<BV extends ViewDataBinding> extends AppCompatActivity {

    protected BV bindingContentView;

    private LinearLayout mLvErrorRefresh;
    private LinearLayout mLvProgressBar;

    private RelativeLayout mContainer;

    private AnimationDrawable mAnimationDrawable;
    private CompositeSubscription mCompositeSubscription;

    private ActivitiyBaseBinding mBaseBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null && this.mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activitiy_base, null, false);
        bindingContentView = DataBindingUtil.inflate(LayoutInflater.from(this), layoutResID, null, false);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        bindingContentView.getRoot().setLayoutParams(layoutParams);
        mContainer = (RelativeLayout) mBaseBinding.getRoot().findViewById(R.id.container);
        mContainer.addView(bindingContentView.getRoot());
        getWindow().setContentView(mBaseBinding.getRoot());

        StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.colorTheme), 0);
        setToolbar();

        mLvProgressBar = getView(R.id.ll_progress_bar);
        ImageView progress = getView(R.id.img_process);
        mAnimationDrawable = (AnimationDrawable) progress.getDrawable();
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        mLvErrorRefresh = getView(R.id.ll_error_refresh);
        mLvErrorRefresh.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                showLoading();
                onRefresh();
            }
        });

        bindingContentView.getRoot().setVisibility(View.GONE);
    }

    protected void setToolbar() {
        setSupportActionBar(mBaseBinding.toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        }

        mBaseBinding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setTitle(CharSequence title) {
        mBaseBinding.toolBar.setTitle(title);
    }

    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }

    /*
    * 加载失败后的操作
    * */
    protected void onRefresh() {

    }


    protected void showLoading() {
        if (mLvProgressBar.getVisibility() != View.VISIBLE) {
            mLvProgressBar.setVisibility(View.VISIBLE);
        }

        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }

        if (bindingContentView.getRoot().getVisibility() != View.GONE) {
            bindingContentView.getRoot().setVisibility(View.GONE);
        }

        if (mLvErrorRefresh.getVisibility() != View.GONE) {
            mLvErrorRefresh.setVisibility(View.GONE);
        }
    }

    protected void showContentView() {
        if (mLvProgressBar.getVisibility() != View.GONE) {
            mLvProgressBar.setVisibility(View.GONE);
        }

        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }

        if (mLvErrorRefresh.getVisibility() != View.GONE) {
            mLvErrorRefresh.setVisibility(View.GONE);
        }

        if (bindingContentView.getRoot().getVisibility() != View.VISIBLE) {
            bindingContentView.getRoot().setVisibility(View.VISIBLE);
        }
    }

    protected void showErrorRefresh() {
        if (mLvProgressBar.getVisibility() != View.GONE) {
            mLvProgressBar.setVisibility(View.GONE);
        }

        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }

        if (bindingContentView.getRoot().getVisibility() != View.GONE) {
            bindingContentView.getRoot().setVisibility(View.GONE);
        }

        if (mLvErrorRefresh.getVisibility() != View.VISIBLE) {
            mLvErrorRefresh.setVisibility(View.VISIBLE);
        }

    }

    protected void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    public void removeSubscription() {
        if (this.mCompositeSubscription != null && this.mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
