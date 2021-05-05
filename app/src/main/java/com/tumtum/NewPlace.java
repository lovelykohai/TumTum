package com.tumtum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class NewPlace extends AppCompatActivity {//Allows the user to store a new place into the local DB
//Not gonna give users direct DB access, security reasons
    private EditText mPlaceNameET;
    private EditText mCommentET;
    private EditText mTagET;
    private TextView mTagTV;
    private Button mBackBtn;
    private Button mAddBtn;
    public static String textHolder = "nothing"; //Flare to detect if placename is implicit

    public NewPlace() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {//Code for manually adding a new place to the list
        //Outside of the normal way (Reccomendation system)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_place);
        mPlaceNameET = (EditText) findViewById(R.id.place_Name_ET3);
        mCommentET = (EditText) findViewById(R.id.place_Comment_ET3);
        mTagET = (EditText) findViewById(R.id.place_tag_ET);
        mTagTV = (TextView) findViewById(R.id.Place_Tag_TV);
        mBackBtn = (Button) findViewById(R.id.btn_bck4);
        mAddBtn = (Button) findViewById(R.id.btn_add);
        if(textHolder.equals("nothing")){
            //Make the tag stuff invisible
            mTagET.setVisibility(View.GONE);
            mTagTV.setVisibility(View.GONE);
        }
        else{
            mTagET.setVisibility(View.VISIBLE);
            mTagTV.setVisibility(View.VISIBLE);
            mPlaceNameET.setText(textHolder);
        }
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Place place = new Place();
                place.setmName(mPlaceNameET.getText().toString());
                place.setmComment(mCommentET.getText().toString());
                PlacesBase.get(getApplicationContext()).newPlace(place);
                Toast.makeText(NewPlace.this,"Place added to list", Toast.LENGTH_LONG).show();
                DoSentiment(mCommentET.getText().toString());
                mPlaceNameET.setText("");
                mCommentET.setText("");
                startActivity(new Intent(NewPlace.this, ListOfThings.class));
            }
        });
    }
    public void DoSentiment(String Comment){
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        Python python = Python.getInstance();
        String rating = python.getModule("processtext").callAttr("pred", Comment).toString();
        Log.d("Out of 5, the AI rates this:", rating);
    }
}