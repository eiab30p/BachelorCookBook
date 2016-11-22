package myapps.jsoupexample;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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
 String url;
   // lowest is 6700 highest is 19900
    ProgressDialog mProgressDialog;
    private DatabaseManager dbManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DatabaseManager(this);

            new Title().execute();

}

   private class Title extends AsyncTask<Void, Void, Void> {
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
            for (int i = 6701; i < 19900; i+= 300) {
                url = "http://allrecipes.com/recipe/" + i + "/";
            try {

                    ////Connect to the web site
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
                long newId = dbManager.insert(title, desc,item,direc,vid);

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            loadHistory(null);
            mProgressDialog.dismiss();
        }
    }

    public void loadHistory( View v ) {
        startActivity( new Intent(getApplicationContext(),
                Recipes.class));
    }











}