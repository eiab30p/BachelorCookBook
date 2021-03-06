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
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Eduardo Verde on 11/26/2016.
 */

public class RecipeActivity extends AppCompatActivity {

    private Recipes recipesDB;
    private DatabaseManager dbManager;
    private SharedPreferences prefs;
    private SharedPreferences.OnSharedPreferenceChangeListener settingsListener;
    private Button saved, favorite, cooked;

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

        saved = (Button)findViewById(R.id.saved_item_button);
        favorite = (Button)findViewById(R.id.favorite_item_button);
        cooked = (Button)findViewById(R.id.cooked_item_button);

        saved.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                String id = getID();
                dbManager.updateRecipe("Saved",id);
                Toast.makeText(getBaseContext(), R.string.saved_items, Toast.LENGTH_LONG).show();
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                String id = getID();
                dbManager.updateRecipe("Favorite",id);
                Toast.makeText(getBaseContext(), R.string.favorite_items, Toast.LENGTH_LONG).show();
            }
        });
        cooked.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                String id = getID();
                dbManager.updateRecipe("Cooked",id);
                Toast.makeText(getBaseContext(), R.string.cooked_items, Toast.LENGTH_LONG).show();


            }
        });


       }


    public String getID(){
        TextView t = (TextView) findViewById(R.id.recipe_id);
        String recipeID = (String)t.getText();
        return recipeID;
    }


    public void setValues (String recipeID){
        Log.d("PLEASE WORK",recipeID);
        ArrayList<String> allRecords = dbManager.displayRecipe(recipeID);

        WebView webView = (WebView) findViewById(R.id.webview);


        TextView recipeTitle = (TextView)findViewById(R.id.recipeScreen_title);
        TextView recipeItems = (TextView)findViewById(R.id.db_ingredient_contents);
        TextView recipeDirection = (TextView)findViewById(R.id.db_directions_contents);
        TextView recipeiD = (TextView)findViewById(R.id.recipe_id);
        recipeiD.setText(recipeID);
        recipeItems.setText(String.valueOf(allRecords.get(3)));
        recipeTitle.setText(String.valueOf(allRecords.get(0)));
        recipeDirection.setText(String.valueOf(allRecords.get(2)));

        loadWebViewLoad(webView, String.valueOf(allRecords.get(4)));
        //webView.loadUrl("https://www.youtube.com/channel/UCtDPbPkC-4RrfKv7L8il7pA");
        allRecords.indexOf(1);//Description
        allRecords.indexOf(4);// Video check if it is null
        Log.d("IN RECIPES TITLE", String.valueOf(allRecords.get(4)));
    }

    private void loadWebViewLoad(WebView webView, String url){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        String fullURL = "http://allrecipes.com"+url+"/";
        String testURL = "http://allrecipes.com/video/673/banana-banana-bread/";
        Log.d(fullURL,testURL);
        webView.loadUrl(fullURL);

    }

    public void loadSettings( View v ) {
        startActivity( new Intent( getApplicationContext( ),
                SettingsActivity.class));
    }


    public void loadHistory(View V){
        startActivity(new Intent(getApplicationContext(),
                RecipesHistoryActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void initializeUserPreferences() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saved_recipes_history:
                loadHistory(null);
                return true;
            case R.id.return_home:
                home(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //returns user to the home screen
    public void home( View v ) {
        startActivity( new Intent(getApplicationContext(),
                MainActivity.class));
    }



}





