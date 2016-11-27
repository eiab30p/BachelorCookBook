package myapps.jsoupexample;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


public class MainActivity extends Activity {

    String url;
    // lowest is 6700 highest is 19900
    ProgressDialog mProgressDialog;
    private DatabaseManager dbManager;
    private ListView recipeListView;
    private ArrayAdapter arrayAdapter;
    private Recipes recipesDB;
    private TextView id_tv, name_tv;
    private ImageButton search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);
     //   dbManager = new DatabaseManager(this);
        recipesDB = new Recipes(this);
        recipesDB.open();
        recipeListView = (ListView) findViewById(R.id.listView1);

        ImageButton searchButton = (ImageButton) findViewById(R.id.search_button);

        //This runs only when it is the first time loading APP
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTime", false)) {
            new GetWebRecipes().execute();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

        //Display All Recipes
        runDataList("Default");


        //If statement on two click one with specified Listener and the other with no Items
        //Need to add a progress to this
        searchButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                                // Execute Title AsyncTask
                            runDataList("Sugar");
                           }
        });

    }

    public void runDataList(String itemSearch){
        Cursor cursor = recipesDB.readData(itemSearch);
        String [] from = new String[]{DatabaseManager.ID, DatabaseManager.RECIPENAME};
        int[] to = new int[] {R.id.recipe_id, R.id.recipe_name};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                MainActivity.this,R.layout.view_recipe_entry,cursor,from,to);
        adapter.notifyDataSetChanged();
        recipeListView.setAdapter(adapter);
    }



    public void setCommonIngredient( View v ) {
        if ( ( (RadioButton) v).isChecked( ) ) {
            switch( v.getId( )) {
                case R.id.chicken_radio_button:
                    Toast.makeText(this, "Chicken", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.cereal_radio_button:
                   Toast.makeText(this, "Cerial", Toast.LENGTH_LONG).show();
                    break;
                case R.id.beef_radio_button:
                    Toast.makeText(this, "Beef", Toast.LENGTH_LONG).show();
                    break;
            }
        }

    }






//JSOUP Way of getting Data
    private class GetWebRecipes extends AsyncTask<Void, Void, Void> {
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
            // 13199 RECIPES IF DONE SINGLE BT ADD 300 FOR RANGE
            for (int i = 6701; i < 19900; i+= 900) {
                url = "http://allrecipes.com/recipe/" + i + "/";
                try {
                    //Connect to the web sites
                    Document document = Jsoup.connect(url).get();
                    String test = String.valueOf(document);
                    Elements thisTitle = document
                            .select("h1[class=recipe-summary__h1]");
                    title = thisTitle.text();

                    Elements description = document
                            .select("meta[name=description]");
                    desc = description.attr("content");

                    Elements items = document
                            .select("[class=recipe-ingred_txt added]");
                    item = items.text();

                    Elements direction = document
                            .select("[class=recipe-directions__list--item]");
                    direc = direction.text();

                    Elements video = document.select("a[id=btn_RecipeVideo]");
                    vid = video.attr("href");

                }catch(IOException e){
                    e.printStackTrace();
                }

                recipesDB.insertData(title, desc,item,direc,vid);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
        }
    }


    //Do a few Onlick listeners 1 for radio buttons and one for search



}