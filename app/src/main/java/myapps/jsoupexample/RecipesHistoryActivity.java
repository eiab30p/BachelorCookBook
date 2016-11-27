package myapps.jsoupexample;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class RecipesHistoryActivity extends AppCompatActivity {

    private ListView recipeListView;
    private Recipes recipesDB;
    private DatabaseManager dbManager;
    private Button saved, favorite, cooked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_recipes);

        dbManager = new DatabaseManager(this);
        recipesDB = new Recipes(this);
        recipesDB.open();
        recipeListView = (ListView) findViewById(R.id.listView1);
        saved = (Button)findViewById(R.id.saved_item_button);
        favorite = (Button)findViewById(R.id.favorite_item_button);
        cooked = (Button)findViewById(R.id.cooked_item_button);

        saved.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                runDataList("Saved");
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                runDataList("Favorite");
            }
        });
        cooked.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                runDataList("Cooked");
            }
        });

    }

    //returns user to the home screen
    public void home( View v ) {
        startActivity( new Intent(getApplicationContext(),
                MainActivity.class));
    }


    public void runDataList(String type){
        Cursor cursor = recipesDB.savedValues(type);
        String [] from = new String[]{DatabaseManager.ID, DatabaseManager.RECIPENAME};
        int[] to = new int[] {R.id.recipe_id, R.id.recipe_name};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                RecipesHistoryActivity.this,R.layout.view_recipe_entry,cursor,from,to);
        adapter.notifyDataSetChanged();
        recipeListView.setAdapter(adapter);
    }
    //creates the menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.return_home:
                //Toast.makeText(this, "Saved Recipes Works", Toast.LENGTH_LONG).show();
                home(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
