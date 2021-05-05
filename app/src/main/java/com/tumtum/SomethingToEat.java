package com.tumtum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class SomethingToEat extends AppCompatActivity {
    static String intention = "Nothing";
    static String typeofplace = "Nothing";
    static String IWant = "Nothing";
    static String FoD="";
    static TextView TV;
    static EditText Hint;
    static boolean theFirst = true;
    static boolean AlcoholChecker;
    Switch alcoholSwitch;
    //EditText enterFoodSug = (EditText) findViewById(R.id.enterFoodSug);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_something_to_eat);
        alcoholSwitch = (Switch) findViewById(R.id.AlcoholSwitch);
        TV = (TextView)findViewById(R.id.TheTV); //Base text view, holds food or drink depending
        //what user clicked
        Hint = (EditText)findViewById(R.id.enterFoodSug);
        //Edit text text box, again stores info based on what user clicked
        FoD = MainActivity.FoodOrDrink();
        //Grabs what user clicked, food or drink
        if(FoD.equals("Food")){
            //Sets values for food
            TV.setText("What kind of food would I like?");
            Hint.setHint("Enter a genre of food! (Example: Italian)");
        }
        if(FoD.equals("Drink")){
            //Sets values for drink
            TV.setText("What kind of Drink would I like?");
            Hint.setHint("Enter a type of beverage! (Example: Bubble Tea)");

        }
        configureBtnSurprise();
        configureBtnBack();
        configureBtnWant();
    }
    public void configureBtnBack(){
        //sets back button to go home
        Button button = findViewById(R.id.button_Back_Food);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(SomethingToEat.this, MainActivity.class));
            }
        });
    }
    public void configureBtnWant(){
        //Sets the "I want" button to send off request
        Button button = findViewById(R.id.button_Submit_Food);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText ET = findViewById(R.id.enterFoodSug);
                AlcoholChecker = alcoholSwitch.isChecked();
                IWant = ET.getText().toString();
                intention = "IWant";
                typeofplace = "Restaurant";
                startActivity(new Intent(SomethingToEat.this, DisplaySuggestion.class));
            }
        });
    }

    public void configureBtnSurprise(){
        //Sets the surprise me button to ask for a random place
        Button button = findViewById(R.id.button_Surprise_Food);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                intention = "Surprise";
                typeofplace = "Restaurant";
                startActivity(new Intent(SomethingToEat.this, DisplaySuggestion.class));
            }
        });
    }
    public static String ReturnIntention(){
        return intention;
    }
    public static String ReturnWant(){
        return IWant;
    }
    public static boolean ReturnAlcohol(){return AlcoholChecker;
    }
}