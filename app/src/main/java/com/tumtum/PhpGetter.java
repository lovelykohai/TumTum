package com.tumtum;

public class PhpGetter {
    public static String php(String request, String Username, String Password){
        if(request.equals("login")){
            return ("SELECT * FROM dbo.users WHERE Username = 'josh' AND password = 'Password';");
        }
        return null;
    }
}
