package com.tumtum;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import android.view.View;
import android.widget.Button;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
//Just a navigation class, doesn't actually do anything
AlertDialog alertDialog;
static String FoodOrDrink = "food"; //Trying out intents, should work?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureBtnFood();
        configureBtnLoP();
        configureBtnLogin();
        configureBtnDrink();
    }

    private void configureBtnFood(){
        Button button = (Button) findViewById(R.id.button_Food);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FoodOrDrink = "Food";
                loginCheckerFood();
            }
        });
    }
    private void configureBtnDrink(){
        Button button = (Button) findViewById(R.id.button_Drink);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FoodOrDrink = "Drink";
                loginCheckerFood();
            }
        });
    }
    private void configureBtnLoP(){
        Button button = (Button) findViewById(R.id.button_List_of_places);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, ListOfThings.class));
            }
        });
    }
    private void configureBtnLogin(){
        Button button = (Button) findViewById(R.id.button_Login);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, TheLogin.class));
            }
        });
    }
    private void loginCheckerFood(){
        if(bgWorker.usernameChecker().equals("You are not logged in!")){
            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Login Status");
            alertDialog.setMessage("You must log in first!");
            alertDialog.show();
        }
        else{
            startActivity(new Intent(MainActivity.this, SomethingToEat.class));
        }
    }
 public static String FoodOrDrink(){
        return FoodOrDrink;
    }
}