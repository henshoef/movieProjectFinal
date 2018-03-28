package com.henshoefgmail.projecttest;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
//-----objects at the class for the use of all the methods
    DataBase db;
    LinearLayout layout;
    List<MovieSample> names;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar));
        App.setContext(this); //i use this for later use to declare startActivityForResult from another Activity.
        db = new DataBase(this);//start my data base
        layout = (LinearLayout) findViewById(R.id.linearLayoutMain);//the outer layout- so i could use it in addPicture();
            loadMovie();
    }
// -1.the loading of the database
    public void loadMovie(){
        names =db.getAllMovieList();//get list of movies from the data base
        int i =0;
            if (names.size() == i) {
               TextView empty = (TextView) findViewById(R.id.empty);
                empty.setVisibility(View.VISIBLE);
            } else {
                while(i<names.size()) {
                String s = names.get(i).getSubject();
                String u = names.get(i).getUrl();
                String d = names.get(i).getBody();
                int id =names.get(i).get_id();
                int No = names.get(i).getOrderNumber();
                int watched = names.get(i).getWatched();
                makeMovie(s,d,u,id,No,watched);

                    i++;
            }
        }
    }
// 2.making movie method
    public void makeMovie(String name,String description , String url,int id,int orderNumber,int watched) {

        ImageView image = new ImageView(this);
        TextView title = new TextView(this);
        TextView des = new TextView(this);
        TextView hint = new TextView(this);
        TextView watchNumberTextView = new TextView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout innerLayout = new LinearLayout(this);
        LinearLayout movieImage = new LinearLayout(this);
        final Button goPageButton = new Button(this);
        Button watchButton = new Button(this);
// resizing the elements
        linearLayoutImageInsideResize(movieImage);
        buttonResize(goPageButton);
        linearLayoutInsideResize(innerLayout);
        textDesResize(des);
        textViewWatchNumberResize(hint);
        hint.setText(getString(R.string.watch));
        linearLayoutResize(linearLayout);
        imageViewResize(image);
        textViewResize(title);
// tick button and writing in the database
        watchButton.setTag(id);
        setAddWatch(watchButton,watched,watchNumberTextView);
        addPicture(image, url);

        image.setTag(name);
        goPageButton.setTag(orderNumber);

        goPageButton.setText(R.string.moviepage);
        goPageButton.setTextSize(13);
        goPageButton.setBackground(getDrawable(R.drawable.buttons));
        goPageButton.setTextColor(getColor(R.color.white));
        title.setText(name);
        des.setText(description);
// calling the custom dialog
        final CustomDialogClass cdd = new CustomDialogClass(MainActivity.this, name, id);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEdit(view);
            }
        });
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                cdd.show();
                return false;
            }
        });

        goPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderNumber = (int)goPageButton.getTag();
                goToMoviePage(orderNumber);
            }
        });

        innerLayout.addView(title);
        innerLayout.addView(des);

        if(orderNumber!=0) {
            innerLayout.addView(goPageButton);
        }
        movieImage.addView(image);

        movieImage.addView(watchNumberTextView);
        movieImage.addView(watchButton);
        movieImage.addView(hint);

        linearLayout.addView(movieImage);
        linearLayout.addView(innerLayout);

        layout.addView(linearLayout);
    }
//all the methods that resize each of the view in particular--------------------
    public void buttonResize(Button sv){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        sv.setLayoutParams(positionRules);
        positionRules.setMargins(10, 10, 10, 10);
    }

    public void linearLayoutImageInsideResize(LinearLayout layoutImage){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutImage.setLayoutParams(positionRules);
        layoutImage.setGravity(Gravity.CENTER);
        layoutImage.setOrientation(LinearLayout.VERTICAL);
    }

    public void linearLayoutInsideResize(LinearLayout innerLayout){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        innerLayout.setLayoutParams(positionRules);
        innerLayout.getLayoutParams().height = 1050;
        positionRules.setMargins(25,0, 0, 5);
        innerLayout.setOrientation(LinearLayout.VERTICAL);
    }

    public void textDesResize(TextView des){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        des.setLayoutParams(positionRules);
        des.setTextColor(Color.WHITE);
        des.setTextSize(13);
        positionRules.setMargins(5,5, 5, 0);
        des.getLayoutParams().height = 425;
    }

    public void linearLayoutResize(LinearLayout linearLayout){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(positionRules);
        positionRules.setMargins(25, 25, 25, 25);
        linearLayout.setLayoutParams(positionRules);
        linearLayout.getLayoutParams().height = 1050;
        linearLayout.setBackgroundResource(R.drawable.layoutstyle);
    }

    public void textViewResize(TextView b){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        b.setLayoutParams(positionRules);
        b.setTextColor(Color.WHITE);
        b.setTextSize(25);
        positionRules.setMargins(15,0, 15, 15);
    }

    public void textViewWatchNumberResize(TextView b){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        b.setLayoutParams(positionRules);
        b.setTextSize(15);
        b.setTextColor(Color.WHITE);

    }

    public void watchButtonResize(Button sv){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        sv.setLayoutParams(positionRules);
        sv.getLayoutParams().height = 80;
        sv.getLayoutParams().width = 80;
        sv.setBackground(getDrawable(R.drawable.ic_check_box_outline_blank_black_24dp));
    }

    public void imageViewResize(ImageView b){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        b.setLayoutParams(positionRules);
        positionRules.setMargins(15, 15, 25, 0);
        b.getLayoutParams().height = 710;
        b.getLayoutParams().width = 400;
    }
// set the appearance for the tick number and logo
    public void setAddWatch(final Button b ,int watched,final TextView watchNumberTextView){

        textViewWatchNumberResize(watchNumberTextView);
        watchButtonResize(b);
        if(watched!=0){
            b.setBackground(getDrawable(R.drawable.ic_check_box_black_24dp));
        }
        String watchString = watched+"";
        watchNumberTextView.setText(watchString);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(view.getTag().toString());
                addWatch(b,watchNumberTextView,id);

            }
        });

        b.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int id = Integer.parseInt(view.getTag().toString());
                resetWatch(b,watchNumberTextView,id);
                return false;
            }
        });

    }

    public void addWatch(Button b,TextView watchNumberTextView,int id){
        int watched=0;
        for(int p=0;p<names.size();p++){
            if(id==names.get(p).get_id()){
                names.get(p).setWatched(names.get(p).getWatched()+1);
                watched = names.get(p).getWatched();
                db.updateWatch(id);
            }
        }
        if(watched>0){
            b.setBackground(getDrawable(R.drawable.ic_check_box_black_24dp));
        }
        String watch = watched+"";
        watchNumberTextView.setText(watch);
    }

    public void resetWatch(Button b,TextView watchNumberTextView,int id){
        int watched=0;
        for(int p=0;p<names.size();p++){
            if(id==names.get(p).get_id()){
                names.get(p).setWatched(0);
                watched = names.get(p).getWatched();
                db.resetWatch(id);
            }
        }
        b.setBackground(getDrawable(R.drawable.ic_check_box_outline_blank_black_24dp));
        String watch = watched+"";
        watchNumberTextView.setText(watch);
    }
// set up the imageView or give the default image
    public void addPicture(ImageView b,String u) {
        if (u.equals("")) {
            b.setBackgroundResource(R.drawable.image);
            b.getBackground().setAlpha(150);
        } else {
                                 new DownloadImageTask(this, layout,this, b, u).execute();
        }
    }
//
    public void goToMoviePage(int No){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            Intent moviePage = new Intent(this,MoviePage.class);
            moviePage.putExtra("No",No);
            moviePage.putExtra("switch",0);
            startActivity(moviePage);
        }else{
            Toast.makeText(this,"there is no internet connection",Toast.LENGTH_SHORT).show();
        }



    }

//       edit movie
    public void goToEdit(View v){
        String movieTitle = v.getTag().toString();
//i send all the movie informations into the edit stage
        for(int i=0;i<names.size();i++){
            if(movieTitle.equals(names.get(i).getSubject())){
                String title =names.get(i).getSubject();
                String des =names.get(i).getBody();
                String url =names.get(i).getUrl();
                int id = names.get(i).get_id();

                Intent editActivity = new Intent(this,EditActivity.class);
                editActivity.putExtra("name",title);
                editActivity.putExtra("des",des);
                editActivity.putExtra("url",url);
                editActivity.putExtra("id",id);
                this.startActivityForResult(editActivity,1);

            }
        }
    }
//  delete all as separated method
    public void deleteall(){
        db.clear();
        restart();
    }

    public void restart(){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finishAffinity();
    }
// inflating the menus
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);


        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {

            case R.id.menuItemSettings:

                return true;
            case R.id.addInternet:
                Intent net = new Intent(this,InternetActivity.class);
                startActivity(net);
                return true;
            case R.id.addManuall:
                Intent add = new Intent(this,EditActivity.class);
                add.putExtra("id",-1);//the id i sent is to get recognized at the next activity that its adding movie that i want
                startActivityForResult(add,1);
                return true;
            case R.id.exit:
                finish();
                return true;
            case R.id.deleteAll:
                deleteall();
                return true;

        }

        return false;
    }
// result for the activity for result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// getting the extras
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                int No=data.getIntExtra("No",0);
                String name=data.getStringExtra("name");
                String des=data.getStringExtra("des");
                String url1=data.getStringExtra("url");

                if(des.equals("")) {
                    if ( url1.equals("")) {
                        MovieSample movieSample = new MovieSample(name,"","",No);
                        movieSample.setWatched(0);
                        db.addMovie(movieSample);
                    } else {
                        MovieSample movieSample = new MovieSample(name, "", url1,No);
                        movieSample.setWatched(0);
                        db.addMovie(movieSample);
                    }
                }else if(url1.equals("")){
                        MovieSample movieSample = new MovieSample(name,des,"",No);
                    movieSample.setWatched(0);
                    db.addMovie(movieSample);
                }else{
                    MovieSample movieSample = new MovieSample(name,des,url1,No);
                    movieSample.setWatched(0);
                    db.addMovie(movieSample);
                }


                this.recreate();



            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }
}