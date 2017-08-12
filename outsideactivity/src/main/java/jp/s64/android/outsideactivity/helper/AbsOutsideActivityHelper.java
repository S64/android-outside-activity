package jp.s64.android.outsideactivity.helper;

import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import jp.s64.android.outsideactivity.R;

/**
 * Created by shuma on 2017/08/11.
 */

public abstract class AbsOutsideActivityHelper<SELF extends AppCompatActivity> implements IOutsideActivityHelper {

    @NonNull
    private SELF self;

    private final FrameLayout mRootView;
    private final ViewGroup mContentView;
    private final RelativeLayout mExtrasView;

    private final RelativeLayout mTopFrame, mLeftFrame, mRightFrame, mBottomFrame;

    protected AbsOutsideActivityHelper(SELF self) {
        {
            this.self = self;
        }
        View org;
        {
            org = self.findViewById(android.support.v7.appcompat.R.id.decor_content_parent);
            mRootView = (FrameLayout) org.getParent();
        }
        {
            mExtrasView = new RelativeLayout(self);
            mExtrasView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mRootView.addView(
                    mExtrasView,
                    mRootView.indexOfChild(org)
            );
        }
        {
            mTopFrame = new RelativeLayout(self);
            mLeftFrame = new RelativeLayout(self);
            mRightFrame = new RelativeLayout(self);
            mBottomFrame = new RelativeLayout(self);
            {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                mTopFrame.setLayoutParams(params);

                mTopFrame.setId(R.id.outside_top);
            }
            {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                mBottomFrame.setLayoutParams(params);

                mBottomFrame.setId(R.id.outside_bottom);
            }
            {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                mLeftFrame.setLayoutParams(params);

                mLeftFrame.setId(R.id.outside_left);
            }
            {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                mRightFrame.setLayoutParams(params);

                mRightFrame.setId(R.id.outside_right);
            }
            mExtrasView.addView(mTopFrame);
            mExtrasView.addView(mBottomFrame);
            mExtrasView.addView(mLeftFrame);
            mExtrasView.addView(mRightFrame);
        }
        {
            mRootView.removeView(org);
            org.setLayoutParams(createContentLayoutParams());
            mExtrasView.addView(mContentView = (ViewGroup) org);
        }
        {
            TypedArray bgAry, ctAry;
            {
                bgAry = self.getTheme().obtainStyledAttributes(new int[]{R.attr.outsideBackground});
                ctAry = self.getTheme().obtainStyledAttributes(new int[]{R.attr.outsideContentBackground});
            }
            try {
                if (bgAry != null && bgAry.hasValue(R.styleable.outsideBackground_outsideBackground)) {
                    int bg = bgAry.getResourceId(R.styleable.outsideBackground_outsideBackground, -1);
                    if (bg != -1) {
                        mExtrasView.setBackgroundResource(bg);
                        mTopFrame.setBackgroundResource(bg);
                        mLeftFrame.setBackgroundResource(bg);
                        mRightFrame.setBackgroundResource(bg);
                        mBottomFrame.setBackgroundResource(bg);
                    }
                }
                if (ctAry != null && ctAry.hasValue(R.styleable.outsideContentBackground_outsideContentBackground)) {
                    int ct = ctAry.getResourceId(R.styleable.outsideContentBackground_outsideContentBackground, -1);
                    if (ct != -1) {
                        mContentView.setBackgroundResource(ct);
                    }
                }
            } finally {
                if (bgAry != null) {
                    bgAry.recycle();
                }
                if (ctAry != null) {
                    ctAry.recycle();
                }
            }
        }
    }

    protected abstract RelativeLayout.LayoutParams createContentLayoutParams();

    @Override
    public ViewGroup getContentView() {
        assertNotDestroyed();
        return mContentView;
    }

    @Override
    public RelativeLayout getTopOutsideView() {
        assertNotDestroyed();
        return mTopFrame;
    }

    @Override
    public RelativeLayout getLeftOutsideView() {
        assertNotDestroyed();
        return mLeftFrame;
    }

    @Override
    public RelativeLayout getRightOutsideView() {
        assertNotDestroyed();
        return mRightFrame;
    }

    @Override
    public RelativeLayout getBottomOutsideView() {
        assertNotDestroyed();
        return mBottomFrame;
    }

    public void onDestroy() {
        assertNotDestroyed();
        self = null;
    }

    protected void assertNotDestroyed() {
        if (self == null) {
            throw new IllegalStateException();
        }
    }

}
