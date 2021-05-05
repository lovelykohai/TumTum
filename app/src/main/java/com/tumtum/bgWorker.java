package com.tumtum;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class bgWorker extends AsyncTask<String,Void,String> {//Should handle all the database connections
    //Gonna hold the command in params
    private static String loggedin = "no";
    private static String finalUsername ="You are not logged in!";
    private static String finalPass ="You are not logged in!";
    private static String suggestion = "Nothing generated!";
    static String Longitude = "0";
    static String Latitude = "0";
    public String iWant = "";
    public Connection con;
    public Boolean isSuccess = false;
    public ResultSet rs;
    String name1 = "";
    public String checker = MainActivity.FoodOrDrink();
    Context context;
    AlertDialog alertDialog;
    public String result;
    bgWorker (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        if(type.equals("test")){
            String k = executeQuery("login","Username","Password");
        }
        else {
            basicHTTPConnection(params[1],params[2],params[0]);
            return result;
        }
        return null;
    }
    public void basicHTTPConnection(String User_Name, String Password, String Request){
            Log.d("The Checker is:", checker);
            result = executeQuery(Request,User_Name,Password);
            if(Password.equals("NA")){
                finalPass = "NA";
            }
            if(result.contains("Invalid")) {
            }
            if(result.contains("No current row")){
                loggedin = "no";
                finalPass = "There IS an error";

            }
            else{
                finalUsername = User_Name;
                finalPass = Password;
                loggedin = "yes";
                suggestion = result;
            }
    }
    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog = new AlertDialog.Builder(context).create();
        if(finalPass.equals("NA")){
            suggestion = result;
            alertDialog.setTitle("I suggest the restaurant...");
        }
        else{
            suggestion = result;
            alertDialog.setTitle("Login Status");
            if(loggedin.equals("no")){
                result = "No user found!";
            }
            else{
                result = ("logged in as:" + result);
            }

        }

        alertDialog.setMessage(result);
        alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    public static String loginChecker(){
        return loggedin;
    }
    public static String usernameChecker(){
        return finalUsername;
    }
    public static String ResultGetter(){
        return suggestion;
    }

    public Connection connectionclass()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://lovelykohai.database.windows.net:1433;DatabaseName=tumtumtwo;user=lovelykohai@lovelykohai;password=100Nights!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (ClassNotFoundException | SQLException e) {
            Log.e("Error", e.getMessage());
        }
        return connection;
    }
    public String executeQuery(String request, String username, String Password){
        String z = "";
        String holder2="";
        String holder3="";
        try{
            con = connectionclass();
            if (con == null){
                z = "Check your internet!";
            }
            else{
                String query ="";
                String holder ="";
                if(request.equals("login")){
                     query = "select * from [dbo].[UserAccounts] WHERE Username ='"+username+"'AND Password = '"+Password+"';";
                     holder = "username";
                }
                else if(request.equals("regester")){
                     query = "INSERT INTO [dbo].[UserAccounts](Username,Password,AmountOfPlacesVisited) VALUES('"+username+"','"+Password+"',0);";
                    holder = "Regester";
                }
                else if(request.equals("IWant")){
                    iWant = BucketCatcher.CreateRealDesire();

                    if(checker.equals("Food")){
                        query = "SELECT TOP 1 name, PropLong, Proplat FROM [dbo].[Restaurants]\n" +
                                "WHERE amenity = 'Restaurant'"+
                                "AND cuisine = '" + iWant+
                                "' OR amenity = 'fast_food' AND cuisine = '" + iWant +
                                "' ORDER BY NEWID()";
                        holder3 = "PropLong";
                        holder2 = "Proplat";
                        holder = "name";
                    }
                    else if(checker.equals("Drink")){
                        if (SomethingToEat.ReturnAlcohol()){
                            query = "SELECT TOP 1 name, PropLong, Proplat FROM [dbo].[Restaurants]\n" +
                                    "WHERE amenity = 'Cafe' AND cuisine = '" + iWant+ "' OR amenity = 'pub'"+
                                    "AND cuisine = '" + iWant+
                                    "' ORDER BY NEWID()";
                            Log.d("the Q:", query);
                        }
                        else{
                            query = "SELECT TOP 1 name, PropLong, Proplat FROM [dbo].[Restaurants]\n" +
                                    "WHERE amenity = 'Cafe'"+
                                    "AND cuisine = '" + iWant+
                                    "' ORDER BY NEWID()";
                            Log.d("the Q:", query);
                        }
                        holder3 = "PropLong";
                        holder2 = "Proplat";
                        holder = "name";
                    }
                }
                else if(request.equals("Surprise")){
                    if(checker.equals("Food")){
                        query = "SELECT TOP 1 name, PropLong, Proplat FROM [dbo].[Restaurants]" +
                                "WHERE amenity = 'Restaurant'"+
                                " OR amenity = 'fast_food' " +
                                "ORDER BY NEWID()";
                        holder3 = "PropLong";
                        holder2 = "Proplat";
                        holder = "name";
                    }
                    if(checker.equals("Drink")){
                        if (SomethingToEat.ReturnAlcohol()){
                            query = "SELECT TOP 1 name, PropLong, Proplat FROM [dbo].[Restaurants]\n" +
                                    "WHERE amenity = 'Cafe' OR amenity = 'pub'"+
                                    "ORDER BY NEWID()";
                        }
                        else{
                            query = "SELECT TOP 1 name, PropLong, Proplat FROM [dbo].[Restaurants]\n" +
                                    "WHERE amenity = 'Cafe'"+
                                    "ORDER BY NEWID()";
                        }
                        holder3 = "PropLong";
                        holder2 = "Proplat";
                        holder = "name";
                    }
                    holder = "name";
                }
                else{
                    Log.d("Request = ",request);
                     query = ":(";
                }
                Log.d("The Q =",query);
                Statement stmt = con.createStatement();

                if(holder.equals("Regester")){
                    z = "New account made!";
                    isSuccess = true;
                    con.close();
                }
                else{
                    rs = stmt.executeQuery(query);
                    Log.d("Results set Next", String.valueOf(rs.next()));
                    name1= rs.getString(holder);
                    if(!holder2.equals("")){
                        Longitude  = rs.getString("propLong");

                        Latitude = rs.getString("propLat");

                    }
                    z = z + name1;
                    isSuccess = true;
                    con.close();
                }

            }
        } catch (SQLException throwables) {
            isSuccess = false;
            throwables.printStackTrace();
            z = throwables.getMessage();
            Log.d("Error", z);
        }
        return z;
    }
    public static String ReturnLong(){
        return Longitude;
    }
    public static String ReturnLat(){
        return Latitude;
    }
}