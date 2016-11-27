package myapps.jsoupexample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Eduardo Verde on 11/26/2016.
 */

public class RecipeActivity extends AppCompatActivity {

    private Recipes recipesDB;
    private DatabaseManager dbManager;
    private SharedPreferences prefs;
    private SharedPreferences.OnSharedPreferenceChangeListener settingsListener;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.recipe_screen);

        dbManager = new DatabaseManager(this);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        initializeUserPreferences();
        settingsListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                initializeUserPreferences();
            }
        };

        Intent i = getIntent();
        String recipeID = i.getStringExtra("recipeID");
        setValues(recipeID);
    }


    public void setValues (String recipeID){
        Log.d("PLEASE WORK",recipeID);
        ArrayList<String> allRecords = dbManager.displayRecipe(recipeID);

        TextView recipeTitle = (TextView)findViewById(R.id.recipeScreen_title);
        TextView recipeItems = (TextView)findViewById(R.id.db_ingredient_contents);
        TextView recipeDirection = (TextView)findViewById(R.id.db_directions_contents);
        recipeItems.setText(String.valueOf(allRecords.get(3)));
        recipeTitle.setText(String.valueOf(allRecords.get(0)));
        recipeDirection.setText(String.valueOf(allRecords.get(2)));

        allRecords.indexOf(1);//Description
        allRecords.indexOf(4);// Video check if it is null

        Log.d("IN RECIPES TITLE", String.valueOf(allRecords.get(4)));

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





