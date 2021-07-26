package com.example.triphelper.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.triphelper.R;
import com.example.triphelper.fragment.MainFragments.MainMenuFragment;
import com.example.triphelper.handler.FragmentController;
import com.example.triphelper.handler.SystemFunctions;
import com.example.triphelper.mvp.core.FragmentByName;
import com.example.triphelper.placesAPI.PlaceAutocompleteAPI;
import com.example.triphelper.placesAPI.Reader;
import com.example.triphelper.struct.PlaceInfo;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static String city = "";
    public static Reader reader;
    public static List<List <PlaceInfo> > listOfPlaces;
    public static PlaceAutocompleteAPI.ApiInterface api;
    public static FragmentManager fragmentManager;
    public static FragmentTransaction fragmentTransaction;
    static public int WIDTH, HEIGHT;
    public static final String MY_SETTINGS = "my_settings";
    public static Context context;
    public static PlaceInfo hotel;
    public static boolean ok = false;
    MainActivity mainActivity = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WIDTH = getWindowManager().getDefaultDisplay().getWidth();
        HEIGHT = getWindowManager().getDefaultDisplay().getHeight();
        api = PlaceAutocompleteAPI.getClient().create(PlaceAutocompleteAPI.ApiInterface.class);
        //getSupportActionBar().hide();
        reader = new Reader();
        fragmentManager = getSupportFragmentManager();
        context = this;
        FragmentController.changeNextFragment(new MainMenuFragment(), FragmentByName.MAIN_MENU_FRAGMENT);
    }
    @Override
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount() == 0){
            SystemFunctions.exitApllication(mainActivity);
        }else fragmentManager.popBackStack();
    }
}

