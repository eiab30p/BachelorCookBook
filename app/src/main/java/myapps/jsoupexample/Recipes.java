package myapps.jsoupexample;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Eduardo Verde on 11/20/2016.
 */

public class Recipes extends AppCompatActivity {
    private DatabaseManager dbManager;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DatabaseManager(this);

        showFileContents();
    }



    public void showFileContents(){
        TextView recipesDisplay = (TextView) findViewById(R.id.db_contents);
        String allHistory="";

        ArrayList<String> allRecords = dbManager.selectAll();

        for(String s : allRecords){
            allHistory += s + "\n";
        }
        recipesDisplay.setText(allHistory);
    }




}
