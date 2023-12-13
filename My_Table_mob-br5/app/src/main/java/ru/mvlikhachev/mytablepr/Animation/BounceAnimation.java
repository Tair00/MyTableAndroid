package ru.mvlikhachev.mytablepr.Animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class BounceAnimation {
    private static final int BOUNCE_DURATION = 500;
    private static final float BOUNCE_DISTANCE = 20f;
    private static final int BOUNCE_COUNT = 2;

    public static void applyBounceAnimation(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0f, BOUNCE_DISTANCE);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(BOUNCE_DURATION);
        animator.setRepeatCount(BOUNCE_COUNT);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Анимация началась
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Анимация завершилась
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // Анимация была отменена
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Анимация повторяется
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.cancel();
            }
        });
    }
}
