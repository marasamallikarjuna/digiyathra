package com.dataevolve.digiyathra.data.api;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApi {

    @FormUrlEncoded
    @POST("/smscwebservice_bulk.aspx")
    Flowable<ResponseBody> getOTP(@Field("user") String user,
                                  @Field("passwd") String passwd,
                                  @Field("message") String message,
                                  @Field("mobilenumber") String mobilenumber,
                                  @Field("mtype") String mtype,
                                  @Field("DR") String dr);
}
