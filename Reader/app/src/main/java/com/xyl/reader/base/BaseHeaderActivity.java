package com.xyl.reader.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.xyl.architectrue.utils.CommonUtils;
import com.xyl.architectrue.utils.StatusBarUtil;
import com.xyl.architectrue.utils.StatusBarUtils;
import com.xyl.reader.R;
import com.xyl.reader.databinding.BaseHeaderTitleBarBinding;
import com.xyl.reader.utils.PerfectClickListener;
import com.xyl.reader.view.MyNestedScrollView;

import jp.wasabeef.glide.transformations.BlurTransformation;
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


public abstract class BaseHeaderActivity<BV extends ViewDataBinding, HV extends ViewDataBinding> extends AppCompatActivity {

    //标题
    protected BaseHeaderTitleBarBinding mHeaderTitleBarBinding;

    // 内容头部布局
    protected  HV mHeaderContentView;

    //内容
    protected BV bindingContentView;

    private LinearLayout mLvErrorRefresh;
    private LinearLayout mLvProgressBar;

    private RelativeLayout mContainer;

    private AnimationDrawable mAnimationDrawable;
    private CompositeSubscription mCompositeSubscription;

    private int mImageHeght;
    //滑动多少距离后标题透明
    private int slidingDistance;

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
        View ll = getLayoutInflater().inflate(R.layout.activity_header_base, null);

        //内容
        bindingContentView = DataBindingUtil.inflate(LayoutInflater.from(this), layoutResID, null, false);
        //标题
        mHeaderTitleBarBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_header_base, null, false);
        //头部
        mHeaderContentView = DataBindingUtil.inflate(LayoutInflater.from(this), setHeaderLayout(), null, false);

        //tille
        RelativeLayout.LayoutParams titleLp = new RelativeLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mHeaderTitleBarBinding.getRoot().setLayoutParams(titleLp);
        RelativeLayout mTitleContainer = (RelativeLayout) ll.findViewById(R.id.title_container);
        mTitleContainer.addView(mHeaderTitleBarBinding.getRoot());
        getWindow().setContentView(ll);


        //header
        RelativeLayout.LayoutParams headerLp = new RelativeLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mHeaderContentView.getRoot().setLayoutParams(headerLp);
        RelativeLayout headerContainer = (RelativeLayout) ll.findViewById(R.id.header_container);
        headerContainer.addView(mHeaderContentView.getRoot());
        getWindow().setContentView(ll);

        //content
        RelativeLayout.LayoutParams contentLp = new RelativeLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        bindingContentView.getRoot().setLayoutParams(contentLp);
        RelativeLayout mContainer = (RelativeLayout) ll.findViewById(R.id.container);
        mContainer.addView(bindingContentView.getRoot());
        getWindow().setContentView(ll);

        StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.colorTheme), 0);

        initSildeShapeTheme(setHeaderImageUrl(), setHeaderImageView());

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
        setSupportActionBar(mHeaderTitleBarBinding.toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        }

        mHeaderTitleBarBinding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setTitle(CharSequence title) {
        mHeaderTitleBarBinding.toolBar.setTitle(title);
    }

    private void initSildeShapeTheme(String url, ImageView imageView) {
        setImgHeaderBg(url);

        int toolbarHeight = mHeaderTitleBarBinding.toolBar.getLayoutParams().height;
        final int headerHeight = toolbarHeight + StatusBarUtil.getStatusBarHeight(this);

        ViewGroup.LayoutParams layoutParams = mHeaderTitleBarBinding.ivBaseTilteBg.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mHeaderTitleBarBinding.ivBaseTilteBg.getLayoutParams();
        int marginTop = layoutParams.height - headerHeight;
        marginLayoutParams.setMargins(0, -marginTop, 0, 0);

        mHeaderTitleBarBinding.ivBaseTilteBg.setImageAlpha(0);
        StatusBarUtils.setTranslucentImageHeader(this, 0, mHeaderTitleBarBinding.ivBaseTilteBg);

        if(imageView != null) {
            ViewGroup.MarginLayoutParams layoutParams1 = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
            layoutParams1.setMargins(0, -StatusBarUtil.getStatusBarHeight(this), 0, 0);

            ViewGroup.LayoutParams imgLayoutParams = imageView.getLayoutParams();
            mImageHeght = imgLayoutParams.height;
        }

        initScrollViewListener();
        intiNewSildingParams();
    }

    private  void initScrollViewListener() {
        ((MyNestedScrollView)findViewById(R.id.scroll_container)).setScrollInterface(new MyNestedScrollView.ScrollInterface() {
            @Override
            public void onScrollChanage(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                scrollHeaderChange(scrollY);
            }
        });
    }

    private void intiNewSildingParams() {
        int titleBarAndStatusBarHeight = (int) (CommonUtils.getDimens(R.dimen.nav_bar_height) +StatusBarUtil.getStatusBarHeight(this) );
        slidingDistance = mImageHeght - titleBarAndStatusBarHeight - (int)(CommonUtils.getDimens(R.dimen.base_header_activity_silde_more));
    }

    /**
     * 根据页面滑动距离改变Header 方法
     * */
    private void scrollHeaderChange(int scrollY) {
        if(scrollY < 0) {
            scrollY = 0;
        }

        float alpha = Math.abs(scrollY) * 1.0f / slidingDistance;
        Drawable d = mHeaderTitleBarBinding.ivBaseTilteBg.getDrawable();

        if(d == null) {
            return;
        }

        if(scrollY <= slidingDistance) {
            d.mutate().setAlpha((int)(alpha * 255));
            mHeaderTitleBarBinding.ivBaseTilteBg.setImageDrawable(d);
        } else {
            d.mutate().setAlpha(255);
            mHeaderTitleBarBinding.ivBaseTilteBg.setImageDrawable(d);
        }


    }

    /*
    * 加载titlebar背景
    * **/
    private void setImgHeaderBg(String imageUrl) {
        Glide.with(this).load(imageUrl)
                .error(R.drawable.stackblur_default)
                .bitmapTransform(new BlurTransformation(this, 23, 4)).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                mHeaderTitleBarBinding.toolBar.setBackgroundColor(Color.TRANSPARENT);
                mHeaderTitleBarBinding.ivBaseTilteBg.setImageAlpha(0);
                mHeaderTitleBarBinding.ivBaseTilteBg.setVisibility(View.VISIBLE);
                return false;
            }
        }).into(mHeaderTitleBarBinding.ivBaseTilteBg);
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

    // 设置头部布局
    protected abstract int setHeaderLayout();

    //设置头部header高斯背景url
    protected abstract String setHeaderImageUrl();

    //设置头部header布局 高斯背景ImageView控件
    protected abstract ImageView setHeaderImageView();
}
