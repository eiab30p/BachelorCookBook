package myapps.jsoupexample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {

    // URL Address
    String url ;
    //lowest is 6700 highest is 19900
    ProgressDialog mProgressDialog;
    private SharedPreferences.OnSharedPreferenceChangeListener settingsListener;
    private SharedPreferences prefs;
    private DatabaseManager dbManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("0000000000000000", "DJNSAKDAIDJAIJDIJAD");
        settingsListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged( SharedPreferences prefs, String key ) {
                Log.d("11111111111111111", "DJNSAKDAIDJAIJDIJAD");
                prefs = PreferenceManager.getDefaultSharedPreferences( null);
                Log.d("22222222222222222222", "DJNSAKDAIDJAIJDIJAD");
                if (!prefs.getBoolean("firstTime", false)) {
                    Log.d("333333333333333333", "DJNSAKDAIDJAIJDIJAD");
                    for (int i = 6700; i == 6750; i++) {
                        url = "http://allrecipes.com/recipe/" + i + "/";
                        Log.d("444444444444444444444D", "DJNSAKDAIDJAIJDIJAD");
                        //lowest is 6700 highest is 19900
                        new MainActivity.GatherRecipes().execute();
                    }

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("firstTime", true);
                    editor.commit();
                }
            }
        };
        loadHistory(null);
        dbManager = new DatabaseManager(this);
    }



    // Title AsyncTask
    private class GatherRecipes extends AsyncTask<Void, Void, Void> {
        String title;
        String desc;
        String item;
        String direc;
        String vid;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("JSoup ");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Title
                Elements thisTitle = document
                        .select("h1[class=recipe-summary__h1]");
                title = thisTitle.text();
                //Description
                Elements description = document
                        .select("meta[name=description]");
                desc = description.attr("content");
                //Items
                Elements items = document
                        .select("[class=recipe-ingred_txt added]");
                item = items.text();
                //Directions
                Elements directions = document
                        .select("[class=recipe-directions__list--item]");
                direc = directions.text();
                //Video
                Elements video = document
                        .select("a[id=btn_RecipeVideo]");
                vid = video.attr("href");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            long newId = dbManager.insert(title, desc, item, direc, vid);

            mProgressDialog.dismiss();
        }
    }

    public void loadHistory( View v ) {
        startActivity( new Intent(getApplicationContext(),
                Recipes.class));
    }




}