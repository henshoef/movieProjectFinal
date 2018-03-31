package com.henshoefgmail.projecttest;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Locale;

public class MoviePage extends AppCompatActivity{
// i use so many statics so i could use it on a static method and declare it on another class while this class is an activity
    public FullMoviesReaderController fullMoviesReaderController;
    public TrailerReaderController trailerReaderController;
    static LinearLayout l;
    static LinearLayout movieLinear;
    static TextView head;
    static TextView description;
    static ImageView image;
    static TextView vote;
    static RatingBar rate;
    static TextView date;
    static TextView budget;
    static TextView runtime;
    static WebView trailer;
    static Activity activity;
    static Context context;
    static String trUrl;
    static Button trailBut;

    static String movieName;
    static String movieScore;
    int aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);
        getSupportActionBar().hide();

        movieLinear=(LinearLayout) findViewById(R.id.fullMovie);
        trailBut=(Button)findViewById(R.id.trailbut);
        fullMoviesReaderController = new FullMoviesReaderController(this);
        trailerReaderController = new TrailerReaderController(this);
        Intent i = getIntent();
        int No =i.getIntExtra("No",0);
        aSwitch =i.getIntExtra("switch",0);
        fullMoviesReaderController.getFullMovie(No,aSwitch);
        trailerReaderController.getTrailer(No,aSwitch);
        l = (LinearLayout) findViewById(R.id.l);
        head = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        image = (ImageView) findViewById(R.id.imageView);
        vote = (TextView) findViewById(R.id.vote_average);
        rate = (RatingBar) findViewById(R.id.MyRating);
        date = (TextView) findViewById(R.id.release_date);
        budget = (TextView) findViewById(R.id.budget);
        runtime = (TextView) findViewById(R.id.runtime);
        trailer = (WebView) findViewById(R.id.trailer);
        activity = this;
        context = this;
        trailer.setBackground(context.getResources().getDrawable(R.drawable.image2));

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(aSwitch==1) {
            finish();
        }
    }
//---------this method are used in another class and checks if there is empty places in the details
    public static void setAllDetails(FullMovieSampleInfo movie){


        head.setText(movie.getSubject());
        description.setText(movie.getBody());

        if (movie.getUrl().equals("")) {
            image.setBackgroundResource(R.drawable.image);
            image.getBackground().setAlpha(150);
        } else {
            new DownloadImageTask(activity,l,context, image, movie.getUrl()).execute();
        }

        String voteText;
        if(0==movie.getVote_average()){
            rate.setVisibility(View.GONE);
            vote.setVisibility(View.VISIBLE);
            voteText = "there are no information on this detail";
        }else {
            voteText = movie.getVote_average()+"";
            if(7<=movie.getVote_average()){
                movieLinear.setBackgroundResource(R.drawable.layoutstylegreen);
            }else{
                movieLinear.setBackgroundResource(R.drawable.layoutstylered);
            }
        }

        vote.setText("Score: "+voteText);
        rate.setNumStars(5);
        rate.setMax(5);
        float rating = (float) 0.5*movie.getVote_average();
        rate.setStepSize((float)0.05);
        rate.setRating(rating);
        Locale locale = Locale.getDefault();
        String locale2=locale.toString();
        if(locale2.equals("iw_IL")) {
            date.setText("תאריך הפצה: " + movie.getRelease_date());
        }else {
            date.setText("Release Date: " + movie.getRelease_date());
        }
        String money;
        if(0==movie.getBudget()){
            if(locale2.equals("iw_IL")) {
                money = "לא נימצא תקציב לסרט זה :(";
            }else {
                money = "there are no information on this detail";
            }
        }else {
            money = movie.getBudget() + "";
        }
        if(locale2.equals("iw_IL")) {
            budget.setText("תקציב: " + money);
        }else {
            budget.setText("Budget: " + money);
        }
        if(0!=movie.getRuntime()) {
            int hours = movie.getRuntime() / 60;
            int minutes = movie.getRuntime() % 60;
            if(locale2.equals("iw_IL")) {
                runtime.setText("אורך הסרט: " + hours + " שעות ו" + minutes + " דקות");
            }else {
                runtime.setText("Movie length: " + hours + " hours and " + minutes + " minutes");
            }
        }else{
            if(locale2.equals("iw_IL")) {
                runtime.setText("אורך הסרט: לא נימצא אורך על סרט זה :(");
            }else {
                runtime.setText("Movie length: there are no information on this detail");
            }
        }

        movieName = movie.getSubject().toString();
        movieScore = movie.getVote_average()+"";
    }
public  static void setTrailer(final TrailerInfo trailerUrl){

      trUrl=trailerUrl.getTrailer();
if(trUrl.equals("")){
    trailBut.setVisibility(View.GONE);
}




        //trailer.setVideoURI(Uri.parse(trailerUrl.getTrailer()));

        //trailer.setText(trailerUrl.getTrailer());
}
public void webViewClick(View v){
    trailer.loadUrl(trUrl);
}
    public void share(View v){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "You just have to see the movie "+movieName+". The movie score is "+movieScore+"!!!");
        sendIntent.setType("text/plain");
        startActivity(sendIntent.createChooser(sendIntent, getResources().getText(R.string.app_name)));
    }
}
