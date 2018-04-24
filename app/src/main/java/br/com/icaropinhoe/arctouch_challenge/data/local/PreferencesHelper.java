package br.com.icaropinhoe.arctouch_challenge.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.ref.WeakReference;

/**
 * Created by icaro on 27/12/2017.
 */

public class PreferencesHelper {

    private static PreferencesHelper INSTANCE;

    private WeakReference<Context> contextWeakReference;

    private PreferencesHelper(WeakReference<Context> contextWeakReference) {
        this.contextWeakReference = contextWeakReference;
    }

    public static PreferencesHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PreferencesHelper(new WeakReference<>(context));
        }

        return INSTANCE;
    }

    public void putString(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(contextWeakReference.get());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(contextWeakReference.get());
        return sharedPreferences.getString(key, null);
    }

}
