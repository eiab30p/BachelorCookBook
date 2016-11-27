package myapps.jsoupexample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;



public class RecipesHistoryActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_recipes);



    }




    //returns user to the home screen
    public void home( View v ) {
        startActivity( new Intent(getApplicationContext(),
                MainActivity.class));
    }


    //creates the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.return_home:
                //Toast.makeText(this, "Saved Recipes Works", Toast.LENGTH_LONG).show();
                home(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
