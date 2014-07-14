package com.example.NWatch.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by AZGARD on 13.07.2014.
 */
public class Settings {

    private static Settings mSettings = null;
    private Context mContext;

    private boolean isPhoneTime;

    public synchronized static Settings getInstance(Context c) {
        if (mSettings == null) {
            mSettings = new Settings(c);
        }
        return mSettings;
    }

    public Settings(Context c) {
        if (mContext == null) {
            mContext = c;
            initialize();
        }
    }

    private void initialize() {
    }

    public synchronized void finalize() {
        mContext = null;
        mSettings = null;
    }

    public synchronized String getMacAddress() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        return prefs.getString(Constants.PREFERENCE_CONN_INFO_ADDRESS, null);
    }

    public synchronized void setMacAddress(String addr) {
        SharedPreferences prefs = mContext.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.PREFERENCE_CONN_INFO_ADDRESS, addr);
        editor.apply();
    }

    public synchronized String getEmailAddress() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        return prefs.getString(Constants.PREFERENCE_KEY_EMAIL_ADDRESS, null);
    }

    public synchronized String getLogin() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        return prefs.getString(Constants.PREFERENCE_KEY_EMAIL_LOGIN, null);
    }

    public synchronized String getPass() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        return prefs.getString(Constants.PREFERENCE_KEY_EMAIL_PASSWORD, null);
    }

    public synchronized String getWatchType() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        return prefs.getString(Constants.PREFERENCE_KEY_WATCH_TYPE, null);
    }

    public synchronized boolean isDefNotification() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        return prefs.getBoolean(Constants.PREFERENCE_NOTIF_DEFAULT, true);
    }

    public synchronized boolean isIncomingCall() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        return prefs.getBoolean(Constants.PREFERENCE_NOTIF_INCOMING_CALL, false);
    }

    public synchronized boolean isMissingCall() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        return prefs.getBoolean(Constants.PREFERENCE_NOTIF_MISSING_CALL, false);
    }

    public synchronized boolean isSMS() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        return prefs.getBoolean(Constants.PREFERENCE_NOTIF_SMS, false);
    }

    public synchronized boolean isEmail() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        return prefs.getBoolean(Constants.PREFERENCE_NOTIF_EMAIL, false);
    }

    public synchronized boolean isSMSFilter() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        return prefs.getBoolean(Constants.PREFERENCE_NOTIF_SMS_FILTER, false);
    }

    public synchronized boolean isPhoneTime() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        return prefs.getBoolean(Constants.PREFERENCE_KEY_WATCH_TIME, true);
    }

}
