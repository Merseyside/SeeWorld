package com.merseyside.admin.seeworld;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.merseyside.admin.seeworld.Modules.AppModule;
import com.merseyside.admin.seeworld.Modules.SeaModule;
import com.merseyside.admin.seeworld.Modules.SettingsModule;

public class Application extends android.app.Application {

    private static AppComponent component;
    public static AppComponent getComponent(){
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
    }

    private AppComponent buildComponent(){
        Context context = getApplicationContext();
        getScreenDimensions(context);
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .seaModule(new SeaModule())
                .settingsModule(new SettingsModule())
                .build();
    }

    private void getScreenDimensions(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        Constants.width = size.x;
        Constants.height = size.y;

        Log.d("Size", "x = " + Constants.width + " y = " + Constants.height);
    }
}
