package com.henshoefgmail.projecttest;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import java.util.List;

public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;//for the use of activity for result
    public Button delete, edit;
    public String NAME;
    public int ID;//name and id to know what movie i want to delete or edit
    DataBase db;//to change by my decision
    List<MovieSample> names;// the list to get the names and to delete from

    public CustomDialogClass(Activity a,String name,int id) {
        super(a);
        NAME=name;
        ID=id;
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.edit_dialog);
        delete = (Button) findViewById(R.id.btn_delete);
        edit = (Button) findViewById(R.id.btn_edit);
        delete.setOnClickListener(this);
        edit.setOnClickListener(this);
        db= new DataBase(c);
        names=db.getAllMovieList();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                db.deleteMovie(ID);
                c.recreate();//so the changes will appear right after the click
                break;
            case R.id.btn_edit:

                for(int i=0;i<names.size();i++){
                    if(NAME.equals(names.get(i).getSubject())){
                        String title =names.get(i).getSubject();
                        String des =names.get(i).getBody();
                        String url =names.get(i).getUrl();
                        int id = names.get(i).get_id();
                        int No = names.get(i).getOrderNumber();
//send the edit page the exist movie details to edit
                        Intent editActivity = new Intent(c,EditActivity.class);
                        editActivity.putExtra("No",No);
                        editActivity.putExtra("name",title);
                        editActivity.putExtra("des",des);
                        editActivity.putExtra("url",url);
                        editActivity.putExtra("id",id);
                        c.startActivityForResult(editActivity,1);
                        break;
                    }
            }


            default:
                break;
        }
        dismiss();
    }
}