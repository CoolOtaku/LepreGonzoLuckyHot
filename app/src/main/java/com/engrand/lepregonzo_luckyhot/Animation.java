package com.engrand.lepregonzo_luckyhot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

public class Animation {

    public static void playAnimation(View view) {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, 1.0f, 0.8f, 1.0f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.0f, 0.8f, 1.0f);

        scaleXAnimator.setDuration(300);
        scaleYAnimator.setDuration(300);

        scaleXAnimator.setInterpolator(new AccelerateInterpolator());
        scaleYAnimator.setInterpolator(new AccelerateInterpolator());

        scaleXAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setScaleX(1.0f);
                view.setScaleY(1.0f);
            }
        });

        scaleXAnimator.start();
        scaleYAnimator.start();
    }

}