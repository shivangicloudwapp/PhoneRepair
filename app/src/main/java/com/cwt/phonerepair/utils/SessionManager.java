package com.cwt.phonerepair.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
    private static String TAG = SessionManager.class.getSimpleName();
    private SharedPreferences pref;
    private Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "dashboard";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_FIRST_NAME = "firstname";
    private static final String KEY_LAST_NAME = "lastname";
    private static final String KEY_USER_Email = "userEmail";
    private static final String KEY_USER_MOBILE = "userMobile";
    private static final String KEY_USER_ID = "userid";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_IS_LOGGED_IN = "IS_LOGGED_IN";
    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }



    //save user email to SharedPref
    public void setSavedMobile(String userMobile) {
        editor.putString(KEY_USER_MOBILE, userMobile);
        editor.commit();
    }

    //retrieve email frome pref
    public String getSavedMobile() {
        return pref.getString(KEY_USER_MOBILE, "");

    } //save user email to SharedPref

    //save user email to SharedPref
    public void setSavedUserId(String userId) {
        editor.putString(KEY_USER_ID, userId);
        editor.commit();
    }

    //retrieve email frome pref
    public String getSavedUserId() {
        return pref.getString(KEY_USER_ID, "");

    } //save user email to SharedPref

    public void setSavedFirstName(String firstName) {
        editor.putString(KEY_FIRST_NAME, firstName);
        editor.commit();
    }

    //retrieve email frome pref
    public String getSavedFirstName() {
        return pref.getString(KEY_FIRST_NAME, "");

    }

    //save user email to SharedPref

    //save user email to SharedPref
    public void setSavedLastName(String lastName) {
        editor.putString(KEY_LAST_NAME, lastName);
        editor.commit();
    }

    //retrieve email frome pref
    public String getSavedLastName() {
        return pref.getString(KEY_LAST_NAME, "");

    } //save user email to SharedPref

    public void setSavedEmail(String email) {
        editor.putString(KEY_USER_Email, email);
        editor.commit();
    }

    //retrieve email frome pref
    public String getSavedEmail() {
        return pref.getString(KEY_USER_Email, "");
    }

    //save user Token to SharedPref
    public void setSavedToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    //retrieve Token frome pref
    public String getSavedToken() {
        return pref.getString(KEY_TOKEN, "");
    }


    //save user name to SharedPref
    public void setSavedUserName(String userName) {
        editor.putString(KEY_USER_NAME, userName);
        editor.commit();
    }

    //retrieve username frome pref
    public String getSavedUserName() {
        return pref.getString(KEY_USER_NAME, "");
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }
}
