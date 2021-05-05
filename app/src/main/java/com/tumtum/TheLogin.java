package com.tumtum;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TheLogin extends AppCompatActivity {//Logs in application
    EditText UsernameEt, PasswordEt;
    TextView loggedIn;
    AlertDialog alertDialog;
    String username ;
    String password ;
    String type ;
    String isLogged;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_login);
        UsernameEt = findViewById(R.id.username);
        PasswordEt = findViewById(R.id.password);
        configureBtnHome();
        configureBtnLogin();
        configureBtnRegister();
    }
    private void configureBtnRegister(){
        Button button = (Button) findViewById(R.id.btn_Regester);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onRegister(view);
                ButtonChanger();
            }
        });
    }
    private void configureBtnHome(){

        Button button = (Button) findViewById(R.id.btn_home);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(TheLogin.this, MainActivity.class));
            }
        });

    }
    private void configureBtnLogin(){
        Button button = (Button) findViewById(R.id.btn_Login);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onLogin(view);
                Log.d("onLogin status", "On login exited sucessfully!");
                ButtonChanger();
            }
        });
    }
    private void onLogin(View view){
        Log.d("a","Button Pressed!");
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String type = "login";
        String isLogged = bgWorker.loginChecker();
        if (isLogged.equals("yes")){
            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Login Status");
            alertDialog.setMessage("You are already logged in!");
            alertDialog.show();
        }
        else{
            bgWorker bgWorker = new bgWorker(this);
            bgWorker.execute(type, username, password);
        }
    }
    private void onRegister(View view){
        Log.d("a","Button Pressed!");
         username = UsernameEt.getText().toString();
         password = PasswordEt.getText().toString();
         type = "regester";
         isLogged = bgWorker.loginChecker();
        if (isLogged.equals("yes")){
            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Login Status");
            alertDialog.setMessage("You are already logged in!");
            alertDialog.show();
        }
        else{
            bgWorker bgWorker = new bgWorker(this);
            bgWorker.execute(type, username, password);
            isLogged = bgWorker.loginChecker();
            if(isLogged.equals("yes")){
                //Add the textview back at a later date, or don't
            }
        }
    }
    public void ButtonChanger(){
        isLogged = bgWorker.loginChecker();
        Log.d("isLogged checker", isLogged);
        if(isLogged.equals("yes")){
            //Add the textview back at a later date, or don't
        }
    }
}