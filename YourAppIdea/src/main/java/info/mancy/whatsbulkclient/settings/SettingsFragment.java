package info.mancy.whatsbulkclient.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import info.mancy.android.ui.preferences.PreferenceCompatFragment;
import info.mancy.whatsbulkclient.BuildConfig;
import info.mancy.whatsbulkclient.R;
import info.mancy.whatsbulkclient.YourApplication;
import info.mancy.whatsbulkclient.tutorial.sync.TutorialSyncHelper;

import javax.inject.Inject;

public class SettingsFragment extends PreferenceCompatFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    TutorialSyncHelper mTutorialSyncHelper;

    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        ((YourApplication) getActivity().getApplication()).inject(this);
        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager preferenceManager = getPreferenceManager();
        preferenceManager.getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        if ( key.equals("notificationPref")) {
            if ( sharedPreferences.getBoolean(key, true)){
                if (BuildConfig.DEBUG) {
                    Log.d(YourApplication.LOG_TAG, "settings notificationPref changed: addPeriodicSync()");
                }
                mTutorialSyncHelper.addPeriodicSync();
            }
            else {
                if (BuildConfig.DEBUG) {
                    Log.d(YourApplication.LOG_TAG, "settings notificationPref changed: removePeriodicSync()");
                }
                mTutorialSyncHelper.removePeriodicSync();
            }
        }
    }
}
