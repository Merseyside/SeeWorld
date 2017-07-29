package com.merseyside.admin.seeworld;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

public class Settings/* implements SharedPreferences.OnSharedPreferenceChangeListener*/{

    private Context context;
    private SharedPreferences sPref;
    private SharedPreferences.Editor editor;
    public static final String APP_PREFERENCES_HUMAN_PLAYERS = "human_players";
    private Bitmap shark_bmp, pinguin_bmp;

    private static int human_players;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    public Settings(Context context){
        this.context = context;
        decodeDrawables();
        //getAllPreferences();
    }

    public Bitmap getShark_bmp(){
        return shark_bmp;
    }

    public Bitmap getPinguin_bmp(){
        return pinguin_bmp;
    }

    /*public void getAllPreferences(){
        PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener(this);
        sPref = PreferenceManager.getDefaultSharedPreferences(context);
        human_players = sPref.getInt(APP_PREFERENCES_HUMAN_PLAYERS, 1);
    }

    public void savePreference(String preference, int value) {
        sPref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sPref.edit();
        editor.putInt(preference, value);
        editor.commit();
    }

    public static int getHuman_players(){
        return human_players;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(APP_PREFERENCES_HUMAN_PLAYERS)) human_players = sharedPreferences.getInt(APP_PREFERENCES_HUMAN_PLAYERS, 1);
    }

    public static float getWidthKoef() {
        return (float)Constants.width / (float)Constants.defaultWidth;
    }

    public static float getHeightKoef() {
        return (float)Constants.height / (float)Constants.defaultHeight;
    }

    public void verifyStoragePermissions(Activity activity) {
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED)  {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }*/

    private void decodeDrawables(){
        shark_bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.orca), Constants.CELL_SIZE, Constants.CELL_SIZE,true);
        pinguin_bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.tux), Constants.CELL_SIZE, Constants.CELL_SIZE,true);
    }

}
