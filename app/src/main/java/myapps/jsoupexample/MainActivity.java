package myapps.jsoupexample;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    String url;
    // lowest is 6700 highest is 19900
    ProgressDialog mProgressDialog;
    private DatabaseManager dbManager;
    private ListView recipeListView;
    private Recipes recipesDB;
    private ImageButton search;
    private ImageView saladBowl;
    private AnimationDrawable frameAnimation;
    private SharedPreferences prefs;
    private SharedPreferences.OnSharedPreferenceChangeListener settingsListener;

    private SoundPool soundPool;
    private int blender;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        dbManager = new DatabaseManager(this);
        recipesDB = new Recipes(this);
        recipesDB.open();
        recipeListView = (ListView) findViewById(R.id.listView1);

        configureSounds();


        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        prefs = PreferenceManager.getDefaultSharedPreferences( this);
        initializeUserPreferences( );
        settingsListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged( SharedPreferences prefs, String key ) {
                initializeUserPreferences( );
            }
        };
        prefs.registerOnSharedPreferenceChangeListener( settingsListener);


        saladBowl = (ImageView)findViewById(R.id.salad_bowl);
        saladBowl.setBackgroundResource(R.drawable.bowl_animation);
        frameAnimation = (AnimationDrawable) saladBowl.getBackground();
        final ImageButton searchButton = (ImageButton) findViewById(R.id.search_button);


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



        //Search Button, Send a recipes items to run Query
        searchButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            searchByRecipe();
                           }
        });


        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View view, int position, long id) {

                LinearLayout parent = (LinearLayout) view;
                TextView t = (TextView) parent.findViewById(R.id.recipe_id);
                String recipeID = (String)t.getText();
                Intent i = new Intent(getApplicationContext(),RecipeActivity.class);
                i.putExtra("recipeID",recipeID);
                startActivity(i);
                //sound effect here
                soundPool.play( blender, 1, 1, 1, 0, 1.5f);
            }
        });
    }//onCreate

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void configureSounds(){
        SoundPool.Builder spb = new SoundPool.Builder();
        spb.setMaxStreams(2);
        soundPool = spb.build();

        blender = soundPool.load(this, R.raw.blender, 1);
    }


    public void searchByRecipe(){

        performAnimation(R.anim.fade_salad);
        frameAnimation.start();
        saladBowl.setVisibility(View.INVISIBLE);

        EditText ingredieantsEntered = (EditText) findViewById(R.id.search_entry);
        if ( ingredieantsEntered.getText( ).toString().length( ) == 0 ) {
            runDataList("Default");
        } else{
            String ingredieant = ingredieantsEntered.getText( ).toString( );
            runDataList(ingredieant);
        }

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
                    runDataList("chicken");
                    break;
                case R.id.cereal_radio_button:
                    runDataList("cereal");
                    break;
                case R.id.beef_radio_button:
                    runDataList("beef");
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
            // 13199 RECIPES IF DONE SINGLE BT ADD 300 FOR RANGE 6710
            for (int i = 6710; i < 30000; i+= 900) {
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

    public void performAnimation( int animationResourceID ) {
        Animation an = AnimationUtils.loadAnimation(this, animationResourceID);
        an.setAnimationListener(new TweenAnimationListener());
        ImageView saladBowlExplotion = (ImageView)findViewById(R.id.salad_bowl);
        saladBowlExplotion.startAnimation(an);
    }


    class TweenAnimationListener implements Animation.AnimationListener {
        public void onAnimationStart(Animation animation) {

        }
        public void onAnimationEnd(Animation animation) {

        }
        public void onAnimationRepeat(Animation animation) {

        }
    }

    public void initializeUserPreferences() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);

        return true;
    }

    public void loadHistory(View V){
        startActivity(new Intent(getApplicationContext(),
                RecipesHistoryActivity.class));
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.saved_recipes_history:
                //Toast.makeText(this, "Saved Recipes Works", Toast.LENGTH_LONG).show();
                loadHistory(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
