package com.henshoefgmail.projecttest;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

public class EditActivity extends AppCompatActivity {

    EditText title;
    EditText description;
    EditText url1;
    LinearLayout l;
    ImageView imageView;
    int ID;
    int check;
    DataBase db;
    int No=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().hide();
        imageView = (ImageView) findViewById(R.id.imageView);
        title = (EditText) findViewById(R.id.name);
        description = (EditText) findViewById(R.id.description);
        url1 = (EditText) findViewById(R.id.url);
        l = (LinearLayout) findViewById(R.id.outside);
        db = new DataBase(this);
        Intent A = getIntent();
        Bundle b = A.getExtras();
        if(b!=null)
        {

            String NAME =(String) b.get("name");
            String d =(String) b.get("des");
            String u =(String) b.get("url");
            int id = (int) b.get("id");
            ID=id;

            if(null!=b.get("No")){
                No = (int) b.get("No");
            }

            if(NAME==null){
                check=0;
                NAME=(String) b.get("title");
            }else{
                check=-1;
            }


            title.setText(NAME);
            description.setText(d);
            url1.setText(u);

        }
    }

    public void ok(View v){
        DataBase db = new DataBase(this);
        if(check==-1){
            db.deleteMovie(ID);
        }
        Intent returnIntent = new Intent();
        String name = title.getText().toString();
        List<MovieSample> m =db.getAllMovieList();
        boolean nameExist = true;
        for(int i=0;i<m.size();i++){
            if(name.equals(m.get(i).getSubject())) {
                nameExist = false;
            }
        }
        if(nameExist) {
            String des = description.getText().toString();
            String url = url1.getText().toString();
            if (name.equals("")) {
                Toast.makeText(this, getResources().getText(R.string.toast), Toast.LENGTH_SHORT).show();
            } else {
                returnIntent.putExtra("No", No);
                returnIntent.putExtra("name", name);
                returnIntent.putExtra("des", des);
                returnIntent.putExtra("url", url);
                setResult(Activity.RESULT_OK, returnIntent);
                No=0;
                finish();
            }
        }else{
            Toast.makeText(this,"the movie title is already existed",Toast.LENGTH_SHORT).show();
        }


    }
    public void cancel(View v){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
    public void show(View v){

        String url = url1.getText().toString();
        new DownloadImageTask(this,l,this, imageView, url).execute();
    }
}
