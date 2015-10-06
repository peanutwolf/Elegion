package com.vigursky.grushahit;

/**
 * Created by vigursky on 01.10.2015.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;


public class SplashActivity extends Activity {

    private static final int ANIMATION_DURATION = 1500;
    private ImageView mAppLogoImageView;
    private final AnimatorSet mAnimatorSet = new AnimatorSet();
    private final Animator.AnimatorListener mAnimatorListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            startMainActivity();
        }

        @Override
        public void onAnimationStart(Animator animation) {
            beforeStartViewAnimation();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAppLogoImageView = (ImageView)findViewById(R.id.img_app_logo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAnimatorSet != null && !mAnimatorSet.isRunning()) {
            startInitialAnimation();
        }
    }

    @Override
    protected void onPause() {
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            mAnimatorSet.cancel();
        }
        super.onPause();
    }


    private void startInitialAnimation() {
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mAppLogoImageView, View.ALPHA,0, 1);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(mAppLogoImageView, View.SCALE_X, 0, 1);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(mAppLogoImageView, View.SCALE_Y, 0, 1);

        mAnimatorSet.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator);
        mAnimatorSet.setDuration(ANIMATION_DURATION);
        mAnimatorSet.setInterpolator(new AccelerateInterpolator());
        mAnimatorSet.addListener(mAnimatorListener);
        mAnimatorSet.start();
    }

    private void beforeStartViewAnimation() {
        mAppLogoImageView.setAlpha(0f);
        mAppLogoImageView.setScaleX(0);
        mAppLogoImageView.setScaleY(0);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, GrushaMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
