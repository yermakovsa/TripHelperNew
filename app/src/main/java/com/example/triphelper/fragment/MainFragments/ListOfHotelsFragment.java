package com.example.triphelper.fragment.MainFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.triphelper.R;
import com.example.triphelper.adapter.PlaceInfoAdapter;
import com.example.triphelper.handler.FragmentController;
import com.example.triphelper.mvp.core.FragmentByName;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.triphelper.activity.MainActivity.city;
import static com.example.triphelper.activity.MainActivity.hotel;
import static com.example.triphelper.activity.MainActivity.listOfPlaces;
import static com.example.triphelper.activity.MainActivity.ok;
import static com.example.triphelper.activity.MainActivity.reader;
import static com.example.triphelper.fragment.MainFragments.ListOfPlacesFragment.currIndexInListOfPlaces;

public class ListOfHotelsFragment extends Fragment {
    private RecyclerView shortDescriptionRecyclerView;
    private PlaceInfoAdapter placeInfoAdapter;
    private Toolbar mToolbar;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.list_of_hotels, container, false);
        setHasOptionsMenu(true);
        ok = false;
        hotel = listOfPlaces.get(0).get(0);
        shortDescriptionRecyclerView = rootView.findViewById(R.id.recView);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbarHot);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        fab = rootView.findViewById(R.id.fabAdd);
        shortDescriptionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        placeInfoAdapter = new PlaceInfoAdapter();
        placeInfoAdapter.setItems(listOfPlaces.get(currIndexInListOfPlaces));
        shortDescriptionRecyclerView.setAdapter(placeInfoAdapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog pd = new ProgressDialog(((AppCompatActivity)getActivity()));
                pd.setTitle("Please wait");
                pd.setMessage("Looking for attracions...");
                pd.show();
                listOfPlaces.remove(0);
                listOfPlaces.add(0, reader.getItems("Attractions in " + city));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ok = true;
                        pd.cancel();
                        // https://medium.com/nuances-of-programming/%D0%BA%D0%B0%D0%BA-%D1%81%D0%BE%D0%B7%D0%B4%D0%B0%D1%82%D1%8C-%D0%BF%D0%BB%D0%B0%D0%B2%D0%B0%D1%8E%D1%89%D1%83%D1%8E-%D0%BA%D0%BD%D0%BE%D0%BF%D0%BA%D1%83-%D0%B4%D0%BB%D1%8F-android-%D0%BF%D1%80%D0%B8%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D1%8F-eda90d7c574b
                        FragmentController.changeNextFragment(new ListOfPlacesFragment(), FragmentByName.LIST_OF_PLACES_FRAGMENT);
                    }
                }, 5000);
            }
        });
        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_of_places_menu, menu);
        MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                placeInfoAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                placeInfoAdapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }
}
