package com.tumtum;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PlaceDatabaseHelper extends SQLiteOpenHelper {
    public PlaceDatabaseHelper(@Nullable Context context) {
        super(context, "placeDatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {// creates local database for storing the "places I've been" data
        //Is way easier than having to send/recieve from cloud whenever anything happens
        db.execSQL("create table places(" +
                "_id integer primary key autoincrement, " +
                "_uuid, " +
                "place_name,"+
                "place_score)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//If I were to recreate the local DB, I would need to update it onUpgrade rather than onCreate
// Because it's already been created
    }

    public PlaceDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
}
