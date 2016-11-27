package myapps.jsoupexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Eduardo Verde on 11/20/2016.
 */

public class Recipes {

    private DatabaseManager dbManager;
    private Context ourcontext;
    private SQLiteDatabase database;


    public Recipes(Context c) {
        ourcontext = c;
    }

    public Recipes open() throws SQLException {
        dbManager = new DatabaseManager(ourcontext);
        database = dbManager.getWritableDatabase();
        return this;
    }

    public void close() {
        dbManager.close();
    }


    //Insert Data Into table
    public void insertData(String name, String description, String items, String directions, String video) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseManager.RECIPENAME, name);
        cv.put(DatabaseManager.RECIPEDESCRIPTION, description);
        cv.put(DatabaseManager.RECIPEITEMS, items);
        cv.put(DatabaseManager.RECIPEDIRECTIONS, directions);
        cv.put(DatabaseManager.VIDEO, video);
        database.insert(DatabaseManager.RECIPESTABLE, null, cv);
    }

    public Cursor readData(String itemSearch) {
        if (itemSearch.equals("Default")) {
            String[] titleColumn = new String[]{"rowid _id", DatabaseManager.ID, DatabaseManager.RECIPENAME};
            Cursor c = database.query(DatabaseManager.RECIPESTABLE, titleColumn, null, null, null, null, null);
            if (c != null) {
                c.moveToFirst();
            }
            return c;
        } else {
            String[] titleColumn = new String[]{"rowid _id", DatabaseManager.ID, DatabaseManager.RECIPENAME};
            Cursor c = database.query(DatabaseManager.RECIPESTABLE, titleColumn, DatabaseManager.RECIPEITEMS + " LIKE ?",
                    new String[]{"%" + itemSearch + "%"}, null, null, null);
            if (c != null) {
                c.moveToFirst();
            }
            return c;
        }
    }

    //Get Title
    public Cursor getTitle(String recipeid) {
        String[] titleColumn = new String[]{"rowid _id", DatabaseManager.ID, DatabaseManager.RECIPENAME};
        Cursor c = database.query(DatabaseManager.RECIPESTABLE, titleColumn, DatabaseManager.RECIPEITEMS + " = ?",
                new String[]{"" + recipeid + "" }, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        Log.d("Mother fluffer work!", "SENDING DATA");
        return c;
    }

    //Get Description
    public Cursor getDescription(String id) {
        String[] titleColumn = new String[]{"rowid _id", DatabaseManager.ID, DatabaseManager.RECIPEDESCRIPTION};
        Cursor c = database.query(DatabaseManager.RECIPESTABLE, titleColumn, DatabaseManager.RECIPEITEMS + " = ?",
                new String[]{"%" + id + "%"}, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    //Get Directions
    public Cursor getDirection(String id) {
        String[] titleColumn = new String[]{"rowid _id", DatabaseManager.ID, DatabaseManager.RECIPEDIRECTIONS};
        Cursor c = database.query(DatabaseManager.RECIPESTABLE, titleColumn, DatabaseManager.RECIPEITEMS + " = ?",
                new String[]{"%" + id + "%"}, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    //Get Video
    public Cursor getVideo(String id) {
        String[] titleColumn = new String[]{"rowid _id", DatabaseManager.ID, DatabaseManager.VIDEO};
        Cursor c = database.query(DatabaseManager.RECIPESTABLE, titleColumn, DatabaseManager.RECIPEITEMS + " = ?",
                new String[]{"%" + id + "%"}, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

}
