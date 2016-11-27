package myapps.jsoupexample;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by carlosrendon on 11/26/16.
 */

public class SettingsFragment extends PreferenceFragment {

    public void onCreate( Bundle savedInstanceState) {
        super.onCreate( savedInstanceState);
        addPreferencesFromResource( R.xml.preferences);
    }

}
