package com.dataevolve.digiyathra;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;

import com.dataevolve.digiyathra.databinding.LoginMainBinding;
import com.dataevolve.digiyathra.utils.Cognito;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {

    private BiometricPrompt biometricPrompt=null;
    private final Executor executor = Executors.newSingleThreadExecutor();

    LoginMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=LoginMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());



       /* binding.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAndAuthenticate();

            }
        });*/

        Cognito cognito=new Cognito(getApplicationContext());

       // cognito.addAttribute("Username","+919550463362");

       // cognito.signUpInBackground("+919550463362","Test@123");

        cognito.userLogin("+919550463362","Test@123",null);





        /*OTPInterface apiInterface1= ApiClient.getClientAuthentication().create(OTPInterface.class);
        Call<ResponseBody> responseBodyCall = apiInterface1.getOTP("dataevolve",
                "38821660",
                "3333 is your OTP to verify your mobile with Echallan. Please do not share this with anyone.",
                "919550463362",
                "N",
                "Y");

        try {
            responseBodyCall.execute().isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }






}