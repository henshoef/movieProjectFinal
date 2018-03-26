package com.henshoefgmail.projecttest;


import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public abstract class MovieController  implements HttpRequest.Callbacks {

    protected static ArrayList<String> Movies; //this is a list just to display the movie names.
    protected Activity activity; //i get the activity so i would be able to get the id of a view from the layout
    protected ProgressDialog progressDialog;//the dialog that show's and tell the user the download and please wait
    protected ListView listViewMovies;
    public List<MovieSample> allMoviesData;

//------------start the dialog
    public MovieController(Activity activity) {
        this.activity = activity;
        listViewMovies = (ListView)activity.findViewById(R.id.listView);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Downloading...");
        progressDialog.setMessage("Please Wait...");
    }


    public void onAboutToStart() {
        progressDialog.show();
    }

    public void onError(String errorMessage) {
        progressDialog.dismiss();
    }
}