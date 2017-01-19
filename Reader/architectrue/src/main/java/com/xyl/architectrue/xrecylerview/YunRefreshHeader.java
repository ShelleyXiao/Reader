package com.xyl.architectrue.xrecylerview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xyl.architectrue.R;
import com.xyl.architectrue.utils.LogUtils;

/**
 * User: ShaudXiao
 * Date: 2017-01-11
 * Time: 14:46
 * Company: zx
 * Description:
 * FIXME
 */


public class YunRefreshHeader extends LinearLayout implements BaseRefreshHeader {

    private TextView tvMsg;
    private ImageView ivProgress;
    private AnimationDrawable mAnimationDrawable;

    private int mState = STATE_NORMAL;
    private int mMeasureHeight;
    private LinearLayout mContainer;


    public YunRefreshHeader(Context context) {
        super(context);
        initView(context);
    }

    public YunRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public YunRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inf = LayoutInflater.from(context);
        inf.inflate(R.layout.yun_refresh_header, this);
        tvMsg = (TextView) findViewById(R.id.msg);
        ivProgress = (ImageView) findViewById(R.id.iv_process);

        mAnimationDrawable = (AnimationDrawable) ivProgress.getDrawable();
        if(mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasureHeight = getMeasuredHeight();
        setGravity(Gravity.CENTER_HORIZONTAL);

        mContainer = (LinearLayout) findViewById(R.id.container);
        LogUtils.e("header ********** init");
        LogUtils.e("header ********** init " + mContainer);
        mContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void onMore(float delta) {
        if(getVisiableHeight() > 0 || delta > 0) {
            setVisiableHeight((int)delta + getVisiableHeight());
            if(mState <= STATE_RELEASE_TO_REFRESH) {
                if(getVisiableHeight() < mMeasureHeight) {
                    setState(STATE_RELEASE_TO_REFRESH);
                } else {
                    setState(STATE_NORMAL);
                }
            }
        }
    }

    @Override
    public boolean releaseAction() {
        boolean isOnRefresh = false;
        int height = getVisiableHeight();
        if(height == 0) {
            isOnRefresh = false;
        }

        if(getVisiableHeight() > mMeasureHeight && mState < STATE_REFRESHING) {
            setState(STATE_REFRESHING);
            isOnRefresh = true;
        }

        if(mState == STATE_REFRESHING && height <= mMeasureHeight) {
            // do nothing
        }

        int destHeight = 0;

        if(mState == STATE_REFRESHING) {
            destHeight = mMeasureHeight; //滑动的正常高度
        }
        smoothScrollTo(destHeight);

        return isOnRefresh;

    }

    @Override
    public void refreshComplete() {
        setState(STATE_DONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                reset();
            }
        }, 500);
    }

    @Override
    public int getVisiableHeight() {
        return mContainer.getHeight();
    }

    public void reset() {
        smoothScrollTo(0);
        setState(STATE_NORMAL);
    }

    private void smoothScrollTo(int height) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisiableHeight(), height);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setVisiableHeight((int)animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    private void setVisiableHeight(int height) {
        if(height < 0) {
            height = 0;
        }

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        if(state == mState) return;
        switch (state) {
            case STATE_NORMAL:
                if(mAnimationDrawable.isRunning()) {
                    mAnimationDrawable.stop();
                }
                tvMsg.setText(R.string.listview_header_hint_normal);
                break;
            case STATE_REFRESHING:
                tvMsg.setText(R.string.refreshing);
                break;
            case STATE_DONE:
                tvMsg.setText(R.string.refresh_done);
                break;
            case STATE_RELEASE_TO_REFRESH:
                if(mState != STATE_RELEASE_TO_REFRESH) {
                    if(!mAnimationDrawable.isRunning()) {
                        mAnimationDrawable.start();
                    }
                    tvMsg.setText(R.string.listview_header_hint_release);
                }
        }
        mState = state;
    }
}
