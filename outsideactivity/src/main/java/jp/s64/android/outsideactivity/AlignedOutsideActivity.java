package jp.s64.android.outsideactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import jp.s64.android.outsideactivity.helper.AlignedOutsideActivityHelper;

/**
 * Created by shuma on 2017/08/11.
 */

public abstract class AlignedOutsideActivity extends AppCompatActivity implements IOutsideActivity {

    protected AlignedOutsideActivityHelper<AlignedOutsideActivity> mHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        {
            mHelper = AlignedOutsideActivityHelper.onCreate(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        {
            mHelper.onDestroy();
            mHelper = null;
        }
    }

    @Override
    public ViewGroup getContentView() {
        return mHelper.getContentView();
    }

    @Override
    public RelativeLayout getTopOutsideView() {
        return mHelper.getTopOutsideView();
    }

    @Override
    public RelativeLayout getLeftOutsideView() {
        return mHelper.getLeftOutsideView();
    }

    @Override
    public RelativeLayout getRightOutsideView() {
        return mHelper.getRightOutsideView();
    }

    @Override
    public RelativeLayout getBottomOutsideView() {
        return mHelper.getBottomOutsideView();
    }

}
