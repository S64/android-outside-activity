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
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

import jp.s64.android.outsideactivity.scale.IScaleOutside;

public class ScaleOutsideActivityHelper<SELF extends AppCompatActivity> extends AbsOutsideActivityHelper<SELF> implements IScaleOutside {

    public static <SELF extends AppCompatActivity> ScaleOutsideActivityHelper<SELF> onCreate(SELF self) {
        return new ScaleOutsideActivityHelper<>(self);
    }

    private boolean withAnimation = false;
    private long mAnimatorDuration = 115l;
    private Interpolator mAnimatorInterpolator = new FastOutSlowInInterpolator();

    protected ScaleOutsideActivityHelper(SELF self) {
        super(self);
        {
            getTopOutsideView().addOnLayoutChangeListener(mListener);
            getLeftOutsideView().addOnLayoutChangeListener(mListener);
            getRightOutsideView().addOnLayoutChangeListener(mListener);
            getBottomOutsideView().addOnLayoutChangeListener(mListener);
        }
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

    @Override
    public void setWithAnimation(boolean withAnimation) {
        this.withAnimation = withAnimation;
    }

    @Override
    public void setAnimationInterpolator(Interpolator interpolator) {
        mAnimatorInterpolator = interpolator;
    }

    @Override
    public void setAnimationDuration(long duration) {
        mAnimatorDuration = duration;
    }

    private final View.OnLayoutChangeListener mListener = new View.OnLayoutChangeListener() {

        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            float width = getContentView().getWidth(), height = getContentView().getHeight();

            if (width == 0 || height == 0) {
                return;
            }

            float scale = 1;

            {
                float h = getTopOutsideView().getHeight();
                if (h > 0) {
                    float x = 1f - (h / (height / 2));
                    if (scale > x) {
                        scale = x;
                    }
                }
            }
            {
                float w = getLeftOutsideView().getWidth();
                if (w > 0) {
                    float x = 1f - (w / (width / 2));
                    if (scale > x) {
                        scale = x;
                    }
                }
            }
            {
                float w = getRightOutsideView().getWidth();
                if (w > 0) {
                    float x = 1f - (w / (width / 2));
                    if (scale > x) {
                        scale = x;
                    }
                }
            }
            {
                float h = getBottomOutsideView().getHeight();
                if (h > 0) {
                    float x = 1f - (h / (height / 2));
                    if (scale > x) {
                        scale = x;
                    }
                }
            }

            if (withAnimation) {
                ValueAnimator anim = ValueAnimator.ofFloat(getContentView().getScaleX(), scale);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        getContentView().setScaleX((Float) animation.getAnimatedValue());
                        getContentView().setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim.setDuration(mAnimatorDuration);
                anim.setInterpolator(mAnimatorInterpolator);
                anim.start();
            } else {
                getContentView().setScaleX(scale);
                getContentView().setScaleY(scale);
            }
        }

    };

}
