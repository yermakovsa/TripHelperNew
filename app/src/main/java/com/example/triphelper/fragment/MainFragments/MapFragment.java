package com.example.triphelper.fragment.MainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.triphelper.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import java.util.ArrayList;
import java.util.List;

import static com.example.triphelper.activity.MainActivity.WIDTH;
import static com.example.triphelper.activity.MainActivity.hotel;
import static com.example.triphelper.activity.MainActivity.listOfPlaces;
import static com.example.triphelper.placesAPI.PlaceAutocompleteAPI.KEY;
import static java.lang.Math.sqrt;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private int currInd = 0;
    private int BIG = 1000000000, SPEED = 22;
    private List<LatLng> places = new ArrayList<>();
    private List<LatLng []> res = new ArrayList<>();
    private Button btnNext;
    private Button bntPrevious;
    private DirectionsResult result = null;
    private GeoApiContext geoApiContext;
    private LatLngBounds.Builder latLngBuilder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.map, container, false);
        geoApiContext = new GeoApiContext.Builder()
                .apiKey(KEY)
                .build();
        btnNext = (Button) rootView.findViewById(R.id.buttonNextDay);
        bntPrevious = (Button) rootView.findViewById(R.id.buttonPreviousDay);
        fillRes();
        return rootView;
    }
    void fillRes(){
        int currTime = 600;
        boolean []used = new boolean[1000];
        List<LatLng> arrayOfPoint = new ArrayList<>();
        for(int i = 0; i < listOfPlaces.size(); i++)
            for(int j = 0; j < listOfPlaces.get(i).size(); j++){
                if(listOfPlaces.get(i).get(j).getIsChecked()){
                    arrayOfPoint.add(new LatLng(listOfPlaces.get(i).get(j).getLat(),
                            listOfPlaces.get(i).get(j).getLon()));
                }
            }
        for(int i = 0; i < 1000; i++)
            used[i] = false;
        ArrayList <Integer> indexes = new ArrayList<>();
        LatLng previous = new LatLng(hotel.getLat(), hotel.getLon());
        for(int i = 0; i < arrayOfPoint.size(); i++){
            int indexBest = -1;
            LatLng currBest = new LatLng(1000, 1000);
            for(int j = 0; j < arrayOfPoint.size(); j++){
                if(!used[j] && getDist(previous, currBest) > getDist(previous, arrayOfPoint.get(j))){
                    currBest = arrayOfPoint.get(j);
                    indexBest = j;
                }
            }
            used[indexBest] = true;
            currTime += getDist(previous, currBest) + 60;
            previous = currBest;
            indexes.add(indexBest);
            if(currTime >= 1140){
                currTime = 600;
                LatLng []ff = new LatLng[indexes.size()];
                for(int j = 0; j < indexes.size(); j++){
                    int ind = indexes.get(j);
                    ff[j] = arrayOfPoint.get(ind);
                }
                res.add(ff);
                indexes.clear();
                previous = new LatLng(hotel.getLat(), hotel.getLon());
            }
        }
        LatLng []ff = new LatLng[indexes.size()];
        for(int j = 0; j < indexes.size(); j++){
            int ind = indexes.get(j);
            ff[j] = arrayOfPoint.get(ind);
        }
        if(ff.length > 0) res.add(ff);
    }
    double getDist(LatLng a, LatLng b){
        return (((int)(sqrt(BIG * (
                (a.longitude - b.longitude) * (a.longitude - b.longitude)
                        + (a.latitude - b.latitude) * (a.latitude - b.latitude))) / SPEED)));
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().
                findFragmentById(R.id.googleMAP);
        supportMapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(getMark(-1));
        for (int i = 0; i < res.get(currInd).length; i++) {
            googleMap.addMarker(getMark(i));
        }
        googleMap.addPolyline(getPolyline());
        LatLngBounds latLngBounds = latLngBuilder.build();
        CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, WIDTH, WIDTH, 25);
        googleMap.moveCamera(track);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currInd++;
                if(currInd == res.size()) currInd--;
                googleMap.clear();
                googleMap.addMarker(getMark(-1));
                for (int i = 0; i < res.get(currInd).length; i++) {
                    googleMap.addMarker(getMark(i));
                }
                googleMap.addPolyline(getPolyline());
                LatLngBounds latLngBounds = latLngBuilder.build();
                CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, WIDTH, WIDTH, 25);//width это размер нашего экрана
                googleMap.moveCamera(track);
            }
        });
        bntPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currInd--;
                if(currInd < 0) currInd++;
                googleMap.clear();
                googleMap.addMarker(getMark(-1));
                for (int i = 0; i < res.get(currInd).length; i++) {
                    googleMap.addMarker(getMark(i));
                }
                googleMap.addPolyline(getPolyline());
                LatLngBounds latLngBounds = latLngBuilder.build();
                CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, WIDTH, WIDTH, 25);//width это размер нашего экрана
                googleMap.moveCamera(track);
            }
        });
    }
    void getDirection(String []ff){
        try {
            result = DirectionsApi.newRequest(geoApiContext)
                    .origin(hotel.getLat() + "," + hotel.getLon())//Место старта
                    .destination(hotel.getLat() + "," + hotel.getLon())//Пункт назначения
                    .mode(TravelMode.WALKING)
                    .waypoints(ff)
                    .await();
        } catch (Exception e) {
        }
    }
    MarkerOptions getMark(int i){
        if(i == -1){
            MarkerOptions marker = new MarkerOptions()
                    .position(new LatLng(hotel.getLat(), hotel.getLon()));
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.flat_icon));
            return marker;
        }
        MarkerOptions marker = new MarkerOptions()
                .position(res.get(currInd)[i]);
        if(i == 0) marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red1));
        else if(i == 1) marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red2));
        else if(i == 2) marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red3));
        else if(i == 3) marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red4));
        else if(i == 4) marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red5));
        else if(i == 5) marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red6));
        else if(i == 6) marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red7));
        else if(i == 7) marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red8));
        else if(i == 8) marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red9));
        else if(i == 9) marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red10));
        else if(i == 10) marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red11));
        else if(i == 11) marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red12));
        return marker;
    }
    PolylineOptions getPolyline(){
        String []ff = new String[res.get(currInd).length];
        for(int i = 0; i < res.get(currInd).length; i++){
            ff[i] = (res.get(currInd)[i].latitude) + "," + (res.get(currInd)[i].longitude);
        }
        getDirection(ff);
        List<com.google.maps.model.LatLng> path = result.routes[0].overviewPolyline.decodePath();
        PolylineOptions line = new PolylineOptions();
        latLngBuilder = new LatLngBounds.Builder();
        for (int i = 0; i < path.size(); i++) {
            line.add(new com.google.android.gms.maps.model.LatLng(path.get(i).lat, path.get(i).lng));
            latLngBuilder.include(new com.google.android.gms.maps.model.LatLng(path.get(i).lat, path.get(i).lng));
        }
        line.width(16f).color(R.color.quantum_bluegrey50);
        return line;
    }
}