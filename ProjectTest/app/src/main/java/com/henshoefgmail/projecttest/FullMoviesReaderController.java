package com.henshoefgmail.projecttest;


import android.app.Activity;
import android.view.View;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;


public class FullMoviesReaderController extends FullMovieController{
    public Activity context;//the context of the current activity that on
    public int aSwitch;

    public FullMoviesReaderController(Activity activity) {
        super(activity);
        context = activity;

    }


    public void getFullMovie(int No, int num) {
        //the aSwitch is to check if its a existed movie page on the list or its a random that i ask for
        aSwitch = num;
        //like i writed before the NO(NumberOrder) is the real id in the original api and to send back to get single movie
        HttpRequest httpRequest = new HttpRequest(this);
        httpRequest.execute("https://api.themoviedb.org/3/movie/" + No + "?api_key=fdbafdad226138d461dcb4c9b2d663f5");
    }


    public void onSuccess(String downloadedText) {

        try {


            JSONObject jsonObject = new JSONObject(downloadedText);
//i get all the needed from the Json object

            int uselessInt = 0;
            int No = jsonObject.getInt("id");
            String name = jsonObject.getString("title");
            String desc = jsonObject.getString("overview");
            String poster_path = jsonObject.getString("poster_path");
            String baseImageUrl = "http://image.tmdb.org/t/p/w185";
            String image = baseImageUrl + poster_path;
            int budget = jsonObject.getInt("budget");
            int runtime;
            try {//in case the runtime is null
                runtime = jsonObject.getInt("runtime");
            }catch (Exception eee) {
                runtime = 0;
            }
            String release_date = jsonObject.getString("release_date");
            String va = jsonObject.getString("vote_average");
            float vote_average = Float.parseFloat(va);

//i declare all the details on the object of full movie
            FullMovieSampleInfo movie = new FullMovieSampleInfo(uselessInt, name, desc, image, No, vote_average, release_date, budget, runtime);

            MoviePage.setAllDetails(movie);//- here i send it to get placed on the page layout and for the user to see the infomation


        } catch (JSONException ex) {
            //the aSwitch is to check if its a existed movie page on the list or its a random that i ask for
            if (aSwitch == 1) {
                //if i got an exception while getting the json or i dont have json to get at all i want the random option to repeat itself
                MainActivity mContext = (MainActivity) App.getContext();
                View v = App.getmView();
                mContext.random(v);
            }
            Toast.makeText(activity, "Give me a second", Toast.LENGTH_LONG).show();//for my own use to see if there is an error while running
        }


        progressDialog.dismiss();


    }
}