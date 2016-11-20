package myapps.jsoupexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by Eduardo Verde on 11/19/2016.
 */

public class DatabaseManager extends SQLiteOpenHelper  {

    public static final String DATABASE_NAME ="BachelorCookBook";
    public static final int DATABASE_VERSION = 1;
    public static final String RECIPESTABLE = "tblRecipes";
    public static final String ID = "id";
    public static final String RECIPENAME = "recipeName";
    public static final String RECIPEDESCRIPTION = "recipeDescription";
    public static final String RECIPEITEMS = "recipeItems";
    public static final String RECIPEDIRECTIONS = "recipeDirections";
    public static final String FAVORITES = "fav";
    public static final String SAVED = "saved";
    public static final String COOKED = "cooked";
    private Context appContext;

    public DatabaseManager (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        appContext = context;
    }


    // 0 is false 1 is true for integer fields no Boolean types
    public void onCreate(SQLiteDatabase db){
        String sqlCreate = "create table " + RECIPESTABLE + " ( "
                + ID + " integer primary key autoincrement, "
                + RECIPENAME + " text, "
                + RECIPEDESCRIPTION + " text, "
                + RECIPEITEMS + " text, "
                + RECIPEDIRECTIONS + " text, "
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

    public  long insert (String name, String description, String items, String directions){
        long newId = -1;
        try{
            SQLiteDatabase db = this.getWritableDatabase( );
            ContentValues values = new ContentValues();
            values.put(  RECIPENAME, name);
            values.put( RECIPEDESCRIPTION, description);
            values.put( RECIPEITEMS, items);
            values.put( RECIPEDIRECTIONS, directions);
            newId = db.insert(RECIPESTABLE, null, values);
            db.close();
        } catch ( SQLException se ) {
            Toast.makeText(appContext, se.getMessage(), Toast.LENGTH_LONG).show();
        }
        return newId;
    }
//Work on select all to display title and description
    public ArrayList<String> selectAll( ) {

        ArrayList<String> recipeList = new ArrayList<String>( );
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String query = "Select * from " + RECIPESTABLE;
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

// THis is going to be searched by ingredient
    public ArrayList<String> selectByColumn( String columnName, String columnValue ) {

        ArrayList<String> noteList = new ArrayList<String>( );
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(RECIPESTABLE, null, columnName + "=?",
                    new String[] {columnValue}, null, null, columnName);

            cursor.moveToFirst();
            while ( !cursor.isAfterLast()) {
                String oneRecord = "";

                for ( int i = 0; i < cursor.getColumnCount(); i++) {
                    oneRecord += cursor.getString(i) + " ";
                }
                noteList.add( oneRecord);
                cursor.moveToNext();
            }
        }
        catch ( SQLException se ) {
            Toast.makeText( appContext, se.getMessage( ), Toast.LENGTH_LONG).show();
        }

        return noteList;
    }


    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText( appContext, "onUpgrade called", Toast.LENGTH_LONG).show( );
    }




}
