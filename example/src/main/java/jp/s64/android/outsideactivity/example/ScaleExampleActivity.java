package jp.s64.android.outsideactivity.example;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import jp.s64.android.outsideactivity.ScaleOutsideActivity;
import jp.s64.android.outsideactivity.example.databinding.ActivityAlignedExampleBinding;
import jp.s64.android.outsideactivity.scale.IScaleOutside;

public class ScaleExampleActivity extends ScaleOutsideActivity implements CompoundButton.OnCheckedChangeListener, IScaleOutside {

    protected static final String EXTRA_WITH_ANIMATION = "jp.s64.android.outsideactivity.example.ScaleExampleActivity.EXTRA_WITH_ANIMATION";

    public static Intent createIntent(Context context, boolean withAnimate) {
        Intent ret = new Intent(context, ScaleExampleActivity.class);
        {
            ret.putExtra(EXTRA_WITH_ANIMATION, withAnimate);
        }
        return ret;
    }

    private ActivityAlignedExampleBinding mBinding;

    private AppCompatTextView mLeft, mRight, mTop, mBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        {
            setWithAnimation(
                    getIntent().getBooleanExtra(EXTRA_WITH_ANIMATION, false)
            );
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
        int size = (int) (48 * getResources().getDisplayMetrics().density);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
        {
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        }
        return params;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == mBinding.toggleTop) {
            if (isChecked) {
                getTopOutsideView().addView(mTop);
            } else {
                getTopOutsideView().removeView(mTop);
            }
        } else if (buttonView == mBinding.toggleBottom) {
            if (isChecked) {
                getBottomOutsideView().addView(mBottom);
            } else {
                getBottomOutsideView().removeView(mBottom);
            }
        } else if (buttonView == mBinding.toggleLeft) {
            if (isChecked) {
                getLeftOutsideView().addView(mLeft);
            } else {
                getLeftOutsideView().removeView(mLeft);
            }
        } else if (buttonView == mBinding.toggleRight) {
            if (isChecked) {
                getRightOutsideView().addView(mRight);
            } else {
                getRightOutsideView().removeView(mRight);
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

}
