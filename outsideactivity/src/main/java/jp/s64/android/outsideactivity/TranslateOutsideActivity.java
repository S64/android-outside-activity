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

package jp.s64.android.outsideactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

import jp.s64.android.outsideactivity.helper.TranslateOutsideActivityHelper;
import jp.s64.android.outsideactivity.translate.ITranslateOutside;
import jp.s64.android.outsideactivity.translate.TranslateTo;

public abstract class TranslateOutsideActivity extends AppCompatActivity implements IOutsideActivity, ITranslateOutside {

    protected TranslateOutsideActivityHelper<TranslateOutsideActivity> mHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = TranslateOutsideActivityHelper.onCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHelper.onDestroy();
        mHelper = null;
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

    @Override
    public void setTranslateTo(@Nullable TranslateTo translateTo) {
        mHelper.setTranslateTo(translateTo);
    }

    @Override
    public void setWithAnimation(boolean withAnimation) {
        mHelper.setWithAnimation(withAnimation);
    }

    @Override
    public void setAnimationDuration(long duration) {
        mHelper.setAnimationDuration(duration);
    }

    @Override
    public void setAnimationInterpolator(Interpolator interpolator) {
        mHelper.setAnimationInterpolator(interpolator);
    }

}
