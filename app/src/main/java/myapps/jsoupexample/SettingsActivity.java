package myapps.jsoupexample;

import android.os.Bundle;
import android.app.Activity;
/**
 * Created by carlosrendon on 11/26/16.
 */

public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState);

        getFragmentManager( ).beginTransaction( )
                .add( android.R.id.content, new SettingsFragment( ))
                .commit( );

    }
}
