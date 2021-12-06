package com.dataevolve.digiyathra.view;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;

import com.dataevolve.digiyathra.prefe.AppPreferencesHelper;
import com.dataevolve.digiyathra.utils.MarshMallowPermission;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dagger.android.support.DaggerAppCompatActivity;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_PRECISE_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static androidx.biometric.BiometricPrompt.ERROR_NEGATIVE_BUTTON;
import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;


public abstract class BaseActivity extends DaggerAppCompatActivity {

    public final static int ALL_PERMISSIONS_RESULT = 101;
    public ArrayList<String> permissions = new ArrayList<>();
    public ArrayList<String> permissionsToRequest;
    public ArrayList<String> permissionsRejected = new ArrayList<>();
    public MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);

    public AppPreferencesHelper _appPreferencesHelper;
    public Intent intent;

    private BiometricPrompt biometricPrompt=null;
    private final Executor executor = Executors.newSingleThreadExecutor();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissions.add(READ_PHONE_STATE);
        permissions.add(CAMERA);
        permissions.add(READ_PRECISE_PHONE_STATE);

        permissionsToRequest = findUnAskedPermissions(permissions);

        _appPreferencesHelper=new AppPreferencesHelper(this,"Digi");

        if(biometricPrompt==null){
            biometricPrompt=new BiometricPrompt(this,executor,callback);
        }

    }

    public ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }




    public boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }
    public boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }



    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(BaseActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }






    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public Bitmap rotImage(String currentPhotoPath, Bitmap bitmap){
        ExifInterface ei = null;
        try {
            ei = new ExifInterface(currentPhotoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap = null;
        switch(orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;
            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
        }
        return rotatedBitmap;
    }


    private void checkAndAuthenticate(){
        BiometricManager biometricManager=BiometricManager.from(this);
        switch (biometricManager.canAuthenticate())
        {
            case BiometricManager.BIOMETRIC_SUCCESS:
                BiometricPrompt.PromptInfo promptInfo = buildBiometricPrompt();
                biometricPrompt.authenticate(promptInfo);
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                snack("Biometric Authentication currently unavailable");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                snack("Your device doesn't support Biometric Authentication");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                snack("Your device doesn't have any fingerprint enrolled");
                break;
        }
    }
    private BiometricPrompt.PromptInfo buildBiometricPrompt()
    {
        return new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setSubtitle("FingerPrint Authentication")
                .setDescription("Please place your finger on the sensor to unlock")
                .setDeviceCredentialAllowed(true)
                .build();

    }
    public void snack(String text,View view)
    {

        Snackbar snackbar=Snackbar.make(view.getRootView(),text, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    private BiometricPrompt.AuthenticationCallback callback=new
            BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    if(errorCode==ERROR_NEGATIVE_BUTTON && biometricPrompt!=null)
                        biometricPrompt.cancelAuthentication();
                    snack((String) errString);
                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    snack("Authenticated");
                }

                @Override
                public void onAuthenticationFailed() {
                    snack("The FingerPrint was not recognized.Please Try Again!");
                }
            };

    private void snack(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
        /*Snackbar snackbar=Snackbar.make(this.get,text, Snackbar.LENGTH_LONG);
        snackbar.show();*/
    }


}















