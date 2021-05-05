package com.tumtum;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.tumtum.MapsFragment.googleMap;

public class DisplaySuggestion extends AppCompatActivity {
    String theIntent;
    String Username;
    String Password;
    MapsFragment fragmentInstance= new MapsFragment();
    Boolean TheFirst = false;
    static TextView suggestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_suggestion);
        theIntent = SomethingToEat.ReturnIntention();
        Log.d("The internt:",theIntent);

        configBtnNo();
        configBtnBck();
        configBtnYes();
        suggestion = (TextView)findViewById(R.id.suggestion);
        suggestion.setText(bgWorker.ResultGetter());
        getSupportFragmentManager().beginTransaction().remove(fragmentInstance).commit();
        if(SomethingToEat.theFirst == true){
            FirstTime(theIntent);
            Handler hanlder = new Handler();
            hanlder.postDelayed(new Runnable(){

                @Override
                public void run() {
                    startActivity(getIntent());
                }
            },1);
        }
    }
    public void configBtnYes(){
        Button btn_yes = (Button) findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                buildButton();
            }
        });
    }
    public void configBtnBck(){
        Button btn_bck = (Button) findViewById(R.id.btn_bckDS);
        btn_bck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(DisplaySuggestion.this, MainActivity.class));
            }
        });
    }
    public void configBtnNo(){
        Button Btn_No = (Button) findViewById(R.id.Btn_No);
            Btn_No.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    NewRandom(theIntent);
                    Handler hanlder = new Handler();
                    hanlder.postDelayed(new Runnable(){

                        @Override
                        public void run() {
                            startActivity(getIntent());
                        }
                    },1);
                }
            });
    }
    public void FirstTime(String theIntent){
        Username = bgWorker.usernameChecker();
        bgWorker bgWorker = new bgWorker(this);
        bgWorker.execute(theIntent, Username, "NA");
        suggestion.setText(bgWorker.ResultGetter());
        SomethingToEat.theFirst = false;
    }
    public void NewRandom(String theIntent){
        Username = bgWorker.usernameChecker();
        bgWorker bgWorker = new bgWorker(this);
        bgWorker.execute(theIntent, Username, "NA");
    }
    public static void updateText(String Text){
        Log.d("The sugestion should have updated to:",bgWorker.ResultGetter());
        suggestion.setText(Text);
    }
    public void buildButton(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Restaurant Chosen!");
        alert.setMessage("Would you like to leave a review of this place?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String placeName = (bgWorker.ResultGetter());
                NewPlace.textHolder = placeName;
                startActivity(new Intent(DisplaySuggestion.this, NewPlace.class));
            }
        });
        alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Place place = new Place();
                place.setmName(bgWorker.ResultGetter());
                place.setmComment("No comment.");
                PlacesBase.get(getApplicationContext()).newPlace(place);
                Toast.makeText(DisplaySuggestion.this,"Place added to list," +
                                " please feel free to update later in the 'Places I have been' tab", Toast.LENGTH_LONG).show();
                startActivity(new Intent(DisplaySuggestion.this, MainActivity.class));
            }
        });
        alert.create().show();
    }

}