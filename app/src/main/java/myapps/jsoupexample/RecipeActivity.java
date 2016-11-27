package myapps.jsoupexample;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

/**
 * Created by Eduardo Verde on 11/26/2016.
 */

public class RecipeActivity extends AppCompatActivity{
    private Recipes recipesDB;
    private SharedPreferences prefs;
    private SharedPreferences.OnSharedPreferenceChangeListener settingsListener;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_screen);
        recipesDB = new Recipes(this);
        recipesDB.open();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        prefs = PreferenceManager.getDefaultSharedPreferences( this);
        initializeUserPreferences( );

        settingsListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged( SharedPreferences prefs, String key ) {
                initializeUserPreferences( );
            }
        };

        prefs.registerOnSharedPreferenceChangeListener( settingsListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe, menu);

        return true;
    }

    public void initializeUserPreferences() {

    }




}
