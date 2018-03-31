package com.henshoefgmail.projecttest;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * Created by hensh on 30/03/2018.
 */

public class TrailerReaderController extends TrailerController {
    String trailer="";
    public Activity context;//the context of the current activity that on
    public int aSwitch;

    public TrailerReaderController(Activity activity) {
        super(activity);
        context = activity;

    }


    public void getTrailer(int no, int num) {
        //the aSwitch is to check if its a existed movie page on the list or its a random that i ask for
        aSwitch = num;
        //like i writed before the NO(NumberOrder) is the real id in the original api and to send back to get single movie
        HttpRequest httpRequest = new HttpRequest(this);
        httpRequest.execute("https://api.themoviedb.org/3/movie/"+no+"/videos?api_key=fdbafdad226138d461dcb4c9b2d663f5&language=en-US");

    }


    public void onSuccess(String downloadedText) {

        try {


            JSONObject jsonObject = new JSONObject(downloadedText);
//i get all the needed from the Json object

            int uselessInt = 0;
            int No = jsonObject.getInt("id");
            JSONArray results= jsonObject.getJSONArray("results");

            if(results.equals("")){
                trailer="";
            }else {
                JSONObject tr=results.getJSONObject(0);
                trailer = tr.getString("key");
            }
            String baseYouTube = "https://www.youtube.com/watch?v=";
            String video = baseYouTube + trailer;

//i declare all the details on the object of full movie
            TrailerInfo trailerUrl = new TrailerInfo(video);

            MoviePage.setTrailer(trailerUrl);//- here i send it to get placed on the page layout and for the user to see the infomation


        } catch (JSONException ex) {
            //the aSwitch is to check if its a existed movie page on the list or its a random that i ask for
            if (aSwitch == 1) {
                //if i got an exception while getting the json or i dont have json to get at all i want the random option to repeat itself
                MainActivity mContext = (MainActivity) App.getContext();
                View v = App.getmView();

            }
          //  Toast.makeText(activity, "Give me a second", Toast.LENGTH_LONG).show();//for my own use to see if there is an error while running
        }


        progressDialog.dismiss();


    }
}