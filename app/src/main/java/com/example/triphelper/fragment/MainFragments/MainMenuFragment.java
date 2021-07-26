package com.example.triphelper.fragment.MainFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.triphelper.R;
import com.example.triphelper.handler.FragmentController;
import com.example.triphelper.mvp.core.FragmentByName;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.triphelper.activity.MainActivity.city;
import static com.example.triphelper.activity.MainActivity.listOfPlaces;
import static com.example.triphelper.activity.MainActivity.reader;

public class MainMenuFragment extends Fragment {

    private boolean ok = false;
    private Button btn;
    private EditText txtCity, arrivalDay, departureDay;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.main_menu, container, false);
        btn = (Button)rootView.findViewById(R.id.btnStart);
        txtCity = (EditText)rootView.findViewById(R.id.textCity);
        arrivalDay = (EditText)rootView.findViewById(R.id.editArrivalDay);
        departureDay = (EditText)rootView.findViewById(R.id.editDepartureDay);
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String date = formatter.format(today);
        arrivalDay.setText(date);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        today = cal.getTime();
        date = formatter.format(today);
        departureDay.setText(date);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FragmentController.changeNextFragment(new MapFragment(), FragmentByName.MAP_FRAGMENT);
                city = txtCity.getText().toString();
                ProgressDialog pd = new ProgressDialog(((AppCompatActivity)getActivity()));
                pd.setTitle("Please wait");
                pd.setMessage("Looking for hotels...");
                pd.show();
                initTest(city);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.cancel();
                        FragmentController.changeNextFragment(new ListOfHotelsFragment(), FragmentByName.LIST_OF_HOTELS);
                    }
                }, 17500);
            }
        });
        return rootView;
    }
    void initTest(String txt){
        listOfPlaces = new ArrayList<>();
        listOfPlaces.add(reader.getItems("Hotels in " + txt));
       // listOfPlaces.add(reader.getItems("Attractions in " + txt));
        listOfPlaces.add(reader.getItems("Museums in " + txt));
        listOfPlaces.add(reader.getItems("Cafes in " + txt));
        listOfPlaces.add(reader.getItems("Shops in " + txt));
        listOfPlaces.add(reader.getItems("Supermarkets in " + txt));
        //ok = true;
    }
}
