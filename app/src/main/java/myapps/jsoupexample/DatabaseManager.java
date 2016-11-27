package myapps.jsoupexample;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Eduardo Verde on 11/19/2016.
 */

public class DatabaseManager extends SQLiteOpenHelper  {


    //DB Information
    public static final String DATABASE_NAME ="BachelorCookBook";
    public static final int DATABASE_VERSION = 1;

    //Table Information
    public static final String RECIPESTABLE = "tblRecipes";
    public static final String ID = "id";
    public static final String RECIPENAME = "recipeName";
    public static final String RECIPEDESCRIPTION = "recipeDescription";
    public static final String RECIPEITEMS = "recipeItems";
    public static final String RECIPEDIRECTIONS = "recipeDirections";
    public static final String VIDEO ="recipeVideo";
    public static final String FAVORITES = "fav";
    public static final String SAVED = "saved";
    public static final String COOKED = "cooked";
    private Context appContext;

    public DatabaseManager (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
            Log.d("Database CREATE", "WORK GOD DAMNIT!");
        String sqlCreate = "create table " + RECIPESTABLE + " ( "
                + ID + " integer primary key autoincrement, "
                + RECIPENAME + " text, "
                + RECIPEDESCRIPTION + " text, "
                + RECIPEITEMS + " text, "
                + RECIPEDIRECTIONS + " text, "
                + VIDEO + " text, "
                + FAVORITES + " integer, "
                + SAVED + " integer, "
                + COOKED + " integer "
                + ")";

        try {
            db.execSQL(sqlCreate);
        } catch ( SQLException se ){
            Toast.makeText(appContext, se.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText( appContext, "onUpgrade called", Toast.LENGTH_LONG).show( );
    }

        public ArrayList<String> getTitle( ) {
            ArrayList<String> recipeList = new ArrayList<String>( );
            try {
                SQLiteDatabase db = this.getReadableDatabase();
                String query = "Select "+ RECIPENAME +" from " + RECIPESTABLE;
                Cursor cursor = db.rawQuery( query, null);
                cursor.moveToFirst();
                while ( !cursor.isAfterLast()) {
                    String oneRecord = "";
                   for ( int i = 0; i < cursor.getColumnCount(); i++) {
                        oneRecord += cursor.getString(i) + " ";
                   }
                   recipeList.add( oneRecord);
                   cursor.moveToNext();
               }
            }
           catch ( SQLException se ) {
                Toast.makeText( appContext, se.getMessage( ), Toast.LENGTH_LONG).show();
            }
           return recipeList;
        }







}
