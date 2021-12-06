package com.dataevolve.digiyathra.view.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.dataevolve.digiyathra.data.api.LoginApi;
import com.dataevolve.digiyathra.data.api.Resource;
import com.dataevolve.digiyathra.data.local.dao.LoginDao;
import com.dataevolve.digiyathra.data.model.OTPDetails;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class LoginRepository {

    private final LoginApi loginApi;
    private final LoginDao loginDao;

    private MediatorLiveData<Resource<ResponseBody>> resourceMediatorLiveData;

    @Inject
    public LoginRepository(LoginDao loginDao, LoginApi loginApi){
        this.loginDao=loginDao;
        this.loginApi=loginApi;
    }

    public MediatorLiveData<Resource<ResponseBody>> getOTP(OTPDetails otpDetails) {

        final String[] error = {""};
        resourceMediatorLiveData = new MediatorLiveData<>();
        resourceMediatorLiveData.setValue(Resource.loading((ResponseBody) null));

        final LiveData<Resource<ResponseBody>> resourceLiveData= LiveDataReactiveStreams.fromPublisher(

                loginApi.getOTP(otpDetails.getUser(),otpDetails.getPasswd(),
                                otpDetails.getMessage(),otpDetails.getMobilenumber(),otpDetails.getMtype(),otpDetails.getDr())
                        .onErrorReturn(new Function<Throwable, ResponseBody>() {
                            @Override
                            public ResponseBody apply(@NonNull Throwable throwable) throws Exception {

                                error[0] =throwable.getLocalizedMessage();

                                return null;
                            }
                        })
                        .map(new Function<ResponseBody, Resource<ResponseBody>>() {
                            @Override
                            public Resource<ResponseBody> apply(@NonNull ResponseBody responseBody) throws Exception {

                                try {
                                    return Resource.success(responseBody);

                                }catch (Exception e){
                                    e.printStackTrace();
                                    return Resource.error(error[0], null);

                                }
                            }
                        })
                        .subscribeOn(Schedulers.io())
        );

        resourceMediatorLiveData.addSource(resourceLiveData, new Observer<Resource<ResponseBody>>() {
            @Override
            public void onChanged(Resource<ResponseBody> responseBodyResource) {
                resourceMediatorLiveData.setValue(responseBodyResource);
                resourceMediatorLiveData.removeSource(resourceLiveData);
            }
        });

        return resourceMediatorLiveData;
    }



}
