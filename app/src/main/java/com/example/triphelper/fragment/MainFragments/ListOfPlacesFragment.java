package com.example.triphelper.fragment.MainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.example.triphelper.handler.OnSwipeTouchListener;
import com.example.triphelper.mvp.core.FragmentByName;
import com.example.triphelper.struct.Categories;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.triphelper.activity.MainActivity.HEIGHT;
import static com.example.triphelper.activity.MainActivity.listOfPlaces;

public class ListOfPlacesFragment extends Fragment {
    private RecyclerView shortDescriptionRecyclerView;
    private PlaceInfoAdapter placeInfoAdapter;
    private LinearLayout categoriesLayout;
    private List<Categories> listOfCategories;

    private int currWidth = -1, currHeight = -1 ;
    public static int currIndexInListOfPlaces = 0;
    public static String currNameInListOfPlaces = "";
    private int currIndexSelected = 0;
    private int lastIndexSelected = 0;
    private Toolbar mToolbar;
    private FloatingActionButton fab;
    // Context context = this;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.list_of_places, container, false);
        setHasOptionsMenu(true);
        listOfCategories = new ArrayList<>();
        categoriesLayout = (LinearLayout)rootView.findViewById(R.id.categoriesLinearLayout);
        shortDescriptionRecyclerView = rootView.findViewById(R.id.shortD_recycler_view);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        fab = rootView.findViewById(R.id.fab2);
        // setSupportActionBar(mToolbar); -- doesnt work!!!
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
      //  listOfPlaces = new ArrayList<>();
       // initTest();
        fillListOfCategories();
        initRecyclerView();
        shortDescriptionRecyclerView.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeTop() {
                //   Toast.makeText(ListOfPlacesFragment.this, "top", Toast.LENGTH_SHORT).show();
                // SystemFunctions.makeAnErrorToast("bottom");
            }
            public void onSwipeRight() {
                // Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
                //SystemFunctions.makeAnErrorToast("left");
                lastIndexSelected = currIndexInListOfPlaces;
                currIndexInListOfPlaces--;
                if(currIndexInListOfPlaces == -1) currIndexInListOfPlaces = listOfPlaces.size() - 1;
                placeInfoAdapter.clearItems();
                placeInfoAdapter.setItems(listOfPlaces.get(currIndexInListOfPlaces));
                categoriesLayout.getChildAt(lastIndexSelected).findViewById(R.id.isSelectedCategory)
                        .setVisibility(View.INVISIBLE);
                categoriesLayout.getChildAt(currIndexInListOfPlaces).findViewById(R.id.isSelectedCategory)
                        .setVisibility(View.VISIBLE);
            }
            public void onSwipeLeft() {
                //Toast.makeText(MyActivity.this, "left", Toast.LENGTH_SHORT).show();
                //SystemFunctions.makeAnErrorToast("right");
                lastIndexSelected = currIndexInListOfPlaces;
                currIndexInListOfPlaces++;
                if(currIndexInListOfPlaces == listOfPlaces.size()) currIndexInListOfPlaces = 0;
                placeInfoAdapter.clearItems();
                placeInfoAdapter.setItems(listOfPlaces.get(currIndexInListOfPlaces));
                categoriesLayout.getChildAt(lastIndexSelected).findViewById(R.id.isSelectedCategory)
                        .setVisibility(View.INVISIBLE);
                categoriesLayout.getChildAt(currIndexInListOfPlaces).findViewById(R.id.isSelectedCategory)
                        .setVisibility(View.VISIBLE);
            }

        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentController.changeNextFragment(new MapFragment(), FragmentByName.MAP_FRAGMENT);
            }
        });
        return rootView;
    }
    //  @Override
    //public boolean onCreateOptionsMenu(Menu menu) {
    //  return super.onCreateOptionsMenu(menu);
    // }
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
    private void initRecyclerView() {
        shortDescriptionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        placeInfoAdapter = new PlaceInfoAdapter();
        fileListTest();
        shortDescriptionRecyclerView.setAdapter(placeInfoAdapter);
    }
    public void fileListTest(){
       // List<ShortDescription> listOfSightseeings = new ArrayList<>();
       // List<LongDescription> longListOfSightseeings = new ArrayList<>();
       /* listOfSightseeings.add(new ShortDescription(R.drawable.coliseum, "Колизей",
                "Древнеримская арена гладиаторских боев", false));
        listOfSightseeings.add(new ShortDescription(R.drawable.cathedral, "Базилика Святого Петра",
                "Крупнейшая христианская церковь в мире", false));
        listOfSightseeings.add(new ShortDescription(R.drawable.forum, "Римский форум",
                "Руины самого сердца Римской Империи", false));
        listOfSightseeings.add(new ShortDescription(R.drawable.pantheon, "Пантеон",
                "Римский храм и исторические гробницы", false));*/
        //listOfPlaces.add(listOfSightseeings);
        // long Description
    //    longListOfSightseeings.add(new LongDescription("1", "2", "3", R.drawable.coliseum));
     //   longListOfSightseeings.add(new LongDescription("4", "5", "6", R.drawable.coliseum));
     //   longListOfSightseeings.add(new LongDescription("7", "8", "9", R.drawable.coliseum));
     //   longListOfSightseeings.add(new LongDescription("10", "11", "12", R.drawable.coliseum));
     //   longListOfPlaces.add(longListOfSightseeings);
     //   List<ShortDescription> listOfMuseums = new ArrayList<>();
     //   List<LongDescription> longListOfMuseums = new ArrayList<>();
       /* listOfMuseums.add(new ShortDescription(R.drawable.museum_rome, "Музей Рима",
                "Музей современного искусства", false));
        listOfMuseums.add(new ShortDescription(R.drawable.museum_vatican, "Музей Ватикана",
                "Художественный музей", false));
        listOfMuseums.add(new ShortDescription(R.drawable.museum_capitol, "Капитолийский Музей",
                "Музей истории", false));
        listOfMuseums.add(new ShortDescription(R.drawable.museum_borg, "Галерея Боргезе",
                "Художественный музей", false));*/
        //listOfPlaces.add(listOfMuseums);
        // Long descrtiption
       /* longListOfMuseums.add(new LongDescription("1", "2", "3", R.drawable.shops));
        longListOfMuseums.add(new LongDescription("4", "5", "6", R.drawable.shops));
        longListOfMuseums.add(new LongDescription("7", "8", "9", R.drawable.shops));
        longListOfMuseums.add(new LongDescription("10", "11", "12", R.drawable.shops));
        longListOfPlaces.add(longListOfMuseums);*/
        //Test
       /* longListOfPlaces.add(longListOfMuseums);
        longListOfPlaces.add(longListOfMuseums);
        longListOfPlaces.add(longListOfMuseums);*/
        //Test
       // List<ShortDescription> listOfCafes = new ArrayList<>();
      /*  listOfCafes.add(new ShortDescription(R.drawable.pantheon, "Tonnarello",
                "$$ · Итальянская кухня", false));
        listOfCafes.add(new ShortDescription(R.drawable.pantheon, "Jazz Cafè",
                "$$ · Итальянская кухня", false));
        listOfCafes.add(new ShortDescription(R.drawable.pantheon, "Matricianella",
                "$$ · Римская кухня", false));
        listOfCafes.add(new ShortDescription(R.drawable.pantheon, "Zuma Rome",
                "$$$$ · Японская кухня ", false));
     //   listOfPlaces.add(listOfCafes);
        List<ShortDescription> listOfShops = new ArrayList<>();
        listOfShops.add(new ShortDescription(R.drawable.pantheon, "ZARA Rome",
                "$$ · Магазин одежды", false));
        listOfShops.add(new ShortDescription(R.drawable.pantheon, "GUESS",
                "$$$ · Магазин одежды", false));
        listOfShops.add(new ShortDescription(R.drawable.pantheon, "Louis Vuitton",
                "$$$$ · Магазин изделий из кожи", false));
        listOfShops.add(new ShortDescription(R.drawable.pantheon, "Gap",
                "$$ · Магазин одежды", false));
       // listOfPlaces.add(listOfShops);
        List<ShortDescription> listOfSupMarkets = new ArrayList<>();
        listOfSupMarkets.add(new ShortDescription(R.drawable.pantheon, "Todis",
                "Супермаркет", false));
        listOfSupMarkets.add(new ShortDescription(R.drawable.pantheon, "Supermercato Emme Più",
                "Супермаркет", false));
        listOfSupMarkets.add(new ShortDescription(R.drawable.pantheon, "Carrefour Market",
                "Супермаркет", false));
        listOfSupMarkets.add(new ShortDescription(R.drawable.pantheon, "Coop Supermarket",
                "Супермаркет", false));
       // listOfPlaces.add(listOfSupMarkets);*/
        placeInfoAdapter.setItems(listOfPlaces.get(currIndexInListOfPlaces));
        categoriesLayout.getChildAt(lastIndexSelected).findViewById(R.id.isSelectedCategory)
                .setVisibility(View.INVISIBLE);
        categoriesLayout.getChildAt(currIndexInListOfPlaces).findViewById(R.id.isSelectedCategory)
                .setVisibility(View.VISIBLE);
    }

    private void fillListOfCategories(){
        listOfCategories.add(new Categories(R.drawable.sightseeing_icon, "Attraction"));
        listOfCategories.add(new Categories(R.drawable.museum_icon, "Museum"));
        listOfCategories.add(new Categories(R.drawable.cafe_icon, "Cafe"));
        listOfCategories.add(new Categories(R.drawable.shop_icon, "Shop"));
        listOfCategories.add(new Categories(R.drawable.supermarket_icon , "Supermarket"));
        for(int i = 0; i < listOfCategories.size(); i++)
            categoriesLayout.addView(getView(i));
    }

    public View getView(final int index){
        final View currView;
        final String currName =listOfCategories.get(index).getText();
        LayoutInflater inflater1 = getLayoutInflater();
        currView = inflater1.inflate(R.layout.categories_layout, null, false);
        ((TextView) currView.findViewById(R.id.categoryName))
                .setText(((Categories)listOfCategories.get(index)).getText());
        ((TextView) currView.findViewById(R.id.isSelectedCategory))
                .setVisibility(View.INVISIBLE);
        int width = HEIGHT / 25;
        int height = width ;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,height);
        ((ImageView) currView.findViewById(R.id.categoryImage)).setLayoutParams(params);
        ((ImageView) currView.findViewById(R.id.categoryImage))
                .setImageResource(listOfCategories.get(index).getImageID());
        currView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastIndexSelected = currIndexInListOfPlaces;
                if(currName.equals("Attraction")) {placeInfoAdapter.clearItems();
                    placeInfoAdapter.setItems(listOfPlaces.get(0));
                    currIndexInListOfPlaces = 0;
                }
                else if(currName.equals("Museum")) {placeInfoAdapter.clearItems();
                    placeInfoAdapter.setItems(listOfPlaces.get(1));
                    currIndexInListOfPlaces = 1;
                }
                else if(currName.equals("Cafe")) {placeInfoAdapter.clearItems();
                    placeInfoAdapter.setItems(listOfPlaces.get(2));
                    currIndexInListOfPlaces = 2;
                }
                else if(currName.equals("Shop")) {placeInfoAdapter.clearItems();
                    placeInfoAdapter.setItems(listOfPlaces.get(3));
                    currIndexInListOfPlaces = 3;
                }
                else  {placeInfoAdapter.clearItems();
                    placeInfoAdapter.setItems(listOfPlaces.get(4));
                    currIndexInListOfPlaces = 4;
                }
                categoriesLayout.getChildAt(lastIndexSelected).findViewById(R.id.isSelectedCategory)
                        .setVisibility(View.INVISIBLE);
                categoriesLayout.getChildAt(currIndexInListOfPlaces).findViewById(R.id.isSelectedCategory)
                        .setVisibility(View.VISIBLE);
                //  categoriesLayout.removeViewAt(currIndexInListOfPlaces);
                // categoriesLayout.addView(getView(currIndexInListOfPlaces), currIndexInListOfPlaces);
            }
        });
        return currView;
    }

}
