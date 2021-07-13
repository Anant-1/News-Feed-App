package com.example.newsforest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

import java.util.List;
import java.util.prefs.PreferenceChangeListener;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }

    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference

            .OnPreferenceChangeListener {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.setting_main);

            //System.out.println("on create called");

            Preference country = findPreference(getString(R.string.country_key));
//            country.setOnPreferenceChangeListener(this);
            bindPreferenceSummaryToValue(country);

            //System.out.println("Calling complete");
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {

            String stringValue = value.toString();
            System.out.println("New Value -> " + stringValue);

            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;

                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                } else {
                    preference.setSummary(stringValue);
                }
            }
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            //System.out.println("bind wala called");
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");

            //System.out.println("ab call hoga on preference change" + preferenceString);
            onPreferenceChange(preference, preferenceString);
        }
    }
}