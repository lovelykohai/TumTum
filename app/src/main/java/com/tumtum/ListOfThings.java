package com.tumtum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListOfThings extends AppCompatActivity {
    //Local DB instantiation
    private RecyclerView mPlaces_recyclerviewer;
    private List<Place> mPlaces;
    private PlacesAdapter mPlacesAdapter;
    private int mPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_things);
        mPlaces_recyclerviewer = findViewById(R.id.places_recyclerview);
        mPlaces_recyclerviewer.setLayoutManager(new LinearLayoutManager(this));
        mPlaces = PlacesBase.get(getApplicationContext()).getPlaces();

        mPlacesAdapter = new PlacesAdapter(mPlaces);
        mPlaces_recyclerviewer.setAdapter(mPlacesAdapter);
        configureBtnBck();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.new_item:
                NewPlace.textHolder="nothing";
                Intent intent = new Intent(this, NewPlace.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void configureBtnBck(){
            Button button = (Button) findViewById(R.id.btn_bck);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    startActivity(new Intent(ListOfThings.this, MainActivity.class));
                }
            });
    }
    class PlacesAdapter extends RecyclerView.Adapter<PlaceViewHolder>{
        private List<Place> mPlaces;

        public PlacesAdapter(List<Place> places) {
            super();
            this.mPlaces = places;
        }

        @NonNull
        @Override
        public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PlaceViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
            holder.bind(this.mPlaces.get(position), position);
        }

        @Override
        public int getItemCount() {
            return this.mPlaces.size();
        }
        public void setPlaces(List<Place> places){
            this.mPlaces = places;
        }
    }
    class PlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mName;
        private UUID mID;
        private int Position;
        @Override
        public void onClick(View view) {
            mPosition = Position;
            Intent data = new Intent(ListOfThings.this, PlaceDetails.class);
            data.putExtra("place_id",this.mID);
            startActivityForResult(data, 1);
        }

        private TextView mComment;
        public PlaceViewHolder(ViewGroup container){
            super(LayoutInflater.from(ListOfThings.this).inflate(R.layout.list_of_things, container, false));
            itemView.setOnClickListener(this);
            mName = (TextView) itemView.findViewById(R.id.place_name);
            mComment = (TextView) itemView.findViewById(R.id.place_comment);
        }
        private void bind(Place place, int pos){
            Position = pos;
            this.mID = place.getmID();
            mComment.setText(place.getmName());
            mName.setText(place.getmComment());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_of_places_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            mPlaces = PlacesBase.get(getApplicationContext()).getPlaces();
            mPlacesAdapter.setPlaces(mPlaces);
            if(requestCode == 1){
                if(data!=null){
                    mPlacesAdapter.notifyDataSetChanged();
                    String action = data.getStringExtra("action");
                    if(action.equals("update")){
                        mPlacesAdapter.notifyItemChanged(mPosition);
                    }
                    if(action.equals("delete")){
                        mPlacesAdapter.notifyItemRemoved(mPosition);
                    }
                }
            }
        }

    }
}
class Place{
    private String mName;
    private String mComment;
    private UUID mID;

    public Place(){
        mID = UUID.randomUUID();
    }
    public Place(UUID ID){
        this.mID = ID;
    }

    public UUID getmID() {
        return mID;
    }

    public void setmComment(String mComment) {
        this.mComment = mComment;
    }

    public void setmName(String Name) {
        this.mName = Name;
    }

    public String getmName() {
        return mName;
    }

    public String getmComment() {
        return mComment;
    }
}
