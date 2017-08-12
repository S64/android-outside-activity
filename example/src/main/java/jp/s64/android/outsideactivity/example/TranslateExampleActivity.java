package jp.s64.android.outsideactivity.example;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import jp.s64.android.outsideactivity.TranslateOutsideActivity;
import jp.s64.android.outsideactivity.example.databinding.ActivityTranslateExampleBinding;
import jp.s64.android.outsideactivity.translate.TranslateTo;

public class TranslateExampleActivity extends TranslateOutsideActivity implements RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    protected static final String EXTRA_WITH_ANIMATION = "jp.s64.android.outsideactivity.example.TranslateExampleActivity.EXTRA_WITH_ANIMATION";

    public static Intent createIntent(Context context, boolean withAnimate) {
        Intent ret = new Intent(context, TranslateExampleActivity.class);
        {
            ret.putExtra(EXTRA_WITH_ANIMATION, withAnimate);
        }
        return ret;
    }

    private ActivityTranslateExampleBinding mBinding;

    private AppCompatTextView mLeft, mRight, mTop, mBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        {
            setWithAnimation(
                    getIntent().getBooleanExtra(EXTRA_WITH_ANIMATION, false)
            );
        }
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_translate_example);
        {
            mLeft = new AppCompatTextView(this);
            mLeft.setText("left");
            mLeft.setLayoutParams(createItemParams());
        }
        {
            mRight = new AppCompatTextView(this);
            mRight.setText("right");
            mRight.setLayoutParams(createItemParams());
        }
        {
            mTop = new AppCompatTextView(this);
            mTop.setText("top");
            mTop.setLayoutParams(createItemParams());
        }
        {
            mBottom = new AppCompatTextView(this);
            mBottom.setText("bottom");
            mBottom.setLayoutParams(createItemParams());
        }
        {
            mBinding.toggleGroup.setOnCheckedChangeListener(this);
            mBinding.toggleElevation.setOnCheckedChangeListener(this);
            mBinding.translateReset.setOnClickListener(this);
        }
        {
            getTopOutsideView().addView(mTop);
            getBottomOutsideView().addView(mBottom);
            getLeftOutsideView().addView(mLeft);
            getRightOutsideView().addView(mRight);
        }
    }

    protected RelativeLayout.LayoutParams createItemParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        {
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        }
        return params;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == mBinding.toggleElevation) {
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

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (group == mBinding.toggleGroup) {
            switch (checkedId) {
                case R.id.toggle_top:
                    setTranslateTo(TranslateTo.TOP);
                    break;
                case R.id.toggle_bottom:
                    setTranslateTo(TranslateTo.BOTTOM);
                    break;
                case R.id.toggle_left:
                    setTranslateTo(TranslateTo.LEFT);
                    break;
                case R.id.toggle_right:
                    setTranslateTo(TranslateTo.RIGHT);
                    break;
                case View.NO_ID:
                    setTranslateTo(null);
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        mBinding.toggleGroup.clearCheck();
    }

}
