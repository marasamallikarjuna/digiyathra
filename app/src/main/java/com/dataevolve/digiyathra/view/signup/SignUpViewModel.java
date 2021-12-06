package com.dataevolve.digiyathra.view.signup;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.dataevolve.digiyathra.data.api.Resource;
import com.dataevolve.digiyathra.data.model.OTPDetails;

import javax.inject.Inject;

import okhttp3.ResponseBody;

public class SignUpViewModel extends ViewModel {

    public SignUpRepository loginRepository;

    private MediatorLiveData<Resource<ResponseBody>> resourceMediatorLiveData;


    @Inject
    public SignUpViewModel(SignUpRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public MediatorLiveData<Resource<ResponseBody>>  sendOtp(OTPDetails otpDetails){
        return loginRepository.getOTP(otpDetails);
    }


}
