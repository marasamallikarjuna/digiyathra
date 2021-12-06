package com.dataevolve.digiyathra.view.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.dataevolve.digiyathra.R;
import com.dataevolve.digiyathra.data.api.Resource;
import com.dataevolve.digiyathra.data.model.OTPDetails;
import com.dataevolve.digiyathra.databinding.LoginMainBinding;
import com.dataevolve.digiyathra.utils.Cognito;
import com.dataevolve.digiyathra.view.BaseActivity;
import com.dataevolve.digiyathra.view.signup.SignUpActivity;
import com.dataevolve.digiyathra.view.submit.SubmitActivity;
import com.dataevolve.digiyathra.viewmodel.ViewModelProviderFactory;

import java.io.IOException;
import java.util.Random;

import javax.inject.Inject;

import okhttp3.ResponseBody;


public class LoginActivity  extends BaseActivity {

    LoginMainBinding binding;
    private LoginViewModel viewModel;

    Cognito cognito;

    @Inject
    ViewModelProviderFactory providerFactory;

    String id="";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = LoginMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = ViewModelProviders.of(this, providerFactory).get(LoginViewModel.class);
        cognito=new Cognito(getApplicationContext());


        binding.otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();

                if (isNetworkConnected()) {
                    if (binding.mobileEdit.getText().toString().length() == 10) {
                        cognito.userLogin("+91" + binding.mobileEdit.getText().toString(), "Test@123", authenticationHandler);
                    } else {
                        Toast.makeText(LoginActivity.this, "invalid mobile number", Toast.LENGTH_LONG).show();
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
                    binding.submitButton.setEnabled(true);
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
                intent =new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otp=binding.otp1.getText().toString()+binding.otp2.getText().toString()+binding.otp3.getText().toString()+binding.otp4.getText().toString();

                if (id.equalsIgnoreCase(otp)) {
                    intent = new Intent(LoginActivity.this, SubmitActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this,"OTP invalid", Toast.LENGTH_LONG).show();

                }
            }
        });







    }


    private void animateOtpLayout(){
        float width = getResources().getDimension(R.dimen.heightL);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) width
        );
        param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        binding.layoutAnimate.setLayoutParams(param);

        binding.otpButton.setVisibility(View.GONE);

        binding.mobileEdit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mobile_white, 0, 0, 0);

        binding.mobileEdit.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

        binding.frameLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_line__white));

        binding.otpLayout.setVisibility(View.VISIBLE);

        binding.submitButton.setEnabled(false);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );


        params.setMargins(0, 90, 0, 0);
        binding.signLayout.setLayoutParams(params);
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

    // Callback handler for the sign-in process
    AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {

        }
        @Override
        public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
            Toast.makeText(getBaseContext(),"Sign in success", Toast.LENGTH_LONG).show();

            Log.d("+++++++++", "AccessToken: " + userSession.getAccessToken().getJWTToken());
            Log.d("+++++++++", "IdToken: " + userSession.getIdToken().getJWTToken());

            if (!userSession.getIdToken().getJWTToken().equalsIgnoreCase("")){
                callOtp(binding.mobileEdit.getText().toString());
            }



        }
        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
            // The API needs user sign-in credentials to continue
            AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId, "Test@123", null);
            // Pass the user sign-in credentials to the continuation
            authenticationContinuation.setAuthenticationDetails(authenticationDetails);
            // Allow the sign-in to continue
            authenticationContinuation.continueTask();
        }
        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation) {
            // Multi-factor authentication is required; get the verification code from user
            //  multiFactorAuthenticationContinuation.setMfaCode(mfaVerificationCode);
            // Allow the sign-in process to continue
            //multiFactorAuthenticationContinuation.continueTask();
        }
        @Override
        public void onFailure(Exception exception) {
            // Sign-in failed, check exception for the cause
            Toast.makeText(LoginActivity.this,"Sign in Failure", Toast.LENGTH_LONG).show();
        }
    };

}
