package com.xyl.architectrue.xrecylerview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xyl.architectrue.R;

/**
 * User: ShaudXiao
 * Date: 2017-01-11
 * Time: 14:16
 * Company: zx
 * Description:
 * FIXME
 */


public class LoadingMoreFooter extends LinearLayout {

    public static final int STATE_LOAING = 0x01;
    public static final int STATE_COMPLETE = 0x02;
    public static final int STATE_NOMORE = 0x04;


    private TextView tvMsg;
    private ImageView ivProgress;
    private AnimationDrawable mAnimationDrawable;

    public LoadingMoreFooter(Context context) {
        super(context);
        initView(context);
    }

    public LoadingMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadingMoreFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inf = LayoutInflater.from(context);
        inf.inflate(R.layout.my_refresh_footer, this);
        tvMsg = (TextView) findViewById(R.id.msg);
        ivProgress = (ImageView) findViewById(R.id.iv_process);
        mAnimationDrawable = (AnimationDrawable) ivProgress.getDrawable();
        if(!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setSate(int state) {
        switch (state) {
            case STATE_LOAING:
                if(!mAnimationDrawable.isRunning()) {
                    mAnimationDrawable.start();
                }
                ivProgress.setVisibility(View.VISIBLE);
                tvMsg.setText(R.string.listview_loading);
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
                if(!mAnimationDrawable.isRunning()) {
                    mAnimationDrawable.stop();
                }
                tvMsg.setText(R.string.listview_loading);
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                if(!mAnimationDrawable.isRunning()) {
                    mAnimationDrawable.stop();
                }
                tvMsg.setText(R.string.nomore_loading);
                ivProgress.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }

    }

    public void reset() {
        this.setVisibility(View.GONE);
    }
}
