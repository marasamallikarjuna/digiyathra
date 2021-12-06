package com.dataevolve.digiyathra.view.signup;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.dataevolve.digiyathra.data.api.Resource;
import com.dataevolve.digiyathra.data.model.OTPDetails;
import com.dataevolve.digiyathra.databinding.SignupMainBinding;
import com.dataevolve.digiyathra.utils.Cognito;
import com.dataevolve.digiyathra.view.BaseActivity;
import com.dataevolve.digiyathra.view.login.LoginActivity;
import com.dataevolve.digiyathra.view.submit.SubmitActivity;
import com.dataevolve.digiyathra.viewmodel.ViewModelProviderFactory;

import java.io.IOException;
import java.util.Random;

import javax.inject.Inject;

import okhttp3.ResponseBody;

public class SignUpActivity extends BaseActivity {

    SignupMainBinding binding;

    private SignUpViewModel viewModel;

    Cognito cognito;

    String id="";


    @Inject
    ViewModelProviderFactory providerFactory;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = SignupMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = ViewModelProviders.of(this, providerFactory).get(SignUpViewModel.class);
        cognito=new Cognito(getApplicationContext());


        binding.otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();

                if (isNetworkConnected()) {
                    if (binding.mobileEdit.getText().toString().length() == 10) {
                        cognito.signUpInBackground("+91" + binding.mobileEdit.getText().toString(), "Test@123", signUpCallback);
                    } else {
                        Toast.makeText(SignUpActivity.this, "invalid mobile number", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });

        binding.otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.otp1.getText().toString().length() == 1)     //size as per your requirement
                {
                    binding.otp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.otp2.getText().toString().length() == 1)     //size as per your requirement
                {
                    binding.otp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding.otp2.getText().toString().length() == 0)     //size as per your requirement
                {
                    binding.otp1.requestFocus();
                }
            }
        });

        binding.otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.otp3.getText().toString().length() == 1)     //size as per your requirement
                {
                    binding.otp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding.otp3.getText().toString().length() == 0)     //size as per your requirement
                {
                    binding.otp2.requestFocus();
                }
            }
        });

        binding.otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.otp4.getText().toString().length() == 1)     //size as per your requirement
                {
                    hideKeyboard();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding.otp4.getText().toString().length() == 0)     //size as per your requirement
                {
                    binding.otp3.requestFocus();
                }
            }
        });

        binding.signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent =new Intent(SignUpActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp=binding.otp1.getText().toString()+binding.otp2.getText().toString()+binding.otp3.getText().toString()+binding.otp4.getText().toString();
                if (id.equalsIgnoreCase(otp)) {
                    intent = new Intent(SignUpActivity.this, SubmitActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(SignUpActivity.this,"OTP invalid", Toast.LENGTH_LONG).show();

                }
            }
        });




    }


    private void animateOtpLayout(){

        binding.otpLayout.setVisibility(View.VISIBLE);
        binding.otpButton.setEnabled(false);

    }


    private void callOtp(String mobile){

         id = String.format("%04d", new Random().nextInt(10000));
        viewModel.sendOtp(new OTPDetails(id+" is your OTP to verify your mobile with Echallan. Please do not share this with anyone.","91"+mobile)).observe(this, new Observer<Resource<ResponseBody>>() {
            @Override
            public void onChanged(Resource<ResponseBody> responseBodyResource) {
                if (responseBodyResource!=null) {
                    switch (responseBodyResource.status) {
                        case LOADING: {
                            Log.d("TAG", "onChanged: LOADING...");
                            break;
                        }

                        case SUCCESS: {
                            Log.d("TAG", "onChanged: got posts...");
                            Log.d("TAG", "+++"+responseBodyResource.status);
                            try
                            {
                                String result= String.valueOf(responseBodyResource.data.byteStream());

                                if (result.contains("OK")){
                                     animateOtpLayout();
                                }


                                Log.d("TAG", "+++"+responseBodyResource.data.byteString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }

                        case ERROR: {


                            Log.e("TAG", "onChanged: ERROR..." + responseBodyResource.message);

                            Toast.makeText(getApplicationContext(), responseBodyResource.message, Toast.LENGTH_LONG).show();


                            break;
                        }
                    }
                }
            }
        });
    }


    SignUpHandler signUpCallback = new SignUpHandler() {
        @Override
        public void onSuccess(CognitoUser cognitoUser, boolean userConfirmed, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
            // Sign-up was successful
            Log.d("+++++++", "Sign-up success");
            Toast.makeText(getApplicationContext(),"Sign-up success", Toast.LENGTH_LONG).show();
            // Check if this user (cognitoUser) needs to be confirmed
            if(!userConfirmed) {
                // This user must be confirmed and a confirmation code was sent to the user
                //cognitoUserCodeDeliveryDetails will indicate where the confirmation code was sent
                // Get the confirmation code from user

                callOtp(binding.mobileEdit.getText().toString());

            }
            else {


                callOtp(binding.mobileEdit.getText().toString());
              //  Toast.makeText(getApplicationContext(),"Error: User Confirmed before", Toast.LENGTH_LONG).show();
                // The user has already been confirmed
                Log.d("+++++++", "User Confirmed before");
            }
        }
        @Override
        public void onFailure(Exception exception) {
            Toast.makeText(getApplicationContext(),exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            Log.d("+++++++++", "Sign-up failed: " + exception.getLocalizedMessage());
        }
    };


}
