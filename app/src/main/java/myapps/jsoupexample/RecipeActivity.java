package myapps.jsoupexample;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Eduardo Verde on 11/26/2016.
 */

public class RecipeActivity extends AppCompatActivity {
    private Recipes recipesDB;
    private SharedPreferences prefs;
    private SharedPreferences.OnSharedPreferenceChangeListener settingsListener;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_screen);
        recipesDB = new Recipes(this);
        recipesDB.open();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        initializeUserPreferences();

        settingsListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                initializeUserPreferences();
            }
        };

        prefs.registerOnSharedPreferenceChangeListener(settingsListener);
    }

    public void loadSettings( View v ) {
        startActivity( new Intent( getApplicationContext( ),
                SettingsActivity.class));
    }


    public void loadHistory(View v) {
        startActivity(new Intent(getApplicationContext(), Recipes.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe, menu);

        return true;
    }

    public void initializeUserPreferences() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.recipe_history:
                loadSettings(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}





