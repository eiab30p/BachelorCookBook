package myapps.jsoupexample;

import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Eduardo Verde on 11/20/2016.
 */

public class Recipes extends AppCompatActivity {
    private DatabaseManager dbManager;
    private boolean bowlExplodeStarted = false;
    private AnimationDrawable frameAnimation = null;
    Animation a = new AlphaAnimation(1.00f, 0.00f);

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        ImageView saladBowl = (ImageView)findViewById(R.id.salad_bowl);
        saladBowl.setBackgroundResource(R.drawable.bowl_animation);
        frameAnimation = (AnimationDrawable) saladBowl.getBackground();

        dbManager = new DatabaseManager(this);

        showFileContents();
    }


    public void search(View v) {

        performAnimation(R.anim.fade_salad);

    }


    public void showFileContents(){
       // TextView recipesDisplay = (TextView) findViewById(R.id.db_contents);
        String allHistory="";

        ArrayList<String> allRecords = dbManager.selectAll();

        for(String s : allRecords){
            allHistory += s + "\n";
        }


         frameAnimation.start();
       // recipesDisplay.setText(allHistory);


    }



    public void displayData( ArrayList<String> data) {
        TextView recipesDisplay = (TextView) findViewById(R.id.db_contents);
        String recipesData = "";
        for ( String s : data ) {
            recipesData += s + "\n";
        }

        recipesDisplay.setText( recipesData);

    }
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

//            ImageView saladBowl = (ImageView)findViewById(R.id.salad_bowl);
//            Animation an = AnimationUtils.loadAnimation(this, R.anim.fade_salad);
//            an.setAnimationListener(new SaladAnimationListener());
//            saladBowl.startAnimation(an);

            return true;
        }
        return super.onTouchEvent(event);
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



}
