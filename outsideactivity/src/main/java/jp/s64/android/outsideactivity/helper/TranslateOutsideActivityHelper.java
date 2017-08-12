/*
 * Copyright (C) 2017 Shuma Yoshioka
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.s64.android.outsideactivity.helper;

import android.animation.ValueAnimator;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

import jp.s64.android.outsideactivity.translate.ITranslateOutside;
import jp.s64.android.outsideactivity.translate.TranslateTo;

public class TranslateOutsideActivityHelper<SELF extends AppCompatActivity> extends AbsOutsideActivityHelper<SELF> implements ITranslateOutside {

    public static <SELF extends AppCompatActivity> TranslateOutsideActivityHelper<SELF> onCreate(SELF self) {
        return new TranslateOutsideActivityHelper<>(self);
    }

    @Nullable
    private TranslateTo mCurrentSide = null;

    private boolean withAnimation = false;

    private Interpolator mInterpolator = new FastOutSlowInInterpolator();
    private long mDuration = 115l;

    protected TranslateOutsideActivityHelper(SELF self) {
        super(self);
        {
            getTopOutsideView().addOnLayoutChangeListener(mListener);
            getLeftOutsideView().addOnLayoutChangeListener(mListener);
            getRightOutsideView().addOnLayoutChangeListener(mListener);
            getBottomOutsideView().addOnLayoutChangeListener(mListener);
        }
        executeTranslate();
    }

    @Override
    protected RelativeLayout.LayoutParams createContentLayoutParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        {
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        }
        return params;
    }

    @Override
    public void onDestroy() {
        {
            getTopOutsideView().removeOnLayoutChangeListener(mListener);
            getLeftOutsideView().removeOnLayoutChangeListener(mListener);
            getRightOutsideView().removeOnLayoutChangeListener(mListener);
            getBottomOutsideView().removeOnLayoutChangeListener(mListener);
        }
        super.onDestroy();
    }

    private final View.OnLayoutChangeListener mListener = new View.OnLayoutChangeListener() {

        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            executeTranslate();
        }

    };

    private ValueAnimator mAnimX, mAnimY;

    protected void executeTranslate() {
        if (mAnimX != null) {
            mAnimX.cancel();
            mAnimX = null;
        }
        if (mAnimY != null) {
            mAnimY.cancel();
            mAnimY = null;
        }

        final int toX, toY;
        if (mCurrentSide == null) {
            toX = toY = 0;
        } else {
            int x = 0, y = 0;
            switch (mCurrentSide) {
                case LEFT:
                    x = getLeftOutsideView().getWidth() * -1;
                    break;
                case RIGHT:
                    x = getRightOutsideView().getWidth();
                    break;
                case TOP:
                    y = getTopOutsideView().getHeight() * -1;
                    break;
                case BOTTOM:
                    y = getBottomOutsideView().getHeight();
                    break;
            }
            toX = x;
            toY = y;
        }

        if (withAnimation) {
            configureAnimator(
                    mAnimX = ValueAnimator.ofFloat(getContentView().getTranslationX(), toX),
                    new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            getContentView().setTranslationX((Float) animation.getAnimatedValue());
                        }
                    }
            ).start();
            configureAnimator(
                    mAnimY = ValueAnimator.ofFloat(getContentView().getTranslationY(), toY),
                    new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            getContentView().setTranslationY((Float) animation.getAnimatedValue());
                        }
                    }
            ).start();
        } else {
            getContentView().setTranslationX(toX);
            getContentView().setTranslationY(toY);
        }
    }

    protected ValueAnimator configureAnimator(ValueAnimator anim, ValueAnimator.AnimatorUpdateListener listener) {
        anim.setInterpolator(mInterpolator);
        anim.addUpdateListener(listener);
        anim.setDuration(mDuration);
        return anim;
    }

    @Override
    public void setTranslateTo(@Nullable TranslateTo translateTo) {
        mCurrentSide = translateTo;
        executeTranslate();
    }

    @Override
    public void setWithAnimation(boolean withAnimation) {
        this.withAnimation = withAnimation;
    }

    @Override
    public void setAnimationDuration(long duration) {
        mDuration = duration;
    }

    @Override
    public void setAnimationInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }

}
