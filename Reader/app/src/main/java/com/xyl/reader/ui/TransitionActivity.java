package com.xyl.reader.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.xyl.reader.MainActivity;
import com.xyl.reader.R;
import com.xyl.reader.SplashActivity;
import com.xyl.reader.databinding.ActivityTransitionBinding;
import com.xyl.reader.utils.PerfectClickListener;

import java.util.Random;

/**
 * User: ShaudXiao
 * Date: 2017-01-10
 * Time: 15:32
 * Company: zx
 * Description:
 * FIXME
 */


public class TransitionActivity extends AppCompatActivity {

    ActivityTransitionBinding mTransitionBinding;

    ImageView mImageView;

    private final int[] mDrawblae = new int[] {
            R.drawable.b_1,R.drawable.b_2,R.drawable.b_3,
            R.drawable.b_4,R.drawable.b_5,R.drawable.b_6
    };

    private boolean isAnimationEnd;

    private boolean isIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTransitionBinding = DataBindingUtil.setContentView(this, R.layout.activity_transition);

        int picIndex = new Random().nextInt(mDrawblae.length);

        mTransitionBinding.ivPic.setImageDrawable(getDrawable(mDrawblae[picIndex]));
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transition_anim);
        animation.setAnimationListener(mAnimationListener);
        mTransitionBinding.ivPic.startAnimation(animation);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                toMainActivity();
//            }
//        }, 2000);

        mTransitionBinding.ivJump.setOnClickListener(new PerfectClickListener() {

            @Override
            public void  onNoDoubleClick(View v) {
                toMainActivity();
            }
        });
    }

    private Animation.AnimationListener mAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            animationEnd();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private void animationEnd() {
        synchronized (TransitionActivity.class) {
            if(!isAnimationEnd) {
                isAnimationEnd = true;
                mTransitionBinding.ivPic.clearAnimation();
                toMainActivity();
            }
        }
    }

    private void toMainActivity () {
        if(isIn) {
            return;
        }
        Intent intent = new Intent(TransitionActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        isIn = true;
    }

    private void toSplashActivity () {
        if(isIn) {
            return;
        }
        Intent intent = new Intent(TransitionActivity.this, SplashActivity.class);
        startActivity(intent);
        finish();
        isIn = true;
    }
}
