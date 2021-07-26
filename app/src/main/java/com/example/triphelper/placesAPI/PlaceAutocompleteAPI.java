package com.example.triphelper.placesAPI;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class PlaceAutocompleteAPI {
    public static String KEY = "AIzaSyDFLZwNZ3j2KNxR6_G4kqL4kO34cUxDZyU";
    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/";
    private static Retrofit retrofit = null;

    public interface ApiInterface {
        @GET("textsearch/json")
        Call<PlaceSerializer> getPredictions(
                @Query("key") String key,
                @Query("input") String input
        );

        @GET("details/json")
        Call<PlaceDetailSerializer> getPlace(
                @Query("key") String key,
                @Query("placeid") String placeid
        );
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
