package com.example.triphelper.struct;

import java.util.List;

public class PlaceInfo {
    String name, description, adress, place_id, website, image;
    List< String > weekday;
    double lat, lon, rating;
    boolean isChecked;
    public PlaceInfo(String name, String description, String adress, String place_id, String website,
                     List < String > weekday, double lat, double lon, double rating, String image, boolean isChecked){
        this.name = name;
        this.description = description;
        this.adress = adress;
        this.place_id = place_id;
        this.website = website;
        this.weekday = weekday;
        this.lat = lat;
        this.lon = lon;
        this.rating = rating;
        this.image = image;
        this.isChecked = isChecked;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setDescription(String description){ this.description = description; }
    public void setAdress(String adress){
        this.adress = adress;
    }
    public void setPlace_id(String place_id){this.place_id = place_id;}
    public  void setWebsite(String website) {this.website = website;}
    public  void setWeekday(List < String >  weekday) {this.weekday = weekday;}
    public void setLat(double lat){this.lat = lat;}
    public void setLon(double lon){this.lon = lon;}
    public void setRating(double rating){this.rating = rating;}
    public void setImage(String image){
        this.image = image;
    }
    public void setIsChecked(boolean isChecked){
        this.isChecked = isChecked;
    }

    public String  getName(){ return  name; }
    public String getDescription(){ return description; }
    public String getAdress(){ return adress; }
    public String getPlace_id(){return place_id;}
    public String getWebsite(){return website;}
    public List < String > getWeekday(){return weekday;}
    public double getLat(){return lat;}
    public double getLon(){return lon;}
    public double getRating(){return rating;}
    public String getImage(){
        return image;
    }
    public boolean getIsChecked(){ return isChecked; }
}
