package com.example.triphelper.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.triphelper.R;
import com.example.triphelper.fragment.MainFragments.LongDescriptionFragment;
import com.example.triphelper.handler.FragmentController;
import com.example.triphelper.mvp.core.FragmentByName;
import com.example.triphelper.placesAPI.DownloadImageTask;
import com.example.triphelper.struct.PlaceInfo;

import static com.example.triphelper.activity.MainActivity.HEIGHT;
import static com.example.triphelper.activity.MainActivity.WIDTH;
import static com.example.triphelper.activity.MainActivity.hotel;
import static com.example.triphelper.activity.MainActivity.listOfPlaces;
import static com.example.triphelper.activity.MainActivity.ok;
import static com.example.triphelper.fragment.MainFragments.ListOfPlacesFragment.currIndexInListOfPlaces;
import static com.example.triphelper.fragment.MainFragments.ListOfPlacesFragment.currNameInListOfPlaces;

public class PlaceInfoViewHolder  extends  RecyclerView.ViewHolder  {
    ImageView placeImage;
    TextView name;
    TextView rating;
    TextView adress;
    ImageView selectedImage;
    RatingBar ratingBar;
    public PlaceInfoViewHolder(View itemView){
        super(itemView);
        placeImage = (ImageView) itemView.findViewById(R.id.placeImage);
        selectedImage = (ImageView) itemView.findViewById(R.id.imageSelected);
        name = (TextView) itemView.findViewById(R.id.name);
        rating = (TextView) itemView.findViewById(R.id.shortDescription);
        adress = (TextView) itemView.findViewById(R.id.textShortAdress);
        ratingBar = itemView.findViewById(R.id.ratingBar);
    }
    public void bind(PlaceInfo placeInfo){
        name.setText(placeInfo.getName());
        rating.setText("" + placeInfo.getRating());
        adress.setText(placeInfo.getAdress());
        ratingBar.setRating((float) placeInfo.getRating());
        int width = WIDTH;
        int height = (int) ((double)HEIGHT / 10 * 3.2);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,height);
        placeImage.setLayoutParams(params);
        //placeImage.setImageResource(shortDescription.getImageId());
        //new DownloadImageTask(placeImage)
        //      .execute("https://www.dlit.dp.ua/wp-content/uploads/2019/09/%D0%92%D0%B8%D1%82%D1%8F-%D1%81-%D0%BE%D1%80%D0%BB%D0%BE%D0%BC-3-1024x768.jpg");
        new DownloadImageTask(placeImage)
                .execute(placeInfo.getImage());
        //System.out.println(shortDescription.getImageId() + " ATTENTION !!!!!!!!");
        if(placeInfo.getIsChecked()) selectedImage.setImageResource(R.drawable.checkbox_square_checked);
        else selectedImage.setImageResource(R.drawable.checkbox_square_unchecked);
        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(placeInfo.getIsChecked()) {selectedImage.setImageResource(R.drawable.checkbox_square_unchecked);
                    placeInfo.setIsChecked(false);
                }
                else {selectedImage.setImageResource(R.drawable.checkbox_square_checked);
                    placeInfo.setIsChecked(true);
                }
                for(int i = 0; i < listOfPlaces.get(currIndexInListOfPlaces).size(); i++)
                    if(listOfPlaces.get(currIndexInListOfPlaces).get(i).getName().equals(placeInfo.getName())){
                        listOfPlaces.get(currIndexInListOfPlaces).get(i).setIsChecked(placeInfo.getIsChecked());
                        if(!ok && placeInfo.getIsChecked()){
                            hotel = listOfPlaces.get(currIndexInListOfPlaces).get(i);
                        }
                    }
            }
        });
        placeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currNameInListOfPlaces = placeInfo.getName();
                FragmentController.changeNextFragment(new LongDescriptionFragment(), FragmentByName.LONG_DESCRIPTION_FRAGMENT);
            }
        });
        ratingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currNameInListOfPlaces = placeInfo.getName();
                FragmentController.changeNextFragment(new LongDescriptionFragment(), FragmentByName.LONG_DESCRIPTION_FRAGMENT);
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currNameInListOfPlaces = placeInfo.getName();
                FragmentController.changeNextFragment(new LongDescriptionFragment(), FragmentByName.LONG_DESCRIPTION_FRAGMENT);
            }
        });
        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currNameInListOfPlaces = placeInfo.getName();
                FragmentController.changeNextFragment(new LongDescriptionFragment(), FragmentByName.LONG_DESCRIPTION_FRAGMENT);
            }
        });
        adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currNameInListOfPlaces = placeInfo.getName();
                FragmentController.changeNextFragment(new LongDescriptionFragment(), FragmentByName.LONG_DESCRIPTION_FRAGMENT);
            }
        });

    }
}
