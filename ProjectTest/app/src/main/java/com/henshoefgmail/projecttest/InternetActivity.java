package com.henshoefgmail.projecttest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.List;

public class InternetActivity extends AppCompatActivity {

    public ListView listViewMovies;
    public List<MovieSample> tempMovieSamples;
    public MoviesReaderController moviesReaderController;
    public Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_from_internet);
        getSupportActionBar().hide();

        mContext = App.getContext();

        listViewMovies = (ListView) findViewById(R.id.listView);
        moviesReaderController = new MoviesReaderController(this);

        listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                send(i);
            }
        });
    }

    public void send(int i){
        String tempName = listViewMovies.getAdapter().getItem(i).toString();
        String baseImageUrl = "http://image.tmdb.org/t/p/w185";//cuz' the obtained image url is only part of the URL of the image
        String baseYouTube = "https://www.youtube.com/watch?v=";

        tempMovieSamples = moviesReaderController.giveMovies();

        for(int j = 0; j< tempMovieSamples.size(); j++){
            if(tempName.equals(tempMovieSamples.get(j).getSubject())){
                if(i==j){
                    String description = tempMovieSamples.get(j).getBody();
                    String imageUrl = baseImageUrl + tempMovieSamples.get(j).getUrl();
                    int No = tempMovieSamples.get(j).getOrderNumber();

                    Intent editActivity = new Intent(this,EditActivity.class);
                    editActivity.putExtra("No",No);
                    editActivity.putExtra("title",tempName);
                    editActivity.putExtra("des",description);
                    editActivity.putExtra("url",imageUrl);
                    editActivity.putExtra("id",1);
                    if (mContext instanceof Activity) {
                        ((Activity) mContext).startActivityForResult(editActivity, 1);
                        finish();
                    }
                }
            }
        }
    }

    public void showMovies(View v){
        EditText nameText = (EditText) findViewById(R.id.name);
        String name = nameText.getText().toString();
        moviesReaderController.readAllMovies(name);
    }

    public void cancel(View v){
        finish();
    }
}