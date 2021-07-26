package com.example.triphelper.handler;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.triphelper.activity.StartActivity;

public class SystemFunctions {
    public static boolean firstLaunch;
    public void launch(StartActivity startActivity){
        firstLaunch = StartActivity.sp.getBoolean("firstLaunch", false);
        if(!firstLaunch){
            SharedPreferences.Editor e = StartActivity.sp.edit();
            e.putBoolean("firstLaunch", true);
            e.commit();
        }else{
            startActivity.goToMainActivity();
            startActivity.finish();
        }
    }
    public static void makeAnErrorToast(String text, Activity activity){
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
    }

    public static void exitApllication(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Вы уверены,что хотите закрыть приложение?")
                .setPositiveButton("Да",
                        (dialog, id) -> activity.finish())
                .setNegativeButton("Нет",
                        (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }
}
