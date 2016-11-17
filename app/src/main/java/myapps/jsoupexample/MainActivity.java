package myapps.jsoupexample;


import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.InputStream;




public class MainActivity extends Activity {

    // URL Address
    String url = "http://allrecipes.com/recipe/67937/";
    //lowest is 6700 highest is 19900
    ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button titlebutton = (Button) findViewById(R.id.titlebutton);
        Button descbutton = (Button) findViewById(R.id.descbutton);
        Button itembutton = (Button) findViewById(R.id.itembutton);
        Button direcbutton = (Button) findViewById(R.id.direcbutton);

        Button videobutton = (Button) findViewById(R.id.videobutton);

        // Capture button click
        titlebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Execute Title AsyncTask
                new Title().execute();
            }
        });

        // Capture button click
        descbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Execute Description AsyncTask
                new Description().execute();
            }
        });

        // Capture button click
        itembutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Execute Description AsyncTask
                new Items().execute();
            }
        });

        // Capture button click
        direcbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Execute Description AsyncTask
                new Direction().execute();
            }
        });

        // Capture button click
        videobutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Execute Description AsyncTask
                new Video().execute();
            }
        });

    }

    // Title AsyncTask
    private class Title extends AsyncTask<Void, Void, Void> {
        String title;

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
            try {

                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Using Elements to get the Meta data
                Elements thisTitle = document
                        .select("h1[class=recipe-summary__h1]");
                // Locate the content attribute
                title = thisTitle.text();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            TextView txttitle = (TextView) findViewById(R.id.titletxt);
            txttitle.setText(title);
            mProgressDialog.dismiss();
        }
    }

    // Description AsyncTask
    private class Description extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("JSoup");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Using Elements to get the Meta data
                Elements description = document
                        .select("meta[name=description]");
                // Locate the content attribute
                desc = description.attr("content");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            TextView txtdesc = (TextView) findViewById(R.id.desctxt);
            txtdesc.setText(desc);
            mProgressDialog.dismiss();
        }
    }


    // Description AsyncTask
    private class Items extends AsyncTask<Void, Void, Void> {
        String item;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("JSoup");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Using Elements to get the Meta data
                Elements description = document
                        .select("[class=recipe-ingred_txt added]");
                // Locate the content attribute
                item = description.text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            TextView txtitem = (TextView) findViewById(R.id.itemtxt);
            txtitem.setText(item);
            mProgressDialog.dismiss();
        }
    }



    // Description AsyncTask
    private class Direction extends AsyncTask<Void, Void, Void> {
        String direc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("JSoup");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Using Elements to get the Meta data
                Elements description = document
                        .select("[class=recipe-directions__list--item]");
                // Locate the content attribute
                direc = description.text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            TextView txtdirec = (TextView) findViewById(R.id.directxt);
            txtdirec.setText(direc);
            mProgressDialog.dismiss();
        }
    }

    // Description AsyncTask
    private class Video extends AsyncTask<Void, Void, Void> {
        String vid;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("JSoup");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Using Elements to get the Meta data
                Elements video = document
                        .select("a[id=btn_RecipeVideo]");
                // Locate the content attribute
                vid = video.attr("href");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            TextView txtvideo = (TextView) findViewById(R.id.videotxt);
            txtvideo.setText(vid);
            mProgressDialog.dismiss();
        }
    }





}