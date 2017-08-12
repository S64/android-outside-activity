package jp.s64.android.outsideactivity.example;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import jp.s64.android.outsideactivity.AlignedOutsideActivity;
import jp.s64.android.outsideactivity.example.databinding.ActivityAlignedExampleBinding;

public class AlignedExampleActivity extends AlignedOutsideActivity implements CompoundButton.OnCheckedChangeListener {

    protected static final String EXTRA_ANIMATE = "jp.s64.android.outsideactivity.example.AlignedExampleActivity.EXTRA_ANIMATE";

    public static Intent createIntent(Context context, boolean animate) {
        Intent ret = new Intent(context, AlignedExampleActivity.class);
        ret.putExtra(EXTRA_ANIMATE, animate);
        return ret;
    }

    private boolean doAnimate = false;

    private ActivityAlignedExampleBinding mBinding;

    private AppCompatTextView mLeft, mRight, mTop, mBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        {
            doAnimate = getIntent().getBooleanExtra(EXTRA_ANIMATE, false);
        }
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_aligned_example);
        {
            mLeft = new AppCompatTextView(this);
            mLeft.setText("left");
            mLeft.setGravity(Gravity.CENTER);
            mLeft.setBackgroundResource(R.color.colorAccent);
            mLeft.setLayoutParams(createItemParams());
        }
        {
            mRight = new AppCompatTextView(this);
            mRight.setText("right");
            mRight.setGravity(Gravity.CENTER);
            mRight.setBackgroundResource(R.color.colorAccent);
            mRight.setLayoutParams(createItemParams());
        }
        {
            mTop = new AppCompatTextView(this);
            mTop.setText("top");
            mTop.setGravity(Gravity.CENTER);
            mTop.setBackgroundResource(R.color.colorAccent);
            mTop.setLayoutParams(createItemParams());
        }
        {
            mBottom = new AppCompatTextView(this);
            mBottom.setText("bottom");
            mBottom.setGravity(Gravity.CENTER);
            mBottom.setBackgroundResource(R.color.colorAccent);
            mBottom.setLayoutParams(createItemParams());
        }
        mBinding.toggleTop.setOnCheckedChangeListener(this);
        mBinding.toggleLeft.setOnCheckedChangeListener(this);
        mBinding.toggleRight.setOnCheckedChangeListener(this);
        mBinding.toggleBottom.setOnCheckedChangeListener(this);
        mBinding.toggleElevation.setOnCheckedChangeListener(this);
    }

    protected RelativeLayout.LayoutParams createItemParams() {
        int size = (int) calcSize();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
        {
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        }
        return params;
    }

    protected float calcSize() {
        return 48 * getResources().getDisplayMetrics().density;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == mBinding.toggleTop) {
            if (isChecked) {
                getTopOutsideView().addView(mTop);
                if (doAnimate) showView(mTop);
            } else {
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        getTopOutsideView().removeView(mTop);
                    }
                };
                if (doAnimate)
                    hideView(mTop, run);
                else
                    run.run();
            }
        } else if (buttonView == mBinding.toggleBottom) {
            if (isChecked) {
                getBottomOutsideView().addView(mBottom);
                if (doAnimate) showView(mBottom);
            } else {
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        getBottomOutsideView().removeView(mBottom);
                    }
                };
                if (doAnimate)
                    hideView(mBottom, run);
                else
                    run.run();

            }
        } else if (buttonView == mBinding.toggleLeft) {
            if (isChecked) {
                getLeftOutsideView().addView(mLeft);
                if (doAnimate) showView(mLeft);
            } else {
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        getLeftOutsideView().removeView(mLeft);
                    }
                };
                if (doAnimate)
                    hideView(mLeft, run);
                else
                    run.run();
            }
        } else if (buttonView == mBinding.toggleRight) {
            if (isChecked) {
                getRightOutsideView().addView(mRight);
                if (doAnimate) showView(mRight);
            } else {
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        getRightOutsideView().removeView(mRight);
                    }
                };
                if (doAnimate)
                    hideView(mRight, run);
                else
                    run.run();
            }
        } else if (buttonView == mBinding.toggleElevation) {
            final float elevation = 8 * getResources().getDisplayMetrics().density;
            float contentElv, outsideElv;
            if (isChecked) {
                contentElv = elevation;
                outsideElv = 0;
            } else {
                contentElv = 0;
                outsideElv = elevation;
            }
            ViewCompat.setElevation(getContentView(), contentElv);
            ViewCompat.setElevation(getTopOutsideView(), outsideElv);
            ViewCompat.setElevation(getLeftOutsideView(), outsideElv);
            ViewCompat.setElevation(getRightOutsideView(), outsideElv);
            ViewCompat.setElevation(getBottomOutsideView(), outsideElv);
        }
    }

    protected void showView(final View v) {
        final boolean isHorizontal = v == mLeft || v == mRight;

        ValueAnimator anim = ValueAnimator.ofInt(0, (int) calcSize());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams params = v.getLayoutParams();
                if (isHorizontal) {
                    params.width = (int) animation.getAnimatedValue();
                } else {
                    params.height = (int) animation.getAnimatedValue();
                }
                v.setLayoutParams(params);
            }
        });
        anim.setDuration(115l);
        anim.setInterpolator(new FastOutSlowInInterpolator());
        anim.start();
    }

    protected void hideView(final View v, final @Nullable Runnable after) {
        final boolean isHorizontal = v == mLeft || v == mRight;

        ValueAnimator anim = ValueAnimator.ofInt((int) calcSize(), 0);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams params = v.getLayoutParams();
                if (isHorizontal) {
                    params.width = (int) animation.getAnimatedValue();
                } else {
                    params.height = (int) animation.getAnimatedValue();
                }
                v.setLayoutParams(params);
            }
        });
        if (after != null) {
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    after.run();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        anim.setDuration(115l);
        anim.setInterpolator(new FastOutSlowInInterpolator());
        anim.start();
    }

}
