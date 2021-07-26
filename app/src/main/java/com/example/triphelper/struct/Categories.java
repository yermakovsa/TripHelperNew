package com.example.triphelper.struct;

public class Categories {
    int imageID;
    String text;
    public Categories(int imageID, String text){
        this.imageID = imageID;
        this.text = text;
    }
    void setImageID(int imageID){
        this.imageID = imageID;
    }
    void setText(String text){
        this.text = text;
    }
    public int getImageID(){
        return imageID;
    }
    public String getText(){
        return text;
    }
}
