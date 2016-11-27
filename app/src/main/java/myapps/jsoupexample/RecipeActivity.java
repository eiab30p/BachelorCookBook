package myapps.jsoupexample;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Eduardo Verde on 11/26/2016.
 */

public class RecipeActivity extends Activity{
    private Recipes recipesDB;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_screen);
        recipesDB = new Recipes(this);
        recipesDB.open();
    }




}
