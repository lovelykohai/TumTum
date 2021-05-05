package com.tumtum;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlacesBase { //Stores all information for local database of places
    //Will be stored on phone storage as cookie
    //private List<Place> mPlaces;
    private SQLiteDatabase mDatabase;
    private PlacesBase(Context context){
        //mPlaces = new ArrayList<>();
        mDatabase = new PlaceDatabaseHelper(context).getWritableDatabase();
       // for(int i = 0; i<100; i++){
        //   Place place = new Place();
       //     place.setmComment("comment #"+i);
       //     place.setmName("Place #"+i);
       //    mPlaces.add(place);
       // } Initial design where I wanted the database to populate locally, got too messy so I made
        // placeDatabaseHelper
    }
    public List<Place> getPlaces(){

        //return mPlaces;
        Cursor cursor = mDatabase.query("places",null,null,null,
                null,null,null);
        List<Place> places = new ArrayList<>();
        try{
            if(cursor!=null && cursor.getCount()>0){
                if(cursor.moveToFirst()){
                while(!cursor.isAfterLast()){
                    //Searches for a specific place using cursor object
                    String Place_Name = cursor.getString(cursor.getColumnIndex("place_name"));
                    String Place_comment = cursor.getString(cursor.getColumnIndex("place_score"));
                    String uuid = cursor.getString(cursor.getColumnIndex("_uuid"));
                    Place place = new Place(UUID.fromString(uuid));
                    place.setmName(Place_Name);
                    place.setmComment(Place_comment);
                    places.add(place);
                    cursor.moveToNext();
                }
                }
            }

        }
        finally{
            cursor.close();
        }
        return places;
    }
    private static PlacesBase mPlacesBase;
    public static PlacesBase get(Context context){
        if(mPlacesBase == null){
            mPlacesBase = new PlacesBase(context);
        }
        return mPlacesBase;
    }
    public void deletePlace(UUID ID){
       // for(Place place:mPlaces){
       //     if(place.getmID().equals(ID)){
      //          mPlaces.remove(place);
      //          break;
      //      }
      //  }
        mDatabase.delete("places","_uuID=?", new String[]{ID.toString()});
    }
    public void updatePlace(UUID ID, String PlaceName, String Comment){
//        for(Place place:mPlaces){
//            if(place.getmID().equals(ID)){
//                place.setmName(PlaceName);
//                place.setmComment(Comment);
//                break;
//            }
//        }
        Place place = new Place(ID);
        place.setmName(PlaceName);
        place.setmComment(Comment);
        mDatabase.update("places",getContentValues(place),"_uuid=?",
                new String[]{ID.toString()});
    }
    public Place getPlace(UUID ID){
     //   for(Place place:mPlaces){
     //       if(place.getmID().equals(ID)){
     //           return place;
     //       }
     //   }
      //  return null;
        Cursor cursor = mDatabase.query("places",null,"_uuid = ?",new String[]{ID.toString()},null,null,null);
        try{
            cursor.moveToFirst();
            String Place_name = cursor.getString(cursor.getColumnIndex("place_name"));
            String Place_score = cursor.getString(cursor.getColumnIndex("place_score"));
            Place place = new Place(ID);
            place.setmName(Place_name);
            place.setmComment(Place_score);
            return place;
        }
        finally{
            cursor.close();
        }
    }
    public void newPlace(Place place){
        //mPlaces.add(place);
        mDatabase.insert("places",null,getContentValues(place));
    }
    private ContentValues getContentValues(Place place){
     ContentValues contentValues = new ContentValues();
     contentValues.put("_uuid", place.getmID().toString());
     contentValues.put("place_name", place.getmName());
     contentValues.put("place_score", place.getmComment());
    return contentValues;
    }
}
