package jp.s64.android.outsideactivity.example;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import jp.s64.android.outsideactivity.example.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mBinding.aligned.setOnClickListener(this);
        mBinding.scale.setOnClickListener(this);
        mBinding.translate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v == mBinding.aligned) {
            intent = AlignedExampleActivity.createIntent(this, mBinding.withAnimation.isChecked());
        } else if (v == mBinding.scale) {
            intent = ScaleExampleActivity.createIntent(this, mBinding.withAnimation.isChecked());
        } else if (v == mBinding.translate) {
            intent = TranslateExampleActivity.createIntent(this, mBinding.withAnimation.isChecked());
        } else {
            intent = null;
        }
        startActivity(intent);
    }

}
