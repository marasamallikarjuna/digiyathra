package com.dataevolve.digiyathra.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OTPDetails {
    public OTPDetails(String message, String mobilenumber) {
        this.user = "dataevolve";
        this.passwd = "38821660";
        this.message = message;
        this.mobilenumber = mobilenumber;
        this.mtype = "N";
        this.dr = "Y";
    }

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("passwd")
    @Expose
    private String passwd;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("mobilenumber")
    @Expose
    private String mobilenumber;
    @SerializedName("mtype")
    @Expose
    private String mtype;
    @SerializedName("DR")
    @Expose
    private String dr;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public String getDr() {
        return dr;
    }

    public void setDr(String dr) {
        this.dr = dr;
    }
}
