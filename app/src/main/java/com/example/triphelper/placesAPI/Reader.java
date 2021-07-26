package com.example.triphelper.placesAPI;

import android.util.Log;

import com.example.triphelper.struct.PlaceInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.triphelper.activity.MainActivity.api;

public class Reader {

    String TAG = "PLACE";
    public List<PlaceInfo> getItems(String input) {
        List<PlaceInfo> resultList = new ArrayList<>();
        String key = PlaceAutocompleteAPI.KEY;
        Log.d(TAG, input); /* show url */
        Call<PlaceSerializer> callPlaces = api.getPredictions(key, input);
        Log.d(TAG, callPlaces.request().url().toString()); /* show url */
        try {
            callPlaces.enqueue(new Callback<PlaceSerializer>() {
                @Override
                public void onResponse(Call<PlaceSerializer> call, Response<PlaceSerializer> response) {
                    List<PlaceSerializer.Place> places;
                    PlaceSerializer predictions = response.body();
                    places = predictions.getPlaces();
                    Log.d(TAG, "Succesful");
                    if (!places.isEmpty()) {
                        for (PlaceSerializer.Place place : places) {
                            resultList.add(detailInformation(place.getPlaceID()));
                        }
                    }
                    else    Log.d(TAG, "EMPTY!!!");
                }
                @Override
                public void onFailure(Call<PlaceSerializer> call, Throwable t) {
                    Log.d(TAG, "Connected failed");
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error connecting to Places API", e);
            return resultList;
        }
        Log.d(TAG, resultList.size() + " = resultList size");
        return resultList;
    }
    PlaceInfo detailInformation(String placeID){
        PlaceInfo currPoint = new PlaceInfo("roflanENtin", "2", "3", "ura",
                "s",new ArrayList<>(),  2, 3, 4, "https://starpri.ru/wp-content/uploads/2019/02/mX2YdEeLJUo.jpg", false);
        String key = PlaceAutocompleteAPI.KEY;
        Call<PlaceDetailSerializer> callPlaces = api.getPlace(key, placeID);
        Log.d(TAG, callPlaces.request().url().toString()); /* show url */
        try {
            callPlaces.enqueue(new Callback<PlaceDetailSerializer>() {
                @Override
                public void onResponse(Call<PlaceDetailSerializer> call, Response<PlaceDetailSerializer> response) {
                    PlaceDetailSerializer predictions = response.body();
                    PlaceDetailSerializer.Place currPlace = predictions.getPlace();
                    Log.d(TAG, "Succesful");
                    Log.d(TAG, currPlace.toString());
                    //String name = currPlace.getName();
                    //String description = currPlac
                    currPoint.setLat(currPlace.getLat());
                    currPoint.setLon(currPlace.getLon());
                    currPoint.setName(currPlace.getName());
                    currPoint.setAdress(currPlace.getAddress());
                    currPoint.setPlace_id(currPlace.getID());
                    currPoint.setRating(currPlace.getRating());
                    currPoint.setWeekday(currPlace.getWeekday());
                    String url = currPlace.getPhotoURL(600);
                    if(url == null) url = "https://starpri.ru/wp-content/uploads/2019/02/mX2YdEeLJUo.jpg";
                    currPoint.setImage(url);
                }
                @Override
                public void onFailure(Call<PlaceDetailSerializer> call, Throwable t) {
                    Log.d(TAG, "Connected failed");
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error connecting to Places API", e);
        }
        return currPoint;
    }
}

