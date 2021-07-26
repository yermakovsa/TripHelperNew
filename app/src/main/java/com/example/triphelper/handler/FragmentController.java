package com.example.triphelper.handler;

import androidx.fragment.app.Fragment;

import com.example.triphelper.R;
import com.example.triphelper.activity.MainActivity;
import com.example.triphelper.mvp.core.FragmentByName;

import org.jetbrains.annotations.NotNull;

public class FragmentController {
    public static void changeNextFragment(Fragment fragment, @NotNull FragmentByName fragmentByName){
        if(fragmentByName.equalsName(FragmentByName.MAIN_MENU_FRAGMENT.toString())){
            MainActivity.fragmentTransaction = MainActivity.fragmentManager.beginTransaction();
            MainActivity.fragmentTransaction.add(R.id.main_layout, fragment);
            MainActivity.fragmentTransaction.commit();
        }
        else {
            MainActivity.fragmentTransaction = MainActivity.fragmentManager.beginTransaction();
            MainActivity.fragmentTransaction.replace(R.id.main_layout, fragment);
            MainActivity.fragmentTransaction.addToBackStack(fragmentByName.toString());
            MainActivity.fragmentTransaction.commit();
        }
    }
    public static void returnToPreviousFragment(@NotNull FragmentByName fragmentByName){
        if(fragmentByName.equalsName(FragmentByName.MAIN_MENU_FRAGMENT.toString()))
            MainActivity.fragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
        else MainActivity.fragmentManager.popBackStackImmediate(fragmentByName.toString(), 0);
    }
}
