package com.tumtum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

public class PlaceDetails extends AppCompatActivity {//The code for activity_place_details.xml

    private EditText mPlaceName_ET;
    private EditText mReview_ET;
    private Button mBtn_back;
    private Button mBtn_del;
    private Button mBtn_update;
    private UUID mID;
    public PlaceDetails() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        mID = (UUID) getIntent().getSerializableExtra("place_id");
        Place place = PlacesBase.get(getApplicationContext()).getPlace(mID);

        mPlaceName_ET = (EditText) findViewById(R.id.Place_Name_ET);
        mReview_ET = (EditText) findViewById(R.id.Place_Comment_ET);
        mBtn_back = (Button) findViewById(R.id.btn_bck1);
        mBtn_del = (Button) findViewById(R.id.btn_del);
        mBtn_update = (Button) findViewById(R.id.btn_update);
        mBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBtn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("action","update");
                setResult(RESULT_OK,intent);
                PlacesBase.get(getApplicationContext()).updatePlace(mID,mPlaceName_ET.getText().toString(), mReview_ET.getText().toString());
                Toast.makeText(PlaceDetails.this, "Place updated!.",Toast.LENGTH_LONG).show();
            }
        });
        mBtn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("action","delete");
                setResult(RESULT_OK,intent);
                PlacesBase.get(getApplicationContext()).deletePlace(mID);
                finish();
            }
        });
        mPlaceName_ET.setText(place.getmName());
        mReview_ET.setText(place.getmComment());

    }
}