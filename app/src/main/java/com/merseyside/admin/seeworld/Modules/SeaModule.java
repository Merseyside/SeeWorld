package com.merseyside.admin.seeworld.Modules;

import com.merseyside.admin.seeworld.SeeWorld.Sea;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ivan_ on 25.07.2017.
 */

@Module
public class SeaModule {

    @Provides
    @Singleton
    public Sea provideSea(){
        return new Sea();
    }
}
