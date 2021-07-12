package com.example.poseidon;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import androidx.appcompat.view.menu.ListMenuPresenter;

import java.util.Map;
import java.util.prefs.PreferenceChangeEvent;

public class BluetoothPreferencesActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        BluetoothHelper.initialize(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference pref = findPreference(key);

        if(pref instanceof ListPreference){
            ListPreference listPref = (ListPreference) pref;
            pref.setSummary(listPref.getEntry());
            BluetoothHelper.initialize(this);
        }

        if(pref instanceof EditTextPreference){
            EditTextPreference editPref = (EditTextPreference) pref;
            pref.setSummary(editPref.getText());
        }
    }

    @Override
    protected void onPause() {
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
        Map<String, ?> keys = PreferenceManager.getDefaultSharedPreferences(this).getAll();

        for(Map.Entry<String, ?> entry : keys.entrySet()){
            Preference pref = findPreference(entry.getKey());
            if(pref != null){
                pref.setSummary(entry.getValue().toString());
            }
        }
        super.onPause();
    }
}
