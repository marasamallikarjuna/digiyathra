package com.dataevolve.digiyathra.view.submit;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.dataevolve.digiyathra.databinding.SignupMainBinding;
import com.dataevolve.digiyathra.databinding.SuccessMainBinding;
import com.dataevolve.digiyathra.view.BaseActivity;


public class SubmitActivity extends BaseActivity {

    SuccessMainBinding binding;

    AnimatedVectorDrawableCompat avdC;

    AnimatedVectorDrawable avd;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = SuccessMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Drawable drawable=binding.doneImageView.getDrawable();

        if (drawable instanceof AnimatedVectorDrawableCompat){
            avdC=(AnimatedVectorDrawableCompat) drawable;
            avdC.start();

        }else if (drawable instanceof AnimatedVectorDrawable){
            avd=(AnimatedVectorDrawable) drawable;
            avd.start();
        }

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                    onBackPressed();
            }
        },1000);

    }
}
