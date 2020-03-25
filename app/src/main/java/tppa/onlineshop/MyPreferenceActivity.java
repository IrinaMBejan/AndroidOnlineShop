package tppa.onlineshop;

import android.app.AppComponentFactory;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;


import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

// Lab 5 - adds PreferenceActivity
// starts
public class MyPreferenceActivity extends PreferenceActivity implements LifecycleObserver {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.layout.preference_layout);
        }
    }
}
// ends