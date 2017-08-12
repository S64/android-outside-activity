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

import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import jp.s64.android.outsideactivity.R;

public class AlignedOutsideActivityHelper<SELF extends AppCompatActivity> extends AbsOutsideActivityHelper<SELF> {

    public static <SELF extends AppCompatActivity> AlignedOutsideActivityHelper<SELF> onCreate(SELF self) {
        return new AlignedOutsideActivityHelper<>(self);
    }

    protected AlignedOutsideActivityHelper(SELF self) {
        super(self);
    }

    @Override
    protected RelativeLayout.LayoutParams createContentLayoutParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        {
            params.addRule(RelativeLayout.BELOW, R.id.outside_top);
            params.addRule(RelativeLayout.RIGHT_OF, R.id.outside_left);
            params.addRule(RelativeLayout.LEFT_OF, R.id.outside_right);
            params.addRule(RelativeLayout.ABOVE, R.id.outside_bottom);
        }
        return params;
    }

}
