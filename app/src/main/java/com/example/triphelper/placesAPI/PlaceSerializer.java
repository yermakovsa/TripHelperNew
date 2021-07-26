package com.example.triphelper.placesAPI;

import com.google.gson.annotations.*;

import java.util.List;

public class PlaceSerializer {
    public class Place {
        @SerializedName("place_id")
        private String placeID;

        //@SerializedName("description")
        private String description = " sosi ";

        @SerializedName("types")
        private String[] types;

        public Place(String placeID, String description, String[] types) {
            this.placeID = placeID;
            this.description = description;
            this.types = types;
        }

        public String getPlaceID() {
            return placeID;
        }

        public String getDescription() {
            return description;
        }

        public String[] getTypes() {
            return types;
        }

        @Override
        public String toString() {
            return "Place{" +
                    "description='" + description + '\'' +
                    '}';
        }
    }

    @SerializedName("results")
    private List<Place> predictions;

    @SerializedName("status")
    private String status;

    public List<Place> getPlaces() {
        return predictions;
    }

    public String getStatus() {
        return status;
    }
}