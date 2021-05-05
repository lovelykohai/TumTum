package com.tumtum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static java.lang.Double.parseDouble;

public class MapsFragment extends Fragment {
    public static GoogleMap googleMap;
    public static String theTitle = "Nowhere yet!";
    public OnMapReadyCallback callback = new OnMapReadyCallback() {
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            Log.d("The map has been updated through OMR!", ":)");
            googleMap.setMinZoomPreference(15.0f);
            googleMap.setMaxZoomPreference(20.0f);
            double Longitute = parseDouble(bgWorker.ReturnLong());
            double Latitude = parseDouble(bgWorker.ReturnLat());
            Log.d("The title should be:", bgWorker.ResultGetter());
            theTitle = bgWorker.ResultGetter();
            DisplaySuggestion.suggestion.setText(theTitle);
            LatLng MapLocal = new LatLng(Latitude, Longitute);
            googleMap.addMarker(new MarkerOptions().position(MapLocal).title(theTitle));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(MapLocal));
        }
    };



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
    public static void UpdateMap(GoogleMap googleMap){
        Log.d("The map has been updated through UM!", ":)");
        double Longitute = parseDouble(bgWorker.ReturnLong());
        double Latitude = parseDouble(bgWorker.ReturnLat());
        theTitle = bgWorker.ResultGetter();
        DisplaySuggestion.suggestion.setText(theTitle);
        LatLng MapLocal = new LatLng(Latitude, Longitute);
        googleMap.addMarker(new MarkerOptions().position(MapLocal).title(theTitle));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(MapLocal));
    }
}