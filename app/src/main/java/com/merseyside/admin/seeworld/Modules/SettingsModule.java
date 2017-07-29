package com.merseyside.admin.seeworld.Modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.merseyside.admin.seeworld.Settings;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ivan_ on 26.07.2017.
 */

@Module
public class SettingsModule {

    @Provides
    @NonNull
    public Settings providePack(@NonNull Context context){
        return new Settings(context);
    }
}
